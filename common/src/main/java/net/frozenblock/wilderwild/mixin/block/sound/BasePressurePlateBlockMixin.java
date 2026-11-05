package net.frozenblock.wilderwild.mixin.block.sound;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.registry.WWConfigPredicates;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BasePressurePlateBlock.class)
public class BasePressurePlateBlockMixin {

	@ModifyExpressionValue(
		method = "checkPressed",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/properties/BlockSetType;pressurePlateClickOff()Lnet/minecraft/sounds/SoundEvent;"
		)
	)
	public SoundEvent wilderWild$paleOakPressurePlateClickOffSound(
		SoundEvent original,
		@Local(argsOnly = true) Level level
	) {
		if (Block.class.cast(this) != Blocks.PALE_OAK_PRESSURE_PLATE) return original;
		if (!WWConfigPredicates.testPaleOakWoodPredicate(level.registryAccess())) return original;

		return WWSounds.BLOCK_PALE_OAK_WOOD_PRESSURE_PLATE_CLICK_OFF.get();
	}

	@ModifyExpressionValue(
		method = "checkPressed",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/properties/BlockSetType;pressurePlateClickOn()Lnet/minecraft/sounds/SoundEvent;"
		)
	)
	public SoundEvent wilderWild$paleOakPressurePlateClickOnSound(
		SoundEvent original,
		@Local(argsOnly = true) Level level
	) {
		if (Block.class.cast(this) != Blocks.PALE_OAK_PRESSURE_PLATE) return original;
		if (!WWConfigPredicates.testPaleOakWoodPredicate(level.registryAccess())) return original;

		return WWSounds.BLOCK_PALE_OAK_WOOD_PRESSURE_PLATE_CLICK_ON.get();
	}
}
