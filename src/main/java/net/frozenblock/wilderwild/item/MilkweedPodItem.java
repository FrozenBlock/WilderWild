package net.frozenblock.wilderwild.item;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.particle.server.EasyParticlePacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MilkweedPodItem extends Item {

    public MilkweedPodItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        WilderWild.log(user, "Used Milkweed Pod", WilderWild.DEV_LOGGING);
        ItemStack itemStack = user.getStackInHand(hand);
        itemStack.decrement(1);
        if (world instanceof ServerWorld server) {
            float pitch = user.getPitch();
            float yaw = user.getYaw();
            float roll = 0.0F;
            float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
            float g = -MathHelper.sin((pitch + roll) * 0.017453292F);
            float h = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
            EasyParticlePacket.EasySeedPacket.createControlledParticle(world, user.getEyePos().add(0, -0.1, 0), f, g, h,server.random.nextBetween(5, 20), true);
        }

        return TypedActionResult.consume(itemStack);
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.TOOT_HORN;
    }

}
