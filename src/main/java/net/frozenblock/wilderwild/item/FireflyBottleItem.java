package net.frozenblock.wilderwild.item;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.FireflyEntity;
import net.frozenblock.wilderwild.entity.ai.FireflyBrain;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class FireflyBottleItem extends Item {

    private final String color;

    public FireflyBottleItem(Settings settings, String color) {
        super(settings);
        this.color = color;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        WilderWild.log(user, "Used Firefly Bottle", WilderWild.DEV_LOGGING);
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
                if (entity != null) {
                    //TODO: FIREFLY BOTTLE SOUNDS
                    entity.playSound(SoundEvents.ITEM_BOTTLE_EMPTY, 1.0F, 1.0F);
                    entity.setVelocity(f * 0.7, g * 0.7, h * 0.7);
                    entity.refreshPositionAndAngles(user.getX(), user.getEyeY(), user.getZ(), user.getPitch(), user.getYaw());
                    entity.setFromBottle(true);
                    boolean spawned = server.spawnEntity(entity);
                    if (spawned) {
                        entity.hasHome = true;
                        FireflyBrain.rememberHome(entity, entity.getBlockPos());
                        entity.setColor(this.color);
                        if (!user.isCreative()) {
                            user.getStackInHand(hand).decrement(1);
                            user.getInventory().offerOrDrop(new ItemStack(Items.GLASS_BOTTLE));
                        }
                    } else {
                        WilderWild.log("Couldn't spawn Firefly from bottle @ " + user.getBlockPos().toShortString(), WilderWild.UNSTABLE_LOGGING);
                    }
                }
            }
        }

        return TypedActionResult.consume(itemStack);
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK; //sus funny funny funny among us sus funny all
    }

}
