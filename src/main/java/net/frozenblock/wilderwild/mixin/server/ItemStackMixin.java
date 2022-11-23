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
public final class ItemStackMixin {

    @Inject(at = @At("TAIL"), method = "inventoryTick")
    public void removeAncientTag(Level level, Entity entity, int slot, boolean selected, CallbackInfo info) {
        ItemStack stack = ItemStack.class.cast(this);
        CompoundTag nbt = stack.getTag();
        if (nbt != null) {
            if (nbt.get("wilderwild_is_ancient") != null) {
                nbt.remove("wilderwild_is_ancient");
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "tagMatches")
    private static void removeAncientTag(ItemStack left, ItemStack right, CallbackInfoReturnable<Boolean> info) {
        CompoundTag lTag = left.getTag();
        if (lTag != null) {
            if (lTag.get("wilderwild_is_ancient") != null) {
                lTag.remove("wilderwild_is_ancient");
            }
            if (lTag.isEmpty()) {
                left.tag = null;
            }
        }

        CompoundTag rTag = right.getTag();
        if (rTag != null) {
            if (rTag.get("wilderwild_is_ancient") != null) {
                rTag.remove("wilderwild_is_ancient");
            }
            if (rTag.isEmpty()) {
                right.tag = null;
            }
        }
    }

}
