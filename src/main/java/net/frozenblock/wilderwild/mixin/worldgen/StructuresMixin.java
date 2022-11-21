package net.frozenblock.wilderwild.mixin.worldgen;

import net.minecraft.data.worldgen.Structures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Structures.class)
public class StructuresMixin {

	//Should this be removed?
	@ModifyArgs(method = "<clinit>",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/structures/JigsawStructure;<init>(Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;Lnet/minecraft/core/Holder;Ljava/util/Optional;ILnet/minecraft/world/level/levelgen/heightproviders/HeightProvider;ZLjava/util/Optional;I)V", ordinal = 0),
			require = 0)
	private static void wilderWild_increaseAncientCitySize(Args args) {
		int structureSize = 20;
		args.set(3, structureSize);
	}

}
