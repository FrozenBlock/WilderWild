package net.frozenblock.wilderwild.mixin.snowlogging.client;

import net.frozenblock.wilderwild.entity.impl.BlockDestructionProgressInterface;
import net.minecraft.server.level.BlockDestructionProgress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockDestructionProgress.class)
public class BlockDestructionProgressMixin implements BlockDestructionProgressInterface {
	@Unique
	private boolean wilderWild$isBreakingOriginal = true;


	@Override
	public void wilderWild$breakingOriginal(boolean breakingOriginal) {
		wilderWild$isBreakingOriginal = breakingOriginal;
	}

	@Override
	public boolean wilderWild$isBreakingOriginal() {
		return wilderWild$isBreakingOriginal;
	}
}
