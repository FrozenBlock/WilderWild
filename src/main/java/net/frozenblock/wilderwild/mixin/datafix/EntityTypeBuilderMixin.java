package net.frozenblock.wilderwild.mixin.datafix;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.mojang.datafixers.DSL;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityType.Builder.class)
public class EntityTypeBuilderMixin {

	@WrapWithCondition(method = "build", at = @At(value = "INVOKE", target = "Lnet/minecraft/Util;fetchChoiceType(Lcom/mojang/datafixers/DSL$TypeReference;Ljava/lang/String;)Lcom/mojang/datafixers/types/Type;"))
	private boolean cancelDataFixerInit(DSL.TypeReference type, String choiceName) {
		return false;
	}
}
