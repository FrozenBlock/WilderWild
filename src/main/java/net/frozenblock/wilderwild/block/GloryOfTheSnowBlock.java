package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.FlowerColors;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class GloryOfTheSnowBlock extends MultiColorFlowerBlock {
    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);
    public static final EnumProperty<FlowerColors> COLORS = RegisterProperties.FLOWER_COLOR;

    public GloryOfTheSnowBlock(Properties settings, List<FlowerColors> list) {
        super(settings, list);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COLORS);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextFloat() > 0.9F && state.getValue(COLORS) == FlowerColors.NONE) {
            world.setBlockAndUpdate(pos, state.setValue(COLORS, COLOR_LIST.get(WilderWild.random().nextInt(COLOR_LIST.size()))));
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world instanceof ServerLevel) {
            FlowerColors color = state.getValue(COLORS);
            if (color != FlowerColors.NONE) {
                ItemStack itemStack = player.getItemInHand(hand);
                if (itemStack.is(Items.SHEARS)) {
                    Item item = color == FlowerColors.BLUE ? RegisterBlocks.BLUE_GLORY_OF_THE_SNOW.asItem() : color == FlowerColors.PINK ? RegisterBlocks.PINK_GLORY_OF_THE_SNOW.asItem() :
                            color == FlowerColors.PURPLE ? RegisterBlocks.PURPLE_GLORY_OF_THE_SNOW.asItem() : RegisterBlocks.WHITE_GLORY_OF_THE_SNOW.asItem();
                    ItemStack stack = new ItemStack(item);
                    stack.setCount(world.random.nextIntBetweenInclusive(1, 2));
                    popResource(world, pos, stack);
                    world.setBlockAndUpdate(pos, state.getBlock().defaultBlockState());
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
                    itemStack.hurtAndBreak(1, player, (playerx) -> playerx.broadcastBreakEvent(hand));
                    world.gameEvent(player, GameEvent.SHEAR, pos);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.use(state, world, pos, player, hand, hit);

    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Vec3 vec3d = state.getOffset(world, pos);
        return SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
    }
}
