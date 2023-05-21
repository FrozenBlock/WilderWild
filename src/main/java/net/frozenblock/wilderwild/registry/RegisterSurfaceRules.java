package net.frozenblock.wilderwild.registry;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.worldgen.surface.api.FrozenDimensionBoundRuleSource;
import net.frozenblock.lib.worldgen.surface.api.FrozenSurfaceRuleEntrypoint;
import net.frozenblock.lib.worldgen.surface.api.FrozenSurfaceRules;
import net.frozenblock.lib.worldgen.surface.impl.BiomeTagConditionSource;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.interfaces.BetaBeachEntrypoint;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.generation.conditionsource.BetaBeachConditionSource;
import net.frozenblock.wilderwild.world.generation.noise.WilderNoise;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import org.jetbrains.annotations.NotNull;

public final class RegisterSurfaceRules implements FrozenSurfaceRuleEntrypoint, BetaBeachEntrypoint {

	// SURFACE RULES

	public static SurfaceRules.RuleSource cypressSurfaceRules() {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(RegisterWorldgen.CYPRESS_WETLANDS),
			SurfaceRules.ifTrue(
				SurfaceRules.ON_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.yBlockCheck(
						VerticalAnchor.absolute(60),
						0
					),
					SurfaceRules.ifTrue(
						SurfaceRules.not(
							SurfaceRules.yBlockCheck(
								VerticalAnchor.absolute(63),
								0
							)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SWAMP, 0.0),
							FrozenSurfaceRules.WATER
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource warmRiverRules() {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(RegisterWorldgen.WARM_RIVER),
			SurfaceRules.ifTrue(
				SurfaceRules.yBlockCheck(VerticalAnchor.absolute(32), 0),
				SurfaceRules.sequence(
					SurfaceRules.ifTrue(
						SurfaceRules.DEEP_UNDER_FLOOR,
						FrozenSurfaceRules.SAND
					),
					FrozenSurfaceRules.SANDSTONE
				)
			)
		);
	}

	public static SurfaceRules.RuleSource desertRules() {
		return SurfaceRules.sequence(
			SurfaceRules.ifTrue(
				SurfaceRules.ON_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.ON_CEILING,
							FrozenSurfaceRules.SANDSTONE
						),
						FrozenSurfaceRules.SAND
					)
				)
			),
			SurfaceRules.ifTrue(
				SurfaceRules.waterStartCheck(-6, -1),
				SurfaceRules.sequence(
					SurfaceRules.ifTrue(
						SurfaceRules.UNDER_FLOOR,
						SurfaceRules.sequence(
							SurfaceRules.ifTrue(
								SurfaceRules.ON_CEILING,
								FrozenSurfaceRules.SANDSTONE
							),
							FrozenSurfaceRules.SAND
						)
					),
					SurfaceRules.ifTrue(
						SurfaceRules.VERY_DEEP_UNDER_FLOOR,
						FrozenSurfaceRules.SANDSTONE
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource oasisRules() {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(RegisterWorldgen.OASIS),
			desertRules()
		);
	}

	public static SurfaceRules.RuleSource aridGrass() {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(RegisterWorldgen.ARID_SAVANNA, RegisterWorldgen.ARID_FOREST),
			SurfaceRules.sequence(
				SurfaceRules.ifTrue(
					SurfaceRules.ON_FLOOR,
					SurfaceRules.ifTrue(
						SurfaceRules.noiseCondition(Noises.SURFACE, 0.155, 0.3666),
						SurfaceRules.sequence(
							SurfaceRules.ifTrue(
								SurfaceRules.ON_CEILING,
								FrozenSurfaceRules.DIRT
							),
							FrozenSurfaceRules.GRASS_BLOCK
						)
					)
				),
				SurfaceRules.ifTrue(
					SurfaceRules.noiseCondition(Noises.SURFACE, 0.155, 0.3666),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.UNDER_FLOOR,
							SurfaceRules.sequence(
								SurfaceRules.ifTrue(
									SurfaceRules.ON_CEILING,
									FrozenSurfaceRules.DIRT
								),
								FrozenSurfaceRules.DIRT
							)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.DEEP_UNDER_FLOOR,
							FrozenSurfaceRules.DIRT
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource aridRules() {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(RegisterWorldgen.ARID_SAVANNA, RegisterWorldgen.ARID_FOREST),
			desertRules()
		);
	}

	public static SurfaceRules.RuleSource oldGrowthSnowyTaigaRules() {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA),
			SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SURFACE, 1.75D / 8.25D, Double.MAX_VALUE),
							FrozenSurfaceRules.COARSE_DIRT
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SURFACE, -0.95D / 8.25D, Double.MAX_VALUE),
							FrozenSurfaceRules.PODZOL
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SURFACE, 0.0222, 0.055),
							FrozenSurfaceRules.POWDER_SNOW
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SURFACE, 0.065, 0.12),
							FrozenSurfaceRules.SNOW_BLOCK
						)
					)
				)
			)
		);
	}
	public static SurfaceRules.RuleSource oldGrowthDarkForestRules() {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(RegisterWorldgen.OLD_GROWTH_DARK_FOREST),
			SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SURFACE, -0.0667, 0.04),
							FrozenSurfaceRules.PODZOL
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource temperateRainforestRules() {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(RegisterWorldgen.TEMPERATE_RAINFOREST),
			SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SURFACE, 0.095, 0.2),
							FrozenSurfaceRules.makeStateRule(Blocks.PODZOL)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.065, 0.15),
							FrozenSurfaceRules.makeStateRule(Blocks.MOSS_BLOCK)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SURFACE_SECONDARY, 0.0667, 0.4),
							FrozenSurfaceRules.COARSE_DIRT
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource rainforestRules() {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(RegisterWorldgen.RAINFOREST),
			SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.waterBlockCheck(-1, 0),
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.065, 0.15),
							FrozenSurfaceRules.makeStateRule(Blocks.MOSS_BLOCK)
						)
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource gravelBetaBeach() {
		return SurfaceRules.ifTrue(
			SurfaceRules.UNDER_FLOOR,
			SurfaceRules.ifTrue(
				SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
				SurfaceRules.ifTrue(
					SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(65), 0)),
					SurfaceRules.ifTrue(
						SurfaceRules.noiseCondition(WilderNoise.GRAVEL_BEACH_KEY, 0.12, 1.7976931348623157E308),
						FrozenSurfaceRules.GRAVEL
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource sandBetaBeach() {
		return SurfaceRules.ifTrue(
			SurfaceRules.DEEP_UNDER_FLOOR,
			SurfaceRules.ifTrue(
				SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
				SurfaceRules.ifTrue(
					SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(65), 0)),
					SurfaceRules.ifTrue(
						SurfaceRules.noiseCondition(WilderNoise.SAND_BEACH_KEY, 0.12, 1.7976931348623157E308),
						FrozenSurfaceRules.SAND
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource multiLayerSandBetaBeach() {
		return SurfaceRules.ifTrue(
			SurfaceRules.DEEP_UNDER_FLOOR,
			SurfaceRules.ifTrue(
				SurfaceRules.yStartCheck(VerticalAnchor.absolute(58), 0),
				SurfaceRules.ifTrue(
					SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(64), 0)),
					SurfaceRules.ifTrue(
						SurfaceRules.noiseCondition(WilderNoise.SAND_BEACH_KEY, 0.12, 1.7976931348623157E308),
						FrozenSurfaceRules.SAND
					)
				)
			)
		);
	}

	public static SurfaceRules.RuleSource betaBeaches() {
		return SurfaceRules.ifTrue(
			BetaBeachConditionSource.betaBeachConditionSource(),
			SurfaceRules.sequence(
				gravelBetaBeaches(),
				sandBetaBeaches(),
				multiLayerSandBetaBeaches()
			)
		);
	}

	public static SurfaceRules.RuleSource gravelBetaBeaches() {
		return SurfaceRules.ifTrue(
			BiomeTagConditionSource.isBiomeTag(WilderBiomeTags.GRAVEL_BEACH),
			gravelBetaBeach()
		);
	}

	public static SurfaceRules.RuleSource sandBetaBeaches() {
		return SurfaceRules.ifTrue(
			BiomeTagConditionSource.isBiomeTag(WilderBiomeTags.SAND_BEACHES),
			sandBetaBeach()
		);
	}

	public static SurfaceRules.RuleSource multiLayerSandBetaBeaches() {
		return SurfaceRules.ifTrue(
			BiomeTagConditionSource.isBiomeTag(WilderBiomeTags.MULTI_LAYER_SAND_BEACHES),
			multiLayerSandBetaBeach()
		);
	}

	public static SurfaceRules.RuleSource optimizedBetaBeaches() {
		List<EntrypointContainer<BetaBeachEntrypoint>> betaBeachEntrypoints = FabricLoader.getInstance().getEntrypointContainers("wilderwild:beta_beaches", BetaBeachEntrypoint.class);
		return SurfaceRules.ifTrue(
			BetaBeachConditionSource.betaBeachConditionSource(),
			SurfaceRules.sequence(
				optimizedGravelBetaBeaches(betaBeachEntrypoints),
				optimizedSandBetaBeaches(betaBeachEntrypoints),
				optimizedMultiLayerSandBetaBeaches(betaBeachEntrypoints)
			)
		);
	}

	public static SurfaceRules.RuleSource optimizedGravelBetaBeaches(List<EntrypointContainer<BetaBeachEntrypoint>> containers) {
		List<ResourceKey<Biome>> biomes = new ArrayList<>();
		for (EntrypointContainer<BetaBeachEntrypoint> container : containers) {
			container.getEntrypoint().addGravelBeachBiomes(biomes);
		}
		return SurfaceRules.ifTrue(
			FrozenSurfaceRules.isBiome(biomes),
			gravelBetaBeaches()
		);
	}

	public static SurfaceRules.RuleSource optimizedSandBetaBeaches(List<EntrypointContainer<BetaBeachEntrypoint>> containers) {
		List<ResourceKey<Biome>> biomes = new ArrayList<>();
		for (EntrypointContainer<BetaBeachEntrypoint> container : containers) {
			container.getEntrypoint().addSandBeachBiomes(biomes);
		}
		return SurfaceRules.ifTrue(
			FrozenSurfaceRules.isBiome(biomes),
			sandBetaBeach()
		);
	}

	public static SurfaceRules.RuleSource optimizedMultiLayerSandBetaBeaches(List<EntrypointContainer<BetaBeachEntrypoint>> containers) {
		List<ResourceKey<Biome>> biomes = new ArrayList<>();
		for (EntrypointContainer<BetaBeachEntrypoint> container : containers) {
			container.getEntrypoint().addMultiLayerBeachBiomes(biomes);
		}
		return SurfaceRules.ifTrue(
			FrozenSurfaceRules.isBiome(biomes),
			multiLayerSandBetaBeach()
		);
	}

	@Override
	public void addOverworldSurfaceRules(@NotNull ArrayList<SurfaceRules.RuleSource> context) {
		SurfaceRules.RuleSource surfaceRules = SurfaceRules.sequence(
			WilderSharedConstants.config().optimizedBetaBeaches() ? optimizedBetaBeaches() : betaBeaches(),
			cypressSurfaceRules(),
			warmRiverRules(),
			oasisRules(),
			aridGrass(),
			aridRules(),
			oldGrowthSnowyTaigaRules(),
			oldGrowthDarkForestRules(),
			temperateRainforestRules(),
			rainforestRules()
		);

		context.add(surfaceRules);
		WilderSharedConstants.log("Wilder Wild's Overworld Surface Rules have been added!", true);
	}

	@Override
	public void addOverworldSurfaceRulesNoPrelimSurface(ArrayList<SurfaceRules.RuleSource> context) {

	}

	@Override
	public void addNetherSurfaceRules(ArrayList<SurfaceRules.RuleSource> context) {

	}

	@Override
	public void addEndSurfaceRules(ArrayList<SurfaceRules.RuleSource> context) {

	}

	@Override
	public void addSurfaceRules(ArrayList<FrozenDimensionBoundRuleSource> context) {

	}

	@Override
	public void addGravelBeachBiomes(List<ResourceKey<Biome>> context) {
		context.add(Biomes.BIRCH_FOREST);
		context.add(Biomes.FROZEN_RIVER);
		context.add(RegisterWorldgen.MIXED_FOREST);
		context.add(Biomes.OLD_GROWTH_BIRCH_FOREST);
		context.add(Biomes.OLD_GROWTH_PINE_TAIGA);
		context.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA);
		context.add(Biomes.TAIGA);
		context.add(Biomes.SNOWY_TAIGA);
		context.add(RegisterWorldgen.BIRCH_TAIGA);
		context.add(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA);
		context.add(RegisterWorldgen.DARK_BIRCH_FOREST);
		context.add(RegisterWorldgen.DARK_TAIGA);
	}

	@Override
	public void addSandBeachBiomes(List<ResourceKey<Biome>> context) {
		context.add(Biomes.DARK_FOREST);
		context.add(Biomes.FLOWER_FOREST);
		context.add(Biomes.FOREST);
		context.add(RegisterWorldgen.PARCHED_FOREST);
		context.add(RegisterWorldgen.ARID_FOREST);
		context.add(RegisterWorldgen.OLD_GROWTH_DARK_FOREST);
		context.add(RegisterWorldgen.SEMI_BIRCH_FOREST);
	}

	@Override
	public void addMultiLayerBeachBiomes(List<ResourceKey<Biome>> context) {
		context.add(Biomes.BAMBOO_JUNGLE);
		context.add(Biomes.JUNGLE);
		context.add(Biomes.SAVANNA);
		context.add(Biomes.SPARSE_JUNGLE);
		context.add(RegisterWorldgen.BIRCH_JUNGLE);
		context.add(RegisterWorldgen.SPARSE_BIRCH_JUNGLE);
		context.add(RegisterWorldgen.ARID_SAVANNA);
		if (FrozenBools.HAS_TERRALITH) {
			context.add(ResourceKey.create(Registries.BIOME, new ResourceLocation("terralith", "arid_highlands")));
		}
	}
}
