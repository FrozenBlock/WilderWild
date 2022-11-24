package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.misc.interfaces.SculkSensorTickInterface;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkSensorBlock.class)
public abstract class SculkSensorBlockMixin extends BaseEntityBlock implements SimpleWaterloggedBlock {

    private SculkSensorBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(at = @At("TAIL"), method = "createBlockStateDefinition")
    private void addHiccuppingState(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
        builder.add(RegisterProperties.HICCUPPING);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void registerDefaultHiccupping(BlockBehaviour.Properties settings, int range, CallbackInfo ci) {
        SculkSensorBlock sculkSensor = SculkSensorBlock.class.cast(this);
        sculkSensor.registerDefaultState(sculkSensor.defaultBlockState().setValue(RegisterProperties.HICCUPPING, false));
    }

    @Inject(at = @At("HEAD"), method = "getTicker", cancellable = true)
    public <T extends BlockEntity> void overrideTicker(Level level, BlockState state, BlockEntityType<T> type, CallbackInfoReturnable<BlockEntityTicker<T>> info) {
        if (level.isClientSide) {
            info.setReturnValue(createTickerHelper(type, BlockEntityType.SCULK_SENSOR, (worldx, pos, statex, blockEntity) -> {
                ((SculkSensorTickInterface) blockEntity).tickClient(worldx, pos, statex);
            }));
        } else {
            info.setReturnValue(createTickerHelper(type, BlockEntityType.SCULK_SENSOR, (worldx, pos, statex, blockEntity) -> {
                ((SculkSensorTickInterface) blockEntity).tickServer((ServerLevel) worldx, pos, statex);
            }));
        }
    }

    @Inject(at = @At("HEAD"), method = "activate")
    private static void activate(@Nullable Entity entity, Level level, BlockPos pos, BlockState state, int power, CallbackInfo info) {
        if (level.getBlockEntity(pos) instanceof SculkSensorBlockEntity blockEntity) {
            ((SculkSensorTickInterface) blockEntity).setActive(true);
            ((SculkSensorTickInterface) blockEntity).setAnimTicks(10);
        }
    }

    @Inject(at = @At("HEAD"), method = "getRenderShape", cancellable = true)
    public void getRenderShape(BlockState state, CallbackInfoReturnable<RenderShape> info) {
        info.setReturnValue(ClothConfigInteractionHandler.mcLiveSensorTendrils() ? RenderShape.INVISIBLE : RenderShape.MODEL);
    }

}
