package net.frozenblock.wilderwild.mixin.block.grass;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SpreadingSnowyDirtBlock.class)
public class SpreadingSnowyDirtBlockMixin {

	@ModifyExpressionValue(
		method = "randomTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/SpreadingSnowyDirtBlock;defaultBlockState()Lnet/minecraft/world/level/block/state/BlockState;"
		)
	)
	public BlockState wilderWild$spreadFoliatedGrassLikeGrass(BlockState original) {
		if (original.is(RegisterBlocks.FOLIATED_GRASS)) {
			return Blocks.GRASS_BLOCK.defaultBlockState();
		}
		return original;
	}
}
