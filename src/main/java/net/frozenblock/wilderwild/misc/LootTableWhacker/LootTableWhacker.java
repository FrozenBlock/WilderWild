package net.frozenblock.wilderwild.misc.LootTableWhacker;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;

import java.util.Arrays;
import java.util.List;
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
                String id = Objects.requireNonNull(context.getPlayer()).getItemInHand(InteractionHand.MAIN_HAND).getHoverName().getString();
                List<String> strings = Arrays.stream(id.split(":")).toList();
                ResourceLocation location = new ResourceLocation(strings.get(1), strings.get(2));
                if (level.getBlockEntity(blockPos) instanceof RandomizableContainerBlockEntity loot) {
                    loot.lootTable = location;
                    WilderWild.log(location.toString(), true);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

}
