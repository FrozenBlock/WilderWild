package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    public void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> info) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState state = world.getBlockState(blockPos);
        if (state.isOf(Blocks.LILY_PAD)) {
            WilderWild.log(Blocks.LILY_PAD, blockPos, "Bonemeal", WilderWild.UNSTABLE_LOGGING);
            if (!world.isClient) {
                world.syncWorldEvent(1505, blockPos, 0);
                world.setBlockState(blockPos, RegisterBlocks.FLOWERED_LILY_PAD.getDefaultState());
                context.getStack().decrement(1);
            }
            info.setReturnValue(ActionResult.success(world.isClient));
            info.cancel();
        }
        if (state.getBlock() instanceof ShelfFungusBlock) {
            if (state.get(RegisterProperties.FUNUGS_STAGE) < 4) {
                WilderWild.log("Shelf Fungus Bonemealed @ " + blockPos + " with FungusStage of " + state.get(RegisterProperties.FUNUGS_STAGE), WilderWild.UNSTABLE_LOGGING);
                if (!world.isClient) {
                    world.syncWorldEvent(1505, blockPos, 0);
                    world.setBlockState(blockPos, state.with(RegisterProperties.FUNUGS_STAGE, state.get(RegisterProperties.FUNUGS_STAGE) + 1));
                    context.getStack().decrement(1);
                }
                info.setReturnValue(ActionResult.success(world.isClient));
                info.cancel();
            }
        }
    }

}
