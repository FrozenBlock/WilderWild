package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkSensorBlock;
import net.minecraft.state.StateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkSensorBlock.class)
public class SculkSensorBlockMixin {

    @Inject(at = @At("TAIL"), method = "appendProperties", cancellable = true)
    public void appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo info) {
        builder.add(RegisterProperties.NOT_HICCUPPING);
    }

}
