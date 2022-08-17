package net.frozenblock.wilderwild.mixin.server;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(at = @At("HEAD"), method = "inventoryTick")
    public void inventoryTick(World world, Entity entity, int slot, boolean selected, CallbackInfo info) {
        ItemStack stack = ItemStack.class.cast(this);
        if (stack.getOrCreateNbt().get("wilderwild_is_ancient") != null) {
            stack.getOrCreateNbt().remove("wilderwild_is_ancient");
        }
    }

}
