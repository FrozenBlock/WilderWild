package net.frozenblock.wilderwild.item;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.FireflyEntity;
import net.frozenblock.wilderwild.entity.ai.FireflyBrain;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class FireflyBottleItem extends Item {

    private final String color;

    public FireflyBottleItem(Settings settings, String color) {
        super(settings);
        this.color = color;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity)user : null;
        if (playerEntity instanceof ServerPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity)playerEntity, stack);
        }

        if (playerEntity != null) {
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!playerEntity.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }

        if (playerEntity == null || !playerEntity.getAbilities().creativeMode) {
            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (playerEntity != null) {
                playerEntity.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        user.emitGameEvent(GameEvent.DRINK);
        return stack;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        WilderWild.log(user, "Used Firefly Bottle", WilderWild.DEV_LOGGING);
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
                    entity.setVelocity(f * 0.7, g * 0.7, h * 0.7);
                    entity.refreshPositionAndAngles(user.getX(), user.getEyeY(), user.getZ(), user.getPitch(), user.getYaw());
                    entity.setFromBottle(true);
                    boolean spawned = server.spawnEntity(entity);
                    if (spawned) {
                        entity.hasHome = true;
                        FireflyBrain.rememberHome(entity, entity.getBlockPos());
                        entity.setColor(this.color);
                    } else {
                        WilderWild.log("Couldn't spawn Firefly from bottle @ " + user.getBlockPos().toShortString(), WilderWild.UNSTABLE_LOGGING);
                    }
                }
            }
        }
        user.emitGameEvent(GameEvent.DRINK);
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK; //sus funny funny funny among us sus funny all
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 1;
    }
}
