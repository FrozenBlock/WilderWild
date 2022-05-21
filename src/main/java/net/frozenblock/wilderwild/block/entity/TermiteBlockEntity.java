package net.frozenblock.wilderwild.block.entity;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.block.HollowedLogBlock;
import net.frozenblock.wilderwild.registry.RegisterBlockEntityType;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
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
        Termite termite = new Termite(pos.getX(), pos.getY(), pos.getZ(), 0);
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
        public static ArrayList<Block> edible = new ArrayList<>() {{
            add(Blocks.ACACIA_LOG);
            add(Blocks.OAK_LOG);
            add(Blocks.BIRCH_LOG);
            add(Blocks.DARK_OAK_LOG);
            add(Blocks.JUNGLE_LOG);
            add(Blocks.MANGROVE_LOG);
            add(Blocks.SPRUCE_LOG);
            add(Blocks.STRIPPED_ACACIA_LOG);
            add(Blocks.STRIPPED_OAK_LOG);
            add(Blocks.STRIPPED_BIRCH_LOG);
            add(Blocks.STRIPPED_DARK_OAK_LOG);
            add(Blocks.STRIPPED_JUNGLE_LOG);
            add(Blocks.STRIPPED_MANGROVE_LOG);
            add(Blocks.STRIPPED_SPRUCE_LOG);
            add(Blocks.ACACIA_WOOD);
            add(Blocks.OAK_WOOD);
            add(Blocks.BIRCH_WOOD);
            add(Blocks.DARK_OAK_WOOD);
            add(Blocks.JUNGLE_WOOD);
            add(Blocks.MANGROVE_WOOD);
            add(Blocks.SPRUCE_WOOD);
            add(Blocks.STRIPPED_ACACIA_WOOD);
            add(Blocks.STRIPPED_OAK_WOOD);
            add(Blocks.STRIPPED_BIRCH_WOOD);
            add(Blocks.STRIPPED_DARK_OAK_WOOD);
            add(Blocks.STRIPPED_JUNGLE_WOOD);
            add(Blocks.STRIPPED_MANGROVE_WOOD);
            add(Blocks.STRIPPED_SPRUCE_WOOD);
            add(Blocks.ACACIA_LEAVES);
            add(Blocks.OAK_LEAVES);
            add(Blocks.BIRCH_LEAVES);
            add(Blocks.DARK_OAK_LEAVES);
            add(Blocks.JUNGLE_LEAVES);
            add(Blocks.MANGROVE_LEAVES);
            add(Blocks.SPRUCE_LEAVES);
        }};
        public static ArrayList<Block> edibleTo = new ArrayList<>() {{
            add(RegisterBlocks.HOLLOWED_ACACIA_LOG);
            add(RegisterBlocks.HOLLOWED_OAK_LOG);
            add(RegisterBlocks.HOLLOWED_BIRCH_LOG);
            add(RegisterBlocks.HOLLOWED_DARK_OAK_LOG);
            add(RegisterBlocks.HOLLOWED_JUNGLE_LOG);
            add(RegisterBlocks.HOLLOWED_MANGROVE_LOG);
            add(RegisterBlocks.HOLLOWED_SPRUCE_LOG);
            add(Blocks.AIR);
            add(Blocks.AIR);
            add(Blocks.AIR);
            add(Blocks.AIR);
            add(Blocks.AIR);
            add(Blocks.AIR);
            add(Blocks.AIR);
            add(Blocks.STRIPPED_ACACIA_WOOD);
            add(Blocks.STRIPPED_OAK_WOOD);
            add(Blocks.STRIPPED_BIRCH_WOOD);
            add(Blocks.STRIPPED_DARK_OAK_WOOD);
            add(Blocks.STRIPPED_JUNGLE_WOOD);
            add(Blocks.STRIPPED_MANGROVE_WOOD);
            add(Blocks.STRIPPED_SPRUCE_WOOD);
            add(Blocks.AIR);
            add(Blocks.AIR);
            add(Blocks.AIR);
            add(Blocks.AIR);
            add(Blocks.AIR);
            add(Blocks.AIR);
            add(Blocks.AIR);
            add(Blocks.AIR);
            add(Blocks.AIR);
            add(Blocks.AIR);
            add(Blocks.AIR);
            add(Blocks.AIR);
            add(Blocks.AIR);
            add(Blocks.AIR);
        }};
        public BlockPos pos;
        public int blockDestroyPower;
        public static final Codec<Termite> CODEC = RecordCodecBuilder.create((instance) -> {
            return instance.group(BlockPos.CODEC.fieldOf("pos").forGetter(Termite::getPos),
                    Codec.intRange(0, 10000).fieldOf("blockDestroyPower").orElse(0).forGetter(Termite::getPower)).apply(instance, Termite::new);
        });

        public Termite(int x, int y, int z, int blockDestroyPower) {
            this.pos = new BlockPos(x,y,z);
            this.blockDestroyPower=blockDestroyPower;
        }
        public Termite(BlockPos pos, int blockDestroyPower) {
            this.pos = pos;
            this.blockDestroyPower=blockDestroyPower;
        }

        public boolean tick(World world) {
            boolean exit = false;
            if (canMove(world, this.pos)) {
                if (edible.contains(world.getBlockState(this.pos).getBlock())) {
                    exit = true;
                    ++this.blockDestroyPower;
                    if (this.blockDestroyPower>200) {
                        if (world.getBlockState(this.pos).getBlock() instanceof LeavesBlock) {
                            world.breakBlock(this.pos, true);
                        } else {
                            world.setBlockState(this.pos, edibleTo.get(edible.indexOf(world.getBlockState(this.pos).getBlock())).getDefaultState());
                        }
                    }
                } else {
                    this.blockDestroyPower = 0;
                    Direction direction = Direction.random(world.getRandom());
                    BlockPos offest = pos.offset(direction);
                    if (world.getBlockState(offest).isSolidBlock(world, offest) && exposedToAir(world, offest)) {
                        this.pos = offest;
                        exit = true;
                    }
                    if (!exit) {
                        direction = Direction.random(world.getRandom());
                        offest = offest.offset(direction);
                        if (world.getBlockState(offest).isSolidBlock(world, offest) && exposedToAir(world, offest)) {
                            this.pos = offest;
                            exit = true;
                        }
                    }
                }
            }
            return exit || ((world.getBlockState(this.pos).isSolidBlock(world, this.pos) || world.getBlockState(this.pos).getBlock() instanceof HollowedLogBlock) && exposedToAir(world, this.pos));
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

        public BlockPos getPos() {
            return this.pos;
        }
        public int getPower() {
            return this.blockDestroyPower;
        }
    }
}