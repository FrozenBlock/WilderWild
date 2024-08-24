package net.frozenblock.wilderwild.mixin.snowlogging.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
	@Shadow
	@Final
	private Minecraft minecraft;

	@Shadow
	private @Nullable ClientLevel level;

	@WrapOperation(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/BlockRenderDispatcher;renderBreakingTexture(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockAndTintGetter;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;)V"))
	void wilderWild$renderBreakingTexture(BlockRenderDispatcher instance, BlockState state, BlockPos pos, BlockAndTintGetter world, PoseStack matrix, VertexConsumer vertexConsumer, Operation<Void> original) {
		if (SnowloggingUtils.isSnowlogged(state)) {
			LocalPlayer player = minecraft.player;
			assert player != null;
			HitResult hitResult = player.pick(player.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE), 0, false);
			Vec3 hitLocation = hitResult.getLocation();
			BlockPos hitPos = new BlockPos(Mth.floor(hitLocation.x()), Mth.floor(hitLocation.y()), Mth.floor(hitLocation.z()));
			if (hitPos.equals(pos)) {
				if (SnowloggingUtils.shouldHitSnow(state, pos, (Level) world, hitLocation)) {
					state = SnowloggingUtils.getSnowEquivalent(state);
				} else {
					state = SnowloggingUtils.getStateWithoutSnow(state);
				}
			}
		}
		original.call(instance, state, pos, world, matrix, vertexConsumer);
//		level.players().get(0).getId()
	}

}
