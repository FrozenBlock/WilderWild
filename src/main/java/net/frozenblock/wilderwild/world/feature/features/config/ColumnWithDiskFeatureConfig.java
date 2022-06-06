package net.frozenblock.wilderwild.world.feature.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryCodecs;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.feature.FeatureConfig;

public class ColumnWithDiskFeatureConfig implements FeatureConfig {
    public static final Codec<ColumnWithDiskFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Registry.BLOCK.getCodec().fieldOf("block").flatXmap(ColumnWithDiskFeatureConfig::validateBlock, DataResult::success).orElse(RegisterBlocks.TERMITE_MOUND).forGetter((config) -> {
            return config.columnBlock;
        }), Registry.BLOCK.getCodec().fieldOf("secondaryBlock").flatXmap(ColumnWithDiskFeatureConfig::validateBlock, DataResult::success).orElse(RegisterBlocks.STRIPPED_BAOBAB_LOG).forGetter((config) -> {
            return config.secondaryBlock;
        }), IntProvider.NON_NEGATIVE_CODEC.fieldOf("radius").forGetter((config) -> {
            return config.radius;
        }), IntProvider.NON_NEGATIVE_CODEC.fieldOf("height").forGetter((config) -> {
            return config.height;
        }), RegistryCodecs.entryList(Registry.BLOCK_KEY).fieldOf("replaceable").forGetter((config) -> {
            return config.replaceable;
        }), RegistryCodecs.entryList(Registry.BLOCK_KEY).fieldOf("diskBlocks").forGetter((config) -> {
            return config.diskBlocks;
        })).apply(instance, ColumnWithDiskFeatureConfig::new);
    });
    public final Block columnBlock;
    public final Block secondaryBlock;
    public final IntProvider radius;
    public final IntProvider height;
    public final RegistryEntryList<Block> replaceable;
    public final RegistryEntryList<Block> diskBlocks;

    private static DataResult<Block> validateBlock(Block block) {
        return DataResult.success(block);
    }

    public ColumnWithDiskFeatureConfig(Block columnBlock, Block secondaryBlock, IntProvider radius, IntProvider height, RegistryEntryList<Block> replaceable, RegistryEntryList<Block> diskBlocks) {
        this.columnBlock = columnBlock;
        this.secondaryBlock = secondaryBlock;
        this.radius = radius;
        this.height = height;
        this.replaceable = replaceable;
        this.diskBlocks = diskBlocks;
    }

}
