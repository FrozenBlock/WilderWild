package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.misc.NewDamageSource;
import net.frozenblock.wilderwild.registry.RegisterGameEvents;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;


public class SculkJawBlock extends Block implements SculkBehaviour {
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    private final IntProvider experience = ConstantInt.of(20);


    public SculkJawBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ACTIVE);
    }

    @Override
    public void tick(BlockState blockState, @NotNull ServerLevel serverWorld, @NotNull BlockPos blockPos, @NotNull RandomSource random) {
        if (blockState.getValue(ACTIVE)) {
            serverWorld.setBlock(blockPos, blockState.setValue(ACTIVE, false), 3);
            serverWorld.playSound(
                    null,
                    blockPos,
                    RegisterSounds.BLOCK_SCULK_JAW_RETRACT,
                    SoundSource.BLOCKS,
                    1.0F,
                    serverWorld.random.nextFloat() * 0.1F + 0.9F
            );
        }
    }

    @Override
    public void spawnAfterBreak(@NotNull BlockState state, @NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull ItemStack stack, boolean bl) {
        super.spawnAfterBreak(state, world, pos, stack, bl);
        if (bl) {
            this.tryDropExperience(world, pos, stack, this.experience);
        }
    }

    @Override
    public void stepOn(Level world, BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
        if (entity instanceof LivingEntity && !state.getValue(ACTIVE) && !(entity instanceof Warden)) {
            if (entity.hurt(NewDamageSource.SCULK_JAW, entity instanceof Player ? 2.5F : 8F)) {
                world.setBlock(pos, state.setValue(ACTIVE, true), 3);
                world.scheduleTick(pos, state.getBlock(), 60);
                world.gameEvent(entity, RegisterGameEvents.JAW_ACTIVATE, pos);
                world.playSound(
                        null,
                        pos,
                        RegisterSounds.BLOCK_SCULK_JAW_CLAMP,
                        SoundSource.BLOCKS,
                        1.0f,
                        world.random.nextFloat() * 0.1F + 0.9F
                );
            }
        }
        super.stepOn(world, pos, state, entity);
    }

    @Override
    public int attemptUseCharge(SculkSpreader.ChargeCursor cursor, @NotNull LevelAccessor world, @NotNull BlockPos catalystPos, @NotNull RandomSource random, @NotNull SculkSpreader spreadManager, boolean shouldConvertToBlock) {
        int i = cursor.getCharge();
        if (i != 0 && random.nextInt(50) == 0) {
            BlockPos blockPos = cursor.getPos();
            boolean bl = blockPos.closerThan(catalystPos, spreadManager.noGrowthRadius());
            return random.nextInt(spreadManager.additionalDecayRate()) != 0 ? i : i - (bl ? 1 : getDecay(spreadManager, blockPos, catalystPos, i));
        } else {
            return i;
        }
    }

    private static int getDecay(SculkSpreader spreadManager, BlockPos cursorPos, BlockPos catalystPos, int charge) {
        int i = spreadManager.noGrowthRadius();
        float f = Mth.square((float) Math.sqrt(cursorPos.distSqr(catalystPos)) - (float) i);
        int j = Mth.square(24 - i);
        float g = Math.min(1.0F, f / (float) j);
        return Math.max(1, (int) ((float) charge * g * 0.5F));
    }
}
