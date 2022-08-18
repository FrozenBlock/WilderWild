package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.world.gen.noise.WilderNoiseKeys;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.world.level.biome.Biomes;
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
    private static SurfaceRules.RuleSource injected(SurfaceRules.RuleSource materialRule) {

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
        ), SurfaceRules.sequence(SurfaceRules.ifTrue(
                SurfaceRules.UNDER_FLOOR, SurfaceRules.sequence(
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(Biomes.BIRCH_FOREST, Biomes.TAIGA, Biomes.FROZEN_RIVER, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.SNOWY_TAIGA, RegisterWorldgen.MIXED_FOREST),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.yBlockCheck(VerticalAnchor.absolute(58), 0),
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(64), 0)),
                                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(WilderNoiseKeys.SAND_BEACH, 0.12, 1.7976931348623157E308), GRAVEL)
                                        )
                                )
                        )
                )
        ), SurfaceRules.sequence(SurfaceRules.ifTrue(
                        SurfaceRules.DEEP_UNDER_FLOOR, SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        SurfaceRules.isBiome(Biomes.FLOWER_FOREST, Biomes.FOREST, Biomes.JUNGLE, Biomes.SPARSE_JUNGLE, Biomes.SAVANNA, Biomes.DARK_FOREST),
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.yBlockCheck(VerticalAnchor.absolute(58), 0),
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(64), 0)),
                                                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(WilderNoiseKeys.SAND_BEACH, 0.12, 1.7976931348623157E308), SAND)
                                                )
                                        )
                                )
                        )
                ), materialRule)));
    }
}
//6217428885497005581