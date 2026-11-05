/*
 * Copyright 2025-2026 FrozenBlock
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
import net.frozenblock.lib.platform.ModLoader;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import java.util.Objects;

@Mixin(WoodType.class)
public class WoodTypeMixin {

	@Shadow
	@Final
	private String name;

	@ModifyReturnValue(method = "setType", at = @At("RETURN"))
	private BlockSetType wilderWild$paleOakSetType(BlockSetType original) {
		if (ModLoader.isNeoForge()) return original; // TODO FIX NEOFORGE
		if (this.wilderWild$useNewPaleOakSounds()) return WWBlocks.PALE_OAK_SET.get();
		return original;
	}

	@ModifyReturnValue(method = "fenceGateClose", at = @At("RETURN"))
	private SoundEvent wilderWild$paleOakFenceGateClose(SoundEvent original) {
		if (ModLoader.isNeoForge()) return original; // TODO FIX NEOFORGE
		if (this.wilderWild$useNewPaleOakSounds()) return WWSounds.BLOCK_PALE_OAK_WOOD_FENCE_GATE_CLOSE.get();
		return original;
	}

	@ModifyReturnValue(method = "fenceGateOpen", at = @At("RETURN"))
	private SoundEvent wilderWild$paleOakFenceGateOpen(SoundEvent original) {
		if (ModLoader.isNeoForge()) return original; // TODO FIX NEOFORGE
		if (this.wilderWild$useNewPaleOakSounds()) return WWSounds.BLOCK_PALE_OAK_WOOD_FENCE_GATE_OPEN.get();
		return original;
	}

	@Unique
	private boolean wilderWild$useNewPaleOakSounds() {
		return Objects.equals(this.name, "pale_oak") && WWBlockConfig.PALE_OAK_SOUNDS.get();
	}
}
