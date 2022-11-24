package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.misc.interfaces.ChestBlockEntityInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//TODO: Move to FrozenBlock eventually
@Mixin(ChestBlockEntity.class)
public class ChestBlockEntityMixin implements ChestBlockEntityInterface {

	@Unique int bubbleTicks;
	@Unique boolean canBubble = true;

	@Override
	public void bubble() {
		if (this.canBubble) {
			this.bubbleTicks = 5;
			ChestBlockEntity chest = ChestBlockEntity.class.cast(this);
			ChestBlockEntity otherChest = getOtherEntity(chest.getLevel(), chest.getBlockPos(), chest.getBlockState());
			if (otherChest != null) {
				((ChestBlockEntityInterface)otherChest).setBubbleTicks(5);
			}
		}
	}

	@Inject(at = @At(value = "TAIL"), method = "lidAnimateTick", cancellable = true)
	private static void tick(Level level, BlockPos pos, BlockState state, ChestBlockEntity blockEntity, CallbackInfo info) {
		if (level instanceof ServerLevel server) {
			info.cancel();
			ChestBlockEntityInterface chest = ((ChestBlockEntityInterface) blockEntity);
			if (!chest.getCanBubble()) {
				chest.setBubbleTicks(0);
			} else if (chest.getBubbleTick() > 0) {
				chest.setBubbleTicks(chest.getBubbleTick() - 1);
				int random = level.random.nextInt(2, 5);
				server.sendParticles(ParticleTypes.BUBBLE, pos.getX() + 0.5, pos.getY() + 0.625, pos.getZ() + 0.5, random, 0.21875F, 0, 0.21875F, 0.05D);
			}
		}
	}

	@Inject(at = @At(value = "TAIL"), method = "load")
	public void load(CompoundTag tag, CallbackInfo info) {
		this.bubbleTicks = tag.getInt("bubbleTicks");
		this.canBubble = tag.getBoolean("canBubble");
	}

	@Inject(at = @At(value = "TAIL"), method = "saveAdditional")
	public void saveAdditional(CompoundTag tag, CallbackInfo info) {
		tag.putInt("bubbleTicks", this.bubbleTicks);
		tag.putBoolean("canBubble", this.canBubble);
	}

	@Override
	public boolean getCanBubble() {
		return canBubble;
	}

	@Override
	public void setCanBubble(boolean b) {
		canBubble = false;
	}

	@Override
	public void setBubbleTicks(int i) {
		this.bubbleTicks = i;
	}

	@Override
	public int getBubbleTick() {
		return this.bubbleTicks;
	}

	@Unique
	private static ChestBlockEntity getOtherEntity(Level level, BlockPos pos, BlockState state) {
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
}
