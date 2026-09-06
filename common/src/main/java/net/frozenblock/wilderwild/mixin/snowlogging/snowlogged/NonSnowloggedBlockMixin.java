package net.frozenblock.wilderwild.mixin.snowlogging.snowlogged;

import net.frozenblock.wilderwild.block.snowlogging.SnowloggedBlockSettings;
import net.minecraft.world.level.block.LeafLitterBlock;
import net.minecraft.world.level.block.LilyPadBlock;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.TallSeagrassBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin({
	LeafLitterBlock.class,
	LilyPadBlock.class,
	SeaPickleBlock.class,
	SeagrassBlock.class,
	TallSeagrassBlock.class
})
public class NonSnowloggedBlockMixin implements SnowloggedBlockSettings {

	@Unique
	@Override
	public boolean wilderWild$snowloggingEnabled() {
		return false;
	}
}
