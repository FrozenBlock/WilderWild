/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.entity.enderman;

import net.frozenblock.lib.sound.api.FrozenSoundPackets;
import net.frozenblock.wilderwild.config.EntityConfig;
import net.frozenblock.wilderwild.misc.client.ClientMethodInteractionHandler;
import net.frozenblock.wilderwild.misc.interfaces.WilderEnderman;
import net.frozenblock.wilderwild.misc.mod_compat.FrozenLibIntegration;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderMan.class)
public abstract class EnderManMixin extends Monster implements WilderEnderman {

	@Unique
	private boolean wilderWild$canPlayLoopingSound = true;

	@Shadow
	private int lastStareSound;

	private EnderManMixin(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}

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

	@Inject(method = "playStareSound", at = @At(value = "HEAD"), cancellable = true)
	public void wilderWild$playStareSound(CallbackInfo info) {
		//NOTE: This only runs on the client.
		if (EntityConfig.get().enderMan.movingStareSound) {
			info.cancel();
			if (this.tickCount >= this.lastStareSound + 400) {
				this.lastStareSound = this.tickCount;
				if (!this.isSilent()) {
					ClientMethodInteractionHandler.playClientEnderManSound(EnderMan.class.cast(this));
				}
			}
		}
	}

	@Inject(method = "addAdditionalSaveData", at = @At(value = "TAIL"))
	public void wilderWild$addAdditionalSaveData(CompoundTag compound, CallbackInfo info) {
		compound.putBoolean("canPlayLoopingSound", this.wilderWild$canPlayLoopingSound);
	}

	@Inject(method = "readAdditionalSaveData", at = @At(value = "TAIL"))
	public void wilderWild$readAdditionalSaveData(CompoundTag compound, CallbackInfo info) {
		if (compound.contains("canPlayLoopingSound")) {
			this.wilderWild$canPlayLoopingSound = compound.getBoolean("canPlayLoopingSound");
		}
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
		if (EntityConfig.get().enderMan.angerLoopSound && this.wilderWild$canPlayLoopingSound) {
			this.wilderWild$canPlayLoopingSound = false;
			EnderMan enderMan = EnderMan.class.cast(this);
			FrozenSoundPackets.createMovingRestrictionLoopingSound(
				enderMan.level(),
				enderMan,
				RegisterSounds.ENTITY_ENDERMAN_ANGER_LOOP,
				SoundSource.HOSTILE,
				1F,
				0.9F,
				FrozenLibIntegration.ENDERMAN_ANGER_SOUND_PREDICATE,
				true
			);
		}
	}

}
