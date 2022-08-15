package net.frozenblock.wilderwild.block.entity;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.frozenblock.wilderwild.misc.ClientMethodInteractionThingy;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
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
    public float lidX;
    public float prevLidX;
    public float lidZ;
    public float prevLidZ;
    public boolean hasLid;

    public boolean hasUpdated = false;

    public StoneChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(RegisterBlockEntities.STONE_CHEST, blockPos, blockState);
        this.hasLid = true;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.lidX = nbt.getFloat("lidX");
        this.prevLidX = nbt.getFloat("prevLidX");
        this.lidZ = nbt.getFloat("lidZ");
        this.prevLidZ = nbt.getFloat("prevLidZ");
        this.hasLid = nbt.getBoolean("hasLid");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putFloat("lidX", this.lidX);
        nbt.putFloat("prevLidX", this.prevLidX);
        nbt.putFloat("lidZ", this.lidZ);
        nbt.putFloat("prevLidZ", this.prevLidZ);
        nbt.putBoolean("hasLid", this.hasLid);
    }

    public float getLidX(float delta) {
        return MathHelper.lerp(delta, this.prevLidX, this.lidX);
    }

    public float getLidZ(float delta) {
        return MathHelper.lerp(delta, this.lidX, this.lidZ);
    }

    public static void serverStoneTick(World world, BlockPos pos, BlockState state, StoneChestBlockEntity blockEntity) {
        blockEntity.prevLidX = blockEntity.lidX;
        blockEntity.prevLidZ = blockEntity.lidZ;
    }

    public static void clientStoneTick(World world, BlockPos pos, BlockState state, StoneChestBlockEntity blockEntity) {
        blockEntity.prevLidX = blockEntity.lidX;
        blockEntity.prevLidZ = blockEntity.lidZ;
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
