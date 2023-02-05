package net.frozenblock.wilderwild.misc.interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface ChestBlockEntityInterface {

	boolean wilderWild$getCanBubble();

	void wilderWild$setCanBubble(boolean b);

	void wilderWild$bubble(Level level, BlockPos pos, BlockState state);

	void wilderWild$bubbleBurst(BlockState state);

	void wilderWild$syncBubble(ChestBlockEntity chest1, ChestBlockEntity chest2);
}
