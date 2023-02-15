package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;

public class ScorchedSandBlock extends Block {
	public static final Map<BlockState, BlockState> SCORCH_MAP = new HashMap<>();
	public static final IntegerProperty CRACKEDNESS = RegisterProperties.CRACKEDNESS;

	public final BlockState wetState;

	public ScorchedSandBlock(Properties settings, BlockState wetState) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(CRACKEDNESS, 0));
		this.wetState = wetState;
		this.fillScorchMap(wetState, this.defaultBlockState());
	}

	public void fillScorchMap(BlockState wetState, BlockState defaultState) {
		SCORCH_MAP.put(wetState, defaultState);
		SCORCH_MAP.put(defaultState, defaultState.setValue(RegisterProperties.CRACKEDNESS, 1));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(CRACKEDNESS);
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
				if (state.getValue(CRACKEDNESS) == 1) {
					level.setBlockAndUpdate(pos, this.defaultBlockState().setValue(CRACKEDNESS, 0));
					level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
				} else if (state.getValue(CRACKEDNESS) == 0) {
					level.setBlockAndUpdate(pos, this.wetState);
					level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
				}
			}
		}
	}
}

