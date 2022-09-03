package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Turtle;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(Turtle.class)
public class TurtleMxin {

    @Shadow @Final
    public GoalSelector goalSelector;
    @Shadow @Final
    public GoalSelector targetSelector;

    @Inject(method = "registerGoals", at = @At("TAIL"))
    public void registerGoals() {
        Turtle turtle = Turtle.class.cast(this);
        this.targetSelector.addGoal(10, new NearestAttackableTargetGoal<Jellyfish>(turtle, Jellyfish.class, false));
    }
}
