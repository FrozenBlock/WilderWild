package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SculkShriekerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkShriekerBlock.class)
public class SculkShriekerBlockMixin extends BlockWithEntity {

    public SculkShriekerBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.union(Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.createCuboidShape(1.0D, 8.0D, 1.0D, 15.0D, 15.0D, 15.0D));
    }

    @Inject(at = @At("TAIL"), method = "appendProperties")
    public void appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo info) {
        builder.add(RegisterProperties.SOULS_TAKEN);
    }

    @Inject(at = @At("HEAD"), method = "onSteppedOn", cancellable = true)
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo info) {
        if (state.get(RegisterProperties.SOULS_TAKEN) == 2) {
            info.cancel();
        }
    }

    @Shadow
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SculkShriekerBlockEntity(pos, state);
    }
}
