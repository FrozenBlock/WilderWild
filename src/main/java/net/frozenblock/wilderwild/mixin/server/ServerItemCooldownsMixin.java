package net.frozenblock.wilderwild.mixin.server;

import com.google.common.collect.Maps;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.impl.networking.server.ServerNetworkingImpl;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.CooldownInterface;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ServerItemCooldowns;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(ServerItemCooldowns.class)
public class ServerItemCooldownsMixin implements CooldownInterface {
    @Shadow @Final
    private ServerPlayer player;

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
        FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
        byteBuf.writeId(Registry.ITEM, item);
        byteBuf.writeVarInt(additional);
        this.player.connection.send(ServerNetworkingImpl.createPlayC2SPacket(WilderWild.COOLDOWN_CHANGE_PACKET, byteBuf));
    }

}
