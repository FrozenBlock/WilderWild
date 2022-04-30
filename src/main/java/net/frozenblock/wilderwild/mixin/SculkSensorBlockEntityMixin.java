package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkSensorBlock;
import net.minecraft.block.entity.SculkSensorBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkSensorBlockEntity.class)
public class SculkSensorBlockEntityMixin {

    @Inject(at = @At("HEAD"), method = "accept")
    public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, int delay, CallbackInfo info) {
        SculkSensorBlockEntity sculkSensorBlockEntity = SculkSensorBlockEntity.class.cast(this);
        BlockState blockState = sculkSensorBlockEntity.getCachedState();
        if (SculkSensorBlock.isInactive(blockState)) {
            world.emitGameEvent(entity, WilderWild.SCULK_SENSOR_ACTIVATE, sculkSensorBlockEntity.getPos());
            BlockState state = world.getBlockState(sculkSensorBlockEntity.getPos());
            world.setBlockState(sculkSensorBlockEntity.getPos(), state.with(RegisterProperties.NOT_HICCUPING, true));
        }
    }

}
