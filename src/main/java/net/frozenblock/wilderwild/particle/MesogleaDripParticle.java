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

import com.mojang.blaze3d.vertex.VertexConsumer;
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
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class MesogleaDripParticle extends TextureSheetParticle {

	private final Quaternionf rotation = new Quaternionf(0F, 0F, 0F, 0F);
	private boolean shouldTickUpXRotMultiplier;
	private float prevXRotMultiplier;
	private float xRotMultiplier;

	MesogleaDripParticle(@NotNull ClientLevel clientLevel, double d, double e, double f) {
		super(clientLevel, d, e, f);
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
	@NotNull
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
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
		if (this.removed) {
			return;
		}
		this.yd -= this.gravity;
		this.move(this.xd, this.yd, this.zd);
		this.postMoveUpdate();
		if (this.removed) {
			return;
		}
		this.prevXRotMultiplier = this.xRotMultiplier;
		this.xRotMultiplier += ((this.shouldTickUpXRotMultiplier ? 1F : 0F) - this.xRotMultiplier) * 0.25F;
		this.xd *= 0.98F;
		this.yd *= 0.98F;
		this.zd *= 0.98F;
		BlockPos blockPos = BlockPos.containing(this.x, this.y, this.z);
		FluidState fluidState = this.level.getFluidState(blockPos);
		if (fluidState.getType() == Fluids.WATER && this.y < (double) ((float) blockPos.getY() + fluidState.getHeight(this.level, blockPos))) {
			this.remove();
		}
	}

	@Override
	public void render(@NotNull VertexConsumer buffer, @NotNull Camera renderInfo, float partialTicks) {
		Vec3 vec3 = renderInfo.getPosition();
		float f = (float) (Mth.lerp(partialTicks, this.xo, this.x) - vec3.x());
		float g = (float) (Mth.lerp(partialTicks, this.yo, this.y) - vec3.y());
		float h = (float) (Mth.lerp(partialTicks, this.zo, this.z) - vec3.z());
		this.rotation.set(0F, 0F, 0F, 1F);
		this.rotation.mul(Axis.YP.rotationDegrees(-renderInfo.getYRot()));
		this.rotation.mul(Axis.XP.rotationDegrees(renderInfo.getXRot() * (Mth.lerp(partialTicks, this.prevXRotMultiplier, this.xRotMultiplier))));
		if (this.roll != 0.0f) {
			float i = Mth.lerp(partialTicks, this.oRoll, this.roll);
			this.rotation.mul(Axis.ZP.rotation(i));
		}
		Vector3f[] vector3fs = new Vector3f[]{
			new Vector3f(-1F, -1F, 0F),
			new Vector3f(-1F, 1F, 0F),
			new Vector3f(1F, 1F, 0F),
			new Vector3f(1F, -1F, 0F)
		};
		float j = this.getQuadSize(partialTicks);
		for (int k = 0; k < 4; ++k) {
			Vector3f vector3f2 = vector3fs[k];
			vector3f2.rotate(this.rotation);
			vector3f2.mul(j);
			vector3f2.add(f, g, h);
		}
		float l = this.getU0();
		float m = this.getU1();
		float n = this.getV0();
		float o = this.getV1();
		int p = this.getLightColor(partialTicks);
		buffer.addVertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).setUv(m, o).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(p);
		buffer.addVertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).setUv(m, n).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(p);
		buffer.addVertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).setUv(l, n).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(p);
		buffer.addVertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).setUv(l, o).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(p);
	}

	protected void preMoveUpdate() {
		if (this.lifetime-- <= 0) {
			this.remove();
		}
	}

	protected void postMoveUpdate() {
	}

	//Blue Pearlescent
	@Environment(EnvType.CLIENT)
	public static class BPMesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;

		public BPMesogleaFallProvider(@NotNull SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			MesogleaDripParticle.FallAndLandParticle dripParticle = new MesogleaDripParticle.FallAndLandParticle(clientLevel, d, e, f, WWParticleTypes.BLUE_PEARLESCENT_LANDING_MESOGLEA);
			dripParticle.pickSprite(this.sprite);
			return dripParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class BPMesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;

		public BPMesogleaHangProvider(@NotNull SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			return new DripHangParticle(clientLevel, d, e, f, WWParticleTypes.BLUE_PEARLESCENT_FALLING_MESOGLEA, sprite);
		}
	}

	@Environment(EnvType.CLIENT)
	public static class BPMesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;

		public BPMesogleaLandProvider(@NotNull SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			MesogleaDripParticle.DripLandParticle dripParticle = new MesogleaDripParticle.DripLandParticle(clientLevel, d, e, f);
			dripParticle.pickSprite(this.sprite);
			return dripParticle;
		}
	}

	//Purple Pearlescent
	@Environment(EnvType.CLIENT)
	public static class PPMesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;

		public PPMesogleaFallProvider(@NotNull SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			MesogleaDripParticle.FallAndLandParticle dripParticle = new MesogleaDripParticle.FallAndLandParticle(clientLevel, d, e, f, WWParticleTypes.PURPLE_PEARLESCENT_LANDING_MESOGLEA);
			dripParticle.pickSprite(this.sprite);
			return dripParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class PPMesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;

		public PPMesogleaHangProvider(@NotNull SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			return new DripHangParticle(clientLevel, d, e, f, WWParticleTypes.PURPLE_PEARLESCENT_FALLING_MESOGLEA, sprite);
		}
	}

	@Environment(EnvType.CLIENT)
	public static class PPMesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;

		public PPMesogleaLandProvider(@NotNull SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			MesogleaDripParticle.DripLandParticle dripParticle = new MesogleaDripParticle.DripLandParticle(clientLevel, d, e, f);
			dripParticle.pickSprite(this.sprite);
			return dripParticle;
		}
	}

	//Blue
	@Environment(EnvType.CLIENT)
	public static class BMesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;

		public BMesogleaFallProvider(@NotNull SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			MesogleaDripParticle.FallAndLandParticle dripParticle = new MesogleaDripParticle.FallAndLandParticle(clientLevel, d, e, f, WWParticleTypes.BLUE_LANDING_MESOGLEA);
			dripParticle.pickSprite(this.sprite);
			return dripParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class BMesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;

		public BMesogleaHangProvider(@NotNull SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			return new DripHangParticle(clientLevel, d, e, f, WWParticleTypes.BLUE_FALLING_MESOGLEA, sprite);
		}
	}

	@Environment(EnvType.CLIENT)
	public static class BMesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;

		public BMesogleaLandProvider(@NotNull SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			MesogleaDripParticle.DripLandParticle dripParticle = new MesogleaDripParticle.DripLandParticle(clientLevel, d, e, f);
			dripParticle.pickSprite(this.sprite);
			return dripParticle;
		}
	}

	//Yellow
	@Environment(EnvType.CLIENT)
	public static class YMesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;

		public YMesogleaFallProvider(@NotNull SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			MesogleaDripParticle.FallAndLandParticle dripParticle = new MesogleaDripParticle.FallAndLandParticle(clientLevel, d, e, f, WWParticleTypes.YELLOW_LANDING_MESOGLEA);
			dripParticle.pickSprite(this.sprite);
			return dripParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class YMesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;

		public YMesogleaHangProvider(@NotNull SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			return new DripHangParticle(clientLevel, d, e, f, WWParticleTypes.YELLOW_FALLING_MESOGLEA, sprite);
		}
	}

	@Environment(EnvType.CLIENT)
	public static class YMesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;

		public YMesogleaLandProvider(@NotNull SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			MesogleaDripParticle.DripLandParticle dripParticle = new MesogleaDripParticle.DripLandParticle(clientLevel, d, e, f);
			dripParticle.pickSprite(this.sprite);
			return dripParticle;
		}
	}

	//Lime
	@Environment(EnvType.CLIENT)
	public static class LMesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;

		public LMesogleaFallProvider(@NotNull SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			MesogleaDripParticle.FallAndLandParticle dripParticle = new MesogleaDripParticle.FallAndLandParticle(clientLevel, d, e, f, WWParticleTypes.LIME_LANDING_MESOGLEA);
			dripParticle.pickSprite(this.sprite);
			return dripParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class LMesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;

		public LMesogleaHangProvider(@NotNull SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			return new DripHangParticle(clientLevel, d, e, f, WWParticleTypes.LIME_FALLING_MESOGLEA, sprite);
		}
	}

	@Environment(EnvType.CLIENT)
	public static class LMesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;

		public LMesogleaLandProvider(@NotNull SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			MesogleaDripParticle.DripLandParticle dripParticle = new MesogleaDripParticle.DripLandParticle(clientLevel, d, e, f);
			dripParticle.pickSprite(this.sprite);
			return dripParticle;
		}
	}

	//Red
	@Environment(EnvType.CLIENT)
	public static class RMesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;

		public RMesogleaFallProvider(@NotNull SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			MesogleaDripParticle.FallAndLandParticle dripParticle = new MesogleaDripParticle.FallAndLandParticle(clientLevel, d, e, f, WWParticleTypes.RED_HANGING_MESOGLEA);
			dripParticle.pickSprite(this.sprite);
			return dripParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class RMesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;

		public RMesogleaHangProvider(@NotNull SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			return new DripHangParticle(clientLevel, d, e, f, WWParticleTypes.RED_FALLING_MESOGLEA, sprite);
		}
	}

	@Environment(EnvType.CLIENT)
	public static class RMesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;

		public RMesogleaLandProvider(@NotNull SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			MesogleaDripParticle.DripLandParticle dripParticle = new MesogleaDripParticle.DripLandParticle(clientLevel, d, e, f);
			dripParticle.pickSprite(this.sprite);
			return dripParticle;
		}
	}

	//Pink
	@Environment(EnvType.CLIENT)
	public static class PMesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;

		public PMesogleaFallProvider(@NotNull SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			MesogleaDripParticle.FallAndLandParticle dripParticle = new MesogleaDripParticle.FallAndLandParticle(clientLevel, d, e, f, WWParticleTypes.PINK_LANDING_MESOGLEA);
			dripParticle.pickSprite(this.sprite);
			return dripParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class PMesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;

		public PMesogleaHangProvider(@NotNull SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			return new DripHangParticle(clientLevel, d, e, f, WWParticleTypes.PINK_FALLING_MESOGLEA, sprite);
		}
	}

	@Environment(EnvType.CLIENT)
	public static class PMesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;

		public PMesogleaLandProvider(@NotNull SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			MesogleaDripParticle.DripLandParticle dripParticle = new MesogleaDripParticle.DripLandParticle(clientLevel, d, e, f);
			dripParticle.pickSprite(this.sprite);
			return dripParticle;
		}
	}


	@Environment(EnvType.CLIENT)
	static class DripLandParticle extends MesogleaDripParticle {
		DripLandParticle(@NotNull ClientLevel clientLevel, double d, double e, double f) {
			super(clientLevel, d, e, f);
			this.lifetime = (int) (16.0 / (AdvancedMath.random().nextDouble() * 0.8 + 0.2));
			this.scale(0.7F);
			this.lerpsToX(true);
			this.setBothXRotMultipliers(1F);
		}
	}

	@Environment(EnvType.CLIENT)
	static class FallingParticle extends MesogleaDripParticle {
		FallingParticle(@NotNull ClientLevel clientLevel, double d, double e, double f) {
			this(clientLevel, d, e, f, (int) (64.0 / (AdvancedMath.random().nextDouble() * 0.8 + 0.2)));
			this.scale(0.7F);
			this.lerpsToX(true);
		}

		FallingParticle(@NotNull ClientLevel clientLevel, double d, double e, double f, int i) {
			super(clientLevel, d, e, f);
			this.lifetime = i;
		}

		@Override
		protected void postMoveUpdate() {
			if (this.onGround) {
				this.remove();
			}
		}
	}

	@Environment(EnvType.CLIENT)
	static class FallAndLandParticle extends MesogleaDripParticle.FallingParticle {
		protected final ParticleOptions landParticle;

		FallAndLandParticle(@NotNull ClientLevel clientLevel, double d, double e, double f, ParticleOptions particleOptions) {
			super(clientLevel, d, e, f);
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

		DripHangParticle(@NotNull ClientLevel clientLevel, double d, double e, double f, ParticleOptions particleOptions, SpriteSet spriteSet) {
			super(clientLevel, d, e - 0.1D, f);
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
			if (!this.removed) {
				this.setSprite(spriteSet.get((int) (this.age * 0.2D) + 1, (int) (this.lifetime * 0.2D) + 1));
			}
		}
	}
}
