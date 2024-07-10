/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.block.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.frozenblock.lib.networking.FrozenByteBufCodecs;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.block.DisplayLanternBlock;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.entity.ai.firefly.FireflyAi;
import net.frozenblock.wilderwild.entity.variant.FireflyColor;
import net.frozenblock.wilderwild.item.FireflyBottle;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterDataComponents;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.level.redstone.Redstone;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class DisplayLanternBlockEntity extends BlockEntity {
	public static final int MAX_FIREFLY_AGE = 20;
	public static final long NECTAR_SOUND_INTERVAL = 70L;
	private final ArrayList<Occupant> fireflies = new ArrayList<>();

	public NonNullList<ItemStack> inventory;
	public int age;
	public boolean clientHanging;
	private boolean firstTick;

	public DisplayLanternBlockEntity(@NotNull BlockPos pos, @NotNull BlockState blockState) {
		super(RegisterBlockEntities.DISPLAY_LANTERN, pos, blockState);
		this.inventory = NonNullList.withSize(1, ItemStack.EMPTY);
	}

	public void serverTick(@NotNull Level level, @NotNull BlockPos pos) {
		boolean hasFireflies = !this.fireflies.isEmpty();
		if (!this.firstTick) {
			this.firstTick = true;
			if (hasFireflies) {
				BlockState state = this.getBlockState();
				level.setBlockAndUpdate(pos, state.setValue(RegisterProperties.DISPLAY_LIGHT, Mth.clamp(this.fireflies.size() * 3, 0, 15)));
			}
		}
		if (hasFireflies) {
			for (Occupant firefly : this.fireflies) {
				firefly.tick(level, pos);
			}
		}
	}

	public void clientTick(@NotNull Level level, @NotNull BlockPos pos) {
		this.age += 1;
		this.clientHanging = this.getBlockState().getValue(BlockStateProperties.HANGING);
		if (!this.fireflies.isEmpty()) {
			for (Occupant firefly : this.fireflies) {
				firefly.tick(level, pos);
			}
		}
	}

	public void updateSync() {
		for (ServerPlayer player : PlayerLookup.tracking(this)) {
			player.connection.send(Objects.requireNonNull(this.getUpdatePacket()));
		}
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@NotNull
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
		return this.saveWithoutMetadata(provider);
	}

	public boolean invEmpty() {
		return this.getItem().isEmpty();
	}

	public ItemStack getItem() {
		return this.inventory.getFirst();
	}

	public boolean noFireflies() {
		return this.getFireflies().isEmpty();
	}

	@Override
	public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
		super.loadAdditional(tag, provider);
		this.fireflies.clear();
		if (tag.contains("fireflies")) {
			Occupant.LIST_CODEC
				.parse(NbtOps.INSTANCE, tag.get("fireflies"))
				.resultOrPartial(WilderConstants.LOGGER::error)
				.ifPresent(this.fireflies::addAll);
		}
		this.inventory = NonNullList.withSize(1, ItemStack.EMPTY);
		ContainerHelper.loadAllItems(tag, this.inventory, provider);
		this.age = tag.getInt("age");
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
		super.saveAdditional(tag, provider);
		tag.put("fireflies", Occupant.LIST_CODEC.encodeStart(NbtOps.INSTANCE, this.fireflies).getOrThrow());
		ContainerHelper.saveAllItems(tag, this.inventory, provider);
		tag.putInt("age", this.age);
	}

	@SuppressWarnings("ClassEscapesDefinedScope")
	@Override
	public void applyImplicitComponents(DataComponentInput input) {
		super.applyImplicitComponents(input);
		this.fireflies.clear();
		List<Occupant> occupants = input.getOrDefault(RegisterDataComponents.FIREFLIES, List.of());
		this.fireflies.addAll(occupants);
	}

	@Override
	public void collectImplicitComponents(DataComponentMap.Builder builder) {
		super.collectImplicitComponents(builder);
		builder.set(RegisterDataComponents.FIREFLIES, this.fireflies);
	}

	@Override
	public void removeComponentsFromTag(CompoundTag compoundTag) {
		super.removeComponentsFromTag(compoundTag);
		compoundTag.remove("fireflies");
	}

	@NotNull
	public List<Occupant> getFireflies() {
		return this.fireflies;
	}

	public void addFirefly(@NotNull LevelAccessor levelAccessor, @NotNull FireflyBottle bottle, @NotNull String name) {
		RandomSource random = levelAccessor.getRandom();
		Vec3 newVec = new Vec3(0.5D + (0.15D - random.nextDouble() * 0.3D), 0D, 0.5D + (0.15D - random.nextDouble() * 0.3D));
		var firefly = new Occupant(newVec, bottle.color, name, random.nextDouble() > 0.7D, random.nextInt(MAX_FIREFLY_AGE), 0D);
		this.fireflies.add(firefly);
		if (this.level != null) {
			this.level.updateNeighbourForOutputSignal(this.getBlockPos(), this.getBlockState().getBlock());
		}
	}

	public void removeFirefly(@NotNull Occupant firefly) {
		this.fireflies.remove(firefly);
	}

	public void spawnFireflies() {
		if (this.level != null) {
			if (!this.level.isClientSide) {
				this.doFireflySpawns(level);
			}
		}
	}

	public void spawnFireflies(@NotNull Level level) {
		if (!this.getFireflies().isEmpty()) {
			this.doFireflySpawns(level);
		}
	}

	private void doFireflySpawns(@NotNull Level level) {
		double extraHeight = this.getBlockState().getValue(BlockStateProperties.HANGING) ? 0.155 : 0;
		for (Occupant firefly : this.getFireflies()) {
			Firefly entity = RegisterEntities.FIREFLY.create(level);
			if (entity != null) {
				entity.moveTo(worldPosition.getX() + firefly.pos.x, worldPosition.getY() + firefly.y + extraHeight + 0.07D, worldPosition.getZ() + firefly.pos.z, 0F, 0F);
				entity.setFromBottle(true);
				if (level.addFreshEntity(entity)) {
					entity.hasHome = true;
					FireflyAi.rememberHome(entity, entity.blockPosition());
					entity.setColor(firefly.color);
					entity.setAnimScale(1F);
					if (!Objects.equals(firefly.customName, "")) {
						entity.setCustomName(Component.nullToEmpty(firefly.customName));
					}
				} else {
					WilderConstants.printStackTrace("Couldn't spawn Firefly from Display Lantern!", true);
				}
			}
		}
	}

	public int getComparatorOutput() {
		if (!this.invEmpty()) {
			return Redstone.SIGNAL_MAX;
		}
		if (!this.noFireflies()) {
			return Mth.clamp(this.getFireflies().size() * DisplayLanternBlock.MAX_FIREFLIES, 0, LightEngine.MAX_LEVEL);
		}
		return 0;
	}

	public static class Occupant {
		public static final Codec<Occupant> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			Vec3.CODEC.fieldOf("pos").forGetter(Occupant::getPos),
			FireflyColor.CODEC.fieldOf("color").forGetter(Occupant::getColor),
			Codec.STRING.fieldOf("custom_name").orElse("").forGetter(Occupant::getCustomName),
			Codec.BOOL.fieldOf("flickers").orElse(false).forGetter(Occupant::getFlickers),
			Codec.INT.fieldOf("age").forGetter(Occupant::getAge),
			Codec.DOUBLE.fieldOf("y").forGetter(Occupant::getY)
		).apply(instance, Occupant::new));

		public static final Codec<List<Occupant>> LIST_CODEC = CODEC.listOf();
		public static final StreamCodec<RegistryFriendlyByteBuf, Occupant> STREAM_CODEC = StreamCodec.composite(
			FrozenByteBufCodecs.VEC3,
			Occupant::getPos,
			FireflyColor.STREAM_CODEC,
			Occupant::getColor,
			ByteBufCodecs.STRING_UTF8,
			Occupant::getCustomName,
			ByteBufCodecs.BOOL,
			Occupant::getFlickers,
			ByteBufCodecs.INT,
			Occupant::getAge,
			ByteBufCodecs.DOUBLE,
			Occupant::getY,
			Occupant::new
		);

		public Vec3 pos;
		public FireflyColor color;
		public String customName;
		public boolean flickers;
		public int age;
		public double y;
		public boolean wasNamedNectar;

		public Occupant(@NotNull Vec3 pos, @NotNull FireflyColor color, @NotNull String customName, boolean flickers, int age, double y) {
			this.pos = pos;
			this.color = color;
			this.customName = customName;
			this.flickers = flickers;
			this.age = age;
			this.y = y;
		}

		public void tick(Level level, BlockPos pos) {
			this.age += 1;
			this.y = Math.sin(this.age * 0.03D) * 0.15D;
			boolean isNectar = this.getCustomName().toLowerCase().contains("nectar");

			if (isNectar != wasNamedNectar) {
				if (isNectar) {
					if (level.getGameTime() % NECTAR_SOUND_INTERVAL == 0L) {
						level.playSound(null, pos, RegisterSounds.BLOCK_DISPLAY_LANTERN_NECTAR_LOOP, SoundSource.AMBIENT, 0.5F, 1.0F);
					}
					this.wasNamedNectar = true;
				} else {
					this.wasNamedNectar = false;
				}
			} else {
				this.wasNamedNectar = false;
			}
		}

		@NotNull
		public Vec3 getPos() {
			return this.pos;
		}

		@NotNull
		public FireflyColor getColor() {
			return this.color;
		}

		@NotNull
		public String getCustomName() {
			return this.customName;
		}

		public boolean getFlickers() {
			return this.flickers;
		}

		public int getAge() {
			return this.age;
		}

		public double getY() {
			return this.y;
		}
	}
}
