/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.item;

import com.mojang.serialization.MapCodec;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.impl.Bottleable;
import net.frozenblock.wilderwild.entity.variant.ButterflyVariant;
import net.frozenblock.wilderwild.entity.variant.FireflyColor;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Optional;

public class MobBottleItem extends Item {
	private static final MapCodec<ButterflyVariant> BUTTERFLY_VARIANT_FIELD_CODEC = ButterflyVariant.CODEC.fieldOf("ButterflyBottleVariantTag");
	private static final MapCodec<FireflyColor> FIREFLY_VARIANT_FIELD_CODEC = FireflyColor.CODEC.fieldOf("FireflyBottleVariantTag");
	private final EntityType<?> type;
	private final SoundEvent releaseSound;

	public MobBottleItem(EntityType<?> type, SoundEvent releaseSound, @NotNull Properties settings) {
		super(settings);
		this.type = type;
		this.releaseSound = releaseSound;
	}

	@NotNull
	@Override
	public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionHand) {
		if (level instanceof ServerLevel server && player.getAbilities().mayBuild) {
			float pitch = player.getXRot();
			float yaw = player.getYRot();
			float f = -Mth.sin(yaw * Mth.DEG_TO_RAD) * Mth.cos(pitch * Mth.DEG_TO_RAD);
			float g = -Mth.sin((pitch) * Mth.DEG_TO_RAD);
			float h = Mth.cos(yaw * Mth.DEG_TO_RAD) * Mth.cos(pitch * Mth.DEG_TO_RAD);
			ItemStack stack = player.getItemInHand(interactionHand);
			Entity entity = this.type.create(server);
			if (entity != null) {
				entity.setDeltaMovement(f * 0.7D, g * 0.7D, h * 0.7D);
				entity.moveTo(player.getX(), player.getEyeY(), player.getZ(), player.getXRot(), player.getYRot());
				boolean spawned = server.addFreshEntity(entity);
				if (spawned) {
					if (entity instanceof Bottleable bottleable) {
						CustomData customData = stack.getOrDefault(WWDataComponents.BOTTLE_ENTITY_DATA, CustomData.EMPTY);
						bottleable.loadFromBottleTag(customData.copyTag());
						bottleable.setFromBottle(true);
						bottleable.onBottleRelease();
					}

					if (!player.getAbilities().instabuild) player.setItemInHand(interactionHand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
					player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
					entity.playSound(this.releaseSound, 1F, level.getRandom().nextFloat() * 0.2F + 0.9F);
					if (stack.has(DataComponents.CUSTOM_NAME)) {
						entity.setCustomName(stack.getHoverName());
					}
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
			Optional<ButterflyVariant> optional = customData.read(BUTTERFLY_VARIANT_FIELD_CODEC).result();
			if (optional.isPresent()) {
				ChatFormatting[] chatFormattings = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
				ResourceLocation variantKey = optional.get().key();
				list.add(Component.translatable(variantKey.getNamespace() + ".butterfly.variant." + variantKey.getPath()).withStyle(chatFormattings));
			}
		} else if (this.type == WWEntityTypes.FIREFLY) {
			Optional<FireflyColor> optional = customData.read(FIREFLY_VARIANT_FIELD_CODEC).result();
			if (optional.isPresent()) {
				ResourceLocation colorKey = optional.get().key();
				if (colorKey == FireflyColor.ON.key()) return;

				ChatFormatting[] chatFormattings = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
				list.add(Component.translatable(colorKey.getNamespace() + ".firefly.color." + colorKey.getPath()).withStyle(chatFormattings));
			}
		}
	}

	@NotNull
	@Override
	public UseAnim getUseAnimation(@NotNull ItemStack stack) {
		return UseAnim.NONE;
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity livingEntity) {
		return 1;
	}

}
