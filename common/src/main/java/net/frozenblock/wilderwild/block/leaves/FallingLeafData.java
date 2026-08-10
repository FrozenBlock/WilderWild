package net.frozenblock.wilderwild.block.leaves;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import io.netty.buffer.ByteBuf;
import net.frozenblock.lib.config.v2.entry.ConfigEntry;
import net.frozenblock.lib.config.v2.entry.getter.ConfigEntryGetter;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.entity.FallingLeafTicker;
import net.frozenblock.wilderwild.particle.options.WWFallingLeavesParticleOptions;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.codec.RegistryCodecs;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.core.registries.codec.RegistryFixedCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public record FallingLeafData(
	HolderSet<Block> leavesBlock,
	HolderSet<Block> leafLitterBlock,
	Optional<ParticleData> leafParticleData,
	Optional<ParticleData> leafLitterParticleData,
	Optional<FallingLeafLitterData> fallingLeafLitterData
) {
	public static final Codec<FallingLeafData> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
		RegistryCodecs.holderSet(Registries.BLOCK).fieldOf("leaves_block").forGetter(FallingLeafData::leavesBlock),
		RegistryCodecs.holderSet(Registries.BLOCK).fieldOf("leaf_litter_block").forGetter(FallingLeafData::leafLitterBlock),
		ParticleData.CODEC.optionalFieldOf("leaf_particle").forGetter(FallingLeafData::leafParticleData),
		ParticleData.CODEC.optionalFieldOf("leaf_litter_particle").forGetter(FallingLeafData::leafLitterParticleData),
		FallingLeafLitterData.CODEC.optionalFieldOf("falling_leaf_litter").forGetter(FallingLeafData::fallingLeafLitterData)
	).apply(instance, FallingLeafData::new));
	public static final Codec<FallingLeafData> NETWORK_CODEC = RecordCodecBuilder.create(instance -> instance.group(
		RegistryCodecs.holderSet(Registries.BLOCK).fieldOf("leaves_block").forGetter(FallingLeafData::leavesBlock),
		RegistryCodecs.holderSet(Registries.BLOCK).fieldOf("leaf_litter_block").forGetter(FallingLeafData::leafLitterBlock),
		ParticleData.CODEC.optionalFieldOf("leaf_particle").forGetter(FallingLeafData::leafParticleData),
		ParticleData.CODEC.optionalFieldOf("leaf_litter_particle").forGetter(FallingLeafData::leafLitterParticleData)
	).apply(instance, FallingLeafData::new));

	public FallingLeafData(
		HolderSet<Block> leavesBlock,
		HolderSet<Block> leafLitterBlock,
		Optional<ParticleData> leafParticleData,
		Optional<ParticleData> leafLitterParticleData
	) {
		this(leavesBlock, leafLitterBlock, leafParticleData, leafLitterParticleData, Optional.empty());
	}

	public record ParticleData(
		Holder<ParticleType<?>> particle,
		Optional<Holder<Block>> originBlock,
		boolean cancelsVanillaParticles,
		float spawnChance,
		Optional<ConfigEntryGetter> spawnChanceModifier,
		int textureSize,
		float gravityScale,
		float windScale,
		LeafMovementType movementType
	) {
		public static final Codec<ParticleData> CODEC = RecordCodecBuilder.<ParticleData>create(instance -> instance.group(
			RegistryFixedCodec.create(Registries.PARTICLE_TYPE).fieldOf("particle").forGetter(ParticleData::particle),
			RegistryFixedCodec.create(Registries.BLOCK).optionalFieldOf("origin_block").forGetter(ParticleData::originBlock),
			Codec.BOOL.optionalFieldOf("cancels_vanilla_particles", true).forGetter(ParticleData::cancelsVanillaParticles),
			ExtraCodecs.floatRange(0F, 1F).optionalFieldOf("spawn_chance", 0F).forGetter(ParticleData::spawnChance),
			ConfigEntryGetter.CODEC.optionalFieldOf("spawn_chance_modifier").forGetter(ParticleData::spawnChanceModifier),
			ExtraCodecs.POSITIVE_INT.fieldOf("texture_size").forGetter(ParticleData::textureSize),
			Codec.FLOAT.fieldOf("gravity_scale").forGetter(ParticleData::gravityScale),
			Codec.FLOAT.fieldOf("wind_scale").forGetter(ParticleData::windScale),
			LeafMovementType.CODEC.fieldOf("movement_type").forGetter(ParticleData::movementType)
		).apply(instance, ParticleData::new)).validate(ParticleData::validate);

		private static DataResult<ParticleData> validate(ParticleData particleData) {
			return particleData.spawnChanceModifier
				.filter(modifier -> !modifier.type().isAssignableFrom(Integer.class))
				.<DataResult<ParticleData>>map(modifier -> DataResult.error(() -> "Falling Leaf Data particle chance modifier" + modifier.id() + "is not of type Integer!"))
				.orElseGet(() -> DataResult.success(particleData));
		}

		public static ParticleData forLeaves(
			Holder<ParticleType<?>> particle,
			Block leavesBlock,
			boolean cancelsVanillaParticles,
			float spawnChance,
			ConfigEntry<Integer> spawnChanceModifier,
			int textureSize,
			float gravityScale,
			float windScale,
			LeafMovementType movementType
		) {
			return new ParticleData(
				particle,
				Optional.of(leavesBlock.builtInRegistryHolder()),
				cancelsVanillaParticles,
				spawnChance,
				Optional.of(new ConfigEntryGetter<>(spawnChanceModifier)),
				textureSize,
				gravityScale,
				windScale,
				movementType
			);
		}

		public static ParticleData forLeaves(
			Holder<ParticleType<?>> particle,
			Block leavesBlock,
			float spawnChance,
			ConfigEntry<Integer> spawnChanceModifier,
			int textureSize,
			float gravityScale,
			float windScale,
			LeafMovementType movementType
		) {
			return forLeaves(particle, leavesBlock, true, spawnChance, spawnChanceModifier, textureSize, gravityScale, windScale, movementType);
		}

		public static ParticleData forLeafLitter(
			Holder<ParticleType<?>> particle,
			Block leafLitterBlock,
			int textureSize,
			float gravityScale,
			float windScale,
			LeafMovementType movementType
		) {
			return new ParticleData(
				particle,
				Optional.of(leafLitterBlock.builtInRegistryHolder()),
				true,
				1F,
				Optional.empty(),
				textureSize,
				gravityScale,
				windScale,
				movementType
			);
		}

		public float getLeafParticleSpawnChance() {
			return this.spawnChance * this.spawnChanceModifier.map(getter -> ((Integer) getter.get()) * 0.01F).orElse(1F);
		}

		public void animateTick(Level level, BlockPos pos, RandomSource random) {
			if (!WWAmbienceAndMiscConfig.USE_WILDER_WILD_FALLING_LEAVES.get()) return;
			if (random.nextFloat() > this.getLeafParticleSpawnChance()) return;

			final BlockPos belowPos = pos.below();
			final BlockState belowState = level.getBlockState(belowPos);
			if (Block.isFaceFull(belowState.getCollisionShape(level, belowPos), Direction.UP)) return;

			ParticleUtils.spawnParticleBelow(level, pos, random, this.createLeafParticleOptions());
		}

		public WWFallingLeavesParticleOptions createLeafParticleOptions(Function<Float, Float> gravityModifier) {
			return WWFallingLeavesParticleOptions.create(
				this.particle,
				this.originBlock,
				this.textureSize,
				gravityModifier.apply(this.gravityScale),
				this.windScale,
				this.movementType
			);
		}

		public WWFallingLeavesParticleOptions createLeafParticleOptions() {
			return this.createLeafParticleOptions(gravity -> gravity);
		}

		public WWFallingLeavesParticleOptions createLeafParticleOptions(Vec3 velocity, boolean useGroundSupportingMovement, Function<Float, Float> gravityModifier) {
			return WWFallingLeavesParticleOptions.createControlledVelocity(
				this.particle,
				this.originBlock,
				velocity,
				this.textureSize,
				gravityModifier.apply(this.gravityScale),
				this.windScale,
				!useGroundSupportingMovement ? this.movementType : this.movementType.getGroundSupportingEquivalent()
			);
		}

		public WWFallingLeavesParticleOptions createLeafParticleOptions(Vec3 velocity, Function<Float, Float> gravityModifier) {
			return this.createLeafParticleOptions(velocity, false, gravityModifier);
		}

		public WWFallingLeavesParticleOptions createLeafParticleOptions(Vec3 velocity) {
			return this.createLeafParticleOptions(velocity, gravity -> gravity);
		}
	}

	public record FallingLeafLitterData(
		Holder<Block> leafLitterBlock,
		float fallChance,
		float placementOnLandChance,
		Optional<Holder<SoundEvent>> fallSound,
		Optional<Holder<SoundEvent>> landSound
	) {
		public static final Codec<FallingLeafLitterData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			RegistryFixedCodec.create(Registries.BLOCK).fieldOf("block").forGetter(FallingLeafLitterData::leafLitterBlock),
			ExtraCodecs.floatRange(0F, 1F).optionalFieldOf("fall_chance", 0.25F).forGetter(FallingLeafLitterData::fallChance),
			ExtraCodecs.floatRange(0F, 1F).optionalFieldOf("placement_on_land_chance", 0F).forGetter(FallingLeafLitterData::fallChance),
			RegistryFixedCodec.create(Registries.SOUND_EVENT).optionalFieldOf("fall_sound").forGetter(FallingLeafLitterData::fallSound),
			RegistryFixedCodec.create(Registries.SOUND_EVENT).optionalFieldOf("land_sound").forGetter(FallingLeafLitterData::landSound)
		).apply(instance, FallingLeafLitterData::new));

		public FallingLeafLitterData(Holder<Block> leafLitterBlock, float fallChance, float placementOnLandChance, Holder<SoundEvent> landSound) {
			this(leafLitterBlock, fallChance, placementOnLandChance, Optional.empty(), Optional.of(landSound));
		}

		public FallingLeafLitterData(Holder<Block> leafLitterBlock, float fallChance, Holder<SoundEvent> landSound) {
			this(leafLitterBlock, fallChance, 0.25F, landSound);
		}

		public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, FallingLeafData owner) {
			final BlockPos belowPos = pos.below();
			final BlockState belowState = level.getBlockState(belowPos);
			if (Block.isFaceFull(belowState.getCollisionShape(level, belowPos), Direction.UP)) return;
			if (random.nextFloat() > this.fallChance) return;

			this.fallSound.ifPresent(sound -> level.playSound(null, pos, sound.value(), SoundSource.BLOCKS, 0.3F, 1F));
			level.sendParticles(
				new BlockParticleOption(ParticleTypes.BLOCK, state),
				pos.getX() + 0.5D,
				pos.getY() - 0.1D,
				pos.getZ() + 0.5D,
				random.nextInt(12, 24),
				0.3D, 0D, 0.3D,
				0.05D
			);

			FallingLeafUtil.sendLeafClusterParticle(level, pos, owner);

			FallingLeafTicker.createAndSpawn(
				WWEntityTypes.FALLING_LEAVES.get(),
				level,
				pos,
				this.leafLitterBlock.value(),
				this.placementOnLandChance
			);
		}
	}

	public enum LeafMovementType implements StringRepresentable {
		NONE(0, "none", false, false, false),
		SWIRL(1, "swirl", true, false, false),
		FLOW_AWAY(2, "flow_away", false, true, false),
		SWIRL_AND_FLOW_AWAY(3, "swirl_and_flow_away", true, true, false),
		BOUNCE(4, "bounce", false, false, true),
		SWIRL_AND_BOUNCE(5, "swirl_and_bounce", true, false, true),
		FLOW_AWAY_AND_BOUNCE(6, "flow_away_and_bounce", true, false, true),
		SWIRL_AND_FLOW_AWAY_AND_BOUNCE(7, "swirl_and_flow_away_and_bounce", true, true, true);
		public static final Codec<LeafMovementType> CODEC = StringRepresentable.fromEnum(LeafMovementType::values);
		private static final IntFunction<LeafMovementType> BY_ID = ByIdMap.continuous(LeafMovementType::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
		public static final StreamCodec<ByteBuf, LeafMovementType> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, LeafMovementType::getId);
		private final int id;
		private final String name;
		private final boolean swirl;
		private final boolean flowAway;
		private final boolean bounceOnFloor;

		LeafMovementType(int id, String name, boolean swirl, boolean flowAway, boolean bounceOnFloor) {
			this.id = id;
			this.name = name;
			this.swirl = swirl;
			this.flowAway = flowAway;
			this.bounceOnFloor = bounceOnFloor;
		}

		public LeafMovementType getGroundSupportingEquivalent() {
			if (this == LeafMovementType.NONE) return LeafMovementType.BOUNCE;
			if (this == LeafMovementType.SWIRL) return LeafMovementType.SWIRL_AND_BOUNCE;
			if (this == LeafMovementType.FLOW_AWAY) return LeafMovementType.FLOW_AWAY_AND_BOUNCE;
			if (this == LeafMovementType.SWIRL_AND_FLOW_AWAY) return LeafMovementType.SWIRL_AND_FLOW_AWAY_AND_BOUNCE;
			return this;
		}

		public int getId() {
			return this.id;
		}

		public boolean swirl() {
			return this.swirl;
		}

		public boolean flowAway() {
			return this.flowAway;
		}

		public boolean bounceOnFloor() {
			return this.bounceOnFloor;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}
}
