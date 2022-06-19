package net.frozenblock.wilderwild.world.feature.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryCodecs;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.feature.FeatureConfig;

public class PathFeatureConfig implements FeatureConfig {
    public static final Codec<PathFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Registry.BLOCK.getCodec().fieldOf("block").flatXmap(PathFeatureConfig::validateBlock, DataResult::success).orElse(Blocks.COARSE_DIRT).forGetter((config) -> {
            return config.pathBlock;
        }), Codec.intRange(1, 64).fieldOf("radius").orElse(10).forGetter((config) -> {
            return config.radius;
        }),Codec.intRange(1, 4).fieldOf("noise").orElse(4).forGetter((config) -> {
            return config.noise;
        }), Codec.doubleRange(0.0001, 128).fieldOf("multiplier").orElse(0.05).forGetter((config) -> {
            return config.multiplier;
        }), Codec.doubleRange(-1, 1).fieldOf("minThresh").orElse(0.2).forGetter((config) -> {
            return config.minThresh;
        }), Codec.doubleRange(-1, 1).fieldOf("maxThresh").orElse(1D).forGetter((config) -> {
            return config.maxThresh;
        }), Codec.BOOL.fieldOf("useY").orElse(false).forGetter((config) -> {
            return config.useY;
        }), Codec.BOOL.fieldOf("multiplyY").orElse(false).forGetter((config) -> {
            return config.multiplyY;
        }), RegistryCodecs.entryList(Registry.BLOCK_KEY).fieldOf("replaceable").forGetter((config) -> {
            return config.replaceable;
        })).apply(instance, PathFeatureConfig::new);
    });
    public final Block pathBlock;
    public final int radius;
    public final int noise;
    public final double multiplier;
    public final double minThresh;
    public final double maxThresh;
    public final boolean useY;
    public final boolean multiplyY;
    public final RegistryEntryList<Block> replaceable;

    private static DataResult<Block> validateBlock(Block block) {
        return DataResult.success(block);
    }

    public PathFeatureConfig(Block pathBlock, int radius, int noise, double multiplier, double minThresh, double maxThresh, boolean useY, boolean multiplyY, RegistryEntryList<Block> replaceable) {
        this.pathBlock = pathBlock;
        this.radius = radius;
        this.noise = noise;
        this.multiplier = multiplier;
        this.minThresh = minThresh;
        this.maxThresh = maxThresh;
        this.useY = useY;
        this.multiplyY = multiplyY;
        this.replaceable = replaceable;
    }

}
