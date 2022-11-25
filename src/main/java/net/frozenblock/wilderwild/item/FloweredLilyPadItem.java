package net.frozenblock.wilderwild.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class FloweredLilyPadItem extends BlockItem {
	public FloweredLilyPadItem(Block block, Properties settings) {
		super(block, settings);
	}

	@Override
	public InteractionResult useOn(@NotNull UseOnContext context) {
		return InteractionResult.PASS;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player user, @NotNull InteractionHand hand) {
		BlockHitResult blockHitResult = getPlayerPOVHitResult(level, user, Fluid.SOURCE_ONLY);
		BlockHitResult blockHitResult2 = blockHitResult.withPosition(blockHitResult.getBlockPos().above());
		InteractionResult actionResult = super.useOn(new UseOnContext(user, hand, blockHitResult2));
		return new InteractionResultHolder<>(actionResult, user.getItemInHand(hand));
	}
}
