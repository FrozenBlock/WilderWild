package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TintedGlassBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class EchoGlassBlock extends TintedGlassBlock {
    public static final IntProperty DAMAGE = RegisterProperties.DAMAGE;

    public EchoGlassBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(DAMAGE, 0));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DAMAGE);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int light = getLightLevel(world, pos);
        if (light <= 7) {
            if (random.nextBoolean()) {
                heal(world, pos);
            }
        } else {
            damage(world, pos);
        }
    }

    public static void damage(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        WilderWild.log("Echo Glass Damaged @ " + pos, WilderWild.UNSTABLE_LOGGING);
        if (state.get(DAMAGE) < 3) {
            world.setBlockState(pos, state.with(DAMAGE, state.get(DAMAGE) + 1));
            world.playSound(null, pos, RegisterSounds.BLOCK_ECHO_GLASS_CRACK, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.syncWorldEvent(null, WorldEvents.BLOCK_BROKEN, pos, getRawIdFromState(state));
        } else {
            world.breakBlock(pos, false);
        }
    }

    public static void heal(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.get(DAMAGE) > 0) {
            WilderWild.log("Echo Glass Healed @ " + pos, WilderWild.UNSTABLE_LOGGING);
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
        int finalLight = 0;
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
        if (state.get(DAMAGE) < 3 && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, player.getMainHandStack()) < 1 && !player.isCreative()) {
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

    @Deprecated
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        Identifier identifier = this.getLootTableId();
        if (builder.getNullable(LootContextParameters.TOOL) != null) {
            ItemStack stack = builder.get(LootContextParameters.TOOL);
            if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) != 0) {
                if (state.get(DAMAGE) == 0) {
                    identifier = WilderWild.id("blocks/echo_glass_full");
                }
            }
        }
        if (identifier == LootTables.EMPTY) {
            return Collections.emptyList();
        } else {
            LootContext lootContext = builder.parameter(LootContextParameters.BLOCK_STATE, state).build(LootContextTypes.BLOCK);
            ServerWorld serverWorld = lootContext.getWorld();
            LootTable lootTable = serverWorld.getServer().getLootManager().getTable(identifier);
            return lootTable.generateLoot(lootContext);
        }
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (projectile instanceof AncientHornProjectile) {
            damage(world, hit.getBlockPos());
        }
        super.onProjectileHit(world, state, hit, projectile);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }
}
