/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.entity.ChestBubbleTicker;
import net.frozenblock.wilderwild.misc.interfaces.ChestBlockEntityInterface;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ChestBlockEntity.class)
public class ChestBlockEntityMixin implements ChestBlockEntityInterface {

	@Unique boolean wilderWild$canBubble = true;

	@Unique
	private static BlockState wilderWild$playedSoundState;

	@Inject(at = @At("HEAD"), method = "playSound")
	private static void wilderWild$playSound(Level level, BlockPos pos, BlockState state, SoundEvent sound, CallbackInfo info) {
		wilderWild$playedSoundState = level.getBlockState(pos);
	}

	@ModifyArgs(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"), method = "playSound")
	private static void wilderWild$playSound(Args args) {
		if (wilderWild$playedSoundState != null && wilderWild$playedSoundState.hasProperty(BlockStateProperties.WATERLOGGED) && wilderWild$playedSoundState.getValue(BlockStateProperties.WATERLOGGED)) {
			SoundEvent sound = args.get(4);
			if (sound == SoundEvents.CHEST_OPEN) {
				args.set(4, RegisterSounds.BLOCK_CHEST_OPEN_UNDERWATER);
			} else if (sound == SoundEvents.CHEST_CLOSE) {
				args.set(4, RegisterSounds.BLOCK_CHEST_CLOSE_UNDERWATER);
			}
		}
	}

	@Unique
	@Override
	public void wilderWild$bubble(Level level, BlockPos pos, BlockState state) {
		if (level != null) {
			if (this.wilderWild$canBubble && state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED)) {
				ChestBubbleTicker.createAndSpawn(RegisterEntities.CHEST_BUBBLER, level, pos);
				this.wilderWild$canBubble = false;
				ChestBlockEntity otherChest = wilderWild$getOtherEntity(level, pos, state);
				if (otherChest != null) {
					ChestBubbleTicker.createAndSpawn(RegisterEntities.CHEST_BUBBLER, level, otherChest.getBlockPos());
					((ChestBlockEntityInterface) otherChest).wilderWild$setCanBubble(false);
				}
			}
		}
	}

	@Unique
	@Override
	public void wilderWild$bubbleBurst(BlockState state) {
		ChestBlockEntity chest = ChestBlockEntity.class.cast(this);
		Level level = chest.getLevel();
		if (level instanceof ServerLevel server) {
			BlockPos pos = chest.getBlockPos();
			if (state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) && this.wilderWild$getCanBubble()) {
				server.sendParticles(ParticleTypes.BUBBLE, pos.getX() + 0.5, pos.getY() + 0.625, pos.getZ() + 0.5, server.random.nextInt(18, 25), 0.21875F, 0, 0.21875F, 0.25D);
			}
		}
	}

	@Inject(at = @At(value = "TAIL"), method = "load")
	public void load(CompoundTag tag, CallbackInfo info) {
		if (tag.contains("wilderwild_can_bubble")) {
			this.wilderWild$canBubble = tag.getBoolean("wilderwild_can_bubble");
		}
	}

	@Inject(at = @At(value = "TAIL"), method = "saveAdditional")
	public void saveAdditional(CompoundTag tag, CallbackInfo info) {
		tag.putBoolean("wilderwild_can_bubble", this.wilderWild$canBubble);
	}

	@Unique
	private static ChestBlockEntity wilderWild$getOtherEntity(Level level, BlockPos pos, BlockState state) {
		ChestType chestType = state.getValue(ChestBlock.TYPE);
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();
		if (chestType == ChestType.RIGHT) {
			Direction direction = ChestBlock.getConnectedDirection(state);
			x += direction.getStepX();
			z += direction.getStepZ();
		} else if (chestType == ChestType.LEFT) {
			Direction direction = ChestBlock.getConnectedDirection(state);
			x += direction.getStepX();
			z += direction.getStepZ();
		} else {
			return null;
		}
		BlockPos newPos = new BlockPos(x, y, z);
		BlockEntity be = level.getBlockEntity(newPos);
		ChestBlockEntity entity = null;
		if (be instanceof ChestBlockEntity chest) {
			entity = chest;
		}
		return entity;
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
		if (!((ChestBlockEntityInterface) chest1).wilderWild$getCanBubble() || !((ChestBlockEntityInterface) chest2).wilderWild$getCanBubble()) {
			((ChestBlockEntityInterface) chest1).wilderWild$setCanBubble(false);
			((ChestBlockEntityInterface) chest2).wilderWild$setCanBubble(false);
		}
	}

}
