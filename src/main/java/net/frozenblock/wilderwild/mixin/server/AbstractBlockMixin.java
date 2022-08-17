package net.frozenblock.wilderwild.mixin.server;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {

    @Inject(method = "scheduledTick", at = @At("HEAD"))
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo info) {
        if (state.isOf(Blocks.DIRT)) {
            world.setBlockState(pos, Blocks.MUD.getDefaultState());
        }
    }

}
