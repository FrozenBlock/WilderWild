/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.sculk;

import net.frozenblock.wilderwild.block.entity.impl.SculkShriekerTickInterface;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.particle.options.FloatingSculkBubbleParticleOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkShriekerBlockEntity.class)
public abstract class SculkShriekerBlockEntityMixin implements SculkShriekerTickInterface {

	@Unique
	public int wilderWild$bubbles;

	@Shadow
	public abstract VibrationSystem.Data getVibrationData();

	@Shadow
	public abstract VibrationSystem.User getVibrationUser();

	@Inject(at = @At("HEAD"), method = "tryShriek")
	public void wilderWild$shriek(ServerLevel level, @Nullable ServerPlayer player, CallbackInfo info) {
		SculkShriekerBlockEntity shrieker = SculkShriekerBlockEntity.class.cast(this);
		if (WWBlockConfig.get().shriekerGargling && shrieker.getBlockState().getValue(BlockStateProperties.WATERLOGGED)) {
			this.wilderWild$bubbles = 50;
		}
	}

	@Inject(at = @At("TAIL"), method = "loadAdditional")
	public void wilderWild$load(CompoundTag tag, HolderLookup.Provider provider, CallbackInfo info) {
		this.wilderWild$bubbles = tag.getInt("wilderwildBubbles");
	}

	@Inject(at = @At("TAIL"), method = "saveAdditional")
	public void wilderWild$saveAdditional(CompoundTag tag, HolderLookup.Provider provider, CallbackInfo info) {
		tag.putInt("wilderwildBubbles", this.wilderWild$bubbles);
	}

	@Unique
	@Override
	public void wilderWild$tickServer(Level level, BlockPos pos) {
		if (level instanceof ServerLevel serverLevel) {
			VibrationSystem.Ticker.tick(level, this.getVibrationData(), this.getVibrationUser());
			if (this.wilderWild$bubbles > 0) {
				--this.wilderWild$bubbles;
				RandomSource random = level.getRandom();
				serverLevel.sendParticles(
					new FloatingSculkBubbleParticleOptions(
						random.nextDouble() > 0.7 ? 1 : 0,
						20 + random.nextInt(80),
						new Vec3(
							FloatingSculkBubbleParticleOptions.getRandomVelocity(random, 0),
							0.075F,
							FloatingSculkBubbleParticleOptions.getRandomVelocity(random, 0)
						)
					),
					pos.getX() + 0.5D,
					pos.getY() + 0.5D,
					pos.getZ() + 0.5D,
					1,
					0D,
					0D,
					0D,
					0D
				);
			}
		}
	}
}
