package net.frozenblock.wilderwild.entity.render;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.WildClientMod;
import net.frozenblock.wilderwild.entity.SculkSensorTendrilEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SculkSensorTendrilRenderer extends LivingEntityRenderer<SculkSensorTendrilEntity, SculkSensorTendrilModel<SculkSensorTendrilEntity>> {
    private static final Identifier TENDRIL_TEXTURE = new Identifier(WilderWild.MOD_ID, "textures/entity/sculk_sensor_tendrils/inactive.png");
    private static final Identifier ACTIVE_TENDRIL_TEXTURE = new Identifier(WilderWild.MOD_ID, "textures/entity/sculk_sensor_tendrils/active.png");


    public SculkSensorTendrilRenderer(EntityRendererFactory.Context context) {
        super(context, new SculkSensorTendrilModel<>(context.getPart(WildClientMod.SENSOR_TENDRILS_LAYER)), 0.0f);
        this.addFeature(new SculkSensorTendrilsFeature(this));
    }

    @Override
    public void render(SculkSensorTendrilEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        //if (clientOptionsCheckHere) {
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
        //}
    }

    @Override
    public Identifier getTexture(SculkSensorTendrilEntity entity) {
        if (entity.activeTicksLeft>0) {
            return ACTIVE_TENDRIL_TEXTURE;
        }
            return TENDRIL_TEXTURE;
    }

    protected boolean hasLabel(SculkSensorTendrilEntity entity) { return false; }
}
