package net.frozenblock.wilderwild.mixin.snowlogging.snowlogged;

import net.frozenblock.wilderwild.block.snowlogging.SimpleSnowloggedBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.WallBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin({
	FenceBlock.class,
	FenceGateBlock.class,
	WallBlock.class
})
public class SnowloggedBlockadeMixin implements SimpleSnowloggedBlock {

	@Unique
	@Override
	public boolean wilderWild$snowloggingEnabled() {
		return true;
	}

	@Unique
	@Override
	public boolean wilderWild$isBlockade() {
		return true;
	}
}
