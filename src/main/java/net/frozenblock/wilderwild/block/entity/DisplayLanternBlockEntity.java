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
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.entity.ai.firefly.FireflyAi;
import net.frozenblock.wilderwild.entity.variant.FireflyColor;
import net.frozenblock.wilderwild.item.FireflyBottle;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class DisplayLanternBlockEntity extends BlockEntity {
	private final ArrayList<Occupant> fireflies = new ArrayList<>();
	public NonNullList<ItemStack> inventory;
	public int age;
	public boolean clientHanging;

	public DisplayLanternBlockEntity(@NotNull BlockPos pos, @NotNull BlockState blockState) {
		super(RegisterBlockEntities.DISPLAY_LANTERN, pos, blockState);
		this.inventory = NonNullList.withSize(1, ItemStack.EMPTY);
	}

	public void serverTick(@NotNull Level level, @NotNull BlockPos pos) {
		if (!this.fireflies.isEmpty()) {
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
		ClientboundBlockEntityDataPacket updatePacket = this.getUpdatePacket();
		if (updatePacket != null) {
			for (ServerPlayer player : PlayerLookup.tracking(this)) {
				player.connection.send(updatePacket);
			}
		}
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}


	@NotNull
	@Override
	public CompoundTag getUpdateTag() {
		return this.saveWithoutMetadata();
	}

	public boolean invEmpty() {
		Optional<ItemStack> stack = this.inventory.stream().findFirst();
		return stack.map(itemStack -> itemStack == ItemStack.EMPTY).orElse(true);
	}

	public Optional<ItemStack> getItem() {
		return this.inventory.stream().findFirst();
	}

	public boolean noFireflies() {
		return this.getFireflies().isEmpty();
	}

	@Override
	public void load(@NotNull CompoundTag tag) {
		super.load(tag);
		if (tag.contains("Fireflies", Tag.TAG_LIST)) {
			this.fireflies.clear();
			DataResult<List<Occupant>> var10000 = Occupant.CODEC.listOf().parse(new Dynamic<>(NbtOps.INSTANCE, tag.getList("Fireflies", 10)));
			Logger var10001 = WilderSharedConstants.LOGGER;
			Objects.requireNonNull(var10001);
			Optional<List<Occupant>> list = var10000.resultOrPartial(var10001::error);
			if (list.isPresent()) {
				List<Occupant> fireflyList = list.get();
				this.fireflies.addAll(fireflyList);
			}
		}
		this.inventory = NonNullList.withSize(1, ItemStack.EMPTY);
		ContainerHelper.loadAllItems(tag, this.inventory);
		this.age = tag.getInt("age");
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		super.saveAdditional(tag);
		DataResult<Tag> fireflies = Occupant.CODEC.listOf().encodeStart(NbtOps.INSTANCE, this.fireflies);
		Logger logger = WilderSharedConstants.LOGGER;
		Objects.requireNonNull(logger);
		fireflies.resultOrPartial(logger::error).ifPresent(cursorsNbt -> tag.put("Fireflies", cursorsNbt));
		ContainerHelper.saveAllItems(tag, this.inventory);
		tag.putInt("age", this.age);
	}

	@NotNull
	public List<Occupant> getFireflies() {
		return this.fireflies;
	}

	public void addFirefly(@NotNull LevelAccessor levelAccessor, @NotNull FireflyBottle bottle, @NotNull String name) {
		RandomSource random = levelAccessor.getRandom();
		Vec3 newVec = new Vec3(0.5 + (0.15 - random.nextDouble() * 0.3), 0, 0.5 + (0.15 - random.nextDouble() * 0.3));
		var firefly = new Occupant(newVec, bottle.color, name, random.nextDouble() > 0.7, random.nextInt(20), 0);
		this.fireflies.add(firefly);
		if (this.level != null) {
			this.level.updateNeighbourForOutputSignal(this.getBlockPos(), this.getBlockState().getBlock());
		}
	}

	public void removeFirefly(@NotNull DisplayLanternBlockEntity.Occupant firefly) {
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
				entity.moveTo(worldPosition.getX() + firefly.pos.x, worldPosition.getY() + firefly.y + extraHeight + 0.07, worldPosition.getZ() + firefly.pos.z, 0, 0);
				entity.setFromBottle(true);
				if (level.addFreshEntity(entity)) {
					entity.hasHome = true;
					FireflyAi.rememberHome(entity, entity.blockPosition());
					entity.setColor(firefly.color);
					entity.setAnimScale(1.0F);
					if (!Objects.equals(firefly.customName, "")) {
						entity.setCustomName(Component.nullToEmpty(firefly.customName));
					}
				} else {
					WilderSharedConstants.printStackTrace("Couldn't spawn Firefly from Display Lantern!", true);
				}
			}
		}
	}

	public int getComparatorOutput() {
		if (!this.invEmpty()) {
			return 15;
		}
		if (!this.noFireflies()) {
			return Mth.clamp(this.getFireflies().size() * 4, 0, 15);
		}
		return 0;
	}

	public static class Occupant {
		public static final Codec<Occupant> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			Vec3.CODEC.fieldOf("pos").forGetter(Occupant::getPos),
			FireflyColor.CODEC.fieldOf("color").forGetter(Occupant::getColor),
			Codec.STRING.fieldOf("customName").orElse("").forGetter(Occupant::getCustomName),
			Codec.BOOL.fieldOf("flickers").orElse(false).forGetter(Occupant::getFlickers),
			Codec.INT.fieldOf("age").forGetter(Occupant::getAge),
			Codec.DOUBLE.fieldOf("y").forGetter(Occupant::getY)
		).apply(instance, Occupant::new));
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
			this.y = Math.sin(this.age * 0.03) * 0.15;
			boolean isNectar = this.getCustomName().toLowerCase().contains("nectar");

			if (isNectar != wasNamedNectar) {
				if (isNectar) {
					if (level.getGameTime() % 70L == 0L) {
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
