package net.frozenblock.wilderwild.block.entity;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.frozenblock.wilderwild.misc.ClientMethodInteractionThingy;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class StoneChestBlockEntity extends ChestBlockEntity {
    public float openProgress;
    public float prevOpenProgress;
    public int stillLidTicks;
    public boolean hasLid;

    public boolean hasUpdated = false;

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
        this.hasLid = nbt.getBoolean("hasLid");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putFloat("openProgress", this.openProgress);
        nbt.putFloat("prevOpenProgress", this.prevOpenProgress);
        nbt.putInt("stillLidTicks", this.stillLidTicks);
        nbt.putBoolean("hasLid", this.hasLid);
    }

    public float getOpenProgress(float delta) {
        return MathHelper.lerp(delta, this.prevOpenProgress, this.openProgress);
    }

    public static void serverStoneTick(World world, BlockPos pos, BlockState state, StoneChestBlockEntity blockEntity) {
        blockEntity.prevOpenProgress = blockEntity.openProgress;
        if (blockEntity.stillLidTicks > 0) {
            blockEntity.stillLidTicks -= 1;
        } else if (blockEntity.openProgress > 0F) {
            blockEntity.openProgress = Math.min(0F, blockEntity.openProgress) - 0.05F;
        }
    }

    public static void clientStoneTick(World world, BlockPos pos, BlockState state, StoneChestBlockEntity blockEntity) {
        blockEntity.prevOpenProgress = blockEntity.openProgress;
        if (blockEntity.stillLidTicks > 0) {
            blockEntity.stillLidTicks -= 1;
        } else if (blockEntity.openProgress > 0F) {
            blockEntity.openProgress = Math.min(0F, blockEntity.openProgress) - 0.05F;
        }
        if (!blockEntity.hasUpdated) {
            ClientMethodInteractionThingy.requestBlockEntitySync(pos, world);
            blockEntity.hasUpdated = true;
        }
    }

    public void updateSync() {
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

}
