package net.frozenblock.wilderwild.client.renderer.chunk;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.client.renderer.block.BlockQuadOutput;
import net.minecraft.client.renderer.block.BlockStateModelSet;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.chunk.RenderSectionRegion;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.block.state.BlockState;

@Environment(EnvType.CLIENT)
public class SnowloggedSectionCompiler {

	public static void tesselateSnowloggedLayer(
		RenderSectionRegion region,
		ModelBlockRenderer blockRenderer,
		BlockQuadOutput quadOutput,
		BlockQuadOutput opaqueQuadOutput,
		BlockPos pos,
		BlockState blockState,
		boolean cutoutLeaves,
		BlockStateModelSet blockModelSet
	) {
		if (!SnowloggingUtils.isSnowlogged(blockState)) return;
		final BlockState snowState = SnowloggingUtils.getSnowEquivalent(blockState);

		blockRenderer.tesselateBlock(
			ModelBlockRenderer.forceOpaque(cutoutLeaves, snowState) ? opaqueQuadOutput : quadOutput,
			(float)SectionPos.sectionRelative(pos.getX()),
			(float)SectionPos.sectionRelative(pos.getY()),
			(float)SectionPos.sectionRelative(pos.getZ()),
			region,
			pos,
			snowState,
			blockModelSet.get(snowState),
			snowState.getSeed(pos)
		);
	}
}
