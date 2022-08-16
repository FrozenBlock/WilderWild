package net.frozenblock.wilderwild.entity.render;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.block.StoneChestBlock;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.LidOpenable;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.render.block.entity.LightmapCoordinatesRetriever;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

public class StoneChestBlockEntityRenderer<T extends StoneChestBlockEntity & LidOpenable> extends ChestBlockEntityRenderer<T> {

    private static final String BASE = "bottom";
    private static final String LID = "lid";
    private static final String LATCH = "lock";
    private final ModelPart singleChestLid;
    private final ModelPart singleChestBase;
    private final ModelPart singleChestLatch;
    private final ModelPart doubleChestLeftLid;
    private final ModelPart doubleChestLeftBase;
    private final ModelPart doubleChestLeftLatch;
    private final ModelPart doubleChestRightLid;
    private final ModelPart doubleChestRightBase;
    private final ModelPart doubleChestRightLatch;

    public static final SpriteIdentifier STONE = getChestTextureId("stone");
    public static final SpriteIdentifier STONE_LEFT = getChestTextureId("stone_left");
    public static final SpriteIdentifier STONE_RIGHT = getChestTextureId("stone_right");

    public StoneChestBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);

        ModelPart modelPart = ctx.getLayerModelPart(WilderWildClient.STONE_CHEST);
        this.singleChestBase = modelPart.getChild(BASE);
        this.singleChestLid = modelPart.getChild(LID);
        this.singleChestLatch = modelPart.getChild(LATCH);
        ModelPart modelPart2 = ctx.getLayerModelPart(WilderWildClient.DOUBLE_STONE_CHEST_LEFT);
        this.doubleChestLeftBase = modelPart2.getChild(BASE);
        this.doubleChestLeftLid = modelPart2.getChild(LID);
        this.doubleChestLeftLatch = modelPart2.getChild(LATCH);
        ModelPart modelPart3 = ctx.getLayerModelPart(WilderWildClient.DOUBLE_STONE_CHEST_RIGHT);
        this.doubleChestRightBase = modelPart3.getChild(BASE);
        this.doubleChestRightLid = modelPart3.getChild(LID);
        this.doubleChestRightLatch = modelPart3.getChild(LATCH);
    }

    public static TexturedModelData getSingleTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(BASE, ModelPartBuilder.create().uv(0, 19).cuboid(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F), ModelTransform.NONE);
        modelPartData.addChild(LID, ModelPartBuilder.create().uv(0, 0).cuboid(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F), ModelTransform.pivot(0.0F, 9.0F, 1.0F));
        modelPartData.addChild(LATCH, ModelPartBuilder.create().uv(0, 0).cuboid(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F), ModelTransform.pivot(0.0F, 8.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    public static TexturedModelData getRightDoubleTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(BASE, ModelPartBuilder.create().uv(0, 19).cuboid(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F), ModelTransform.NONE);
        modelPartData.addChild(LID, ModelPartBuilder.create().uv(0, 0).cuboid(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F), ModelTransform.pivot(0.0F, 9.0F, 1.0F));
        modelPartData.addChild(LATCH, ModelPartBuilder.create().uv(0, 0).cuboid(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F), ModelTransform.pivot(0.0F, 8.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    public static TexturedModelData getLeftDoubleTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(BASE, ModelPartBuilder.create().uv(0, 19).cuboid(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F), ModelTransform.NONE);
        modelPartData.addChild(LID, ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F), ModelTransform.pivot(0.0F, 9.0F, 1.0F));
        modelPartData.addChild(LATCH, ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F), ModelTransform.pivot(0.0F, 8.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        boolean bl = world != null;
        BlockState blockState = bl ? entity.getCachedState() : Blocks.CHEST.getDefaultState().with(StoneChestBlock.FACING, Direction.SOUTH);
        ChestType chestType = blockState.contains(StoneChestBlock.CHEST_TYPE) ? blockState.get(StoneChestBlock.CHEST_TYPE) : ChestType.SINGLE;
        Block block = blockState.getBlock();
        if (block instanceof AbstractChestBlock<?> abstractStoneChestBlock) {
            boolean bl2 = chestType != ChestType.SINGLE;
            matrices.push();
            float f = blockState.get(StoneChestBlock.FACING).asRotation();
            matrices.translate(0.5, 0.5, 0.5);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-f));
            matrices.translate(-0.5, -0.5, -0.5);
            DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> propertySource;
            if (bl) {
                propertySource = abstractStoneChestBlock.getBlockEntitySource(blockState, world, entity.getPos(), true);
            } else {
                propertySource = DoubleBlockProperties.PropertyRetriever::getFallback;
            }

            float openProg = entity.getOpenProgress(tickDelta);
            openProg = 1.0f - openProg;
            openProg = 1.0f - openProg * openProg * openProg;
            int i = propertySource.apply(new LightmapCoordinatesRetriever<>()).applyAsInt(light);
            SpriteIdentifier spriteIdentifier = getChestTexture(entity, chestType, false);
            VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);
            if (bl2) {
                if (chestType == ChestType.LEFT) {
                    this.render(matrices, vertexConsumer, this.doubleChestLeftLid, this.doubleChestLeftLatch, this.doubleChestLeftBase, openProg, i, overlay);
                } else {
                    this.render(matrices, vertexConsumer, this.doubleChestRightLid, this.doubleChestRightLatch, this.doubleChestRightBase, openProg, i, overlay);
                }
            } else {
                this.render(matrices, vertexConsumer, this.singleChestLid, this.singleChestLatch, this.singleChestBase, openProg, i, overlay);
            }

            matrices.pop();
        }
    }

    private void render(MatrixStack matrices, VertexConsumer vertices, ModelPart lid, ModelPart latch, ModelPart base, float openFactor, int light, int overlay) {
        latch.pitch = lid.pitch = -(openFactor * 1.5707964f);
        lid.render(matrices, vertices, light, overlay);
        latch.render(matrices, vertices, light, overlay);
        base.render(matrices, vertices, light, overlay);
    }

    public static SpriteIdentifier getChestTexture(BlockEntity blockEntity, ChestType type, boolean christmas) {
        return getChestTexture(type, STONE, STONE_LEFT, STONE_RIGHT);
    }

    private static SpriteIdentifier getChestTexture(ChestType type, SpriteIdentifier single, SpriteIdentifier left, SpriteIdentifier right) {
        switch (type) {
            case LEFT:
                return left;
            case RIGHT:
                return right;
            case SINGLE:
            default:
                return single;
        }
    }

    public static SpriteIdentifier getChestTextureId(String variant) {
        return new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, WilderWild.id("entity/stone_chest/" + variant));
    }
}
