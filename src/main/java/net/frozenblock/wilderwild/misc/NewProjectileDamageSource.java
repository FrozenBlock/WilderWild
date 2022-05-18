package net.frozenblock.wilderwild.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class NewProjectileDamageSource extends ProjectileDamageSource {
    public NewProjectileDamageSource(String name, Entity projectile, @Nullable Entity attacker) {
        super(name, projectile, attacker);
    }

    public static DamageSource ancientHornDamageSource(Entity projectile, @Nullable Entity attacker) {
        return new NewProjectileDamageSource("ancient_horn", projectile, attacker).setBypassesArmor();
    }

    public Text getDeathMessage(LivingEntity entity) {
        Text text = this.getAttacker() == null ? this.source.getDisplayName() : this.getAttacker().getDisplayName();
        ItemStack itemStack = this.getAttacker() instanceof LivingEntity ? ((LivingEntity) this.getAttacker()).getMainHandStack() : ItemStack.EMPTY;
        String string = "death.attack." + this.name;
        String string2 = string + ".item";
        return !itemStack.isEmpty() && itemStack.hasCustomName() ? Text.translatable(string2, entity.getDisplayName(), text, itemStack.toHoverableText()) : Text.translatable(string, entity.getDisplayName(), text);
    }
}
