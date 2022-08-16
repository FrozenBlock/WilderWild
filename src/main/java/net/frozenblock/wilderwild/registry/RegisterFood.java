package net.frozenblock.wilderwild.registry;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class RegisterFood {

    public static final FoodComponent PRICKLY_PEAR;

    static {
        /** Goal: Make Prickly pear deal normal damage, playing the Sweet Berry Thorn Sound **/
        PRICKLY_PEAR = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.3F).statusEffect(new StatusEffectInstance(StatusEffects.WITHER, 20, 1), 1.0F).build();
    }
}
