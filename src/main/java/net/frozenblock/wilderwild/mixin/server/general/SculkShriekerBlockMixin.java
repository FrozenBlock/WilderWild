package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.misc.interfaces.SculkShriekerTickInterface;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkShriekerBlock.class)
public class SculkShriekerBlockMixin extends BaseEntityBlock {

    private SculkShriekerBlockMixin(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Shapes.or(Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(1.0D, 8.0D, 1.0D, 15.0D, 15.0D, 15.0D));
    }

    @Inject(at = @At("TAIL"), method = "createBlockStateDefinition")
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
        builder.add(RegisterProperties.SOULS_TAKEN);
    }

    @Inject(at = @At("HEAD"), method = "stepOn", cancellable = true)
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity, CallbackInfo info) {
        if (state.getValue(RegisterProperties.SOULS_TAKEN) == 2) {
            info.cancel();
        }
    }

	@Inject(at = @At("HEAD"), method = "getTicker", cancellable = true)
	public <T extends BlockEntity> void getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType, CallbackInfoReturnable<BlockEntityTicker<T>> info) {
		if (!level.isClientSide) {
			info.setReturnValue(BaseEntityBlock.createTickerHelper(blockEntityType, BlockEntityType.SCULK_SHRIEKER, (_world, pos, _state, blockEntity) -> ((SculkShriekerTickInterface)blockEntity).tickServer(_world, pos)));
		}
	}

    @Shadow
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		throw new AssertionError("Mixin injection failed - WilderWild SculkShriekerBlockMixin.");
    }

}
