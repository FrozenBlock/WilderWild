package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.item.enchantment.HornCooldownEnchantment;
import net.frozenblock.wilderwild.item.enchantment.HornSpeedEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegisterEnchantments {
    public static final Enchantment ANCIENT_HORN_COOLDOWN_ENCHANTMENT = register("technique", new HornCooldownEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
    public static final Enchantment ANCIENT_HORN_SPEED_ENCHANTMENT = register("reverberation", new HornSpeedEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));

    public static void init() {

    }

    public static Enchantment register(String string, Enchantment enchantment) {
        return Registry.register(Registry.ENCHANTMENT, new Identifier(WilderWild.MOD_ID, string), enchantment);
    }

}
