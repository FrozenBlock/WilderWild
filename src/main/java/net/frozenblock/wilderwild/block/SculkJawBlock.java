package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.NewDamageSource;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkSpreadable;
import net.minecraft.block.entity.SculkSpreadManager;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class SculkJawBlock extends Block implements SculkSpreadable {
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
            if (entity.damage(NewDamageSource.SCULK_JAW, entity instanceof PlayerEntity ? 2.5f : 8f)) {
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

    @Override
    public int spread(SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos, AbstractRandom random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock) {
        int i = cursor.getCharge();
        if (i != 0 && random.nextInt(50) == 0) {
            BlockPos blockPos = cursor.getPos();
            boolean bl = blockPos.isWithinDistance(catalystPos, spreadManager.getMaxDistance());
            return random.nextInt(spreadManager.getDecayChance()) != 0 ? i : i - (bl ? 1 : getDecay(spreadManager, blockPos, catalystPos, i));
        } else {
            return i;
        }
    }

    private static int getDecay(SculkSpreadManager spreadManager, BlockPos cursorPos, BlockPos catalystPos, int charge) {
        int i = spreadManager.getMaxDistance();
        float f = MathHelper.square((float)Math.sqrt(cursorPos.getSquaredDistance(catalystPos)) - (float)i);
        int j = MathHelper.square(24 - i);
        float g = Math.min(1.0F, f / (float)j);
        return Math.max(1, (int)((float)charge * g * 0.5F));
    }
}