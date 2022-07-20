package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterGameEvents;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkSensorBlockEntity.class)
public class SculkSensorBlockEntityMixin {

    @Inject(at = @At("HEAD"), method = "onSignalReceive")
    public void onSignalReceive(ServerLevel world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, float f, CallbackInfo info) {
        SculkSensorBlockEntity sculkSensorBlockEntity = SculkSensorBlockEntity.class.cast(this);
        BlockState blockState = sculkSensorBlockEntity.getBlockState();
        if (SculkSensorBlock.canActivate(blockState)) {
            world.gameEvent(entity, RegisterGameEvents.SCULK_SENSOR_ACTIVATE, sculkSensorBlockEntity.getBlockPos());
            BlockState state = world.getBlockState(sculkSensorBlockEntity.getBlockPos());
            world.setBlockAndUpdate(sculkSensorBlockEntity.getBlockPos(), state.setValue(RegisterProperties.NOT_HICCUPPING, true));
        }
    }

}
