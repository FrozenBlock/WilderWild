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

package net.frozenblock.wilderwild.mixin.block.leaves;

import net.frozenblock.wilderwild.block.impl.FallingLeafUtil;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.function.BiConsumer;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {

	@Shadow
	protected abstract BlockState asState();

	@Inject(method = "entityInside", at = @At("HEAD"))
	public void wilderWild$entityInsideLeafLitter(Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier insideBlockEffectApplier, CallbackInfo info) {
		BlockState state = this.asState();
		if (!state.is(WWBlockTags.LEAF_LITTERS)) return;
		FallingLeafUtil.trySpawnWalkParticles(state, level, pos, entity, true);
	}

	@Inject(method = "onExplosionHit", at = @At("HEAD"))
	public void wilderWild$createLeafParticlesOnExplosionHit(
		ServerLevel level, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> biConsumer, CallbackInfo info
	) {
		FallingLeafUtil.trySpawnExplosionParticles(this.asState(), level, pos, explosion);
	}

}
