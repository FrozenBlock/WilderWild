package net.frozenblock.wilderwild.misc.simple_pipe_compatability;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface WilderSimplePipeInterface {

    SaveableAncientHorn getSavedAncientHorn();

    public void setSavedAncientHorn(SaveableAncientHorn horn);

    public void moveHorn(World world, BlockPos blockPos, BlockState blockState);
}
