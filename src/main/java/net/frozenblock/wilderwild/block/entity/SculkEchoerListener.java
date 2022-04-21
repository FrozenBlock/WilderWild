package net.frozenblock.wilderwild.block.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.VibrationParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.GameEventTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockStateRaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.GameEventListener;
import net.minecraft.world.event.listener.SculkSensorListener;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class SculkEchoerListener implements GameEventListener {
    protected final PositionSource positionSource;
    protected final int range;
    protected final int normalRange;
    protected final SculkEchoerListener.Callback callback;
    @Nullable
    protected SculkEchoerListener.Vibration vibration;
    protected int distance;
    protected int delay;

    public static Codec<SculkEchoerListener> createCodec(SculkEchoerListener.Callback callback) {
        return RecordCodecBuilder.create((instance) -> {
            return instance.group(PositionSource.CODEC.fieldOf("source").forGetter((listener) -> {
                return listener.positionSource;
            }), Codecs.NONNEGATIVE_INT.fieldOf("nonTendrilRange").forGetter((listener) -> {
                return listener.normalRange;
            }), Codecs.NONNEGATIVE_INT.fieldOf("range").forGetter((listener) -> {
                return listener.range;
            }), SculkEchoerListener.Vibration.CODEC.optionalFieldOf("event").forGetter((listener) -> {
                return Optional.ofNullable(listener.vibration);
            }), Codecs.NONNEGATIVE_INT.fieldOf("event_distance").orElse(0).forGetter((listener) -> {
                return listener.distance;
            }), Codecs.NONNEGATIVE_INT.fieldOf("event_delay").orElse(0).forGetter((listener) -> {
                return listener.delay;
            })).apply(instance, (positionSource, normalRange, tendrilRange, vibration, distance, delay) -> {
                return new SculkEchoerListener(positionSource, normalRange, tendrilRange, callback, vibration.orElse(null), distance, delay);
            });
        });
    }
    public SculkEchoerListener(PositionSource positionSource, int range, int hangingTendrilRange, Callback callback, @Nullable SculkEchoerListener.Vibration vibration, int distance, int delay) {
        this.positionSource = positionSource;
        this.range = hangingTendrilRange;
        this.callback = callback;
        this.vibration = vibration;
        this.distance = distance;
        this.delay = delay;
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
                    boolean accept = new BlockPos(vec3d).isWithinDistance(pos, normalRange);
                    if (world.getBlockState(new BlockPos(pos)).getBlock() == RegisterBlocks.HANGING_TENDRIL) { accept=true; }
                    if (accept) {
                        this.listen(world, event, emitter, pos, vec3d);
                        return true;
                    } return false;
                }
            }
        }
    }

    public void tick(World world) {
        if (world instanceof ServerWorld serverWorld) {
            if (this.vibration != null) {
                --this.delay;
                if (this.delay <= 0) {
                    this.delay = 0;
                    this.callback.accept(serverWorld, this, new BlockPos(this.vibration.pos), this.vibration.gameEvent, this.vibration.getEntity(serverWorld).orElse(null), this.vibration.getOwner(serverWorld).orElse(null), this.distance);
                    this.vibration = null;
                }
            }
        }

    }

    public PositionSource getPositionSource() {
        return this.positionSource;
    }

    public int getRange() {
        return this.range;
    }

    private void listen(ServerWorld world, GameEvent gameEvent, GameEvent.Emitter emitter, Vec3d start, Vec3d end) {
        this.distance = MathHelper.floor(start.distanceTo(end));
        this.vibration = new SculkEchoerListener.Vibration(gameEvent, this.distance, start, emitter.sourceEntity());
        this.delay = this.distance;
        world.spawnParticles(new VibrationParticleEffect(this.positionSource, this.delay), start.x, start.y, start.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        this.callback.onListen();
    }

    private static boolean isOccluded(World world, Vec3d start, Vec3d end) {
        Vec3d vec3d = new Vec3d((double)MathHelper.floor(start.x) + 0.5D, (double)MathHelper.floor(start.y) + 0.5D, (double)MathHelper.floor(start.z) + 0.5D);
        Vec3d vec3d2 = new Vec3d((double)MathHelper.floor(end.x) + 0.5D, (double)MathHelper.floor(end.y) + 0.5D, (double)MathHelper.floor(end.z) + 0.5D);
        Direction[] var5 = Direction.values();
        int var6 = var5.length;

        for (Direction direction : var5) {
            Vec3d vec3d3 = vec3d.withBias(direction, 9.999999747378752E-6D);
            if (world.raycast(new BlockStateRaycastContext(vec3d3, vec3d2, (state) -> {
                return state.isIn(BlockTags.OCCLUDES_VIBRATION_SIGNALS);
            })).getType() != HitResult.Type.BLOCK) {
                return false;
            }
        }

        return true;
    }

    public interface Callback {
        default TagKey<GameEvent> getTag() {
            return GameEventTags.VIBRATIONS;
        }

        default boolean canAccept(GameEvent gameEvent, GameEvent.Emitter emitter) {
            if (!gameEvent.isIn(this.getTag())) {
                return false;
            } else {
                Entity entity = emitter.sourceEntity();
                if (entity != null) {
                    if (entity.isSpectator()) {
                        return false;
                    }

                    if (entity.bypassesSteppingEffects() && gameEvent.isIn(GameEventTags.IGNORE_VIBRATIONS_SNEAKING)) {
                        if (entity instanceof ServerPlayerEntity serverPlayerEntity) {
                            Criteria.AVOID_VIBRATION.trigger(serverPlayerEntity);
                        }

                        return false;
                    }

                    if (entity.occludeVibrationSignals()) {
                        return false;
                    }

                    if (gameEvent.isIn(GameEventTags.DAMPENABLE_VIBRATIONS)) {
                        return !entity.getSteppingBlockState().isIn(BlockTags.DAMPENS_VIBRATIONS);
                    }
                }

                if (emitter.affectedState() != null) {
                    return !emitter.affectedState().isIn(BlockTags.DAMPENS_VIBRATIONS);
                } else {
                    return true;
                }
            }
        }

        boolean accepts(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, GameEvent.Emitter emitter);

        void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, int delay);

        default void onListen() {
        }
    }

    public static record Vibration(GameEvent gameEvent, int distance, Vec3d pos, @Nullable UUID uuid, @Nullable UUID projectileOwnerUuid, @Nullable Entity entity) {
        final GameEvent gameEvent;
        final Vec3d pos;
        public static final Codec<SculkEchoerListener.Vibration> CODEC = RecordCodecBuilder.create((instance) -> {
            return instance.group(Registry.GAME_EVENT.getCodec().fieldOf("game_event").forGetter(SculkEchoerListener.Vibration::gameEvent), Codecs.NONNEGATIVE_INT.fieldOf("distance").forGetter(SculkEchoerListener.Vibration::distance), Vec3d.CODEC.fieldOf("pos").forGetter(SculkEchoerListener.Vibration::pos), Codecs.UUID.optionalFieldOf("source").forGetter((vibration) -> {
                return Optional.ofNullable(vibration.uuid());
            }), Codecs.UUID.optionalFieldOf("projectile_owner").forGetter((vibration) -> {
                return Optional.ofNullable(vibration.projectileOwnerUuid());
            })).apply(instance, (gameEvent, integer, vec3d, optional, optional2) -> {
                return new SculkEchoerListener.Vibration(gameEvent, integer, vec3d, optional.orElse(null), optional2.orElse(null));
            });
        });

        public Vibration(GameEvent gameEvent, int distance, Vec3d pos, @Nullable UUID uuid, @Nullable UUID sourceUuid) {
            this(gameEvent, distance, pos, uuid, sourceUuid, null);
        }

        public Vibration(GameEvent gameEvent, int distance, Vec3d pos, @Nullable Entity entity) {
            this(gameEvent, distance, pos, entity == null ? null : entity.getUuid(), getOwnerUuid(entity), entity);
        }

        public Vibration(GameEvent gameEvent, int distance, Vec3d pos, @Nullable UUID uuid, @Nullable UUID projectileOwnerUuid, @Nullable Entity entity) {
            this.gameEvent = gameEvent;
            this.distance = distance;
            this.pos = pos;
            this.uuid = uuid;
            this.projectileOwnerUuid = projectileOwnerUuid;
            this.entity = entity;
        }

        @Nullable
        private static UUID getOwnerUuid(@Nullable Entity entity) {
            if (entity instanceof ProjectileEntity projectileEntity) {
                if (projectileEntity.getOwner() != null) {
                    return projectileEntity.getOwner().getUuid();
                }
            }

            return null;
        }

        public Optional<Entity> getEntity(ServerWorld world) {
            return Optional.ofNullable(this.entity).or(() -> {
                Optional<UUID> var10000 = Optional.ofNullable(this.uuid);
                Objects.requireNonNull(world);
                return var10000.map(world::getEntity);
            });
        }

        public Optional<Entity> getOwner(ServerWorld world) {
            return this.getEntity(world).filter((entity) -> {
                return entity instanceof ProjectileEntity;
            }).map((entity) -> {
                return (ProjectileEntity)entity;
            }).map(ProjectileEntity::getOwner).or(() -> {
                Optional<UUID> var10000 = Optional.ofNullable(this.projectileOwnerUuid);
                Objects.requireNonNull(world);
                return var10000.map(world::getEntity);
            });
        }

        public GameEvent gameEvent() {
            return this.gameEvent;
        }

        public int distance() {
            return this.distance;
        }

        public Vec3d pos() {
            return this.pos;
        }

        @Nullable
        public UUID uuid() {
            return this.uuid;
        }

        @Nullable
        public UUID projectileOwnerUuid() {
            return this.projectileOwnerUuid;
        }

        @Nullable
        public Entity entity() {
            return this.entity;
        }
    }
}
