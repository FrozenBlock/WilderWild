package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.registry.RegisterEnchantments;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TintedGlassBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EchoGlassBlock extends TintedGlassBlock {
    public static final IntProperty DAMAGE = RegisterProperties.DAMAGE;

    public EchoGlassBlock(Settings settings) {
        super(settings);
        this.setDefaultState( this.getDefaultState().with(DAMAGE, 0));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DAMAGE);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, AbstractRandom random) {
        int light = getLightLevel(world, pos);
        if (light<=7) {
            if (random.nextBoolean()) {
                heal(world, pos);
            }
        } else {
            damage(world, pos);
        }
    }

    public static void damage(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.get(DAMAGE)<3) {
            world.setBlockState(pos, state.with(DAMAGE, state.get(DAMAGE) + 1));
            world.playSound(null, pos, RegisterSounds.BLOCK_ECHO_GLASS_CRACK, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.syncWorldEvent(null, 2001, pos, getRawIdFromState(state));
        } else {
            world.breakBlock(pos, false);
        }
    }
    public static void heal(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.get(DAMAGE)>0) {
            world.setBlockState(pos, state.with(DAMAGE, state.get(DAMAGE) - 1));
            world.playSound(
                    null,
                    pos,
                    RegisterSounds.BLOCK_ECHO_GLASS_REPAIR,
                    SoundCategory.BLOCKS,
                    1.0F,
                    world.random.nextFloat() * 0.1F + 0.9F
            );
        }
    }

    public static int getLightLevel(World world, BlockPos blockPos) {
        int finalLight=0;
        for (Direction direction : DIRECTIONS) {
            BlockPos pos = blockPos.offset(direction);
            int skyLight = 0;
            int blockLight = world.getLightLevel(LightType.BLOCK, pos);
            if (world.isDay() && !world.isRaining()) {
                skyLight = world.getLightLevel(LightType.SKY, pos);
            }
            finalLight = Math.max(finalLight, Math.max(skyLight, blockLight));
        }
        return finalLight;
    }

    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        player.addExhaustion(0.005F);
        if (state.get(DAMAGE)<3 && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, player.getMainHandStack())<1) {
            world.setBlockState(pos, state.with(DAMAGE, state.get(DAMAGE) + 1));
        } else {
            player.incrementStat(Stats.MINED.getOrCreateStat(this));
            dropStacks(state, world, pos, blockEntity, player, stack);
            world.playSound(
                    null,
                    pos,
                    SoundEvents.BLOCK_GLASS_BREAK,
                    SoundCategory.BLOCKS,
                    1.3F,
                    world.random.nextFloat() * 0.1F + 0.8F
            );
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) { return true; }
}
