package net.frozenblock.wilderwild.misc.interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface ChestBlockEntityInterface {

	boolean getCanBubble();

	void setCanBubble(boolean b);

	void setBubbleTicks(int i);

	int getBubbleTick();

	void bubble();

	void bubbleBurst();

	void setHasJellyfish(boolean b);

	boolean getHasJellyfish();

	void releaseJellyfish(Level level, BlockState state, BlockPos pos);
}
