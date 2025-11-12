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

package net.frozenblock.wilderwild.entity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.variant.moobloom.MoobloomVariant;
import net.frozenblock.wilderwild.entity.variant.moobloom.MoobloomVariants;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.AbstractCow;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.VariantUtils;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FlowerCow extends AbstractCow implements Shearable {
	public static final int MAX_FLOWERS = 4;
	private static final byte GROW_FLOWER_EVENT_ID = 61;
	private static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(FlowerCow.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<Integer> FLOWERS_LEFT = SynchedEntityData.defineId(FlowerCow.class, EntityDataSerializers.INT);

	private Optional<MoobloomVariant> moobloomVariant = Optional.empty();

	public FlowerCow(EntityType<? extends FlowerCow> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public float getWalkTargetValue(BlockPos blockPos, @NotNull LevelReader levelReader) {
		BlockState state = levelReader.getBlockState(blockPos);
		if (this.getVariant().getFlowerBlockState().is(state.getBlock())) return 20F;
		return super.getWalkTargetValue(blockPos, levelReader);
	}

	public static boolean checkFlowerCowSpawnRules(
		@NotNull EntityType<FlowerCow> type, @NotNull LevelAccessor level, EntitySpawnReason reason, @NotNull BlockPos pos, @NotNull RandomSource random
	) {
		if (!EntitySpawnReason.isSpawner(reason) && !WWEntityConfig.get().moobloom.spawnMooblooms) return false;
		return checkAnimalSpawnRules(type, level, reason, pos, random);
	}

	@Override
	public @NotNull SpawnGroupData finalizeSpawn(
		@NotNull ServerLevelAccessor level, DifficultyInstance difficultyInstance, EntitySpawnReason reason, @Nullable SpawnGroupData spawnGroupData
	) {
		if (spawnGroupData instanceof FlowerCowSpawnGroupData flowerCowSpawnGroupData) {
			this.setVariant(flowerCowSpawnGroupData.type.value());
		} else {
			Optional<Holder.Reference<MoobloomVariant>> optionalMoobloomVariantReference = MoobloomVariants.selectVariantToSpawn(
				level.getRandom(), this.registryAccess(), SpawnContext.create(level, this.blockPosition())
			);
			if (optionalMoobloomVariantReference.isPresent()) {
				spawnGroupData = new FlowerCowSpawnGroupData(optionalMoobloomVariantReference.get());
				this.setVariant(optionalMoobloomVariantReference.get().value());
			}
		}

		return super.finalizeSpawn(level, difficultyInstance, reason, spawnGroupData);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(VARIANT, MoobloomVariants.DEFAULT.identifier().toString());
		builder.define(FLOWERS_LEFT, MAX_FLOWERS);
	}

	@Override
	public @NotNull InteractionResult mobInteract(@NotNull Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (!this.isBaby() && this.isFood(itemStack) && !this.hasMaxFlowersLeft()) {
			if (!this.level().isClientSide()) {
				this.usePlayerItem(player, interactionHand, itemStack);
				this.incrementFlowersLeft();
				this.level().broadcastEntityEvent(this, GROW_FLOWER_EVENT_ID);
				return InteractionResult.SUCCESS_SERVER;
			}
			return InteractionResult.CONSUME;
		} else if (itemStack.is(Items.SHEARS)) {
			if (this.level() instanceof ServerLevel serverLevel && this.readyForShearing()) {
				this.shear(serverLevel, SoundSource.PLAYERS, itemStack);
				this.gameEvent(GameEvent.SHEAR, player);
				itemStack.hurtAndBreak(1, player, interactionHand);
				return InteractionResult.SUCCESS_SERVER;
			}
			return InteractionResult.CONSUME;
		}
		return super.mobInteract(player, interactionHand);
	}

	@Override
	public void shear(ServerLevel serverLevel, SoundSource soundSource, ItemStack itemStack) {
		this.level().playSound(null, this, WWSounds.ENTITY_MOOBLOOM_SHEAR, soundSource, 1F, 1F);
		BlockState flowerState = this.getVariant().getFlowerBlockState();
		spawnShearParticles(serverLevel, this, flowerState);
		this.level().addFreshEntity(
			new ItemEntity(
				this.level(),
				this.getX(),
				this.getY(1D),
				this.getZ(),
				new ItemStack(flowerState.getBlock())
			)
		);
		this.decrementFlowersLeft();
	}

	private static void spawnShearParticles(@NotNull ServerLevel serverLevel, @NotNull FlowerCow flowerCow, BlockState flowerBlockState) {
		serverLevel.sendParticles(
			new BlockParticleOption(ParticleTypes.BLOCK, flowerBlockState),
			flowerCow.getX(),
			flowerCow.getY(0.6666666666666666D),
			flowerCow.getZ(),
			10,
			flowerCow.getBbWidth() / 4D,
			flowerCow.getBbHeight() / 4D,
			flowerCow.getBbWidth() / 4D,
			0.05D
		);
	}

	@Override
	public void handleEntityEvent(byte b) {
		if (b == GROW_FLOWER_EVENT_ID) {
			for (int i = 0; i < 7; i++) {
				double d = this.random.nextGaussian() * 0.02D;
				double e = this.random.nextGaussian() * 0.02D;
				double f = this.random.nextGaussian() * 0.02D;
				this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1D), this.getRandomY() + 0.5D, this.getRandomZ(1D), d, e, f);
			}
		} else {
			super.handleEntityEvent(b);
		}
	}

	@Override
	public boolean readyForShearing() {
		return this.isAlive() && !this.isBaby() && this.hasFlowersLeft();
	}

	@Override
	public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
		if (VARIANT.equals(key)) this.moobloomVariant = Optional.of(this.getVariant());
		super.onSyncedDataUpdated(key);
	}

	@Nullable
	@Override
	public <T> T get(@NotNull DataComponentType<? extends T> dataComponentType) {
		if (dataComponentType == WWDataComponents.MOOBLOOM_VARIANT) return castComponentValue(dataComponentType, this.getVariantAsHolder());
		return super.get(dataComponentType);
	}

	@Override
	protected void applyImplicitComponents(@NotNull DataComponentGetter dataComponentGetter) {
		this.applyImplicitComponentIfPresent(dataComponentGetter, WWDataComponents.MOOBLOOM_VARIANT);
		super.applyImplicitComponents(dataComponentGetter);
	}

	@Override
	protected <T> boolean applyImplicitComponent(@NotNull DataComponentType<T> dataComponentType, @NotNull T object) {
		if (dataComponentType == WWDataComponents.MOOBLOOM_VARIANT) {
			this.setVariant(castComponentValue(WWDataComponents.MOOBLOOM_VARIANT, object).value());
			return true;
		}
		return super.applyImplicitComponent(dataComponentType, object);
	}

	@Override
	public void addAdditionalSaveData(@NotNull ValueOutput valueOutput) {
		super.addAdditionalSaveData(valueOutput);
		VariantUtils.writeVariant(valueOutput, this.getVariantAsHolder());
		valueOutput.putInt("FlowersLeft", this.getFlowersLeft());
	}

	@Override
	public void readAdditionalSaveData(@NotNull ValueInput valueInput) {
		super.readAdditionalSaveData(valueInput);
		VariantUtils.readVariant(valueInput, WilderWildRegistries.MOOBLOOM_VARIANT)
			.ifPresent(variant -> this.setVariant(variant.value()));
		valueInput.getInt("FlowersLeft").ifPresent(this::setFlowersLeft);
	}

	public Identifier getVariantLocation() {
		return Identifier.parse(this.entityData.get(VARIANT));
	}

	public MoobloomVariant getVariantByLocation() {
		return this.registryAccess().lookupOrThrow(WilderWildRegistries.MOOBLOOM_VARIANT).getValue(this.getVariantLocation());
	}

	public Holder<MoobloomVariant> getVariantAsHolder() {
		return this.registryAccess().lookupOrThrow(WilderWildRegistries.MOOBLOOM_VARIANT).get(this.getVariantLocation()).orElseThrow();
	}

	public MoobloomVariant getVariantForRendering() {
		return this.moobloomVariant.orElse(this.registryAccess().lookupOrThrow(WilderWildRegistries.MOOBLOOM_VARIANT).getValue(MoobloomVariants.DEFAULT));
	}

	public MoobloomVariant getVariant() {
		return this.getVariantByLocation();
	}

	public void setVariant(@NotNull MoobloomVariant variant) {
		this.entityData.set(VARIANT, Objects.requireNonNull(this.registryAccess().lookupOrThrow(WilderWildRegistries.MOOBLOOM_VARIANT).getKey(variant)).toString());
	}

	public void setVariant(@NotNull Identifier variant) {
		this.entityData.set(VARIANT, variant.toString());
	}

	public int getFlowersLeft() {
		return this.entityData.get(FLOWERS_LEFT);
	}

	public void setFlowersLeft(int flowersLeft) {
		this.entityData.set(FLOWERS_LEFT, flowersLeft);
	}

	public boolean hasMaxFlowersLeft() {
		return this.getFlowersLeft() >= MAX_FLOWERS;
	}

	public boolean hasFlowersLeft() {
		return this.getFlowersLeft() > 0;
	}

	public void incrementFlowersLeft() {
		this.setFlowersLeft(this.getFlowersLeft() + 1);
	}

	public void decrementFlowersLeft() {
		this.setFlowersLeft(this.getFlowersLeft() - 1);
	}

	@Nullable
	public FlowerCow getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		FlowerCow flowerCow = WWEntityTypes.MOOBLOOM.create(serverLevel, EntitySpawnReason.BREEDING);
		if (flowerCow != null && ageableMob instanceof FlowerCow otherFlowerCow) flowerCow.setVariant(this.getOffspringType(serverLevel, otherFlowerCow));
		return flowerCow;
	}

	private MoobloomVariant getOffspringType(ServerLevel serverLevel, @NotNull FlowerCow flowerCow) {
		MoobloomVariant flowerType = this.getVariant();
		MoobloomVariant otherFlowerType = flowerCow.getVariant();
		if (flowerType == otherFlowerType) return flowerType;
		return this.getOffspringVariant(serverLevel, flowerCow);
	}

	private @NotNull MoobloomVariant getOffspringVariant(ServerLevel serverLevel, @NotNull FlowerCow otherFlowerCow) {
		MoobloomVariant moobloomVariant = this.getVariant();
		MoobloomVariant otherMoobloomVariant = otherFlowerCow.getVariant();

		BlockState flowerBlock = moobloomVariant.getFlowerBlockState();
		BlockState otherFlowerBlock = otherMoobloomVariant.getFlowerBlockState();

		Optional<DyeColor> dyeColor = getDyeColorFromFlower(serverLevel, flowerBlock.getBlock());
		Optional<DyeColor> otherDyeColor = getDyeColorFromFlower(serverLevel, otherFlowerBlock.getBlock());

		if (dyeColor.isPresent() && otherDyeColor.isPresent()) {
			DyeColor outputDyeColor = getCombinedDyeColor(serverLevel, dyeColor.get(), otherDyeColor.get());
			Stream<MoobloomVariant> variantStream = serverLevel.registryAccess().lookupOrThrow(WilderWildRegistries.MOOBLOOM_VARIANT).stream();
			for (MoobloomVariant registeredVariant : Util.toShuffledList(variantStream, this.random)) {
				Block variantFlower = registeredVariant.getFlowerBlockState().getBlock();
				Optional<DyeColor> flowerDyeColor = getDyeColorFromFlower(serverLevel, variantFlower);
				if (flowerDyeColor.isPresent() && flowerDyeColor.get().equals(outputDyeColor)) return registeredVariant;
			}
		}

		return this.random.nextBoolean() ? moobloomVariant : otherMoobloomVariant;
	}

	private static @NotNull Optional<DyeColor> getDyeColorFromFlower(@NotNull ServerLevel level, Block flowerBlock) {
		CraftingInput craftingInput = makeCraftInputForFlower(flowerBlock);
		return level.recipeAccess()
			.getRecipeFor(RecipeType.CRAFTING, craftingInput, level)
			.map(recipeHolder -> recipeHolder.value().assemble(craftingInput, level.registryAccess()))
			.map(ItemStack::getItem)
			.filter(DyeItem.class::isInstance)
			.map(DyeItem.class::cast)
			.map(DyeItem::getDyeColor);
	}

	private static DyeColor getCombinedDyeColor(@NotNull ServerLevel level, DyeColor dyeColor, DyeColor otherDyeColor) {
		CraftingInput craftingInput = makeCraftInput(dyeColor, otherDyeColor);
		return level.recipeAccess()
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
