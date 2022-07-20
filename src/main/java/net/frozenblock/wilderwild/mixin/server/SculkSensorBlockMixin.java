package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkSensorBlock.class)
public class SculkSensorBlockMixin {

    @Inject(at = @At("TAIL"), method = "createBlockStateDefinition")
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
        builder.add(RegisterProperties.NOT_HICCUPPING);
    }

}
