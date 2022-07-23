package net.frozenblock.wilderwild.simple_copper_pipes_mixin.simple_copper_pipes;

import net.frozenblock.wilderwild.simple_copper_pipes_mixin.SaveableAncientHorn;
import net.frozenblock.wilderwild.simple_copper_pipes_mixin.WilderSimplePipeInterface;
import net.lunade.copper.block_entity.CopperFittingEntity;
import net.lunade.copper.block_entity.CopperPipeEntity;
import net.lunade.copper.blocks.CopperPipe;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CopperFittingEntity.class)
public class CopperFittingEntityMixin implements WilderSimplePipeInterface {

    public SaveableAncientHorn savedHorn;

    @Inject(at = @At("HEAD"), method = "serverTick")
    private static void serverTick(World world, BlockPos blockPos, BlockState blockState, CopperFittingEntity copperFittingEntity, CallbackInfo info) {
        if (!world.isClient) {
            ((WilderSimplePipeInterface)copperFittingEntity).moveHorn(world, blockPos, blockState);
        }
    }

    public void moveHorn(World world, BlockPos blockPos, BlockState blockState) {
        if (this.savedHorn!=null) {
            for (Direction direction : Direction.values()) {
                BlockPos newPos = blockPos.offset(direction);
                if (world.isChunkLoaded(newPos)) {
                    BlockState state = world.getBlockState(newPos);
                    if (state.getBlock() instanceof CopperPipe) {
                        if (state.get(CopperPipe.FACING) == direction) {
                            BlockEntity entity = world.getBlockEntity(newPos);
                            if (entity instanceof CopperPipeEntity pipeEntity) {
                                ((WilderSimplePipeInterface)pipeEntity).setSavedAncientHorn(this.savedHorn);
                            }
                        }
                    }
                }
            }
            this.savedHorn = null;
            CopperFittingEntity.class.cast(this).markDirty();
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
