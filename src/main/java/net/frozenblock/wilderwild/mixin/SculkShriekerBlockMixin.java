package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkShriekerBlock;
import net.minecraft.state.StateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static net.minecraft.block.SculkShriekerBlock.*;

@Mixin(SculkShriekerBlock.class)
public class SculkShriekerBlockMixin {

    @Overwrite
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SHRIEKING, WATERLOGGED, CAN_SUMMON, RegisterProperties.SOULS_TAKEN);
    }

}
