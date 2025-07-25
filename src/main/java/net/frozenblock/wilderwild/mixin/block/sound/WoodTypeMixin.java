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
import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WoodType.class)
public class WoodTypeMixin {

	@ModifyReturnValue(method = "fenceGateClose", at = @At("RETURN"))
	private SoundEvent wilderWild$fenceGateClose(SoundEvent original) {
		if (this.wilderWild$useNewPaleOakSounds()) return WWSounds.BLOCK_PALE_OAK_WOOD_FENCE_GATE_CLOSE;
		return original;
	}

	@ModifyReturnValue(method = "fenceGateOpen", at = @At("RETURN"))
	private SoundEvent wilderWild$fenceGateOpen(SoundEvent original) {
		if (this.wilderWild$useNewPaleOakSounds()) return WWSounds.BLOCK_PALE_OAK_WOOD_FENCE_GATE_OPEN;
		return original;
	}

	@Unique
	private boolean wilderWild$useNewPaleOakSounds() {
		return WoodType.class.cast(this) == WoodType.PALE_OAK && WWBlockConfig.get().blockSounds.paleOakSounds;
	}
}
