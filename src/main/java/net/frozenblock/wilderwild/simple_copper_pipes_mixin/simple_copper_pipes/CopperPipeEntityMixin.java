package net.frozenblock.wilderwild.simple_copper_pipes_mixin.simple_copper_pipes;

import net.frozenblock.wilderwild.simple_copper_pipes_mixin.SaveableAncientHorn;
import net.frozenblock.wilderwild.simple_copper_pipes_mixin.WilderSimplePipeInterface;
import net.lunade.copper.block_entity.CopperFittingEntity;
import net.lunade.copper.block_entity.CopperPipeEntity;
import net.lunade.copper.blocks.CopperFitting;
import net.lunade.copper.blocks.CopperPipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.state.property.Properties.FACING;

@Mixin(CopperPipeEntity.class)
public class CopperPipeEntityMixin implements WilderSimplePipeInterface {

    public SaveableAncientHorn savedHorn;

    @Inject(at = @At("HEAD"), method = "serverTick")
    public void serverTick(World world, BlockPos blockPos, BlockState blockState, CallbackInfo info) {
        if (!world.isClient) {
            dispenseHorn((ServerWorld) world, blockPos, blockState);
            moveHorn(world, blockPos, blockState);
        }
    }

    private void dispenseHorn(ServerWorld serverWorld, BlockPos blockPos, BlockState blockState) {
        Direction direction = blockState.get(FACING);
        Direction directionOpp = direction.getOpposite();
        Block dirBlock = serverWorld.getBlockState(blockPos.offset(direction)).getBlock();
        Block oppBlock = serverWorld.getBlockState(blockPos.offset(directionOpp)).getBlock();
        boolean bl1 = dirBlock == Blocks.AIR;
        boolean bl2 = oppBlock != Blocks.AIR;
        boolean bl3 = dirBlock == Blocks.WATER;
        boolean bl4 = oppBlock != Blocks.WATER;
        if ((bl1 || bl3) && (bl2 && bl4)) {
            if (this.savedHorn != null) {
                this.savedHorn.emit(serverWorld, blockPos);
                this.moveHorn(serverWorld, blockPos, blockState);
                this.savedHorn = null;
            }
        }
    }

    public void moveHorn(World world, BlockPos blockPos, BlockState blockState) {
        if (this.savedHorn!=null) {
            Direction facing = blockState.get(FACING);
            Direction except = facing.getOpposite();
            for (Direction direction : Direction.values()) {
                if (direction != except) {
                    BlockPos newPos = blockPos.offset(direction);
                    if (world.isChunkLoaded(newPos)) {
                        BlockState state = world.getBlockState(newPos);
                        if (state.getBlock() instanceof CopperPipe) {
                            if (state.get(FACING) == direction || direction == facing) {
                                BlockEntity entity = world.getBlockEntity(newPos);
                                if (entity instanceof CopperPipeEntity pipeEntity) {
                                    ((WilderSimplePipeInterface)pipeEntity).setSavedAncientHorn(this.savedHorn);
                                }
                            }
                        }
                        if (direction==facing) {
                            if (state.getBlock() instanceof CopperFitting) {
                                BlockEntity entity = world.getBlockEntity(newPos);
                                if (entity instanceof CopperFittingEntity fittingEntity) {
                                    ((WilderSimplePipeInterface)fittingEntity).setSavedAncientHorn(this.savedHorn);
                                }
                            }
                        }
                    }
                }
            }
            this.savedHorn = null;
            CopperPipeEntity.class.cast(this).markDirty();
        }
    }

    @Inject(at = @At("TAIL"), method = "readNbt")
    public void readNbt(NbtCompound nbtCompound, CallbackInfo info) {
        this.savedHorn = SaveableAncientHorn.readNbt(nbtCompound);
    }

    @Inject(at = @At("TAIL"), method = "writeNbt")
    public void writeNbt(NbtCompound nbtCompound, CallbackInfo info) {
        SaveableAncientHorn.writeNbt(nbtCompound, this.savedHorn);
    }

    @Override
    public SaveableAncientHorn getSavedAncientHorn() {
        return this.savedHorn;
    }

    @Override
    public void setSavedAncientHorn(SaveableAncientHorn horn) {
        this.savedHorn = horn;
    }
}
