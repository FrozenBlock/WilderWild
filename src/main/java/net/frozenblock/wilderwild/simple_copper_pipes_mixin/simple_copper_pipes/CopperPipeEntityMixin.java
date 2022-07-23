package net.frozenblock.wilderwild.simple_copper_pipes_mixin.simple_copper_pipes;

import net.frozenblock.wilderwild.misc.simple_pipe_compatability.RegisterSaveableMoveablePipeNbt;
import net.frozenblock.wilderwild.misc.simple_pipe_compatability.SaveableAncientHorn;
import net.lunade.copper.block_entity.CopperFittingEntity;
import net.lunade.copper.block_entity.CopperPipeEntity;
import net.lunade.copper.blocks.CopperFitting;
import net.lunade.copper.blocks.CopperPipe;
import net.lunade.copper.pipe_nbt.MoveablePipeDataHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
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
public class CopperPipeEntityMixin {

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
            CopperPipeEntity pipe = CopperPipeEntity.class.cast(this);
            MoveablePipeDataHandler.SaveableMovablePipeNbt nbt = pipe.moveablePipeDataHandler.getMoveablePipeNbt(RegisterSaveableMoveablePipeNbt.horn);
            if (nbt != null) {
                SaveableAncientHorn horn = SaveableAncientHorn.class.cast(nbt);
                horn.dispense(serverWorld, blockPos);
                this.moveHorn(serverWorld, blockPos, blockState);
                pipe.moveablePipeDataHandler.removeMoveablePipeNbt(RegisterSaveableMoveablePipeNbt.horn);
            }
        }
    }

    public void moveHorn(World world, BlockPos blockPos, BlockState blockState) {
        CopperPipeEntity pipe = CopperPipeEntity.class.cast(this);
        MoveablePipeDataHandler.SaveableMovablePipeNbt nbt = pipe.moveablePipeDataHandler.getMoveablePipeNbt(RegisterSaveableMoveablePipeNbt.horn);
        if (nbt!=null) {
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
                                    pipeEntity.moveablePipeDataHandler.setMoveablePipeNbt(RegisterSaveableMoveablePipeNbt.horn, nbt);
                                }
                            }
                        }
                        if (direction==facing) {
                            if (state.getBlock() instanceof CopperFitting) {
                                BlockEntity entity = world.getBlockEntity(newPos);
                                if (entity instanceof CopperFittingEntity fittingEntity) {
                                    fittingEntity.moveablePipeDataHandler.setMoveablePipeNbt(RegisterSaveableMoveablePipeNbt.horn, nbt);
                                }
                            }
                        }
                    }
                }
            }
            pipe.moveablePipeDataHandler.removeMoveablePipeNbt(RegisterSaveableMoveablePipeNbt.horn);
            pipe.markDirty();
        }
    }

}
