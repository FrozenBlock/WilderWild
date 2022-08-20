package net.frozenblock.wilderwild.misc;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class WilderDirectDamageSource extends DamageSource {

    public WilderDirectDamageSource(String name) {
        super(name);
    }

    public static DamageSource pricklyPear() {
        return new WilderDirectDamageSource("prickly_pear").bypassArmor();
    }

    public Component getLocalizedDeathMessage(LivingEntity entity) {
        LivingEntity livingEntity = entity.getKillCredit();
        String string = "death.attack." + this.msgId;
        String string2 = string + ".player";
        if (livingEntity != null) {
            return Component.translatable(string2, entity.getDisplayName(), livingEntity.getDisplayName());
        }
        return Component.translatable(string, entity.getDisplayName());
    }

}
