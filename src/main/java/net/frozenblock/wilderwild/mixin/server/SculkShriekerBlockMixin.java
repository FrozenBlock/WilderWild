package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkShriekerBlock.class)
public class SculkShriekerBlockMixin {

    @Inject(at = @At("TAIL"), method = "createBlockStateDefinition")
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
        builder.add(RegisterProperties.SOULS_TAKEN);
    }

    @Inject(at = @At("HEAD"), method = "stepOn", cancellable = true)
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity, CallbackInfo info) {
        if (state.getValue(RegisterProperties.SOULS_TAKEN) == 2) {
            info.cancel();
        }
    }

}
