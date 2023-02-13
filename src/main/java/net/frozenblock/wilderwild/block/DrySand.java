package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class DrySand extends Block {
	public static final IntegerProperty CRACKNESS = RegisterProperties.CRACKNESS;

	public DrySand(Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(CRACKNESS, 0));
	}
	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(CRACKNESS);
	}

	protected static boolean shouldHandlePrecipitation(Level level, Biome.Precipitation precipitation) {
		if (precipitation == Biome.Precipitation.RAIN) {
			return level.getRandom().nextFloat() < 0.75F;
		} else {
			return false;
		}
	}

	@Override
	public void handlePrecipitation(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Biome.@NotNull Precipitation precipitation) {
		if (shouldHandlePrecipitation(level, precipitation)) {
			if (precipitation == Biome.Precipitation.RAIN) {
				if (state.getValue(CRACKNESS) == 1) {
					level.setBlockAndUpdate(pos, RegisterBlocks.DRY_SAND.defaultBlockState().setValue(CRACKNESS, 0));
					level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
				} else if (state.getValue(CRACKNESS) == 0) {
					level.setBlockAndUpdate(pos, Blocks.SAND.defaultBlockState());
					level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
				}
			}
		}
	}
}

