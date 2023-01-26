package net.frozenblock.wilderwild.misc.mod_compat.scp;

import net.frozenblock.lib.integration.api.ModIntegration;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

public class AbstractSimpleCopperPipesIntegration extends ModIntegration {
    public AbstractSimpleCopperPipesIntegration() {
        super("copper_pipe");
    }

	public void init() {}

	public boolean addHornNbtToBlock(ServerLevel level, BlockPos pos, Entity owner) {
		return false;
	}

	public boolean isCopperPipe(BlockState state) {
		return false;
	}

}
