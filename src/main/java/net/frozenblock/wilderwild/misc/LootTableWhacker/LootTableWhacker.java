package net.frozenblock.wilderwild.misc.LootTableWhacker;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;

import java.util.Objects;

public class LootTableWhacker extends Item {

    public LootTableWhacker(Properties settings) {
        super(settings);
    }

    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        if (stack.hasCustomHoverName()) {
            if (stack.getHoverName().toString().contains(":")) {
                String id = Objects.requireNonNull(context.getPlayer()).getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTag().getString("loot_table");
                ResourceLocation location = new ResourceLocation(id.substring(0, id.lastIndexOf(":") - 1), id.substring(id.lastIndexOf(":" + 1)));
                if (level.getBlockEntity(blockPos) instanceof RandomizableContainerBlockEntity loot) {
                    loot.lootTable = location;
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

}
