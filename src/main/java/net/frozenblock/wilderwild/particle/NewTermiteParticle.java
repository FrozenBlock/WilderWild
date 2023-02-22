/*
 * Copyright 2022-2023 FrozenBlock
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
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class NewTermiteParticle extends TextureSheetParticle {
    private final SpriteSet spriteProvider;
	private final float spinSpeed;
	private float prevScale = 0F;
	private float scale = 0F;
	private float targetScale = 0F;
	private final Rotation xRot;
	private final Rotation yRot;
	private final Rotation zRot;
	private final boolean backwardsX;
	private final boolean backwardsY;
	private final boolean backwardsZ;

    public NewTermiteParticle(ClientLevel level, double x, double y, double z, Rotation xRot, Rotation yRot, Rotation zRot, boolean backwardsX, boolean backwardsY, boolean backwardsZ, float spinSpeed, SpriteSet spriteProvider) {
        super(level, x, y, z);
        this.spriteProvider = spriteProvider;
        this.hasPhysics = false;
        this.setSpriteFromAge(spriteProvider);
		this.x = x;
		this.y = y;
		this.z = z;
		this.xRot = xRot;
		this.yRot = yRot;
		this.zRot = zRot;
		this.backwardsX = backwardsX;
		this.backwardsY = backwardsY;
		this.backwardsZ = backwardsZ;
		this.spinSpeed = spinSpeed;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
		this.prevScale = this.scale;
		this.scale += (this.targetScale - this.scale) * 0.25F;
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

	@Override
	public void render(@NotNull VertexConsumer buffer, @NotNull Camera renderInfo, float partialTicks) {
		float animationProgress = this.age + partialTicks;
		float cos = (float) Math.cos((animationProgress * Math.PI) / this.spinSpeed);
		float sin = (float) Math.sin((animationProgress * Math.PI) / this.spinSpeed);

		double x = this.x;
		double y = this.y;
		double z = this.z;

		float xRotation = (this.xRot == Rotation.COS ? cos : sin) * (this.backwardsX ? -1 : 1);
		float yRotation = (this.yRot == Rotation.COS ? cos : sin) * (this.backwardsY ? -1 : 1);
		float zRotation = (this.zRot == Rotation.COS ? cos : sin) * (this.backwardsZ ? -1 : 1);

		Quaternion quaternion;
		Vec3 vec3 = renderInfo.getPosition();
		float f = (float)(x - vec3.x() + xRotation);
		float g = (float)(y - vec3.y() + yRotation);
		float h = (float)(z - vec3.z() + zRotation);
		if (this.roll == 0.0f) {
			quaternion = renderInfo.rotation();
		} else {
			quaternion = new Quaternion(renderInfo.rotation());
			float i = Mth.lerp(partialTicks, this.oRoll, this.roll);
			quaternion.mul(Vector3f.ZP.rotation(i));
		}
		Vector3f[] vector3fs = new Vector3f[]{new Vector3f(-1.0f, -1.0f, 0.0f), new Vector3f(-1.0f, 1.0f, 0.0f), new Vector3f(1.0f, 1.0f, 0.0f), new Vector3f(1.0f, -1.0f, 0.0f)};
		float j = this.getQuadSize(partialTicks);
		for (int k = 0; k < 4; ++k) {
			Vector3f vector3f2 = vector3fs[k];
			vector3f2.transform(quaternion);
			vector3f2.mul(j);
			vector3f2.add(f, g, h);
		}
		float l = this.getU0();
		float m = this.getU1();
		float n = this.getV0();
		float o = this.getV1();
		int p = this.getLightColor(x, y, z, xRotation, yRotation, zRotation);
		buffer.vertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).uv(m, o).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(p).endVertex();
		buffer.vertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).uv(m, n).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(p).endVertex();
		buffer.vertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).uv(l, n).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(p).endVertex();
		buffer.vertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).uv(l, o).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(p).endVertex();
	}

	private int getLightColor(double x, double y, double z, float cos, float sin, float aCos) {
		BlockPos blockPos = this.getLerpedTermiteBlockPos(x, y, z, cos, sin, aCos);
		if (this.level.hasChunkAt(blockPos)) {
			return LevelRenderer.getLightColor(this.level, blockPos);
		}
		return 0;
	}

	private BlockPos getLerpedTermiteBlockPos(double x, double y, double z, float cos, float sin, float aCos) {
		return new BlockPos(
				x + cos,
				y + sin,
				z + aCos
		);
	}

	@Override
	public float getQuadSize(float partialTicks) {
		return this.quadSize * Mth.lerp(partialTicks, this.prevScale, this.scale);
	}

	enum Rotation {
		COS,
		SIN
	}

	@Environment(EnvType.CLIENT)
    public record Factory(SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(@NotNull SimpleParticleType termiteParticleOptions, @NotNull ClientLevel clientLevel, double x, double y, double z, double g, double h, double i) {
            NewTermiteParticle termite = new NewTermiteParticle(clientLevel, x, y, z, clientLevel.random.nextBoolean() ? Rotation.COS : Rotation.SIN, clientLevel.random.nextBoolean() ? Rotation.COS : Rotation.SIN, clientLevel.random.nextBoolean() ? Rotation.COS : Rotation.SIN, clientLevel.random.nextBoolean(), clientLevel.random.nextBoolean(), clientLevel.random.nextBoolean(), ((float)clientLevel.random.nextInt(12, 24)), this.spriteProvider);
            termite.setAlpha(1.0F);
			termite.age = clientLevel.random.nextInt(24);
			termite.setLifetime(clientLevel.random.nextInt(30) + 60 + termite.age);
            termite.scale(2.0F);
            return termite;
        }

        public SpriteSet spriteProvider() {
            return this.spriteProvider;
        }
	}
}
