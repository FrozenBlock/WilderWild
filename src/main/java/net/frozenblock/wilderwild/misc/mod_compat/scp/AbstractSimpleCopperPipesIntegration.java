package net.frozenblock.wilderwild.misc.mod_compat.scp;

import net.frozenblock.lib.integration.api.ModIntegration;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractSimpleCopperPipesIntegration extends ModIntegration {

	public AbstractSimpleCopperPipesIntegration() {
		super("copper_pipe");
	}

	@Override
	public void init() {
	}

	public abstract boolean addHornNbtToBlock(ServerLevel level, BlockPos pos, Entity owner);

	public abstract boolean isCopperPipe(BlockState state);
}
