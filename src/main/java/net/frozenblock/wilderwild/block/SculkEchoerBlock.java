package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SculkEchoerBlock extends Block {
    private static final VoxelShape SHAPE = VoxelShapes.union(Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D), Block.createCuboidShape(1.0D, 5.0D, 1.0D, 15.0D, 16D, 15.0D));
    private static final VoxelShape COLLISION = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 11.0D, 15.0D);
//why dont these work
    public SculkEchoerBlock(Settings settings, int i) {
        super(settings);
    }

    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
        super.onStacksDropped(state, world, pos, stack);
        int i = 20;
        this.dropExperience(world, pos, i);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && !Registry.ENTITY_TYPE.getId(entity.getType()).equals(EntityType.WARDEN)) {
            world.emitGameEvent(entity, WilderWild.SCULK_ECHOER_ECHO, pos);
            world.playSound(
                    null,
                    pos,
                    RegisterSounds.BLOCK_SCULK_ECHOER_RECEIVE_VIBRATION,
                    SoundCategory.BLOCKS,
                    1.0f,
                    world.random.nextFloat() * 0.1F + 0.9F
            );
        }
        super.onEntityCollision(state, world, pos, entity);
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
}
