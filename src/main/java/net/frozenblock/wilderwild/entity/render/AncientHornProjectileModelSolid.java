package net.frozenblock.wilderwild.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.AncientHornProjectileEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class AncientHornProjectileModelSolid extends Model {
    public static final Identifier TEXTURE = new Identifier(WilderWild.MOD_ID, "textures/entity/ancient_horn_projectile.png");
    private final ModelPart root;
    public float merp = (float) (90 * (Math.PI/180));
    public AncientHornProjectileModelSolid(ModelPart root) {
        super(RenderLayer::getEntityCutout);
        this.root = root;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData2 = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -16.0F, 8.0F, 16.0F, 16.0F, 0.001F), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 32, 32);
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha, float tickDelta, AncientHornProjectileEntity entity) {
        matrices.scale(0.599f,0.599f,0.599f);
        float f = MathHelper.sin(((float)entity.aliveTicks + tickDelta - 6.2831855F) * 0.05F) * 2.0F;
        this.root.yaw = f + merp;
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {

    }
}
