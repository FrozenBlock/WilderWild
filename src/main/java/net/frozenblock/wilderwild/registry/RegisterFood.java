package net.frozenblock.wilderwild.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class RegisterFood {

    public static final FoodProperties BAOBAB_NUT = new FoodProperties.Builder().nutrition(1).alwaysEat().saturationMod(0.2F).effect(new MobEffectInstance(MobEffects.REGENERATION, 120, 0), 0.5F).effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 140, 0), 0.7F).effect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 250, 0), 0.15F).build();
    public static final FoodProperties PRICKLY_PEAR = new FoodProperties.Builder().nutrition(2).saturationMod(0.3F).build();
    public static final FoodProperties JELLY_BOTTLE = new FoodProperties.Builder().nutrition(6).saturationMod(0.4f).build();

}
