package net.frozenblock.wilderwild.registry;

import com.mojang.serialization.Lifecycle;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class RegisterMobEffects {

    //TODO: UNIQUE COLOR
    public static final MobEffect VENOMOUS_STING = register(19, "venomous_sting", new MobEffect(MobEffectCategory.HARMFUL, 5149489));

    public static MobEffect register(int i, String string, MobEffect mobEffect) {
        ((WritableRegistry)Registry.MOB_EFFECT).registerMapping(i, ResourceKey.create(Registry.MOB_EFFECT_REGISTRY, WilderWild.id(string)), mobEffect, Lifecycle.stable());
        return mobEffect;
    }

    public static void init() {

    }
}
