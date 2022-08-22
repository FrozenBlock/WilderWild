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

    @Inject(at = @At("HEAD"), method = "tagMatches", cancellable = true)
    private static void tagMatches(ItemStack left, ItemStack right, CallbackInfoReturnable<Boolean> info) {
        CompoundTag lTag = left.getTag();
        if (lTag != null) {
            if (lTag.get("wilderwild_is_ancient") != null) {
                lTag.remove("wilderwild_is_ancient");
            }
        }

        CompoundTag rTag = right.getTag();
        if (rTag != null) {
            if (rTag.get("wilderwild_is_ancient") != null) {
                rTag.remove("wilderwild_is_ancient");
            }
        }
    }

    @Inject(at = @At("TAIL"), method = "matches(Lnet/minecraft/world/item/ItemStack;)Z", cancellable = true)
    private void matches(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
        ItemStack thisStack = ItemStack.class.cast(this);
        CompoundTag lTag = thisStack.getTag();
        if (lTag != null) {
            if (lTag.get("wilderwild_is_ancient") != null) {
                lTag.remove("wilderwild_is_ancient");
            }
        }

        CompoundTag rTag = stack.getTag();
        if (rTag != null) {
            if (rTag.get("wilderwild_is_ancient") != null) {
                rTag.remove("wilderwild_is_ancient");
            }
        }

        if (lTag != null) {
            if (rTag != null) {
                if (lTag.isEmpty() && rTag.isEmpty()) {
                    info.cancel();
                    info.setReturnValue(true);
                }
            } else if (lTag.isEmpty()) {
                info.cancel();
                info.setReturnValue(true);
            }
        }

        if (rTag != null) {
            if (lTag != null) {
                if (rTag.isEmpty() && lTag.isEmpty()) {
                    info.cancel();
                    info.setReturnValue(true);
                }
            } else if (rTag.isEmpty()) {
                info.cancel();
                info.setReturnValue(true);
            }
        }
    }

}
