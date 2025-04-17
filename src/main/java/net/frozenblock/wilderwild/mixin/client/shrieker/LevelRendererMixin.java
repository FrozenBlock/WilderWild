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

package net.frozenblock.wilderwild.mixin.client.shrieker;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.SculkShriekerBlock;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

	@Shadow
	@Nullable
	private ClientLevel level;

	@ModifyExpressionValue(
		method = "levelEvent",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;",
			ordinal = 0
		),
		require = 0
	)
	private Comparable<Boolean> wilderWild$shriekerGargle(Comparable<Boolean> original, int eventId, BlockPos pos, int data) {
		if (this.level != null && WWBlockConfig.get().sculk.shriekerGargling) {
			if (original.compareTo(true) == 0 || this.level.getFluidState(pos.above()).is(FluidTags.WATER)) {
				double x = (double) pos.getX() + 0.5D;
				double y = (double) pos.getY() + SculkShriekerBlock.TOP_Y;
				double z = (double) pos.getZ() + 0.5D;

				this.level.playLocalSound(
					x,
					y,
					z,
					WWSounds.BLOCK_SCULK_SHRIEKER_GARGLE,
					SoundSource.BLOCKS,
					2.0F,
					0.6F + this.level.random.nextFloat() * 0.4F,
					false
				);

				this.level.addParticle(
					WWParticleTypes.SHRIEKER_BUBBLE_SPAWNER,
					false,
					x,
					y,
					z,
					0D,
					0D,
					0D
				);
			}
		}
		return original;
	}

}
