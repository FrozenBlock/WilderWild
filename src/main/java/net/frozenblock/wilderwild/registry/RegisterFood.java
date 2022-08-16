package net.frozenblock.wilderwild.registry;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class RegisterFood {

    public static final FoodComponent BAOBAB_NUT = new FoodComponent.Builder().hunger(1).saturationModifier(0.2F).statusEffect(new  StatusEffectInstance(StatusEffects.REGENERATION, 100, 0), 0.5F).statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 120, 0), 0.7F).statusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 150, 0), 0.15F).build();
        /** Goal: Make Prickly pear deal normal damage, playing the Sweet Berry Thorn Sound **/
    public static final FoodComponent PRICKLY_PEAR = new FoodComponent.Builder().hunger(2).saturationModifier(0.3F).statusEffect(new StatusEffectInstance(StatusEffects.WITHER, 20, 1), 1.0F).build();

    }
