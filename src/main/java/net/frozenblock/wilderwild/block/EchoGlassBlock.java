package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.TintedGlassBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class EchoGlassBlock extends TintedGlassBlock {
    public static final IntegerProperty DAMAGE = RegisterProperties.DAMAGE;

    public EchoGlassBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(DAMAGE, 0));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DAMAGE);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int light = getLightLevel(world, pos);
        if (light <= 7) {
            if (random.nextBoolean()) {
                heal(world, pos);
            }
        } else {
            damage(world, pos);
        }
    }

    public static void damage(Level world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        WilderWild.log("Echo Glass Damaged @ " + pos, WilderWild.UNSTABLE_LOGGING);
        if (state.getValue(DAMAGE) < 3) {
            world.setBlockAndUpdate(pos, state.setValue(DAMAGE, state.getValue(DAMAGE) + 1));
            world.playSound(null, pos, RegisterSounds.BLOCK_ECHO_GLASS_CRACK, SoundSource.BLOCKS, 1.0F, 1.0F);
            world.levelEvent(null, LevelEvent.PARTICLES_DESTROY_BLOCK, pos, getId(state));
        } else {
            world.destroyBlock(pos, false);
        }
    }

    public static void heal(Level world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.getValue(DAMAGE) > 0) {
            WilderWild.log("Echo Glass Healed @ " + pos, WilderWild.UNSTABLE_LOGGING);
            world.setBlockAndUpdate(pos, state.setValue(DAMAGE, state.getValue(DAMAGE) - 1));
            world.playSound(
                    null,
                    pos,
                    RegisterSounds.BLOCK_ECHO_GLASS_REPAIR,
                    SoundSource.BLOCKS,
                    1.0F,
                    world.random.nextFloat() * 0.1F + 0.9F
            );
        }
    }

    public static int getLightLevel(Level world, BlockPos blockPos) {
        int finalLight = 0;
        for (Direction direction : UPDATE_SHAPE_ORDER) {
            BlockPos pos = blockPos.relative(direction);
            int skyLight = 0;
            int blockLight = world.getBrightness(LightLayer.BLOCK, pos);
            if (world.isDay() && !world.isRaining()) {
                skyLight = world.getBrightness(LightLayer.SKY, pos);
            }
            finalLight = Math.max(finalLight, Math.max(skyLight, blockLight));
        }
        return finalLight;
    }

    public void playerDestroy(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        player.causeFoodExhaustion(0.005F);
        if (state.getValue(DAMAGE) < 3 && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, player.getMainHandItem()) < 1 && !player.isCreative()) {
            world.setBlockAndUpdate(pos, state.setValue(DAMAGE, state.getValue(DAMAGE) + 1));
        } else {
            player.awardStat(Stats.BLOCK_MINED.get(this));
            dropResources(state, world, pos, blockEntity, player, stack);
            world.playSound(
                    null,
                    pos,
                    SoundEvents.GLASS_BREAK,
                    SoundSource.BLOCKS,
                    1.3F,
                    world.random.nextFloat() * 0.1F + 0.8F
            );
        }
    }

    @Override
    public void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (projectile instanceof AncientHornProjectile) {
            damage(world, hit.getBlockPos());
        }
        super.onProjectileHit(world, state, hit, projectile);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }
}
