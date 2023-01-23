package net.frozenblock.wilderwild.misc.interfaces;

import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface ChestBlockEntityInterface {

	boolean getCanBubble();

	void setCanBubble(boolean b);

	void setBubbleTicks(int i);

	int getBubbleTick();

	void bubble(BlockState state);

	void bubbleBurst(BlockState state);

	void syncBubble(ChestBlockEntity chest1, ChestBlockEntity chest2);
}
