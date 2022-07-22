package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.FlowerColors;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class GloryOfTheSnowBlock extends PlantBlock {
    private static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);
    public static final EnumProperty<FlowerColors> COLORS = RegisterProperties.FLOWER_COLOR;

    public final List<FlowerColors> COLOR_LIST;

    public GloryOfTheSnowBlock(Settings settings, List<FlowerColors> list) {
        super(settings);
        this.COLOR_LIST = list;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COLORS);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextFloat() > 0.9F && state.get(COLORS) == FlowerColors.NONE) {
            world.setBlockState(pos, state.with(COLORS, COLOR_LIST.get(WilderWild.random().nextInt(COLOR_LIST.size()))));
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world instanceof ServerWorld) {
            FlowerColors color = state.get(COLORS);
            if (color != FlowerColors.NONE) {
                ItemStack itemStack = player.getStackInHand(hand);
                if (itemStack.isOf(Items.SHEARS)) {
                    Item item = color == FlowerColors.BLUE ? RegisterBlocks.BLUE_GLORY_OF_THE_SNOW.asItem() : color == FlowerColors.PINK ? RegisterBlocks.PINK_GLORY_OF_THE_SNOW.asItem() :
                            color == FlowerColors.PURPLE ? RegisterBlocks.PURPLE_GLORY_OF_THE_SNOW.asItem() : RegisterBlocks.WHITE_GLORY_OF_THE_SNOW.asItem();
                    ItemStack stack = new ItemStack(item);
                    stack.setCount(world.random.nextBetween(1, 2));
                    dropStack(world, pos, stack);
                    world.setBlockState(pos, state.getBlock().getDefaultState());
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_GROWING_PLANT_CROP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    itemStack.damage(1, player, (playerx) -> playerx.sendToolBreakStatus(hand));
                    world.emitGameEvent(player, GameEvent.SHEAR, pos);
                    return ActionResult.SUCCESS;
                }
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);

    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d vec3d = state.getModelOffset(world, pos);
        return SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
    }
}
