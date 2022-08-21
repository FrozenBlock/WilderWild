package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.misc.config.ModMenuInteractionHandler;
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
public abstract class VanillaSurfaceRulesMixin {

    @Shadow
    @Final
    private static SurfaceRules.RuleSource WATER;
    @Shadow
    @Final
    private static SurfaceRules.RuleSource GRAVEL;

    @Shadow
    @Final
    private static SurfaceRules.RuleSource SAND;

    @Shadow
    @Final
    private static SurfaceRules.RuleSource SANDSTONE;


    @ModifyVariable(method = "overworldLike", at = @At("STORE"), ordinal = 8)
    private static SurfaceRules.RuleSource injected(SurfaceRules.RuleSource materialRule) {
        if (ModMenuInteractionHandler.modWindsweptSavannaPlacement()) {
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
                                            SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
                                            SurfaceRules.ifTrue(
                                                    SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(65), 0)),
                                                    SurfaceRules.ifTrue(SurfaceRules.noiseCondition(WilderNoiseKeys.SAND_BEACH, 0.12, 1.7976931348623157E308), GRAVEL)
                                            )
                                    )
                            )
                    )
            ), SurfaceRules.sequence(SurfaceRules.ifTrue(
                    SurfaceRules.DEEP_UNDER_FLOOR, SurfaceRules.sequence(
                            SurfaceRules.ifTrue(
                                    SurfaceRules.isBiome(Biomes.FLOWER_FOREST, Biomes.FOREST, Biomes.SAVANNA, Biomes.DARK_FOREST),
                                    SurfaceRules.ifTrue(
                                            SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
                                            SurfaceRules.ifTrue(
                                                    SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(65), 0)),
                                                    SurfaceRules.ifTrue(SurfaceRules.noiseCondition(WilderNoiseKeys.SAND_BEACH, 0.12, 1.7976931348623157E308), SAND)
                                            )
                                    )
                            )
                    )
            ), SurfaceRules.sequence(SurfaceRules.ifTrue(
                    SurfaceRules.DEEP_UNDER_FLOOR, SurfaceRules.sequence(
                            SurfaceRules.ifTrue(
                                    SurfaceRules.isBiome(Biomes.JUNGLE, Biomes.SPARSE_JUNGLE, Biomes.BAMBOO_JUNGLE),
                                    SurfaceRules.ifTrue(
                                            SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
                                            SurfaceRules.ifTrue(
                                                    SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(64), 0)),
                                                    SurfaceRules.ifTrue(SurfaceRules.noiseCondition(WilderNoiseKeys.SAND_BEACH, 0.12, 1.7976931348623157E308), SAND)
                                            )
                                    )
                            )
                    )
            ), materialRule))));


            //SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.JUNGLE),
            // SurfaceRules.ifTrue(SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
            // SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.yStartCheck(VerticalAnchor.absolute(65),0),
            // SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.steep()),
            // SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.noiseCondition(WilderNoiseKeys.SAND_BEACH, 0.12, 1.7976931348623157E308),
            // SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
            // SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(3, false, CaveSurface.CEILING),SANDSTONE), SAND})),
            // SurfaceRules.ifTrue(SurfaceRules.waterStartCheck(-6, -1),
            // SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
            // SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.ON_CEILING,
            //SANDSTONE), SAND})), SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, SANDSTONE)}))}))})))})))}));
        }
        return materialRule;
    }
}

//6217428885497005581