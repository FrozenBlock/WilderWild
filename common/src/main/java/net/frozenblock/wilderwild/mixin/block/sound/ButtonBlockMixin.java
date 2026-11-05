package net.frozenblock.wilderwild.mixin.block.sound;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.registry.WWConfigPredicates;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ButtonBlock.class)
public class ButtonBlockMixin {

	@ModifyExpressionValue(
		method = "playSound",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/ButtonBlock;getSound(Z)Lnet/minecraft/sounds/SoundEvent;"
		)
	)
	public SoundEvent wilderWild$paleOakButtonSounds(
		SoundEvent original,
		@Local(argsOnly = true) LevelAccessor level
	) {
		if (Block.class.cast(this) != Blocks.PALE_OAK_BUTTON) return original;
		if (!WWConfigPredicates.testPaleOakWoodPredicate(level.registryAccess())) return original;

		if (original == SoundEvents.WOODEN_BUTTON_CLICK_ON) return WWSounds.BLOCK_PALE_OAK_BUTTON_CLICK_ON.get();
		if (original == SoundEvents.WOODEN_BUTTON_CLICK_OFF) return WWSounds.BLOCK_PALE_OAK_BUTTON_CLICK_OFF.get();

		return original;
	}
}
