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

package net.frozenblock.wilderwild.entity;

import net.frozenblock.wilderwild.registry.WWEntities;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.frozenblock.wilderwild.tag.WilderEntityTags;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class CoconutProjectile extends ThrowableItemProjectile {
	public static final float ENTITY_SPLIT_CHANCE = 0.7F;

	public CoconutProjectile(@NotNull EntityType<? extends CoconutProjectile> entityType, @NotNull Level level) {
		super(entityType, level);
	}

	public CoconutProjectile(@NotNull Level level, @NotNull LivingEntity shooter) {
		super(WWEntities.COCONUT, shooter, level);
	}

	public CoconutProjectile(@NotNull Level level, double x, double y, double z) {
		super(WWEntities.COCONUT, x, y, z, level);
	}

	@Override
	@NotNull
	protected Item getDefaultItem() {
		return WWItems.COCONUT;
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == 3) {
			ParticleOptions particleOptions = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(this.getDefaultItem()));
			for (int i = 0; i < 8; ++i) {
				this.level().addParticle(particleOptions, this.getX(), this.getY(), this.getZ(), 0D, 0D, 0D);
			}
		} else {
			super.handleEntityEvent(id);
		}
	}

	@Override
	protected void onHitEntity(@NotNull EntityHitResult result) {
		super.onHitEntity(result);
		SoundEvent hitSound = WWSounds.ITEM_COCONUT_LAND;
		Entity entity = result.getEntity();
		entity.hurt(entity.damageSources().thrown(this, this.getOwner()), 2F);
		if (this.getY() > entity.getEyeY() && !entity.getType().is(WilderEntityTags.COCONUT_CANT_BONK)) {
			hitSound = WWSounds.ITEM_COCONUT_HIT_HEAD;
		}
		if (!entity.getType().is(WilderEntityTags.COCONUT_CANT_SPLIT) && entity.getBoundingBox().getSize() > this.getBoundingBox().getSize() && this.random.nextFloat() < ENTITY_SPLIT_CHANCE) {
			this.splitAndDiscard();
		} else {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), hitSound, this.getSoundSource(), 1F, 0.9F + (this.random.nextFloat() * 0.2F));
		}
	}

	@Override
	protected void onHitBlock(@NotNull BlockHitResult result) {
		super.onHitBlock(result);
		if (this.level().getBlockState(result.getBlockPos()).is(WilderBlockTags.SPLITS_COCONUT)) {
			this.splitAndDiscard();
			return;
		}
		this.level().playSound(null, this.getX(), this.getY(), this.getZ(), WWSounds.ITEM_COCONUT_LAND, SoundSource.BLOCKS, 0.4F, 0.9F + (this.random.nextFloat() * 0.2F));
		this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), this.getItem()));
		this.discard();
	}

	public void splitAndDiscard() {
		if (this.level() instanceof ServerLevel server) {
			server.playSound(null, this.getX(), this.getY(), this.getZ(), WWSounds.ITEM_COCONUT_LAND_BREAK, SoundSource.BLOCKS, 1.0F, 0.9F + 0.2F * this.random.nextFloat());
			for (int i = 0; i < 2; ++i) {
				server.addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(WWItems.SPLIT_COCONUT)));
			}
			EntityDimensions dimensions = this.getDimensions(Pose.STANDING);
			server.sendParticles(WWParticleTypes.COCONUT_SPLASH, this.position().x + (dimensions.width() * 0.5), this.position().y + (dimensions.height() * 0.5), this.position().z + (dimensions.width() * 0.5), this.random.nextInt(1, 5), dimensions.width() / 4F, dimensions.height() / 4F, dimensions.width() / 4F, 0.1D);
			this.discard();
		}
	}

}
