package net.frozenblock.wilderwild.world.gen;

import com.mojang.serialization.Codec;
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
    public static final Codec<ShelfFungusTreeDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(ShelfFungusTreeDecorator::new, (decorator) -> {
        return decorator.probability;
    }).codec();
    private final float probability;

    public ShelfFungusTreeDecorator(float probability) {
        this.probability = probability;
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
                            generator.replace(blockPos, RegisterBlocks.SHELF_FUNGUS.getDefaultState().with(ShelfFungusBlock.STAGE, abstractRandom.nextInt(3)+1).with(ShelfFungusBlock.FACE, WallMountLocation.WALL).with(ShelfFungusBlock.FACING, direction));
                        }
                    }
                }
            });
        }
    }

}