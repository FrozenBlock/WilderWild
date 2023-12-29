package net.frozenblock.wilderwild.mixin.client.brush;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.ScorchedBlock;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

	@Shadow
	@Nullable
	private ClientLevel level;

	@ModifyExpressionValue(method = "levelEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;"))
	private Block scorchedBrush(Block original, int eventId, BlockPos pos, int data) {
		if (this.level == null) return original;
		if (original instanceof ScorchedBlock scorchedBlock) {
			this.level.playLocalSound(pos, scorchedBlock.brushCompletedSound, SoundSource.PLAYERS, 1.0F, 1.0F, false);
		}
		return original;
	}

}
