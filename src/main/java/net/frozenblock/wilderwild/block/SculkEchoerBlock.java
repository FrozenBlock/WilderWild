package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterBlockEntityType;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SculkEchoerBlock extends BlockWithEntity implements Waterloggable {
    private static final VoxelShape SHAPE = VoxelShapes.union(Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D), Block.createCuboidShape(1.0D, 5.0D, 1.0D, 15.0D, 16D, 15.0D));
    private static final VoxelShape COLLISION = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 11.0D, 15.0D);
    public SculkEchoerBlock(Settings settings, int i) {
        super(settings);
    }

    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
        super.onStacksDropped(state, world, pos, stack);
        int i = 20;
        this.dropExperience(world, pos, i);
    }

    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld)world;
            if (entity instanceof PlayerEntity || entity.getPrimaryPassenger() instanceof PlayerEntity) {
                serverWorld.getBlockEntity(pos, RegisterBlockEntityType.SCULK_ECHOER).ifPresent((blockEntity) -> {
                    //blockEntity.shriek(serverWorld);
                });
            }
        }

        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean hasSidedTransparency(BlockState blockState) {
        return true;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos blockPos, ShapeContext shapeContext) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockView blockView, BlockPos blockPos, ShapeContext shapeContext) {
        return COLLISION;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }
}
