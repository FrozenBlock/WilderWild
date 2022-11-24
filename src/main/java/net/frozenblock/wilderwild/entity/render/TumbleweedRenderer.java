package net.frozenblock.wilderwild.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class TumbleweedRenderer extends MobRenderer<Tumbleweed, TumbleweedModel<Tumbleweed>> {

	public TumbleweedRenderer(Context context) {
		super(context, new TumbleweedModel<>(context.bakeLayer(WilderWildClient.TUMBLEWEED)), 0.6F);
	}

	@Override
	public ResourceLocation getTextureLocation(@NotNull Tumbleweed entity) {
		return WilderWild.id("textures/entity/tumbleweed/tumbleweed.png");
	}

}
