package net.frozenblock.wilderwild.entity.render;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.AncientHornProjectileEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class AncientHornProjectileRenderer extends ProjectileEntityRenderer<AncientHornProjectileEntity> {
    private static final Identifier PROJECTILE_TEXTURE = new Identifier(WilderWild.MOD_ID, "textures/entity/ancient_horn_projectile.png");

    public AncientHornProjectileRenderer(Object context) {
        super((EntityRendererFactory.Context) context);
    }

    @Override
    public Identifier getTexture(AncientHornProjectileEntity entity) {
        return PROJECTILE_TEXTURE;
    }

}
