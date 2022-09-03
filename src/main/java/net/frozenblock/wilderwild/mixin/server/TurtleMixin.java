package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Turtle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Turtle.class)
public class TurtleMixin {

    @Inject(method = "registerGoals", at = @At("TAIL"))
    public void registerGoals(CallbackInfo info) {
        Turtle turtle = Turtle.class.cast(this);
        turtle.goalSelector.addGoal(3, new MeleeAttackGoal(turtle, 1.0, true));
        turtle.targetSelector.addGoal(10, new NearestAttackableTargetGoal<Jellyfish>(turtle, Jellyfish.class, false));
    }

    @Inject(method = "createAttributes", at = @At("TAIL"))
    public static void createAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> info) {
        AttributeSupplier.Builder builder = info.getReturnValue();
        builder.add(Attributes.ATTACK_DAMAGE, 3.0);
        info.cancel();
        info.setReturnValue(builder);
    }

}
