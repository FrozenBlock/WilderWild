/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.particle;

import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.state.QuadParticleRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class MesogleaDripParticle extends SingleQuadParticle {
	private boolean shouldTickUpXRotMultiplier;
	private float prevXRotMultiplier;
	private float xRotMultiplier;

	MesogleaDripParticle(@NotNull ClientLevel clientLevel, double x, double y, double z, TextureAtlasSprite sprite) {
		super(clientLevel, x, y, z, sprite);
		this.setSize(0.5F, 0.5F);
		this.gravity = 0.06F;
		this.quadSize = 0.5F;
	}

	public void setBothXRotMultipliers(float f) {
		this.prevXRotMultiplier = f;
		this.xRotMultiplier = f;
	}

	public void lerpsToX(boolean b) {
		this.shouldTickUpXRotMultiplier = b;
	}

	@Override
	public int getLightColor(float f) {
		return 240;
	}

	@Override
	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		this.preMoveUpdate();
		if (this.removed) return;
		this.yd -= this.gravity;
		this.move(this.xd, this.yd, this.zd);
		this.postMoveUpdate();
		if (this.removed) return;
		this.prevXRotMultiplier = this.xRotMultiplier;
		this.xRotMultiplier += ((this.shouldTickUpXRotMultiplier ? 1F : 0F) - this.xRotMultiplier) * 0.25F;
		this.xd *= 0.98F;
		this.yd *= 0.98F;
		this.zd *= 0.98F;
		BlockPos blockPos = BlockPos.containing(this.x, this.y, this.z);
		FluidState fluidState = this.level.getFluidState(blockPos);
		if (fluidState.getType() == Fluids.WATER && this.y < (double) ((float) blockPos.getY() + fluidState.getHeight(this.level, blockPos))) this.remove();
	}

	@Override
	public void extract(QuadParticleRenderState renderState, @NotNull Camera camera, float partialTick) {
		final Quaternionf quaternionf = new Quaternionf(0F, 0F, 0F, 0F);
		quaternionf.set(0F, 0F, 0F, 1F);
		quaternionf.mul(Axis.YP.rotationDegrees(-camera.yRot));
		quaternionf.mul(Axis.XP.rotationDegrees(camera.xRot() * (Mth.lerp(partialTick, this.prevXRotMultiplier, this.xRotMultiplier))));
		if (this.roll != 0F) quaternionf.mul(Axis.ZP.rotation(Mth.lerp(partialTick, this.oRoll, this.roll)));
		this.extractRotatedQuad(renderState, camera, quaternionf, partialTick);
	}

	protected void preMoveUpdate() {
		if (this.lifetime-- <= 0) this.remove();
	}

	protected void postMoveUpdate() {
	}

	@Override
	protected @NotNull Layer getLayer() {
		return Layer.OPAQUE;
	}

	//Blue Pearlescent
	public static class BPMesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public BPMesogleaFallProvider(@NotNull SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new FallAndLandParticle(level, x, y, z, WWParticleTypes.BLUE_PEARLESCENT_LANDING_MESOGLEA, this.spriteSet.get(random));
		}
	}

	public static class BPMesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public BPMesogleaHangProvider(@NotNull SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new DripHangParticle(level, x, y, z, WWParticleTypes.BLUE_PEARLESCENT_FALLING_MESOGLEA, this.spriteSet);
		}
	}

	public static class BPMesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public BPMesogleaLandProvider(@NotNull SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new DripLandParticle(level, x, y, z, this.spriteSet.get(random));
		}
	}

	//Purple Pearlescent
	public static class PPMesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public PPMesogleaFallProvider(@NotNull SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new FallAndLandParticle(level, x, y, z, WWParticleTypes.PURPLE_PEARLESCENT_LANDING_MESOGLEA, this.spriteSet.get(random));
		}
	}

	public static class PPMesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public PPMesogleaHangProvider(@NotNull SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new DripHangParticle(level, x, y, z, WWParticleTypes.PURPLE_PEARLESCENT_FALLING_MESOGLEA, this.spriteSet);
		}
	}

	public static class PPMesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public PPMesogleaLandProvider(@NotNull SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new DripLandParticle(level, x, y, z, this.spriteSet.get(random));
		}
	}

	//Blue
	public static class BMesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public BMesogleaFallProvider(@NotNull SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new FallAndLandParticle(level, x, y, z, WWParticleTypes.BLUE_LANDING_MESOGLEA, this.spriteSet.get(random));
		}
	}

	public static class BMesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public BMesogleaHangProvider(@NotNull SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new DripHangParticle(level, x, y, z, WWParticleTypes.BLUE_FALLING_MESOGLEA, this.spriteSet);
		}
	}

	public static class BMesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public BMesogleaLandProvider(@NotNull SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new DripLandParticle(level, x, y, z, this.spriteSet.get(random));
		}
	}

	//Yellow
	public static class YMesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public YMesogleaFallProvider(@NotNull SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new FallAndLandParticle(level, x, y, z, WWParticleTypes.YELLOW_LANDING_MESOGLEA, this.spriteSet.get(random));
		}
	}

	public static class YMesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public YMesogleaHangProvider(@NotNull SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new DripHangParticle(level, x, y, z, WWParticleTypes.YELLOW_FALLING_MESOGLEA, this.spriteSet);
		}
	}

	public static class YMesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public YMesogleaLandProvider(@NotNull SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new DripLandParticle(level, x, y, z, this.spriteSet.get(random));
		}
	}

	//Lime
	public static class LMesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public LMesogleaFallProvider(@NotNull SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new FallAndLandParticle(level, x, y, z, WWParticleTypes.LIME_LANDING_MESOGLEA, this.spriteSet.get(random));
		}
	}

	public static class LMesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public LMesogleaHangProvider(@NotNull SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new DripHangParticle(level, x, y, z, WWParticleTypes.LIME_FALLING_MESOGLEA, this.spriteSet);
		}
	}

	public static class LMesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public LMesogleaLandProvider(@NotNull SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new DripLandParticle(level, x, y, z, this.spriteSet.get(random));
		}
	}

	//Red
	public static class RMesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public RMesogleaFallProvider(@NotNull SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new FallAndLandParticle(level, x, y, z, WWParticleTypes.RED_HANGING_MESOGLEA, this.spriteSet.get(random));
		}
	}

	public static class RMesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public RMesogleaHangProvider(@NotNull SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new DripHangParticle(level, x, y, z, WWParticleTypes.RED_FALLING_MESOGLEA, this.spriteSet);
		}
	}

	public static class RMesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public RMesogleaLandProvider(@NotNull SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new DripLandParticle(level, x, y, z, this.spriteSet.get(random));
		}
	}

	//Pink
	public static class PMesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public PMesogleaFallProvider(@NotNull SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new FallAndLandParticle(level, x, y, z, WWParticleTypes.PINK_LANDING_MESOGLEA, this.spriteSet.get(random));
		}
	}

	public static class PMesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public PMesogleaHangProvider(@NotNull SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new DripHangParticle(level, x, y, z, WWParticleTypes.PINK_FALLING_MESOGLEA, this.spriteSet);
		}
	}

	public static class PMesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteSet;

		public PMesogleaLandProvider(@NotNull SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new DripLandParticle(level, x, y, z, this.spriteSet.get(random));
		}
	}

	static class DripLandParticle extends MesogleaDripParticle {
		DripLandParticle(@NotNull ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite) {
			super(level, x, y, z, sprite);
			this.lifetime = (int) (16.0 / (AdvancedMath.random().nextDouble() * 0.8 + 0.2));
			this.scale(0.7F);
			this.lerpsToX(true);
			this.setBothXRotMultipliers(1F);
		}
	}

	static class FallingParticle extends MesogleaDripParticle {
		FallingParticle(@NotNull ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite) {
			this(level, x, y, z, (int) (64.0 / (AdvancedMath.random().nextDouble() * 0.8 + 0.2)), sprite);
			this.scale(0.7F);
			this.lerpsToX(true);
		}

		FallingParticle(@NotNull ClientLevel level, double x, double y, double z, int lifetime, TextureAtlasSprite sprite) {
			super(level, x, y, z, sprite);
			this.lifetime = lifetime;
		}

		@Override
		protected void postMoveUpdate() {
			if (this.onGround) this.remove();
		}
	}

	static class FallAndLandParticle extends MesogleaDripParticle.FallingParticle {
		protected final ParticleOptions landParticle;

		FallAndLandParticle(@NotNull ClientLevel clientLevel, double x, double y, double z, ParticleOptions particleOptions, TextureAtlasSprite sprite) {
			super(clientLevel, x, y, z, sprite);
			this.landParticle = particleOptions;
		}

		@Override
		protected void postMoveUpdate() {
			if (this.onGround) {
				this.remove();
				this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0D, 0D, 0D);
				SoundEvent soundEvent = WWSounds.PARTICLE_MESOGLEA_DRIP_LAND;
				float f = Mth.randomBetween(this.random, 0.3F, 1F);
				this.level.playLocalSound(this.x, this.y, this.z, soundEvent, SoundSource.BLOCKS, f, 1F, false);
			}
		}
	}

	@Environment(EnvType.CLIENT)
	static class DripHangParticle extends MesogleaDripParticle {
		private final ParticleOptions fallingParticle;
		private final SpriteSet spriteSet;

		DripHangParticle(@NotNull ClientLevel clientLevel, double x, double y, double z, ParticleOptions particleOptions, SpriteSet spriteSet) {
			super(clientLevel, x, y - 0.1D, z, spriteSet.first());
			this.fallingParticle = particleOptions;
			this.gravity *= 0.00F;
			this.lifetime = 40;
			this.spriteSet = spriteSet;
			this.setSpriteFromAge(this.spriteSet);
			this.scale(0.7F);
		}

		@Override
		protected void preMoveUpdate() {
			if (this.lifetime-- <= 0) {
				this.remove();
				this.level.addParticle(this.fallingParticle, this.x, this.y, this.z, this.xd, this.yd, this.zd);
			}
		}

		@Override
		protected void postMoveUpdate() {
			if (!this.removed) this.setSprite(spriteSet.get((int) (this.age * 0.2D) + 1, (int) (this.lifetime * 0.2D) + 1));
		}
	}
}
