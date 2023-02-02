package net.frozenblock.wilderwild.world.generation.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class LargeMesogleaConfig implements FeatureConfiguration {
	public static final Codec<LargeMesogleaConfig> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").orElse(30).forGetter((config) -> {
			return config.floorToCeilingSearchRange;
		}), IntProvider.codec(1, 60).fieldOf("column_radius").forGetter((config) -> {
			return config.columnRadius;
		}), BlockStateProvider.CODEC.fieldOf("block").forGetter((config) -> {
				return	config.pathBlock;
			}), FloatProvider.codec(0.0F, 20.0F).fieldOf("height_scale").forGetter((config) -> {
			return config.heightScale;
		}), Codec.floatRange(0.1F, 1.0F).fieldOf("max_column_radius_to_cave_height_ratio").forGetter((config) -> {
			return config.maxColumnRadiusToCaveHeightRatio;
		}), FloatProvider.codec(0.1F, 10.0F).fieldOf("stalactite_bluntness").forGetter((config) -> {
			return config.stalactiteBluntness;
		}), FloatProvider.codec(0.1F, 10.0F).fieldOf("stalagmite_bluntness").forGetter((config) -> {
			return config.stalagmiteBluntness;
		}), FloatProvider.codec(0.0F, 2.0F).fieldOf("wind_speed").forGetter((config) -> {
			return config.windSpeed;
		}), Codec.intRange(0, 100).fieldOf("min_radius_for_wind").forGetter((config) -> {
			return config.minRadiusForWind;
		}), Codec.floatRange(0.0F, 5.0F).fieldOf("min_bluntness_for_wind").forGetter((config) -> {
			return config.minBluntnessForWind;
		})).apply(instance, LargeMesogleaConfig::new);
	});
	public final int floorToCeilingSearchRange;
	public final IntProvider columnRadius;
	public final BlockStateProvider pathBlock;
	public final FloatProvider heightScale;
	public final float maxColumnRadiusToCaveHeightRatio;
	public final FloatProvider stalactiteBluntness;
	public final FloatProvider stalagmiteBluntness;
	public final FloatProvider windSpeed;
	public final int minRadiusForWind;
	public final float minBluntnessForWind;

	public LargeMesogleaConfig(int floorToCeilingSearchRange, IntProvider columnRadius, BlockStateProvider pathBlock, FloatProvider heightScale, float maxColumnRadiusToCaveHeightRatio, FloatProvider stalactiteBluntness, FloatProvider stalagmiteBluntness, FloatProvider windSpeed, int minRadiusForWind, float minBluntnessForWind) {
		this.floorToCeilingSearchRange = floorToCeilingSearchRange;
		this.columnRadius = columnRadius;
		this.pathBlock = pathBlock;
		this.heightScale = heightScale;
		this.maxColumnRadiusToCaveHeightRatio = maxColumnRadiusToCaveHeightRatio;
		this.stalactiteBluntness = stalactiteBluntness;
		this.stalagmiteBluntness = stalagmiteBluntness;
		this.windSpeed = windSpeed;
		this.minRadiusForWind = minRadiusForWind;
		this.minBluntnessForWind = minBluntnessForWind;
	}
}

