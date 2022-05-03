package net.frozenblock.wilderwild.world.gen.treedecorators;

import net.frozenblock.wilderwild.mixin.worldgen.TreeDecoratorTypeInvoker;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class WildTreeDecorators {
    public static TreeDecoratorType<ShelfFungusTreeDecorator> FUNGUS_TREE_DECORATOR = TreeDecoratorTypeInvoker.callRegister("shelf_fungus_tree_decorator", ShelfFungusTreeDecorator.CODEC);

    public static void generateTreeDecorators() {
        //Just to ensure the class is loaded.
    }
}
