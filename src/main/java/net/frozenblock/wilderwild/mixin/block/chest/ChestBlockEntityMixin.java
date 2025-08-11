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

package net.frozenblock.wilderwild.mixin.block.chest;

import java.util.Optional;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.block.entity.impl.ChestBlockEntityInterface;
import net.frozenblock.wilderwild.block.impl.ChestUtil;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlockEntity.class)
public class ChestBlockEntityMixin implements ChestBlockEntityInterface {

	@Unique
	private boolean wilderWild$canBubble = true;

	@Shadow
	@Final
	@Mutable
	public ContainerOpenersCounter openersCounter;

	@Inject(
		method = "<init>(Lnet/minecraft/world/level/block/entity/BlockEntityType;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V",
		at = @At("TAIL")
	)
	public void wilderWild$customStoneChestOpenersCounter(BlockEntityType blockEntityType, BlockPos blockPos, BlockState blockState, CallbackInfo info) {
		if (ChestBlockEntity.class.cast(this) instanceof StoneChestBlockEntity stoneChestBlockEntity) {
			this.openersCounter = new ContainerOpenersCounter() {

				@Override
				protected void onOpen(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state) {
				}

				@Override
				protected void onClose(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state) {
				}

				@Override
				protected void openerCountChanged(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, int count, int openCount) {
					stoneChestBlockEntity.signalOpenCount(level, pos, state, count, openCount);
				}

				@Override
				public boolean isOwnContainer(@NotNull Player player) {
					if (player.containerMenu instanceof ChestMenu chest) {
						Container inventory = chest.getContainer();
						return inventory == stoneChestBlockEntity || inventory instanceof CompoundContainer container && container.contains(stoneChestBlockEntity);
					}
					return false;
				}
			};
		}
	}

	@ModifyVariable(method = "playSound", at = @At("HEAD"), argsOnly = true)
	private static SoundEvent wilderWild$playSound(
		SoundEvent soundEvent, Level level, BlockPos blockPos, BlockState blockState
	) {
		if (blockState.getFluidState().is(Fluids.WATER) && WWBlockConfig.get().chestBubbling) {
			if (soundEvent == SoundEvents.CHEST_OPEN) return WWSounds.BLOCK_CHEST_OPEN_UNDERWATER;
			if (soundEvent == SoundEvents.CHEST_CLOSE) return WWSounds.BLOCK_CHEST_CLOSE_UNDERWATER;
			if (soundEvent == SoundEvents.COPPER_CHEST_OPEN) return WWSounds.BLOCK_COPPER_CHEST_OPEN_UNDERWATER;
			if (soundEvent == SoundEvents.COPPER_CHEST_CLOSE) return WWSounds.BLOCK_COPPER_CHEST_CLOSE_UNDERWATER;
			if (soundEvent == SoundEvents.COPPER_CHEST_OXIDIZED_OPEN) return WWSounds.BLOCK_COPPER_CHEST_OXIDIZED_OPEN_UNDERWATER;
			if (soundEvent == SoundEvents.COPPER_CHEST_OXIDIZED_CLOSE) return WWSounds.BLOCK_COPPER_CHEST_OXIDIZED_CLOSE_UNDERWATER;
			if (soundEvent == SoundEvents.COPPER_CHEST_WEATHERED_OPEN) return WWSounds.BLOCK_COPPER_CHEST_WEATHERED_OPEN_UNDERWATER;
			if (soundEvent == SoundEvents.COPPER_CHEST_WEATHERED_CLOSE) return WWSounds.BLOCK_COPPER_CHEST_WEATHERED_CLOSE_UNDERWATER;
		}
		return soundEvent;
	}

	@Unique
	@Override
	public void wilderWild$bubble(Level level, BlockPos pos, BlockState state) {
		if (level == null) return;
		if (this.wilderWild$canBubble && state.getOptionalValue(BlockStateProperties.WATERLOGGED).orElse(false)) {
			wilderWild$sendBubbleSeedParticle(level, pos);
			this.wilderWild$canBubble = false;
			Optional<ChestBlockEntity> possibleCoupledChest = ChestUtil.getCoupledChestBlockEntity(level, pos, state);
			possibleCoupledChest.ifPresent(coupledChest -> {
				wilderWild$sendBubbleSeedParticle(level, coupledChest.getBlockPos());
				if (coupledChest instanceof ChestBlockEntityInterface coupledChestInterface) coupledChestInterface.wilderWild$setCanBubble(false);
			});
		}
	}

	@Unique
	private static void wilderWild$sendBubbleSeedParticle(Level level, BlockPos pos) {
		if (!(level instanceof ServerLevel serverLevel)) return;
		Vec3 centerPos = Vec3.atCenterOf(pos);
		serverLevel.sendParticles(
			WWParticleTypes.CHEST_BUBBLE_SPAWNER,
			centerPos.x(),
			centerPos.y(),
			centerPos.z(),
			1,
			0D,
			0D,
			0D,
			0D
		);
	}

	@Unique
	@Override
	public void wilderWild$bubbleBurst(BlockState state) {
		ChestBlockEntity chest = ChestBlockEntity.class.cast(this);
		if (chest.getLevel() instanceof ServerLevel server && WWBlockConfig.get().chestBubbling) {
			BlockPos pos = chest.getBlockPos();
			if (state.getFluidState().is(Fluids.WATER) && this.wilderWild$getCanBubble()) {
				server.sendParticles(
					ParticleTypes.BUBBLE,
					pos.getX() + 0.5D,
					pos.getY() + 0.625D,
					pos.getZ() + 0.5D,
					server.random.nextInt(18, 25),
					0.21875D,
					0D,
					0.21875D,
					0.25D
				);
			}
		}
	}

	@Inject(method = "loadAdditional", at = @At("TAIL"))
	public void wilderWild$load(ValueInput valueInput, CallbackInfo info) {
		this.wilderWild$canBubble = valueInput.getBooleanOr("wilderwild_can_bubble", true);
	}

	@Inject(method = "saveAdditional", at = @At("TAIL"))
	public void wilderWild$saveAdditional(ValueOutput valueOutput, CallbackInfo info) {
		valueOutput.putBoolean("wilderwild_can_bubble", this.wilderWild$canBubble);
	}

	@Unique
	@Override
	public boolean wilderWild$getCanBubble() {
		return this.wilderWild$canBubble;
	}

	@Unique
	@Override
	public void wilderWild$setCanBubble(boolean b) {
		this.wilderWild$canBubble = b;
	}

	@Unique
	@Override
	public void wilderWild$syncBubble(ChestBlockEntity chest1, ChestBlockEntity chest2) {
		if (chest1 instanceof ChestBlockEntityInterface chest1Interface && chest2 instanceof ChestBlockEntityInterface chest2Interface) {
			if (!chest1Interface.wilderWild$getCanBubble() || !chest2Interface.wilderWild$getCanBubble()) {
				chest1Interface.wilderWild$setCanBubble(false);
				chest2Interface.wilderWild$setCanBubble(false);
			}
		}
	}

}
