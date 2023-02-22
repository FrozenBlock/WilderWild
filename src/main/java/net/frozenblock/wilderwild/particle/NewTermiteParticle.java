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
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class NewTermiteParticle extends TextureSheetParticle {
    private final SpriteSet spriteProvider;
	private boolean hasSetLightColor;
	private int lightColor;

    public NewTermiteParticle(ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(level, x, y, z, velocityX, velocityY, velocityZ);
        this.spriteProvider = spriteProvider;
        this.hasPhysics = false;
        this.setSpriteFromAge(spriteProvider);
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
		if (this.age++ >= this.lifetime) {
			this.remove();
			return;
		}
        if (!this.removed) {
			this.setLightColor();
        }
    }

	private void setLightColor() {
		BlockPos thisPos = new BlockPos(this.x, this.y, this.z);
		int light = 0;
		for (Direction direction : Direction.values()) {
			BlockPos pos = thisPos.relative(direction);
			if (this.level.hasChunkAt(pos)) {
				light = Math.max(LevelRenderer.getLightColor(this.level, pos), light);
			}
		}
		this.lightColor = light;
	}

	@Override
	public void render(@NotNull VertexConsumer buffer, @NotNull Camera renderInfo, float partialTicks) {
		if (!this.hasSetLightColor) {
			this.setLightColor();
			this.hasSetLightColor = true;
		}

		float animationProgress = this.age + partialTicks;
		float cos = (float) Math.cos((animationProgress * Math.PI) / 19F);
		float sin = (float) Math.sin((animationProgress * Math.PI) / 19F);
		float aCos = (float) Math.acos((animationProgress * Math.PI) / 19F);

		Quaternion quaternion;
		Vec3 vec3 = renderInfo.getPosition();
		float f = (float)(Mth.lerp(partialTicks, this.xo, this.x) - vec3.x() + cos);
		float g = (float)(Mth.lerp(partialTicks, this.yo, this.y) - vec3.y() + sin);
		float h = (float)(Mth.lerp(partialTicks, this.zo, this.z) - vec3.z() + aCos);
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
		int p = this.getLightColor(partialTicks);
		buffer.vertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).uv(m, o).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(p).endVertex();
		buffer.vertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).uv(m, n).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(p).endVertex();
		buffer.vertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).uv(l, n).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(p).endVertex();
		buffer.vertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).uv(l, o).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(p).endVertex();
	}

	protected int getLightColor(float partialTick) {
		return this.lightColor;
	}

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(@NotNull SimpleParticleType defaultParticleType, @NotNull ClientLevel clientLevel, double x, double y, double z, double g, double h, double i) {
            NewTermiteParticle termite = new NewTermiteParticle(clientLevel, x, y, z, g, h, i, this.spriteProvider);
            termite.setAlpha(1.0F);
			termite.age = clientLevel.random.nextInt(24);
			termite.setLifetime(clientLevel.random.nextInt(30) + 20 + termite.age);
            termite.scale(1.0F);
            return termite;
        }

        public SpriteSet spriteProvider() {
            return this.spriteProvider;
        }
    }
}
