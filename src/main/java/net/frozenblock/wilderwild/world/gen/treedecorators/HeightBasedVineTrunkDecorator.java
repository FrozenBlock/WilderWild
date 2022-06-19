package net.frozenblock.wilderwild.world.gen.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;

public class HeightBasedVineTrunkDecorator extends TreeDecorator {
    public static final Codec<HeightBasedVineTrunkDecorator> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((treeDecorator) -> {
            return treeDecorator.probability;
        }), Codec.intRange(-63, 319).fieldOf("maxHeight").forGetter((treeDecorator) -> {
            return treeDecorator.maxHeight;
        })).apply(instance, HeightBasedVineTrunkDecorator::new);
    });
    private final float probability;
    private final int maxHeight;

    public HeightBasedVineTrunkDecorator(float probability, int maxHeight) {
        this.probability = probability;
        this.maxHeight = maxHeight;
    }

    protected TreeDecoratorType<?> getType() {
        return WildTreeDecorators.HEIGHT_BASED_VINE_TRUNK_DECORATOR;
    }

    public void generate(Generator generator) {
        Random abstractRandom = generator.getRandom();
        if (abstractRandom.nextFloat() <= this.probability) {
            List<BlockPos> list = generator.getLogPositions();
            list.forEach((pos) -> {
                if (pos.getY() <= this.maxHeight) {
                    for (Direction direction : Direction.Type.HORIZONTAL) {
                        if (abstractRandom.nextFloat() <= 0.25F) {
                            BlockPos blockPos = pos.add(direction.getOffsetX(), 0, direction.getOffsetZ());
                            if (generator.isAir(blockPos)) {
                                BooleanProperty dir = direction == Direction.NORTH ? VineBlock.SOUTH : direction == Direction.SOUTH ? VineBlock.NORTH : direction == Direction.WEST ? VineBlock.EAST : VineBlock.WEST;
                                generator.replace(blockPos, Blocks.VINE.getDefaultState().with(dir, true));
                            }
                        }
                    }
                }
            });
        }
    }

}