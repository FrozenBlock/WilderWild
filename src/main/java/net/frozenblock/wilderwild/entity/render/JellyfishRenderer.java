package net.frozenblock.wilderwild.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class JellyfishRenderer extends MobRenderer<Jellyfish, JellyfishModel<Jellyfish>> {

    private static final ResourceLocation TEXTURE = WilderWild.id("textures/entity/jellyfish/jellyfish.png");

    public JellyfishRenderer(Context context) {
        super(context, new JellyfishModel<>(context.bakeLayer(WilderWildClient.JELLYFISH)), 0.9F);
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull Jellyfish jellyfish) {
        return TEXTURE;
    }
}
