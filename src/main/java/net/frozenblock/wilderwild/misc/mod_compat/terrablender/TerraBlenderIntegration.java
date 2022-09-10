package net.frozenblock.wilderwild.misc.mod_compat.terrablender;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.world.gen.noise.WilderNoise;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

import java.util.ArrayList;

public final class TerraBlenderIntegration implements TerraBlenderApi {

    @Override
    public void onTerraBlenderInitialized() {
        // Set the weight to the number of biomes added by the mod.
        Regions.register(new WilderOverworldRegion(WilderWild.id("overworld"), 3));

        // Register our surface rules
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, WilderWild.MOD_ID, makeRules());
    }


    // SURFACE RULES

    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource GRAVEL = makeStateRule(Blocks.GRAVEL);
    private static final SurfaceRules.RuleSource SAND = makeStateRule(Blocks.SAND);
    private static final SurfaceRules.RuleSource WATER = makeStateRule(Blocks.WATER);

    private static SurfaceRules.RuleSource makeRules() {
        ArrayList<SurfaceRules.RuleSource> surfaceRuleList = new ArrayList<>();

        var cypressWetlands = SurfaceRules.ifTrue(
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
        );

        surfaceRuleList.add(cypressWetlands);

        if (ClothConfigInteractionHandler.betaBeaches()) {
            var betaBeaches = SurfaceRules.sequence(SurfaceRules.ifTrue(
                    SurfaceRules.UNDER_FLOOR, SurfaceRules.sequence(
                            SurfaceRules.ifTrue(
                                    SurfaceRules.isBiome(Biomes.BIRCH_FOREST, Biomes.TAIGA, Biomes.FROZEN_RIVER, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.SNOWY_TAIGA, RegisterWorldgen.MIXED_FOREST),
                                    SurfaceRules.ifTrue(
                                            SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
                                            SurfaceRules.ifTrue(
                                                    SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(65), 0)),
                                                    SurfaceRules.ifTrue(SurfaceRules.noiseCondition(WilderNoise.SAND_BEACH, 0.12, 1.7976931348623157E308), GRAVEL)
                                            )
                                    )
                            )
                    )
            ), SurfaceRules.sequence(SurfaceRules.ifTrue(
                    SurfaceRules.DEEP_UNDER_FLOOR, SurfaceRules.sequence(
                            SurfaceRules.ifTrue(
                                    SurfaceRules.isBiome(Biomes.FLOWER_FOREST, Biomes.FOREST, Biomes.DARK_FOREST),
                                    SurfaceRules.ifTrue(
                                            SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
                                            SurfaceRules.ifTrue(
                                                    SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(65), 0)),
                                                    SurfaceRules.ifTrue(SurfaceRules.noiseCondition(WilderNoise.SAND_BEACH, 0.12, 1.7976931348623157E308), SAND)
                                            )
                                    )
                            )
                    )
            ), SurfaceRules.sequence(SurfaceRules.ifTrue(
                    SurfaceRules.DEEP_UNDER_FLOOR, SurfaceRules.sequence(
                            SurfaceRules.ifTrue(
                                    SurfaceRules.isBiome(Biomes.JUNGLE, Biomes.SPARSE_JUNGLE, Biomes.BAMBOO_JUNGLE, Biomes.SAVANNA),
                                    SurfaceRules.ifTrue(
                                            SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
                                            SurfaceRules.ifTrue(
                                                    SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(64), 0)),
                                                    SurfaceRules.ifTrue(SurfaceRules.noiseCondition(WilderNoise.SAND_BEACH, 0.12, 1.7976931348623157E308), SAND)
                                            )
                                    )
                            )
                    )
            ))));

            surfaceRuleList.add(betaBeaches);

            //SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.JUNGLE),
            // SurfaceRules.ifTrue(SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
            // SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.yStartCheck(VerticalAnchor.absolute(65),0),
            // SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.steep()),
            // SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.noiseCondition(WilderNoise.SAND_BEACH, 0.12, 1.7976931348623157E308),
            // SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
            // SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(3, false, CaveSurface.CEILING),SANDSTONE), SAND})),
            // SurfaceRules.ifTrue(SurfaceRules.waterStartCheck(-6, -1),
            // SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
            // SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.ON_CEILING,
            //SANDSTONE), SAND})), SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, SANDSTONE)}))}))})))})))}));
        }
        return new SurfaceRules.SequenceRuleSource(surfaceRuleList);
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
