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

package net.frozenblock.wilderwild.advancements.predicates.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.registry.WWAttachmentTypes;
import net.minecraft.advancements.predicates.entity.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class WasGrappledByPlayerHarpoonPredicate implements EntitySubPredicate {
	public static final WasGrappledByPlayerHarpoonPredicate INSTANCE = new WasGrappledByPlayerHarpoonPredicate();
	public static final Codec<WasGrappledByPlayerHarpoonPredicate> CODEC = RecordCodecBuilder.create((instance) -> instance.point(INSTANCE));

	private WasGrappledByPlayerHarpoonPredicate() {}

	@Override
	public boolean matches(Entity entity, ServerLevel serverLevel, @Nullable Vec3 vec3) {
		return EntityReference.get(WWAttachmentTypes.GRAPPLED_BY_PLAYER_HARPOON.get(entity), serverLevel, Player.class) != null;
	}
}
