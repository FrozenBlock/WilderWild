package net.frozenblock.wilderwild.item;

import net.frozenblock.wilderwild.particle.PollenParticle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class MilkweedPodItem extends Item {

    public MilkweedPodItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        itemStack.decrement(1);
        if (world instanceof ServerWorld server) {
            PollenParticle.EasySeedPacket.createParticle(world, user.getEyePos().add(0, -0.1, 0), server.random.nextBetween(8, 20), true);
        }

        return TypedActionResult.consume(itemStack);
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.TOOT_HORN;
    }

}
