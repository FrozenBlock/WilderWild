/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.block;

import com.mojang.serialization.MapCodec;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.frozenblock.wilderwild.block.entity.DisplayLanternBlockEntity;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColor;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
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
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DisplayLanternBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
	public static final int MAX_FIREFLIES = 4;
	public static final int LIGHT_PER_FIREFLY = 3;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
	public static final IntegerProperty DISPLAY_LIGHT = WWBlockStateProperties.DISPLAY_LIGHT;
	public static final MapCodec<DisplayLanternBlock> CODEC = simpleCodec(DisplayLanternBlock::new);
	protected static final VoxelShape STANDING_SHAPE = Shapes.or(Block.box(5D, 0D, 5D, 11D, 7D, 11.0D), Block.box(6D, 7D, 6D, 10D, 8D, 10D));
	protected static final VoxelShape HANGING_SHAPE = Shapes.or(Block.box(5D, 2D, 5D, 11D, 9D, 11.0D), Block.box(6D, 9D, 6D, 10D, 10D, 10D));

	public DisplayLanternBlock(@NotNull Properties properties) {
		super(properties.pushReaction(PushReaction.DESTROY));
		this.registerDefaultState(this.stateDefinition.any().setValue(HANGING, false).setValue(WATERLOGGED, false).setValue(DISPLAY_LIGHT, 0));
	}

	private static Direction attachedDirection(@NotNull BlockState state) {
		return state.getValue(HANGING) ? Direction.DOWN : Direction.UP;
	}

	@NotNull
	@Override
	protected MapCodec<? extends DisplayLanternBlock> codec() {
		return CODEC;
	}

	@Override
	@NotNull
	public InteractionResult useItemOn(
		@NotNull ItemStack stack,
		@NotNull BlockState state,
		@NotNull Level level,
		@NotNull BlockPos pos,
		@NotNull Player player,
		@NotNull InteractionHand hand,
		@NotNull BlockHitResult hit
	) {
		if (level.isClientSide()) return InteractionResult.SUCCESS;
		if (!(level.getBlockEntity(pos) instanceof DisplayLanternBlockEntity displayLantern)) return InteractionResult.TRY_WITH_EMPTY_HAND;

		if (!displayLantern.invEmpty()) {
			if (displayLantern.noFireflies()) {
				final Optional<ItemStack> lanternStack = displayLantern.inventory.stream().findFirst();
				if (lanternStack.isPresent()) {
					popResource(level, pos, lanternStack.get());
					displayLantern.inventory.clear();
					displayLantern.markForUpdate();
					level.setBlockAndUpdate(pos, state.setValue(DISPLAY_LIGHT, 0));
					level.updateNeighbourForOutputSignal(pos, this);
					return InteractionResult.SUCCESS;
				}
			}
			return InteractionResult.TRY_WITH_EMPTY_HAND;
		}

		final List<DisplayLanternBlockEntity.Occupant> fireflies = displayLantern.getFireflies();
		if (stack.has(WWDataComponents.FIREFLY_COLOR)) {
			final Holder<FireflyColor> fireflyColor = stack.get(WWDataComponents.FIREFLY_COLOR);
			if (fireflyColor != null && fireflies.size() < MAX_FIREFLIES) {
				String name = "";
				if (stack.has(DataComponents.CUSTOM_NAME)) name = stack.getHoverName().getString();
				displayLantern.addFirefly(level, fireflyColor.unwrapKey().orElseThrow().identifier(), name);
				player.getItemInHand(hand).consume(1, player);
				player.getInventory().placeItemBackInInventory(new ItemStack(Items.GLASS_BOTTLE));
				level.setBlockAndUpdate(pos, state.setValue(DISPLAY_LIGHT, Mth.clamp(displayLantern.getFireflies().size() * LIGHT_PER_FIREFLY, 0, LightEngine.MAX_LEVEL)));
				level.playSound(null, pos, WWSounds.ITEM_BOTTLE_PUT_IN_LANTERN_FIREFLY, SoundSource.BLOCKS, 1F, level.random.nextFloat() * 0.2F + 0.9F);
				displayLantern.markForUpdate();
				level.updateNeighbourForOutputSignal(pos, this);
				return InteractionResult.SUCCESS;
			}
		} else if (stack.is(Items.GLASS_BOTTLE)) {
			if (!fireflies.isEmpty()) {
				final DisplayLanternBlockEntity.Occupant fireflyInLantern = Util.getRandom(fireflies, level.random);
				level.playSound(null, pos, WWSounds.ITEM_BOTTLE_CATCH_FIREFLY, SoundSource.BLOCKS, 1F, level.random.nextFloat() * 0.2F + 0.9F);
				player.getItemInHand(hand).consume(1, player);
				final ItemStack bottleStack = new ItemStack(WWItems.FIREFLY_BOTTLE);
				bottleStack.set(
					WWDataComponents.FIREFLY_COLOR,
					level.registryAccess()
						.lookupOrThrow(WilderWildRegistries.FIREFLY_COLOR)
						.get(fireflyInLantern.getColor()).orElseThrow()
				);
				if (!Objects.equals(fireflyInLantern.customName, "")) bottleStack.set(DataComponents.CUSTOM_NAME, Component.nullToEmpty(fireflyInLantern.customName));
				player.getInventory().placeItemBackInInventory(bottleStack);
				displayLantern.removeFirefly(fireflyInLantern);
				level.setBlockAndUpdate(pos, state.setValue(DISPLAY_LIGHT, Mth.clamp(displayLantern.getFireflies().size() * LIGHT_PER_FIREFLY, 0, LightEngine.MAX_LEVEL)));
				displayLantern.markForUpdate();
				level.updateNeighbourForOutputSignal(pos, this);
				return InteractionResult.SUCCESS;
			}
		}
		if (!stack.isEmpty() && displayLantern.noFireflies()) {
			int light = 0;
			if (stack.getItem() instanceof BlockItem blockItem) {
				light = blockItem.getBlock().defaultBlockState().getLightEmission();
			} else if (stack.isEnchanted()) {
				light = (int) Math.round(stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).size() * 0.5D);
			}
			level.setBlockAndUpdate(pos, state.setValue(DISPLAY_LIGHT, Mth.clamp(light, 0, LightEngine.MAX_LEVEL)));
			displayLantern.inventory.set(0, stack.split(1));
			displayLantern.markForUpdate();
			level.updateNeighbourForOutputSignal(pos, this);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.TRY_WITH_EMPTY_HAND;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		final FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
		for (Direction direction : context.getNearestLookingDirections()) {
			if (direction.getAxis() != Direction.Axis.Y) continue;
			final BlockState state = this.defaultBlockState().setValue(HANGING, direction == Direction.UP);
			if (state.canSurvive(context.getLevel(), context.getClickedPos())) return state.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
		}
		return null;
	}

	@Override
	@NotNull
	public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return state.getValue(HANGING) ? HANGING_SHAPE : STANDING_SHAPE;
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(HANGING, WATERLOGGED, DISPLAY_LIGHT);
	}

	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
		final Direction direction = attachedDirection(state).getOpposite();
		return Block.canSupportCenter(level, pos.relative(direction), direction.getOpposite());
	}

	@Override
	@NotNull
	public RenderShape getRenderShape(@NotNull BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	protected @NotNull BlockState updateShape(
		@NotNull BlockState state,
		LevelReader level,
		ScheduledTickAccess scheduledTickAccess,
		BlockPos pos,
		Direction direction,
		BlockPos neighborPos,
		BlockState neighborState,
		RandomSource random
	) {
		if (state.getValue(WATERLOGGED)) scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		if (attachedDirection(state).getOpposite() == direction && !state.canSurvive(level, pos)) {
			if (level.getBlockEntity(pos) instanceof DisplayLanternBlockEntity lanternEntity) lanternEntity.spawnFireflies();
			return Blocks.AIR.defaultBlockState();
		}
		return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
	}

	@Override
	protected void affectNeighborsAfterRemoval(BlockState state, @NotNull ServerLevel level, BlockPos pos, boolean bl) {
		if (!(level.getBlockEntity(pos) instanceof DisplayLanternBlockEntity displayLantern)) return;
		for (ItemStack item : displayLantern.inventory) popResource(level, pos, item);
		displayLantern.inventory.clear();
		level.updateNeighbourForOutputSignal(pos, this);
	}

	@Override
	@NotNull
	public FluidState getFluidState(@NotNull BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType type) {
		return false;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new DisplayLanternBlockEntity(pos, state);
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
		return !level.isClientSide() ?
			createTickerHelper(type, WWBlockEntityTypes.DISPLAY_LANTERN, (levelx, pos, statex, blockEntity) -> blockEntity.serverTick(levelx, pos)) :
			createTickerHelper(type, WWBlockEntityTypes.DISPLAY_LANTERN, (levelx, pos, statex, blockEntity) -> blockEntity.clientTick(levelx));
	}

	@Override
	public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
		return true;
	}

	@Override
	protected int getAnalogOutputSignal(@NotNull BlockState state, Level level, @NotNull BlockPos pos, Direction direction) {
		if (level.getBlockEntity(pos) instanceof DisplayLanternBlockEntity displayLantern) return displayLantern.getComparatorOutput();
		return 0;
	}

	@Override
	public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack) {
		if (!level.isClientSide() && blockEntity instanceof DisplayLanternBlockEntity lanternEntity) {
			final var silkTouch = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH);
			if (EnchantmentHelper.getItemEnchantmentLevel(silkTouch, stack) == 0) lanternEntity.spawnFireflies(level);
		}
		super.playerDestroy(level, player, pos, state, blockEntity, stack);
	}

}
