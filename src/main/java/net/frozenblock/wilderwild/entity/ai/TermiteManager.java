/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.entity.ai;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import net.frozenblock.wilderwild.block.HollowedLogBlock;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

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
		if (!canSpawn) {
			return 0;
		}
		if (!awake) {
			return natural ? TERMITE_COUNT_ASLEEP_NATURAL : TERMITE_COUNT_ASLEEP;
		}
		return natural ? TERMITE_COUNT_NATURAL : TERMITE_COUNT;
	}

	public static boolean areTermitesSafe(@NotNull Level level, @NotNull BlockPos pos) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
		for (Direction direction : Direction.values()) {
			if (!isPosSafeForTermites(level, mutableBlockPos.move(direction))) {
				return false;
			}
			mutableBlockPos.move(direction, -1);
		}
		return true;
	}

	public static boolean isPosSafeForTermites(@NotNull LevelAccessor level, @NotNull BlockPos pos) {
		return isStateSafeForTermites(level.getBlockState(pos)) && level.getFluidState(pos).isEmpty();
	}

	public static boolean isPosSafeForTermites(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state) {
		return isStateSafeForTermites(state) && level.getFluidState(pos).isEmpty();
	}

	public static boolean isStateSafeForTermites(@NotNull BlockState state) {
		return !state.is(WilderBlockTags.KILLS_TERMITE) && (!state.hasProperty(BlockStateProperties.WATERLOGGED) || !state.getValue(BlockStateProperties.WATERLOGGED));
	}

	public void addTermite(@NotNull BlockPos pos) {
		Termite termite = new Termite(pos, pos, 0, 0, 0, false, this.highestID += 1);
		this.termites.add(termite);
	}

	public void tick(@NotNull Level level, @NotNull BlockPos pos, boolean natural, boolean awake, boolean canSpawn) {
		int maxTermites = maxTermites(natural, awake, canSpawn);
		ArrayList<Termite> termitesToRemove = new ArrayList<>();
		RandomSource random = level.getRandom();
		for (Termite termite : this.termites) {
			if (termite.tick(level, natural, random)) {
				if (level instanceof ServerLevel serverLevel) {
					BlockPos termitePos = termite.getPos();
					serverLevel.sendParticles(
						RegisterParticles.TERMITE,
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
				level.playSound(null, termite.pos, RegisterSounds.BLOCK_TERMITE_MOUND_ENTER, SoundSource.NEUTRAL, BLOCK_SOUND_VOLUME, 1F);
				termitesToRemove.add(termite);
			}
		}
		for (Termite termite : termitesToRemove) {
			level.gameEvent(null, GameEvent.ENTITY_DIE, Vec3.atCenterOf(termite.pos));
			this.termites.remove(termite);
			level.gameEvent(null, GameEvent.BLOCK_CHANGE, Vec3.atCenterOf(pos));
		}
		if (this.termites.size() < maxTermites) {
			if (this.ticksToNextTermite > 0) {
				--this.ticksToNextTermite;
			} else {
				this.addTermite(pos);
				level.gameEvent(null, GameEvent.BLOCK_CHANGE, Vec3.atCenterOf(pos));
				level.playSound(null, pos, RegisterSounds.BLOCK_TERMITE_MOUND_EXIT, SoundSource.NEUTRAL, BLOCK_SOUND_VOLUME, 1F);
				this.ticksToNextTermite = natural ? TERMITE_RELEASE_COUNTDOWN_NATURAL : TERMITE_RELEASE_COUNTDOWN;
			}
		}
		while (this.termites.size() > maxTermites) {
			Termite termite = this.termites.get(random.nextInt(this.termites.size()));
			level.playSound(null, termite.pos, RegisterSounds.BLOCK_TERMITE_MOUND_ENTER, SoundSource.NEUTRAL, BLOCK_SOUND_VOLUME, 1F);
			level.gameEvent(null, GameEvent.TELEPORT, Vec3.atCenterOf(termite.pos));
			this.termites.remove(termite);
			level.gameEvent(null, GameEvent.BLOCK_CHANGE, Vec3.atCenterOf(pos));
		}
		termitesToRemove.clear();
	}
	public static final int TERMITE_RELEASE_COUNTDOWN = 200;
	public static final int TERMITE_RELEASE_COUNTDOWN_NATURAL = 320;

	public void clearTermites(@NotNull Level level) {
		for (Termite termite : this.termites) {
			level.gameEvent(null, GameEvent.ENTITY_DIE, Vec3.atCenterOf(termite.pos));
			level.playSound(null, termite.pos, RegisterSounds.BLOCK_TERMITE_MOUND_ENTER, SoundSource.NEUTRAL, BLOCK_SOUND_VOLUME, 1F);
		}
		this.termites.clear();
	}

	public ArrayList<Termite> termites() {
		return this.termites;
	}

	public void saveAdditional(@NotNull CompoundTag tag) {
		tag.putInt("ticksToNextTermite", this.ticksToNextTermite);
		tag.putInt("highestID", this.highestID);
		Logger logger = WilderSharedConstants.LOGGER;
		DataResult<Tag> var10000 = Termite.CODEC.listOf().encodeStart(NbtOps.INSTANCE, this.termites);
		Objects.requireNonNull(logger);
		var10000.resultOrPartial(logger::error).ifPresent((nbt) -> tag.put("termites", nbt));
	}

	public void load(@NotNull CompoundTag tag) {
		this.ticksToNextTermite = tag.getInt("ticksToNextTermite");
		this.highestID = tag.getInt("highestID");
		if (tag.contains("termites", 9)) {
			this.termites.clear();
			DataResult<List<Termite>> var10000 = Termite.CODEC.listOf().parse(new Dynamic<>(NbtOps.INSTANCE, tag.getList("termites", 10)));
			Logger logger = WilderSharedConstants.LOGGER;
			Objects.requireNonNull(logger);
			Optional<List<Termite>> list = var10000.resultOrPartial(logger::error);
			if (list.isPresent()) {
				List<Termite> termiteList = list.get();
				this.termites.addAll(termiteList);
			}
		}
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

		public static final Map<Block, Block> DEGRADABLE_BLOCKS = new Object2ObjectOpenHashMap<>();
		public static final Map<Block, Block> NATURAL_DEGRADABLE_BLOCKS = new Object2ObjectOpenHashMap<>();

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
			if (canMove(level, this.pos)) {
				BlockState blockState = level.getBlockState(this.pos);
				Block block = blockState.getBlock();
				boolean degradable = !natural ? DEGRADABLE_BLOCKS.containsKey(block) : NATURAL_DEGRADABLE_BLOCKS.containsKey(block);
				boolean breakable = blockState.is(WilderBlockTags.TERMITE_BREAKABLE);
				boolean leaves = blockState.is(BlockTags.LEAVES);
				if ((degradable || breakable) && isEdibleProperty(blockState)) {
					this.eating = true;
					exit = true;
					int additionalPower = breakable ? leaves ? DESTROY_POWER_LEAVES : DESTROY_POWER_BREAKABLE : DESTROY_POWER;
					this.blockDestroyPower += additionalPower;
					spawnGnawParticles(level, blockState, this.pos, random);
					if (this.blockDestroyPower > DESTROY_POWER_BEFORE_BLOCK_BREAKS) {
						this.blockDestroyPower = 0;
						this.idleTicks = natural ? Math.max(0, this.idleTicks - (DESTROY_POWER_BEFORE_BLOCK_BREAKS / additionalPower)) : 0;
						if (breakable) {
							level.destroyBlock(this.pos, true);
						} else {
							level.addDestroyBlockEffect(this.pos, blockState);
							Block setBlock = !natural ? DEGRADABLE_BLOCKS.get(block) : NATURAL_DEGRADABLE_BLOCKS.get(block);
							BlockState setState = setBlock.withPropertiesOf(blockState);
							level.setBlockAndUpdate(this.pos, setState);
							if (setBlock instanceof HollowedLogBlock) {
								boolean nether = new ItemStack(setBlock.asItem()).is(ItemTags.NON_FLAMMABLE_WOOD);
								level.playSound(null, pos, nether ? RegisterSounds.STEM_HOLLOWED : RegisterSounds.LOG_HOLLOWED, SoundSource.BLOCKS, BLOCK_SOUND_VOLUME, 0.95F + (random.nextFloat() * 0.2F));
							}
						}
						spawnEatParticles(level, blockState, this.pos, random);
						level.playSound(null, pos, RegisterSounds.BLOCK_TERMITE_MOUND_TERMITE_GNAW_FINISH, SoundSource.BLOCKS, BLOCK_SOUND_VOLUME, 0.9F + (random.nextFloat() * 0.25F));
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
								this.pos = offset;
								if (ledge != null) {
									this.pos = ledge;
								}
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
			if (DEGRADABLE_BLOCKS.containsKey(state.getBlock()) || state.is(WilderBlockTags.TERMITE_BREAKABLE)) {
				return mutableBlockPos;
			}
			mutableBlockPos.move(Direction.DOWN);
			state = level.getBlockState(mutableBlockPos);
			if (!state.isAir() && isBlockMovable(state, Direction.DOWN) && exposedToAir(level, mutableBlockPos, natural)) {
				return mutableBlockPos;
			}
			mutableBlockPos.move(Direction.UP, 2);
			state = level.getBlockState(mutableBlockPos);
			if (!state.isAir() && isBlockMovable(state, Direction.UP) && exposedToAir(level, mutableBlockPos, natural)) {
				return mutableBlockPos;
			}
			return null;
		}

		@Nullable
		public static BlockPos degradableBreakablePos(@NotNull Level level, @NotNull BlockPos pos, boolean natural, RandomSource random) {
			BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
			List<Direction> directions = Util.shuffledCopy(Direction.values(), random);
			BlockState upState = level.getBlockState(mutableBlockPos.move(Direction.UP));
			if (canEatBlock(natural, mutableBlockPos, upState)) return mutableBlockPos;
			mutableBlockPos.move(Direction.DOWN);
			for (Direction direction : directions) {
				BlockState state = level.getBlockState(mutableBlockPos.move(direction));
				if (canEatBlock(natural, mutableBlockPos, state)) return mutableBlockPos;
				mutableBlockPos.move(direction, -1);
			}
			return null;
		}

		private static boolean canEatBlock(boolean natural, @NotNull BlockPos.MutableBlockPos mutableBlockPos, @NotNull BlockState state) {
			if (((!natural ? DEGRADABLE_BLOCKS.containsKey(state.getBlock()) : NATURAL_DEGRADABLE_BLOCKS.containsKey(state.getBlock())) || state.is(WilderBlockTags.TERMITE_BREAKABLE)) && isEdibleProperty(state)) {
				if (state.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF) && state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER) {
					mutableBlockPos.move(Direction.DOWN);
				}
				return true;
			}
			return false;
		}

		public static boolean isEdibleProperty(@NotNull BlockState state) {
			return !BlockConfig.get().termite.onlyEatNaturalBlocks() || (state.hasProperty(RegisterProperties.TERMITE_EDIBLE) ? state.getValue(RegisterProperties.TERMITE_EDIBLE) : !state.is(BlockTags.LEAVES) || !state.hasProperty(BlockStateProperties.PERSISTENT) || !state.getValue(BlockStateProperties.PERSISTENT));
		}

		public static boolean exposedToAir(@NotNull Level level, @NotNull BlockPos pos, boolean natural) {
			BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
			for (Direction direction : Direction.values()) {
				BlockState state = level.getBlockState(mutableBlockPos.move(direction));
				if (state.isAir() || (!state.isRedstoneConductor(level, mutableBlockPos) && !state.is(WilderBlockTags.BLOCKS_TERMITE)) || ((!natural && DEGRADABLE_BLOCKS.containsKey(state.getBlock())) || (natural && NATURAL_DEGRADABLE_BLOCKS.containsKey(state.getBlock())) || state.is(WilderBlockTags.TERMITE_BREAKABLE)) && isEdibleProperty(state)) {
					return true;
				}
				mutableBlockPos.move(direction, -1);
			}
			return false;
		}

		public static boolean canMove(@NotNull LevelAccessor level, @NotNull BlockPos pos) {
			if (level instanceof ServerLevel serverLevel) {
				return serverLevel.shouldTickBlocksAt(pos);
			}
			return false;
		}

		public static boolean isBlockMovable(@NotNull BlockState state, @NotNull Direction direction) {
			if (state.is(WilderBlockTags.BLOCKS_TERMITE)) {
				return false;
			}
			boolean moveableUp = !(direction == Direction.UP && (state.is(BlockTags.INSIDE_STEP_SOUND_BLOCKS) || state.is(BlockTags.REPLACEABLE_BY_TREES) || state.is(BlockTags.FLOWERS)));
			boolean moveableDown = !(direction == Direction.DOWN && (state.is(Blocks.WATER) || state.is(Blocks.LAVA) || (state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED))));
			return moveableUp && moveableDown;
		}

		public static boolean isTooFar(boolean natural, @NotNull BlockPos mound, @NotNull BlockPos pos) {
			return !mound.closerThan(pos, natural ? BlockConfig.get().termite.maxNaturalDistance : BlockConfig.get().termite.maxDistance);
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

		public static void addDegradableBlocks() {
			addDegradable(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
			addDegradable(Blocks.STRIPPED_ACACIA_LOG, RegisterBlocks.STRIPPED_HOLLOWED_ACACIA_LOG);
			addDegradable(RegisterBlocks.HOLLOWED_ACACIA_LOG, RegisterBlocks.STRIPPED_HOLLOWED_ACACIA_LOG);
			addDegradable(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD);

			addDegradable(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
			addDegradable(Blocks.STRIPPED_BIRCH_LOG, RegisterBlocks.STRIPPED_HOLLOWED_BIRCH_LOG);
			addDegradable(RegisterBlocks.HOLLOWED_BIRCH_LOG, RegisterBlocks.STRIPPED_HOLLOWED_BIRCH_LOG);
			addDegradable(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD);

			addDegradable(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
			addDegradable(Blocks.STRIPPED_OAK_LOG, RegisterBlocks.STRIPPED_HOLLOWED_OAK_LOG);
			addDegradable(RegisterBlocks.HOLLOWED_OAK_LOG, RegisterBlocks.STRIPPED_HOLLOWED_OAK_LOG);
			addDegradable(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD);

			addDegradable(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
			addDegradable(Blocks.STRIPPED_DARK_OAK_LOG, RegisterBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG);
			addDegradable(RegisterBlocks.HOLLOWED_DARK_OAK_LOG, RegisterBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG);
			addDegradable(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD);

			addDegradable(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
			addDegradable(Blocks.STRIPPED_JUNGLE_LOG, RegisterBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG);
			addDegradable(RegisterBlocks.HOLLOWED_JUNGLE_LOG, RegisterBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG);
			addDegradable(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD);

			addDegradable(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
			addDegradable(Blocks.STRIPPED_SPRUCE_LOG, RegisterBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG);
			addDegradable(RegisterBlocks.HOLLOWED_SPRUCE_LOG, RegisterBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG);
			addDegradable(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD);

			addDegradable(Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG);
			addDegradable(Blocks.STRIPPED_MANGROVE_LOG, RegisterBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG);
			addDegradable(RegisterBlocks.HOLLOWED_MANGROVE_LOG, RegisterBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG);
			addDegradable(Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD);

			addDegradable(Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG);
			addDegradable(Blocks.STRIPPED_CHERRY_LOG, RegisterBlocks.STRIPPED_HOLLOWED_CHERRY_LOG);
			addDegradable(RegisterBlocks.HOLLOWED_CHERRY_LOG, RegisterBlocks.STRIPPED_HOLLOWED_CHERRY_LOG);
			addDegradable(Blocks.CHERRY_WOOD, Blocks.STRIPPED_CHERRY_WOOD);
		}

		public static void addDegradable(Block degradable, Block result) {
			DEGRADABLE_BLOCKS.put(degradable, result);
		}

		public static void addNaturalDegradableBlocks() {
			addNaturalDegradable(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
			addNaturalDegradable(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
			addNaturalDegradable(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
			addNaturalDegradable(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
			addNaturalDegradable(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
			addNaturalDegradable(Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG);
			addNaturalDegradable(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
			addNaturalDegradable(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD);
			addNaturalDegradable(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD);
			addNaturalDegradable(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD);
			addNaturalDegradable(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD);
			addNaturalDegradable(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD);
			addNaturalDegradable(Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD);
			addNaturalDegradable(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD);
		}

		public static void addNaturalDegradable(@NotNull Block degradable, @NotNull Block result) {
			NATURAL_DEGRADABLE_BLOCKS.put(degradable, result);
		}
	}
}
