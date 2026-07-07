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

package net.frozenblock.wilderwild.mixin.worldgen.biome.dappled_forest;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.util.Pair;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.data.worldgen.biome.MapleForest;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;
import java.util.function.Consumer;

@Mixin(value = OverworldBiomeBuilder.class, priority = 69420)
public final class OverworldBiomeBuilderMidSliceMixin {

	@Shadow
	@Final
	private Climate.Parameter[] erosions;

	@WrapOperation(
		method = "addMidSlice",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/biome/OverworldBiomeBuilder;addSurfaceBiome(Ljava/util/function/Consumer;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;FLnet/minecraft/resources/ResourceKey;)V",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/world/level/biome/OverworldBiomeBuilder;coastContinentalness:Lnet/minecraft/world/level/biome/Climate$Parameter;",
				ordinal = 2,
				opcode = Opcodes.GETFIELD
			)
		)
	)
	public void wilderWild$midCoastToNearInland(
		OverworldBiomeBuilder instance,
		Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomes,
		Climate.Parameter temperature,
		Climate.Parameter humidity,
		Climate.Parameter continentalness,
		Climate.Parameter erosion,
		Climate.Parameter weirdness,
		float offset,
		ResourceKey<Biome> second,
		Operation<Void> operation
	) {
		// Disabled for balance
		if (false && second.equals(Biomes.DAPPLED_FOREST) && WWWorldgenConfig.MAPLE_FOREST_GENERATION.get()) {
			WWConstants.log("Replacing Dappled Forest Mid: Coast -> Near Inland", WWConstants.UNSTABLE_LOGGING);
			operation.call(instance, biomes, MapleForest.TEMPERATURE, humidity, continentalness, this.erosions[3], weirdness, offset, WWBiomes.MAPLE_FOREST);
			operation.call(instance, biomes, MapleForest.DAPPLED_TEMPERATURE, humidity, continentalness, this.erosions[3], weirdness, offset, second);
			operation.call(instance, biomes, temperature, humidity, continentalness, this.erosions[2], weirdness, offset, second);
			return;
		}

		operation.call(instance, biomes, temperature, humidity, continentalness, erosion, weirdness, offset, second);
	}

	@WrapOperation(
		method = "addMidSlice",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/biome/OverworldBiomeBuilder;addSurfaceBiome(Ljava/util/function/Consumer;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;FLnet/minecraft/resources/ResourceKey;)V",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/world/level/biome/OverworldBiomeBuilder;midInlandContinentalness:Lnet/minecraft/world/level/biome/Climate$Parameter;",
				ordinal = 2,
				opcode = Opcodes.GETFIELD
			)
		)
	)
	public void wilderWild$midMidInlandToFarInland(
		OverworldBiomeBuilder instance,
		Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomes,
		Climate.Parameter temperature,
		Climate.Parameter humidity,
		Climate.Parameter continentalness,
		Climate.Parameter erosion,
		Climate.Parameter weirdness,
		float offset,
		ResourceKey<Biome> second,
		Operation<Void> operation
	) {
		// Disabled for balance
		if (false && second.equals(Biomes.DAPPLED_FOREST) && WWWorldgenConfig.MAPLE_FOREST_GENERATION.get()) {
			WWConstants.log("Replacing Dappled Forest Mid: Mid Inland -> Far Inland", WWConstants.UNSTABLE_LOGGING);
			operation.call(instance, biomes, MapleForest.TEMPERATURE, humidity, continentalness, erosion, weirdness, offset, WWBiomes.MAPLE_FOREST);
			operation.call(instance, biomes, MapleForest.DAPPLED_TEMPERATURE, humidity, continentalness, erosion, weirdness, offset, second);
			return;
		}

		operation.call(instance, biomes, temperature, humidity, continentalness, erosion, weirdness, offset, second);
	}

	@WrapOperation(
		method = "addMidSlice",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/biome/OverworldBiomeBuilder;addSurfaceBiome(Ljava/util/function/Consumer;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;FLnet/minecraft/resources/ResourceKey;)V",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/world/level/biome/OverworldBiomeBuilder;nearInlandContinentalness:Lnet/minecraft/world/level/biome/Climate$Parameter;",
				ordinal = 6,
				opcode = Opcodes.GETFIELD
			)
		)
	)
	public void wilderWild$midNearInlandToFarInland(
		OverworldBiomeBuilder instance,
		Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomes,
		Climate.Parameter temperature,
		Climate.Parameter humidity,
		Climate.Parameter continentalness,
		Climate.Parameter erosion,
		Climate.Parameter weirdness,
		float offset,
		ResourceKey<Biome> second,
		Operation<Void> operation
	) {
		if (second.equals(Biomes.DAPPLED_FOREST) && WWWorldgenConfig.MAPLE_FOREST_GENERATION.get()) {
			WWConstants.log("Replacing Dappled Forest Mid: Near Inland -> Far Inland", WWConstants.UNSTABLE_LOGGING);
			operation.call(instance, biomes, MapleForest.TEMPERATURE, humidity, continentalness, erosion, weirdness, offset, WWBiomes.MAPLE_FOREST);
			operation.call(instance, biomes, MapleForest.DAPPLED_TEMPERATURE, humidity, continentalness, erosion, weirdness, offset, second);
			return;
		}

		operation.call(instance, biomes, temperature, humidity, continentalness, erosion, weirdness, offset, second);
	}

	@WrapOperation(
		method = "addMidSlice",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/biome/OverworldBiomeBuilder;addSurfaceBiome(Ljava/util/function/Consumer;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;Lnet/minecraft/world/level/biome/Climate$Parameter;FLnet/minecraft/resources/ResourceKey;)V",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/world/level/biome/OverworldBiomeBuilder;nearInlandContinentalness:Lnet/minecraft/world/level/biome/Climate$Parameter;",
				ordinal = 7,
				opcode = Opcodes.GETFIELD
			)
		)
	)
	public void wilderWild$midNearInland(
		OverworldBiomeBuilder instance,
		Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomes,
		Climate.Parameter temperature,
		Climate.Parameter humidity,
		Climate.Parameter continentalness,
		Climate.Parameter erosion,
		Climate.Parameter weirdness,
		float offset,
		ResourceKey<Biome> second,
		Operation<Void> operation
	) {
		if (second.equals(Biomes.DAPPLED_FOREST) && WWWorldgenConfig.MAPLE_FOREST_GENERATION.get()) {
			WWConstants.log("Replacing Dappled Forest Mid: Near Inland", WWConstants.UNSTABLE_LOGGING);
			operation.call(instance, biomes, MapleForest.TEMPERATURE, humidity, continentalness, erosion, weirdness, offset, WWBiomes.MAPLE_FOREST);
			operation.call(instance, biomes, MapleForest.DAPPLED_TEMPERATURE, humidity, continentalness, erosion, weirdness, offset, second);
			return;
		}

		operation.call(instance, biomes, temperature, humidity, continentalness, erosion, weirdness, offset, second);
	}
}
