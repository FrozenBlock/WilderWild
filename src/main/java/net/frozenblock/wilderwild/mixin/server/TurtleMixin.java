package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Turtle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(Turtle.class)
public class TurtleMixin {

    @Inject(method = "registerGoals", at = @At("TAIL"))
    public void registerGoals() {
        Turtle turtle = Turtle.class.cast(this);
        turtle.targetSelector.addGoal(10, new NearestAttackableTargetGoal<Jellyfish>(turtle, Jellyfish.class, false));
    }
}
