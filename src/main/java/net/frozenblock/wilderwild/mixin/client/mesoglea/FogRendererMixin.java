package net.frozenblock.wilderwild.mixin.client.mesoglea;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(FogRenderer.class)
public class FogRendererMixin {

	@ModifyExpressionValue(
		method = "setupColor",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/biome/Biome;getWaterFogColor()I"
		)
	)
	private static int wilderWild$replaceWaterFogColorInMesoglea(int original, Camera camera, float tickDelta, ClientLevel clientLevel) {
		BlockState state = clientLevel.getBlockState(camera.getBlockPosition());
		if (state.getBlock() instanceof MesogleaBlock mesogleaBlock) {
			return mesogleaBlock.getWaterFogColorOverride();
		}
		return original;
	}

}
