package net.frozenblock.wilderwild.mixin.server.general;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
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

	@Inject(at = @At("HEAD"), method = "tagMatches", cancellable = true)
	private static void removeAncientTagAndCompare(ItemStack left, ItemStack right, CallbackInfoReturnable<Boolean> info) {
		CompoundTag lTag = left.getTag();
		if (lTag != null) {
			if (lTag.get("wilderwild_is_ancient") != null) {
				lTag.remove("wilderwild_is_ancient");
			}
			if (lTag.isEmpty()) {
				left.tag = null;
			}
		}

		CompoundTag rTag = right.tag;
		if (rTag != null) {
			if (rTag.get("wilderwild_is_ancient") != null) {
				rTag.remove("wilderwild_is_ancient");
			}
			if (rTag.isEmpty()) {
				right.tag = null;
			}
		}

		if (tagIsNullMatching(left, right)) {
			info.setReturnValue(true);
		}
	}

	@Inject(at = @At(value = "RETURN", ordinal = 2, shift = At.Shift.BEFORE), method = "matches(Lnet/minecraft/world/item/ItemStack;)Z", cancellable = true)
	private void matches(ItemStack other, CallbackInfoReturnable<Boolean> info) {
		info.setReturnValue(tagIsNullMatching(ItemStack.class.cast(this), other));
	}

	@Unique
	private static boolean tagIsNullMatching(ItemStack stack, ItemStack other) {
		boolean stackTagEmpty = stack.tag == null || stack.tag.isEmpty();
		boolean otherTagEmpty = other.tag == null || other.tag.isEmpty();
		return stackTagEmpty && otherTagEmpty;
	}
}
