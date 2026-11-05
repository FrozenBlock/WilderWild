package net.frozenblock.wilderwild.mixin.block.sound;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.registry.WWConfigPredicates;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DoorBlock.class)
public class DoorBlockMixin {

	@ModifyExpressionValue(
		method = "playSound",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/properties/BlockSetType;doorOpen()Lnet/minecraft/sounds/SoundEvent;"
		)
	)
	public SoundEvent wilderWild$paleOakDoorOpenSound(
		SoundEvent original,
		@Local(argsOnly = true) Level level
	) {
		if (Block.class.cast(this) != Blocks.PALE_OAK_DOOR) return original;
		if (!WWConfigPredicates.testPaleOakWoodPredicate(level.registryAccess())) return original;

		return WWSounds.BLOCK_PALE_OAK_WOOD_DOOR_OPEN.get();
	}

	@ModifyExpressionValue(
		method = "playSound",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/properties/BlockSetType;doorClose()Lnet/minecraft/sounds/SoundEvent;"
		)
	)
	public SoundEvent wilderWild$paleOakDoorCloseSound(
		SoundEvent original,
		@Local(argsOnly = true) Level level
	) {
		if (Block.class.cast(this) != Blocks.PALE_OAK_DOOR) return original;
		if (!WWConfigPredicates.testPaleOakWoodPredicate(level.registryAccess())) return original;

		return WWSounds.BLOCK_PALE_OAK_WOOD_DOOR_CLOSE.get();
	}
}
