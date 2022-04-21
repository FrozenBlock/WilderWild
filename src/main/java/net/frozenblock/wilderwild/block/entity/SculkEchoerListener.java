package net.frozenblock.wilderwild.block.entity;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.particle.VibrationParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockStateRaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.SculkSensorListener;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SculkEchoerListener extends SculkSensorListener {
    private final int normalRange;
    public SculkEchoerListener(PositionSource positionSource, int range, int hangingTendrilRange, Callback callback, @Nullable SculkSensorListener.Vibration vibration, int distance, int delay) {
        super(positionSource, hangingTendrilRange, callback, vibration, distance, delay);
        normalRange=range;
    }

    public boolean listen(ServerWorld world, GameEvent event, GameEvent.Emitter emitter, Vec3d pos) {
        if (this.vibration != null) {
            return false;
        } else if (!this.callback.canAccept(event, emitter)) {
            return false;
        } else {
            Optional<Vec3d> optional = this.positionSource.getPos(world);
            if (optional.isEmpty()) {
                return false;
            } else {
                Vec3d vec3d = optional.get();
                if (!this.callback.accepts(world, this, new BlockPos(pos), event, emitter)) {
                    return false;
                } else if (isOccluded(world, pos, vec3d)) {
                    return false;
                } else {
                    boolean accept = new BlockPos(vec3d).isWithinDistance(pos, range);
                    if (world.getBlockState(new BlockPos(pos)).getBlock() == RegisterBlocks.HANGING_TENDRIL) { accept=true; }
                    if (accept) {
                        this.listen(world, event, emitter, pos, vec3d);
                        return true;
                    } return false;
                }
            }
        }
    }

    private void listen(ServerWorld world, GameEvent gameEvent, GameEvent.Emitter emitter, Vec3d start, Vec3d end) {
        this.distance = MathHelper.floor(start.distanceTo(end));
        this.vibration = new SculkSensorListener.Vibration(gameEvent, this.distance, start, emitter.sourceEntity());
        this.delay = this.distance;
        world.spawnParticles(new VibrationParticleEffect(this.positionSource, this.delay), start.x, start.y, start.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        this.callback.onListen();
    }

    private static boolean isOccluded(World world, Vec3d start, Vec3d end) {
        Vec3d vec3d = new Vec3d((double)MathHelper.floor(start.x) + 0.5D, (double)MathHelper.floor(start.y) + 0.5D, (double)MathHelper.floor(start.z) + 0.5D);
        Vec3d vec3d2 = new Vec3d((double)MathHelper.floor(end.x) + 0.5D, (double)MathHelper.floor(end.y) + 0.5D, (double)MathHelper.floor(end.z) + 0.5D);
        Direction[] var5 = Direction.values();
        int var6 = var5.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Direction direction = var5[var7];
            Vec3d vec3d3 = vec3d.withBias(direction, 9.999999747378752E-6D);
            if (world.raycast(new BlockStateRaycastContext(vec3d3, vec3d2, (state) -> state.isIn(BlockTags.OCCLUDES_VIBRATION_SIGNALS))).getType() != HitResult.Type.BLOCK) {
                return false;
            }
        }

        return true;
    }

}
