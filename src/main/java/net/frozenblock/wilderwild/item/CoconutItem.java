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

import net.frozenblock.wilderwild.entity.CoconutProjectile;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class CoconutItem extends BlockItem implements ProjectileItem {

	public CoconutItem(@NotNull Block block, @NotNull Properties properties) {
		super(block, properties);
	}

	@NotNull
	@Override
	public InteractionResult useOn(@NotNull UseOnContext context) {
		InteractionResult interactionResult = super.useOn(context);
		if (interactionResult == InteractionResult.FAIL) return InteractionResult.PASS;
		return interactionResult;
	}

	@Override
	@NotNull
	public InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
		ItemStack itemStack = player.getItemInHand(usedHand);
		level.playSound(
			player,
			player.getX(),
			player.getY(),
			player.getZ(),
			WWSounds.ITEM_COCONUT_THROW,
			SoundSource.NEUTRAL,
			0.5F,
			0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
		);
		itemStack.consume(1, player);
		if (!level.isClientSide) Projectile.spawnProjectileFromRotation(CoconutProjectile::new, (ServerLevel) level, itemStack, player, 0F, 0.8F, 1.4F);
		player.awardStat(Stats.ITEM_USED.get(this));
		return InteractionResult.SUCCESS;
	}

	@Override
	@NotNull
	public Projectile asProjectile(Level level, @NotNull Position position, ItemStack stack, Direction direction) {
		return new CoconutProjectile(level, position.x(), position.y(), position.z(), stack);
	}

	@Override
	@NotNull
	public DispenseConfig createDispenseConfig() {
		return DispenseConfig.builder().uncertainty(9F).power(0.75F).build();
	}
}
