/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.block.ice;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWDamageTypes;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpeleothemBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SpeleothemBlock.class)
public class SpeleothemBlockMixin {
	@Unique
	private static final float WILDERWILD$MAX_ICICLE_GROWTH_FREQUENCY = 0.165F;

	@ModifyExpressionValue(
		method = "randomTick",
		at = @At(
			value = "CONSTANT",
			args = "floatValue=0.011377778F"
		)
	)
	private static float wilderWild$fallingIciclesDontDropItems(float original) {
		final float growthFrequencyEntry = Math.clamp(WWBlockConfig.ICICLE_GROWTH_FREQUENCY.get() / 100F, 0F, 1F);
		return Mth.lerp(growthFrequencyEntry, original, WILDERWILD$MAX_ICICLE_GROWTH_FREQUENCY);
	}

	@WrapOperation(
		method = "canGrow",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z"
		)
	)
	private static boolean wilderWild$allowGrowthOnOtherBlocks(BlockState instance, Object o, Operation<Boolean> original) {
		return original.call(instance, o) || instance.is(WWBlockTags.ICICLE_CAN_GROW_UNDER);
	}

	@ModifyExpressionValue(
		method = "spawnFallingStalactite",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;fall(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/entity/item/FallingBlockEntity;"
		)
	)
	private static FallingBlockEntity wilderWild$fallingIciclesDontDropItems(FallingBlockEntity original) {
		if (original.getBlockState().is(Blocks.ICICLE) && !WWBlockConfig.FALLING_ICICLE_DROPS_ITEM.get()) original.disableDrop();
		return original;
	}

	@WrapOperation(
		method = "spawnFallingStalactite",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;setHurtsEntities(FI)V"
		)
	)
	private static void wilderWild$fallingIciclesAreWeaker(FallingBlockEntity instance, float damagePerDistance, int damageMax, Operation<Void> original) {
		if (instance.getBlockState().is(Blocks.ICICLE) && WWBlockConfig.FALLING_ICICLE_HAS_WEAKER_DAMAGE.get()) damageMax = 10;
		original.call(instance, damagePerDistance, damageMax);
	}

	@ModifyExpressionValue(
		method = "fallOnDamage",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/damagesource/DamageSources;stalagmite()Lnet/minecraft/world/damagesource/DamageSource;"
		)
	)
	private static DamageSource wilderWild$useIcicleDamageType(
		DamageSource original,
		Level level, BlockState state
	) {
		if (state.is(Blocks.ICICLE) && WWBlockConfig.FALLING_ICICLE_DAMAGE_TYPE.get()) return level.damageSources().source(WWDamageTypes.FALLING_ICICLE);
		return original;
	}
}
