package net.frozenblock.wilderwild.world.feature.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryCodecs;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.feature.FeatureConfig;

public class ColumnWithDiskFeatureConfig implements FeatureConfig {
    public static final Codec<ColumnWithDiskFeatureConfig> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    BlockState.CODEC.fieldOf("columnBlock").forGetter((config) -> config.columnBlock),
                    BlockState.CODEC.fieldOf("secondaryBlock").forGetter((config) -> config.secondaryBlock),
                    IntProvider.NON_NEGATIVE_CODEC.fieldOf("radius").forGetter((config) -> config.radius),
                    IntProvider.NON_NEGATIVE_CODEC.fieldOf("height").forGetter((config) -> config.height),
                    RegistryCodecs.entryList(Registry.BLOCK_KEY).fieldOf("replaceable").forGetter((config) -> config.replaceable),
                    RegistryCodecs.entryList(Registry.BLOCK_KEY).fieldOf("diskBlocks").forGetter((config) -> config.diskBlocks)
            ).apply(instance, ColumnWithDiskFeatureConfig::new));

    public final BlockState columnBlock;
    public final BlockState secondaryBlock;
    public final IntProvider radius;
    public final IntProvider height;
    public final RegistryEntryList<Block> replaceable;
    public final RegistryEntryList<Block> diskBlocks;

    private static DataResult<Block> validateBlock(Block block) {
        return DataResult.success(block);
    }

    public ColumnWithDiskFeatureConfig(BlockState columnBlock, BlockState secondaryBlock, IntProvider radius, IntProvider height, RegistryEntryList<Block> replaceable, RegistryEntryList<Block> diskBlocks) {
        this.columnBlock = columnBlock;
        this.secondaryBlock = secondaryBlock;
        this.radius = radius;
        this.height = height;
        this.replaceable = replaceable;
        this.diskBlocks = diskBlocks;
    }

}
