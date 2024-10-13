/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.block;

import java.util.List;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.block.property.FlowerColor;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
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
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GloryOfTheSnowBlock extends BushBlock implements BonemealableBlock {
	public static float GROWTH_CHANCE_RANDOM_TICK = 0.9F;
	public static final EnumProperty<FlowerColor> COLOR_STATE = WWBlockStateProperties.FLOWER_COLOR;
	public static final List<FlowerColor> FLOWER_COLORS = List.of(FlowerColor.BLUE, FlowerColor.PINK, FlowerColor.PURPLE, FlowerColor.WHITE);
	private static final VoxelShape SHAPE = Block.box(3D, 0D, 3D, 13D, 4D, 13D);
	private static final VoxelShape GROWN_SHAPE = Block.box(3D, 0D, 3D, 13D, 8D, 13D);

	public GloryOfTheSnowBlock(@NotNull Properties settings) {
		super(settings);
	}

	public static boolean hasColor(@NotNull BlockState state) {
		return state.hasProperty(COLOR_STATE) && state.getValue(COLOR_STATE) != FlowerColor.NONE;
	}

	public static void onShear(@NotNull Level level, BlockPos pos, @NotNull BlockState state, @Nullable Player player) {
		FlowerColor color = state.getValue(COLOR_STATE);
		Item item = color == FlowerColor.BLUE ? WWBlocks.BLUE_GIANT_GLORY_OF_THE_SNOW.asItem() : color == FlowerColor.PINK ? WWBlocks.PINK_GIANT_GLORY_OF_THE_SNOW.asItem() :
			color == FlowerColor.PURPLE ? WWBlocks.VIOLET_BEAUTY_GLORY_OF_THE_SNOW.asItem() : WWBlocks.ALBA_GLORY_OF_THE_SNOW.asItem();
		popResource(level, pos, new ItemStack(item, 1));
		level.setBlockAndUpdate(pos, state.getBlock().defaultBlockState());
		level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1F, 1F);
		level.gameEvent(player, GameEvent.SHEAR, pos);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(COLOR_STATE);
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (random.nextFloat() > GROWTH_CHANCE_RANDOM_TICK && state.getValue(COLOR_STATE) == FlowerColor.NONE) {
			level.setBlockAndUpdate(pos, state.setValue(COLOR_STATE, FLOWER_COLORS.get(random.nextInt(FLOWER_COLORS.size()))));
		}
	}

	@Override
	@NotNull
	public InteractionResult use(
		@NotNull BlockState state,
		@NotNull Level level,
		@NotNull BlockPos pos,
		@NotNull Player player,
		@NotNull InteractionHand hand,
		@NotNull BlockHitResult hit
	) {
		if (level instanceof ServerLevel) {
			ItemStack itemStack = player.getItemInHand(hand);
			if (hasColor(state) && itemStack.is(Items.SHEARS)) {
				onShear(level, pos, state, player);
				itemStack.hurtAndBreak(1, player, (playerx) -> playerx.broadcastBreakEvent(hand));
				return InteractionResult.SUCCESS;
			}
		}
		return super.use(state, level, pos, player, hand, hit);
	}

	@Override
	@NotNull
	public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		VoxelShape shape = state.getValue(COLOR_STATE) == FlowerColor.NONE ? SHAPE : GROWN_SHAPE;
		Vec3 vec3d = state.getOffset(level, pos);
		return shape.move(vec3d.x, vec3d.y, vec3d.z);
	}

	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
		return state.getValue(COLOR_STATE) == FlowerColor.NONE;
	}

	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		level.setBlockAndUpdate(pos, state.setValue(WWBlockStateProperties.FLOWER_COLOR, FLOWER_COLORS.get(AdvancedMath.random().nextInt(FLOWER_COLORS.size()))));
	}
}
