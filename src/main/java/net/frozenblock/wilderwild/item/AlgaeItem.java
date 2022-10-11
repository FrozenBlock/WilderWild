package net.frozenblock.wilderwild.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public final class AlgaeItem extends BlockItem {

    public AlgaeItem(final Block block, final Properties settings) {
        super(block, settings);
    }

    @Override
    public InteractionResult useOn(final @NotNull UseOnContext context) {
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(final @NotNull Level level,
                                                  final @NotNull Player user,
                                                  final @NotNull InteractionHand hand) {
        BlockHitResult blockHitResult = getPlayerPOVHitResult(level, user,
                ClipContext.Fluid.SOURCE_ONLY);
        BlockHitResult blockHitResult2 = blockHitResult.withPosition(
                blockHitResult.getBlockPos().above());
        InteractionResult actionResult =
                super.useOn(new UseOnContext(user, hand, blockHitResult2));
        return new InteractionResultHolder<>(actionResult,
                user.getItemInHand(hand));
    }
}
