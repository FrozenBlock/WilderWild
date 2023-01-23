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
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBlock.class)
public abstract class ChestBlockMixin extends AbstractChestBlock<ChestBlockEntity> {

	protected ChestBlockMixin(Properties properties, Supplier<BlockEntityType<? extends ChestBlockEntity>> blockEntityType) {
		super(properties, blockEntityType);
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;openMenu(Lnet/minecraft/world/MenuProvider;)Ljava/util/OptionalInt;", shift = At.Shift.AFTER), method = "use")
	public void use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> info) {
		if (level.getBlockEntity(pos) instanceof ChestBlockEntity sourceChest) {
			((ChestBlockEntityInterface) sourceChest).bubble(state);
			ChestBlockEntity otherChest = getOtherChest(level, pos, state);
			if (otherChest != null) {
				((ChestBlockEntityInterface) sourceChest).syncBubble(sourceChest, otherChest);
			}
		}
	}

	@Inject(at = @At(value = "RETURN"), method = "updateShape")
	public void updateShape(BlockState blockStateUnneeded, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> info) {
		BlockState state = info.getReturnValue();
		ChestBlockEntity otherChest = getOtherChest(level, currentPos, state);
		if (level.getBlockEntity(currentPos) instanceof ChestBlockEntity chest) {
			if (otherChest != null) {
				BlockState otherState = level.getBlockState(otherChest.getBlockPos());
				boolean wasLogged = blockStateUnneeded.getValue(BlockStateProperties.WATERLOGGED);
				if (wasLogged != state.getValue(BlockStateProperties.WATERLOGGED) && wasLogged) {
					if (!otherState.getValue(BlockStateProperties.WATERLOGGED)) {
						((ChestBlockEntityInterface) chest).setCanBubble(true);
						((ChestBlockEntityInterface) otherChest).setCanBubble(true);
					} else if (!((ChestBlockEntityInterface) otherChest).getCanBubble()) {
						((ChestBlockEntityInterface) chest).setCanBubble(false);
						((ChestBlockEntityInterface) otherChest).setCanBubble(false);
					}
				}
			} else {
				boolean wasLogged = blockStateUnneeded.getValue(BlockStateProperties.WATERLOGGED);
				if (wasLogged != state.getValue(BlockStateProperties.WATERLOGGED) && wasLogged) {
					((ChestBlockEntityInterface) chest).setCanBubble(true);
				}
			}
			if (otherChest != null && level.getBlockEntity(currentPos) instanceof ChestBlockEntity sourceChest) {
				((ChestBlockEntityInterface) sourceChest).syncBubble(sourceChest, otherChest);
			}
		}
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/Containers;dropContents(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/Container;)V", shift = At.Shift.BEFORE), method = "onRemove")
	public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean isMoving, CallbackInfo info) {
		if (level.getBlockEntity(pos) instanceof ChestBlockEntity chestBlockEntity) {
			((ChestBlockEntityInterface)chestBlockEntity).bubbleBurst(state);
			ChestBlockEntity otherChest = getOtherChest(level, pos, state);
			if (otherChest != null) {
				((ChestBlockEntityInterface) chestBlockEntity).syncBubble(chestBlockEntity, otherChest);
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
