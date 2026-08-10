package net.frozenblock.wilderwild.mixin.worldgen.biome;

import java.util.ArrayList;
import java.util.Arrays;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.data.worldgen.biome.impl.WWGrassColorModifier;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BiomeSpecialEffects.GrassColorModifier.class)
public class GrassColorModifierMixin { // In common mixins.json

	@SuppressWarnings("InvokerTarget")
	@Invoker("<init>")
	private static BiomeSpecialEffects.GrassColorModifier newType(
		String enumName,
		int ordinal,
		String name,
		BiomeSpecialEffects.GrassColorModifier.ColorModifier delegate
	) {
		throw new AssertionError("Mixin injection failed - Wilder Wild GrassColorModifierNeoForgeMixin");
	}

	@SuppressWarnings("ShadowTarget")
	@Shadow
	@Final
	@Mutable
	private static BiomeSpecialEffects.GrassColorModifier[] $VALUES;

	@Inject(
		method = "<clinit>",
		at = @At(
			value = "FIELD",
			opcode = Opcodes.PUTSTATIC,
			target = "Lnet/minecraft/world/level/biome/BiomeSpecialEffects$GrassColorModifier;$VALUES:[Lnet/minecraft/world/level/biome/BiomeSpecialEffects$GrassColorModifier;",
			shift = At.Shift.AFTER
		)
	)
	private static void wilderwild$addTundra(CallbackInfo info) {
		final var modifiers = new ArrayList<>(Arrays.asList($VALUES));
		final int ordinal = modifiers.getLast().ordinal() + 1;

		final var tundraColorModifier = new BiomeSpecialEffects.GrassColorModifier.ColorModifier() {
			@Override
			public int modifyGrassColor(double x, double z, int baseColor) {
				return WWGrassColorModifier.modifyColorTundra(x, z, baseColor);
			}
		};
		final BiomeSpecialEffects.GrassColorModifier tundra = newType("WILDERWILD_TUNDRA", ordinal, WWConstants.safeString("tundra"), tundraColorModifier);
		modifiers.add(tundra);
		WWGrassColorModifier.WILDERWILD_TUNDRA = tundra;

		$VALUES = modifiers.toArray(new BiomeSpecialEffects.GrassColorModifier[0]);
	}
}
