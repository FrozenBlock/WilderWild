package net.frozenblock.wilderwild.world.feature.features.config;/*

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.frozenblock.wilderwild.block.NematocystBlock;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;

public class ClusterFeatureConfig implements FeatureConfiguration {
    public static final Codec<ClusterFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Registry.BLOCK
                                    .byNameCodec()
                                    .fieldOf("block")
                                    .flatXmap(ClusterFeatureConfig::apply, DataResult::success)
                                    .orElse((NematocystBlock) RegisterBlocks.NEMATOCYST)
                                    .forGetter(config -> config.placeBlock),
                            Codec.intRange(1, 64).fieldOf("search_range").orElse(10).forGetter(config -> config.searchRange),
                            Codec.BOOL.fieldOf("can_place_on_floor").orElse(false).forGetter(config -> config.canPlaceOnFloor),
                            Codec.BOOL.fieldOf("can_place_on_ceiling").orElse(false).forGetter(config -> config.canPlaceOnCeiling),
                            Codec.BOOL.fieldOf("can_place_on_wall").orElse(false).forGetter(config -> config.canPlaceOnWall),
                            Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spreading").orElse(0.5F).forGetter(config -> config.chanceOfSpreading),
                            RegistryCodecs.homogeneousList(Registry.BLOCK_REGISTRY).fieldOf("can_be_placed_on").forGetter(config -> config.canBePlacedOn)
                    )
                    .apply(instance, ClusterFeatureConfig::new)
    );
    public final NematocystBlock placeBlock;
    public final int searchRange;
    public final boolean canPlaceOnFloor;
    public final boolean canPlaceOnCeiling;
    public final boolean canPlaceOnWall;
    public final float chanceOfSpreading;
    public final HolderSet<Block> canBePlacedOn;
    private final ObjectArrayList<Direction> validDirections;

    private static DataResult<NematocystBlock> apply(Block block) {
        return block instanceof NematocystBlock clusterBlock
                ? DataResult.success(clusterBlock)
                : DataResult.error("Growth block should be an amethyst cluster block");
    }

    public ClusterFeatureConfig(
            NematocystBlock block,
            int searchRange,
            boolean canPlaceOnFloor,
            boolean canPlaceOnCeiling,
            boolean canPlaceOnWall,
            float spreadChance,
            HolderSet<Block> canBePlacedOn
    ) {
        this.placeBlock = block;
        this.searchRange = searchRange;
        this.canPlaceOnFloor = canPlaceOnFloor;
        this.canPlaceOnCeiling = canPlaceOnCeiling;
        this.canPlaceOnWall = canPlaceOnWall;
        this.chanceOfSpreading = spreadChance;
        this.canBePlacedOn = canBePlacedOn;
        this.validDirections = new ObjectArrayList<>(6);
        if (canPlaceOnCeiling) {
            this.validDirections.add(Direction.UP);
        }

        if (canPlaceOnFloor) {
            this.validDirections.add(Direction.DOWN);
        }

        if (canPlaceOnWall) {
            Direction.Plane.HORIZONTAL.forEach(this.validDirections::add);
        }

    }

    public List<Direction> getShuffledDirectionsExcept(RandomSource random, Direction direction) {
        return Util.toShuffledList(this.validDirections.stream().filter(directionx -> directionx != direction), random);
    }

    public List<Direction> getShuffledDirections(RandomSource random) {
        return Util.shuffledCopy(this.validDirections, random);
    }
}
*/