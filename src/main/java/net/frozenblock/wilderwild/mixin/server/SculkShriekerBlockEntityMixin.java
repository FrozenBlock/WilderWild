package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkShriekerBlockEntity.class)
public class SculkShriekerBlockEntityMixin {

	@Unique
    public int wilderWild$bubbles;

    @Inject(at = @At("HEAD"), method = "canRespond", cancellable = true)
    private void canRespond(ServerLevel level, CallbackInfoReturnable<Boolean> info) {
        SculkShriekerBlockEntity entity = SculkShriekerBlockEntity.class.cast(this);
        BlockState blockState = entity.getBlockState();
        if (blockState.getValue(RegisterProperties.SOULS_TAKEN) == 2) {
            WilderSharedConstants.log(Blocks.SCULK_SHRIEKER, entity.getBlockPos(), "All Souls Have Already Been Taken, Cannot Warn", WilderSharedConstants.DEV_LOGGING);
            info.setReturnValue(false);
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "shouldListen", cancellable = true)
    public void shouldListen(ServerLevel level, GameEventListener listener, BlockPos pos, GameEvent event, GameEvent.Context emitter, CallbackInfoReturnable<Boolean> info) {
        SculkShriekerBlockEntity entity = SculkShriekerBlockEntity.class.cast(this);
        if (entity.getBlockState().getValue(RegisterProperties.SOULS_TAKEN) == 2) {
            info.setReturnValue(false);
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "tryShriek", cancellable = true)
    public void shriek(ServerLevel level, @Nullable ServerPlayer player, CallbackInfo info) {
        SculkShriekerBlockEntity shrieker = SculkShriekerBlockEntity.class.cast(this);
        if (shrieker.getBlockState().getValue(RegisterProperties.SOULS_TAKEN) == 2) {
            info.cancel();
        } else {
            if (shrieker.getBlockState().getValue(BlockStateProperties.WATERLOGGED)) {//TODO: fix this. i want it to emit a constant flow of bubbles but it just doesnt
                if (this.wilderWild$bubbles > 0 && level != null) {
                    --this.wilderWild$bubbles;
                    EasyPacket.EasyFloatingSculkBubblePacket.createParticle(level, Vec3.atCenterOf(shrieker.getBlockPos()), Math.random() > 0.7 ? 1 : 0, 20 + WilderSharedConstants.random().nextInt(80), 0.075, level.random.nextIntBetweenInclusive(8, 14));
                }
            }
        }
    }

}
