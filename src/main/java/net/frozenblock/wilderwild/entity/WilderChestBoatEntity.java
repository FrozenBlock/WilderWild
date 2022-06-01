package net.frozenblock.wilderwild.entity;

import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.RideableInventory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.vehicle.VehicleInventory;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class WilderChestBoatEntity extends WilderBoatEntity implements RideableInventory, VehicleInventory {
    private static final int INVENTORY_SIZE = 27;
    private DefaultedList<ItemStack> inventory;
    @Nullable
    private Identifier lootTableId;
    private long lootTableSeed;

    public WilderChestBoatEntity(EntityType<? extends WilderBoatEntity> entityType, World world) {
        super(entityType, world);
        this.inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
    }

    public WilderChestBoatEntity(World world, double d, double e, double f) {
        this(RegisterEntities.BAOBAB_CHEST_BOAT, world);
        this.setPosition(d, e, f);
        this.prevX = d;
        this.prevY = e;
        this.prevZ = f;
    }

    protected float getPassengerHorizontalOffset() {
        return 0.15F;
    }

    protected int getMaxPassengers() {
        return 1;
    }

    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.writeInventoryToNbt(nbt);
    }

    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.readInventoryFromNbt(nbt);
    }

    public void dropItems(DamageSource source) {
        super.dropItems(source);
        this.onBroken(source, this.world, this);
    }

    public void remove(RemovalReason reason) {
        if (!this.world.isClient && reason.shouldDestroy()) {
            ItemScatterer.spawn(this.world, this, this);
        }

        super.remove(reason);
    }

    public ActionResult interact(PlayerEntity player, Hand hand) {
        return this.canAddPassenger(player) && !player.shouldCancelInteraction() ? super.interact(player, hand) : this.open(this::emitGameEvent, player);
    }

    public void openInventory(PlayerEntity player) {
        player.openHandledScreen(this);
        if (!player.world.isClient) {
            this.emitGameEvent(GameEvent.CONTAINER_OPEN, player);
            PiglinBrain.onGuardedBlockInteracted(player, true);
        }

    }

    public Item asItem() {
        Item var10000;
        switch(this.getBoatType()) {
            default:
                var10000 = RegisterItems.BAOBAB_CHEST_BOAT_ITEM;
        }

        return var10000;
    }

    public void clear() {
        this.clearInventory();
    }

    public int size() {
        return 27;
    }

    public ItemStack getStack(int slot) {
        return this.getInventoryStack(slot);
    }

    public ItemStack removeStack(int slot, int amount) {
        return this.removeInventoryStack(slot, amount);
    }

    public ItemStack removeStack(int slot) {
        return this.removeInventoryStack(slot);
    }

    public void setStack(int slot, ItemStack stack) {
        this.setInventoryStack(slot, stack);
    }

    public StackReference getStackReference(int mappedIndex) {
        return this.getInventoryStackReference(mappedIndex);
    }

    public void markDirty() {
    }

    public boolean canPlayerUse(PlayerEntity player) {
        return this.canPlayerAccess(player);
    }

    @Nullable
    public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        if (this.lootTableId != null && playerEntity.isSpectator()) {
            return null;
        } else {
            this.generateLoot(playerInventory.player);
            return GenericContainerScreenHandler.createGeneric9x3(i, playerInventory, this);
        }
    }

    public void generateLoot(@Nullable PlayerEntity player) {
        this.generateInventoryLoot(player);
    }

    @Nullable
    public Identifier getLootTableId() {
        return this.lootTableId;
    }

    public void setLootTableId(@Nullable Identifier lootTableId) {
        this.lootTableId = lootTableId;
    }

    public long getLootTableSeed() {
        return this.lootTableSeed;
    }

    public void setLootTableSeed(long lootTableSeed) {
        this.lootTableSeed = lootTableSeed;
    }

    public DefaultedList<ItemStack> getInventory() {
        return this.inventory;
    }

    public void resetInventory() {
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
    }
}
