package net.frozenblock.wilderwild;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.renderer.special.StoneChestSpecialRenderer;
import net.frozenblock.wilderwild.config.gui.WWMainConfigGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.BlockQuadOutput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.AddSectionGeometryEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = WWPreLoadConstants.MOD_ID, dist = Dist.CLIENT)
public final class WilderWildNeoForgeClient {

	public WilderWildNeoForgeClient(IEventBus modBus) {
		WilderWildClient.init();

		// AFTER register event
		modBus.addListener(FMLClientSetupEvent.class, event -> {
			WWModelLayers.setup();
		});

		// TODO: multiloader
		modBus.addListener(RegisterSpecialModelRendererEvent.class, event -> {
			event.register(WWConstants.id("stone_chest"), StoneChestSpecialRenderer.Unbaked.MAP_CODEC);
		});

		// TODO: see if removing cloth causes a crash
		ModLoadingContext.get().registerExtensionPoint(
			IConfigScreenFactory.class,
			() -> (container, parent) ->
				WWMainConfigGui.buildScreen(parent)
		);

		// This seems to work only while Sodium's installed, despite being a native NeoForge event.
		// Nonetheless, this fixes Snowlogging with Sodium. Mixins on Sodium (like what we do on Fabric) don't seem to work.
		NeoForge.EVENT_BUS.addListener(AddSectionGeometryEvent.class, event -> {
			event.addRenderer(context -> {
				final BlockAndTintGetter region = context.getRegion();
				final BlockPos origin = event.getSectionOrigin();
				final BlockPos maxPos = origin.offset(15, 15, 15);

				final BlockQuadOutput quadOutput = (x, y, z, quad, instance) -> {
					final VertexConsumer builder = context.getOrCreateChunkBuffer(quad.materialInfo().layer());
					builder.putBlockBakedQuad(x, y, z, quad, instance);
				};

				for (BlockPos pos : BlockPos.betweenClosed(origin, maxPos)) {
					final BlockState blockState = region.getBlockState(pos);
					if (blockState.isAir()) continue;

					if (!SnowloggingUtils.isSnowlogged(blockState)) continue;

					final BlockState snowEquivalent = SnowloggingUtils.getSnowEquivalent(blockState);
					if (snowEquivalent.getRenderShape() != RenderShape.MODEL) continue;

					context.getBlockRenderer().tesselateBlock(
						quadOutput,
						SectionPos.sectionRelative(pos.getX()),
						SectionPos.sectionRelative(pos.getY()),
						SectionPos.sectionRelative(pos.getZ()),
						region,
						pos,
						snowEquivalent,
						Minecraft.getInstance().getModelManager().getBlockStateModelSet().get(snowEquivalent),
						snowEquivalent.getSeed(pos)
					);
				}
			});
		});
	}
}
