/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.block.termite;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TermiteManager {
	public static final int TERMITE_COUNT_ASLEEP = 1;
	public static final int TERMITE_COUNT_ASLEEP_NATURAL = 0;
	public static final int TERMITE_COUNT = 5;
	public static final int TERMITE_COUNT_NATURAL = 3;
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
		return natural ? TERMITE_COUNT_NATURAL : TERMITE_COUNT;
	}

	public static boolean areTermitesSafe(@NotNull LevelReader level, @NotNull BlockPos pos) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
		for (Direction direction : Direction.values()) {
			if (!isPosSafeForTermites(level, mutableBlockPos.move(direction))) {
				return false;
			}
			mutableBlockPos.move(direction, -1);
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

	public void tick(@NotNull Level level, @NotNull BlockPos pos, boolean natural, boolean awake, boolean canSpawn) {
		int maxTermites = maxTermites(natural, awake, canSpawn);
		RandomSource random = level.getRandom();

		this.termites.removeIf(termite -> {
			if (termite.tick(level, natural, random)) {
				if (level instanceof ServerLevel serverLevel) {
					BlockPos termitePos = termite.getPos();
					serverLevel.sendParticles(
						WWParticleTypes.TERMITE,
						termitePos.getX() + 0.5D,
						termitePos.getY() + 0.5D,
						termitePos.getZ() + 0.5D,
						termite.eating ? PARTICLE_COUNT_WHILE_EATING : PARTICLE_COUNT,
						0D,
						0D,
						0D,
						0D
					);
				}
			} else {
				level.playSound(null, termite.pos, WWSounds.BLOCK_TERMITE_MOUND_ENTER, SoundSource.NEUTRAL, BLOCK_SOUND_VOLUME, 1F);
				level.gameEvent(null, GameEvent.BLOCK_CHANGE, Vec3.atCenterOf(pos));
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
			}
		}
		while (this.termites.size() > maxTermites) {
			Termite termite = this.termites.get(random.nextInt(this.termites.size()));
			level.playSound(null, termite.pos, WWSounds.BLOCK_TERMITE_MOUND_ENTER, SoundSource.NEUTRAL, BLOCK_SOUND_VOLUME, 1F);
			this.termites.remove(termite);
			level.gameEvent(null, GameEvent.BLOCK_CHANGE, Vec3.atCenterOf(pos));
		}
	}
	public static final int TERMITE_RELEASE_COUNTDOWN = 200;
	public static final int TERMITE_RELEASE_COUNTDOWN_NATURAL = 320;

	public void clearTermites(@NotNull Level level) {
		for (Termite termite : this.termites) {
			level.gameEvent(null, GameEvent.ENTITY_DIE, Vec3.atCenterOf(termite.pos));
			level.playSound(null, termite.pos, WWSounds.BLOCK_TERMITE_MOUND_ENTER, SoundSource.NEUTRAL, BLOCK_SOUND_VOLUME, 1F);
		}
		this.termites.clear();
	}

	public ArrayList<Termite> termites() {
		return this.termites;
	}

	public void saveAdditional(@NotNull CompoundTag tag) {
		Termite.LIST_CODEC
			.encodeStart(NbtOps.INSTANCE, this.termites)
			.resultOrPartial(WWConstants.LOGGER::error)
			.ifPresent((nbt) -> tag.put("termites", nbt));
		tag.putInt("ticksToNextTermite", this.ticksToNextTermite);
		tag.putInt("highestID", this.highestID);
	}

	public void load(@NotNull CompoundTag tag) {
		if (tag.contains("termites", 9)) {
			this.termites.clear();
			Termite.LIST_CODEC
				.parse(new Dynamic<>(NbtOps.INSTANCE, tag.getList("termites", 10)))
				.resultOrPartial(WWConstants.LOGGER::error)
				.ifPresent(this.termites::addAll);
		}
		this.ticksToNextTermite = tag.getInt("ticksToNextTermite");
		this.highestID = tag.getInt("highestID");
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
			if (this.idleTicks > (natural ? MAX_IDLE_TICKS_NATURAL : MAX_IDLE_TICKS) || isTooFar(natural, this.mound, this.pos)) {
				return false;
			}
			if (!areTermitesSafe(level, this.pos)) {
				return false;
			}
			if (isPosTickable(level, this.pos)) {
				BlockState blockState = level.getBlockState(this.pos);
				Optional<Holder<TermiteBlockBehavior>> optionalBlockBehavior = TermiteBlockBehaviors.getTermiteBlockBehavior(
					level.registryAccess(),
					blockState.getBlock(),
					natural
				);

				if (optionalBlockBehavior.isPresent()) {
					TermiteBlockBehavior termiteBlockBehavior = optionalBlockBehavior.get().value();
					boolean destroysBlock = termiteBlockBehavior.destroysBlock();
					if (isEdibleProperty(blockState)) {
						this.eating = true;
						exit = true;
						int additionalPower = destroysBlock ? blockState.is(BlockTags.LEAVES) ? DESTROY_POWER_LEAVES : DESTROY_POWER_BREAKABLE : DESTROY_POWER;
						this.blockDestroyPower += additionalPower;
						spawnGnawParticles(level, blockState, this.pos, random);
						if (this.blockDestroyPower > DESTROY_POWER_BEFORE_BLOCK_BREAKS) {
							this.blockDestroyPower = 0;
							this.idleTicks = natural ? Math.max(0, this.idleTicks - (DESTROY_POWER_BEFORE_BLOCK_BREAKS / additionalPower)) : 0;
							if (destroysBlock) {
								level.destroyBlock(this.pos, true);
							} else {
								level.addDestroyBlockEffect(this.pos, blockState);
								Block setBlock = termiteBlockBehavior.getOutputBlock().get();
								BlockState setState = termiteBlockBehavior.copyProperties() ? setBlock.withPropertiesOf(blockState) : setBlock.defaultBlockState();
								level.setBlockAndUpdate(this.pos, setState);
							}
							spawnEatParticles(level, blockState, this.pos, random);
							termiteBlockBehavior.getEatSound()
								.ifPresent(sound -> level.playSound(null, this.pos, sound, SoundSource.BLOCKS, BLOCK_SOUND_VOLUME, 0.9F + (random.nextFloat() * 0.25F)));
							level.playSound(null, this.pos, WWSounds.BLOCK_TERMITE_MOUND_TERMITE_GNAW_FINISH, SoundSource.BLOCKS, BLOCK_SOUND_VOLUME, 0.9F + (random.nextFloat() * 0.25F));
						}
					}
				} else {
					this.eating = false;
					this.blockDestroyPower = 0;
					Direction direction = Direction.getRandom(random);
					if (blockState.isAir()) {
						direction = Direction.DOWN;
					}
					BlockPos offset = this.pos.relative(direction);
					BlockState state = level.getBlockState(offset);
					if (!isStateSafeForTermites(state)) {
						return false;
					}

					if (this.update > 0 && !blockState.isAir()) {
						--this.update;
						return true;
					} else {
						this.update = UPDATE_DELAY_IN_TICKS;
						BlockPos priority = degradableBreakablePos(level, this.pos, natural, random);
						if (priority != null) {
							this.pos = priority;
							exit = true;
						} else {
							BlockPos ledge = ledgePos(level, offset, natural);
							BlockPos posUp = this.pos.above();
							BlockState stateUp = level.getBlockState(posUp);
							if (exposedToAir(level, offset, natural)
								&& isBlockMovable(state, direction)
								&& !(direction != Direction.DOWN && state.isAir() && (!this.mound.closerThan(this.pos, 1.5D)) && ledge == null)
							) {
								this.pos = Objects.requireNonNullElse(ledge, offset);
								exit = true;
							} else if (ledge != null && exposedToAir(level, ledge, natural)) {
								this.pos = ledge;
								exit = true;
							} else if (!stateUp.isAir() && isBlockMovable(stateUp, Direction.UP) && exposedToAir(level, posUp, natural)) {
								this.pos = posUp;
								exit = true;
							}
						}
					}
				}
			}
			return exit || (exposedToAir(level, this.pos, natural));
		}

		@Nullable
		public static BlockPos ledgePos(@NotNull Level level, @NotNull BlockPos pos, boolean natural) {
			BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
			BlockState state = level.getBlockState(mutableBlockPos);

			Optional<Holder<TermiteBlockBehavior>> optionalBlockBehavior = TermiteBlockBehaviors.getTermiteBlockBehavior(
				level.registryAccess(),
				state.getBlock(),
				natural
			);

			if (optionalBlockBehavior.isPresent()) return mutableBlockPos;

			mutableBlockPos.move(Direction.DOWN);
			state = level.getBlockState(mutableBlockPos);
			if (!state.isAir() && isBlockMovable(state, Direction.DOWN) && exposedToAir(level, mutableBlockPos, natural)) {
				return mutableBlockPos.immutable();
			}
			mutableBlockPos.move(Direction.UP, 2);
			state = level.getBlockState(mutableBlockPos);
			if (!state.isAir() && isBlockMovable(state, Direction.UP) && exposedToAir(level, mutableBlockPos, natural)) {
				return mutableBlockPos.immutable();
			}
			return null;
		}

		@Nullable
		public static BlockPos degradableBreakablePos(@NotNull Level level, @NotNull BlockPos pos, boolean natural, RandomSource random) {
			BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
			List<Direction> directions = Util.shuffledCopy(Direction.values(), random);
			BlockState upState = level.getBlockState(mutableBlockPos.move(Direction.UP));
			if (checkIfBlockIsEdibleAndFixPos(level, natural, mutableBlockPos, upState)) return mutableBlockPos.immutable();

			mutableBlockPos.move(Direction.DOWN);
			for (Direction direction : directions) {
				BlockState state = level.getBlockState(mutableBlockPos.move(direction));
				if (checkIfBlockIsEdibleAndFixPos(level, natural, mutableBlockPos, state)) return mutableBlockPos.immutable();

				mutableBlockPos.move(direction, -1);
			}

			return null;
		}

		private static boolean checkIfBlockIsEdibleAndFixPos(
			@NotNull Level level, boolean natural, @NotNull BlockPos.MutableBlockPos mutableBlockPos, @NotNull BlockState state
		) {
			Optional<Holder<TermiteBlockBehavior>> optionalBlockBehavior = TermiteBlockBehaviors.getTermiteBlockBehavior(
				level.registryAccess(),
				state.getBlock(),
				natural
			);

			if (optionalBlockBehavior.isPresent() && isEdibleProperty(state)) {
				if (state.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF) && state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER) {
					mutableBlockPos.move(Direction.DOWN);
				}
				return true;
			}
			return false;
		}

		public static boolean isEdibleProperty(@NotNull BlockState state) {
			return !WWBlockConfig.get().termite.onlyEatNaturalBlocks
				|| (state.hasProperty(WWBlockStateProperties.TERMITE_EDIBLE)
				? state.getValue(WWBlockStateProperties.TERMITE_EDIBLE)
				: (!state.is(BlockTags.LEAVES) || !state.hasProperty(BlockStateProperties.PERSISTENT) || !state.getValue(BlockStateProperties.PERSISTENT))
			);
		}

		public static boolean exposedToAir(@NotNull Level level, @NotNull BlockPos pos, boolean natural) {
			BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
			for (Direction direction : Direction.values()) {
				BlockState state = level.getBlockState(mutableBlockPos.move(direction));
				Optional<Holder<TermiteBlockBehavior>> optionalBlockBehavior = TermiteBlockBehaviors.getTermiteBlockBehavior(
					level.registryAccess(),
					state.getBlock(),
					natural
				);

				if (
					state.isAir()
					|| (!state.isRedstoneConductor(level, mutableBlockPos) && !state.is(WWBlockTags.BLOCKS_TERMITE))
					|| (optionalBlockBehavior.isPresent() && isEdibleProperty(state))) {
					return true;
				}
				mutableBlockPos.move(direction, -1);
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
			if (level instanceof ServerLevel serverLevel && random.nextInt(GNAW_PARTICLE_CHANCE) == 0) {
				int count = random.nextInt(MIN_GNAW_PARTICLES, MAX_GNAW_PARTICLES);
				if (count > 0) {
					serverLevel.sendParticles(
						new BlockParticleOption(ParticleTypes.BLOCK, eatState),
						pos.getX() + 0.5D,
						pos.getY() + 0.5D,
						pos.getZ() + 0.5D,
						count,
						0.3F,
						0.3F,
						0.3F,
						0.05D
					);
				}
			}
		}

		public static void spawnEatParticles(@NotNull Level level, @NotNull BlockState eatState, @NotNull BlockPos pos, RandomSource random) {
			if (level instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(
					new BlockParticleOption(ParticleTypes.BLOCK, eatState),
					pos.getX() + 0.5D,
					pos.getY() + 0.5D,
					pos.getZ() + 0.5D,
					random.nextInt(MIN_EAT_PARTICLES, MAX_EAT_PARTICLES),
					0.3F,
					0.3F,
					0.3F,
					0.05D
				);
			}
		}

		@NotNull
		public BlockPos getMoundPos() {
			return this.mound;
		}

		@NotNull
		public BlockPos getPos() {
			return this.pos;
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
