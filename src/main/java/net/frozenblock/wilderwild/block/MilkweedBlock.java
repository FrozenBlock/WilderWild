package net.frozenblock.wilderwild.block;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.particle.PollenParticle;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class MilkweedBlock extends TallFlowerBlock {

    public MilkweedBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.AGE_3);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, AbstractRandom random) {
        if (state.isOf(RegisterBlocks.MILKWEED)) {
            if (state.get(Properties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER) {
                if (state.get(Properties.AGE_3) < 3) {
                    if (world.getBlockState(pos).isOf(RegisterBlocks.MILKWEED)) {
                        world.setBlockState(pos, state.with(Properties.AGE_3, state.get(Properties.AGE_3) + 1));
                    }
                    if (world.getBlockState(pos.up()).isOf(RegisterBlocks.MILKWEED)) {
                        world.setBlockState(pos.up(), world.getBlockState(pos.up()).with(Properties.AGE_3, state.get(Properties.AGE_3) + 1));
                    }
                }
            }
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world instanceof ServerWorld server) {
            if (state.get(Properties.AGE_3)==3) {
                ItemStack itemStack = player.getStackInHand(hand);
                if (itemStack.isOf(Items.SHEARS)) {
                    ItemStack stack = new ItemStack(RegisterItems.MILKWEED_POD);
                    stack.setCount(world.random.nextBetween(2,7));
                    dropStack(world, pos, stack);
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_GROWING_PLANT_CROP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    itemStack.damage(1, player, (playerx) -> playerx.sendToolBreakStatus(hand));
                    world.emitGameEvent(player, GameEvent.SHEAR, pos);
                    if (state.get(Properties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER) {
                        world.setBlockState(pos, state.with(Properties.AGE_3, 0));
                        world.setBlockState(pos.up(), world.getBlockState(pos.up()).with(Properties.AGE_3, 0));
                    } else if (state.get(Properties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER) {
                        world.setBlockState(pos, state.with(Properties.AGE_3, 0));
                        world.setBlockState(pos.down(), world.getBlockState(pos.down()).with(Properties.AGE_3, 0));
                    }
                } else {
                    createParticle(world, Vec3d.ofCenter(pos).add(0, 0.3, 0), server.random.nextBetween(14, 28), true);
                    if (state.get(Properties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER) {
                        world.setBlockState(pos, state.with(Properties.AGE_3, 1));
                        world.setBlockState(pos.up(), world.getBlockState(pos.up()).with(Properties.AGE_3, 1));
                    } else if (state.get(Properties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER) {
                        world.setBlockState(pos, state.with(Properties.AGE_3, 1));
                        world.setBlockState(pos.down(), world.getBlockState(pos.down()).with(Properties.AGE_3, 1));
                    }
                }
                return ActionResult.SUCCESS;
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);

    }

    public static void createParticle(World world, Vec3d pos, int count, boolean isMilkweed) {
        if (world.isClient)
            throw new IllegalStateException("Particle attempting spawning on THE CLIENT JESUS CHRIST WHAT THE HECK");
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
        byteBuf.writeDouble(pos.x);
        byteBuf.writeDouble(pos.y);
        byteBuf.writeDouble(pos.z);
        byteBuf.writeVarInt(count);
        byteBuf.writeBoolean(isMilkweed);
        for (ServerPlayerEntity player : PlayerLookup.around((ServerWorld)world, pos, 32)) {
            ServerPlayNetworking.send(player, WilderWild.SEED_PACKET, byteBuf);
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) { return true; }
}