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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.particle.options.SeedParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class SeedParticle extends TextureSheetParticle {
    public double windIntensity;

    SeedParticle(ClientLevel level, SpriteSet spriteProvider, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(level, x, y - 0.125D, z, velocityX, velocityY, velocityZ);
        this.setSize(0.01F, 0.02F);
        this.pickSprite(spriteProvider);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.6F;
        this.lifetime = (int) (16.0D / (AdvancedMath.random().nextDouble() * 0.8D + 0.2D));
        this.hasPhysics = true;
        this.friction = 1.0F;
        this.gravity = 0.0F;
    }

    @Override
    public void tick() {
        super.tick();
		this.windIntensity *= 0.945F;
		BlockPos blockPos = BlockPos.containing(this.x, this.y, this.z);
		FluidState fluidState = this.level.getBlockState(blockPos).getFluidState();
		if (!fluidState.isEmpty() && (fluidState.getHeight(this.level, blockPos) + (float)blockPos.getY()) >= this.y) {
			return;
		}
		double multXZ = (this.onGround ? 0.0005 : 0.007) * this.windIntensity;
		double multY = (this.onGround ? 0.0005 : 0.0035) * this.windIntensity;
		Vec3 wind = ClientWindManager.getWindMovement(this.level, BlockPos.containing(this.x, this.y, this.z)).scale(WilderSharedConstants.config().particleWindMovement());
		this.xd += wind.x() * multXZ;
		this.yd += (wind.y() + 0.1) * multY;
		this.zd += wind.z() * multXZ;
    }

	@Override
	@NotNull
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
	public record Factory(SpriteSet spriteProvider) implements ParticleProvider<SeedParticleOptions> {
        @Override
        public Particle createParticle(@NotNull SeedParticleOptions options, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            double windex = options.isControlled() ? xSpeed * 1.1 : ClientWindManager.getWindX(1) * 1.1;
            double windZ = options.isControlled() ? zSpeed * 1.1 : ClientWindManager.getWindZ(1) * 1.1;
            SeedParticle seedParticle = new SeedParticle(level, this.spriteProvider, x, y, z, windex, -0.800000011920929D, windZ);
			seedParticle.lifetime = Mth.randomBetweenInclusive(level.random, 500, 1000);
			seedParticle.gravity = 0.01F;
			seedParticle.xd = (windex + level.random.triangle(0, 0.8)) / 17;
			seedParticle.zd = (windZ + level.random.triangle(0, 0.8)) / 17;
			seedParticle.yd = options.isControlled() ? ySpeed / 17 : seedParticle.yd;
			seedParticle.setColor(250F / 255F, 250F / 255F, 250F / 255F);
			seedParticle.windIntensity = options.isControlled() ? 0.5 : 1;
            return seedParticle;
        }
    }

}
