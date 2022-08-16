package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.noise.NoiseParametersKeys;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.VanillaSurfaceRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(VanillaSurfaceRules.class)
public class VanillaSurfaceRulesMixin {

    @Shadow
    @Final
    private static MaterialRules.MaterialRule WATER;

    @Shadow @Final private static MaterialRules.MaterialRule GRAVEL;

    @Shadow @Final private static MaterialRules.MaterialRule SAND;

    @ModifyVariable(method = "createDefaultRule", at = @At("STORE"), ordinal = 8)
    private static MaterialRules.MaterialRule injected(MaterialRules.MaterialRule materialRule) {

        return MaterialRules.sequence(MaterialRules.condition(
                MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.sequence(
                        MaterialRules.condition(
                                MaterialRules.biome(RegisterWorldgen.CYPRESS_WETLANDS),
                                MaterialRules.condition(
                                        MaterialRules.aboveY(YOffset.fixed(60), 0),
                                        MaterialRules.condition(
                                                MaterialRules.not(MaterialRules.aboveY(YOffset.fixed(63), 0)),
                                                MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE_SWAMP, 0.0), WATER)
                                        )
                                )
                        )
                )
        ), MaterialRules.sequence(MaterialRules.condition(
                MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.sequence(
                        MaterialRules.condition(
                                MaterialRules.biome(BiomeKeys.BIRCH_FOREST, BiomeKeys.TAIGA),
                                MaterialRules.condition(
                                        MaterialRules.aboveY(YOffset.fixed(60), 0),
                                        MaterialRules.condition(
                                                MaterialRules.not(MaterialRules.aboveY(YOffset.fixed(65), 0)),
                                                MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.SOUL_SAND_LAYER, 0.0), GRAVEL)
                                        )
                                )
                        )
                )
        ), MaterialRules.sequence(MaterialRules.condition(
                        MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.sequence(
                                MaterialRules.condition(
                                        MaterialRules.biome(BiomeKeys.FLOWER_FOREST, BiomeKeys.PLAINS, BiomeKeys.FOREST, BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE),
                                        MaterialRules.condition(
                                                MaterialRules.aboveY(YOffset.fixed(60), 0),
                                                MaterialRules.condition(
                                                        MaterialRules.not(MaterialRules.aboveY(YOffset.fixed(64), 0)),
                                                        MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.SOUL_SAND_LAYER, 0.0), SAND)
                                                )
                                        )
                                )
                        )
                ), materialRule)));
    }
}
//6217428885497005581