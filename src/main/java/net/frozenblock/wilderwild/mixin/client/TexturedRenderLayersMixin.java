package net.frozenblock.wilderwild.mixin.client;

import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.entity.render.StoneChestBlockEntityRenderer;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TexturedRenderLayers.class)
public abstract class TexturedRenderLayersMixin {
    @Shadow
    private static SpriteIdentifier getChestTexture(ChestType type, SpriteIdentifier single, SpriteIdentifier left, SpriteIdentifier right) {
        return null;
    }

    @Inject(method = "getChestTexture(Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/block/enums/ChestType;Z)Lnet/minecraft/client/util/SpriteIdentifier;", at = @At("HEAD"), cancellable = true)
    private static void getChestTexture(BlockEntity blockEntity, ChestType type, boolean christmas, CallbackInfoReturnable<SpriteIdentifier> cir) {
        if (blockEntity instanceof StoneChestBlockEntity) {
            cir.setReturnValue(getChestTexture(type, WilderWildClient.STONE, WilderWildClient.STONE_LEFT, WilderWildClient.STONE_RIGHT));
        }
    }
}
