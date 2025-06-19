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

package net.frozenblock.wilderwild.item;

import com.mojang.serialization.MapCodec;
import java.util.List;
import java.util.Optional;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.impl.WWBottleable;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColors;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import net.minecraft.ChatFormatting;

public class MobBottleItem extends Item {
	public static final String FIREFLY_BOTTLE_VARIANT_FIELD = "FireflyBottleVariantTag";
	public static final String BUTTERFLY_BOTTLE_VARIANT_FIELD = "ButterflyBottleVariantTag";
	private static final MapCodec<ResourceLocation> FIREFLY_VARIANT_FIELD_CODEC = ResourceLocation.CODEC.fieldOf(FIREFLY_BOTTLE_VARIANT_FIELD);
	private static final MapCodec<ResourceLocation> BUTTERFLY_VARIANT_FIELD_CODEC = ResourceLocation.CODEC.fieldOf(BUTTERFLY_BOTTLE_VARIANT_FIELD);
	private final EntityType<?> type;
	private final SoundEvent releaseSound;

	public MobBottleItem(EntityType<?> type, SoundEvent releaseSound, @NotNull Properties settings) {
		super(settings);
		this.type = type;
		this.releaseSound = releaseSound;
	}

	@NotNull
	@Override
	public InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionHand) {
		if (level instanceof ServerLevel server && player.getAbilities().mayBuild) {
			float pitch = player.getXRot();
			float yaw = player.getYRot();
			float f = -Mth.sin(yaw * Mth.DEG_TO_RAD) * Mth.cos(pitch * Mth.DEG_TO_RAD);
			float g = -Mth.sin((pitch) * Mth.DEG_TO_RAD);
			float h = Mth.cos(yaw * Mth.DEG_TO_RAD) * Mth.cos(pitch * Mth.DEG_TO_RAD);
			ItemStack stack = player.getItemInHand(interactionHand);
			Entity entity = this.type.create(server, EntitySpawnReason.BUCKET);
			if (entity != null) {
				entity.setDeltaMovement(f * 0.7D, g * 0.7D, h * 0.7D);
				entity.moveTo(player.getX(), player.getEyeY(), player.getZ(), player.getXRot(), player.getYRot());
				boolean spawned = server.addFreshEntity(entity);
				if (spawned) {
					if (entity instanceof WWBottleable bottleable) {
						CustomData customData = stack.getOrDefault(WWDataComponents.BOTTLE_ENTITY_DATA, CustomData.EMPTY);
						bottleable.wilderWild$loadFromBottleTag(customData.copyTag());
						bottleable.wilderWild$setFromBottle(true);
						bottleable.wilderWild$onBottleRelease();
					}

					if (!player.getAbilities().instabuild) player.setItemInHand(interactionHand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
					player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
					server.playSound(
						null,
						player.getX(),
						player.getEyeY(),
						player.getZ(),
						this.releaseSound,
						SoundSource.PLAYERS,
						0.75F,
						level.getRandom().nextFloat() * 0.2F + 0.9F
					);

					if (stack.has(DataComponents.CUSTOM_NAME)) entity.setCustomName(stack.getHoverName());
					player.gameEvent(GameEvent.ENTITY_PLACE);
				} else {
					WWConstants.printStackTrace("Couldn't spawn entity from bottle!", true);
				}
			}
		}
		return ItemUtils.startUsingInstantly(level, player, interactionHand);
	}

	@Override
	public void appendHoverText(@NotNull ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
		CustomData customData = itemStack.getOrDefault(WWDataComponents.BOTTLE_ENTITY_DATA, CustomData.EMPTY);
		if (customData.isEmpty()) return;
		if (this.type == WWEntityTypes.BUTTERFLY) {
			Optional<ResourceLocation> optional = customData.read(BUTTERFLY_VARIANT_FIELD_CODEC).result();
			if (optional.isEmpty()) return;
			ChatFormatting[] chatFormattings = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
			ResourceLocation variantKey = optional.get();
			list.add(Component.translatable(variantKey.getNamespace() + ".butterfly.variant." + variantKey.getPath()).withStyle(chatFormattings));
		} else if (this.type == WWEntityTypes.FIREFLY) {
			Optional<ResourceLocation> optional = customData.read(FIREFLY_VARIANT_FIELD_CODEC).result();
			if (optional.isEmpty()) return;
			ResourceLocation colorKey = optional.get();
			if (colorKey.equals(FireflyColors.DEFAULT.location())) return;
			ChatFormatting[] chatFormattings = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
			list.add(Component.translatable(colorKey.getNamespace() + ".firefly.color." + colorKey.getPath()).withStyle(chatFormattings));
		}
	}

	@NotNull
	@Override
	public ItemUseAnimation getUseAnimation(@NotNull ItemStack stack) {
		return ItemUseAnimation.NONE;
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity livingEntity) {
		return 1;
	}

}
