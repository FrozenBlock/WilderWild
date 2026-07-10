package net.frozenblock.wilderwild.mixin.block.chest;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ChestBlockEntity.class)
public interface ChestBlockEntityAccessor {

	@Invoker("signalOpenCount")
	void wilderWild$signalOpenCount(Level level, BlockPos pos, BlockState blockState, int previous, int current);
}
