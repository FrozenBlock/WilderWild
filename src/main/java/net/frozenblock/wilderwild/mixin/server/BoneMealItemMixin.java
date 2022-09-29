package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.GloryOfTheSnowBlock;
import net.frozenblock.wilderwild.misc.FlowerColors;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.List;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> info) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState state = level.getBlockState(blockPos);
        if (state.getBlock() instanceof GloryOfTheSnowBlock glory) {
            if (state.getValue(RegisterProperties.FLOWER_COLOR) == FlowerColors.NONE) {
                WilderWild.log("Glory Of The Snow Bonemealed @ " + blockPos, WilderWild.DEV_LOGGING);
                if (!level.isClientSide) {
                    level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, blockPos, 0);
                    level.setBlockAndUpdate(blockPos, state.setValue(RegisterProperties.FLOWER_COLOR, glory.COLOR_LIST.get(WilderWild.random().nextInt(glory.COLOR_LIST.size()))));
                    context.getItemInHand().shrink(1);
                }
                info.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide));
            }
        }
    }

    @Unique
    private static List<Direction> shuffleOffsets(RandomSource random) {
        return Util.toShuffledList(Arrays.stream(Direction.values()).filter(direction -> direction.getAxis().isHorizontal()), random);
    }

}
