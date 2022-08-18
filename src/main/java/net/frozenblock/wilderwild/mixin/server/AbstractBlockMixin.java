package net.frozenblock.wilderwild.mixin.server;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.class)
public class AbstractBlockMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo info) {
        if (state.is(Blocks.DIRT)) {
            world.setBlockAndUpdate(pos, Blocks.MUD.defaultBlockState());
        }
    }

}
