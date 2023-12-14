package net.frozenblock.wilderwild.networking.packet;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.config.EntityConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record WilderLightningStrikePacket(int blockStateId, double x, double y, double z, int tickCount) implements FabricPacket {

	public static final PacketType<WilderLightningStrikePacket> PACKET_TYPE = PacketType.create(
			WilderSharedConstants.id("lightning_strike_packet"),
			WilderLightningStrikePacket::new
	);

	public WilderLightningStrikePacket(@NotNull FriendlyByteBuf buf) {
		this(buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readVarInt());
	}

	public static void sendToAll(@NotNull Entity entity, @NotNull BlockState blockState, int tickCount) {
		WilderLightningStrikePacket lightningStrikePacket = new WilderLightningStrikePacket(
				Block.getId(blockState),
				entity.getX(),
				entity.getY(),
				entity.getZ(),
				tickCount
		);
		for (ServerPlayer player : PlayerLookup.tracking(entity)) {
			ServerPlayNetworking.send(player, lightningStrikePacket);
		}
	}

	@Environment(EnvType.CLIENT)
	public static void receive() {
		ClientPlayNetworking.registerGlobalReceiver(PACKET_TYPE, (packet, player, responseSender) -> {
			BlockState blockState = Block.stateById(packet.blockStateId());
			double x = packet.x();
			double y = packet.y();
			double z = packet.z();
			int tickCount = packet.tickCount();
			Minecraft minecraft = Minecraft.getInstance();
			ClientLevel clientLevel = player.clientLevel;
			if (clientLevel != null && !blockState.isAir()) {
				RandomSource random = clientLevel.getRandom();
				if (EntityConfig.get().lightning.lightningBlockParticles) {
					lightningBlockParticles(tickCount, x, y, z, blockState, random, minecraft.particleEngine);
				}
				if (EntityConfig.get().lightning.lightningSmokeParticles) {
					lightningSmokeParticles(tickCount, x, y, z, blockState, random, minecraft.particleEngine);
				}
			}
		});
	}

	@Environment(EnvType.CLIENT)
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

	@Environment(EnvType.CLIENT)
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

	@Override
	public void write(@NotNull FriendlyByteBuf buf) {
		buf.writeInt(this.blockStateId());
		buf.writeDouble(this.x());
		buf.writeDouble(this.y());
		buf.writeDouble(this.z());
		buf.writeVarInt(this.tickCount());
	}

	@Override
	public PacketType<?> getType() {
		return PACKET_TYPE;
	}
}
