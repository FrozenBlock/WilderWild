package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.world.gen.noise.WilderNoiseKeys;
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
                MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, MaterialRules.sequence(
                        MaterialRules.condition(
                                MaterialRules.biome(BiomeKeys.BIRCH_FOREST, BiomeKeys.TAIGA, BiomeKeys.FROZEN_RIVER, BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.OLD_GROWTH_PINE_TAIGA, BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA, BiomeKeys.SNOWY_TAIGA, RegisterWorldgen.MIXED_FOREST),
                                MaterialRules.condition(
                                        MaterialRules.aboveY(YOffset.fixed(58), 0),
                                        MaterialRules.condition(
                                                MaterialRules.not(MaterialRules.aboveY(YOffset.fixed(64), 0)),
                                                MaterialRules.condition(MaterialRules.noiseThreshold(WilderNoiseKeys.SAND_BEACH, 0.12, 1.7976931348623157E308), GRAVEL)
                                        )
                                )
                        )
                )
        ), MaterialRules.sequence(MaterialRules.condition(
                        MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH_RANGE_6, MaterialRules.sequence(
                                MaterialRules.condition(
                                        MaterialRules.biome(BiomeKeys.FLOWER_FOREST, BiomeKeys.FOREST, BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.SAVANNA, BiomeKeys.DARK_FOREST),
                                        MaterialRules.condition(
                                                MaterialRules.aboveY(YOffset.fixed(58), 0),
                                                MaterialRules.condition(
                                                        MaterialRules.not(MaterialRules.aboveY(YOffset.fixed(64), 0)),
                                                        MaterialRules.condition(MaterialRules.noiseThreshold(WilderNoiseKeys.SAND_BEACH, 0.12, 1.7976931348623157E308), SAND)
                                                )
                                        )
                                )
                        )
                ), materialRule)));
    }
}
//6217428885497005581