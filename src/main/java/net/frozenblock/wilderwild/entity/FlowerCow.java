/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.entity;

import net.frozenblock.wilderwild.entity.variant.moobloom.MoobloomVariant;
import net.frozenblock.wilderwild.entity.variant.moobloom.MoobloomVariants;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class FlowerCow extends Cow implements Shearable {
	private static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(FlowerCow.class, EntityDataSerializers.STRING);

	private Optional<MoobloomVariant> moobloomVariant = Optional.empty();

	public FlowerCow(EntityType<? extends FlowerCow> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public float getWalkTargetValue(BlockPos blockPos, @NotNull LevelReader levelReader) {
		return levelReader.getBlockState(blockPos).is(BlockTags.FLOWERS) ? 10F : levelReader.getPathfindingCostFromLightLevels(blockPos);
	}

	@Override
	public @NotNull SpawnGroupData finalizeSpawn(
		@NotNull ServerLevelAccessor level, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData
	) {
		Holder<Biome> holder = level.getBiome(this.blockPosition());
		if (spawnGroupData instanceof FlowerCowSpawnGroupData flowerCowSpawnGroupData) {
			this.setVariant(flowerCowSpawnGroupData.type.value());
		} else {
			Holder<MoobloomVariant> moobloomVariantHolder = MoobloomVariants.getSpawnVariant(this.registryAccess(), holder, level.getRandom());
			spawnGroupData = new FlowerCowSpawnGroupData(moobloomVariantHolder);
			this.setVariant(moobloomVariantHolder.value());
		}

		return super.finalizeSpawn(level, difficultyInstance, mobSpawnType, spawnGroupData);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(VARIANT, MoobloomVariants.DEFAULT.location().toString());
	}

	@Override
	public @NotNull InteractionResult mobInteract(@NotNull Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.is(Items.SHEARS) && this.readyForShearing()) {
			this.shear(SoundSource.PLAYERS);
			this.gameEvent(GameEvent.SHEAR, player);
			if (!this.level().isClientSide) {
				itemStack.hurtAndBreak(1, player, getSlotForHand(interactionHand));
			}

			return InteractionResult.sidedSuccess(this.level().isClientSide);
		} else {
			return super.mobInteract(player, interactionHand);
		}
	}

	@Override
	public void shear(SoundSource soundSource) {
		this.level().playSound(null, this, SoundEvents.MOOSHROOM_SHEAR, soundSource, 1F, 1F);
		if (this.level() instanceof ServerLevel serverLevel) {
			Cow cow = EntityType.COW.create(this.level());
			if (cow != null) {
				serverLevel.sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(0.5D), this.getZ(), 1, 0D, 0D, 0D, 0D);
				this.discard();
				cow.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
				cow.setHealth(this.getHealth());
				cow.yBodyRot = this.yBodyRot;
				if (this.hasCustomName()) {
					cow.setCustomName(this.getCustomName());
					cow.setCustomNameVisible(this.isCustomNameVisible());
				}

				if (this.isPersistenceRequired()) {
					cow.setPersistenceRequired();
				}

				cow.setInvulnerable(this.isInvulnerable());
				this.level().addFreshEntity(cow);

				for (int i = 0; i < 5; i++) {
					this.level()
						.addFreshEntity(
							new ItemEntity(
								this.level(),
								this.getX(),
								this.getY(1D),
								this.getZ(),
								new ItemStack(this.getVariantByLocation().getFlowerBlockState().getBlock())
							)
						);
				}
			}
		}
	}

	@Override
	public boolean readyForShearing() {
		return this.isAlive() && !this.isBaby();
	}

	@Override
	public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
		if (VARIANT.equals(key)) {
			this.moobloomVariant = Optional.of(this.getVariantByLocation());
		}
		super.onSyncedDataUpdated(key);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putString("variant", this.getVariantLocation().toString());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		Optional.ofNullable(ResourceLocation.tryParse(compoundTag.getString("variant")))
			.map(resourceLocation -> ResourceKey.create(WilderWildRegistries.MOOBLOOM_VARIANT, resourceLocation))
			.flatMap(resourceKey -> this.registryAccess().registryOrThrow(WilderWildRegistries.MOOBLOOM_VARIANT).getHolder(resourceKey))
			.ifPresent(reference -> this.setVariant(reference.value()));
	}

	public ResourceLocation getVariantLocation() {
		return ResourceLocation.parse(this.entityData.get(VARIANT));
	}

	public MoobloomVariant getVariantByLocation() {
		return this.registryAccess().registryOrThrow(WilderWildRegistries.MOOBLOOM_VARIANT).get(this.getVariantLocation());
	}

	public Holder<MoobloomVariant> getVariantAsHolder() {
		return this.registryAccess().registryOrThrow(WilderWildRegistries.MOOBLOOM_VARIANT).getHolder(this.getVariantLocation()).orElseThrow();
	}

	public MoobloomVariant getVariantForRendering() {
		return this.moobloomVariant.orElse(this.registryAccess().registryOrThrow(WilderWildRegistries.MOOBLOOM_VARIANT).get(MoobloomVariants.DEFAULT));
	}

	public void setVariant(@NotNull MoobloomVariant variant) {
		this.entityData.set(VARIANT, Objects.requireNonNull(this.registryAccess().registryOrThrow(WilderWildRegistries.MOOBLOOM_VARIANT).getKey(variant)).toString());
	}

	public void setVariant(@NotNull ResourceLocation variant) {
		this.entityData.set(VARIANT, variant.toString());
	}

	@Nullable
	public FlowerCow getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		FlowerCow flowerCow = WWEntityTypes.MOOBLOOM.create(serverLevel);
		if (flowerCow != null && ageableMob instanceof FlowerCow otherFlowerCow) {
			flowerCow.setVariant(this.getOffspringType(otherFlowerCow));
		}

		return flowerCow;
	}

	private MoobloomVariant getOffspringType(@NotNull FlowerCow flowerCow) {
		MoobloomVariant flowerType = this.getVariantByLocation();
		MoobloomVariant otherFlowerType = flowerCow.getVariantByLocation();
		if (flowerType == otherFlowerType) {
			return flowerType;
		} else {
			return this.getOffspringVariant(flowerCow);
		}
	}

	private @NotNull MoobloomVariant getOffspringVariant(@NotNull FlowerCow otherFlowerCow) {
		MoobloomVariant moobloomVariant = this.getVariantByLocation();
		MoobloomVariant otherMoobloomVariant = otherFlowerCow.getVariantByLocation();

		BlockState flowerBlock = moobloomVariant.getFlowerBlockState();
		BlockState otherFlowerBlock = otherMoobloomVariant.getFlowerBlockState();

		Optional<DyeColor> dyeColor = getDyeColorFromFlower(this.level(), flowerBlock.getBlock());
		Optional<DyeColor> otherDyeColor = getDyeColorFromFlower(this.level(), otherFlowerBlock.getBlock());

		if (dyeColor.isPresent() && otherDyeColor.isPresent()) {
			DyeColor outputDyeColor = getCombinedDyeColor(this.level(), dyeColor.get(), otherDyeColor.get());
			Stream<MoobloomVariant> variantStream = this.level().registryAccess().registryOrThrow(WilderWildRegistries.MOOBLOOM_VARIANT).stream();
			for (MoobloomVariant registeredVariant : Util.toShuffledList(variantStream, this.random)) {
				Block variantFlower = registeredVariant.getFlowerBlockState().getBlock();
				Optional<DyeColor> flowerDyeColor = getDyeColorFromFlower(this.level(), variantFlower);
				if (flowerDyeColor.isPresent() && flowerDyeColor.get().equals(outputDyeColor)) {
					return registeredVariant;
				}
			}
		}

		return this.random.nextBoolean() ? moobloomVariant : otherMoobloomVariant;
	}

	private static @NotNull Optional<DyeColor> getDyeColorFromFlower(@NotNull Level level, Block flowerBlock) {
		CraftingInput craftingInput = makeCraftInputForFlower(flowerBlock);
		return level.getRecipeManager()
			.getRecipeFor(RecipeType.CRAFTING, craftingInput, level)
			.map(recipeHolder -> recipeHolder.value().assemble(craftingInput, level.registryAccess()))
			.map(ItemStack::getItem)
			.filter(DyeItem.class::isInstance)
			.map(DyeItem.class::cast)
			.map(DyeItem::getDyeColor);
	}

	private static DyeColor getCombinedDyeColor(@NotNull Level level, DyeColor dyeColor, DyeColor otherDyeColor) {
		CraftingInput craftingInput = makeCraftInput(dyeColor, otherDyeColor);
		return level.getRecipeManager()
			.getRecipeFor(RecipeType.CRAFTING, craftingInput, level)
			.map(recipeHolder -> recipeHolder.value().assemble(craftingInput, level.registryAccess()))
			.map(ItemStack::getItem)
			.filter(DyeItem.class::isInstance)
			.map(DyeItem.class::cast)
			.map(DyeItem::getDyeColor)
			.orElseGet(() -> level.random.nextBoolean() ? dyeColor : otherDyeColor);
	}

	private static @NotNull CraftingInput makeCraftInputForFlower(Block flowerBlock) {
		return CraftingInput.of(1, 1, List.of(new ItemStack(flowerBlock)));
	}

	private static @NotNull CraftingInput makeCraftInput(DyeColor dyeColor, DyeColor dyeColor2) {
		return CraftingInput.of(2, 1, List.of(new ItemStack(DyeItem.byColor(dyeColor)), new ItemStack(DyeItem.byColor(dyeColor2))));
	}

	public static class FlowerCowSpawnGroupData extends AgeableMob.AgeableMobGroupData {
		public final Holder<MoobloomVariant> type;

		public FlowerCowSpawnGroupData(Holder<MoobloomVariant> holder) {
			super(true);
			this.type = holder;
		}
	}
}
