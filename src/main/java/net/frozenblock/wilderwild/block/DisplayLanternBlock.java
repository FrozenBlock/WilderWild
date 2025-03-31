/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.block;

import com.mojang.serialization.MapCodec;
import java.util.Objects;
import java.util.Optional;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.block.entity.DisplayLanternBlockEntity;
import net.frozenblock.wilderwild.item.MobBottleItem;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
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

	public DisplayLanternBlock(@NotNull Properties settings) {
		super(settings.pushReaction(PushReaction.DESTROY));
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
		@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit
	) {
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		}
		BlockEntity entity = level.getBlockEntity(pos);
		if (entity instanceof DisplayLanternBlockEntity lantern) {
			if (lantern.invEmpty()) {
				if (stack.has(WWDataComponents.BOTTLE_ENTITY_DATA)) {
					CustomData colorData = stack.get(WWDataComponents.BOTTLE_ENTITY_DATA);
					if (colorData != null && !colorData.isEmpty()) {
						CompoundTag tag = colorData.copyTag();
						if (tag.contains(MobBottleItem.FIREFLY_BOTTLE_VARIANT_FIELD)) {
							ResourceLocation color = ResourceLocation.tryParse(tag.getString(MobBottleItem.FIREFLY_BOTTLE_VARIANT_FIELD));

							if (lantern.getFireflies().size() < MAX_FIREFLIES) {
								String name = "";
								if (stack.has(DataComponents.CUSTOM_NAME)) {
									name = stack.getHoverName().getString();
								}
								lantern.addFirefly(level, color, name);
								if (!player.isCreative()) {
									player.getItemInHand(hand).shrink(1);
								}
								player.getInventory().placeItemBackInInventory(new ItemStack(Items.GLASS_BOTTLE));
								level.setBlockAndUpdate(pos, state.setValue(DISPLAY_LIGHT, Mth.clamp(lantern.getFireflies().size() * LIGHT_PER_FIREFLY, 0, LightEngine.MAX_LEVEL)));
								level.playSound(null, pos, WWSounds.ITEM_BOTTLE_PUT_IN_LANTERN_FIREFLY, SoundSource.BLOCKS, 1F, level.random.nextFloat() * 0.2F + 0.9F);
								lantern.markForUpdate();
								level.updateNeighbourForOutputSignal(pos, this);
								return InteractionResult.SUCCESS;
							}
						}
					}
				} else if (stack.is(Items.GLASS_BOTTLE)) {
					if (!lantern.getFireflies().isEmpty()) {
						DisplayLanternBlockEntity.Occupant fireflyInLantern = lantern.getFireflies().get(AdvancedMath.random().nextInt(lantern.getFireflies().size()));
						level.playSound(null, pos, WWSounds.ITEM_BOTTLE_CATCH_FIREFLY, SoundSource.BLOCKS, 1F, level.random.nextFloat() * 0.2F + 0.9F);
						if (!player.getAbilities().instabuild) {
							player.getItemInHand(hand).shrink(1);
						}
						ItemStack bottleStack = new ItemStack(WWItems.FIREFLY_BOTTLE);
						CustomData.update(
							WWDataComponents.BOTTLE_ENTITY_DATA,
							bottleStack,
							compoundTag -> compoundTag.putString(MobBottleItem.FIREFLY_BOTTLE_VARIANT_FIELD, fireflyInLantern.getColor().toString())
						);
						if (!Objects.equals(fireflyInLantern.customName, "")) {
							bottleStack.set(DataComponents.CUSTOM_NAME, Component.nullToEmpty(fireflyInLantern.customName));
						}
						player.getInventory().placeItemBackInInventory(bottleStack);
						((DisplayLanternBlockEntity) entity).removeFirefly(fireflyInLantern);
						level.setBlockAndUpdate(pos, state.setValue(DISPLAY_LIGHT, Mth.clamp(lantern.getFireflies().size() * LIGHT_PER_FIREFLY, 0, LightEngine.MAX_LEVEL)));
						lantern.markForUpdate();
						level.updateNeighbourForOutputSignal(pos, this);
						return InteractionResult.SUCCESS;
					}
				}
				if (!stack.isEmpty() && lantern.noFireflies()) {
					int light = 0;
					if (stack.getItem() instanceof BlockItem blockItem) {
						light = blockItem.getBlock().defaultBlockState().getLightEmission();
					} else if (stack.isEnchanted()) {
						light = (int) Math.round(stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).size() * 0.5D);
					}
					level.setBlockAndUpdate(pos, state.setValue(DISPLAY_LIGHT, Mth.clamp(light, 0, LightEngine.MAX_LEVEL)));
					lantern.inventory.set(0, stack.split(1));
					lantern.markForUpdate();
					level.updateNeighbourForOutputSignal(pos, this);
					return InteractionResult.SUCCESS;
				}
			} else if (lantern.noFireflies()) {
				Optional<ItemStack> stack1 = lantern.inventory.stream().findFirst();
				if (stack1.isPresent()) {
					popResource(level, pos, stack1.get());
					lantern.inventory.clear();
					lantern.markForUpdate();
					level.setBlockAndUpdate(pos, state.setValue(DISPLAY_LIGHT, 0));
					level.updateNeighbourForOutputSignal(pos, this);
					return InteractionResult.SUCCESS;
				}
			}
		}
		return InteractionResult.TRY_WITH_EMPTY_HAND;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext ctx) {
		FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
		for (Direction direction : ctx.getNearestLookingDirections()) {
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
		Direction direction = attachedDirection(state).getOpposite();
		return Block.canSupportCenter(level, pos.relative(direction), direction.getOpposite());
	}

	@Override
	@NotNull
	public RenderShape getRenderShape(@NotNull BlockState blockState) {
		return RenderShape.MODEL;
	}

	@Override
	protected @NotNull BlockState updateShape(
		@NotNull BlockState blockState,
		LevelReader levelReader,
		ScheduledTickAccess scheduledTickAccess,
		BlockPos blockPos,
		Direction direction,
		BlockPos neighborPos,
		BlockState neighborState,
		RandomSource randomSource
	) {
		if (blockState.getValue(WATERLOGGED)) {
			scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
		}

		if (attachedDirection(blockState).getOpposite() == direction && !blockState.canSurvive(levelReader, blockPos)) {
			BlockEntity entity = levelReader.getBlockEntity(blockPos);
			if (entity instanceof DisplayLanternBlockEntity lanternEntity) {
				lanternEntity.spawnFireflies();
			}
			return Blocks.AIR.defaultBlockState();
		}
		return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, neighborPos, neighborState, randomSource);
	}

	@Override
	public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
		if (!state.is(newState.getBlock())) {
			if (level.getBlockEntity(pos) instanceof DisplayLanternBlockEntity lantern) {
				for (ItemStack item : lantern.inventory) {
					popResource(level, pos, item);
				}
				lantern.inventory.clear();
				level.updateNeighbourForOutputSignal(pos, this);
			}
		}
		super.onRemove(state, level, pos, newState, movedByPiston);
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
		return !level.isClientSide ?
			createTickerHelper(type, WWBlockEntityTypes.DISPLAY_LANTERN, (levelx, pos, statex, blockEntity) -> blockEntity.serverTick(levelx, pos)) :
			createTickerHelper(type, WWBlockEntityTypes.DISPLAY_LANTERN, (levelx, pos, statex, blockEntity) -> blockEntity.clientTick(levelx));
	}

	@Override
	public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity instanceof DisplayLanternBlockEntity displayLanternBlockEntity) {
			return displayLanternBlockEntity.getComparatorOutput();
		}
		return 0;
	}

	@Override
	public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity blockEntity, @NotNull ItemStack stack) {
		if (!level.isClientSide && blockEntity instanceof DisplayLanternBlockEntity lanternEntity) {
			var silkTouch = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH);
			if (EnchantmentHelper.getItemEnchantmentLevel(silkTouch, stack) == 0) {
				lanternEntity.spawnFireflies(level);
			}
		}
		super.playerDestroy(level, player, pos, state, blockEntity, stack);
	}

}
