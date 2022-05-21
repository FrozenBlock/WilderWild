package net.frozenblock.wilderwild.block.entity;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.frozenblock.wilderwild.registry.RegisterBlockEntityType;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WildBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TermiteBlockEntity extends BlockEntity {
    private static final Logger LOGGER = LogUtils.getLogger();
    ArrayList<Termite> termites = new ArrayList<>();

    public TermiteBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntityType.TERMITE, pos, state);
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("termites", 9)) {
            this.termites.clear();
            DataResult<?> var10000 = Termite.CODEC.listOf().parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getList("termites", 10)));
            Logger var10001 = LOGGER;
            Objects.requireNonNull(var10001);
            Optional<List> list = (Optional<List>) var10000.resultOrPartial(var10001::error);
            if (list.isPresent()) {
                List termitesAllAllAll = list.get();
                int i = Math.min(termitesAllAllAll.size(), 32);

                for (int j = 0; j < i; ++j) {
                    this.termites.add((Termite) termitesAllAllAll.get(j));
                }
            }
        }
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        DataResult<?> var10000 = Termite.CODEC.listOf().encodeStart(NbtOps.INSTANCE, this.termites);
        Logger var10001 = LOGGER;
        Objects.requireNonNull(var10001);
        var10000.resultOrPartial(var10001::error).ifPresent((cursorsNbt) -> {
            nbt.put("termites", (NbtElement) cursorsNbt);
        });
    }

    public void addTermite(BlockPos pos) {
        Termite termite = new Termite(pos, pos, 0);
        this.termites.add(termite);
    }

    public void tick(World world, BlockPos pos) {
        ArrayList<Termite> termitesToRemove = new ArrayList<>();
        for (Termite termite : this.termites) {
            if (termite.tick(world)) {
                world.syncWorldEvent(3006, termite.pos, 0);
            } else {
                termitesToRemove.add(termite);
            }
        }
        for (Termite termite : termitesToRemove) {
            this.termites.remove(termite);
        }
        if (this.termites.size()<32) {
            this.addTermite(pos);
        }
    }

    public static class Termite {
        public BlockPos mound;
        public BlockPos pos;
        public int blockDestroyPower;
        public static final Codec<Termite> CODEC = RecordCodecBuilder.create((instance) -> {
            return instance.group(BlockPos.CODEC.fieldOf("mound").forGetter(Termite::getMoundPos),
                    BlockPos.CODEC.fieldOf("pos").forGetter(Termite::getPos),
                    Codec.intRange(0, 10000).fieldOf("blockDestroyPower").orElse(0).forGetter(Termite::getPower)).apply(instance, Termite::new);
        });

        public Termite(BlockPos mound, BlockPos pos, int blockDestroyPower) {
            this.mound = mound;
            this.pos = pos;
            this.blockDestroyPower=blockDestroyPower;
        }

        public boolean tick(World world) {
            boolean exit = false;
            if (canMove(world, this.pos)) {
                BlockState blockState = world.getBlockState(this.pos);
                Block block = blockState.getBlock();
                if (EDIBLE.containsKey(block)) {
                    exit = true;
                    ++this.blockDestroyPower;
                    if (this.blockDestroyPower>200) {
                        if (block instanceof LeavesBlock) {
                            world.breakBlock(this.pos, true);
                        } else {
                            world.setBlockState(this.pos, EDIBLE.get(block).getDefaultState());
                        }
                    }
                } else {
                    this.blockDestroyPower = 0;
                    Direction direction = Direction.random(world.getRandom());
                    if (blockState.isAir()) { direction=Direction.DOWN; }
                    BlockPos offest = pos.offset(direction);
                    BlockState state = world.getBlockState(offest);
                    if (state.isIn(WildBlockTags.KILLS_TERMITE) || state.isOf(Blocks.WATER) || state.isOf(Blocks.LAVA)) { return false; }
                    if (exposedToAir(world, offest) && !(direction!=Direction.DOWN && state.isAir() && !this.mound.isWithinDistance(this.pos, 1.5))) {
                        this.pos = offest;
                        exit = true;
                    }
                }
            }
            return exit || (exposedToAir(world, this.pos));
        }

        public static boolean exposedToAir(World world, BlockPos pos) {
            for (Direction direction : Direction.values()) {
                if (world.getBlockState(pos.offset(direction)).isAir()) {
                    return true;
                }
            } return false;
        }

        public boolean canMove(WorldAccess world, BlockPos pos) {
            if (world instanceof ServerWorld serverWorld) {
                return serverWorld.shouldTickBlockPos(pos);
            } return false;
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
        public static final Object2ObjectMap<Block, Block> EDIBLE = Object2ObjectMaps.unmodifiable(Util.make(new Object2ObjectOpenHashMap<>(), (map) -> {
            map.put(Blocks.ACACIA_LOG, RegisterBlocks.HOLLOWED_ACACIA_LOG);
            map.put(Blocks.OAK_LOG, RegisterBlocks.HOLLOWED_OAK_LOG);
            map.put(Blocks.BIRCH_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG);
            map.put(Blocks.DARK_OAK_LOG, RegisterBlocks.HOLLOWED_DARK_OAK_LOG);
            map.put(Blocks.JUNGLE_LOG, RegisterBlocks.HOLLOWED_JUNGLE_LOG);
            map.put(Blocks.MANGROVE_LOG, RegisterBlocks.HOLLOWED_MANGROVE_LOG);
            map.put(Blocks.SPRUCE_LOG, RegisterBlocks.HOLLOWED_SPRUCE_LOG);
            map.put(Blocks.STRIPPED_ACACIA_LOG, Blocks.AIR);
            map.put(Blocks.STRIPPED_OAK_LOG, Blocks.AIR);
            map.put(Blocks.STRIPPED_BIRCH_LOG, Blocks.AIR);
            map.put(Blocks.STRIPPED_DARK_OAK_LOG, Blocks.AIR);
            map.put(Blocks.STRIPPED_JUNGLE_LOG, Blocks.AIR);
            map.put(Blocks.STRIPPED_MANGROVE_LOG, Blocks.AIR);
            map.put(Blocks.STRIPPED_SPRUCE_LOG, Blocks.AIR);
            map.put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD);
            map.put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD);
            map.put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD);
            map.put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD);
            map.put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD);
            map.put(Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD);
            map.put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD);
            map.put(Blocks.STRIPPED_ACACIA_WOOD, Blocks.AIR);
            map.put(Blocks.STRIPPED_OAK_WOOD, Blocks.AIR);
            map.put(Blocks.STRIPPED_BIRCH_WOOD, Blocks.AIR);
            map.put(Blocks.STRIPPED_DARK_OAK_WOOD, Blocks.AIR);
            map.put(Blocks.STRIPPED_JUNGLE_WOOD, Blocks.AIR);
            map.put(Blocks.STRIPPED_MANGROVE_WOOD, Blocks.AIR);
            map.put(Blocks.STRIPPED_SPRUCE_WOOD, Blocks.AIR);
            map.put(Blocks.ACACIA_LEAVES, Blocks.AIR);
            map.put(Blocks.OAK_LEAVES, Blocks.AIR);
            map.put(Blocks.BIRCH_LEAVES, Blocks.AIR);
            map.put(Blocks.DARK_OAK_LEAVES, Blocks.AIR);
            map.put(Blocks.JUNGLE_LEAVES, Blocks.AIR);
            map.put(Blocks.MANGROVE_LEAVES, Blocks.AIR);
            map.put(Blocks.SPRUCE_LEAVES, Blocks.AIR);
        }));
    }
}