package net.frozenblock.wilderwild.networking;

import java.util.concurrent.atomic.AtomicInteger;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.config.EntityConfig;
import net.frozenblock.wilderwild.particle.options.FloatingSculkBubbleParticleOptions;
import net.frozenblock.wilderwild.particle.options.SeedParticleOptions;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class WilderClientNetworking {

	public static void registerPacketReceivers() {
		receiveEasyEchoerBubblePacket();
		receiveSeedPacket();
		receiveControlledSeedPacket();
		receiveTermitePacket();
		receiveSensorHiccupPacket();
		receiveJellyStingPacket();
		receiveLightningStrikePacket();
	}
	private static void receiveEasyEchoerBubblePacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderNetworking.FLOATING_SCULK_BUBBLE_PACKET, (ctx, handler, byteBuf, responseSender) -> {
			Vec3 pos = new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
			double size = byteBuf.readDouble();
			int age = byteBuf.readInt();
			double yVel = byteBuf.readDouble();
			int count = byteBuf.readVarInt();
			ctx.execute(() -> {
				if (ctx.level == null)
					throw new IllegalStateException("why is your world null");
				var random = AdvancedMath.random();
				for (int i = 0; i < count; i++) {
					double xVel = (random.nextDouble() - 0.5) / 9.5;
					double zVel = (random.nextDouble() - 0.5) / 9.5;
					if (size >= 1) {
						xVel = (random.nextDouble() - 0.5) / 10.5;
						zVel = (random.nextDouble() - 0.5) / 10.5;
					}
					ctx.level.addParticle(new FloatingSculkBubbleParticleOptions(size, age, new Vec3(xVel, yVel, zVel)), pos.x, pos.y, pos.z, 0, 0, 0);
				}
			});
		});
	}

	private static void receiveSeedPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderNetworking.SEED_PACKET, (ctx, handler, byteBuf, responseSender) -> {
			Vec3 pos = new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
			int count = byteBuf.readVarInt();
			boolean milkweed = byteBuf.readBoolean();
			ctx.execute(() -> {
				if (ctx.level == null)
					throw new IllegalStateException("why is your world null");
				for (int i = 0; i < count; i++) {
					ctx.level.addParticle(new SeedParticleOptions(milkweed, false), pos.x, pos.y, pos.z, 0, 0, 0);
				}
			});
		});
	}

	private static void receiveControlledSeedPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderNetworking.CONTROLLED_SEED_PACKET, (ctx, handler, byteBuf, responseSender) -> {
			Vec3 pos = new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
			double velx = byteBuf.readDouble();
			double vely = byteBuf.readDouble();
			double velz = byteBuf.readDouble();
			int count = byteBuf.readVarInt();
			boolean milkweed = byteBuf.readBoolean();
			double posRandomizer = byteBuf.readDouble();
			ctx.execute(() -> {
				if (ctx.level == null)
					throw new IllegalStateException("why is your world null");
				for (int i = 0; i < count; i++) {
					ctx.level.addParticle(new SeedParticleOptions(milkweed, true), pos.x, pos.y + ((ctx.level.random.nextBoolean() ? -1 : 1) * (ctx.level.random.nextDouble() * posRandomizer)), pos.z, velx, vely + (ctx.level.random.nextDouble() * 0.07), velz);
				}
			});
		});
	}

	private static void receiveTermitePacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderNetworking.TERMITE_PARTICLE_PACKET, (ctx, handler, byteBuf, responseSender) -> {
			Vec3 pos = new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
			AtomicInteger count = new AtomicInteger(byteBuf.readVarInt());
			ctx.execute(() -> {
				if (ctx.level == null)
					throw new IllegalStateException("why is your world null");

				count.addAndGet(-1);
				ctx.level.addAlwaysVisibleParticle(RegisterParticles.TERMITE, pos.x, pos.y, pos.z, 0, 0, 0);
				for (int i = 0; i < count.get(); i++) {
					ctx.level.addParticle(RegisterParticles.TERMITE, pos.x, pos.y, pos.z, 0, 0, 0);
				}
			});
		});
	}

	private static void receiveSensorHiccupPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderNetworking.SENSOR_HICCUP_PACKET, (ctx, handler, byteBuf, responseSender) -> {
			Vec3 pos = new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
			ctx.execute(() -> {
				if (ctx.level == null)
					throw new IllegalStateException("why is your world null");
				ClientLevel level = ctx.level;
				int i = 5578058;
				boolean bl2 = level.random.nextBoolean();
				if (bl2) {
					double d = (double) (i >> 16 & 255) / 255.0D;
					double e = (double) (i >> 8 & 255) / 255.0D;
					double f = (double) (i & 255) / 255.0D;
					level.addParticle(ParticleTypes.ENTITY_EFFECT, pos.x, pos.y, pos.z, d, e, f);
				}
			});
		});
	}

	private static void receiveJellyStingPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderNetworking.JELLY_STING_PACKET, (ctx, handler, byteBuf, responseSender) -> ctx.execute(() -> {
			boolean baby = byteBuf.readBoolean();
			if (ctx.level != null) {
				LocalPlayer player = ctx.player;
				if (player != null) {
					ctx.level.playSound(player, player.getX(), player.getY(), player.getZ(), RegisterSounds.ENTITY_JELLYFISH_STING, SoundSource.NEUTRAL, 1.0F, ctx.level.random.nextFloat() * 0.2F + (baby ? 1.2F : 0.9F));
				}
			}
		}));
	}

	private static void receiveLightningStrikePacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderNetworking.LIGHTNING_STRIKE_PACKET, (ctx, handler, byteBuf, responseSender) -> ctx.execute(() -> {
			BlockState blockState = Block.stateById(byteBuf.readInt());
			double x = byteBuf.readDouble();
			double y = byteBuf.readDouble();
			double z = byteBuf.readDouble();
			int tickCount = byteBuf.readVarInt();
			if (ctx.level != null && !blockState.isAir()) {
				RandomSource random = ctx.level.getRandom();
				if (EntityConfig.get().lightningBlockParticles) {
					lightningBlockParticles(tickCount, x, y, z, blockState, random, ctx.particleEngine);
				}
				if (EntityConfig.get().lightningSmokeParticles) {
					lightningSmokeParticles(tickCount, x, y, z, blockState, random, ctx.particleEngine);
				}
			}
		}));
	}

	private static void lightningBlockParticles(int tickCount, double x, double y, double z, @NotNull BlockState blockState, @NotNull RandomSource random, @NotNull ParticleEngine particleEngine) {
		if (blockState.is(WilderBlockTags.NO_LIGHTNING_BLOCK_PARTICLES)) {
			return;
		}
		boolean first = tickCount == 0;
		double calmDownAge = Math.max(1, tickCount - 6D);
		Vec3 origin = new Vec3(x, y, z);
		int particles = first ? random.nextInt(25, 40) : random.nextInt(5, 15);
		double rotAngle = 360D / (double) particles;
		double angle = random.nextDouble() * 360D;
		ParticleOptions particleOptions = new BlockParticleOption(ParticleTypes.BLOCK, blockState);
		if (blockState.is(Blocks.WATER)) {
			particleOptions = ParticleTypes.SPLASH;
		} else if (blockState.is(Blocks.LAVA)) {
			particleOptions = ParticleTypes.LAVA;
		}
		double speedMultiplier = first ? 1.5D : 1D;
		double speedMultiplierY = first ? 1.13D : 1D;

		for (int a = 0; a < particles; a++) {
			Vec3 offsetPos = AdvancedMath.rotateAboutXZ(origin, 0.4D, angle + (((random.nextDouble() * rotAngle) * 0.35D) * (random.nextBoolean() ? 1D : -1D)));
			double dirX = (offsetPos.x - origin.x) * ((random.nextFloat() * 0.6D) + 0.4D);
			double dirZ = (offsetPos.z - origin.z) * ((random.nextFloat() * 0.6D) + 0.4D);

			Particle blockParticle = particleEngine.createParticle(particleOptions, x + dirX, y, z + dirZ, 0D, 0D, 0D);
			if (blockParticle != null) {
				blockParticle.xd = ((dirX * 0.8D) / calmDownAge) * speedMultiplier;
				blockParticle.yd = ((0.4D / calmDownAge) * ((random.nextFloat() * 0.4D) + 0.7D)) * speedMultiplierY;
				blockParticle.zd = ((dirZ * 0.8D) / calmDownAge) * speedMultiplier;
			}

			if (random.nextBoolean()) {
				Particle particle2 = particleEngine.createParticle(ParticleTypes.LARGE_SMOKE, x + dirX * 0.3D, y, z + dirZ * 0.3D, 0D, 0D, 0D);
				if (particle2 != null) {
					particle2.xd = ((dirX * 0.2D) / calmDownAge) * speedMultiplier;
					particle2.yd = ((0.5D / calmDownAge) * ((random.nextFloat() * 0.4D) + 0.7D)) * speedMultiplierY;
					particle2.zd = ((dirZ * 0.2D) / calmDownAge) * speedMultiplier;
				}
			}

			angle += rotAngle;
		}
	}

	private static void lightningSmokeParticles(int tickCount, double x, double y, double z, @NotNull BlockState blockState, @NotNull RandomSource random, @NotNull ParticleEngine particleEngine) {
		if (blockState.is(WilderBlockTags.NO_LIGHTNING_SMOKE_PARTICLES)) {
			return;
		}
		boolean first = tickCount == 0;
        Vec3 origin = new Vec3(x, y, z);
		int particles = random.nextInt(2, 15);
		double rotAngle = 360D / (double) particles;
		double angle = random.nextDouble() * 360D;
		double speedMultiplier = first ? 1.5D : 1D;
		double speedMultiplierY = first ? 1.13D : 1D;

		for (int a = 0; a < particles; a++) {
			Vec3 offsetPos = AdvancedMath.rotateAboutXZ(origin, 0.4D, angle + (((random.nextDouble() * rotAngle) * 0.35D) * (random.nextBoolean() ? 1D : -1D)));
			double dirX = (offsetPos.x - origin.x) * ((random.nextFloat() * 0.6D) + 0.4D) / (double) tickCount;
			double dirZ = (offsetPos.z - origin.z) * ((random.nextFloat() * 0.6D) + 0.4D) / (double) tickCount;

			if (random.nextBoolean()) {
				Particle particle2 = particleEngine.createParticle(ParticleTypes.LARGE_SMOKE, x + dirX * 0.3D, y, z + dirZ * 0.3D, 0D, 0D, 0D);
				if (particle2 != null) {
					particle2.xd = ((dirX * 0.2D)) * speedMultiplier;
					particle2.yd = ((0.5D / (double) tickCount) * ((random.nextFloat() * 0.4D) + 0.7D)) * speedMultiplierY;
					particle2.zd = ((dirZ * 0.2D)) * speedMultiplier;
				}
			}

			angle += rotAngle;
		}
	}

}
