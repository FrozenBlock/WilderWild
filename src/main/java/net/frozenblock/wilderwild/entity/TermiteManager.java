/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.block.HollowedLogBlock;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class TermiteManager {
	private final ArrayList<Termite> termites = new ArrayList<>();
	public int ticksToNextTermite;

	public TermiteManager() {
	}

	public void addTermite(BlockPos pos, boolean natural) {
		Termite termite = new Termite(pos, pos, 0, 0, 0, natural);
		this.termites.add(termite);
	}

	public void tick(Level level, BlockPos pos, boolean natural, boolean awake, boolean canSpawn) {
		int maxTermites = maxTermites(natural, awake, canSpawn);
		ArrayList<Termite> termitesToRemove = new ArrayList<>();
		for (Termite termite : this.termites) {
			if (termite.tick(level)) {
				EasyPacket.EasyTermitePacket.createParticle(level, Vec3.atCenterOf(termite.pos), termite.eating ? 4 : 6);
			} else {
				level.playSound(null, termite.pos, RegisterSounds.BLOCK_TERMITE_MOUND_ENTER, SoundSource.NEUTRAL, 1.0F, 1.0F);
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
				this.addTermite(pos, natural);
				level.gameEvent(null, GameEvent.BLOCK_CHANGE, Vec3.atCenterOf(pos));
				level.playSound(null, pos, RegisterSounds.BLOCK_TERMITE_MOUND_EXIT, SoundSource.NEUTRAL, 0.6F, 1.0F);
				this.ticksToNextTermite = natural ? 320 : 200;
			}
		}
		while (this.termites.size() > maxTermites) {
			Termite termite = this.termites.get(AdvancedMath.random().nextInt(this.termites.size()));
			level.playSound(null, termite.pos, RegisterSounds.BLOCK_TERMITE_MOUND_ENTER, SoundSource.NEUTRAL, 0.6F, 1.0F);
			level.gameEvent(null, GameEvent.TELEPORT, Vec3.atCenterOf(termite.pos));
			this.termites.remove(termite);
			level.gameEvent(null, GameEvent.BLOCK_CHANGE, Vec3.atCenterOf(pos));
		}
		termitesToRemove.clear();
	}

	public static int maxTermites(boolean natural, boolean awake, boolean canSpawn) {
		if (!canSpawn) {
			return 0;
		}
		if (!awake) {
			return natural ? 0 : 1;
		}
		return natural ? 3 : 5;
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

	public void saveAdditional(@NotNull CompoundTag tag) {
		tag.putInt("ticksToNextTermite", this.ticksToNextTermite);
		Logger logger = WilderSharedConstants.LOGGER;
		DataResult<Tag> var10000 = Termite.CODEC.listOf().encodeStart(NbtOps.INSTANCE, this.termites);
		Objects.requireNonNull(logger);
		var10000.resultOrPartial(logger::error).ifPresent((nbt) -> tag.put("termites", nbt));
	}

	public void load(@NotNull CompoundTag tag) {
		this.ticksToNextTermite = tag.getInt("ticksToNextTermite");
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
		public BlockPos mound;
		public BlockPos pos;
		public int blockDestroyPower;
		public int aliveTicks;
		public int update;
		public boolean natural;
		public boolean eating;

		public static final Codec<Termite> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				BlockPos.CODEC.fieldOf("mound").forGetter(Termite::getMoundPos),
				BlockPos.CODEC.fieldOf("pos").forGetter(Termite::getPos),
				Codec.intRange(0, 10000).fieldOf("blockDestroyPower").orElse(0).forGetter(Termite::getPower),
				Codec.intRange(0, 2002).fieldOf("aliveTicks").orElse(0).forGetter(Termite::getAliveTicks),
				Codec.intRange(0, 5).fieldOf("update").orElse(0).forGetter(Termite::getUpdateTicks),
				Codec.BOOL.fieldOf("natural").orElse(true).forGetter(Termite::getNatural)
		).apply(instance, Termite::new));

		public Termite(BlockPos mound, BlockPos pos, int blockDestroyPower, int aliveTicks, int update, boolean natural) {
			this.mound = mound;
			this.pos = pos;
			this.blockDestroyPower = blockDestroyPower;
			this.aliveTicks = aliveTicks;
			this.update = update;
			this.natural = natural;
		}

		public boolean tick(Level level) {
			boolean exit = false;
			++this.aliveTicks;
			if (this.aliveTicks > (this.natural ? 1200 : 2000) || isTooFar(this.natural, this.mound, this.pos)) {
				return false;
			}
			if (!areTermitesSafe(level, this.pos)) {
				return false;
			}
			if (canMove(level, this.pos)) {
				BlockState blockState = level.getBlockState(this.pos);
				Block block = blockState.getBlock();
				boolean degradable = !this.natural ? DEGRADABLE_BLOCKS.containsKey(block) : NATURAL_DEGRADABLE_BLOCKS.containsKey(block);
				boolean breakable = blockState.is(WilderBlockTags.TERMITE_BREAKABLE);
				boolean leaves = blockState.is(BlockTags.LEAVES);
				if ((degradable || breakable) && isEdibleProperty(blockState)) {
					this.eating = true;
					exit = true;
					int additionalPower = breakable ? leaves ? 4 : 2 : 1;
					this.blockDestroyPower += additionalPower;
					spawnGnawParticles(level, blockState, this.pos);
					if (this.blockDestroyPower > 200) {
						this.blockDestroyPower = 0;
						this.aliveTicks = this.natural ? Math.max(0, this.aliveTicks - (200 / additionalPower)) : 0;
						if (breakable) {
							level.destroyBlock(this.pos, true);
						} else {
							level.addDestroyBlockEffect(this.pos, blockState);
							Block setBlock = !this.natural ? DEGRADABLE_BLOCKS.get(block) : NATURAL_DEGRADABLE_BLOCKS.get(block);
							BlockState setState = setBlock.withPropertiesOf(blockState);
							level.setBlockAndUpdate(this.pos, setState);
							if (setState.getBlock() instanceof HollowedLogBlock) {
								level.playSound(null, pos, setState.getMaterial().equals(Material.NETHER_WOOD) ? RegisterSounds.STEM_HOLLOWED : RegisterSounds.LOG_HOLLOWED, SoundSource.BLOCKS, 0.6F, 0.95F + (level.random.nextFloat() * 0.2F));
							}
						}
						spawnEatParticles(level, blockState, this.pos);
						level.playSound(null, pos, RegisterSounds.BLOCK_TERMITE_MOUND_TERMITE_GNAW_FINISH, SoundSource.BLOCKS, 0.6F, 0.9F + (level.random.nextFloat() * 0.25F));
					} else {
						level.playSound(null, pos, RegisterSounds.BLOCK_TERMITE_MOUND_TERMITE_GNAW, SoundSource.BLOCKS, 0.08F, 0.9F + (level.random.nextFloat() * 0.25F));
					}
				} else {
					this.eating = false;
					this.blockDestroyPower = 0;
					Direction direction = Direction.getRandom(level.getRandom());
					if (blockState.isAir()) {
						direction = Direction.DOWN;
					}
					BlockPos offest = this.pos.relative(direction);
					BlockState state = level.getBlockState(offest);
					if (!isStateSafeForTermites(state)) {
						return false;
					}

					if (this.update > 0 && !blockState.isAir()) {
						--this.update;
						return true;
					} else {
						this.update = 1;
						BlockPos priority = degradableBreakablePos(level, this.pos, this.natural);
						if (priority != null) {
							this.pos = priority;
							exit = true;
						} else {
							BlockPos ledge = ledgePos(level, offest, this.natural);
							BlockPos posUp = this.pos.above();
							BlockState stateUp = level.getBlockState(posUp);
							if (exposedToAir(level, offest, this.natural) && isBlockMovable(state, direction) && !(direction != Direction.DOWN && state.isAir() && (!this.mound.closerThan(this.pos, 1.5)) && ledge == null)) {
								this.pos = offest;
								if (ledge != null) {
									this.pos = ledge;
								}
								exit = true;
							} else if (ledge != null && exposedToAir(level, ledge, this.natural)) {
								this.pos = ledge;
								exit = true;
							} else if (!stateUp.isAir() && isBlockMovable(stateUp, Direction.UP) && exposedToAir(level, posUp, this.natural)) {
								this.pos = posUp;
								exit = true;
							}
						}
					}
				}
			}
			if (exit || (exposedToAir(level, this.pos, this.natural))) {
				level.playSound(null, pos, RegisterSounds.BLOCK_TERMITE_MOUND_TERMITE_IDLE, SoundSource.BLOCKS, 0.05F, 0.95F + (level.random.nextFloat() * 0.2F));
				return true;
			} else {
				return false;
			}
		}

		@Nullable
		public static BlockPos ledgePos(Level level, BlockPos pos, boolean natural) {
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
		public static BlockPos degradableBreakablePos(Level level, BlockPos pos, boolean natural) {
			BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
			List<Direction> directions = Util.shuffledCopy(Direction.values(), level.random);
			BlockState upState = level.getBlockState(mutableBlockPos.move(Direction.UP));
			if (((!natural ? DEGRADABLE_BLOCKS.containsKey(upState.getBlock()) : NATURAL_DEGRADABLE_BLOCKS.containsKey(upState.getBlock())) || upState.is(WilderBlockTags.TERMITE_BREAKABLE)) && isEdibleProperty(upState)) {
				if (upState.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF) && upState.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER) {
					mutableBlockPos.move(Direction.DOWN);
				}
				return mutableBlockPos;
			}
			mutableBlockPos.move(Direction.DOWN);
			for (Direction direction : directions) {
				BlockState state = level.getBlockState(mutableBlockPos.move(direction));
				if (((!natural ? DEGRADABLE_BLOCKS.containsKey(state.getBlock()) : NATURAL_DEGRADABLE_BLOCKS.containsKey(state.getBlock())) || state.is(WilderBlockTags.TERMITE_BREAKABLE)) && isEdibleProperty(state)) {
					if (state.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF) && state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER) {
						mutableBlockPos.move(Direction.DOWN);
					}
					return mutableBlockPos;
				}
				mutableBlockPos.move(direction, -1);
			}
			return null;
		}

		public static boolean isEdibleProperty(BlockState state) {
			return state.hasProperty(RegisterProperties.TERMITE_EDIBLE) ? state.getValue(RegisterProperties.TERMITE_EDIBLE) : !state.is(BlockTags.LEAVES) || !state.hasProperty(BlockStateProperties.PERSISTENT) || !state.getValue(BlockStateProperties.PERSISTENT);
		}

		public static boolean exposedToAir(Level level, BlockPos pos, boolean natural) {
			BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
			for (Direction direction : Direction.values()) {
				BlockState state = level.getBlockState(mutableBlockPos.move(direction));
				if (state.isAir() || (!state.isRedstoneConductor(level, mutableBlockPos) && !state.is(WilderBlockTags.BLOCKS_TERMITE)) || ((!natural && DEGRADABLE_BLOCKS.containsKey(state.getBlock())) || (natural && NATURAL_DEGRADABLE_BLOCKS.containsKey(state.getBlock())) || state.is(WilderBlockTags.TERMITE_BREAKABLE))  && isEdibleProperty(state)) {
					return true;
				}
				mutableBlockPos.move(direction, -1);
			}
			return false;
		}

		public static boolean canMove(LevelAccessor level, BlockPos pos) {
			if (level instanceof ServerLevel serverLevel) {
				return serverLevel.shouldTickBlocksAt(pos);
			}
			return false;
		}

		public static boolean isBlockMovable(BlockState state, Direction direction) {
			if (state.is(WilderBlockTags.BLOCKS_TERMITE)) {
				return false;
			}
			boolean moveableUp = !(direction == Direction.UP && (state.is(BlockTags.INSIDE_STEP_SOUND_BLOCKS) || state.is(BlockTags.REPLACEABLE_PLANTS) || state.is(BlockTags.FLOWERS)));
			boolean moveableDown = !(direction == Direction.DOWN && (state.is(Blocks.WATER) || state.is(Blocks.LAVA) || (state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED))));
			return moveableUp && moveableDown;
		}

		public static boolean isTooFar(boolean natural, BlockPos mound, BlockPos pos) {
			return !mound.closerThan(pos, natural ? 10 : 32);
		}

		public BlockPos getMoundPos() {
			return this.mound;
		}

		public BlockPos getPos() {
			return this.pos;
		}

		public int getPower() {
			return this.blockDestroyPower;
		}

		public int getAliveTicks() {
			return this.aliveTicks;
		}

		public int getUpdateTicks() {
			return this.update;
		}

		public boolean getNatural() {
			return this.natural;
		}

		public static final Map<Block, Block> DEGRADABLE_BLOCKS = new HashMap<>();
		public static final Map<Block, Block> NATURAL_DEGRADABLE_BLOCKS = new HashMap<>();

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

		public static void addNaturalDegradable(Block degradable, Block result) {
			NATURAL_DEGRADABLE_BLOCKS.put(degradable, result);
		}

		public static void spawnGnawParticles(Level level, BlockState eatState, BlockPos pos) {
			if (level.random.nextInt(0, 4) == 3 && level instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, eatState), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, level.random.nextInt(0, 3), 0.3F, 0.3F, 0.3F, 0.05D);
			}
		}

		public static void spawnEatParticles(Level level, BlockState eatState, BlockPos pos) {
			if (level instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, eatState), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, level.random.nextInt(18, 25), 0.3F, 0.3F, 0.3F, 0.05D);
			}
		}
	}
}
