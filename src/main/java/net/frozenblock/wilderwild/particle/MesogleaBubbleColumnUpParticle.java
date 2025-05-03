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
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.RisingParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class MesogleaBubbleColumnUpParticle extends RisingParticle {
	private final ParticleOptions popParticle;

	protected MesogleaBubbleColumnUpParticle(
		@NotNull ClientLevel clientLevel,
		double x,
		double y,
		double z,
		double xd,
		double yd,
		double zd,
		ParticleOptions popParticle
	) {
		super(clientLevel, x, y, z, xd, yd, zd);
		this.popParticle = popParticle;
	}

	@Override
	public int getLightColor(float tint) {
		return 240;
	}

	@Override
	@NotNull
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public void tick() {
		super.tick();
		Vec3 wind = ClientWindManager.getWindMovement(this.level, new Vec3(this.x, this.y, this.z), 1.5D, 7D, 5D)
			.scale(WWAmbienceAndMiscConfig.getParticleWindIntensity());
		this.xd += wind.x * 0.001D;
		this.yd += wind.y * 0.00005D;
		this.zd += wind.z * 0.001D;

		if (!this.level.getFluidState(BlockPos.containing(this.x, this.y, this.z)).is(FluidTags.WATER)) this.age += 1;
	}

	@Override
	public void remove() {
		this.level.playSound(
			Minecraft.getInstance().player,
			this.x,
			this.y,
			this.z,
			WWSounds.PARTICLE_MESOGLEA_BUBBLE_POP,
			SoundSource.NEUTRAL,
			0.025F,
			this.random.nextFloat() * 0.2F + 0.8F
		);
		this.level.addParticle(
			this.popParticle,
			this.x,
			this.y,
			this.z,
			this.xd,
			this.yd,
			this.zd
		);
		super.remove();
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteProvider;
		private final ParticleOptions popParticle;

		public Provider(SpriteSet spriteProvider, ParticleOptions popParticle) {
			this.spriteProvider = spriteProvider;
			this.popParticle = popParticle;
		}

		@Override
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel clientLevel,
			double x,
			double y,
			double z,
			double xd,
			double yd,
			double zd
		) {
			MesogleaBubbleColumnUpParticle bubble = new MesogleaBubbleColumnUpParticle(clientLevel, x, y, z, xd, yd, zd, this.popParticle);
			bubble.pickSprite(this.spriteProvider);
			return bubble;
		}
	}

}
