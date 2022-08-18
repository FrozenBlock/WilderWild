package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Shadow
    protected ItemStack useItem;
    @Shadow
    protected int useItemRemaining;

    @Inject(
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/block/entity/LivingEntity;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"),
            method = "startUsingItem(Lnet/minecraft/world/InteractionHand;)V",
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private void startUsingItem(InteractionHand hand, CallbackInfo info, ItemStack itemStack) {
        if (itemStack.is(RegisterItems.ANCIENT_HORN)) {
            LivingEntity entity = LivingEntity.class.cast(this);
            info.cancel();
            if (!itemStack.isEmpty() && !entity.isUsingItem()) {
                this.useItem = itemStack;
                this.useItemRemaining = itemStack.getUseDuration();
                if (!entity.level.isClientSide) {
                    this.setLivingEntityFlag(1, true);
                    this.setLivingEntityFlag(2, hand == InteractionHand.OFF_HAND);
                }
            }
        }
    }

    @Shadow
    protected void setLivingEntityFlag(int mask, boolean value) {

    }

}
