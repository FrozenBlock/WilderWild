package net.frozenblock.wilderwild.mixin.server;

import com.google.common.collect.Maps;
import net.frozenblock.wilderwild.misc.CooldownInterface;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(ItemCooldowns.class)
public class ItemCooldownsMixin implements CooldownInterface {

    @Shadow
    public final Map<Item, ItemCooldowns.CooldownInstance> cooldowns = Maps.newHashMap();

    public void changeCooldown(Item item, int additional) {
        if (this.cooldowns.containsKey(item)) {
            ItemCooldowns.CooldownInstance cooldown = this.cooldowns.get(item);
            this.cooldowns.put(item, new ItemCooldowns.CooldownInstance(cooldown.startTime, cooldown.endTime + additional));
            this.onCooldownChanged(item, additional);
        }
    }

    public void onCooldownChanged(Item item, int additional) {
    }

}
