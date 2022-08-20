package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.entity.DisplayLanternBlockEntity;
import net.frozenblock.wilderwild.item.FireflyBottle;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DisplayLanternBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    public static final IntegerProperty LIGHT = RegisterProperties.LIGHT;
    protected static final VoxelShape STANDING_SHAPE = Shapes.or(Block.box(5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D), Block.box(6.0D, 7.0D, 6.0D, 10.0D, 8.0D, 10.0D));
    protected static final VoxelShape HANGING_SHAPE = Shapes.or(Block.box(5.0D, 2.0D, 5.0D, 11.0D, 9.0D, 11.0D), Block.box(6.0D, 9.0D, 6.0D, 10.0D, 10.0D, 10.0D));

    public DisplayLanternBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(HANGING, false).setValue(WATERLOGGED, false).setValue(LIGHT, 0));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof DisplayLanternBlockEntity lantern) {
            ItemStack stack = player.getItemInHand(hand);
            if (lantern.invEmpty()) {
                if (stack.getItem() instanceof FireflyBottle bottle) {
                    if (lantern.getFireflies().size() < 4) {
                        String name = "";
                        if (stack.hasCustomHoverName()) {
                            name = stack.getHoverName().getString();
                        }
                        lantern.addFirefly(bottle, name);
                        if (!player.isCreative()) {
                            player.getItemInHand(hand).shrink(1);
                        }
                        player.getInventory().placeItemBackInInventory(new ItemStack(Items.GLASS_BOTTLE));
                        world.setBlockAndUpdate(pos, state.setValue(LIGHT, Mth.clamp(lantern.getFireflies().size() * 3, 0, 15)));
                        world.playSound(null, pos, RegisterSounds.ITEM_BOTTLE_PUT_IN_LANTERN_FIREFLY, SoundSource.BLOCKS, 1.0F, world.random.nextFloat() * 0.2f + 0.9f);
                        lantern.updateSync();
                        return InteractionResult.SUCCESS;
                    }
                }
                if (stack.is(Items.GLASS_BOTTLE)) {
                    if (!lantern.getFireflies().isEmpty()) {
                        DisplayLanternBlockEntity.FireflyInLantern fireflyInLantern = lantern.getFireflies().get((int) (lantern.getFireflies().size() * Math.random()));
                        Optional<Item> optionalItem = Registry.ITEM.getOptional(WilderWild.id(Objects.equals(fireflyInLantern.color, "on") ? "firefly_bottle" : fireflyInLantern.color + "_firefly_bottle"));
                        Item item = RegisterItems.FIREFLY_BOTTLE;
                        if (optionalItem.isPresent()) {
                            item = optionalItem.get();
                        }
                        world.playSound(null, pos, RegisterSounds.ITEM_BOTTLE_CATCH_FIREFLY, SoundSource.BLOCKS, 1.0F, world.random.nextFloat() * 0.2f + 0.9f);
                        if (!player.isCreative()) {
                            player.getItemInHand(hand).shrink(1);
                        }
                        ItemStack bottleStack = new ItemStack(item);
                        if (!Objects.equals(fireflyInLantern.customName, "")) {
                            bottleStack.setHoverName(Component.nullToEmpty(fireflyInLantern.customName));
                        }
                        player.getInventory().placeItemBackInInventory(bottleStack);
                        ((DisplayLanternBlockEntity) entity).removeFirefly(fireflyInLantern);
                        world.setBlockAndUpdate(pos, state.setValue(LIGHT, Mth.clamp(lantern.getFireflies().size() * 3, 0, 15)));
                        lantern.updateSync();
                        return InteractionResult.SUCCESS;
                    }
                }
                if (!stack.isEmpty() && lantern.noFireflies()) {
                    int light = 0;
                    if (stack.getItem() instanceof BlockItem blockItem) {
                        light = blockItem.getBlock().defaultBlockState().getLightEmission();
                    } else if (stack.isEnchanted()) {
                        light = (int) Math.round(stack.getEnchantmentTags().size() * 0.5);
                    }
                    world.setBlockAndUpdate(pos, state.setValue(LIGHT, Mth.clamp(light, 0, 15)));
                    lantern.inventory.set(0, stack.split(1));
                    lantern.updateSync();
                    return InteractionResult.SUCCESS;
                }
            } else if (lantern.noFireflies()) {
                Optional<ItemStack> stack1 = lantern.inventory.stream().findFirst();
                if (stack1.isPresent()) {
                    popResource(world, pos, stack1.get());
                    lantern.inventory.clear();
                    lantern.updateSync();
                    world.setBlockAndUpdate(pos, state.setValue(LIGHT, 0));
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        Direction[] var3 = ctx.getNearestLookingDirections();

        for (Direction direction : var3) {
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockState = this.defaultBlockState().setValue(HANGING, direction == Direction.UP);
                if (blockState.canSurvive(ctx.getLevel(), ctx.getClickedPos())) {
                    return blockState.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return state.getValue(HANGING) ? HANGING_SHAPE : STANDING_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, net.minecraft.world.level.block.state.BlockState> builder) {
        builder.add(HANGING, WATERLOGGED, LIGHT);
    }

    @Override
    public boolean canSurvive(net.minecraft.world.level.block.state.BlockState state, LevelReader world, BlockPos pos) {
        Direction direction = attachedDirection(state).getOpposite();
        return Block.canSupportCenter(world, pos.relative(direction), direction.getOpposite());
    }

    private static Direction attachedDirection(net.minecraft.world.level.block.state.BlockState state) {
        return state.getValue(HANGING) ? Direction.DOWN : Direction.UP;
    }

    @Override
    public PushReaction getPistonPushReaction(net.minecraft.world.level.block.state.BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public RenderShape getRenderShape(net.minecraft.world.level.block.state.BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public net.minecraft.world.level.block.state.BlockState updateShape(net.minecraft.world.level.block.state.BlockState state, Direction direction, net.minecraft.world.level.block.state.BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        if (attachedDirection(state).getOpposite() == direction && !state.canSurvive(world, pos)) {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof DisplayLanternBlockEntity lanternEntity) {
                lanternEntity.spawnFireflies();
            }
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock())) {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof DisplayLanternBlockEntity lantern) {
                for (ItemStack item : lantern.inventory) {
                    popResource(world, pos, item);
                }
                lantern.inventory.clear();
            }
        }
        super.onRemove(state, world, pos, newState, moved);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DisplayLanternBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return !world.isClientSide ? createTickerHelper(type, RegisterBlockEntities.DISPLAY_LANTERN, (worldx, pos, statex, blockEntity) -> blockEntity.serverTick(world, pos)) : createTickerHelper(type, RegisterBlockEntities.DISPLAY_LANTERN, (worldx, pos, statex, blockEntity) -> blockEntity.clientTick(world, pos));
    }

    public void playerDestroy(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        player.causeFoodExhaustion(0.005F);
        if (!world.isClientSide && blockEntity instanceof DisplayLanternBlockEntity lanternEntity) {
            boolean silk = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0 || player.isCreative();
            if (silk && !lanternEntity.getFireflies().isEmpty()) {
                lanternEntity.spawnFireflies(world);
            }
        }
        player.awardStat(Stats.BLOCK_MINED.get(this));
        dropResources(state, world, pos, blockEntity, player, stack);
    }

    @Deprecated
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        ResourceLocation identifier = this.getLootTable();
        if (builder.getOptionalParameter(LootContextParams.TOOL) != null) {
            ItemStack stack = builder.getParameter(LootContextParams.TOOL);
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) != 0) {
                if (builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY) != null) {
                    BlockEntity blockEntity = builder.getParameter(LootContextParams.BLOCK_ENTITY);
                    if (blockEntity instanceof DisplayLanternBlockEntity lanternBlockEntity) {
                        if (!lanternBlockEntity.getFireflies().isEmpty()) {
                            identifier = WilderWild.id("blocks/display_lantern_fireflies");
                        }
                    }
                }
            }
        }
        if (identifier == BuiltInLootTables.EMPTY) {
            return Collections.emptyList();
        } else {
            LootContext lootContext = builder.withParameter(LootContextParams.BLOCK_STATE, state).create(LootContextParamSets.BLOCK);
            ServerLevel serverWorld = lootContext.getLevel();
            LootTable lootTable = serverWorld.getServer().getLootTables().get(identifier);
            return lootTable.getRandomItems(lootContext);
        }
    }

}
