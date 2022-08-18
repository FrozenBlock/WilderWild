package net.frozenblock.wilderwild.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class RegisterFood {

    public static final FoodProperties BAOBAB_NUT = new FoodProperties.Builder().nutrition(1).saturationMod(0.2F).effect(new MobEffectInstance(MobEffects.REGENERATION, 120, 0), 0.5F).effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 140, 0), 0.7F).effect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 250, 0), 0.15F).build();
    /**
     * Goal: Make Prickly pear deal normal damage, playing the Sweet Berry Thorn Sound
     **/
    public static final FoodProperties PRICKLY_PEAR = new FoodProperties.Builder().nutrition(2).saturationMod(0.3F).effect(new MobEffectInstance(MobEffects.WITHER, 20, 1), 1.0F).build();

}
