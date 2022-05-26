package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.entity.NewSculkSensorBlockEntity;
import net.frozenblock.wilderwild.registry.RegisterBlockEntityType;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkSensorBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkSensorBlock.class)
public class SculkSensorBlockMixin {

    @Inject(at = @At("TAIL"), method = "appendProperties", cancellable = true)
    public void appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo info) {
        builder.add(RegisterProperties.NOT_HICCUPPING);
    }

    @Nullable
    @Inject(at = @At("HEAD"), method = "createBlockEntity", cancellable = true)
    public void createBlockEntity(BlockPos pos, BlockState state, CallbackInfoReturnable<BlockEntity> info) {
        info.setReturnValue(new NewSculkSensorBlockEntity(pos, state));
        info.cancel();
    }

    @Inject(at = @At("HEAD"), method = "setActive", cancellable = true)
    private static void setActive(@Nullable Entity entity, World world, BlockPos pos, BlockState state, int power, CallbackInfo info) {
        world.addSyncedBlockEvent(pos, state.getBlock(), 1, 1);
    }


    @Nullable
    @Inject(at = @At("HEAD"), method = "getGameEventListener", cancellable = true)
    public <T extends BlockEntity> void getGameEventListener(ServerWorld world, T blockEntity, CallbackInfoReturnable<GameEventListener> info) {
        info.setReturnValue(blockEntity instanceof NewSculkSensorBlockEntity ? ((NewSculkSensorBlockEntity)blockEntity).getEventListener() : null);
        info.cancel();
    }

    @Nullable
    @Inject(at = @At("HEAD"), method = "getTicker", cancellable = true)
    public <T extends BlockEntity> void getTicker(World world, BlockState state, BlockEntityType<T> type, CallbackInfoReturnable<BlockEntityTicker<T>> info) {
        info.setReturnValue(checkType(type, RegisterBlockEntityType.NEW_SCULK_SENSOR, (worldx, pos, statex, blockEntity) -> {
            blockEntity.tick(worldx, pos, statex);
        }));
        info.cancel();
    }

    @Inject(at = @At("HEAD"), method = "getRenderType", cancellable = true)
    public void getRenderType(BlockState state, CallbackInfoReturnable<BlockRenderType> info) {
        info.setReturnValue(WilderWild.RENDER_TENDRILS ? BlockRenderType.INVISIBLE : BlockRenderType.MODEL);
    }


    @Nullable
    private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

}
