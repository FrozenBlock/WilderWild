package net.frozenblock.wilderwild.misc.interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface SculkSensorTickInterface {

    void wilderWild$tickServer(ServerLevel level, BlockPos pos, BlockState state);

    void wilderWild$tickClient(Level level, BlockPos pos, BlockState state);

    int wilderWild$getAge();

    int wilderWild$getAnimTicks();

    int wilderWild$getPrevAnimTicks();

    boolean wilderWild$isActive();

    void wilderWild$setActive(boolean active);

    void wilderWild$setAnimTicks(int i);

}
