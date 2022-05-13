package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.NewDamageSource;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.World;

public class SculkJawBlock extends Block {
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");
    private final IntProvider experience = ConstantIntProvider.create(20);


    public SculkJawBlock(Settings settings) {
        super(settings);
        this.setDefaultState( this.getDefaultState().with(ACTIVE, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ACTIVE);
    }

    @Deprecated
    public void scheduledTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, AbstractRandom random) {
        if (blockState.get(ACTIVE)) {
            serverWorld.setBlockState(blockPos, blockState.with(ACTIVE, false), 3);
            serverWorld.playSound(
                null,
                blockPos,
                RegisterSounds.BLOCK_SCULK_JAW_RETRACT,
                SoundCategory.BLOCKS,
                1.0F,
                serverWorld.random.nextFloat() * 0.1F + 0.9F
            );
        }
    }

    @Override
    @Deprecated
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, boolean bl) {
        super.onStacksDropped(state, world, pos, stack, bl);
        if (bl) {
            this.dropExperienceWhenMined(world, pos, stack, this.experience);
        }
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof LivingEntity && !state.get(ACTIVE) && !(entity instanceof WardenEntity)) {
            float damage = entity instanceof PlayerEntity ? 2.5f : 8f;
            if (entity.damage(NewDamageSource.SCULK_JAW, damage)) {
                world.setBlockState(pos, state.with(ACTIVE, true), 3);
                world.createAndScheduleBlockTick(pos, state.getBlock(), 60);
                world.emitGameEvent(entity, WilderWild.JAW_ACTIVATE, pos);
                world.playSound(
                        null,
                        pos,
                        RegisterSounds.BLOCK_SCULK_JAW_CLAMP,
                        SoundCategory.BLOCKS,
                        1.0f,
                        world.random.nextFloat() * 0.1F + 0.9F
                );
            }
        }
        super.onSteppedOn(world, pos, state, entity);
    }
}