package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.SculkSensorTickInterface;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SculkSensorBlockEntity;
import net.minecraft.block.enums.SculkSensorPhase;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkSensorBlock.class)
public class SculkSensorBlockMixin {

    @Inject(at = @At("TAIL"), method = "appendProperties")
    public void appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo info) {
        builder.add(RegisterProperties.HICCUPPING);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void SculkSensorBlock(AbstractBlock.Settings settings, int range, CallbackInfo ci) {
        SculkSensorBlock sculkSensor = SculkSensorBlock.class.cast(this);
        sculkSensor.setDefaultState(sculkSensor.getStateManager().getDefaultState().with(RegisterProperties.HICCUPPING, false));
    }

    @Inject(at = @At("HEAD"), method = "getTicker", cancellable = true)
    public <T extends BlockEntity> void getTicker(World world, BlockState state, BlockEntityType<T> type, CallbackInfoReturnable<BlockEntityTicker<T>> info) {
        info.cancel();
        if (world.isClient) {
            info.setReturnValue(checkType(type, BlockEntityType.SCULK_SENSOR, (worldx, pos, statex, blockEntity) -> {
                ((SculkSensorTickInterface)blockEntity).tickClient(worldx, pos, statex);
            }));
        } else {
            info.setReturnValue(checkType(type, BlockEntityType.SCULK_SENSOR, (worldx, pos, statex, blockEntity) -> {
                ((SculkSensorTickInterface)blockEntity).tickServer((ServerWorld) worldx, pos, statex);
            }));
        }
    }

    @Inject(at = @At("HEAD"), method = "setActive")
    private static void setActive(@Nullable Entity entity, World world, BlockPos pos, BlockState state, int power, CallbackInfo info) {
        if (world.getBlockEntity(pos) instanceof SculkSensorBlockEntity blockEntity) {
            ((SculkSensorTickInterface)blockEntity).setActive(true);
            ((SculkSensorTickInterface)blockEntity).setAnimTicks(10);
        }
    }

    @Inject(at = @At("HEAD"), method = "getRenderType", cancellable = true)
    public void getRenderType(BlockState state, CallbackInfoReturnable<BlockRenderType> info) {
        info.setReturnValue(WilderWild.RENDER_TENDRILS ? BlockRenderType.INVISIBLE : BlockRenderType.MODEL);
        info.cancel();
    }

    @Nullable
    private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }
}