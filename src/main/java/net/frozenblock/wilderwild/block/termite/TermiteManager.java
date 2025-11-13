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

package net.frozenblock.wilderwild.block.termite;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import net.frozenblock.wilderwild.advancement.TermiteEatTrigger;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWCriteria;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TermiteManager {
	public static final int TERMITE_COUNT_ASLEEP = 1;
	public static final int TERMITE_COUNT_ASLEEP_NATURAL = 0;
	public static final int TERMITE_COUNT = 3;
	public static final int PARTICLE_COUNT_WHILE_EATING = 4;
	public static final int PARTICLE_COUNT = 6;
	public static final float BLOCK_SOUND_VOLUME = 0.6F;
	private final ArrayList<Termite> termites = new ArrayList<>();
	public int ticksToNextTermite;
	public int highestID;

	public TermiteManager() {
	}

	public static int maxTermites(boolean natural, boolean awake, boolean canSpawn) {
		if (!canSpawn) return 0;
		if (!awake) return natural ? TERMITE_COUNT_ASLEEP_NATURAL : TERMITE_COUNT_ASLEEP;
		return TERMITE_COUNT;
	}

	public static boolean areTermitesSafe(@NotNull LevelReader level, @NotNull BlockPos pos) {
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		for (Direction direction : Direction.values()) {
			if (!isPosSafeForTermites(level, mutable.setWithOffset(pos, direction))) return false;
		}
		return true;
	}

	public static boolean isPosSafeForTermites(@NotNull LevelReader level, @NotNull BlockPos pos) {
		return isStateSafeForTermites(level.getBlockState(pos));
	}

	public static boolean isStateSafeForTermites(@NotNull BlockState state) {
		return !state.is(WWBlockTags.KILLS_TERMITE) && state.getFluidState().isEmpty();
	}

	public void addTermite(@NotNull BlockPos pos) {
		Termite termite = new Termite(pos, pos, 0, 0, 0, false, this.highestID += 1);
		this.termites.add(termite);
	}

	public void tick(@NotNull Level level, @NotNull BlockPos pos, boolean natural, boolean awake, boolean canSpawn, Runnable onTermitesUpdated) {
		final int maxTermites = maxTermites(natural, awake, canSpawn);
		final AtomicBoolean termitesUpdated = new AtomicBoolean();
		final RandomSource random = level.getRandom();

		this.termites.removeIf(termite -> {
			if (termite.tick(level, natural, random)) {
				if (level instanceof ServerLevel serverLevel) {
					final BlockPos termitePos = termite.getPos();
					serverLevel.sendParticles(
						WWParticleTypes.TERMITE,
						termitePos.getX() + 0.5D, termitePos.getY() + 0.5D, termitePos.getZ() + 0.5D,
						termite.eating ? PARTICLE_COUNT_WHILE_EATING : PARTICLE_COUNT,
						0D, 0D, 0D, 0D
					);
					termitesUpdated.set(true);
				}
			} else {
				level.playSound(null, termite.pos, WWSounds.BLOCK_TERMITE_MOUND_ENTER, SoundSource.NEUTRAL, BLOCK_SOUND_VOLUME, 1F);
				level.gameEvent(null, GameEvent.BLOCK_CHANGE, Vec3.atCenterOf(pos));
				termitesUpdated.set(true);
				return true;
			}
			return false;
		});

		if (this.termites.size() < maxTermites) {
			if (this.ticksToNextTermite > 0) {
				--this.ticksToNextTermite;
			} else {
				this.addTermite(pos);
				level.gameEvent(null, GameEvent.BLOCK_CHANGE, Vec3.atCenterOf(pos));
				level.playSound(null, pos, WWSounds.BLOCK_TERMITE_MOUND_EXIT, SoundSource.NEUTRAL, BLOCK_SOUND_VOLUME, 1F);
				this.ticksToNextTermite = natural ? TERMITE_RELEASE_COUNTDOWN_NATURAL : TERMITE_RELEASE_COUNTDOWN;
				termitesUpdated.set(true);
			}
		}
		while (this.termites.size() > maxTermites) {
			Termite termite = this.termites.get(random.nextInt(this.termites.size()));
			level.playSound(null, termite.pos, WWSounds.BLOCK_TERMITE_MOUND_ENTER, SoundSource.NEUTRAL, BLOCK_SOUND_VOLUME, 1F);
			this.termites.remove(termite);
			level.gameEvent(null, GameEvent.BLOCK_CHANGE, Vec3.atCenterOf(pos));
			termitesUpdated.set(true);
		}

		if (termitesUpdated.get()) onTermitesUpdated.run();
	}

	public static final int TERMITE_RELEASE_COUNTDOWN = 200;
	public static final int TERMITE_RELEASE_COUNTDOWN_NATURAL = 320;

	public void clearTermites(@NotNull Level level) {
		for (Termite termite : this.termites) {
			level.playSound(null, termite.pos, WWSounds.BLOCK_TERMITE_MOUND_ENTER, SoundSource.NEUTRAL, BLOCK_SOUND_VOLUME, 1F);
		}
		this.termites.clear();
	}

	public ArrayList<Termite> termites() {
		return this.termites;
	}

	public void saveAdditional(@NotNull ValueOutput valueOutput) {
		valueOutput.store("termites", Termite.LIST_CODEC, this.termites);
		valueOutput.putInt("ticksToNextTermite", this.ticksToNextTermite);
		valueOutput.putInt("highestID", this.highestID);
	}

	public void load(@NotNull ValueInput valueInput) {
		this.termites.clear();
		valueInput.read("termites", Termite.LIST_CODEC).ifPresent(this.termites::addAll);
		this.ticksToNextTermite = valueInput.getIntOr("ticksToNextTermite", 0);
		this.highestID = valueInput.getIntOr("highestID", 0);
	}

	public static class Termite {
		public static final int DESTROY_POWER_BEFORE_BLOCK_BREAKS = 200;
		public static final int DESTROY_POWER_LEAVES = 4;
		public static final int DESTROY_POWER_BREAKABLE = 2;
		public static final int DESTROY_POWER = 1;
		public static final int MAX_IDLE_TICKS = 2000;
		public static final int MAX_IDLE_TICKS_NATURAL = 1200;
		public static final int UPDATE_DELAY_IN_TICKS = 1;
		public static final int GNAW_PARTICLE_CHANCE = 4;
		public static final int MIN_GNAW_PARTICLES = 0;
		public static final int MAX_GNAW_PARTICLES = 3;
		public static final int MIN_EAT_PARTICLES = 18;
		public static final int MAX_EAT_PARTICLES = 25;
		public static final Codec<Termite> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			BlockPos.CODEC.fieldOf("mound").forGetter(Termite::getMoundPos),
			BlockPos.CODEC.fieldOf("pos").forGetter(Termite::getPos),
			Codec.intRange(0, DESTROY_POWER_BEFORE_BLOCK_BREAKS + 1).fieldOf("block_destroy_power").orElse(0).forGetter(Termite::getBlockDestroyPower),
			Codec.INT.fieldOf("idle_ticks").orElse(0).forGetter(Termite::getIdleTicks),
			Codec.INT.fieldOf("update_delay").orElse(0).forGetter(Termite::getUpdateTicks),
			Codec.BOOL.fieldOf("eating").orElse(true).forGetter(Termite::getEating),
			Codec.INT.fieldOf("id").orElse(0).forGetter(Termite::getID)
		).apply(instance, Termite::new));
		public static final Codec<List<Termite>> LIST_CODEC = CODEC.listOf();

		public BlockPos mound;
		public BlockPos pos;
		public int blockDestroyPower;
		public int idleTicks;
		public int update;
		public boolean eating;
		public int id;

		public Termite(@NotNull BlockPos mound, @NotNull BlockPos pos, int blockDestroyPower, int aliveTicks, int update) {
			this.mound = mound;
			this.pos = pos;
			this.blockDestroyPower = blockDestroyPower;
			this.idleTicks = aliveTicks;
			this.update = update;
		}

		public Termite(@NotNull BlockPos mound, @NotNull BlockPos pos, int blockDestroyPower, int aliveTicks, int update, boolean eating, int id) {
			this.mound = mound;
			this.pos = pos;
			this.blockDestroyPower = blockDestroyPower;
			this.idleTicks = aliveTicks;
			this.update = update;
			this.eating = eating;
			this.id = id;
		}

		public boolean tick(@NotNull Level level, boolean natural, RandomSource random) {
			boolean exit = false;
			++this.idleTicks;
			if (this.idleTicks > (natural ? MAX_IDLE_TICKS_NATURAL : MAX_IDLE_TICKS) || isTooFar(natural, this.mound, this.pos)) return false;
			if (!areTermitesSafe(level, this.pos)) return false;
			if (!isPosTickable(level, this.pos)) return exposedToAir(level, this.pos, natural);

			final BlockState state = level.getBlockState(this.pos);
			final Optional<Holder<TermiteBlockBehavior>> optionalBlockBehavior = TermiteBlockBehaviors.getTermiteBlockBehavior(
				level.registryAccess(),
				state.getBlock(),
				natural
			);

			if (optionalBlockBehavior.isPresent() && isEdibleProperty(state)) {
				final TermiteBlockBehavior termiteBlockBehavior = optionalBlockBehavior.get().value();
				final boolean destroysBlock = termiteBlockBehavior.destroysBlock();
				this.eating = true;
				exit = true;
				final int additionalPower = destroysBlock ? state.is(BlockTags.LEAVES) ? DESTROY_POWER_LEAVES : DESTROY_POWER_BREAKABLE : DESTROY_POWER;
				this.blockDestroyPower += additionalPower;
				spawnGnawParticles(level, state, this.pos, random);
				if (this.blockDestroyPower > DESTROY_POWER_BEFORE_BLOCK_BREAKS) {
					this.blockDestroyPower = 0;
					this.idleTicks = natural ? Math.max(0, this.idleTicks - (DESTROY_POWER_BEFORE_BLOCK_BREAKS / additionalPower)) : 0;

					// Must trigger criteria before destroying block in order to validate criteria properly.
					if (level instanceof ServerLevel serverLevel) {
						for (ServerPlayer serverPlayer : serverLevel.getPlayers(serverPlayerx -> serverPlayerx.distanceToSqr(this.getCenterOfPos()) < TermiteEatTrigger.TRIGGER_DISTANCE_FROM_PLAYER)) {
							WWCriteria.TERMITE_EAT.trigger(serverPlayer, serverLevel, this.pos, !natural);
						}
					}

					if (destroysBlock) {
						level.destroyBlock(this.pos, true);
					} else {
						level.addDestroyBlockEffect(this.pos, state);
						final Block setBlock = termiteBlockBehavior.getOutputBlock().get();
						final BlockState setState = termiteBlockBehavior.copyProperties() ? setBlock.withPropertiesOf(state) : setBlock.defaultBlockState();
						level.setBlockAndUpdate(this.pos, setState);
					}
					spawnEatParticles(level, state, this.pos, random);
					termiteBlockBehavior.getEatSound()
						.ifPresent(sound -> level.playSound(null, this.pos, sound, SoundSource.BLOCKS, BLOCK_SOUND_VOLUME, 0.9F + (random.nextFloat() * 0.25F)));
					level.playSound(null, this.pos, WWSounds.BLOCK_TERMITE_MOUND_TERMITE_GNAW_FINISH, SoundSource.BLOCKS, BLOCK_SOUND_VOLUME, 0.9F + (random.nextFloat() * 0.25F));
				}
			} else {
				this.eating = false;
				this.blockDestroyPower = 0;
				Direction direction = Direction.getRandom(random);
				if (state.isAir()) direction = Direction.DOWN;
				final BlockPos offsetPos = this.pos.relative(direction);
				final BlockState offsetState = level.getBlockState(offsetPos);
				if (!isStateSafeForTermites(offsetState)) return false;

				if (this.update > 0 && !state.isAir()) {
					--this.update;
					return true;
				} else {
					this.update = UPDATE_DELAY_IN_TICKS;
					final BlockPos priority = degradableBreakablePos(level, this.pos, natural, random);
					if (priority != null) {
						this.pos = priority;
						exit = true;
					} else {
						final BlockPos ledge = ledgePos(level, offsetPos, natural);
						final BlockPos abovePos = this.pos.above();
						final BlockState aboveState = level.getBlockState(abovePos);
						if (exposedToAir(level, offsetPos, natural)
							&& isBlockMovable(offsetState, direction)
							&& !(direction != Direction.DOWN && offsetState.isAir() && (!this.mound.closerThan(this.pos, 1.5D)) && ledge == null)
						) {
							this.pos = Objects.requireNonNullElse(ledge, offsetPos);
							exit = true;
						} else if (ledge != null && exposedToAir(level, ledge, natural)) {
							this.pos = ledge;
							exit = true;
						} else if (!aboveState.isAir() && isBlockMovable(aboveState, Direction.UP) && exposedToAir(level, abovePos, natural)) {
							this.pos = abovePos;
							exit = true;
						}
					}
				}
			}
			return exit || (exposedToAir(level, this.pos, natural));
		}

		@Nullable
		public static BlockPos ledgePos(@NotNull Level level, @NotNull BlockPos pos, boolean natural) {
			final BlockPos.MutableBlockPos mutable = pos.mutable();
			BlockState state = level.getBlockState(mutable);

			final Optional<Holder<TermiteBlockBehavior>> optionalBlockBehavior = TermiteBlockBehaviors.getTermiteBlockBehavior(
				level.registryAccess(),
				state.getBlock(),
				natural
			);
			if (optionalBlockBehavior.isPresent()) return mutable;

			mutable.move(Direction.DOWN);
			state = level.getBlockState(mutable);
			if (!state.isAir() && isBlockMovable(state, Direction.DOWN) && exposedToAir(level, mutable, natural)) return mutable.immutable();

			mutable.move(Direction.UP, 2);
			state = level.getBlockState(mutable);
			if (!state.isAir() && isBlockMovable(state, Direction.UP) && exposedToAir(level, mutable, natural)) return mutable.immutable();
			return null;
		}

		@Nullable
		public static BlockPos degradableBreakablePos(@NotNull Level level, @NotNull BlockPos pos, boolean natural, RandomSource random) {
			final BlockPos.MutableBlockPos mutable = pos.mutable();
			final List<Direction> directions = Util.shuffledCopy(Direction.values(), random);
			final BlockState aboveState = level.getBlockState(mutable.move(Direction.UP));
			if (checkIfBlockIsEdibleAndFixPos(level, natural, mutable, aboveState)) return mutable.immutable();

			for (Direction direction : directions) {
				final BlockState state = level.getBlockState(mutable.setWithOffset(pos, direction));
				if (checkIfBlockIsEdibleAndFixPos(level, natural, mutable, state)) return mutable.immutable();
			}

			return null;
		}

		private static boolean checkIfBlockIsEdibleAndFixPos(
			@NotNull Level level, boolean natural, @NotNull BlockPos.MutableBlockPos mutableBlockPos, @NotNull BlockState state
		) {
			final Optional<Holder<TermiteBlockBehavior>> optionalBlockBehavior = TermiteBlockBehaviors.getTermiteBlockBehavior(
				level.registryAccess(),
				state.getBlock(),
				natural
			);

			if (optionalBlockBehavior.isEmpty() || !isEdibleProperty(state)) return false;
			if (state.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF) && state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER) {
				mutableBlockPos.move(Direction.DOWN);
			}
			return true;
		}

		public static boolean isEdibleProperty(@NotNull BlockState state) {
			return !WWBlockConfig.get().termite.onlyEatNaturalBlocks
				|| (state.hasProperty(WWBlockStateProperties.TERMITE_EDIBLE)
				? state.getValue(WWBlockStateProperties.TERMITE_EDIBLE)
				: (!state.is(BlockTags.LEAVES) || !state.hasProperty(BlockStateProperties.PERSISTENT) || !state.getValue(BlockStateProperties.PERSISTENT))
			);
		}

		public static boolean exposedToAir(@NotNull Level level, @NotNull BlockPos pos, boolean natural) {
			final BlockPos.MutableBlockPos mutable = pos.mutable();
			for (Direction direction : Direction.values()) {
				final BlockState state = level.getBlockState(mutable.move(direction));
				final Optional<Holder<TermiteBlockBehavior>> optionalBlockBehavior = TermiteBlockBehaviors.getTermiteBlockBehavior(
					level.registryAccess(),
					state.getBlock(),
					natural
				);

				if (state.isAir()
					|| (!state.isRedstoneConductor(level, mutable) && !state.is(WWBlockTags.BLOCKS_TERMITE))
					|| (optionalBlockBehavior.isPresent() && isEdibleProperty(state))) {
					return true;
				}
				mutable.move(direction, -1);
			}
			return false;
		}

		private static boolean isPosTickable(@NotNull LevelAccessor level, @NotNull BlockPos pos) {
			if (level instanceof ServerLevel serverLevel) return serverLevel.shouldTickBlocksAt(pos);
			return false;
		}

		public static boolean isBlockMovable(@NotNull BlockState state, @NotNull Direction direction) {
			if (state.is(WWBlockTags.BLOCKS_TERMITE)) return false;
			boolean moveableUp = !(direction == Direction.UP && (state.is(BlockTags.INSIDE_STEP_SOUND_BLOCKS) || state.is(BlockTags.REPLACEABLE_BY_TREES) || state.is(BlockTags.FLOWERS)));
			boolean moveableDown = !(direction == Direction.DOWN && !state.getFluidState().isEmpty());
			return moveableUp && moveableDown;
		}

		public static boolean isTooFar(boolean natural, @NotNull BlockPos mound, @NotNull BlockPos pos) {
			return !mound.closerThan(pos, natural ? WWBlockConfig.get().termite.maxNaturalDistance : WWBlockConfig.get().termite.maxDistance);
		}

		public static void spawnGnawParticles(@NotNull Level level, @NotNull BlockState eatState, @NotNull BlockPos pos, RandomSource random) {
			if (!(level instanceof ServerLevel serverLevel) || random.nextInt(GNAW_PARTICLE_CHANCE) != 0) return;
			final int count = random.nextInt(MIN_GNAW_PARTICLES, MAX_GNAW_PARTICLES);
			if (count <= 0) return;
			serverLevel.sendParticles(
				new BlockParticleOption(ParticleTypes.BLOCK, eatState),
				pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
				count,
				0.3F, 0.3F, 0.3F,
				0.05D
			);
		}

		public static void spawnEatParticles(@NotNull Level level, @NotNull BlockState eatState, @NotNull BlockPos pos, RandomSource random) {
			if (!(level instanceof ServerLevel serverLevel)) return;
			serverLevel.sendParticles(
				new BlockParticleOption(ParticleTypes.BLOCK, eatState),
				pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
				random.nextInt(MIN_EAT_PARTICLES, MAX_EAT_PARTICLES),
				0.3F, 0.3F, 0.3F,
				0.05D
			);
		}

		@NotNull
		public BlockPos getMoundPos() {
			return this.mound;
		}

		@NotNull
		public BlockPos getPos() {
			return this.pos;
		}

		@NotNull
		public Vec3 getCenterOfPos() {
			return Vec3.atCenterOf(this.pos);
		}

		public int getBlockDestroyPower() {
			return this.blockDestroyPower;
		}

		public int getIdleTicks() {
			return this.idleTicks;
		}

		public int getUpdateTicks() {
			return this.update;
		}

		public boolean getEating() {
			return this.eating;
		}

		public int getID() {
			return this.id;
		}

	}
}
