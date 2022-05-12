package net.frozenblock.wilderwild.registry;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;

public class NewDamageSource extends DamageSource {

    protected NewDamageSource(String name) { super(name); }

    public static final DamageSource SCULK_JAW = new NewDamageSource("sculk_jaw").setScaledWithDifficulty();

    public Text getDeathMessage(LivingEntity entity) {
        LivingEntity livingEntity = entity.getPrimeAdversary();
        String string = "death.attack." + this.name;
        String string2 = string + ".player";
        return livingEntity != null ? Text.translatable(string2, entity.getDisplayName(), livingEntity.getDisplayName()) : Text.translatable(string, entity.getDisplayName());
    }

}
