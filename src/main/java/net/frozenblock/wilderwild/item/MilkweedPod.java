package net.frozenblock.wilderwild.item;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class MilkweedPod extends Item {

    public MilkweedPod(Properties settings) {
        super(settings);
    }

	@Override
    public InteractionResultHolder<ItemStack> use(Level level, Player user, InteractionHand hand) {
        WilderWild.log(user, "Used Milkweed Pod", WilderWild.DEV_LOGGING);
        ItemStack itemStack = user.getItemInHand(hand);
        itemStack.shrink(1);
        if (level instanceof ServerLevel server) {
            float pitch = user.getXRot();
            float yaw = user.getYRot();
            float roll = 0.0F;
            float f = -Mth.sin(yaw * 0.017453292F) * Mth.cos(pitch * 0.017453292F);
            float g = -Mth.sin((pitch + roll) * 0.017453292F);
            float h = Mth.cos(yaw * 0.017453292F) * Mth.cos(pitch * 0.017453292F);
            EasyPacket.EasySeedPacket.createControlledParticle(level, user.getEyePosition().add(0, -0.1, 0), f, g, h, server.random.nextIntBetweenInclusive(5, 20), true, 48);
        }

        return InteractionResultHolder.consume(itemStack);
    }

	@Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.TOOT_HORN;
    }
}
