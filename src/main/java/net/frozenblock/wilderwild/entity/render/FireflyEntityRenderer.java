package net.frozenblock.wilderwild.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WildClientMod;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.FireflyEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class FireflyEntityRenderer extends MobEntityRenderer<FireflyEntity, FireflyEntityModel<FireflyEntity>> {
    private static final Identifier TEXTURE = new Identifier(WilderWild.MOD_ID, "textures/entity/firefly/firefly_off.png");
    private static final Identifier LIGHT_LAYER = new Identifier(WilderWild.MOD_ID, "textures/entity/firefly/firefly_on.png");

    public FireflyEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new FireflyEntityModel<>(context.getPart(WildClientMod.MODEL_FIREFLY_LAYER)), 0.3F);
        //this.addFeature(new FireflyFeatureRenderer<>(this, LIGHT_LAYER, (firefly, tickDelta, animationProgress) -> Math.max(1.0F, MathHelper.cos(animationProgress * 0.1F) * 0.25F), FireflyEntityModel::getMain));
    }

    @Override
    public Identifier getTexture(FireflyEntity entity) {
        return TEXTURE;
    }
}
