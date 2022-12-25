package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.misc.WilderEnumValues;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.minecraft.world.entity.MobCategory;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.ArrayList;
import java.util.Arrays;

@Mixin(MobCategory.class)
public class MobCategoryMixin {

	@SuppressWarnings("InvokerTarget")
	@Invoker("<init>")
	private static MobCategory newType(String internalName, int internalId, String name, int max, boolean isFriendly, boolean isPersistent, int despawnDistance ) {
		throw new AssertionError("Mixin injection failed - WilderWild MobCategoryMixin");
	}

	@SuppressWarnings("ShadowTarget")
	@Shadow
	@Final
	@Mutable
	private static MobCategory[] $VALUES;

	@Inject(
			method = "<clinit>",
			at = @At(
					value = "FIELD",
					opcode = Opcodes.PUTSTATIC,
					target = "Lnet/minecraft/world/entity/MobCategory;$VALUES:[Lnet/minecraft/world/entity/MobCategory;",
					shift = At.Shift.AFTER
			)
	)
	private static void addCustomCategories(CallbackInfo ci) {
		var categories = new ArrayList<>(Arrays.asList($VALUES));
		var last = categories.get(categories.size() - 1);

		var fireflies = newType("WILDERWILDFIREFLIES", last.ordinal() + 1, "wilderwildfireflies", ClothConfigInteractionHandler.fireflySpawnCap(), true, false, 80);
		WilderEnumValues.FIREFLIES = fireflies;
		categories.add(fireflies);

		var jellyfish = newType("WILDERWILDJELLYFISH", last.ordinal() + 2, "wilderwildjellyfish", ClothConfigInteractionHandler.jellyfishSpawnCap(), true, false, 64);
		WilderEnumValues.JELLYFISH = jellyfish;
		categories.add(jellyfish);

		$VALUES = categories.toArray(new MobCategory[0]);
	}
}
