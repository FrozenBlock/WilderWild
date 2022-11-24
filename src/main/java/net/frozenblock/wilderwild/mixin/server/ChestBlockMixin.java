package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.misc.interfaces.ChestBlockEntityInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//TODO: Move to FrozenBlock eventually
@Mixin(ChestBlock.class)
public class ChestBlockMixin {

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/ChestBlock;getMenuProvider(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/MenuProvider;", shift = At.Shift.AFTER), method = "use")
	public void use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> info) {
		if (level.getBlockEntity(pos) instanceof ChestBlockEntity sourceChest) {
			ChestBlockEntity chest = getLeftEntity(level, pos, state, sourceChest);
			((ChestBlockEntityInterface) chest).bubble();
		}
	}

	@Inject(at = @At(value = "RETURN"), method = "updateShape")
	public void updateShape(BlockState blockStateUnneeded, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> info) {
		BlockState state = info.getReturnValue();
		ChestBlockEntity otherChest = getOtherChest(level, currentPos, state);
		if (otherChest != null) {
			BlockState otherState = otherChest.getBlockState();
			boolean wasLogged = blockStateUnneeded.getValue(BlockStateProperties.WATERLOGGED);
			if (wasLogged != state.getValue(BlockStateProperties.WATERLOGGED) && wasLogged) {
				if (!otherState.getValue(BlockStateProperties.WATERLOGGED)) {
					if (level.getBlockEntity(currentPos) instanceof ChestBlockEntity chest) {
						((ChestBlockEntityInterface) chest).setCanBubble(true);
						((ChestBlockEntityInterface) otherChest).setCanBubble(true);
					}
				} else {
					if (level.getBlockEntity(currentPos) instanceof ChestBlockEntity chest) {
						((ChestBlockEntityInterface) chest).setCanBubble(false);
						((ChestBlockEntityInterface) otherChest).setCanBubble(false);
					}
				}
			}
		} else {
			boolean wasLogged = blockStateUnneeded.getValue(BlockStateProperties.WATERLOGGED);
			if (level.getBlockEntity(currentPos) instanceof ChestBlockEntity chest) {
				if (wasLogged != state.getValue(BlockStateProperties.WATERLOGGED) && wasLogged) {
					((ChestBlockEntityInterface) chest).setCanBubble(true);
				}
			}
		}
	}

	@Unique
	public static ChestBlockEntity getLeftEntity(Level level, BlockPos pos, BlockState state, ChestBlockEntity source) {
		ChestType chestType = state.getValue(ChestBlock.TYPE);
		if (chestType == ChestType.SINGLE) {
			return source;
		}
		double d = pos.getX();
		double e = pos.getY();
		double f = pos.getZ();
		if (chestType == ChestType.RIGHT) {
			Direction direction = ChestBlock.getConnectedDirection(state);
			d += direction.getStepX();
			f += direction.getStepZ();
		} else if (chestType == ChestType.LEFT) {
			return source;
		}
		BlockEntity be = level.getBlockEntity(new BlockPos(d, e, f));
		ChestBlockEntity entity = null;
		if (be instanceof ChestBlockEntity chest) {
			entity = chest;
		}
		return entity;
	}

	@Unique
	public static ChestBlockEntity getOtherChest(LevelAccessor level, BlockPos pos, BlockState state) {
		ChestType chestType = state.getValue(ChestBlock.TYPE);
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();
		if (chestType == ChestType.RIGHT) {
			Direction direction = ChestBlock.getConnectedDirection(state);
			x += direction.getStepX();
			z += direction.getStepZ();
		} else if (chestType == ChestType.LEFT) {
			Direction direction = ChestBlock.getConnectedDirection(state);
			x += direction.getStepX();
			z += direction.getStepZ();
		} else {
			return null;
		}
		BlockPos newPos = new BlockPos(x, y, z);
		BlockEntity be = level.getBlockEntity(newPos);
		ChestBlockEntity entity = null;
		if (be instanceof ChestBlockEntity chest) {
			entity = chest;
		}
		return entity;
	}

}
