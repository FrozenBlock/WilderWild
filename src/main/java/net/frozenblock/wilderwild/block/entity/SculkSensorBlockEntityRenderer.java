package net.frozenblock.wilderwild.block.entity;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WildClientMod;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.render.block.entity.LightmapCoordinatesRetriever;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

import java.util.Calendar;

public class SculkSensorBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
    private final ModelPart ear1;
    private final ModelPart ear2;

    public SculkSensorBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {

        ModelPart modelPart = ctx.getLayerModelPart(WildClientMod.SCULK_SENSOR_LAYER);
        this.ear1 = modelPart.getChild("ear1");
        this.ear2 = modelPart.getChild("ear2"); //youre welcome for these nice names
    }

        public static TexturedModelData getSingleTexturedModelData() {
            ModelData modelData = new ModelData();
            ModelPartData modelPartData = modelData.getRoot();
            //idk how to do this
            modelPartData.addChild("ear1", ModelPartBuilder.create().uv(0, 19).cuboid(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F), ModelTransform.NONE);
            modelPartData.addChild("ear2", ModelPartBuilder.create().uv(0, 0).cuboid(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F), ModelTransform.pivot(0.0F, 9.0F, 1.0F));
            return TexturedModelData.of(modelData, 32, 32);
        }

        public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
            World world = entity.getWorld();
            SpriteIdentifier spriteIdentifier = TexturedRenderLayers.getChestTexture(entity, ChestType.SINGLE, false);
            VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);
            this.render(matrices, vertexConsumer, this.ear1, this.ear2, 1, light, overlay);
        }
        private void render(MatrixStack matrices, VertexConsumer vertices, ModelPart ear1, ModelPart ear2, float openFactor, int light, int overlay) {
        //SOME REFERENCES FROMT CHESTS
            /*lid.pitch = -(openFactor * 1.5707964F);
            latch.pitch = lid.pitch;*/
         ear1.render(matrices, vertices, light, overlay);
         ear2.render(matrices, vertices, light, overlay);
        }

}
