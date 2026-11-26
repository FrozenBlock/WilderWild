/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.data.wilder_aquatic;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.advancements.predicates.entity.WasGrappledByPlayerHarpoonPredicate;
import net.frozenblock.wilderwild.advancements.trigger.FallAfterHarpoonGrappleFromGroundTrigger;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.predicates.DamageSourcePredicate;
import net.minecraft.advancements.predicates.DistancePredicate;
import net.minecraft.advancements.predicates.MinMaxBounds;
import net.minecraft.advancements.predicates.TagPredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.KilledTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class WAAdvancementProvider extends FabricAdvancementProvider {

	public WAAdvancementProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	public void generateAdvancement(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
		final HolderGetter<Item> items = registries.lookupOrThrow(Registries.ITEM);
		final AdvancementHolder adventure = Advancement.Builder.advancement().build(WWConstants.vanillaId("adventure/root"));
		final AdvancementHolder olBetsy = Advancement.Builder.advancement().build(WWConstants.vanillaId("adventure/ol_betsy"));
		final AdvancementHolder husbandry = Advancement.Builder.advancement().build(WWConstants.vanillaId("husbandry/root"));

		final HolderLookup.RegistryLookup<Block> blocks = registries.lookupOrThrow(Registries.BLOCK);
		final HolderLookup.RegistryLookup<EntityType<?>> entityTypes = registries.lookupOrThrow(Registries.ENTITY_TYPE);
		final HolderLookup.RegistryLookup<DamageType> damageTypes = registries.lookupOrThrow(Registries.DAMAGE_TYPE);

		final AdvancementHolder reelAndKillSunkenWithFallDamage = Advancement.Builder.advancement()
			.parent(olBetsy)
			.display(
				WWItems.HARPOON.get(),
				Component.translatable("wilderwild.advancements.adventure.reel_and_kill_sunken_with_fall_damage.title"),
				Component.translatable("wilderwild.advancements.adventure.reel_and_kill_sunken_with_fall_damage.description"),
				AdvancementType.CHALLENGE,
				true,
				true,
				false
			)
			.rewards(AdvancementRewards.Builder.experience(40))
			.addCriterion(
				"reel_and_kill_sunken_with_fall_damage",
				KilledTrigger.TriggerInstance.playerKilledEntity(
					EntityPredicate.Builder.entity()
						.of(entityTypes, WWEntityTypes.SUNKEN.get())
						.put(WasGrappledByPlayerHarpoonPredicate.CODEC, WasGrappledByPlayerHarpoonPredicate.INSTANCE),
					DamageSourcePredicate.Builder.damageType()
						.tag(TagPredicate.is(damageTypes, DamageTypeTags.IS_FALL))
				)
			)
			.save(writer, WWConstants.id("adventure/reel_and_kill_sunken_with_fall_damage"));

		Advancement.Builder.advancement()
			.parent(reelAndKillSunkenWithFallDamage)
			.display(
				WWItems.HARPOON.get(),
				Component.translatable("wilderwild.advancements.adventure.makeshift_pirate.title"),
				Component.translatable("wilderwild.advancements.adventure.makeshift_pirate.description"),
				AdvancementType.TASK,
				true,
				true,
				false
			)
			.addCriterion(
				"makeshift_pirate",
				FallAfterHarpoonGrappleFromGroundTrigger.TriggerInstance.fallAfterHarpoonGrappleFromGround(
					DistancePredicate.vertical(MinMaxBounds.Doubles.atLeast(5D))
				)
			)
			.save(writer, WWConstants.id("adventure/makeshift_pirate"));
	}
}
