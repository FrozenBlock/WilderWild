package net.frozenblock.wilderwild.simple_copper_pipes_mixin.simple_copper_pipes;

import net.frozenblock.wilderwild.misc.simple_pipe_compatability.RegisterSaveableMoveablePipeNbt;
import net.frozenblock.wilderwild.misc.simple_pipe_compatability.SaveableAncientHorn;
import net.frozenblock.wilderwild.misc.simple_pipe_compatability.WilderSimplePipeInterface;
import net.lunade.copper.block_entity.CopperFittingEntity;
import net.lunade.copper.block_entity.CopperPipeEntity;
import net.lunade.copper.blocks.CopperPipe;
import net.lunade.copper.pipe_nbt.MoveablePipeDataHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CopperFittingEntity.class)
public class CopperFittingEntityMixin implements WilderSimplePipeInterface {

    @Inject(at = @At("HEAD"), method = "serverTick")
    private static void serverTick(World world, BlockPos blockPos, BlockState blockState, CopperFittingEntity copperFittingEntity, CallbackInfo info) {
        if (!world.isClient) {
            ((WilderSimplePipeInterface)copperFittingEntity).moveHorn(world, blockPos, blockState);
        }
    }

    @Override
    public void moveHorn(World world, BlockPos blockPos, BlockState blockState) {
        CopperFittingEntity fitting = CopperFittingEntity.class.cast(this);
        MoveablePipeDataHandler.SaveableMovablePipeNbt nbt = fitting.moveablePipeDataHandler.getMoveablePipeNbt(RegisterSaveableMoveablePipeNbt.horn);
        if (nbt!=null) {
            SaveableAncientHorn horn = SaveableAncientHorn.class.cast(nbt);
            for (Direction direction : Direction.values()) {
                BlockPos newPos = blockPos.offset(direction);
                if (world.isChunkLoaded(newPos)) {
                    BlockState state = world.getBlockState(newPos);
                    if (state.getBlock() instanceof CopperPipe) {
                        if (state.get(CopperPipe.FACING) == direction) {
                            BlockEntity entity = world.getBlockEntity(newPos);
                            if (entity instanceof CopperPipeEntity pipeEntity) {
                                pipeEntity.moveablePipeDataHandler.setMoveablePipeNbt(RegisterSaveableMoveablePipeNbt.horn, horn);
                            }
                        }
                    }
                }
            }
            fitting.moveablePipeDataHandler.removeMoveablePipeNbt(RegisterSaveableMoveablePipeNbt.horn);
            fitting.markDirty();
        }
    }

}
