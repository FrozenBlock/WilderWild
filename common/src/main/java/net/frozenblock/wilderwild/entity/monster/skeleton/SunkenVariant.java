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

package net.frozenblock.wilderwild.entity.monster.skeleton;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.codec.RegistryCodecs;
import net.minecraft.core.registries.codec.RegistryFixedCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.minecraft.world.level.storage.loot.LootTable;

public record SunkenVariant(
	ModelType model,
	ClientAsset.ResourceTexture texture,
	ClientAsset.ResourceTexture eyePatchLeftHandedTexture,
	ClientAsset.ResourceTexture eyePatchRightHandedTexture,
	Optional<ConversionResult> dryResult,
	Optional<ConversionResult> shearResult,
	SpawnPrioritySelectors spawnConditions
) implements PriorityProvider<SpawnContext, SpawnCondition> {
	public static final Codec<SunkenVariant> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
		ModelType.CODEC.optionalFieldOf("model", ModelType.NORMAL).forGetter(SunkenVariant::model),
		ClientAsset.ResourceTexture.DEFAULT_FIELD_CODEC.forGetter(SunkenVariant::texture),
		ClientAsset.ResourceTexture.CODEC.fieldOf("eye_patch_left_handed_asset_id").forGetter(SunkenVariant::eyePatchLeftHandedTexture),
		ClientAsset.ResourceTexture.CODEC.fieldOf("eye_patch_right_handed_asset_id").forGetter(SunkenVariant::eyePatchRightHandedTexture),
		ConversionResult.CODEC.optionalFieldOf("dry_result").forGetter(SunkenVariant::dryResult),
		ConversionResult.CODEC.optionalFieldOf("shear_result").forGetter(SunkenVariant::shearResult),
		SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").forGetter(SunkenVariant::spawnConditions)
	).apply(instance, SunkenVariant::new));
	public static final Codec<SunkenVariant> NETWORK_CODEC = RecordCodecBuilder.create(instance -> instance.group(
		ModelType.CODEC.optionalFieldOf("model", ModelType.NORMAL).forGetter(SunkenVariant::model),
		ClientAsset.ResourceTexture.DEFAULT_FIELD_CODEC.forGetter(SunkenVariant::texture),
		ClientAsset.ResourceTexture.CODEC.fieldOf("eye_patch_left_handed_asset_id").forGetter(SunkenVariant::eyePatchLeftHandedTexture),
		ClientAsset.ResourceTexture.CODEC.fieldOf("eye_patch_right_handed_asset_id").forGetter(SunkenVariant::eyePatchRightHandedTexture),
		ConversionResult.CODEC.optionalFieldOf("shear_result").forGetter(SunkenVariant::shearResult)
	).apply(instance, SunkenVariant::new));

	public static final Codec<Holder<SunkenVariant>> CODEC = RegistryFixedCodec.create(WilderWildRegistries.SUNKEN_VARIANT);
	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SunkenVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(WilderWildRegistries.SUNKEN_VARIANT);

	private SunkenVariant(
		ModelType model,
		ClientAsset.ResourceTexture texture,
		ClientAsset.ResourceTexture eyePatchLeftHandedTexture,
		ClientAsset.ResourceTexture eyePatchRightHandedTexture,
		Optional<ConversionResult> shearResult
	) {
		this(
			model,
			texture,
			eyePatchLeftHandedTexture,
			eyePatchRightHandedTexture,
			Optional.empty(),
			shearResult,
			SpawnPrioritySelectors.EMPTY
		);
	}

	@Override
	public List<Selector<SpawnContext, SpawnCondition>> selectors() {
		return this.spawnConditions.selectors();
	}

	public record ConversionResult(Holder<SunkenVariant> resultVariant, Optional<ResourceKey<LootTable>> resultLoot) {
		public static final Codec<ConversionResult> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			RegistryCodecs.holder(WilderWildRegistries.SUNKEN_VARIANT).fieldOf("result_variant").forGetter(ConversionResult::resultVariant),
			ResourceKey.codec(Registries.LOOT_TABLE).optionalFieldOf("drop_on_conversion").forGetter(ConversionResult::resultLoot)
		).apply(instance, ConversionResult::new));

		public boolean convertTo(Sunken sunken, Consumer<ResourceKey<LootTable>> ifLootTablePresent) {
			if (sunken.wilderWild$getVariant() == this.resultVariant) return false;

			sunken.wilderWild$setVariant(this.resultVariant);
			return true;
		}
	}

	public enum ModelType implements StringRepresentable {
		NORMAL("normal"),
		CORAL("coral");
		public static final Codec<ModelType> CODEC = StringRepresentable.fromEnum(ModelType::values);
		private final String name;

		ModelType(String name) {
			this.name = name;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}
}
