package net.frozenblock.wilderwild.block.entity;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.registry.RegisterBlockEntityType;
import net.minecraft.block.BlockState;
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

    public void tick(World world, BlockPos pos, BlockState state) {
        for (Termite termite : this.termites) {
            termite.move(world);
            world.syncWorldEvent(3006, termite.pos, 0);
        }
        this.addTermite(pos.up());
    }

    public static class Termite {
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

        public void move(World world) {
            if (canMove(world, pos)) {
                Direction direction = Direction.random(world.getRandom());
                BlockPos offest = pos.offset(direction);
                if (world.getBlockState(offest).isSolidBlock(world, pos) && canMove(world, pos, offest)) {
                    this.pos = offest;
                }
            }
        }

        public boolean canMove(WorldAccess world, BlockPos pos) {
            if (world instanceof ServerWorld serverWorld) {
                return serverWorld.shouldTickBlockPos(pos);
            } else {
                return false;
            }
        }

        private static boolean canMove(WorldAccess world, BlockPos sourcePos, BlockPos targetPos) {
            if (sourcePos.getManhattanDistance(targetPos) == 1) {
                return true;
            } else {
                BlockPos blockPos = targetPos.subtract(sourcePos);
                Direction direction = Direction.from(Direction.Axis.X, blockPos.getX() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
                Direction direction2 = Direction.from(Direction.Axis.Y, blockPos.getY() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
                Direction direction3 = Direction.from(Direction.Axis.Z, blockPos.getZ() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
                if (blockPos.getX() == 0) {
                    return canMove(world, sourcePos, direction2) || canMove(world, sourcePos, direction3);
                } else if (blockPos.getY() == 0) {
                    return canMove(world, sourcePos, direction) || canMove(world, sourcePos, direction3);
                } else {
                    return canMove(world, sourcePos, direction) || canMove(world, sourcePos, direction2);
                }
            }
        }
        private static boolean canMove(WorldAccess world, BlockPos pos, Direction direction) {
            BlockPos blockPos = pos.offset(direction);
            return !world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction.getOpposite());
        }

        public BlockPos getPos() {
            return this.pos;
        }
        public int getPower() {
            return this.blockDestroyPower;
        }
    }
}