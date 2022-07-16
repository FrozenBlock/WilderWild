package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@Mixin(SlimeEntity.class)
public abstract class SlimeMixin extends MobEntity {

    protected SlimeMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "canSpawn", at = @At("HEAD"), cancellable = true)
    private static void canSpawn(EntityType<SlimeEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> info) {
        if (world.getDifficulty() != Difficulty.PEACEFUL) {
            if (world.getBiome(pos).isIn(WilderBiomeTags.SLIMES_SPAWN_ON_FLOATING_MOSS)) {
                boolean test = spawnReason == SpawnReason.SPAWNER || random.nextInt(15) == 0;
                if (test && isFloatingMossNearby(world, pos, 1)) {
                    info.setReturnValue(true);
                    info.cancel();
                }
            }
        }
    }

    private static boolean isFloatingMossNearby(WorldAccess world, BlockPos blockPos, int x) {
        Iterator<BlockPos> iterator = BlockPos.iterate(blockPos.add(-x, -x, -x), blockPos.add(x, x, x)).iterator();
        BlockPos pos;
        do {
            if (!iterator.hasNext()) { return false; }
            pos = iterator.next();
        } while(!world.getBlockState(pos).isOf(RegisterBlocks.FLOATING_MOSS));
        return true;
    }

    @Override
    public boolean canSpawn(WorldView world) {
        return world.doesNotIntersectEntities(this);
    }

}
