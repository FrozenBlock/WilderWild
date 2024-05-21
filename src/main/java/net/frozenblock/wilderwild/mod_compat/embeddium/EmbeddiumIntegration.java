/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.mod_compat.embeddium;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import org.embeddedt.embeddium.api.ChunkMeshEvent;
import org.jetbrains.annotations.NotNull;

public class EmbeddiumIntegration extends AbstractEmbeddiumIntegration {
	public EmbeddiumIntegration() {
		super();
	}

	@Environment(EnvType.CLIENT)
	public void clientInit() {
		ChunkMeshEvent.BUS.addListener(EmbeddiumIntegration::onChunkMesh);
	}

	// Credit to embeddedt! https://github.com/embeddedt/embeddium/issues/284#issuecomment-2096107916
	@Environment(EnvType.CLIENT)
	private static void onChunkMesh(@NotNull ChunkMeshEvent event) {
		LevelChunk chunk = event.getWorld().getChunk(event.getSectionOrigin().x(), event.getSectionOrigin().z());
		LevelChunkSection section = chunk.getSection(chunk.getSectionIndexFromSectionY(event.getSectionOrigin().y()));
		// Never schedule a mesh appender for empty sections (unless you need to render geometry in them).
		// Embeddium normally greatly speeds up initial world loads by not scheduling a rebuild task for an empty
		// section (since there is nothing to build), but the task is still created if you register a mesh appender
		// because mods may wish to render custom geometry in otherwise empty sections.
		// Since we only want to render a snowlogged effect on actual blocks, we don't need to bother with empty
		// sections.
		if(section != null && !section.hasOnlyAir()) {
			event.addMeshAppender(ctx -> {
				// This part of the code runs on the meshing thread, not the main thread
				BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
				var vertexConsumer = ctx.vertexConsumerProvider().apply(RenderType.solid());
				PoseStack stack = new PoseStack();
				RandomSource random = RandomSource.create();
				// Even though we have a snapshot of the world, we still use the same coordinates
				int baseX = ctx.sectionOrigin().minBlockX();
				int baseY = ctx.sectionOrigin().minBlockY();
				int baseZ = ctx.sectionOrigin().minBlockZ();
				for (int y = 0; y < 16; y++) {
					for (int z = 0; z < 16; z++) {
						for (int x = 0; x < 16; x++) {
							cursor.set(baseX + x, baseY + y, baseZ + z);
							// Access blocks through the context's getter, not the section itself, as the latter
							// is not safe to use on a meshing thread
							BlockState currentState = ctx.blockRenderView().getBlockState(cursor);
							if (SnowloggingUtils.isSnowlogged(currentState)) {
								BlockState snowState = SnowloggingUtils.getSnowEquivalent(currentState);
								stack.pushPose();
								stack.translate(x, y, z);

								// Fix Z-Fighting on blocks like Pink Petals
								if (snowState.getValue(BlockStateProperties.LAYERS) < 8) {
									float scaleFactor = 0.9999F;
									float translateFactor = (1F - scaleFactor) / 2F;
									stack.translate(0F, -translateFactor, 0F);
									stack.scale(1F, scaleFactor, 1F);
								}

								// Render the snow blockstate at the given position
								Minecraft.getInstance().getBlockRenderer().renderBatched(snowState, cursor, ctx.blockRenderView(), stack, vertexConsumer, true, random);

								// Reset the PoseStack for the next use
								stack.popPose();
							}
						}
					}
				}
			});
		}
	}
}
