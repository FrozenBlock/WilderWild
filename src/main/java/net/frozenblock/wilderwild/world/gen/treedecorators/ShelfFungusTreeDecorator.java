package net.frozenblock.wilderwild.world.gen.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;

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

    protected TreeDecoratorType<?> getType() {
        return WildTreeDecorators.FUNGUS_TREE_DECORATOR;
    }

    public void generate(TreeDecorator.Generator generator) {
        AbstractRandom abstractRandom = generator.getRandom();
        if (abstractRandom.nextFloat() <= this.probability) {
            List<BlockPos> list = generator.getLogPositions();
            int i = list.get(4).getY();
            list.stream().filter((pos) -> pos.getY() - i <= 8).forEach((pos) -> {
                for (Direction direction : Direction.Type.HORIZONTAL) {
                    if (abstractRandom.nextFloat() <= 0.25F) {
                        BlockPos blockPos = pos.add(direction.getOffsetX(), 0, direction.getOffsetZ());
                        if (generator.isAir(blockPos)) {
                            if (generator.getRandom().nextFloat() < redChance) {
                                generator.replace(blockPos, RegisterBlocks.RED_SHELF_FUNGUS.getDefaultState().with(ShelfFungusBlock.STAGE, abstractRandom.nextInt(3) + 1).with(ShelfFungusBlock.FACE, WallMountLocation.WALL).with(ShelfFungusBlock.FACING, direction));
                            } else {
                                generator.replace(blockPos, RegisterBlocks.BROWN_SHELF_FUNGUS.getDefaultState().with(ShelfFungusBlock.STAGE, abstractRandom.nextInt(3) + 1).with(ShelfFungusBlock.FACE, WallMountLocation.WALL).with(ShelfFungusBlock.FACING, direction));
                            }
                        }
                    }
                }
            });
        }
    }

}