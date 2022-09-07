package net.frozenblock.wilderwild.world.feature.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record NematocystFeatureConfig(BlockStateProvider stateProvider, int tries, int xzSpread, int ySpread) implements FeatureConfiguration {

    public static final Codec<NematocystFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BlockStateProvider.CODEC.fieldOf("stateProvider").forGetter((config) -> {
            return config.stateProvider;
        }), ExtraCodecs.POSITIVE_INT.fieldOf("tries").orElse(128).forGetter((config) -> {
            return config.tries;
        }), ExtraCodecs.NON_NEGATIVE_INT.fieldOf("xz_spread").orElse(7).forGetter((config) -> {
            return config.xzSpread;
        }), ExtraCodecs.NON_NEGATIVE_INT.fieldOf("y_spread").orElse(3).forGetter((config) -> {
            return config.ySpread;
        })).apply(instance, NematocystFeatureConfig::new);
    });

}

