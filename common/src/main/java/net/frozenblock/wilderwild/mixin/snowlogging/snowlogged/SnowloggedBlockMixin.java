package net.frozenblock.wilderwild.mixin.snowlogging.snowlogged;

import net.frozenblock.wilderwild.block.snowlogging.SimpleSnowloggedBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerBedBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.VineBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin({
	DoublePlantBlock.class,
	FlowerBedBlock.class,
	SugarCaneBlock.class,
	SweetBerryBushBlock.class,
	VegetationBlock.class,
	VineBlock.class,
})
public class SnowloggedBlockMixin implements SimpleSnowloggedBlock {

	@Unique
	@Override
	public boolean wilderWild$snowloggingEnabled() {
		return true;
	}

	@Unique
	@Override
	public boolean wilderWild$isBlockade() {
		return false;
	}
}
