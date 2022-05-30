package net.frozenblock.wilderwild.item;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.FireflyEntity;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class FireflyBottleItem extends Item {

    public FireflyBottleItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        WilderWild.log(user, "Used Firefly Bottle");
        ItemStack itemStack = user.getStackInHand(hand);
        if (world instanceof ServerWorld server) {
            float pitch = user.getPitch();
            float yaw = user.getYaw();
            float roll = 0.0F;
            float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
            float g = -MathHelper.sin((pitch + roll) * 0.017453292F);
            float h = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
            if (user.getAbilities().allowModifyWorld) {
                FireflyEntity entity = RegisterEntities.FIREFLY.create(server);
                boolean spawned = server.spawnEntity(entity);
                if (spawned && entity != null) {
                    entity.setVelocity(f * 0.5, h * 0.5, g * 0.5);
                    itemStack.decrement(1);
                    PlayerInventory inv = user.getInventory();
                    inv.offerOrDrop(new ItemStack(Items.GLASS_BOTTLE));
                }
            }
        }

        return TypedActionResult.consume(itemStack);
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK; //sus funny funny funny among us sus funny all
    }

}
