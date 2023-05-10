/*
 * Copyright 2022-2023 FrozenBlock
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
import net.frozenblock.wilderwild.misc.FlowerColor;
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

public class GloryOfTheSnowBlock extends BushBlock implements BonemealableBlock {
    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);
    public static final EnumProperty<FlowerColor> COLORS = RegisterProperties.FLOWER_COLOR;

    public final List<FlowerColor> COLOR_LIST;

    public GloryOfTheSnowBlock(Properties settings, List<FlowerColor> list) {
        super(settings);
        this.COLOR_LIST = list;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COLORS);
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, RandomSource random) {
        if (random.nextFloat() > 0.9F && state.getValue(COLORS) == FlowerColor.NONE) {
            level.setBlockAndUpdate(pos, state.setValue(COLORS, COLOR_LIST.get(random.nextInt(COLOR_LIST.size()))));
        }
    }

    @Override
	@NotNull
    public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (level instanceof ServerLevel) {
            FlowerColor color = state.getValue(COLORS);
            if (color != FlowerColor.NONE) {
                ItemStack itemStack = player.getItemInHand(hand);
                if (itemStack.is(Items.SHEARS)) {
                    Item item = color == FlowerColor.BLUE ? RegisterBlocks.BLUE_GLORY_OF_THE_SNOW.asItem() : color == FlowerColor.PINK ? RegisterBlocks.PINK_GLORY_OF_THE_SNOW.asItem() :
                            color == FlowerColor.PURPLE ? RegisterBlocks.PURPLE_GLORY_OF_THE_SNOW.asItem() : RegisterBlocks.WHITE_GLORY_OF_THE_SNOW.asItem();
                    ItemStack stack = new ItemStack(item);
                    stack.setCount(level.random.nextIntBetweenInclusive(1, 2));
                    popResource(level, pos, stack);
                    level.setBlockAndUpdate(pos, state.getBlock().defaultBlockState());
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
                    itemStack.hurtAndBreak(1, player, (playerx) -> playerx.broadcastBreakEvent(hand));
                    level.gameEvent(player, GameEvent.SHEAR, pos);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.use(state, level, pos, player, hand, hit);

    }

	@Override
	@NotNull
    public VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Vec3 vec3d = state.getOffset(level, pos);
        return SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient) {
        return state.getValue(COLORS) == FlowerColor.NONE;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        level.setBlockAndUpdate(pos, state.setValue(RegisterProperties.FLOWER_COLOR, this.COLOR_LIST.get(AdvancedMath.random().nextInt(this.COLOR_LIST.size()))));
    }
}
