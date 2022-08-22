package net.frozenblock.wilderwild.mixin.server;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(at = @At("HEAD"), method = "inventoryTick")
    public void inventoryTick(Level world, Entity entity, int slot, boolean selected, CallbackInfo info) {
        ItemStack stack = ItemStack.class.cast(this);
        CompoundTag nbt = stack.getTag();
        if (nbt != null) {
            if (nbt.get("wilderwild_is_ancient") != null) {
                nbt.remove("wilderwild_is_ancient");
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "tagMatches")
    private static void tagMatches(ItemStack left, ItemStack right, CallbackInfoReturnable<Boolean> info) {
        if (left.getTag() != null) {
            if (left.getTag().get("wilderwild_is_ancient") != null) {
                left.getTag().remove("wilderwild_is_ancient");
            }
        }
        if (right.getTag() != null) {
            if (right.getTag().get("wilderwild_is_ancient") != null) {
                right.getTag().remove("wilderwild_is_ancient");
            }
        }
    }

}
