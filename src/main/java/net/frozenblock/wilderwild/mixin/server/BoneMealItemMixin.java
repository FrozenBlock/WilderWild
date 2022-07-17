package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    public void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> info) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState state = world.getBlockState(blockPos);
        if (state.isOf(Blocks.LILY_PAD)) {
            WilderWild.log(Blocks.LILY_PAD, blockPos, "Bonemeal", WilderWild.DEV_LOGGING);
            if (!world.isClient) {
                world.syncWorldEvent(1505, blockPos, 0);
                world.setBlockState(blockPos, RegisterBlocks.FLOWERED_LILY_PAD.getDefaultState());
                context.getStack().decrement(1);
            }
            info.setReturnValue(ActionResult.success(world.isClient));
            info.cancel();
        }
        if (state.getBlock() instanceof ShelfFungusBlock) {
            if (state.get(RegisterProperties.FUNGUS_STAGE) < 4) {
                WilderWild.log("Shelf Fungus Bonemealed @ " + blockPos + " with FungusStage of " + state.get(RegisterProperties.FUNGUS_STAGE), WilderWild.DEV_LOGGING);
                if (!world.isClient) {
                    world.syncWorldEvent(1505, blockPos, 0);
                    world.setBlockState(blockPos, state.with(RegisterProperties.FUNGUS_STAGE, state.get(RegisterProperties.FUNGUS_STAGE) + 1));
                    context.getStack().decrement(1);
                }
                info.setReturnValue(ActionResult.success(world.isClient));
                info.cancel();
            }
        }
        if (state.isOf(RegisterBlocks.FLOATING_MOSS)) {
            WilderWild.log("Floating Moss Bonemealed @ " + blockPos, WilderWild.DEV_LOGGING);
            if (!world.isClient) {
                for (Direction offset : shuffleOffsets(world.getRandom())) {
                    BlockPos pos = blockPos.offset(offset);
                    if (world.getBlockState(pos).isAir() && state.getBlock().canPlaceAt(state, world, pos)) {
                        world.syncWorldEvent(1505, blockPos, 0);
                        world.syncWorldEvent(1505, pos, 0);
                        world.setBlockState(pos, state);
                        context.getStack().decrement(1);
                        break;
                    }
                }
            }
            info.setReturnValue(ActionResult.success(world.isClient));
            info.cancel();
        }
    }

    private static final List<Direction> offsets = new ArrayList<>() {{
        add(Direction.EAST);
        add(Direction.NORTH);
        add(Direction.SOUTH);
        add(Direction.WEST);
    }};

    private static List<Direction> shuffleOffsets(Random random) {
        return Util.copyShuffled(offsets.stream(), random);
    }

}
