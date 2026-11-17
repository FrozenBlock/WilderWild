package net.frozenblock.wilderwild.block.dispenser;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import org.slf4j.Logger;

public class SpawnTumbleweedDispenseBehavior implements DispenseBehavior {
	private static final Logger LOGGER = LogUtils.getLogger();
	public static final SpawnTumbleweedDispenseBehavior INSTANCE = new SpawnTumbleweedDispenseBehavior();
	public static final MapCodec<SpawnTumbleweedDispenseBehavior> MAP_CODEC = MapCodec.unit(INSTANCE);

	@Override
	public DispenseResult execute(BlockSource source, ItemStack dispensed) {
		final Direction direction = source.state().getValue(DispenserBlock.FACING);

		try {
			final Entity spawned = WWEntityTypes.TUMBLEWEED.get().spawn(
				source.level(),
				dispensed,
				null,
				source.pos().relative(direction),
				EntitySpawnReason.DISPENSER,
				direction != Direction.UP,
				false
			);
			if (spawned == null) return DispenseBehavior.DispenseResult.failure();
		} catch (Exception exception) {
			LOGGER.error("Error while dispensing tumbleweed from dispenser at {}", source.pos(), exception);
			return DispenseBehavior.DispenseResult.failure();
		}

		dispensed.shrink(1);
		source.level().gameEvent(null, GameEvent.ENTITY_PLACE, source.pos());
		return DispenseBehavior.DispenseResult.success(dispensed);
	}

	@Override
	public MapCodec<SpawnTumbleweedDispenseBehavior> codec() {
		return MAP_CODEC;
	}
}
