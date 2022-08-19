package net.frozenblock.wilderwild.world.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ChestToStoneChestProcessor extends StructureProcessor {
    public static final ChestToStoneChestProcessor INSTANCE = new ChestToStoneChestProcessor();
    public static final Codec<ChestToStoneChestProcessor> CODEC = Codec.unit(() -> {
        return INSTANCE;
    });

    public ChestToStoneChestProcessor() {
    }

    @Nullable
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader world, BlockPos pos, BlockPos pivot, StructureTemplate.StructureBlockInfo localBlockInfo, StructureTemplate.StructureBlockInfo absoluteBlockInfo, StructurePlaceSettings placementData) {
        RandomSource randomSource = placementData.getRandom(absoluteBlockInfo.pos);
        return (!this.rottableBlocks.isPresent() || localBlockInfo.state.is((HolderSet)this.rottableBlocks.get())) && !(randomSource.nextFloat() <= this.integrity) ? null : absoluteBlockInfo;
    }

    protected StructureProcessorType<?> getType() {
        return StructureProcessorType.BLOCK_ROT;
    }
}
