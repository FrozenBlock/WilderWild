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

package net.frozenblock.wilderwild.registry;

import com.mojang.serialization.Codec;
import net.frozenblock.lib.platform.api.attachment.DataAttachmentSyncPredicate;
import net.frozenblock.lib.platform.api.attachment.DataAttachmentType;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.ambient.butterfly.ButterflyVariant;
import net.frozenblock.wilderwild.entity.ambient.firefly.FireflyVariant;
import net.frozenblock.wilderwild.entity.animal.cow.MoobloomVariant;
import net.frozenblock.wilderwild.entity.animal.crab.CrabVariant;
import net.frozenblock.wilderwild.entity.animal.jellyfish.JellyfishVariant;
import net.frozenblock.wilderwild.entity.monster.skeleton.SunkenVariant;
import net.frozenblock.wilderwild.entity.projectile.arrow.Harpoon;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.LivingEntity;

public final class WWAttachmentTypes {
	// VARIANT
	public static final DataAttachmentType<Holder<FireflyVariant>> FIREFLY_VARIANT = DataAttachmentType.create(
		WWConstants.id("firefly_variant"),
		builder -> {
			builder.syncWith(ByteBufCodecs.holderRegistry(WilderWildRegistries.FIREFLY_VARIANT), DataAttachmentSyncPredicate.all());
		}
	);
	public static final DataAttachmentType<Holder<ButterflyVariant>> BUTTERFLY_VARIANT = DataAttachmentType.create(
		WWConstants.id("butterfly_variant"),
		builder -> {
			builder.syncWith(ByteBufCodecs.holderRegistry(WilderWildRegistries.BUTTERFLY_VARIANT), DataAttachmentSyncPredicate.all());
		}
	);
	public static final DataAttachmentType<Holder<CrabVariant>> CRAB_VARIANT = DataAttachmentType.create(
		WWConstants.id("crab_variant"),
		builder -> {
			builder.syncWith(ByteBufCodecs.holderRegistry(WilderWildRegistries.CRAB_VARIANT), DataAttachmentSyncPredicate.all());
		}
	);
	public static final DataAttachmentType<Holder<JellyfishVariant>> JELLYFISH_VARIANT = DataAttachmentType.create(
		WWConstants.id("jellyfish_variant"),
		builder -> {
			builder.syncWith(ByteBufCodecs.holderRegistry(WilderWildRegistries.JELLYFISH_VARIANT), DataAttachmentSyncPredicate.all());
		}
	);
	public static final DataAttachmentType<Holder<MoobloomVariant>> MOOBLOOM_VARIANT = DataAttachmentType.create(
		WWConstants.id("moobloom_variant"),
		builder -> {
			builder.syncWith(ByteBufCodecs.holderRegistry(WilderWildRegistries.MOOBLOOM_VARIANT), DataAttachmentSyncPredicate.all());
		}
	);
	public static final DataAttachmentType<Holder<SunkenVariant>> SUNKEN_VARIANT = DataAttachmentType.create(
		WWConstants.id("sunken_variant"),
		builder -> {
			builder.syncWith(ByteBufCodecs.holderRegistry(WilderWildRegistries.SUNKEN_VARIANT), DataAttachmentSyncPredicate.all());
		}
	);

	// HARPOON
	public static final DataAttachmentType<EntityReference<Harpoon>> HARPOON = DataAttachmentType.create(
		WWConstants.id("harpoon"),
		builder -> {
			builder.syncWith(EntityReference.streamCodec(), DataAttachmentSyncPredicate.all());
		}
	);
	public static final DataAttachmentType<EntityReference<LivingEntity>> HARPOON_ATTACHED_TO = DataAttachmentType.create(
		WWConstants.id("harpoon_attached_to"),
		builder -> {
			builder.syncWith(EntityReference.streamCodec(), DataAttachmentSyncPredicate.all());
		}
	);

	// BOAT
	public static final DataAttachmentType<Integer> BOAT_BOOST_TICKS = DataAttachmentType.create(
		WWConstants.id("boat_boosted"),
		builder -> {
			builder.initializer(() -> 0);
			builder.syncWith(ByteBufCodecs.VAR_INT, DataAttachmentSyncPredicate.all());
			builder.persistent(Codec.INT);
		}
	);
	public static final DataAttachmentType<Boolean> CHEST_CAN_BUBBLE = DataAttachmentType.create(
		WWConstants.id("chest_can_bubble"),
		builder -> {
			builder.initializer(() -> false);
			builder.persistent(Codec.BOOL);
		}
	);
	public static final DataAttachmentType<Integer> TURTLE_HUNT_COOLDOWN = DataAttachmentType.create(
		WWConstants.id("turtle_hunt_cooldown"),
		builder -> {
			builder.initializer(() -> 0);
			builder.persistent(Codec.INT);
		}
	);

	// SCULK SENSOR
	public static final DataAttachmentType<Integer> SCULK_SENSOR_AGE_IN_TICKS = DataAttachmentType.create(
		WWConstants.id("sculk_sensor_age_in_ticks"),
		builder -> builder.initializer(() -> 0)
	);
	public static final DataAttachmentType<Integer> SCULK_SENSOR_TENDRIL_ANIMATION0 = DataAttachmentType.create(
		WWConstants.id("sculk_sensor_tendril_animation0"),
		builder -> builder.initializer(() -> 0)
	);
	public static final DataAttachmentType<Integer> SCULK_SENSOR_TENDRIL_ANIMATION = DataAttachmentType.create(
		WWConstants.id("sculk_sensor_tendril_animation"),
		builder -> builder.initializer(() -> 0)
	);
	public static final DataAttachmentType<Boolean> SCULK_SENSOR_ACTIVE = DataAttachmentType.create(
		WWConstants.id("sculk_sensor_active"),
		builder -> builder.initializer(() -> false)
	);
	public static final DataAttachmentType<Direction> SCULK_SENSOR_FACING = DataAttachmentType.create(
		WWConstants.id("sculk_sensor_facing"),
		builder -> builder.initializer(() -> Direction.NORTH)
	);

	public static void init() {}

	private WWAttachmentTypes() {}
}
