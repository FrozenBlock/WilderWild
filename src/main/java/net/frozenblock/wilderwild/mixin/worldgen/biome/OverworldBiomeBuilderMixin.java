/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.worldgen.biome;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.frozenblock.lib.worldgen.biome.api.parameters.OverworldBiomeBuilderParameters;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.worldgen.WWSharedWorldgen;
import net.frozenblock.wilderwild.worldgen.biome.WarmRiver;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = OverworldBiomeBuilder.class, priority = 69420)
public final class OverworldBiomeBuilderMixin {

	@Shadow
	@Final
	private ResourceKey<Biome>[][] MIDDLE_BIOMES;
	@Shadow
	@Final
	private ResourceKey<Biome>[][] MIDDLE_BIOMES_VARIANT;
	@Shadow
	@Final
	private Climate.Parameter[] temperatures;

	@Shadow
	private void addSurfaceBiome(
		Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters,
		Climate.Parameter temperature,
		Climate.Parameter humidity,
		Climate.Parameter continentalness,
		Climate.Parameter erosion,
		Climate.Parameter weirdness,
		final float offset,
		ResourceKey<Biome> biome
	) {}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void wilderWild$injectBiomes(CallbackInfo info) {
		if (WWWorldgenConfig.get().biomePlacement.modifyJunglePlacement) {
			MIDDLE_BIOMES_VARIANT[4][3] = Biomes.JUNGLE;
			MIDDLE_BIOMES[4][4] = Biomes.JUNGLE;
		}
	}

	@Inject(method = "addLowSlice", at = @At("TAIL"))
	private void wilderWild$injectLowSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo info) {
		if (WWWorldgenConfig.get().biomePlacement.modifyStonyShorePlacement) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.BEACH)) {
				this.addSurfaceBiome(
					parameters,
					WWSharedWorldgen.StonyShoreTaiga.TEMPERATURE,
					WWSharedWorldgen.StonyShoreTaiga.HUMIDITY,
					WWSharedWorldgen.StonyShoreTaiga.CONTINENTALNESS,
					WWSharedWorldgen.StonyShoreTaiga.EROSION,
					point.weirdness(),
					point.offset(),
					Biomes.STONY_SHORE
				);
			}
		}
	}

	@Inject(method = "addMidSlice", at = @At("TAIL"))
	private void wilderWild$injectMidBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo info) {
		if (WWWorldgenConfig.get().biomePlacement.modifyCherryGrovePlacement) {
			this.addSurfaceBiome(
				parameters,
				WWSharedWorldgen.CherryGrove.TEMPERATURE,
				WWSharedWorldgen.CherryGrove.HUMIDITY,
				WWSharedWorldgen.CherryGrove.CONTINENTALNESS,
				WWSharedWorldgen.CherryGrove.EROSION,
				WWSharedWorldgen.CherryGrove.WEIRDNESS,
				WWSharedWorldgen.CherryGrove.OFFSET,
				Biomes.CHERRY_GROVE
			);
		}
	}

	@Inject(method = "pickBeachBiome", at = @At("HEAD"), cancellable = true)
	private void wilderWild$injectWarmBeach(int temperature, int humidity, CallbackInfoReturnable<ResourceKey<Biome>> info) {
		if (WWWorldgenConfig.get().biomeGeneration.generateWarmBeach && temperature == 3) {
			info.setReturnValue(WWBiomes.WARM_BEACH);
		}
	}

	@WrapOperation(
		method = "addValleys",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/biome/OverworldBiomeBuilder;addSurfaceBiome(Ljava/util/function/Consumer;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;FLnet/minecraft/resources/ResourceKey;)V"
		)
	)
	private void wilderWild$accountForWarmRivers(
		OverworldBiomeBuilder instance,
		Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer,
		Climate.Parameter temperature,
		Climate.Parameter humidity,
		Climate.Parameter continentalness,
		Climate.Parameter erosion,
		Climate.Parameter depth,
		float weirdness,
		ResourceKey<Biome> biomeKey,
		Operation<Void> operation
	) {
		if (biomeKey.equals(Biomes.RIVER) && WWWorldgenConfig.get().biomeGeneration.generateWarmRiver) {
			temperature = WarmRiver.UNFROZEN_NOT_WARM_RANGE;
			operation.call(instance, consumer, this.temperatures[3], WarmRiver.HUMIDITY_TO_TWO, continentalness, erosion, depth, weirdness, WWBiomes.WARM_RIVER);
			Climate.Parameter jungleHumidity = WWWorldgenConfig.get().biomePlacement.modifyJunglePlacement ? WarmRiver.HUMIDITY_TO_THREE : humidity;
			operation.call(instance, consumer, this.temperatures[4], jungleHumidity, continentalness, erosion, depth, weirdness, WWBiomes.WARM_RIVER);
		}
		operation.call(instance, consumer, temperature, humidity, continentalness, erosion, depth, weirdness, biomeKey);
	}

	@WrapOperation(
		method = {"addMidSlice", "addLowSlice", "addValleys"},
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/biome/OverworldBiomeBuilder;addSurfaceBiome(Ljava/util/function/Consumer;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;FLnet/minecraft/resources/ResourceKey;)V"
		)
	)
	public void wilderWild$replaceMidSwamp(
		OverworldBiomeBuilder instance,
		Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer,
		Climate.Parameter temperature,
		Climate.Parameter humidity,
		Climate.Parameter continentalness,
		Climate.Parameter erosion,
		Climate.Parameter depth,
		float weirdness,
		ResourceKey<Biome> biomeKey,
		Operation<Void> operation
	) {
		if (biomeKey.equals(Biomes.SWAMP) && WWWorldgenConfig.get().biomePlacement.modifySwampPlacement) {
			temperature = WWSharedWorldgen.Swamp.TEMPERATURE;
			humidity = WWSharedWorldgen.Swamp.HUMIDITY;
		} else if (biomeKey.equals(Biomes.MANGROVE_SWAMP) && WWWorldgenConfig.get().biomePlacement.modifyMangroveSwampPlacement) {
			temperature = WWSharedWorldgen.MangroveSwamp.TEMPERATURE;
			humidity = WWSharedWorldgen.MangroveSwamp.HUMIDITY;
		}

		operation.call(instance, consumer, temperature, humidity, continentalness, erosion, depth, weirdness, biomeKey);
	}

	@ModifyExpressionValue(method = "maybePickWindsweptSavannaBiome", at = @At(value = "CONSTANT",  args = "intValue=1"), require = 0)
	private int wilderWild$fixWindsweptSavannaTemperature(int original) {
		return WWWorldgenConfig.get().biomePlacement.modifyWindsweptSavannaPlacement ? 2 : original;
	}

	@ModifyExpressionValue(method = "maybePickWindsweptSavannaBiome", at = @At(value = "CONSTANT",  args = "intValue=4"), require = 0)
	private int wilderWild$fixWindsweptSavannaHumidity(int original) {
		return WWWorldgenConfig.get().biomePlacement.modifyWindsweptSavannaPlacement ? 2 : original;
	}

}
