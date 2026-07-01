package net.frozenblock.wilderwild.mixin.fabric;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.fabricmc.fabric.impl.registry.sync.trackers.StateIdTracker;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.ApiStatus;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@ApiStatus.Experimental
@Mixin(StateIdTracker.class)
public class StateIdTrackerMixin<T, S>  {

	@Shadow
	@Final
	private Registry<T> registry;

	@ModifyExpressionValue(
		method = "recalcStateMap",
		at = @At(
			value = "INVOKE",
			target = "Ljava/util/function/Function;apply(Ljava/lang/Object;)Ljava/lang/Object;"
		)
	)
	private Object wilderWild$stripIllegalBlockStates(Object original) {
		if (!this.registry.key().equals(Registries.BLOCK)) return original;

		if (!(original instanceof Collection<?>)) {
			WWConstants.error("NOT A COLLECTION WTH?????", WWConstants.UNSTABLE_LOGGING);
			return original;
		}

		final List<S> finalCollection = new ArrayList<>();
		if (!WWConstants.SERVER_HAS_WIILDER_WILD_BLOCKS) {
			for (S state : (Collection<S>) original) {
				if (!(state instanceof BlockState blockState)) return original;
				if (blockState.getValueOrElse(WWBlockStateProperties.TERMITE_EDIBLE, false)) continue;
				if (blockState.getValueOrElse(WWBlockStateProperties.SNOW_LAYERS, 0) > 0) continue;
				if (blockState.is(Blocks.REINFORCED_DEEPSLATE) && blockState.getValueOrElse(BlockStateProperties.AXIS, Direction.Axis.Y) != Direction.Axis.Y) continue;
				finalCollection.add(state);
			}
		}

		return finalCollection;
	}
}
