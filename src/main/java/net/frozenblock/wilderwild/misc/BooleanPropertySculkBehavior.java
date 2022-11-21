package net.frozenblock.wilderwild.misc;

import java.util.Collection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class BooleanPropertySculkBehavior implements SculkBehaviour {
	public final BooleanProperty changingProperty;
	public final boolean propertySetValue;

	public BooleanPropertySculkBehavior(BooleanProperty changingProperty, boolean propertySetValue) {
		this.changingProperty = changingProperty;
		this.propertySetValue = propertySetValue;
	}

	@Override
	public int attemptUseCharge(SculkSpreader.ChargeCursor cursor, LevelAccessor level, BlockPos catalystPos, RandomSource random, SculkSpreader spreadManager, boolean shouldConvertToBlock) {
		BlockState placementState = null;
		BlockPos cursorPos = cursor.getPos();
		BlockState currentState = level.getBlockState(cursorPos);
		if (currentState.hasProperty(this.changingProperty)) {
			if (currentState.getValue(this.changingProperty) != this.propertySetValue) {
				placementState = currentState.setValue(this.changingProperty, this.propertySetValue);
			}
		}

		if (placementState != null) {
			level.setBlock(cursorPos, placementState, 3);
			return cursor.getCharge() - 1;
		}
		return random.nextInt(spreadManager.chargeDecayRate()) == 0 ? Mth.floor((float) cursor.getCharge() * 0.5F) : cursor.getCharge();
	}

	@Override
	public boolean attemptSpreadVein(LevelAccessor level, BlockPos pos, BlockState state, @Nullable Collection<Direction> directions, boolean markForPostProcessing) {
		BlockState placementState = null;
		BlockState currentState = level.getBlockState(pos);
		if (currentState.hasProperty(this.changingProperty)) {
			if (currentState.getValue(this.changingProperty) != this.propertySetValue) {
				placementState = currentState.setValue(this.changingProperty, this.propertySetValue);
			}
		}

		if (placementState != null) {
			level.setBlock(pos, placementState, 3);
			return true;
		}
		return false;
	}
}
