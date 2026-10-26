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

package net.frozenblock.wilderwild.entity.animal.cow;

import java.util.List;
import java.util.Optional;
import net.frozenblock.lib.platform.api.attachment.DataAttachmentType;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.variant.WWEntityVariantHolder;
import net.frozenblock.wilderwild.registry.WWAttachmentTypes;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
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

public class FlowerCow extends AbstractCow implements Shearable, WWEntityVariantHolder<MoobloomVariant> {
	public static final int MAX_FLOWERS = 4;
	private static final EntityDataAccessor<Integer> FLOWERS_LEFT = SynchedEntityData.defineId(FlowerCow.class, EntityDataSerializers.INT);

	public FlowerCow(EntityType<? extends FlowerCow> type, Level level) {
		super(type, level);
		this.wilderWild$setVariantToDefaultIfEmpty();
	}

	@Override
	public float getWalkTargetValue(BlockPos pos, LevelReader level) {
		final BlockState state = level.getBlockState(pos);
		final Holder<MoobloomVariant> variant = this.wilderWild$getVariant();
		if (variant != null && variant.value().flowerBlockState().is(state.getBlock())) return 20F;

		return super.getWalkTargetValue(pos, level);
	}

	public static boolean checkFlowerCowSpawnRules(EntityType<FlowerCow> type, LevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
		if (!EntitySpawnReason.isSpawner(reason) && !WWEntityConfig.SPAWN_MOOBLOOMS.get()) return false;
		return checkAnimalSpawnRules(type, level, reason, pos, random);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData) {
		if (groupData instanceof FlowerCowSpawnGroupData flowerCowGroupData) {
			this.wilderWild$setVariant(flowerCowGroupData.type);
		} else {
			final Optional<Holder<MoobloomVariant>> optionalVariant = MoobloomVariants.selectVariantToSpawn(
				level.getRandom(), this.registryAccess(), SpawnContext.create(level, this.blockPosition())
			);
			if (optionalVariant.isPresent()) {
				groupData = new FlowerCowSpawnGroupData(optionalVariant.get());
				this.wilderWild$setVariant(optionalVariant.get());
			}
		}

		return super.finalizeSpawn(level, difficulty, spawnReason, groupData);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder entityData) {
		super.defineSynchedData(entityData);
		entityData.define(FLOWERS_LEFT, MAX_FLOWERS);
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
		this.level().playSound(null, this, WWSounds.ENTITY_MOOBLOOM_SHEAR.get(), source, 1F, 1F);
		final BlockState flowerState = this.wilderWild$getVariant().value().flowerBlockState();
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
	public void handleEntityEvent(@EntityEvent.Value byte id) {
		if (id == EntityEvent.TENDRILS_SHIVER) {
			for (int i = 0; i < 7; i++) {
				final double xd = this.random.nextGaussian() * 0.02D;
				final double yd = this.random.nextGaussian() * 0.02D;
				final double zd = this.random.nextGaussian() * 0.02D;
				this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1D), this.getRandomY() + 0.5D, this.getRandomZ(1D), xd, yd, zd);
			}
		} else {
			super.handleEntityEvent(id);
		}
	}

	@Override
	public boolean readyForShearing() {
		return this.isAlive() && !this.isBaby() && this.hasFlowersLeft();
	}

	@Nullable
	@Override
	public <T> T get(DataComponentType<? extends T> type) {
		if (type == WWDataComponents.MOOBLOOM_VARIANT.get()) return castComponentValue(type, this.wilderWild$getVariant());
		return super.get(type);
	}

	@Override
	protected void applyImplicitComponents(DataComponentGetter components) {
		this.applyImplicitComponentIfPresent(components, WWDataComponents.MOOBLOOM_VARIANT.get());
		super.applyImplicitComponents(components);
	}

	@Override
	protected <T> boolean applyImplicitComponent(DataComponentType<T> type, T value) {
		if (type == WWDataComponents.MOOBLOOM_VARIANT.get()) {
			this.wilderWild$setVariant(castComponentValue(WWDataComponents.MOOBLOOM_VARIANT.get(), value));
			return true;
		}
		return super.applyImplicitComponent(type, value);
	}

	@Override
	public void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		this.wilderWild$writeVariant(output);
		output.putInt("FlowersLeft", this.getFlowersLeft());
	}

	@Override
	public void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		this.wilderWild$readVariant(input).ifPresent(this::wilderWild$setVariant);
		input.getInt("FlowersLeft").ifPresent(this::setFlowersLeft);
	}

	@Override
	public ResourceKey<Registry<MoobloomVariant>> wilderWild$variantRegistry() {
		return WilderWildRegistries.MOOBLOOM_VARIANT;
	}

	@Override
	public ResourceKey<MoobloomVariant> wilderWild$defaultVariant() {
		return MoobloomVariants.DEFAULT;
	}

	@Override
	public DataAttachmentType<Holder<MoobloomVariant>> wilderWild$variantAttachmentType() {
		return WWAttachmentTypes.MOOBLOOM_VARIANT;
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
	public FlowerCow getBreedOffspring(ServerLevel level, AgeableMob partner) {
		final FlowerCow flowerCow = WWEntityTypes.MOOBLOOM.get().create(level, EntitySpawnReason.BREEDING);
		if (flowerCow != null && partner instanceof FlowerCow otherFlowerCow) flowerCow.wilderWild$setVariant(this.getOffspringType(level, otherFlowerCow));
		return flowerCow;
	}

	private Holder<MoobloomVariant> getOffspringType(ServerLevel level, FlowerCow other) {
		final Holder<MoobloomVariant> thisType = this.wilderWild$getVariant();
		final Holder<MoobloomVariant> otherType = other.wilderWild$getVariant();
		if (thisType == otherType) return thisType;

		return this.getOffspringVariant(level, other);
	}

	private Holder<MoobloomVariant> getOffspringVariant(ServerLevel level, FlowerCow otherFlowerCow) {
		final Holder<MoobloomVariant> myVariant = this.wilderWild$getVariant();
		final Holder<MoobloomVariant> otherVariant = otherFlowerCow.wilderWild$getVariant();

		final BlockState flowerBlock = myVariant.value().flowerBlockState();
		final BlockState otherFlowerBlock = otherVariant.value().flowerBlockState();

		final Optional<DyeColor> dyeColor = getDyeColorFromFlower(level, flowerBlock.getBlock());
		final Optional<DyeColor> otherDyeColor = getDyeColorFromFlower(level, otherFlowerBlock.getBlock());

		returnCombinedColor: {
			if (dyeColor.isEmpty() && otherDyeColor.isEmpty()) break returnCombinedColor;

			final DyeColor thisColor = dyeColor.get();
			final DyeColor otherColor = otherDyeColor.get();
			if (thisColor == otherColor) break returnCombinedColor;

			final DyeColor outputDyeColor = DyeColor.getMixedColor(level, dyeColor.get(), otherDyeColor.get());
			if (outputDyeColor == thisColor || outputDyeColor == otherColor) break returnCombinedColor;

			final Registry<MoobloomVariant> registry = level.registryAccess().lookupOrThrow(WilderWildRegistries.MOOBLOOM_VARIANT);
			for (MoobloomVariant variant : Util.toShuffledList(registry.stream(), this.random)) {
				final Block variantFlower = variant.flowerBlockState().getBlock();
				final Optional<DyeColor> flowerDyeColor = getDyeColorFromFlower(level, variantFlower);
				if (flowerDyeColor.isPresent() && flowerDyeColor.get().equals(outputDyeColor)) return registry.wrapAsHolder(variant);
			}
		}

		return this.random.nextBoolean() ? myVariant : otherVariant;
	}

	private static Optional<DyeColor> getDyeColorFromFlower(ServerLevel level, Block flowerBlock) {
		final CraftingInput craftingInput = makeCraftInputForFlower(flowerBlock);
		return level.recipeAccess()
			.getRecipeFor(RecipeType.CRAFTING, craftingInput, level)
			.map(recipeHolder -> recipeHolder.value().assemble(craftingInput))
			.map(ItemStack::getItem)
			.filter(DyeItem.class::isInstance)
			.map(DyeItem.class::cast)
			.map(item -> item.components().get(DataComponents.DYE));
	}

	private static CraftingInput makeCraftInputForFlower(Block flowerBlock) {
		return CraftingInput.of(1, 1, List.of(new ItemStack(flowerBlock)));
	}

	public static class FlowerCowSpawnGroupData extends AgeableMob.AgeableMobGroupData {
		public final Holder<MoobloomVariant> type;

		public FlowerCowSpawnGroupData(Holder<MoobloomVariant> holder) {
			super(true);
			this.type = holder;
		}
	}
}
