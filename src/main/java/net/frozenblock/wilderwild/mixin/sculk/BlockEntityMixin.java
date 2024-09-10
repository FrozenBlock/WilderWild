package net.frozenblock.wilderwild.mixin.sculk;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntity.class)
public class BlockEntityMixin {

	@Inject(method = "getUpdatePacket", at = @At("HEAD"), cancellable = true)
	public void wilderWild$getUpdatePacket(CallbackInfoReturnable<Packet<ClientGamePacketListener>> info) {
		if (BlockEntity.class.cast(this) instanceof SculkSensorBlockEntity sensorBlockEntity) {
			info.setReturnValue(ClientboundBlockEntityDataPacket.create(sensorBlockEntity));
		}
	}

	@Inject(method = "getUpdateTag", at = @At("HEAD"), cancellable = true)
	public void wilderWild$getUpdateTag(HolderLookup.Provider lookupProvider, CallbackInfoReturnable<CompoundTag> info) {
		if (BlockEntity.class.cast(this) instanceof SculkSensorBlockEntity sensorBlockEntity) {
			info.setReturnValue(sensorBlockEntity.saveWithoutMetadata(lookupProvider));
		}
	}
}
