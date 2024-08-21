package net.frozenblock.wilderwild.mixin.snowlogging;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PointedDripstoneBlock.class)
public abstract class PointedDripstoneBlockMixin extends Block {

	public PointedDripstoneBlockMixin(Properties properties) {
		super(properties);
	}

	/**
	 * Checks if the player landed on a snow layer, and cancels the dripstone damage if so.
	 */
	@WrapOperation(method = "fallOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;causeFallDamage(FFLnet/minecraft/world/damagesource/DamageSource;)Z"))
	public boolean wilderWild$fallOn(
		Entity entity, float modifiedFallDistance, float damageMultiplier, DamageSource damageSource, Operation<Boolean> original,
		Level world, BlockState state, BlockPos pos, Entity entityOriginal, float fallDistanceOriginal
	) {
		if (SnowloggingUtils.isSnowlogged(state)) {
			double entityY = entityOriginal.position().y();
			VoxelShape snowLayerShape = SnowloggingUtils.getSnowEquivalent(state).getShape(world, pos);
			if (entityY <= (snowLayerShape.max(Direction.Axis.Y)) + pos.getY()) {
				super.fallOn(world, state, pos, entity, fallDistanceOriginal);
				return false;
			}
		}
		return original.call(entity, modifiedFallDistance, damageMultiplier, damageSource);
	}

	@Unique
	@Override
	protected boolean isRandomlyTicking(BlockState state) {
		return super.isRandomlyTicking(state) || SnowloggingUtils.isSnowlogged(state);
	}

	@Inject(method = "createBlockStateDefinition", at = @At(value = "TAIL"))
	public void wilderWild$createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
		if (BlockConfig.get().snowlogging.snowlogging) builder.add(SnowloggingUtils.SNOW_LAYERS);
	}

	@ModifyReturnValue(method = "getStateForPlacement", at = @At("RETURN"))
	public BlockState wilderWild$getStateForPlacement(BlockState original, BlockPlaceContext context) {
		return SnowloggingUtils.getSnowPlacementState(original, context);
	}
}
