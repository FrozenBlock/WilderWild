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
import net.frozenblock.wilderwild.block.IcicleBlock;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.tag.WWGameEventTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class IcicleBlockEntity extends BlockEntity implements GameEventListener.Provider<VibrationSystem.Listener>, VibrationSystem {
	private static final Logger LOGGER = LogUtils.getLogger();
	private final Listener vibrationListener;
	private final User vibrationUser = this.createVibrationUser();
	private Data vibrationData;

	public IcicleBlockEntity(BlockPos pos, BlockState state) {
		super(WWBlockEntityTypes.ICICLE, pos, state);
		this.vibrationData = new Data();
		this.vibrationListener = new Listener(this);
	}

	public void serverTick(Level level, BlockPos pos, BlockState state) {
		Ticker.tick(level, this.getVibrationData(), this.getVibrationUser());
	}

	@Override
	public void loadAdditional(ValueInput valueInput) {
		super.loadAdditional(valueInput);
		this.vibrationData = valueInput.read("listener", Data.CODEC).orElseGet(Data::new);
	}

	@Override
	protected void saveAdditional(ValueOutput valueOutput) {
		super.saveAdditional(valueOutput);
		valueOutput.store("listener", Data.CODEC, this.vibrationData);
	}

	public User createVibrationUser() {
		return new IcicleBlockEntity.VibrationUser(this.getBlockPos());
	}

	@Override
	public Listener getListener() {
		return this.vibrationListener;
	}

	@Override
	public Data getVibrationData() {
		return this.vibrationData;
	}

	@Override
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
		public PositionSource getPositionSource() {
			return this.positionSource;
		}

		@Override
		public TagKey<GameEvent> getListenableEvents() {
			return WWGameEventTags.MAKES_ICICLE_FALL;
		}

		@Override
		public boolean canReceiveVibration(
			ServerLevel level,
			BlockPos pos,
			Holder<GameEvent> gameEvent,
			@Nullable GameEvent.Context context
		) {
			if (pos.equals(this.blockPos) && (gameEvent == GameEvent.BLOCK_DESTROY || gameEvent == GameEvent.BLOCK_PLACE)) return false;
			if (IcicleBlockEntity.this.getBlockState().getValue(IcicleBlock.TIP_DIRECTION) == Direction.UP) return false;
			return level.getBlockState(IcicleBlockEntity.this.getBlockPos().above()).is(WWBlockTags.ICICLE_FALLS_FROM);
		}

		@Override
		public void onReceiveVibration(
			ServerLevel level,
			BlockPos pos,
			Holder<GameEvent> gameEvent,
			@Nullable Entity entity,
			@Nullable Entity entity2,
			float f
		) {
			if (IcicleBlockEntity.this.getBlockState().getBlock() instanceof IcicleBlock icicleBlock) icicleBlock.triggerFall(level, this.blockPos);
		}

		@Override
		public void onDataChanged() {
			IcicleBlockEntity.this.setChanged();
		}
	}
}
