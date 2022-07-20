package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> info) {
        Level world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState state = world.getBlockState(blockPos);
        if (state.is(Blocks.LILY_PAD)) {
            WilderWild.log(Blocks.LILY_PAD, blockPos, "Bonemeal", WilderWild.DEV_LOGGING);
            if (!world.isClientSide) {
                world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, blockPos, 0);
                world.setBlockAndUpdate(blockPos, RegisterBlocks.FLOWERED_LILY_PAD.defaultBlockState());
                context.getItemInHand().shrink(1);
            }
            info.setReturnValue(InteractionResult.sidedSuccess(world.isClientSide));
            info.cancel();
        }
        if (state.getBlock() instanceof ShelfFungusBlock) {
            if (state.getValue(RegisterProperties.FUNGUS_STAGE) < 4) {
                WilderWild.log("Shelf Fungus Bonemealed @ " + blockPos + " with FungusStage of " + state.getValue(RegisterProperties.FUNGUS_STAGE), WilderWild.DEV_LOGGING);
                if (!world.isClientSide) {
                    world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, blockPos, 0);
                    world.setBlockAndUpdate(blockPos, state.setValue(RegisterProperties.FUNGUS_STAGE, state.getValue(RegisterProperties.FUNGUS_STAGE) + 1));
                    context.getItemInHand().shrink(1);
                }
                info.setReturnValue(InteractionResult.sidedSuccess(world.isClientSide));
                info.cancel();
            }
        }
        if (state.is(RegisterBlocks.FLOATING_MOSS)) {
            WilderWild.log("Floating Moss Bonemealed @ " + blockPos, WilderWild.DEV_LOGGING);
            if (!world.isClientSide) {
                for (Direction offset : shuffleOffsets(world.getRandom())) {
                    BlockPos pos = blockPos.relative(offset);
                    if (world.getBlockState(pos).isAir() && state.getBlock().canSurvive(state, world, pos)) {
                        world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, blockPos, 0);
                        world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0);
                        world.setBlockAndUpdate(pos, state);
                        context.getItemInHand().shrink(1);
                        break;
                    }
                }
            }
            info.setReturnValue(InteractionResult.sidedSuccess(world.isClientSide));
            info.cancel();
        }
    }

    private static final List<Direction> offsets = new ArrayList<>() {{
        add(Direction.EAST);
        add(Direction.NORTH);
        add(Direction.SOUTH);
        add(Direction.WEST);
    }};

    private static List<Direction> shuffleOffsets(RandomSource random) {
        return Util.toShuffledList(offsets.stream(), random);
    }

}
