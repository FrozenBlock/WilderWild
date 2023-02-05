package net.frozenblock.wilderwild.mixin.server.general;

import net.minecraft.world.level.block.BigDripleafStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BigDripleafStemBlock.class)
public final class BigDripleafStemBlockMixin extends HorizontalDirectionalBlock {

    private BigDripleafStemBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(at = @At("TAIL"), method = "createBlockStateDefinition")
    public void wilderWild$createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
        builder.add(BlockStateProperties.POWERED);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void wilderWild$bigDripleafStemBlock(BlockBehaviour.Properties setting, CallbackInfo info) {
        BigDripleafStemBlock stem = BigDripleafStemBlock.class.cast(this);
        stem.registerDefaultState(stem.defaultBlockState().setValue(BlockStateProperties.POWERED, false));
    }

}
