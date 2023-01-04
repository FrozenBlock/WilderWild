package net.frozenblock.wilderwild.block;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.entity.DisplayLanternBlockEntity;
import net.frozenblock.wilderwild.item.FireflyBottle;
import net.frozenblock.wilderwild.misc.FireflyColor;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DisplayLanternBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
	public static final IntegerProperty DISPLAY_LIGHT = RegisterProperties.DISPLAY_LIGHT;
	protected static final VoxelShape STANDING_SHAPE = Shapes.or(Block.box(5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D), Block.box(6.0D, 7.0D, 6.0D, 10.0D, 8.0D, 10.0D));
	protected static final VoxelShape HANGING_SHAPE = Shapes.or(Block.box(5.0D, 2.0D, 5.0D, 11.0D, 9.0D, 11.0D), Block.box(6.0D, 9.0D, 6.0D, 10.0D, 10.0D, 10.0D));

	public DisplayLanternBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(HANGING, false).setValue(WATERLOGGED, false).setValue(DISPLAY_LIGHT, 0));
	}

	@Override
	public InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		}
		BlockEntity entity = level.getBlockEntity(pos);
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
						level.setBlockAndUpdate(pos, state.setValue(DISPLAY_LIGHT, Mth.clamp(lantern.getFireflies().size() * 3, 0, 15)));
						level.playSound(null, pos, RegisterSounds.ITEM_BOTTLE_PUT_IN_LANTERN_FIREFLY, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.2F + 0.9F);
						lantern.updateSync();
						return InteractionResult.SUCCESS;
					}
				}
				if (stack.is(Items.GLASS_BOTTLE)) {
					if (!lantern.getFireflies().isEmpty()) {
						DisplayLanternBlockEntity.FireflyInLantern fireflyInLantern = lantern.getFireflies().get(AdvancedMath.random().nextInt(lantern.getFireflies().size()));
						Optional<Item> optionalItem = BuiltInRegistries.ITEM.getOptional(new ResourceLocation(fireflyInLantern.color.getKey().getNamespace(), Objects.equals(fireflyInLantern.color, FireflyColor.ON) ? "firefly_bottle" : fireflyInLantern.color.getKey().getPath() + "_firefly_bottle"));
						Item item = RegisterItems.FIREFLY_BOTTLE;
						if (optionalItem.isPresent()) {
							item = optionalItem.get();
						}
						level.playSound(null, pos, RegisterSounds.ITEM_BOTTLE_CATCH_FIREFLY, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.2F + 0.9F);
						if (!player.isCreative()) {
							player.getItemInHand(hand).shrink(1);
						}
						ItemStack bottleStack = new ItemStack(item);
						if (!Objects.equals(fireflyInLantern.customName, "")) {
							bottleStack.setHoverName(Component.nullToEmpty(fireflyInLantern.customName));
						}
						player.getInventory().placeItemBackInInventory(bottleStack);
						((DisplayLanternBlockEntity) entity).removeFirefly(fireflyInLantern);
						level.setBlockAndUpdate(pos, state.setValue(DISPLAY_LIGHT, Mth.clamp(lantern.getFireflies().size() * 3, 0, 15)));
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
					level.setBlockAndUpdate(pos, state.setValue(DISPLAY_LIGHT, Mth.clamp(light, 0, 15)));
					lantern.inventory.set(0, stack.split(1));
					lantern.updateSync();
					return InteractionResult.SUCCESS;
				}
			} else if (lantern.noFireflies()) {
				Optional<ItemStack> stack1 = lantern.inventory.stream().findFirst();
				if (stack1.isPresent()) {
					popResource(level, pos, stack1.get());
					lantern.inventory.clear();
					lantern.updateSync();
					level.setBlockAndUpdate(pos, state.setValue(DISPLAY_LIGHT, 0));
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
	public VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return state.getValue(HANGING) ? HANGING_SHAPE : STANDING_SHAPE;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(HANGING, WATERLOGGED, DISPLAY_LIGHT);
	}

	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, BlockPos pos) {
		Direction direction = attachedDirection(state).getOpposite();
		return Block.canSupportCenter(level, pos.relative(direction), direction.getOpposite());
	}

	private static Direction attachedDirection(BlockState state) {
		return state.getValue(HANGING) ? Direction.DOWN : Direction.UP;
	}

	@Override
	public PushReaction getPistonPushReaction(@NotNull BlockState state) {
		return PushReaction.DESTROY;
	}

	@Override
	public RenderShape getRenderShape(@NotNull BlockState blockState) {
		return RenderShape.MODEL;
	}

	@Override
	public BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}

		if (attachedDirection(state).getOpposite() == direction && !state.canSurvive(level, currentPos)) {
			BlockEntity entity = level.getBlockEntity(currentPos);
			if (entity instanceof DisplayLanternBlockEntity lanternEntity) {
				lanternEntity.spawnFireflies();
			}
			return Blocks.AIR.defaultBlockState();
		}
		return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
	}

	@Override
	public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			BlockEntity entity = level.getBlockEntity(pos);
			if (entity instanceof DisplayLanternBlockEntity lantern) {
				for (ItemStack item : lantern.inventory) {
					popResource(level, pos, item);
				}
				lantern.inventory.clear();
			}
		}
		super.onRemove(state, level, pos, newState, isMoving);
	}

	@Override
	@NotNull
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull PathComputationType type) {
		return false;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new DisplayLanternBlockEntity(pos, state);
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
		return !level.isClientSide ? createTickerHelper(type, RegisterBlockEntities.DISPLAY_LANTERN, (worldx, pos, statex, blockEntity) -> blockEntity.serverTick(level, pos)) : createTickerHelper(type, RegisterBlockEntities.DISPLAY_LANTERN, (worldx, pos, statex, blockEntity) -> blockEntity.clientTick(level, pos));
	}

	@Override
	public void playerDestroy(Level level, Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity blockEntity, @NotNull ItemStack stack) {
		player.causeFoodExhaustion(0.005F);
		if (!level.isClientSide && blockEntity instanceof DisplayLanternBlockEntity lanternEntity) {
			boolean silk = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0 || player.isCreative();
			if (silk && !lanternEntity.getFireflies().isEmpty()) {
				lanternEntity.spawnFireflies(level);
			}
		}
		player.awardStat(Stats.BLOCK_MINED.get(this));
		dropResources(state, level, pos, blockEntity, player, stack);
	}

	@Override
	@NotNull
	public List<ItemStack> getDrops(@NotNull BlockState state, LootContext.Builder builder) {
		ResourceLocation identifier = this.getLootTable();
		if (builder.getOptionalParameter(LootContextParams.TOOL) != null) {
			ItemStack stack = builder.getParameter(LootContextParams.TOOL);
			if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) != 0) {
				if (builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY) != null) {
					BlockEntity blockEntity = builder.getParameter(LootContextParams.BLOCK_ENTITY);
					if (blockEntity instanceof DisplayLanternBlockEntity lanternBlockEntity) {
						if (!lanternBlockEntity.getFireflies().isEmpty()) {
							identifier = WilderSharedConstants.id("blocks/display_lantern_fireflies");
						}
					}
				}
			}
		}
		if (identifier == BuiltInLootTables.EMPTY) {
			return Collections.emptyList();
		} else {
			LootContext lootContext = builder.withParameter(LootContextParams.BLOCK_STATE, state).create(LootContextParamSets.BLOCK);
			ServerLevel serverLevel = lootContext.getLevel();
			LootTable lootTable = serverLevel.getServer().getLootTables().get(identifier);
			return lootTable.getRandomItems(lootContext);
		}
	}

}
