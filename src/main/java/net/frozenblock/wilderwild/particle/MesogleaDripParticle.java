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
		final Quaternionf rotation = new Quaternionf();
		this.getFacingCameraMode().setRotation(rotation, camera, partialTick);
		rotation.rotateX(-camera.xRot() * (Mth.lerp(partialTick, this.prevXRotMultiplier, this.xRotMultiplier)) * Mth.DEG_TO_RAD);
		if (this.roll != 0F) rotation.rotateZ(Mth.lerp(partialTick, this.oRoll, this.roll));
		this.extractRotatedQuad(renderState, camera, rotation, partialTick);
	}

	@Override
	public @NotNull FacingCameraMode getFacingCameraMode() {
		return FacingCameraMode.LOOKAT_Y;
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

	public record LandProvider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
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

	//Blue Pearlescent
	public record PearlescentBlueFallProvider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new FallAndLandParticle(level, x, y, z, WWParticleTypes.LANDING_MESOGLEA_PEARLESCENT_BLUE, this.spriteSet.get(random));
		}
	}

	public record PearlescentBlueHangProvider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new DripHangParticle(level, x, y, z, WWParticleTypes.FALLING_MESOGLEA_PEARLESCENT_BLUE, this.spriteSet);
		}
	}

	//Purple Pearlescent
	public record PearlescentPurpleFallProvider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new FallAndLandParticle(level, x, y, z, WWParticleTypes.LANDING_MESOGLEA_PEARLESCENT_PURPLE, this.spriteSet.get(random));
		}
	}

	public record PearlescentPurpleHangProvider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new DripHangParticle(level, x, y, z, WWParticleTypes.FALLING_MESOGLEA_PEARLESCENT_PURPLE, this.spriteSet);
		}
	}

	//Blue
	public record BlueFallProvider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new FallAndLandParticle(level, x, y, z, WWParticleTypes.LANDING_MESOGLEA_BLUE, this.spriteSet.get(random));
		}
	}

	public record BlueHangProvider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new DripHangParticle(level, x, y, z, WWParticleTypes.FALLING_MESOGLEA_BLUE, this.spriteSet);
		}
	}

	//Yellow
	public record YellowFallProvider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new FallAndLandParticle(level, x, y, z, WWParticleTypes.LANDING_MESOGLEA_YELLOW, this.spriteSet.get(random));
		}
	}

	public record YellowHangProvider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new DripHangParticle(level, x, y, z, WWParticleTypes.FALLING_MESOGLEA_YELLOW, this.spriteSet);
		}
	}

	//Lime
	public record LimeFallProvider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new FallAndLandParticle(level, x, y, z, WWParticleTypes.LANDING_MESOGLEA_LIME, this.spriteSet.get(random));
		}
	}

	public record LimeHangProvider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new DripHangParticle(level, x, y, z, WWParticleTypes.FALLING_MESOGLEA_LIME, this.spriteSet);
		}
	}

	//Red
	public record RedFallProvider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new FallAndLandParticle(level, x, y, z, WWParticleTypes.HANGING_MESOGLEA_RED, this.spriteSet.get(random));
		}
	}

	public record RedHangProvider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new DripHangParticle(level, x, y, z, WWParticleTypes.FALLING_MESOGLEA_RED, this.spriteSet);
		}
	}

	//Pink
	public record PinkFallProvider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new FallAndLandParticle(level, x, y, z, WWParticleTypes.LANDING_MESOGLEA_PINK, this.spriteSet.get(random));
		}
	}

	public record PinkHangProvider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new DripHangParticle(level, x, y, z, WWParticleTypes.FALLING_MESOGLEA_PINK, this.spriteSet);
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
			if (!this.onGround) return;
			this.remove();
			this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0D, 0D, 0D);
			final float pitch = Mth.randomBetween(this.random, 0.3F, 1F);
			this.level.playLocalSound(this.x, this.y, this.z, WWSounds.PARTICLE_MESOGLEA_DRIP_LAND, SoundSource.BLOCKS, pitch, 1F, false);
		}
	}

	@Environment(EnvType.CLIENT)
	static class DripHangParticle extends MesogleaDripParticle {
		private final ParticleOptions fallingParticle;
		private final SpriteSet spriteSet;

		DripHangParticle(@NotNull ClientLevel clientLevel, double x, double y, double z, ParticleOptions particleOptions, SpriteSet spriteSet) {
			super(clientLevel, x, y - 0.04D, z, spriteSet.first());
			this.fallingParticle = particleOptions;
			this.gravity *= 0.00F;
			this.lifetime = 40;
			this.spriteSet = spriteSet;
			this.setSpriteFromAge(this.spriteSet);
			this.scale(0.7F);
		}

		@Override
		protected void preMoveUpdate() {
			if (this.lifetime-- > 0) return;
			this.remove();
			this.level.addParticle(this.fallingParticle, this.x, this.y, this.z, this.xd, this.yd, this.zd);
		}

		@Override
		protected void postMoveUpdate() {
			if (!this.removed) this.setSprite(spriteSet.get((int) (this.age * 0.2D) + 1, (int) (this.lifetime * 0.2D) + 1));
		}
	}
}
