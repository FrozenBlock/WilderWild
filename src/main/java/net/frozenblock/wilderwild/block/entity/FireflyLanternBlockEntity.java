package net.frozenblock.wilderwild.block.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.entity.ai.FireflyBrain;
import net.frozenblock.wilderwild.item.FireflyBottle;
import net.frozenblock.wilderwild.misc.ClientMethodInteractionThingy;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FireflyLanternBlockEntity extends BlockEntity {
    public DefaultedList<ItemStack> inventory;
    ArrayList<FireflyInLantern> fireflies = new ArrayList<>();

    public int age;
    public boolean hasUpdated = false;

    public FireflyLanternBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntities.FIREFLY_LANTERN, pos, state);
        this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    }

    public void serverTick(World world, BlockPos pos) {
        if (!this.fireflies.isEmpty()) {
            for (FireflyInLantern firefly : this.fireflies) {
                firefly.tick(world, pos);
            }
        }
    }

    public void clientTick(World world, BlockPos pos) {
        if (world.isClient) {
        if (!this.hasUpdated) {
            this.hasUpdated = true;
            ClientMethodInteractionThingy.requestClientLanternBlahBlahBlah(pos, world);
            }
        }
        this.age += 1;
        if (!this.fireflies.isEmpty()) {
            for (FireflyInLantern firefly : this.fireflies) {
                firefly.tick(world, pos);
            }
        }
    }

    public void updatePlayer(ServerPlayerEntity player) {
        player.networkHandler.sendPacket(this.toUpdatePacket());
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

    public boolean invEmpty() {
        Optional<ItemStack> stack1 = this.inventory.stream().findFirst();
        return stack1.map(itemStack -> itemStack == ItemStack.EMPTY).orElse(true);
    }

    public Optional<ItemStack> getItem() {
        return this.inventory.stream().findFirst();
    }

    public boolean noFireflies() {
        return this.getFireflies().isEmpty();
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("Fireflies", 9)) {
            this.fireflies.clear();
            DataResult<?> var10000 = FireflyInLantern.CODEC.listOf().parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getList("Fireflies", 10)));
            Logger var10001 = WilderWild.LOGGER;
            Objects.requireNonNull(var10001);
            Optional<List> list = (Optional<List>) var10000.resultOrPartial(var10001::error);
            if (list.isPresent()) {
                List<?> fireflyList = list.get();
                for (Object o : fireflyList) {
                    this.fireflies.add((FireflyInLantern) o);
                }
            }
        }
        this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
        this.age = nbt.getInt("age");
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        DataResult<?> var10000 = FireflyInLantern.CODEC.listOf().encodeStart(NbtOps.INSTANCE, this.fireflies);
        Logger var10001 = WilderWild.LOGGER;
        Objects.requireNonNull(var10001);
        var10000.resultOrPartial(var10001::error).ifPresent((cursorsNbt) -> {
            nbt.put("Fireflies", (NbtElement) cursorsNbt);
        });
        Inventories.writeNbt(nbt, this.inventory);
        nbt.putInt("age", this.age);
    }

    public ArrayList<FireflyInLantern> getFireflies() {
        return this.fireflies;
    }

    public void addFirefly(FireflyBottle bottle, String name) {
        Vec3d newVec = new Vec3d(0.5 + (0.15 - Math.random() * 0.3), 0, 0.5 + (0.15 - Math.random() * 0.3));
        this.fireflies.add(new FireflyInLantern(newVec, bottle.color, name, Math.random() > 0.7, (int) (Math.random() * 20), 0));
    }

    public void removeFirefly(FireflyInLantern firefly) {
        this.fireflies.remove(firefly);
    }

    public void spawnFireflies() {
        if (this.world != null) {
            if (!this.world.isClient) {
                double extraHeight = this.getCachedState().get(Properties.HANGING) ? 0.155 : 0;
                for (FireflyLanternBlockEntity.FireflyInLantern firefly : this.getFireflies()) {
                    Firefly entity = RegisterEntities.FIREFLY.create(world);
                    if (entity != null) {
                        entity.refreshPositionAndAngles(pos.getX() + firefly.pos.x, pos.getY() + firefly.y + extraHeight + 0.07, pos.getZ() + firefly.pos.z, 0, 0);
                        entity.setFromBottle(true);
                        boolean spawned = world.spawnEntity(entity);
                        if (spawned) {
                            entity.hasHome = true;
                            FireflyBrain.rememberHome(entity, entity.getBlockPos());
                            entity.setColor(firefly.color);
                            entity.setScale(1.0F);
                            if (!Objects.equals(firefly.customName, "")) {
                                entity.setCustomName(Text.of(firefly.customName));
                            }
                        } else {
                            WilderWild.log("Couldn't spawn Firefly from lantern @ " + pos, WilderWild.UNSTABLE_LOGGING);
                        }
                    }
                }
            }
        }
    }

    public void spawnFireflies(World world) {
        double extraHeight = this.getCachedState().get(Properties.HANGING) ? 0.155 : 0;
        for (FireflyLanternBlockEntity.FireflyInLantern firefly : this.getFireflies()) {
            Firefly entity = RegisterEntities.FIREFLY.create(world);
            if (entity != null) {
                entity.refreshPositionAndAngles(pos.getX() + firefly.pos.x, pos.getY() + firefly.y + extraHeight + 0.07, pos.getZ() + firefly.pos.z, 0, 0);
                entity.setFromBottle(true);
                boolean spawned = world.spawnEntity(entity);
                if (spawned) {
                    entity.hasHome = true;
                    FireflyBrain.rememberHome(entity, entity.getBlockPos());
                    entity.setColor(firefly.color);
                    entity.setScale(1.0F);
                    if (!Objects.equals(firefly.customName, "")) {
                        entity.setCustomName(Text.of(firefly.customName));
                    }
                } else {
                    WilderWild.log("Couldn't spawn Firefly from lantern @ " + pos, WilderWild.UNSTABLE_LOGGING);
                }
            }
        }
    }

    public static class FireflyInLantern {
        public Vec3d pos;
        public String color;
        public String customName;
        public boolean flickers;
        public int age;
        public double y;
        public boolean wasNamedNectar;

        public static final Codec<FireflyInLantern> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                Vec3d.CODEC.fieldOf("pos").forGetter(FireflyInLantern::getPos),
                Codec.STRING.fieldOf("color").forGetter(FireflyInLantern::getColor),
                Codec.STRING.fieldOf("customName").orElse("").forGetter(FireflyInLantern::getCustomName),
                Codec.BOOL.fieldOf("flickers").orElse(false).forGetter(FireflyInLantern::getFlickers),
                Codec.INT.fieldOf("age").forGetter(FireflyInLantern::getAge),
                Codec.DOUBLE.fieldOf("y").forGetter(FireflyInLantern::getY)
        ).apply(instance, FireflyInLantern::new));

        public FireflyInLantern(Vec3d pos, String color, String customName, boolean flickers, int age, double y) {
            this.pos = pos;
            this.color = color;
            this.customName = customName;
            this.flickers = flickers;
            this.age = age;
            this.y = y;
        }

        boolean nectar = false;

        public void tick(World world, BlockPos pos) {
            this.age += 1;
            this.y = Math.sin(this.age * 0.03) * 0.15;
            nectar = this.getCustomName().toLowerCase().contains("nectar");

            if (nectar != wasNamedNectar) {
                if (nectar) {
                    if (world.getTime() % 70L == 0L) {
                        world.playSound(null, pos, RegisterSounds.BLOCK_FIREFLY_LANTERN_NECTAR_LOOP, SoundCategory.AMBIENT, 0.5F, 1.0F);
                    }
                    this.wasNamedNectar = true;
                } else {
                    this.wasNamedNectar = false;
                }
            } else {
                this.wasNamedNectar = false;
            }
        }

        public Vec3d getPos() {
            return this.pos;
        }

        public String getColor() {
            return this.color;
        }

        public String getCustomName() {
            return this.customName;
        }

        public boolean getFlickers() {
            return this.flickers;
        }

        public int getAge() {
            return this.age;
        }

        public double getY() {
            return this.y;
        }

    }

}