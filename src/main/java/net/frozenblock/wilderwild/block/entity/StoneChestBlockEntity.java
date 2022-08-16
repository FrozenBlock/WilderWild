package net.frozenblock.wilderwild.block.entity;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.frozenblock.wilderwild.misc.ClientMethodInteractionThingy;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.block.enums.ChestType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class StoneChestBlockEntity extends ChestBlockEntity {
    public float openProgress;
    public float prevOpenProgress;
    public int stillLidTicks;
    public int cooldownTicks;
    public boolean closing;
    public boolean hasLid;

    public boolean hasUpdated = false;
    public boolean shouldSkip = false;

    public StoneChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(RegisterBlockEntities.STONE_CHEST, blockPos, blockState);
        this.hasLid = true;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.openProgress = nbt.getFloat("openProgress");
        this.prevOpenProgress = nbt.getFloat("prevOpenProgress");
        this.stillLidTicks = nbt.getInt("stillLidTicks");
        this.cooldownTicks = nbt.getInt("cooldownTicks");
        this.hasLid = nbt.getBoolean("hasLid");
        this.closing = nbt.getBoolean("closing");
        this.shouldSkip = nbt.getBoolean("shouldSkip");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putFloat("openProgress", this.openProgress);
        nbt.putFloat("prevOpenProgress", this.prevOpenProgress);
        nbt.putInt("stillLidTicks", this.stillLidTicks);
        nbt.putInt("cooldownTicks", this.cooldownTicks);
        nbt.putBoolean("hasLid", this.hasLid);
        nbt.putBoolean("closing", this.closing);
        nbt.putBoolean("shouldSkip", this.shouldSkip);
    }

    public float getOpenProgress(float delta) {
        return MathHelper.lerp(delta, this.prevOpenProgress, this.openProgress);
    }

    public static void serverStoneTick(World world, BlockPos pos, BlockState state, StoneChestBlockEntity blockEntity) {
        if (!blockEntity.shouldSkip) {
            if (blockEntity.cooldownTicks > 0) {
                --blockEntity.cooldownTicks;
            }
            blockEntity.prevOpenProgress = blockEntity.openProgress;
            if (blockEntity.stillLidTicks > 0) {
                blockEntity.stillLidTicks -= 1;
            } else if (blockEntity.openProgress > 0F) {
                world.emitGameEvent(null, GameEvent.CONTAINER_CLOSE, pos);
                blockEntity.openProgress = Math.max(0F, blockEntity.openProgress - 0.0425F);
                if (!blockEntity.closing) {
                    blockEntity.closing = true;
                    playSound(world, pos, state, RegisterSounds.BLOCK_STONE_CHEST_CLOSE_START, 0.3F);
                }
                if (blockEntity.openProgress <= 0F) {
                    playSound(world, pos, state, RegisterSounds.BLOCK_STONE_CHEST_SLAM, 0.6F);
                    blockEntity.closing = false;
                    blockEntity.cooldownTicks = 15;
                }
            }
            if (isLeft(state)) {
                blockEntity.syncLidValues(world, pos, state);
            }
        }
        blockEntity.shouldSkip = false;
    }

    public static void clientStoneTick(World world, BlockPos pos, BlockState state, StoneChestBlockEntity blockEntity) {
        if (!blockEntity.shouldSkip) {
            if (blockEntity.cooldownTicks > 0) {
                --blockEntity.cooldownTicks;
            }
            blockEntity.prevOpenProgress = blockEntity.openProgress;
            if (blockEntity.stillLidTicks > 0) {
                blockEntity.stillLidTicks -= 1;
            } else if (blockEntity.openProgress > 0F) {
                blockEntity.closing = true;
                blockEntity.openProgress = Math.max(0F, blockEntity.openProgress - 0.0425F);
                if (blockEntity.openProgress <= 0F) {
                    blockEntity.closing = false;
                    blockEntity.cooldownTicks = 15;
                }
            }
        }
        blockEntity.shouldSkip = false;
        if (!blockEntity.hasUpdated) {
            ClientMethodInteractionThingy.requestBlockEntitySync(pos, world);
            blockEntity.hasUpdated = true;
        }
        if (isLeft(state)) {
            blockEntity.syncLidValues(world, pos, state);
        }
    }

    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        }
        return !(player.squaredDistanceTo((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5) > 64.0) && ((!this.closing && this.openProgress >= 0.3) || !this.hasLid);
    }

    public void syncLidValues(World world, BlockPos pos, BlockState state) {
        StoneChestBlockEntity stoneChest = getOtherEntity(world, pos, state);
        if (stoneChest != null) {
            stoneChest.openProgress = this.openProgress;
            stoneChest.prevOpenProgress = this.prevOpenProgress;
            stoneChest.stillLidTicks = this.stillLidTicks;
            stoneChest.cooldownTicks = this.cooldownTicks;
            stoneChest.hasLid = this.hasLid;
            stoneChest.shouldSkip = true;
            stoneChest.closing = this.closing;
        }
    }

    public void updateSync() {
        StoneChestBlockEntity stoneChest = getOtherEntity(world, pos, this.getCachedState());
        if (stoneChest != null) {
            stoneChest.openProgress = this.openProgress;
            stoneChest.prevOpenProgress = this.prevOpenProgress;
            stoneChest.stillLidTicks = this.stillLidTicks;
            stoneChest.cooldownTicks = this.cooldownTicks;
            stoneChest.hasLid = this.hasLid;
            stoneChest.shouldSkip = true;
            stoneChest.closing = this.closing;
            for (ServerPlayerEntity player : PlayerLookup.tracking(stoneChest)) {
                player.networkHandler.sendPacket(stoneChest.toUpdatePacket());
            }
        }
        for (ServerPlayerEntity player : PlayerLookup.tracking(this)) {
            player.networkHandler.sendPacket(this.toUpdatePacket());
        }
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    public StoneChestBlockEntity getOtherEntity(World world, BlockPos pos, BlockState state) {
        ChestType chestType = state.get(ChestBlock.CHEST_TYPE);
        double d = pos.getX();
        double e = pos.getY();
        double f = pos.getZ();
        if (chestType == ChestType.RIGHT) {
            Direction direction = ChestBlock.getFacing(state);
            d += direction.getOffsetX();
            f += direction.getOffsetZ();
        } else if (chestType == ChestType.LEFT) {
            Direction direction = ChestBlock.getFacing(state);
            d += direction.getOffsetX();
            f += direction.getOffsetZ();
        } else {
            return null;
        }
        BlockPos newPos = new BlockPos(d, e, f);
        BlockEntity be = world.getBlockEntity(newPos);
        StoneChestBlockEntity entity = null;
        if (be instanceof StoneChestBlockEntity stone) {
            entity = stone;
        }
        return entity;
    }

    public StoneChestBlockEntity getLeftEntity(World world, BlockPos pos, BlockState state, StoneChestBlockEntity source) {
        ChestType chestType = state.get(ChestBlock.CHEST_TYPE);
        if (chestType == ChestType.SINGLE) {
            return source;
        }
        double d = pos.getX();
        double e = pos.getY();
        double f = pos.getZ();
        if (chestType == ChestType.RIGHT) {
            Direction direction = ChestBlock.getFacing(state);
            d += direction.getOffsetX();
            f += direction.getOffsetZ();
        } else if (chestType == ChestType.LEFT) {
            return source;
        }
        BlockPos newPos = new BlockPos(d, e, f);
        BlockEntity be = world.getBlockEntity(newPos);
        StoneChestBlockEntity entity = null;
        if (be instanceof StoneChestBlockEntity stone) {
            entity = stone;
        }
        return entity;
    }

    public static boolean isLeft(BlockState state) {
        ChestType chestType = state.get(ChestBlock.CHEST_TYPE);
        return chestType == ChestType.LEFT;
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.stone_chest");
    }

    @Override
    public void onOpen(PlayerEntity player) {
    }

    @Override
    public void onClose(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stoneStateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    private final ViewerCountManager stoneStateManager = new ViewerCountManager() {

        @Override
        protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
            //StoneChestBlockEntity.playSound(world, pos, state, RegisterSounds.BLOCK_STONE_CHEST_SEARCH);
        }

        @Override
        protected void onContainerClose(World world, BlockPos pos, BlockState state) {
        }

        @Override
        protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
            StoneChestBlockEntity.this.onInvOpenOrClose(world, pos, state, oldViewerCount, newViewerCount);
        }

        @Override
        protected boolean isPlayerViewing(PlayerEntity player) {
            if (player.currentScreenHandler instanceof GenericContainerScreenHandler) {
                Inventory inventory = ((GenericContainerScreenHandler) player.currentScreenHandler).getInventory();
                return inventory == StoneChestBlockEntity.this || inventory instanceof DoubleInventory && ((DoubleInventory) inventory).isPart(StoneChestBlockEntity.this);
            }
            return false;
        }
    };

    public static void playSound(World world, BlockPos pos, BlockState state, SoundEvent soundEvent, float volume) {
        ChestType chestType = state.get(ChestBlock.CHEST_TYPE);
        double d = (double) pos.getX() + 0.5;
        double e = (double) pos.getY() + 0.5;
        double f = (double) pos.getZ() + 0.5;
        if (chestType == ChestType.RIGHT) {
            Direction direction = ChestBlock.getFacing(state);
            d += (double) direction.getOffsetX() * 0.5;
            f += (double) direction.getOffsetZ() * 0.5;
        } else if (chestType == ChestType.LEFT) {
            Direction direction = ChestBlock.getFacing(state);
            d -= (double) direction.getOffsetX() * 0.5;
            f -= (double) direction.getOffsetZ() * 0.5;
        }
        world.playSound(null, d, e, f, soundEvent, SoundCategory.BLOCKS, volume, world.random.nextFloat() * 0.1f + 0.9f);
    }

}
