package net.frozenblock.wilderwild.world.gen;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.CocoaBlock;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.gen.treedecorator.CocoaBeansTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.Iterator;
import java.util.List;

public class ShelfFungusTreeDecorator extends CocoaBeansTreeDecorator {
    public static final Codec<ShelfFungusTreeDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(ShelfFungusTreeDecorator::new, (decorator) -> {
        return decorator.probability;
    }).codec();
    private final float probability;

    public ShelfFungusTreeDecorator(float probability) {
        super(0);
        this.probability = probability;
    }

    protected TreeDecoratorType<?> getType() {
        return TreeDecoratorType.COCOA;
    }

    public void generate(TreeDecorator.Generator generator) {
        AbstractRandom abstractRandom = generator.getRandom();
        if (!(abstractRandom.nextFloat() >= this.probability)) {
            List<BlockPos> list = generator.getLogPositions();
            int i = ((BlockPos)list.get(4)).getY();
            list.stream().filter((pos) -> {
                return pos.getY() - i <= 8;
            }).forEach((pos) -> {
                Iterator var3 = Direction.Type.HORIZONTAL.iterator();

                while(var3.hasNext()) {
                    Direction direction = (Direction)var3.next();
                    if (abstractRandom.nextFloat() <= 0.25F) {
                        Direction direction2 = direction.getOpposite();
                        BlockPos blockPos = pos.add(direction2.getOffsetX(), 0, direction2.getOffsetZ());
                        if (generator.isAir(blockPos)) {
                            generator.replace(blockPos, (BlockState)((BlockState) RegisterBlocks.SHELF_FUNGUS.getDefaultState().with(ShelfFungusBlock.STAGE, abstractRandom.nextInt(3))).with(ShelfFungusBlock.FACE, WallMountLocation.WALL).with(ShelfFungusBlock.FACING, direction.getOpposite()));
                        }
                    }
                }

            });
        }
    }
}
