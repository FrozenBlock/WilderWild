package net.frozenblock.wilderwild.world.feature.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryCodecs;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.List;
import java.util.Objects;

public class ShelfFungusFeatureConfig implements FeatureConfig {
    public static final Codec<ShelfFungusFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Registry.BLOCK.getCodec().fieldOf("block").flatXmap(ShelfFungusFeatureConfig::validateBlock, DataResult::success).orElse((ShelfFungusBlock)RegisterBlocks.BROWN_SHELF_FUNGUS).forGetter((config) -> {
            return config.fungus;
        }), Codec.intRange(1, 64).fieldOf("search_range").orElse(10).forGetter((config) -> {
            return config.searchRange;
        }), Codec.BOOL.fieldOf("can_place_on_floor").orElse(false).forGetter((config) -> {
            return config.placeOnFloor;
        }), Codec.BOOL.fieldOf("can_place_on_ceiling").orElse(false).forGetter((config) -> {
            return config.placeOnCeiling;
        }), Codec.BOOL.fieldOf("can_place_on_wall").orElse(false).forGetter((config) -> {
            return config.placeOnWalls;
        }), RegistryCodecs.entryList(Registry.BLOCK_KEY).fieldOf("can_be_placed_on").forGetter((config) -> {
            return config.canPlaceOn;
        })).apply(instance, ShelfFungusFeatureConfig::new);
    });
    public final ShelfFungusBlock fungus;
    public final int searchRange;
    public final boolean placeOnFloor;
    public final boolean placeOnCeiling;
    public final boolean placeOnWalls;
    public final RegistryEntryList<Block> canPlaceOn;
    private final ObjectArrayList<Direction> directions;

    private static DataResult<ShelfFungusBlock> validateBlock(Block block) {
        DataResult<ShelfFungusBlock> var10000;
        if (block instanceof ShelfFungusBlock shelfFungusBlock) {
            var10000 = DataResult.success(shelfFungusBlock);
        } else {
            var10000 = DataResult.error("Growth block should be a shelf fungus block bruh bruh bruh bruh bruh");
        }

        return var10000;
    }

    public ShelfFungusFeatureConfig(ShelfFungusBlock fungus, int searchRange, boolean placeOnFloor, boolean placeOnCeiling, boolean placeOnWalls, RegistryEntryList<Block> canPlaceOn) {
        this.fungus = fungus;
        this.searchRange = searchRange;
        this.placeOnFloor = placeOnFloor;
        this.placeOnCeiling = placeOnCeiling;
        this.placeOnWalls = placeOnWalls;
        this.canPlaceOn = canPlaceOn;
        this.directions = new ObjectArrayList<>(6);
        if (placeOnCeiling) {
            this.directions.add(Direction.UP);
        }

        if (placeOnFloor) {
            this.directions.add(Direction.DOWN);
        }

        if (placeOnWalls) {
            Direction.Type var10000 = Direction.Type.HORIZONTAL;
            ObjectArrayList<Direction> var10001 = this.directions;
            Objects.requireNonNull(var10001);
            var10000.forEach(var10001::add);
        }

    }

    public List<Direction> shuffleDirections(Random random, Direction excluded) {
        return Util.copyShuffled(this.directions.stream().filter((direction) -> {
            return direction != excluded;
        }), random);
    }

    public List<Direction> shuffleDirections(Random random) {
        return Util.copyShuffled(this.directions, random);
    }
}
