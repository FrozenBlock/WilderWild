/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.block.entity;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import net.frozenblock.wilderwild.block.IcicleBlock;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.tag.WWGameEventTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class IcicleBlockEntity extends BlockEntity implements GameEventListener.Provider<VibrationSystem.Listener>, VibrationSystem {
	private static final Logger LOGGER = LogUtils.getLogger();
	private final Listener vibrationListener;
	private final User vibrationUser = this.createVibrationUser();
	private Data vibrationData;

	public IcicleBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		super(WWBlockEntityTypes.ICICLE, pos, state);
		this.vibrationData = new Data();
		this.vibrationListener = new Listener(this);
	}

	public void serverTick(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state) {
		Ticker.tick(level, this.getVibrationData(), this.getVibrationUser());
	}

	@Override
	public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
		super.loadAdditional(tag, provider);

		if (tag.contains("listener")) {
			RegistryOps<Tag> registryOps = provider.createSerializationContext(NbtOps.INSTANCE);
			Data.CODEC.parse(new Dynamic<>(registryOps, tag.getCompound("listener").get()))
				.resultOrPartial((string) -> LOGGER.error("Failed to parse vibration listener for Icicle: '{}'", string))
				.ifPresent(data -> this.vibrationData = data);
		}
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
		super.saveAdditional(tag, provider);

		RegistryOps<Tag> registryOps = provider.createSerializationContext(NbtOps.INSTANCE);
		Data.CODEC.encodeStart(registryOps, this.vibrationData)
			.resultOrPartial((string) -> LOGGER.error("Failed to encode vibration listener for Icicle: '{}'", string))
			.ifPresent(nbt -> tag.put("listener", nbt));
	}

	@NotNull
	public VibrationSystem.User createVibrationUser() {
		return new IcicleBlockEntity.VibrationUser(this.getBlockPos());
	}

	@Override
	@NotNull
	public Listener getListener() {
		return this.vibrationListener;
	}

	@Override
	@NotNull
	public Data getVibrationData() {
		return this.vibrationData;
	}

	@Override
	@NotNull
	public User getVibrationUser() {
		return this.vibrationUser;
	}

	public class VibrationUser implements User {
		public static final int LISTENER_RANGE = 8;
		protected final BlockPos blockPos;
		private final PositionSource positionSource;

		public VibrationUser(BlockPos pos) {
			this.blockPos = pos;
			this.positionSource = new BlockPositionSource(pos);
		}

		@Override
		public int getListenerRadius() {
			return LISTENER_RANGE;
		}

		@Override
		@NotNull
		public PositionSource getPositionSource() {
			return this.positionSource;
		}

		@Override
		public @NotNull TagKey<GameEvent> getListenableEvents() {
			return WWGameEventTags.MAKES_ICICLE_FALL;
		}

		@Override
		public boolean canReceiveVibration(
			@NotNull ServerLevel level,
			@NotNull BlockPos pos,
			@NotNull Holder<GameEvent> gameEvent,
			@Nullable GameEvent.Context context
		) {
			if (pos.equals(this.blockPos) && (gameEvent == GameEvent.BLOCK_DESTROY || gameEvent == GameEvent.BLOCK_PLACE)) return false;
			if (IcicleBlockEntity.this.getBlockState().getValue(IcicleBlock.TIP_DIRECTION) == Direction.UP) return false;
			return level.getBlockState(IcicleBlockEntity.this.getBlockPos().above()).is(WWBlockTags.ICICLE_FALLS_FROM);
		}

		@Override
		public void onReceiveVibration(
			@NotNull ServerLevel world,
			@NotNull BlockPos pos,
			@NotNull Holder<GameEvent> gameEvent,
			@Nullable Entity entity,
			@Nullable Entity entity2,
			float f
		) {
			if (IcicleBlockEntity.this.getBlockState().getBlock() instanceof IcicleBlock icicleBlock) {
				icicleBlock.triggerFall(world, this.blockPos);
			}
		}

		@Override
		public void onDataChanged() {
			IcicleBlockEntity.this.setChanged();
		}
	}
}
