package net.frozenblock.wilderwild.world.generation.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class PathSwapUnderWaterFeatureConfig implements FeatureConfiguration {
    public static final Codec<PathSwapUnderWaterFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BlockStateProvider.CODEC.fieldOf("block").forGetter((config) -> {
            return config.pathBlock;
        }), BlockStateProvider.CODEC.fieldOf("waterPathBlock").forGetter((config) -> {
			return config.waterPathBlock;
		}), Codec.intRange(1, 64).fieldOf("radius").orElse(10).forGetter((config) -> {
            return config.radius;
        }), Codec.intRange(1, 4).fieldOf("noise").orElse(4).forGetter((config) -> {
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
        }), RegistryCodecs.homogeneousList(Registry.BLOCK_REGISTRY).fieldOf("replaceable").forGetter((config) -> {
            return config.replaceable;
        })).apply(instance, PathSwapUnderWaterFeatureConfig::new);
    });
    public final BlockStateProvider pathBlock;
	public final BlockStateProvider waterPathBlock;
    public final int radius;
    public final int noise;
    public final double multiplier;
    public final double minThresh;
    public final double maxThresh;
    public final boolean useY;
    public final boolean multiplyY;
    public final HolderSet<Block> replaceable;

    private static DataResult<Block> validateBlock(Block block) {
        return DataResult.success(block);
    }

    public PathSwapUnderWaterFeatureConfig(BlockStateProvider pathBlock, BlockStateProvider waterPathBlock, int radius, int noise, double multiplier, double minThresh, double maxThresh, boolean useY, boolean multiplyY, HolderSet<Block> replaceable) {
        this.pathBlock = pathBlock;
		this.waterPathBlock = waterPathBlock;
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
