package net.frozenblock.wilderwild.client.renderer.chunk;

import net.frozenblock.wilderwild.block.snowlogging.SnowloggingUtil;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.renderer.block.BlockQuadOutput;
import net.minecraft.client.renderer.block.BlockStateModelSet;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.chunk.RenderSectionRegion;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.block.state.BlockState;

@ClientOnly
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
		if (!SnowloggingUtil.isSnowlogged(blockState)) return;
		final BlockState snowState = SnowloggingUtil.getSnowEquivalent(blockState);

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
