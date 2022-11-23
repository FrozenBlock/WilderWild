package net.frozenblock.wilderwild.world.generation.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class ShelfFungusTreeDecorator extends TreeDecorator {
    public static final Codec<ShelfFungusTreeDecorator> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((treeDecorator) -> {
            return treeDecorator.probability;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("red_shelf_fungus_chance").forGetter((treeDecorator) -> {
            return treeDecorator.redChance;
        })).apply(instance, ShelfFungusTreeDecorator::new);
    });
    private final float probability;
    private final float redChance;

    public ShelfFungusTreeDecorator(float probability, float redChance) {
        this.probability = probability;
        this.redChance = redChance;
    }

    protected TreeDecoratorType<?> type() {
        return WilderTreeDecorators.FUNGUS_TREE_DECORATOR;
    }

    public void place(TreeDecorator.Context generator) {
        RandomSource abstractRandom = generator.random();
        if (abstractRandom.nextFloat() <= this.probability) {
            List<BlockPos> list = generator.logs();
            list.forEach((pos) -> {
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    if (abstractRandom.nextFloat() <= 0.25F) {
                        BlockPos blockPos = pos.offset(direction.getStepX(), 0, direction.getStepZ());
                        if (generator.isAir(blockPos)) {
                            if (generator.random().nextFloat() < redChance) {
                                generator.setBlock(blockPos, RegisterBlocks.RED_SHELF_FUNGUS.defaultBlockState().setValue(ShelfFungusBlock.STAGE, abstractRandom.nextInt(3) + 1).setValue(ShelfFungusBlock.FACE, AttachFace.WALL).setValue(ShelfFungusBlock.FACING, direction));
                            } else {
                                generator.setBlock(blockPos, RegisterBlocks.BROWN_SHELF_FUNGUS.defaultBlockState().setValue(ShelfFungusBlock.STAGE, abstractRandom.nextInt(3) + 1).setValue(ShelfFungusBlock.FACE, AttachFace.WALL).setValue(ShelfFungusBlock.FACING, direction));
                            }
                        }
                    }
                }
            });
        }
    }

}
