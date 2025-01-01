/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class TermiteParticle extends TextureSheetParticle {
	private final TermiteRotationType xRot;
	private final TermiteRotationType yRot;
	private final TermiteRotationType zRot;
	private final boolean backwardsX;
	private final boolean backwardsY;
	private final boolean backwardsZ;
	private final float xOffset;
	private final float yOffset;
	private final float zOffset;
	private final float xSpinSpeed;
	private final float ySpinSpeed;
	private final float zSpinSpeed;
	private float prevScale = 0F;
	private float scale = 0F;
	private float targetScale = 0F;

	public TermiteParticle(@NotNull ClientLevel clientLevel, @NotNull SpriteSet spriteProvider, double x, double y, double z) {
		super(clientLevel, x, y, z);
		this.pickSprite(spriteProvider);
		this.hasPhysics = false;
		this.x = x;
		this.y = y;
		this.z = z;
		RandomSource random = clientLevel.getRandom();
		this.xRot = random.nextBoolean() ? TermiteRotationType.COS : TermiteRotationType.SIN;
		this.yRot = random.nextBoolean() ? TermiteRotationType.COS : TermiteRotationType.SIN;
		this.zRot = random.nextBoolean() ? TermiteRotationType.COS : TermiteRotationType.SIN;
		this.backwardsX = random.nextBoolean();
		this.backwardsY = random.nextBoolean();
		this.backwardsZ = random.nextBoolean();
		this.xOffset = random.nextFloat() * 240F;
		this.yOffset = random.nextFloat() * 240F;
		this.zOffset = random.nextFloat() * 240F;
		this.xSpinSpeed = 8F + (random.nextFloat() * 8F);
		this.ySpinSpeed = 8F + (random.nextFloat() * 8F);
		this.zSpinSpeed = 8F + (random.nextFloat() * 8F);
	}

	@Override
	@NotNull
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public void tick() {
		this.prevScale = this.scale;
		this.scale += (this.targetScale - this.scale) * 0.65F;
		if (this.age++ >= this.lifetime) {
			if (this.prevScale <= 0.05F) {
				this.remove();
			} else {
				this.targetScale = 0F;
			}
		} else {
			this.targetScale = 1F;
		}
	}

	private float cos(float progress, float offset, float spinSpeed) {
		return (float) Math.cos(((progress + offset) * Math.PI) / spinSpeed);
	}

	private float sin(float progress, float offset, float spinSpeed) {
		return (float) Math.sin(((progress + offset) * Math.PI) / spinSpeed);
	}

	private float rotate(@NotNull TermiteRotationType rotation, float progress, float offset, float spinSpeed) {
		return rotation == TermiteRotationType.COS ? cos(progress, offset, spinSpeed) : sin(progress, offset, spinSpeed);
	}

	@Override
	protected void renderRotatedQuad(VertexConsumer vertexConsumer, @NotNull Camera camera, Quaternionf quaternionf, float partialTick) {
		Vec3 vec3 = camera.getPosition();
		float animationProgress = this.age + partialTick;
		float xRotation = (rotate(this.xRot, animationProgress, this.xOffset, this.xSpinSpeed)) * (this.backwardsX ? -1 : 1) * 0.65F;
		float yRotation = (rotate(this.yRot, animationProgress, this.yOffset, this.ySpinSpeed)) * (this.backwardsY ? -1 : 1) * 0.65F;
		float zRotation = (rotate(this.zRot, animationProgress, this.zOffset, this.zSpinSpeed)) * (this.backwardsZ ? -1 : 1) * 0.65F;
		float g = (float) (x - vec3.x() + xRotation);
		float h = (float) (y - vec3.y() + yRotation);
		float i = (float) (z - vec3.z() + zRotation);
		this.renderRotatedQuad(vertexConsumer, quaternionf, g, h, i, partialTick);
	}

	@Override
	protected int getLightColor(float partialTick) {
		float animationProgress = this.age + partialTick;
		float xRotation = (rotate(this.xRot, animationProgress, this.xOffset, this.xSpinSpeed)) * (this.backwardsX ? -1 : 1) * 0.65F;
		float yRotation = (rotate(this.yRot, animationProgress, this.yOffset, this.ySpinSpeed)) * (this.backwardsY ? -1 : 1) * 0.65F;
		float zRotation = (rotate(this.zRot, animationProgress, this.zOffset, this.zSpinSpeed)) * (this.backwardsZ ? -1 : 1) * 0.65F;
		BlockPos blockPos = this.getLerpedTermiteBlockPos(x, y, z, xRotation, yRotation, zRotation);
		return this.level.hasChunkAt(blockPos) ? LevelRenderer.getLightColor(this.level, blockPos) : 0;
	}

	@NotNull
	private BlockPos getLerpedTermiteBlockPos(double x, double y, double z, float cos, float sin, float aCos) {
		return BlockPos.containing(
			x + cos,
			y + sin,
			z + aCos
		);
	}

	@Override
	public float getQuadSize(float partialTicks) {
		return this.quadSize * Mth.lerp(partialTicks, this.prevScale, this.scale);
	}

	enum TermiteRotationType {
		COS,
		SIN
	}

	@Environment(EnvType.CLIENT)
	public record Factory(@NotNull SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
		@Override
		@NotNull
		public Particle createParticle(@NotNull SimpleParticleType termiteParticleOptions, @NotNull ClientLevel clientLevel, double x, double y, double z, double g, double h, double i) {
			TermiteParticle termite = new TermiteParticle(clientLevel, spriteProvider, x, y, z);
			termite.setAlpha(1.0F);
			termite.setLifetime(clientLevel.getRandom().nextInt(10) + 5 + termite.age);
			termite.scale(0.75F);
			return termite;
		}
	}
}
