package net.frozenblock.wilderwild.world.generation.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class WilderPillarConfig implements FeatureConfiguration {
    public static final Codec<WilderPillarConfig> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    BlockState.CODEC.fieldOf("columnBlock").forGetter((config) -> config.columnBlock),
                    IntProvider.NON_NEGATIVE_CODEC.fieldOf("height").forGetter((config) -> config.height),
                    RegistryCodecs.homogeneousList(Registry.BLOCK_REGISTRY).fieldOf("replaceable").forGetter((config) -> config.replaceable)
            ).apply(instance, WilderPillarConfig::new));

    public final BlockState columnBlock;
    public final IntProvider height;
    public final HolderSet<Block> replaceable;

    private static DataResult<Block> validateBlock(Block block) {
        return DataResult.success(block);
    }

    public WilderPillarConfig(BlockState columnBlock, IntProvider height, HolderSet<Block> replaceable) {
        this.columnBlock = columnBlock;
        this.height = height;
        this.replaceable = replaceable;
    }

}
