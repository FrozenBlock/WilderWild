package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.entity.JellyCloud;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BottleItem.class)
public class BottleItemMixin {

    @Inject(at = @At("HEAD"), method = "use", cancellable = true)
    public void use(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> info) {
        ItemStack stack = player.getItemInHand(interactionHand);
        EntityHitResult hitResult = entityHitResult(player);
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            Entity entity = hitResult.getEntity();
            if (entity instanceof JellyCloud cloud) {
                if (cloud.getAge() < JellyCloud.MAX_AGE - (JellyCloud.TEXTURE_INCREASE_PERCENT * 2)) {
                    cloud.setAge(cloud.getAge() + JellyCloud.TEXTURE_INCREASE_PERCENT);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    level.gameEvent(player, GameEvent.FLUID_PICKUP, player.position());
                    info.setReturnValue(InteractionResultHolder.sidedSuccess(this.turnBottleIntoItem(stack, player, new ItemStack(RegisterItems.JELLY_BOTTLE)), level.isClientSide()));
                    info.cancel();
                }
            }
        }
    }

    private static EntityHitResult entityHitResult(Player player) {
        float f = player.getXRot();
        float g = player.getYRot();
        Vec3 vec3 = player.getEyePosition();
        float h = Mth.cos(-g * ((float)Math.PI / 180) - (float)Math.PI);
        float i = Mth.sin(-g * ((float)Math.PI / 180) - (float)Math.PI);
        float j = -Mth.cos(-f * ((float)Math.PI / 180));
        float k = Mth.sin(-f * ((float)Math.PI / 180));
        float l = i * j;
        float n = h * j;
        Vec3 vec32 = vec3.add((double)l * 5.0, (double) k * 5.0, (double)n * 5.0);
        return ProjectileUtil.getEntityHitResult(player, vec3, vec32, player.getBoundingBox().inflate(6), entity -> true, 0.3f);
        };

    @Shadow
    public ItemStack turnBottleIntoItem(ItemStack itemStack, Player player, ItemStack itemStack2) {
        return null;
    }

}
