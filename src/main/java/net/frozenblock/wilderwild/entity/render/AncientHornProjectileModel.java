package net.frozenblock.wilderwild.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class AncientHornProjectileModel extends Model {
    public static final Identifier TEXTURE = new Identifier(WilderWild.MOD_ID, "textures/entity/ancient_horn_projectile.png");
    private final ModelPart root;

    public AncientHornProjectileModel(ModelPart root) {
        super(RenderLayer::getEntityCutout);
        this.root = root;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData2 = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 0.0F, 0.0F, 16.0F, 16.0F, 0.001F), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 32, 32);
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        matrices.scale(0.6f,0.6f,0.6f);
        this.root.yaw = (float) (90 * (Math.PI/180));
        this.root.render(matrices, vertices, 15728640, overlay, red, green, blue, alpha);
    }
}
