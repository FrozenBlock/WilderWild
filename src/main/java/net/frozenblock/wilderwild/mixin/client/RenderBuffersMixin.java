/*package net.frozenblock.wilderwild.mixin.client;

import com.mojang.blaze3d.vertex.BufferBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWildClient;
import net.minecraft.Util;
import net.minecraft.client.renderer.ChunkBufferBuilderPack;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.ModelBakery;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.SortedMap;

@Environment(EnvType.CLIENT)
@Mixin(RenderBuffers.class)
public class RenderBuffersMixin {

    @Shadow
    private static void put(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> object2ObjectLinkedOpenHashMap, RenderType renderType) {
    }

    @Shadow
    @Final
    private ChunkBufferBuilderPack fixedBufferPack;
    @Shadow
    @Final
    @Mutable
    private final SortedMap<RenderType, BufferBuilder> fixedBuffers = Util.make(new Object2ObjectLinkedOpenHashMap<>(), object2ObjectLinkedOpenHashMap -> {
        object2ObjectLinkedOpenHashMap.put(Sheets.solidBlockSheet(), this.fixedBufferPack.builder(RenderType.solid()));
        object2ObjectLinkedOpenHashMap.put(Sheets.cutoutBlockSheet(), this.fixedBufferPack.builder(RenderType.cutout()));
        object2ObjectLinkedOpenHashMap.put(Sheets.bannerSheet(), this.fixedBufferPack.builder(RenderType.cutoutMipped()));
        object2ObjectLinkedOpenHashMap.put(Sheets.translucentCullBlockSheet(), this.fixedBufferPack.builder(RenderType.translucent()));
        object2ObjectLinkedOpenHashMap.put(Sheets.translucentCullBlockSheet(), this.fixedBufferPack.builder(WilderWildClient.translucentCutout()));
        put(object2ObjectLinkedOpenHashMap, Sheets.shieldSheet());
        put(object2ObjectLinkedOpenHashMap, Sheets.bedSheet());
        put(object2ObjectLinkedOpenHashMap, Sheets.shulkerBoxSheet());
        put(object2ObjectLinkedOpenHashMap, Sheets.signSheet());
        put(object2ObjectLinkedOpenHashMap, Sheets.chestSheet());
        put(object2ObjectLinkedOpenHashMap, RenderType.translucentNoCrumbling());
        put(object2ObjectLinkedOpenHashMap, RenderType.armorGlint());
        put(object2ObjectLinkedOpenHashMap, RenderType.armorEntityGlint());
        put(object2ObjectLinkedOpenHashMap, RenderType.glint());
        put(object2ObjectLinkedOpenHashMap, RenderType.glintDirect());
        put(object2ObjectLinkedOpenHashMap, RenderType.glintTranslucent());
        put(object2ObjectLinkedOpenHashMap, RenderType.entityGlint());
        put(object2ObjectLinkedOpenHashMap, RenderType.entityGlintDirect());
        put(object2ObjectLinkedOpenHashMap, RenderType.waterMask());
        ModelBakery.DESTROY_TYPES.forEach(renderType -> put(object2ObjectLinkedOpenHashMap, renderType));
    });
}
*/