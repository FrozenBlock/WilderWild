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

package net.frozenblock.wilderwild.mixin.worldgen.biome;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.frozenblock.lib.worldgen.biome.api.parameters.OverworldBiomeBuilderParameters;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.mod_compat.WWModIntegrations;
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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = OverworldBiomeBuilder.class, priority = 69420)
public final class OverworldBiomeBuilderMixin {

	@Unique
	private WWWorldgenConfig wilderWild$worldgenConfig;

	@Inject(
		method = "addBiomes",
		at = @At("HEAD")
	)
	public void wilderWild$saveDupedParamsA(
		Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, CallbackInfo info,
		@Local(argsOnly = true) LocalRef<Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>>> argConsumer,
		@Share("wilderWild$injectionMap") LocalRef<Map<ResourceKey<Biome>, Pair<List<Climate.ParameterPoint>, AtomicInteger>>> injectionMap
	) {
		final Map<ResourceKey<Biome>, Pair<List<Climate.ParameterPoint>, AtomicInteger>> map = new Object2ObjectOpenHashMap<>();
		final Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> modifiedConsumer = (Pair<Climate.ParameterPoint, ResourceKey<Biome>> pair) -> {
			final Climate.ParameterPoint parameterPoint = pair.getFirst();
			final ResourceKey<Biome> key = pair.getSecond();

			final Pair<List<Climate.ParameterPoint>, AtomicInteger> injectionPair = map.getOrDefault(key, Pair.of(new ArrayList<>(), new AtomicInteger()));
			final List<Climate.ParameterPoint> list = injectionPair.getFirst();
			if (!list.contains(parameterPoint)) {
				list.add(parameterPoint);
				consumer.accept(pair);
			} else {
				injectionPair.getSecond().incrementAndGet();
			}
			map.put(key, injectionPair);
		};
		argConsumer.set(modifiedConsumer);
		injectionMap.set(map);

		if (!WWConstants.UNSTABLE_LOGGING) return;
		map.forEach((key, pair) -> {
			WWConstants.log(pair.getSecond().get() + " duplicate parameters from biome " + key.location() + " have been saved!", true);
		});
	}

	@Inject(
		method = "addBiomes",
		at = @At("TAIL")
	)
	public void wilderWild$saveDupedParamsB(
		Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, CallbackInfo info,
		@Share("wilderWild$injectionMap") LocalRef<Map<ResourceKey<Biome>, Pair<List<Climate.ParameterPoint>, AtomicInteger>>> injectionMap
	) {

		if (!WWConstants.UNSTABLE_LOGGING) return;
		injectionMap.get().forEach((key, pair) -> {
			WWConstants.log(pair.getSecond().get() + " duplicate parameters from biome " + key.location() + " have been saved!", true);
		});
	}

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
		this.wilderWild$worldgenConfig = WWWorldgenConfig.get();
		if (WWModIntegrations.BIOLITH_INTEGRATION.modLoaded()) return;
		if (this.wilderWild$worldgenConfig.biomePlacement.modifyJunglePlacement) {
			MIDDLE_BIOMES_VARIANT[4][3] = Biomes.JUNGLE;
			MIDDLE_BIOMES[4][4] = Biomes.JUNGLE;
		}
	}

	@Inject(method = "addLowSlice", at = @At("TAIL"))
	private void wilderWild$injectLowSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, Climate.Parameter weirdness, CallbackInfo info) {
		if (WWModIntegrations.BIOLITH_INTEGRATION.modLoaded()) return;
		if (this.wilderWild$worldgenConfig.biomePlacement.modifyStonyShorePlacement) {
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
		if (WWModIntegrations.BIOLITH_INTEGRATION.modLoaded()) return;
		if (this.wilderWild$worldgenConfig.biomePlacement.modifyCherryGrovePlacement) {
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
		if (WWModIntegrations.BIOLITH_INTEGRATION.modLoaded()) return;
		if (this.wilderWild$worldgenConfig.biomeGeneration.generateWarmBeach && temperature == 3) {
			info.setReturnValue(WWBiomes.WARM_BEACH);
		}
	}

	@ModifyReturnValue(
		method = {"pickMiddleBiome", "pickPlateauBiome"},
		at = @At("RETURN")
	)
	private ResourceKey<Biome> wilderWild$injectOldGrowthDarkForest(
		ResourceKey<Biome> original,
		@Local(argsOnly = true) Climate.Parameter parameter
	) {
		if (WWModIntegrations.BIOLITH_INTEGRATION.modLoaded()) return original;
		if (this.wilderWild$worldgenConfig.biomeGeneration.generateOldGrowthDarkForest && original.equals(Biomes.DARK_FOREST)) {
			if (parameter.max() >= 0L) return WWBiomes.OLD_GROWTH_DARK_FOREST;
		}
		return original;
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
		if (WWModIntegrations.BIOLITH_INTEGRATION.modLoaded()) {
			operation.call(instance, consumer, temperature, humidity, continentalness, erosion, depth, weirdness, biomeKey);
			return;
		}
		if (biomeKey.equals(Biomes.RIVER) && this.wilderWild$worldgenConfig.biomeGeneration.generateWarmRiver) {
			temperature = WarmRiver.UNFROZEN_NOT_WARM_RANGE;
			operation.call(instance, consumer, this.temperatures[3], WarmRiver.HUMIDITY_TO_TWO, continentalness, erosion, depth, weirdness, WWBiomes.WARM_RIVER);
			Climate.Parameter jungleHumidity = this.wilderWild$worldgenConfig.biomePlacement.modifyJunglePlacement ? WarmRiver.HUMIDITY_TO_THREE : humidity;
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
		if (biomeKey.equals(Biomes.SWAMP) && this.wilderWild$worldgenConfig.biomePlacement.modifySwampPlacement) {
			temperature = WWSharedWorldgen.Swamp.TEMPERATURE;
			humidity = WWSharedWorldgen.Swamp.HUMIDITY;
		} else if (biomeKey.equals(Biomes.MANGROVE_SWAMP) && this.wilderWild$worldgenConfig.biomePlacement.modifyMangroveSwampPlacement) {
			temperature = WWSharedWorldgen.MangroveSwamp.TEMPERATURE;
			humidity = WWSharedWorldgen.MangroveSwamp.HUMIDITY;
		}

		operation.call(instance, consumer, temperature, humidity, continentalness, erosion, depth, weirdness, biomeKey);
	}

	@ModifyExpressionValue(
		method = "maybePickWindsweptSavannaBiome",
		at = @At(
			value = "CONSTANT",
			args = "intValue=1"
		),
		require = 0
	)
	private int wilderWild$fixWindsweptSavannaTemperature(int original) {
		return this.wilderWild$worldgenConfig.biomePlacement.modifyWindsweptSavannaPlacement ? 2 : original;
	}

	@ModifyExpressionValue(
		method = "maybePickWindsweptSavannaBiome",
		at = @At(
			value = "CONSTANT",
			args = "intValue=4"
		),
		require = 0
	)
	private int wilderWild$fixWindsweptSavannaHumidity(int original) {
		return this.wilderWild$worldgenConfig.biomePlacement.modifyWindsweptSavannaPlacement ? 2 : original;
	}

}
