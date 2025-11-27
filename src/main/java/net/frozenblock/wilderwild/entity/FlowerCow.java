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
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.cow.AbstractCow;
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
import org.jetbrains.annotations.Nullable;

public class FlowerCow extends AbstractCow implements Shearable {
	public static final int MAX_FLOWERS = 4;
	private static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(FlowerCow.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<Integer> FLOWERS_LEFT = SynchedEntityData.defineId(FlowerCow.class, EntityDataSerializers.INT);

	private Optional<MoobloomVariant> moobloomVariant = Optional.empty();

	public FlowerCow(EntityType<? extends FlowerCow> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public float getWalkTargetValue(BlockPos pos, LevelReader level) {
		BlockState state = level.getBlockState(pos);
		if (this.getVariant().flowerBlockState().is(state.getBlock())) return 20F;
		return super.getWalkTargetValue(pos, level);
	}

	public static boolean checkFlowerCowSpawnRules(EntityType<FlowerCow> type, LevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
		if (!EntitySpawnReason.isSpawner(reason) && !WWEntityConfig.get().moobloom.spawnMooblooms) return false;
		return checkAnimalSpawnRules(type, level, reason, pos, random);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData spawnGroupData) {
		if (spawnGroupData instanceof FlowerCowSpawnGroupData flowerCowSpawnGroupData) {
			this.setVariant(flowerCowSpawnGroupData.type.value());
		} else {
			final Optional<Holder.Reference<MoobloomVariant>> optionalMoobloomVariantReference = MoobloomVariants.selectVariantToSpawn(
				level.getRandom(), this.registryAccess(), SpawnContext.create(level, this.blockPosition())
			);
			if (optionalMoobloomVariantReference.isPresent()) {
				spawnGroupData = new FlowerCowSpawnGroupData(optionalMoobloomVariantReference.get());
				this.setVariant(optionalMoobloomVariantReference.get().value());
			}
		}

		return super.finalizeSpawn(level, difficulty, reason, spawnGroupData);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(VARIANT, MoobloomVariants.DEFAULT.identifier().toString());
		builder.define(FLOWERS_LEFT, MAX_FLOWERS);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		final ItemStack stack = player.getItemInHand(hand);
		if (!this.isBaby() && this.isFood(stack) && !this.hasMaxFlowersLeft()) {
			if (!this.level().isClientSide()) {
				this.usePlayerItem(player, hand, stack);
				this.incrementFlowersLeft();
				this.level().broadcastEntityEvent(this, EntityEvent.TENDRILS_SHIVER);
				return InteractionResult.SUCCESS_SERVER;
			}
			return InteractionResult.CONSUME;
		} else if (stack.is(Items.SHEARS)) {
			if (this.level() instanceof ServerLevel serverLevel && this.readyForShearing()) {
				this.shear(serverLevel, SoundSource.PLAYERS, stack);
				this.gameEvent(GameEvent.SHEAR, player);
				stack.hurtAndBreak(1, player, hand);
				return InteractionResult.SUCCESS_SERVER;
			}
			return InteractionResult.CONSUME;
		}
		return super.mobInteract(player, hand);
	}

	@Override
	public void shear(ServerLevel level, SoundSource source, ItemStack stack) {
		this.level().playSound(null, this, WWSounds.ENTITY_MOOBLOOM_SHEAR, source, 1F, 1F);
		final BlockState flowerState = this.getVariant().flowerBlockState();
		spawnShearParticles(level, this, flowerState);
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

	private static void spawnShearParticles(ServerLevel level, FlowerCow flowerCow, BlockState flowerState) {
		level.sendParticles(
			new BlockParticleOption(ParticleTypes.BLOCK, flowerState),
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
	public void handleEntityEvent(byte event) {
		if (event == EntityEvent.TENDRILS_SHIVER) {
			for (int i = 0; i < 7; i++) {
				final double xd = this.random.nextGaussian() * 0.02D;
				final double yd = this.random.nextGaussian() * 0.02D;
				final double zd = this.random.nextGaussian() * 0.02D;
				this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1D), this.getRandomY() + 0.5D, this.getRandomZ(1D), xd, yd, zd);
			}
		} else {
			super.handleEntityEvent(event);
		}
	}

	@Override
	public boolean readyForShearing() {
		return this.isAlive() && !this.isBaby() && this.hasFlowersLeft();
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
		if (VARIANT.equals(key)) this.moobloomVariant = Optional.of(this.getVariant());
		super.onSyncedDataUpdated(key);
	}

	@Nullable
	@Override
	public <T> T get(DataComponentType<? extends T> type) {
		if (type == WWDataComponents.MOOBLOOM_VARIANT) return castComponentValue(type, this.getVariantAsHolder());
		return super.get(type);
	}

	@Override
	protected void applyImplicitComponents(DataComponentGetter getter) {
		this.applyImplicitComponentIfPresent(getter, WWDataComponents.MOOBLOOM_VARIANT);
		super.applyImplicitComponents(getter);
	}

	@Override
	protected <T> boolean applyImplicitComponent(DataComponentType<T> type, T object) {
		if (type == WWDataComponents.MOOBLOOM_VARIANT) {
			this.setVariant(castComponentValue(WWDataComponents.MOOBLOOM_VARIANT, object).value());
			return true;
		}
		return super.applyImplicitComponent(type, object);
	}

	@Override
	public void addAdditionalSaveData(ValueOutput valueOutput) {
		super.addAdditionalSaveData(valueOutput);
		VariantUtils.writeVariant(valueOutput, this.getVariantAsHolder());
		valueOutput.putInt("FlowersLeft", this.getFlowersLeft());
	}

	@Override
	public void readAdditionalSaveData(ValueInput valueInput) {
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

	public void setVariant(MoobloomVariant variant) {
		this.entityData.set(VARIANT, Objects.requireNonNull(this.registryAccess().lookupOrThrow(WilderWildRegistries.MOOBLOOM_VARIANT).getKey(variant)).toString());
	}

	public void setVariant(Identifier variant) {
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
	public FlowerCow getBreedOffspring(ServerLevel level, AgeableMob mob) {
		final FlowerCow flowerCow = WWEntityTypes.MOOBLOOM.create(level, EntitySpawnReason.BREEDING);
		if (flowerCow != null && mob instanceof FlowerCow otherFlowerCow) flowerCow.setVariant(this.getOffspringType(level, otherFlowerCow));
		return flowerCow;
	}

	private MoobloomVariant getOffspringType(ServerLevel level, FlowerCow flowerCow) {
		MoobloomVariant flowerType = this.getVariant();
		MoobloomVariant otherFlowerType = flowerCow.getVariant();
		if (flowerType == otherFlowerType) return flowerType;
		return this.getOffspringVariant(level, flowerCow);
	}

	private MoobloomVariant getOffspringVariant(ServerLevel level, FlowerCow otherFlowerCow) {
		MoobloomVariant moobloomVariant = this.getVariant();
		MoobloomVariant otherMoobloomVariant = otherFlowerCow.getVariant();

		BlockState flowerBlock = moobloomVariant.flowerBlockState();
		BlockState otherFlowerBlock = otherMoobloomVariant.flowerBlockState();

		Optional<DyeColor> dyeColor = getDyeColorFromFlower(level, flowerBlock.getBlock());
		Optional<DyeColor> otherDyeColor = getDyeColorFromFlower(level, otherFlowerBlock.getBlock());

		if (dyeColor.isPresent() && otherDyeColor.isPresent()) {
			DyeColor outputDyeColor = getCombinedDyeColor(level, dyeColor.get(), otherDyeColor.get());
			Stream<MoobloomVariant> variantStream = level.registryAccess().lookupOrThrow(WilderWildRegistries.MOOBLOOM_VARIANT).stream();
			for (MoobloomVariant registeredVariant : Util.toShuffledList(variantStream, this.random)) {
				Block variantFlower = registeredVariant.flowerBlockState().getBlock();
				Optional<DyeColor> flowerDyeColor = getDyeColorFromFlower(level, variantFlower);
				if (flowerDyeColor.isPresent() && flowerDyeColor.get().equals(outputDyeColor)) return registeredVariant;
			}
		}

		return this.random.nextBoolean() ? moobloomVariant : otherMoobloomVariant;
	}

	private static Optional<DyeColor> getDyeColorFromFlower(ServerLevel level, Block flowerBlock) {
		CraftingInput craftingInput = makeCraftInputForFlower(flowerBlock);
		return level.recipeAccess()
			.getRecipeFor(RecipeType.CRAFTING, craftingInput, level)
			.map(recipeHolder -> recipeHolder.value().assemble(craftingInput, level.registryAccess()))
			.map(ItemStack::getItem)
			.filter(DyeItem.class::isInstance)
			.map(DyeItem.class::cast)
			.map(DyeItem::getDyeColor);
	}

	private static DyeColor getCombinedDyeColor(ServerLevel level, DyeColor dyeColor, DyeColor otherDyeColor) {
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

	private static CraftingInput makeCraftInputForFlower(Block flowerBlock) {
		return CraftingInput.of(1, 1, List.of(new ItemStack(flowerBlock)));
	}

	private static CraftingInput makeCraftInput(DyeColor color, DyeColor otherColor) {
		return CraftingInput.of(2, 1, List.of(new ItemStack(DyeItem.byColor(color)), new ItemStack(DyeItem.byColor(otherColor))));
	}

	public static class FlowerCowSpawnGroupData extends AgeableMob.AgeableMobGroupData {
		public final Holder<MoobloomVariant> type;

		public FlowerCowSpawnGroupData(Holder<MoobloomVariant> holder) {
			super(true);
			this.type = holder;
		}
	}
}
