package net.frozenblock.wilderwild.item;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MilkweedPodItem extends Item {

    public MilkweedPodItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        WilderWild.log(user, "Used Milkweed Pod");
        ItemStack itemStack = user.getStackInHand(hand);
        itemStack.decrement(1);
        if (world instanceof ServerWorld server) {
            float pitch = user.getPitch();
            float yaw = user.getYaw();
            float roll = 0.0F;
            float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
            float g = -MathHelper.sin((pitch + roll) * 0.017453292F);
            float h = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
            createControlledParticle(world, user.getEyePos().add(0, -0.1, 0), f, g, h,server.random.nextBetween(5, 20), true);
        }

        return TypedActionResult.consume(itemStack);
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.TOOT_HORN;
    }

    public static void createControlledParticle(World world, Vec3d pos, double xvel, double yvel, double zvel, int count, boolean isMilkweed) {
        if (world.isClient)
            throw new IllegalStateException("Particle attempting spawning on THE CLIENT JESUS CHRIST WHAT THE HECK");
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
        byteBuf.writeDouble(pos.x);
        byteBuf.writeDouble(pos.y);
        byteBuf.writeDouble(pos.z);
        byteBuf.writeDouble(xvel*1.5);
        byteBuf.writeDouble(yvel);
        byteBuf.writeDouble(zvel*1.5);
        byteBuf.writeVarInt(count);
        byteBuf.writeBoolean(isMilkweed);
        for (ServerPlayerEntity player : PlayerLookup.around((ServerWorld)world, pos, 32)) {
            ServerPlayNetworking.send(player, WilderWild.CONTROLLED_SEED_PACKET, byteBuf);
        }
    }

}
