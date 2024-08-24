package net.frozenblock.wilderwild.mixin.snowlogging.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.SortedSet;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.entity.impl.BlockDestructionProgressInterface;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.BlockDestructionProgress;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

	@Shadow
	private @Nullable ClientLevel level;

	@WrapOperation(method = "destroyBlockProgress", at = @At(value = "NEW", target = "(ILnet/minecraft/core/BlockPos;)Lnet/minecraft/server/level/BlockDestructionProgress;"))
	BlockDestructionProgress wilderWild$destroyBlockProgress(int entityId, BlockPos pos, Operation<BlockDestructionProgress> original) {
		BlockDestructionProgressInterface originalInterface = (BlockDestructionProgressInterface) original.call(entityId, pos);
		BlockState state = level.getBlockState(pos);
		if (SnowloggingUtils.isSnowlogged(state)) {
			Player player = null;
			for (AbstractClientPlayer p : level.players()) {
				if (p.getId() == entityId) {
					player = p;
					break;
				}
			}
			if (player != null) {
				boolean breakingOriginal = !SnowloggingUtils.shouldHitSnow(state, pos, level, player);
				originalInterface.wilderWild$breakingOriginal(breakingOriginal);
			}
		}
		return (BlockDestructionProgress) originalInterface;
	}

	@WrapOperation(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/BlockRenderDispatcher;renderBreakingTexture(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockAndTintGetter;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;)V"))
	void wilderWild$renderLevel(BlockRenderDispatcher instance, BlockState state, BlockPos pos, BlockAndTintGetter world, PoseStack matrix, VertexConsumer vertexConsumer, Operation<Void> original,
								@Local SortedSet<BlockDestructionProgress> sortedSet2) {
		if (SnowloggingUtils.isSnowlogged(state)) {
			BlockDestructionProgressInterface blockDestructionProgress = (BlockDestructionProgressInterface) sortedSet2.last();
			if (blockDestructionProgress.wilderWild$isBreakingOriginal()) {
				state = SnowloggingUtils.getStateWithoutSnow(state);
			} else {
				state = SnowloggingUtils.getSnowEquivalent(state);
			}
		}
		original.call(instance, state, pos, world, matrix, vertexConsumer);
	}
}
