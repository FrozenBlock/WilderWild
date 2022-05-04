package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin {
	@Shadow protected abstract Block asBlock();

	private static final BlockSoundGroup MUSHROOMS = new BlockSoundGroup(1.0F, 1.0F, RegisterSounds.BLOCK_MUSHROOM_BREAK, RegisterSounds.BLOCK_MUSHROOM_STEP, RegisterSounds.BLOCK_MUSHROOM_PLACE, RegisterSounds.BLOCK_MUSHROOM_HIT, RegisterSounds.BLOCK_MUSHROOM_FALL);
  
	@Inject(method = "getSoundGroup", at = @At("TAIL"), cancellable = true)
	private void getSoundGroupMixin(BlockState state, CallbackInfoReturnable<BlockSoundGroup> cir){
		String blockId = String.valueOf(Registry.BLOCK.getKey(state.getBlock()).get());
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
