package net.frozenblock.wilderwild.block.entity;

import net.frozenblock.wilderwild.misc.ClientMethodInteractionThingy;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class StoneChestBlockEntity extends ChestBlockEntity {
    public float lidPushProgress;
    public float prevLidPushProgress;

    public boolean hasUpdated = false;

    public StoneChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(RegisterBlockEntities.STONE_CHEST, blockPos, blockState);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.lidPushProgress = nbt.getFloat("lidPushProgress");
        this.prevLidPushProgress = nbt.getFloat("prevLidPushProgress");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putFloat("lidPushProgress", this.lidPushProgress);
        nbt.putFloat("prevLidPushProgress", this.prevLidPushProgress);
    }

    public float getPushProgress(float delta) {
        return MathHelper.lerp(delta, this.prevLidPushProgress, this.lidPushProgress);
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, StoneChestBlockEntity blockEntity) {
        if (!blockEntity.hasUpdated) {
            ClientMethodInteractionThingy.requestBlockEntitySync(pos, world);
            blockEntity.hasUpdated = true;
        }
    }

}
