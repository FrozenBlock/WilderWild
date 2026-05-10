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

package net.frozenblock.wilderwild.mod_compat;

import java.util.List;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLevelEvents;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.block.api.dripstone.DripstoneDripApi;
import net.frozenblock.lib.block.api.friction.BlockFrictionAPI;
import net.frozenblock.lib.block.api.tick.BlockRandomTicks;
import net.frozenblock.lib.block.api.tick.BlockScheduledTicks;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.item.api.ItemTooltipAdditionAPI;
import net.frozenblock.lib.particle.api.VibrationParticleVisibilityApi;
import net.frozenblock.lib.sound.api.damage.PlayerDamageTypeSounds;
import net.frozenblock.lib.spotting_icons.api.SpottingIconPredicate;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.WWFeatureFlags;
import net.frozenblock.wilderwild.block.FroglightGoopBlock;
import net.frozenblock.wilderwild.block.entity.IcicleBlockEntity;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.network.chat.Component;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireflyBushBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.ChatFormatting;

public class FrozenLibIntegration extends ModIntegration {

	public FrozenLibIntegration() {
		super("frozenlib");
	}

	@Override
	public void initPreFreeze() {
		WWConstants.log("FrozenLib pre-freeze mod integration ran!", WWConstants.UNSTABLE_LOGGING);
		SpottingIconPredicate.register(
			WWConstants.id("stella"),
			entity -> entity.hasCustomName() && entity.getCustomName().getString().equalsIgnoreCase("stella")
		);

		ItemTooltipAdditionAPI.addTooltip(
			Component.translatable("item.disabled.trailiertales").withStyle(ChatFormatting.RED),
			stack -> !FrozenBools.HAS_TRAILIERTALES && stack.getItem().requiredFeatures().contains(WWFeatureFlags.TRAILIER_TALES_COMPAT)
		);

		VibrationParticleVisibilityApi.registerVisibilityTest((data, user) -> !(user instanceof Crab.VibrationUser) && !(user instanceof IcicleBlockEntity.VibrationUser));
	}

	@Override
	public void init() {
		WWConstants.log("FrozenLib mod integration ran!", WWConstants.UNSTABLE_LOGGING);

		ServerLevelEvents.LOAD.register(
			(server, level) -> PlayerDamageTypeSounds.addDamageSound(
				level.damageSources().damageTypes.getValueOrThrow(DamageTypes.CACTUS),
				WWSounds.PLAYER_HURT_CACTUS,
				WWConstants.id("cactus")
			)
		);

		DripstoneDripApi.addWaterDrip(
			Blocks.WET_SPONGE,
			(level, pos, fluidInfo) -> {
				BlockState blockState = Blocks.SPONGE.defaultBlockState();
				level.setBlockAndUpdate(fluidInfo.pos(), blockState);
				Block.pushEntitiesUp(fluidInfo.sourceState(), blockState, level, fluidInfo.pos());
				level.gameEvent(GameEvent.BLOCK_CHANGE, fluidInfo.pos(), GameEvent.Context.of(blockState));
				level.levelEvent(LevelEvent.DRIPSTONE_DRIP, pos, 0);
			});
		DripstoneDripApi.addWaterDrip(
			Blocks.MUD,
			(level, pos, fluidInfo) -> {
				BlockState blockState = Blocks.CLAY.defaultBlockState();
				level.setBlockAndUpdate(fluidInfo.pos(), blockState);
				Block.pushEntitiesUp(fluidInfo.sourceState(), blockState, level, fluidInfo.pos());
				level.gameEvent(GameEvent.BLOCK_CHANGE, fluidInfo.pos(), GameEvent.Context.of(blockState));
				level.levelEvent(LevelEvent.DRIPSTONE_DRIP, pos, 0);
			}
		);

		BlockScheduledTicks.addToBlock(
			Blocks.DIRT,
			(state, level, pos, random) -> {
				if (DripstoneDripApi.getDripstoneFluid(level, pos) == Fluids.WATER) {
					level.setBlockAndUpdate(pos, Blocks.MUD.defaultBlockState());
				}
			}
		);

		BlockRandomTicks.addToBlock(Blocks.PEARLESCENT_FROGLIGHT, FroglightGoopBlock::growFromFroglight);
		BlockRandomTicks.addToBlock(Blocks.VERDANT_FROGLIGHT, FroglightGoopBlock::growFromFroglight);
		BlockRandomTicks.addToBlock(Blocks.OCHRE_FROGLIGHT, FroglightGoopBlock::growFromFroglight);

		BlockRandomTicks.addToBlock(
			Blocks.FIREFLY_BUSH,
			(state, level, pos, random) -> {
				if (!WWEntityConfig.FIREFLIES_NEED_BUSH.get() || !WWEntityConfig.SPAWN_FIREFLIES.get() || level.isClientSide()) return;
				if (level.getMaxLocalRawBrightness(pos) > FireflyBushBlock.FIREFLY_SPAWN_MAX_BRIGHTNESS_LEVEL) return;
				if (!level.hasNearbyAlivePlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 24D)) return;

				final Vec3 bushPos = Vec3.atCenterOf(pos);
				final List<Firefly> fireflies = level.getEntitiesOfClass(
					Firefly.class,
					AABB.ofSize(bushPos, 16D, 16D, 16D),
					EntitySelector.LIVING_ENTITY_STILL_ALIVE.and(EntitySelector.NO_SPECTATORS)
				);
				if (!fireflies.isEmpty()) {
					if (fireflies.size() >= 16) return;
					if (fireflies.stream().filter(firefly -> firefly.position().distanceTo(bushPos) <= 4D).toList().size() >= 4) return;
				}

				for (int i = 0; i < random.nextInt(3, 6); i++) {
					SpawnUtil.trySpawnMob(
						WWEntityTypes.FIREFLY,
						EntitySpawnReason.TRIGGERED,
						level,
						pos,
						3,
						3,
						3,
						Firefly.FIREFLY_SPAWN_STRATEGY,
						false
					);
				}
			}
		);

		BlockFrictionAPI.MODIFICATIONS.register(ctx -> {
			if (ctx.entity instanceof Penguin && ctx.state.is(WWBlockTags.PENGUIN_IGNORE_FRICTION)) ctx.friction = 0.6F;
		});

		BlockFrictionAPI.MODIFICATIONS.register(ctx -> {
			if (ctx.entity instanceof Tumbleweed tumbleweed && tumbleweed.isCannonball()) ctx.friction = 0.965F;
		});
	}
}
