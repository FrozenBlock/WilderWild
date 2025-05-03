/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.mixin.entity.enderman;

import net.frozenblock.lib.sound.impl.networking.FrozenLibSoundPackets;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.impl.WilderEnderman;
import net.frozenblock.wilderwild.mod_compat.FrozenLibIntegration;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.monster.EnderMan;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderMan.class)
public abstract class EnderManMixin implements WilderEnderman {

	@Unique
	private boolean wilderWild$canPlayLoopingSound = true;

	@Unique
	@Override
	public void wilderWild$setCanPlayLoopingSound(boolean bl) {
		this.wilderWild$canPlayLoopingSound = bl;
	}

	@Unique
	@Override
	public boolean wilderWild$getCanPlayLoopingSound() {
		return this.wilderWild$canPlayLoopingSound;
	}

	@Inject(method = "addAdditionalSaveData", at = @At(value = "TAIL"))
	public void wilderWild$addAdditionalSaveData(CompoundTag compound, CallbackInfo info) {
		compound.putBoolean("canPlayLoopingSound", this.wilderWild$canPlayLoopingSound);
	}

	@Inject(method = "readAdditionalSaveData", at = @At(value = "TAIL"))
	public void wilderWild$readAdditionalSaveData(CompoundTag compound, CallbackInfo info) {
		if (compound.contains("canPlayLoopingSound")) this.wilderWild$canPlayLoopingSound = compound.getBoolean("canPlayLoopingSound");
	}

	@Inject(method = "onSyncedDataUpdated", at = @At("TAIL"))
	public void wilderWild$onSyncedDataUpdated(EntityDataAccessor<?> key, CallbackInfo info) {
		if (this.isCreepy() || this.hasBeenStaredAt()) {
			this.wilderWild$createAngerLoop();
		} else {
			this.wilderWild$canPlayLoopingSound = true;
		}
	}

	@Shadow
	public abstract boolean isCreepy();

	@Shadow
	public abstract boolean hasBeenStaredAt();

	@Unique
	@Override
	public void wilderWild$createAngerLoop() {
		if (!WWEntityConfig.get().enderMan.angerLoopSound || !this.wilderWild$canPlayLoopingSound) return;
		this.wilderWild$canPlayLoopingSound = false;
		EnderMan enderMan = EnderMan.class.cast(this);
		FrozenLibSoundPackets.createAndSendMovingRestrictionLoopingSound(
			enderMan.level(),
			enderMan,
			BuiltInRegistries.SOUND_EVENT.getHolder(WWSounds.ENTITY_ENDERMAN_ANGER_LOOP.getLocation()).orElseThrow(),
			SoundSource.HOSTILE,
			1F,
			0.9F,
			FrozenLibIntegration.ENDERMAN_ANGER_SOUND_PREDICATE,
			true
		);
	}

}
