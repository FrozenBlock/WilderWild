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

package net.frozenblock.wilderwild.mixin.block.sound;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockSetType.class)
public class BlockSetTypeMixin {

	@ModifyReturnValue(method = "doorClose", at = @At("RETURN"))
	private SoundEvent wilderWild$doorClose(SoundEvent original) {
		if (this.wilderWild$useNewPaleOakSounds()) return WWSounds.BLOCK_PALE_OAK_WOOD_DOOR_CLOSE;
		return original;
	}

	@ModifyReturnValue(method = "doorOpen", at = @At("RETURN"))
	private SoundEvent wilderWild$doorOpen(SoundEvent original) {
		if (this.wilderWild$useNewPaleOakSounds()) return WWSounds.BLOCK_PALE_OAK_WOOD_DOOR_OPEN;
		return original;
	}

	@ModifyReturnValue(method = "trapdoorClose", at = @At("RETURN"))
	private SoundEvent wilderWild$trapdoorClose(SoundEvent original) {
		if (this.wilderWild$useNewPaleOakSounds()) return WWSounds.BLOCK_PALE_OAK_WOOD_TRAPDOOR_CLOSE;
		return original;
	}

	@ModifyReturnValue(method = "trapdoorOpen", at = @At("RETURN"))
	private SoundEvent wilderWild$trapdoorOpen(SoundEvent original) {
		if (this.wilderWild$useNewPaleOakSounds()) return WWSounds.BLOCK_PALE_OAK_WOOD_TRAPDOOR_OPEN;
		return original;
	}

	@ModifyReturnValue(method = "pressurePlateClickOff", at = @At("RETURN"))
	private SoundEvent wilderWild$pressurePlateClickOff(SoundEvent original) {
		if (this.wilderWild$useNewPaleOakSounds()) return WWSounds.BLOCK_PALE_OAK_WOOD_PRESSURE_PLATE_CLICK_OFF;
		return original;
	}

	@ModifyReturnValue(method = "pressurePlateClickOn", at = @At("RETURN"))
	private SoundEvent wilderWild$pressurePlateClickOn(SoundEvent original) {
		if (this.wilderWild$useNewPaleOakSounds()) return WWSounds.BLOCK_PALE_OAK_WOOD_PRESSURE_PLATE_CLICK_ON;
		return original;
	}

	@ModifyReturnValue(method = "buttonClickOff", at = @At("RETURN"))
	private SoundEvent wilderWild$buttonClickOff(SoundEvent original) {
		if (this.wilderWild$useNewPaleOakSounds()) return WWSounds.BLOCK_PALE_OAK_BUTTON_CLICK_OFF;
		return original;
	}

	@ModifyReturnValue(method = "buttonClickOn", at = @At("RETURN"))
	private SoundEvent wilderWild$buttonClickOn(SoundEvent original) {
		if (this.wilderWild$useNewPaleOakSounds()) return WWSounds.BLOCK_PALE_OAK_BUTTON_CLICK_ON;
		return original;
	}

	@Unique
	private boolean wilderWild$useNewPaleOakSounds() {
		return BlockSetType.class.cast(this) == BlockSetType.PALE_OAK && WWBlockConfig.get().blockSounds.paleOakSounds;
	}
}
