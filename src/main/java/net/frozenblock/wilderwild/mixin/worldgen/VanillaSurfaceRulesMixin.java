package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SurfaceRuleData.class)
public class VanillaSurfaceRulesMixin {

    @Shadow
    @Final
    private static SurfaceRules.RuleSource WATER;
    @Shadow @Final private static SurfaceRules.RuleSource GRAVEL;

    @Shadow @Final private static SurfaceRules.RuleSource SAND;

    @ModifyVariable(method = "overworldLike", at = @At("STORE"), ordinal = 8)
    private static Surfacerules.Rulesource injected(SurfaceRules.RuleSource materialRule) {

        return SurfaceRules.sequence(SurfaceRules.ifTrue(
                SurfaceRules.ON_FLOOR, SurfaceRules.sequence(
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(RegisterWorldgen.CYPRESS_WETLANDS),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.yBlockCheck(VerticalAnchor.absolute(60), 0),
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0)),
                                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, 0.0), WATER)
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