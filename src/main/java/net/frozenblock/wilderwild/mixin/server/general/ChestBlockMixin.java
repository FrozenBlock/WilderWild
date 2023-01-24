package net.frozenblock.wilderwild.mixin.server.general;

import java.util.function.Supplier;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.interfaces.ChestBlockEntityInterface;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
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

	@Unique
	@Nullable
	private ResourceLocation wilderWild$lootTable;

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;openMenu(Lnet/minecraft/world/MenuProvider;)Ljava/util/OptionalInt;", shift = At.Shift.BEFORE), method = "use")
	public void wilderWild$useBeforeOpenMenu(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> info) {
		if (level.getBlockEntity(pos) instanceof ChestBlockEntity sourceChest) {
			WilderSharedConstants.log("Z", WilderSharedConstants.UNSTABLE_LOGGING);
			if (sourceChest.lootTable != null && level.getServer() != null) {
				WilderSharedConstants.log("A", WilderSharedConstants.UNSTABLE_LOGGING);
				this.wilderWild$lootTable = new ResourceLocation(sourceChest.lootTable.getNamespace(), sourceChest.lootTable.getPath());
				WilderSharedConstants.log("B", WilderSharedConstants.UNSTABLE_LOGGING);
			} else {
				this.wilderWild$lootTable = null;
			}
		}
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;openMenu(Lnet/minecraft/world/MenuProvider;)Ljava/util/OptionalInt;", shift = At.Shift.AFTER), method = "use")
	public void wilderWild$useAfterOpenMenu(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> info) {
		WilderSharedConstants.log("C", WilderSharedConstants.UNSTABLE_LOGGING);
	}

	@Inject(at = @At("RETURN"), method = "use")
	public void wilderWild$useReturn(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> info) {
		WilderSharedConstants.log("BEFORE", WilderSharedConstants.UNSTABLE_LOGGING);
		if (info.getReturnValue() == InteractionResult.CONSUME && level.getBlockEntity(pos) instanceof ChestBlockEntity sourceChest) {
			WilderSharedConstants.log("GEESGES", WilderSharedConstants.UNSTABLE_LOGGING);
			if (this.wilderWild$lootTable != null) {
				WilderSharedConstants.log("LOOT TABLE: " + this.wilderWild$lootTable, WilderSharedConstants.UNSTABLE_LOGGING);
				if (state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED)) {
					String lootPath = this.wilderWild$lootTable.getPath().toLowerCase();
					if (lootPath.contains("shipwreck") && level.random.nextBoolean()) {
						WilderSharedConstants.log("MAKING JELLY", WilderSharedConstants.UNSTABLE_LOGGING);
						Jellyfish jellyfish = new Jellyfish(RegisterEntities.JELLYFISH, level);
						jellyfish.setVariantFromPos(level, pos);
						double additionalX = 0;
						double additionalZ = 0;
						if (state.hasProperty(BlockStateProperties.CHEST_TYPE) && state.getValue(BlockStateProperties.CHEST_TYPE) != ChestType.SINGLE) {
							Direction direction = ChestBlock.getConnectedDirection(state);
							additionalX += (double) direction.getStepX() * 0.25;
							additionalZ += (double) direction.getStepZ() * 0.25;
						}
						jellyfish.setPos(pos.getX() + 0.5 + additionalX, pos.getY() + 0.75, pos.getZ() + 0.5 + additionalZ);
						jellyfish.setDeltaMovement(0, 0.1 + level.random.nextDouble() * 0.07, 0);
						level.addFreshEntity(jellyfish);
						WilderSharedConstants.log("SPAWNED JELLY", WilderSharedConstants.UNSTABLE_LOGGING);
					} else {
						WilderSharedConstants.log("NO JELLY", WilderSharedConstants.UNSTABLE_LOGGING);
					}
				}
			}
			((ChestBlockEntityInterface) sourceChest).bubble(level, pos, state);
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
		}
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
