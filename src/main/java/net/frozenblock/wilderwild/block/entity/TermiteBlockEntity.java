package net.frozenblock.wilderwild.block.entity;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.registry.RegisterBlockEntityType;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
            this.addTermite(pos.up());
        }
    }

    public static class Termite {
        public static ArrayList<Block> highPriority = new ArrayList<>() {{add(Blocks.OAK_LOG);}};
        public static ArrayList<Block> highPriorityTo = new ArrayList<>() {{add(RegisterBlocks.HOLLOWED_OAK_LOG);}};
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

        public boolean tick (World world) {
            boolean exit = false;
            if (canMove(world, this.pos)) {
                if (highPriority.contains(world.getBlockState(this.pos).getBlock())) {
                    exit = true;
                    ++this.blockDestroyPower;
                    if (this.blockDestroyPower>200) {
                        world.setBlockState(this.pos, highPriorityTo.get(highPriority.indexOf(world.getBlockState(this.pos).getBlock())).getDefaultState());
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
            return exit || (world.getBlockState(this.pos).isSolidBlock(world, this.pos) && exposedToAir(world, this.pos));
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