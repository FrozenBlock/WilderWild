package net.frozenblock.wilderwild.world.feature.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record NematocystFeatureConfig(BlockStateProvider stateProvider, int tries, int xzSpread,
									  int ySpread) implements FeatureConfiguration {

	public static final Codec<NematocystFeatureConfig> CODEC = RecordCodecBuilder.create((instance) ->
			instance.group(BlockStateProvider.CODEC.fieldOf("stateProvider").forGetter((config) -> config.stateProvider),
							ExtraCodecs.POSITIVE_INT.fieldOf("tries").orElse(256).forGetter((config) -> config.tries),
							ExtraCodecs.NON_NEGATIVE_INT.fieldOf("xz_spread").orElse(32).forGetter((config) -> config.xzSpread),
							ExtraCodecs.NON_NEGATIVE_INT.fieldOf("y_spread").orElse(6).forGetter((config) -> config.ySpread))
					.apply(instance, NematocystFeatureConfig::new));

}

