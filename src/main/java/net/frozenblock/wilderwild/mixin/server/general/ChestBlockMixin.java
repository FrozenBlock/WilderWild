package net.frozenblock.wilderwild.mixin.server.general;

import java.util.function.Supplier;
import net.frozenblock.wilderwild.misc.interfaces.ChestBlockEntityInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBlock.class)
public abstract class ChestBlockMixin extends AbstractChestBlock<ChestBlockEntity> {

	protected ChestBlockMixin(Properties properties, Supplier<BlockEntityType<? extends ChestBlockEntity>> blockEntityType) {
		super(properties, blockEntityType);
	}

	@Inject(at = @At(value = "RETURN"), method = "getStateForPlacement")
	public void getStateForPlacement(BlockPlaceContext context, CallbackInfoReturnable<BlockState> info) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		ChestBlockEntity otherChest = getOtherChest(level, pos, info.getReturnValue());
		if (otherChest != null) {
			if (level.getBlockEntity(pos) instanceof ChestBlockEntity chest) {
				((ChestBlockEntityInterface) chest).setCanBubble(((ChestBlockEntityInterface) otherChest).getCanBubble());
			}
		}
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/ChestBlock;getMenuProvider(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/MenuProvider;", shift = At.Shift.AFTER), method = "use")
	public void use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> info) {
		if (level.getBlockEntity(pos) instanceof ChestBlockEntity sourceChest) {
			ChestBlockEntity chest = getLeftEntity(level, pos, state, sourceChest);
			((ChestBlockEntityInterface) chest).bubble();
			((ChestBlockEntityInterface) chest).releaseJellyfish();
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
				if (level.getBlockEntity(currentPos) instanceof ChestBlockEntity chest) {
					((ChestBlockEntityInterface) chest).setHasJellyfish(false);
					((ChestBlockEntityInterface) otherChest).setHasJellyfish(false);
					if (!otherState.getValue(BlockStateProperties.WATERLOGGED)) {
						((ChestBlockEntityInterface) chest).setCanBubble(true);
						((ChestBlockEntityInterface) otherChest).setCanBubble(true);
					} else if (!((ChestBlockEntityInterface) otherChest).getCanBubble()) {
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
					((ChestBlockEntityInterface) chest).setHasJellyfish(false);
				}
			}
		}
	}

	@Inject(at = @At(value = "HEAD"), method = "getTicker", cancellable = true)
	public <T extends BlockEntity> void getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType, CallbackInfoReturnable<BlockEntityTicker<T>> info) {
		if (!level.isClientSide) {
			info.setReturnValue(createTickerHelper(blockEntityType, this.blockEntityType(), ChestBlockEntity::lidAnimateTick));
		}
	}

	@Shadow
	public BlockEntityType<? extends ChestBlockEntity> blockEntityType() {
		throw new AssertionError("Mixin injection failed - WilderWild ChestBlockMixin");
	}

	@Unique
	private static ChestBlockEntity getLeftEntity(Level level, BlockPos pos, BlockState state, ChestBlockEntity source) {
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
	private static ChestBlockEntity getOtherChest(LevelAccessor level, BlockPos pos, BlockState state) {
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
