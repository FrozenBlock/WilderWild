package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.entity.FireflyLanternBlockEntity;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.entity.ai.FireflyBrain;
import net.frozenblock.wilderwild.item.FireflyBottle;
import net.frozenblock.wilderwild.registry.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FireflyLanternBlock extends BlockWithEntity implements Waterloggable {

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty HANGING = Properties.HANGING;
    public static final IntProperty FIREFLIES = RegisterProperties.FIREFLIES;
    protected static final VoxelShape STANDING_SHAPE = VoxelShapes.union(Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D), Block.createCuboidShape(6.0D, 7.0D, 6.0D, 10.0D, 8.0D, 10.0D));
    protected static final VoxelShape HANGING_SHAPE = VoxelShapes.union(Block.createCuboidShape(5.0D, 2.0D, 5.0D, 11.0D, 9.0D, 11.0D), Block.createCuboidShape(6.0D, 9.0D, 6.0D, 10.0D, 10.0D, 10.0D));

    public FireflyLanternBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(HANGING, false).with(WATERLOGGED, false).with(FIREFLIES, 0));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof FireflyLanternBlockEntity lantern) {
            ItemStack stack = player.getStackInHand(hand);
            if (stack.getItem() instanceof FireflyBottle bottle) {
                if (lantern.getFireflies().size() < 4) {
                    String name = "";
                    if (stack.hasCustomName()) {
                        name = stack.getName().getString();
                    }
                    lantern.addFirefly(bottle, name);
                    if (!player.isCreative()) {
                        player.getStackInHand(hand).decrement(1);
                    }
                    player.getInventory().offerOrDrop(new ItemStack(Items.GLASS_BOTTLE));
                    world.setBlockState(pos, state.with(FIREFLIES, MathHelper.clamp(lantern.getFireflies().size(), 0, 4)));
                    world.playSound(null, pos, RegisterSounds.ITEM_BOTTLE_CATCH_FIREFLY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return ActionResult.SUCCESS;
                }
            }
            if (stack.isOf(Items.GLASS_BOTTLE)) {
                if (!lantern.getFireflies().isEmpty()) {
                    FireflyLanternBlockEntity.FireflyInLantern fireflyInLantern = lantern.getFireflies().get((int) (lantern.getFireflies().size() * Math.random()));
                    Optional<Item> optionalItem = Registry.ITEM.getOrEmpty(WilderWild.id(Objects.equals(fireflyInLantern.color, "on") ? "firefly_bottle" : fireflyInLantern.color + "_firefly_bottle"));
                    Item item = RegisterItems.FIREFLY_BOTTLE;
                    if (optionalItem.isPresent()) {
                        item = optionalItem.get();
                    }
                    world.playSound(null, pos, RegisterSounds.ITEM_BOTTLE_CATCH_FIREFLY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    if (!player.isCreative()) {
                        player.getStackInHand(hand).decrement(1);
                    }
                    ItemStack bottleStack = new ItemStack(item);
                    if (!Objects.equals(fireflyInLantern.customName, "")) {
                        bottleStack.setCustomName(Text.of(fireflyInLantern.customName));
                    }
                    player.getInventory().offerOrDrop(bottleStack);
                    ((FireflyLanternBlockEntity) entity).removeFirefly(fireflyInLantern);
                    world.setBlockState(pos, state.with(FIREFLIES, MathHelper.clamp(lantern.getFireflies().size(), 0, 4)));
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.PASS;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        Direction[] var3 = ctx.getPlacementDirections();

        for (Direction direction : var3) {
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockState = this.getDefaultState().with(HANGING, direction == Direction.UP);
                if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
                    return blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(HANGING) ? HANGING_SHAPE : STANDING_SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HANGING, WATERLOGGED, FIREFLIES);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = attachedDirection(state).getOpposite();
        return Block.sideCoversSmallSquare(world, pos.offset(direction), direction.getOpposite());
    }

    private static Direction attachedDirection(BlockState state) {
        return state.get(HANGING) ? Direction.DOWN : Direction.UP;
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return attachedDirection(state).getOpposite() == direction && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FireflyLanternBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return !world.isClient ? checkType(type, RegisterBlockEntities.FIREFLY_LANTERN, (worldx, pos, statex, blockEntity) -> blockEntity.serverTick(worldx, pos, statex)) : null;
    }

    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        player.addExhaustion(0.005F);
        if (blockEntity instanceof FireflyLanternBlockEntity lanternEntity) {
            boolean silk = EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) != 0 && !lanternEntity.getFireflies().isEmpty();
            if (!silk) {
                if (!world.isClient) {
                    double extraHeight = state.get(Properties.HANGING) ? 0.155 : 0;
                    for (FireflyLanternBlockEntity.FireflyInLantern firefly : lanternEntity.getFireflies()) {
                        Firefly entity = RegisterEntities.FIREFLY.create(world);
                        if (entity != null) {
                            entity.refreshPositionAndAngles(pos.getX() + firefly.pos.x, pos.getY() + firefly.y + extraHeight + 0.11, pos.getZ() + firefly.pos.z, 0, 0);
                            entity.setFromBottle(true);
                            boolean spawned = world.spawnEntity(entity);
                            if (spawned) {
                                entity.hasHome = true;
                                FireflyBrain.rememberHome(entity, entity.getBlockPos());
                                entity.setColor(firefly.color);
                                entity.setScale(1.0F);
                                if (!Objects.equals(firefly.customName, "")) {
                                    entity.setCustomName(Text.of(firefly.customName));
                                }
                            } else {
                                WilderWild.log("Couldn't spawn Firefly from lantern @ " + pos, WilderWild.UNSTABLE_LOGGING);
                            }
                        }
                    }
                }
            }
        }
        player.incrementStat(Stats.MINED.getOrCreateStat(this));
        dropStacks(state, world, pos, blockEntity, player, stack);
        world.playSound(
                null,
                pos,
                SoundEvents.BLOCK_LANTERN_BREAK,
                SoundCategory.BLOCKS,
                1.0F,
                world.random.nextFloat() * 0.1F + 0.8F
            );
    }

    @Deprecated
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        Identifier identifier = this.getLootTableId();
        if (builder.getNullable(LootContextParameters.TOOL) != null) {
            ItemStack stack = builder.get(LootContextParameters.TOOL);
            if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) != 0) {
                if (builder.getNullable(LootContextParameters.BLOCK_ENTITY) != null) {
                    BlockEntity blockEntity = builder.get(LootContextParameters.BLOCK_ENTITY);
                    if (blockEntity instanceof FireflyLanternBlockEntity lanternBlockEntity) {
                        if (!lanternBlockEntity.getFireflies().isEmpty()) {
                            identifier = WilderWild.id("blocks/firefly_lantern_fireflies");
                        }
                    }
                }
            }
        }
        if (identifier == LootTables.EMPTY) {
            return Collections.emptyList();
        } else {
            LootContext lootContext = builder.parameter(LootContextParameters.BLOCK_STATE, state).build(LootContextTypes.BLOCK);
            ServerWorld serverWorld = lootContext.getWorld();
            LootTable lootTable = serverWorld.getServer().getLootManager().getTable(identifier);
            return lootTable.generateLoot(lootContext);
        }
    }

}
