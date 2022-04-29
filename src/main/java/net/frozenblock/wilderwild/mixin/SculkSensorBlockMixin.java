package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkSensorBlock;
import net.minecraft.state.StateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static net.minecraft.block.SculkSensorBlock.POWER;
import static net.minecraft.block.SculkSensorBlock.SCULK_SENSOR_PHASE;
import static net.minecraft.block.SculkShriekerBlock.WATERLOGGED;

@Mixin(SculkSensorBlock.class)
public class SculkSensorBlockMixin {
    /**
     * @author FrozenBlock
     * @reason hiccup property
     */
    @Overwrite
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SCULK_SENSOR_PHASE, POWER, WATERLOGGED, RegisterProperties.NOT_HICCUPING);
    }

}
