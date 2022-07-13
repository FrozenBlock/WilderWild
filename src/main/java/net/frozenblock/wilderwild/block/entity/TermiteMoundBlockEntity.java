package net.frozenblock.wilderwild.block.entity;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.tag.WildBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TermiteMoundBlockEntity extends BlockEntity {
    private static final Logger LOGGER = LogUtils.getLogger();
    ArrayList<Termite> termites = new ArrayList<>();
    public int ticksToNextTermite;
    public int ticksToCheckLight;
    public int lastLight;

    public TermiteMoundBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntities.TERMITE_MOUND, pos, state);
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.ticksToNextTermite = nbt.getInt("ticksToNextTermite");
        this.ticksToCheckLight = nbt.getInt("ticksToCheckLight");
        this.lastLight = nbt.getInt("lastLight");
        if (nbt.contains("termites", 9)) {
            this.termites.clear();
            DataResult<?> var10000 = Termite.CODEC.listOf().parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getList("termites", 10)));
            Logger var10001 = LOGGER;
            Objects.requireNonNull(var10001);
            Optional<List> list = (Optional<List>) var10000.resultOrPartial(var10001::error);
            if (list.isPresent()) {
                List termitesAllAllAll = list.get();
                int max = this.world != null ? maxTermites(this.world, this.lastLight, this.getCachedState().get(RegisterProperties.NATURAL)) : 5;
                int i = Math.min(termitesAllAllAll.size(), max);

                for (int j = 0; j < i; ++j) {
                    this.termites.add((Termite) termitesAllAllAll.get(j));
                }
            }
        }
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("ticksToNextTermite", this.ticksToNextTermite);
        nbt.putInt("ticksToCheckLight", this.ticksToCheckLight);
        nbt.putInt("lastLight", this.lastLight);
        DataResult<?> var10000 = Termite.CODEC.listOf().encodeStart(NbtOps.INSTANCE, this.termites);
        Logger var10001 = LOGGER;
        Objects.requireNonNull(var10001);
        var10000.resultOrPartial(var10001::error).ifPresent((cursorsNbt) -> {
            nbt.put("termites", (NbtElement) cursorsNbt);
        });
    }

    public void addTermite(BlockPos pos) {
        Termite termite = new Termite(pos, pos, 0, 0, 0, this.getCachedState().get(RegisterProperties.NATURAL));
        this.termites.add(termite);
    }

    public void tick(World world, BlockPos pos) {
        if (this.ticksToCheckLight > 0) {
            --this.ticksToCheckLight;
        } else {
            this.lastLight = getLightLevel(world, this.pos);
            this.ticksToCheckLight = 100;
        }

        int maxTermites = maxTermites(world, this.lastLight, this.getCachedState().get(RegisterProperties.NATURAL));
        ArrayList<Termite> termitesToRemove = new ArrayList<>();
        for (Termite termite : this.termites) {
            if (termite.tick(world)) {
                //TODO: TERMITE SPAWNING, MOVING, AND EATING TEXTURES + PARTICLES
                EasyPacket.EasyTermitePacket.createParticle(world, Vec3d.ofCenter(termite.pos), termite.eating ? 5 : 9);
            } else {
                world.playSound(null, termite.pos, SoundEvents.BLOCK_BEEHIVE_ENTER, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                termitesToRemove.add(termite);
            }
        }
        for (Termite termite : termitesToRemove) {
            world.emitGameEvent(null, GameEvent.ENTITY_DIE, Vec3d.ofCenter(termite.pos));
            this.termites.remove(termite);
            world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, Vec3d.ofCenter(pos));
        }
        if (this.termites.size() < maxTermites) {
            if (this.ticksToNextTermite > 0) {
                --this.ticksToNextTermite;
            } else {
                this.addTermite(pos);
                world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, Vec3d.ofCenter(pos));
                //TODO: TERMITE SPAWN (EXIT MOUND,) DESPAWN, EATING, AND MOVING SOUNDS
                world.playSound(null, this.pos, SoundEvents.BLOCK_BEEHIVE_EXIT, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                this.ticksToNextTermite = this.getCachedState().get(RegisterProperties.NATURAL) ? 320 : 200;
            }
        }
        while (this.termites.size() > maxTermites) {
            Termite termite = this.termites.get(WilderWild.random().nextInt(this.termites.size()));
            //TODO: TERMITE SPAWN (EXIT MOUND,) DESPAWN, EATING, AND MOVING SOUNDS
            world.playSound(null, termite.pos, SoundEvents.BLOCK_BEEHIVE_ENTER, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            world.emitGameEvent(null, GameEvent.ENTITY_DIE, Vec3d.ofCenter(termite.pos));
            this.termites.remove(termite);
            world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, Vec3d.ofCenter(pos));
        }
    }

    public static int maxTermites(World world, int light, boolean natural) {
        if (world.isNight() && light < 7) {
            return natural ? 0 : 1;
        }
        return natural ? 3 : 5;
    }

    public static int getLightLevel(World world, BlockPos blockPos) {
        int finalLight = 0;
        for (Direction direction : Direction.values()) {
            BlockPos pos = blockPos.offset(direction);
            int skyLight = 0;
            int blockLight = world.getLightLevel(LightType.BLOCK, pos);
            if (world.isDay() && !world.isRaining()) {
                skyLight = world.getLightLevel(LightType.SKY, pos);
            }
            finalLight = Math.max(finalLight, Math.max(skyLight, blockLight));
        }
        return finalLight;
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

        public boolean tick(World world) {
            boolean exit = false;
            ++this.aliveTicks;
            if (this.aliveTicks > (this.natural ? 1200 : 2000) || isTooFar(this.natural, this.mound, this.pos)) {
                return false;
            }
            if (canMove(world, this.pos)) {
                BlockState blockState = world.getBlockState(this.pos);
                Block block = blockState.getBlock();
                boolean degradable = !this.natural ? degradableBlocks.contains(block) : naturalDegradableBlocks.contains(block);
                boolean breakable = blockState.isIn(WildBlockTags.TERMITE_BREAKABLE);
                boolean leaves = blockState.isIn(BlockTags.LEAVES);
                if (degradable || breakable) {
                    this.eating = true;
                    exit = true;
                    int additionalPower = breakable ? leaves ? 4 : 2 : 1;
                    this.blockDestroyPower += additionalPower;
                    if (this.blockDestroyPower > 200) {
                        this.blockDestroyPower = 0;
                        this.aliveTicks = Math.max(0, this.aliveTicks - (200 / additionalPower));
                        if (blockState.isIn(WildBlockTags.TERMITE_BREAKABLE)) {
                            world.breakBlock(this.pos, true);
                        } else {
                            Direction.Axis axis = blockState.contains(Properties.AXIS) ? blockState.get(Properties.AXIS) : Direction.Axis.X;
                            world.addBlockBreakParticles(this.pos, blockState);
                            BlockState setState = !this.natural ? degradableBlockResults.get(degradableBlocks.indexOf(block)).getDefaultState() : naturalDegradableBlockResults.get(naturalDegradableBlocks.indexOf(block)).getDefaultState();
                            if (setState.contains(Properties.AXIS)) {
                                setState = setState.with(Properties.AXIS, axis);
                            }
                            world.setBlockState(this.pos, setState);
                        }
                    }
                } else {
                    this.eating = false;
                    this.blockDestroyPower = 0;
                    Direction direction = Direction.random(world.getRandom());
                    if (blockState.isAir()) {
                        direction = Direction.DOWN;
                    }
                    BlockPos offest = this.pos.offset(direction);
                    BlockState state = world.getBlockState(offest);
                    if (state.isIn(WildBlockTags.KILLS_TERMITE) || state.isOf(Blocks.WATER) || state.isOf(Blocks.LAVA)) {
                        return false;
                    }

                    if (this.update > 0 && !blockState.isAir()) {
                        --this.update;
                        return true;
                    } else {
                        this.update = 1;
                        BlockPos priority = degradableBreakablePos(world, this.pos, this.natural);
                        if (priority != null) {
                            this.pos = priority;
                            exit = true;
                        } else {
                            BlockPos ledge = ledgePos(world, offest, this.natural);
                            BlockPos posUp = this.pos.up();
                            BlockState stateUp = world.getBlockState(posUp);
                            if (exposedToAir(world, offest, this.natural) && isBlockMovable(state, direction) && !(direction != Direction.DOWN && state.isAir() && (!this.mound.isWithinDistance(this.pos, 1.5)) && ledge == null)) {
                                this.pos = offest;
                                if (ledge != null) {
                                    this.pos = ledge;
                                }
                                exit = true;
                            } else if (ledge != null && exposedToAir(world, ledge, this.natural)) {
                                this.pos = ledge;
                                exit = true;
                            } else if (!stateUp.isAir() && isBlockMovable(stateUp, Direction.UP) && exposedToAir(world, posUp, this.natural)) {
                                this.pos = posUp;
                                exit = true;
                            }
                        }
                    }
                }
            }
            return exit || (exposedToAir(world, this.pos, this.natural));
        }

        @Nullable
        public static BlockPos ledgePos(World world, BlockPos pos, boolean natural) {
            BlockState state = world.getBlockState(pos);
            if (degradableBlocks.contains(state.getBlock()) || state.isIn(WildBlockTags.TERMITE_BREAKABLE)) {
                return pos;
            }
            pos = pos.down();
            state = world.getBlockState(pos);
            if (!state.isAir() && isBlockMovable(state, Direction.DOWN) && exposedToAir(world, pos, natural)) {
                return pos;
            }
            pos = pos.up().up();
            state = world.getBlockState(pos);
            if (!state.isAir() && isBlockMovable(state, Direction.UP) && exposedToAir(world, pos, natural)) {
                return pos;
            }
            return null;
        }

        @Nullable
        public static BlockPos degradableBreakablePos(World world, BlockPos pos, boolean natural) {
            List<Direction> directions = Util.copyShuffled(Direction.values(), world.random);
            BlockState upState = world.getBlockState(pos.offset(Direction.UP));
            if ((!natural ? degradableBlocks.contains(upState.getBlock()) : naturalDegradableBlocks.contains(upState.getBlock())) || upState.isIn(WildBlockTags.TERMITE_BREAKABLE)) {
                return pos.offset(Direction.UP);
            }
            for (Direction direction : directions) {
                BlockState state = world.getBlockState(pos.offset(direction));
                if ((!natural ? degradableBlocks.contains(state.getBlock()) : naturalDegradableBlocks.contains(state.getBlock())) || state.isIn(WildBlockTags.TERMITE_BREAKABLE)) {
                    return pos.offset(direction);
                }
            }
            return null;
        }

        public static boolean exposedToAir(World world, BlockPos pos, boolean natural) {
            for (Direction direction : Direction.values()) {
                BlockState state = world.getBlockState(pos.offset(direction));
                if (state.isAir() || (!state.isSolidBlock(world, pos.offset(direction)) && !state.isIn(WildBlockTags.BLOCKS_TERMITE)) || (!natural && degradableBlocks.contains(state.getBlock())) || (natural && naturalDegradableBlocks.contains(state.getBlock())) || state.isIn(WildBlockTags.TERMITE_BREAKABLE)) {
                    return true;
                }
            }
            return false;
        }

        public static boolean canMove(WorldAccess world, BlockPos pos) {
            if (world instanceof ServerWorld serverWorld) {
                return serverWorld.shouldTickBlockPos(pos);
            }
            return false;
        }

        public static boolean isBlockMovable(BlockState state, Direction direction) {
            if (state.isIn(WildBlockTags.BLOCKS_TERMITE)) {
                return false;
            }
            boolean moveableUp = !(direction == Direction.UP && (state.isIn(BlockTags.INSIDE_STEP_SOUND_BLOCKS) || state.isIn(BlockTags.REPLACEABLE_PLANTS) || state.isIn(BlockTags.FLOWERS)));
            boolean moveableDown = !(direction == Direction.DOWN && (state.isOf(Blocks.WATER) || state.isOf(Blocks.LAVA)));
            return moveableUp && moveableDown;
        }

        public static boolean isTooFar(boolean natural, BlockPos mound, BlockPos pos) {
            return !mound.isWithinDistance(pos, natural ? 10 : 32);
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

        public static final ArrayList<Block> degradableBlocks = new ArrayList<>();
        public static final ArrayList<Block> degradableBlockResults = new ArrayList<>();
        public static final ArrayList<Block> naturalDegradableBlocks = new ArrayList<>();
        public static final ArrayList<Block> naturalDegradableBlockResults = new ArrayList<>();

        public static void addDegradableBlocks() {
            addDegradable(Blocks.ACACIA_LOG, RegisterBlocks.HOLLOWED_ACACIA_LOG);
            addDegradable(Blocks.OAK_LOG, RegisterBlocks.HOLLOWED_OAK_LOG);
            addDegradable(Blocks.BIRCH_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG);
            addDegradable(Blocks.DARK_OAK_LOG, RegisterBlocks.HOLLOWED_DARK_OAK_LOG);
            addDegradable(Blocks.JUNGLE_LOG, RegisterBlocks.HOLLOWED_JUNGLE_LOG);
            addDegradable(Blocks.MANGROVE_LOG, RegisterBlocks.HOLLOWED_MANGROVE_LOG);
            addDegradable(Blocks.SPRUCE_LOG, RegisterBlocks.HOLLOWED_SPRUCE_LOG);
            addDegradable(Blocks.STRIPPED_ACACIA_LOG, Blocks.AIR);
            addDegradable(Blocks.STRIPPED_OAK_LOG, Blocks.AIR);
            addDegradable(Blocks.STRIPPED_BIRCH_LOG, Blocks.AIR);
            addDegradable(Blocks.STRIPPED_DARK_OAK_LOG, Blocks.AIR);
            addDegradable(Blocks.STRIPPED_JUNGLE_LOG, Blocks.AIR);
            addDegradable(Blocks.STRIPPED_MANGROVE_LOG, Blocks.AIR);
            addDegradable(Blocks.STRIPPED_SPRUCE_LOG, Blocks.AIR);
            addDegradable(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD);
            addDegradable(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD);
            addDegradable(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD);
            addDegradable(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD);
            addDegradable(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD);
            addDegradable(Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD);
            addDegradable(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD);
            addDegradable(Blocks.STRIPPED_ACACIA_WOOD, Blocks.AIR);
            addDegradable(Blocks.STRIPPED_OAK_WOOD, Blocks.AIR);
            addDegradable(Blocks.STRIPPED_BIRCH_WOOD, Blocks.AIR);
            addDegradable(Blocks.STRIPPED_DARK_OAK_WOOD, Blocks.AIR);
            addDegradable(Blocks.STRIPPED_JUNGLE_WOOD, Blocks.AIR);
            addDegradable(Blocks.STRIPPED_MANGROVE_WOOD, Blocks.AIR);
            addDegradable(Blocks.STRIPPED_SPRUCE_WOOD, Blocks.AIR);
        }

        public static void addDegradable(Block degradable, Block result) {
            degradableBlocks.add(degradable);
            degradableBlockResults.add(result);
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
            naturalDegradableBlocks.add(degradable);
            naturalDegradableBlockResults.add(result);
        }
    }
}