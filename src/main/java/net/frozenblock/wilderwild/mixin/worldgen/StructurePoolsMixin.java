package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.world.structure.AbandonedCabinGenerator;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StructurePools.class)
public class StructurePoolsMixin {

    @Inject(method = "initDefaultPools", at = @At("RETURN"))
    private static void initDefaultPools(Registry<StructurePool> registry, CallbackInfoReturnable<RegistryEntry<StructurePool>> cir) {
        AbandonedCabinGenerator.init();
    }
}
