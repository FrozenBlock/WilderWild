package net.frozenblock.wilderwild.misc;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class NewDamageSource extends DamageSource {

    protected NewDamageSource(String name) {
        super(name);
    }

    public static final DamageSource SCULK_JAW = new NewDamageSource("sculk_jaw").setScalesWithDifficulty();

    public Component getDeathMessage(LivingEntity entity) {
        LivingEntity livingEntity = entity.getKillCredit();
        String string = "death.attack." + this.msgId;
        String string2 = string + ".player";
        return livingEntity != null ? Component.translatable(string2, entity.getDisplayName(), livingEntity.getDisplayName()) : Component.translatable(string, entity.getDisplayName());
    }

}
