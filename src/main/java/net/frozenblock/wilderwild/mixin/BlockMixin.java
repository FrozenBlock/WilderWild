package net.frozenblock.wilderwild.mixin;

import net.minecraft.core.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin {
	@Shadow protected abstract Block asBlock();

	private static final SoundType MUSHROOMS = new SoundType(1.0F, 1.0F, RegisterSounds.BLOCK_MUSHROOM_BREAK, RegisterSounds.BLOCK_MUSHROOM_STEP, RegisterSounds.BLOCK_MUSHROOM_PLACE, RegisterSounds.BLOCK_MUSHROOM_HIT, RegisterSounds.BLOCK_MUSHROOM_FALL);
  
	@Inject(method = "getSoundType", at = @At("TAIL"), cancellable = true)
	private void getSoundGroupMixin(CallbackInfoReturnable<SoundType> cir){
		String blockId = Registry.BLOCK.getKey(this.asBlock()).getPath();
		cir.setReturnValue(
				switch (blockId){
					case
							"red_mushroom",
							"brown_mushroom"
							-> MUSHROOMS;
          
					default -> cir.getReturnValue();
				}
		);
	}
}
