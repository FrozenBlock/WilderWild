package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.interfaces.SculkShriekerTickInterface;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkShriekerBlockEntity.class)
public class SculkShriekerBlockEntityMixin implements SculkShriekerTickInterface {

	@Shadow
	private VibrationListener listener;

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
            if (shrieker.getBlockState().getValue(BlockStateProperties.WATERLOGGED)) {
                this.wilderWild$bubbles = 50;
            }
        }
    }

	@Inject(at = @At("TAIL"), method = "load")
	public void load(CompoundTag tag, CallbackInfo info) {
		this.wilderWild$bubbles = tag.getInt("wilderwildBubbles");
	}

	@Inject(at = @At("TAIL"), method = "saveAdditional")
	public void saveAdditional(CompoundTag tag, CallbackInfo info) {
		tag.putInt("wilderwildBubbles", this.wilderWild$bubbles);
	}

	@Override
	public void tickServer(Level level, BlockPos pos) {
		if (level != null) {
			this.listener.tick(level);
			if (this.wilderWild$bubbles > 0) {
				--this.wilderWild$bubbles;
				EasyPacket.EasyFloatingSculkBubblePacket.createParticle(level, Vec3.atCenterOf(pos), AdvancedMath.random() > 0.7 ? 1 : 0, 20 + AdvancedMath.random().nextInt(80), 0.075, 1);
			}
		}
	}
}
