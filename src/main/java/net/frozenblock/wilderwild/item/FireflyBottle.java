package net.frozenblock.wilderwild.item;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.entity.ai.FireflyBrain;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class FireflyBottle extends Item {

    private final String color;

    public FireflyBottle(Properties settings, String color) {
        super(settings);
        this.color = color;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        Player playerEntity = user instanceof Player ? (Player) user : null;
        if (playerEntity instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) playerEntity, stack);
        }

        if (playerEntity != null) {
            playerEntity.awardStat(Stats.ITEM_USED.get(this));
            if (!playerEntity.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        if (playerEntity == null || !playerEntity.getAbilities().instabuild) {
            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (playerEntity != null) {
                playerEntity.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        user.gameEvent(GameEvent.ENTITY_PLACE);
        return stack;
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        WilderWild.log(user, "Used Firefly Bottle", WilderWild.DEV_LOGGING);
        if (world instanceof ServerLevel server) {
            float pitch = user.getXRot();
            float yaw = user.getYRot();
            float roll = 0.0F;
            float f = -Mth.sin(yaw * 0.017453292F) * Mth.cos(pitch * 0.017453292F);
            float g = -Mth.sin((pitch + roll) * 0.017453292F);
            float h = Mth.cos(yaw * 0.017453292F) * Mth.cos(pitch * 0.017453292F);
            ItemStack stack = user.getItemInHand(hand);
            if (user.getAbilities().mayBuild) {
                Firefly entity = RegisterEntities.FIREFLY.create(server);
                if (entity != null) {
                    entity.setDeltaMovement(f * 0.7, g * 0.7, h * 0.7);
                    entity.moveTo(user.getX(), user.getEyeY(), user.getZ(), user.getXRot(), user.getYRot());
                    entity.setFromBottle(true);
                    boolean spawned = server.addFreshEntity(entity);
                    if (spawned) {
                        entity.playSound(RegisterSounds.ITEM_BOTTLE_RELEASE_FIREFLY, 1.0F, 0.8F + world.random.nextFloat() * 0.2F);
                        entity.hasHome = true;
                        FireflyBrain.rememberHome(entity, entity.blockPosition());
                        entity.setColor(this.color);
                        if (stack.hasCustomHoverName()) {
                            entity.setCustomName(stack.getHoverName());
                        }
                    } else {
                        WilderWild.log("Couldn't spawn Firefly from bottle @ " + user.blockPosition().toShortString(), WilderWild.UNSTABLE_LOGGING);
                    }
                }
            }
        }
        user.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
        return ItemUtils.startUsingInstantly(world, user, hand);
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE; //sus funny funny funny among us sus funny all
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 1;
    }
}
