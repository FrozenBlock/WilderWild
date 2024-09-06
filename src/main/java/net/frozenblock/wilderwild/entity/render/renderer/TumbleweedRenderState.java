package net.frozenblock.wilderwild.entity.render.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Environment(EnvType.CLIENT)
public class TumbleweedRenderState extends LivingEntityRenderState {

	public float tumbleRot;
	public float pitch;
	public float roll;
	public ItemStack visibleItem;
	public float itemX;
	public float itemZ;
	public Level level;
}
