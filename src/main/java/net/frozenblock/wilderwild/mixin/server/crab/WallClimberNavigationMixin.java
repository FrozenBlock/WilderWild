package net.frozenblock.wilderwild.mixin.server.crab;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.wilderwild.entity.Crab;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WallClimberNavigation.class)
public abstract class WallClimberNavigationMixin extends PathNavigation {

	public WallClimberNavigationMixin(Mob mob, Level level) {
		super(mob, level);
	}

	@ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;getBbWidth()F"))
	public float tick(float original) {
		if (this.mob instanceof Crab) {
			return original * 2F;
		}
		return original;
	}

}
