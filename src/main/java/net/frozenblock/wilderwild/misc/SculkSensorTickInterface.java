package net.frozenblock.wilderwild.misc;

import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface SculkSensorTickInterface {

    void tickServer(ServerWorld world, BlockPos pos, BlockState state);

    void tickClient(World world, BlockPos pos, BlockState state);

    int getAge();

    int getAnimTicks();

    int getPrevAnimTicks();

    boolean isActive();

}
