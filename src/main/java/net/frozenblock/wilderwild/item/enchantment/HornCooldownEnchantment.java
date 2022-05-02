package net.frozenblock.wilderwild.item.enchantment;

import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class HornCooldownEnchantment extends Enchantment {

    public HornCooldownEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    public int getMinPower(int level) {
        return 1 + 10 * (level - 1);
    }

    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    public int getMaxLevel() {
        return 5;
    }

    public boolean isAcceptableItem(ItemStack stack) {
        return stack.isOf(RegisterItems.ANCIENT_HORN);
    }
}
