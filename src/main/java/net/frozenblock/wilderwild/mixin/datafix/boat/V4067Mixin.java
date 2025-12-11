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

package net.frozenblock.wilderwild.mixin.datafix.boat;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.schemas.V4067;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(V4067.class)
public abstract class V4067Mixin {

	@Shadow
	protected abstract void registerChestBoat(Map<String, Supplier<TypeTemplate>> map, String string);

	@Inject(method = "registerEntities", at = @At("RETURN"))
	public void wilderWild$registerBlockEntities(
		Schema schema, CallbackInfoReturnable<Map<String, Supplier<TypeTemplate>>> info,
		@Local Map<String, Supplier<TypeTemplate>> map
	) {
		schema.registerSimple(map, "wilderwild:baobab_boat");
		schema.registerSimple(map, "wilderwild:cypress_boat");
		schema.registerSimple(map, "wilderwild:palm_boat");
		schema.registerSimple(map, "wilderwild:maple_boat");
		schema.registerSimple(map, "wilderwild:willow_boat");

		this.registerChestBoat(map, "wilderwild:boabab_chest_boat");
		this.registerChestBoat(map, "wilderwild:cypress_chest_boat");
		this.registerChestBoat(map, "wilderwild:palm_chest_boat");
		this.registerChestBoat(map, "wilderwild:maple_chest_boat");
		this.registerChestBoat(map, "wilderwild:willow_chest_boat");
	}

}
