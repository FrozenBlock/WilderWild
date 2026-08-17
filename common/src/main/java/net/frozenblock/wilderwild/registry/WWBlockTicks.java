package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.block.api.dripstone.DripstoneDripApi;
import net.frozenblock.lib.block.api.tick.BlockTickEvents;
import net.frozenblock.wilderwild.block.FroglightGoopBlock;
import net.frozenblock.wilderwild.block.state.properties.FroglightType;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.minecraft.util.SpawnUtil;
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
import java.util.List;

public final class WWBlockTicks {

	public static void setup() {
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

		BlockTickEvents.TICK.register(((state, level, pos, random) -> {
			if (!state.is(Blocks.DIRT)) return;
			if (DripstoneDripApi.getDripstoneFluid(level, pos) == Fluids.WATER) level.setBlockAndUpdate(pos, Blocks.MUD.defaultBlockState());
		}));

		BlockTickEvents.RANDOM_TICK.register((state, level, pos, random) -> {
			FroglightType.getFromBaseBlock(state.getBlock()).ifPresent(froglightType -> {
				FroglightGoopBlock.growFromFroglight(froglightType, level, pos, random);
			});
		});

		BlockTickEvents.RANDOM_TICK.register((state, level, pos, random) -> {
			if (!state.is(Blocks.FIREFLY_BUSH)) return;

			if (!WWEntityConfig.FIREFLIES_NEED_BUSH.get() || !WWEntityConfig.SPAWN_FIREFLIES.get() || level.isClientSide()) return;
			if (level.getMaxLocalRawBrightness(pos) > FireflyBushBlock.FIREFLY_SPAWN_MAX_BRIGHTNESS_LEVEL) return;
			if (!level.hasNearbyAlivePlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 24D)) return;

			final Vec3 bushPos = Vec3.atCenterOf(pos);
			final List<net.frozenblock.wilderwild.entity.Firefly> fireflies = level.getEntitiesOfClass(
				net.frozenblock.wilderwild.entity.Firefly.class,
				AABB.ofSize(bushPos, 16D, 16D, 16D),
				EntitySelector.LIVING_ENTITY_STILL_ALIVE.and(EntitySelector.NO_SPECTATORS)
			);
			if (!fireflies.isEmpty()) {
				if (fireflies.size() >= 16) return;
				if (fireflies.stream().filter(firefly -> firefly.position().distanceTo(bushPos) <= 4D).toList().size() >= 4) return;
			}

			for (int i = 0; i < random.nextInt(3, 6); i++) {
				SpawnUtil.trySpawnMob(
					WWEntityTypes.FIREFLY.get(),
					EntitySpawnReason.TRIGGERED,
					level,
					pos,
					3,
					3,
					3,
					net.frozenblock.wilderwild.entity.Firefly.FIREFLY_SPAWN_STRATEGY,
					false
				);
			}
		});
	}

	private WWBlockTicks() {}
}
