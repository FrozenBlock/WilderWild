package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.misc.interfaces.ChestBlockEntityInterface;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ChestBlockEntity.class)
public class ChestBlockEntityMixin implements ChestBlockEntityInterface {

	@Unique int bubbleTicks;
	@Unique boolean canBubble = true;
	@Unique boolean hasJellyfish;

	@Unique
	private static BlockState playedSoundState;

	@Inject(at = @At("HEAD"), method = "playSound")
	private static void playSound(Level level, BlockPos pos, BlockState state, SoundEvent sound, CallbackInfo info) {
		playedSoundState = level.getBlockState(pos);
	}

	@ModifyArgs(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"), method = "playSound")
	private static void playSound(Args args) {
		if (playedSoundState != null && playedSoundState.hasProperty(BlockStateProperties.WATERLOGGED) && playedSoundState.getValue(BlockStateProperties.WATERLOGGED)) {
			SoundEvent sound = args.get(4);
			if (sound == SoundEvents.CHEST_OPEN) {
				args.set(4, RegisterSounds.BLOCK_CHEST_OPEN_UNDERWATER);
			} else if (sound == SoundEvents.CHEST_CLOSE) {
				args.set(4, RegisterSounds.BLOCK_CHEST_CLOSE_UNDERWATER);
			}
		}
	}

	@Unique
	@Override
	public void bubble(BlockState state) {
		ChestBlockEntity chest = ChestBlockEntity.class.cast(this);
		Level level = chest.getLevel();
		if (level != null) {
			BlockPos pos = chest.getBlockPos();
			if (this.canBubble && this.bubbleTicks <= 0 && state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED)) {
				this.bubbleTicks = 5;
				ChestBlockEntity otherChest = getOtherEntity(level, pos, state);
				if (otherChest != null) {
					((ChestBlockEntityInterface) otherChest).setBubbleTicks(5);
				}
			}
		}
	}

	@Unique
	@Override
	public void bubbleBurst(BlockState state) {
		ChestBlockEntity chest = ChestBlockEntity.class.cast(this);
		Level level = chest.getLevel();
		if (level instanceof ServerLevel server) {
			BlockPos pos = chest.getBlockPos();
			if (state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) && this.getCanBubble()) {
				server.sendParticles(ParticleTypes.BUBBLE, pos.getX() + 0.5, pos.getY() + 0.625, pos.getZ() + 0.5, server.random.nextInt(18, 25), 0.21875F, 0, 0.21875F, 0.25D);
			}
		}
	}

	@Inject(at = @At(value = "TAIL"), method = "lidAnimateTick", cancellable = true)
	private static void tick(Level level, BlockPos pos, BlockState state, ChestBlockEntity blockEntity, CallbackInfo info) {
		if (level instanceof ServerLevel server) {
			info.cancel();
			state = level.getBlockState(pos);
			ChestBlockEntityInterface chest = ((ChestBlockEntityInterface)blockEntity);
			if (!chest.getCanBubble()) {
				chest.setBubbleTicks(0);
			} else if (chest.getBubbleTick() > 0) {
				chest.setBubbleTicks(chest.getBubbleTick() - 1);
				double additionalX = 0;
				double additionalZ = 0;
				if (state.hasProperty(BlockStateProperties.CHEST_TYPE) && state.getValue(BlockStateProperties.CHEST_TYPE) != ChestType.SINGLE) {
					Direction direction = ChestBlock.getConnectedDirection(state);
					additionalX += (double)direction.getStepX() * 0.1;
					additionalZ += (double)direction.getStepZ() * 0.1;
				}
				server.sendParticles(ParticleTypes.BUBBLE, pos.getX() + 0.5 + additionalX, pos.getY() + 0.625, pos.getZ() + 0.5 + additionalZ, level.random.nextInt(4, 10), 0.21875F, 0, 0.21875F, 0.2D);
				if (chest.getBubbleTick() <= 0) {
					chest.setCanBubble(false);
				}
			}
		}
	}

	@Inject(at = @At(value = "TAIL"), method = "load")
	public void load(CompoundTag tag, CallbackInfo info) {
		this.bubbleTicks = tag.getInt("bubbleTicks");
		this.canBubble = tag.getBoolean("canBubble");
		this.hasJellyfish = tag.getBoolean("hasJellyfish");
	}

	@Inject(at = @At(value = "TAIL"), method = "saveAdditional")
	public void saveAdditional(CompoundTag tag, CallbackInfo info) {
		tag.putInt("bubbleTicks", this.bubbleTicks);
		tag.putBoolean("canBubble", this.canBubble);
		tag.putBoolean("hasJellyfish", this.hasJellyfish);
	}

	@Unique
	@Override
	public void releaseJellyfish(Level level, BlockState state, BlockPos pos) {
		ChestBlockEntity chest = ChestBlockEntity.class.cast(this);
		if (this.hasJellyfish && state.getValue(BlockStateProperties.WATERLOGGED)) {
			ChestBlockEntity otherChest = getOtherEntity(level, pos, state);
			this.hasJellyfish = false;
			if (otherChest != null) {
				((ChestBlockEntityInterface)otherChest).setHasJellyfish(false);
			}
			Jellyfish jellyfish = new Jellyfish(RegisterEntities.JELLYFISH, level);
			BlockPos chestPos = chest.getBlockPos();
			jellyfish.setVariantFromPos(level, chestPos);
			double additionalX = 0;
			double additionalZ = 0;
			if (state.hasProperty(BlockStateProperties.CHEST_TYPE) && state.getValue(BlockStateProperties.CHEST_TYPE) != ChestType.SINGLE) {
				Direction direction = ChestBlock.getConnectedDirection(state);
				additionalX += (double)direction.getStepX() * 0.25;
				additionalZ += (double)direction.getStepZ() * 0.25;
			}
			jellyfish.setPos(chestPos.getX() + 0.5 + additionalX, chestPos.getY() + 0.75, chestPos.getZ() + 0.5 + additionalZ);
			jellyfish.setDeltaMovement(0, 0.1 + level.random.nextDouble() * 0.07, 0);
			level.addFreshEntity(jellyfish);
		}
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
	@Override
	public boolean getCanBubble() {
		return this.canBubble;
	}

	@Unique
	@Override
	public void setCanBubble(boolean b) {
		this.canBubble = b;
	}

	@Unique
	@Override
	public void setBubbleTicks(int i) {
		this.bubbleTicks = i;
	}

	@Unique
	@Override
	public int getBubbleTick() {
		return this.bubbleTicks;
	}

	@Unique
	@Override
	public void setHasJellyfish(boolean b) {
		this.hasJellyfish = b;
	}

	@Unique
	@Override
	public boolean getHasJellyfish() {
		return this.hasJellyfish;
	}
}
