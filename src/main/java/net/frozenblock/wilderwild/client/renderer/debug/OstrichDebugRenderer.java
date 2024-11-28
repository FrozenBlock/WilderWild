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

package net.frozenblock.wilderwild.client.renderer.debug;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Collections;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.debug.client.impl.DebugRenderManager;
import net.frozenblock.wilderwild.entity.Ostrich;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShapeRenderer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class OstrichDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
	private static final int NECK_LINE_COLOR = ARGB.color(255, 100, 255, 255);
	private final Minecraft minecraft;
	private List<Entity> surroundEntities = Collections.emptyList();

	public OstrichDebugRenderer(Minecraft client) {
		this.minecraft = client;
	}

	public void tick() {
		this.surroundEntities = ImmutableList.copyOf(
			this.minecraft.level.entitiesForRendering()
		);
	}

	@Override
	public void clear() {
		this.surroundEntities = Collections.emptyList();
	}

	@Override
	public void render(PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, double cameraX, double cameraY, double cameraZ) {
		for (Entity entity2 : this.surroundEntities) {
			if (entity2 instanceof Ostrich ostrich) {
				try {
					AABB attackBox = ostrich.createAttackBox(DebugRenderManager.PARTIAL_TICK).move(-cameraX, -cameraY, -cameraZ);
					ShapeRenderer.renderLineBox(
						matrices,
						vertexConsumers.getBuffer(RenderType.lines()),
						attackBox,
						1F, 0F, 0F, 1F
					);

					List<Vec3> debugPoses = ostrich.getDebugRenderingPoses(DebugRenderManager.PARTIAL_TICK);
					for (int i = 1; i < debugPoses.size(); i++) {
						Vec3 previous = debugPoses.get(i - 1);
						Vec3 target = debugPoses.get(i);
						drawLine(
							matrices,
							vertexConsumers,
							cameraX, cameraY, cameraZ,
							previous, target,
							NECK_LINE_COLOR
						);
					}
					debugPoses.forEach(
						vec3 -> renderFilledBox(
							matrices,
							vertexConsumers,
							AABB.ofSize(vec3, 0.1D, 0.1D, 0.1D),
							cameraX, cameraY, cameraZ
						)
					);
				} catch (Exception ignored) {}
			}
		}
	}

	private static void renderFilledBox(
		PoseStack matrices,
		MultiBufferSource vertexConsumers,
		@NotNull AABB box,
		double cameraX, double cameraY, double cameraZ
	) {
		Vec3 vec3 = new Vec3(-cameraX, -cameraY, -cameraZ);
		DebugRenderer.renderFilledBox(matrices, vertexConsumers, box.move(vec3), 1F, 1F, 1F, 1F);
	}

	private static void drawLine(
		@NotNull PoseStack matrices,
		@NotNull MultiBufferSource vertexConsumers,
		double cameraX,
		double cameraY,
		double cameraZ,
		@NotNull Vec3 start,
		@NotNull Vec3 target,
		int color
	) {
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderType.debugLineStrip(4D));
		vertexConsumer.addVertex(matrices.last(), (float)(start.x - cameraX), (float)(start.y - cameraY), (float)(start.z - cameraZ)).setColor(color);
		vertexConsumer.addVertex(matrices.last(), (float)(target.x - cameraX), (float)(target.y - cameraY), (float)(target.z - cameraZ)).setColor(color);
	}
}
