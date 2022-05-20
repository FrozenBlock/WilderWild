package net.frozenblock.wilderwild.entity;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.Direction.AxisDirection;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.*;
import java.util.stream.Stream;

public class TermiteManager {
    private final TagKey<Block> replaceableTag;
    private final int extraBlockChance;
    private final int maxDistance;
    private final int spreadChance;
    private final int decayChance;
    private List<TermiteManager.Cursor> cursors = new ArrayList<>();
    private static final Logger LOGGER = LogUtils.getLogger();

    public TermiteManager(TagKey<Block> replaceableTag, int extraBlockChance, int maxDistance, int spreadChance, int decayChance) {
        this.replaceableTag = replaceableTag;
        this.extraBlockChance = extraBlockChance;
        this.maxDistance = maxDistance;
        this.spreadChance = spreadChance;
        this.decayChance = decayChance;
    }

    public static TermiteManager create() {
        return new TermiteManager(BlockTags.SCULK_REPLACEABLE, 10, 4, 10, 5);
    }

    @VisibleForTesting
    public List<TermiteManager.Cursor> getCursors() {
        return this.cursors;
    }

    public void clearCursors() {
        this.cursors.clear();
    }

    public void readNbt(NbtCompound nbt) {
        if (nbt.contains("cursors", 9)) {
            this.cursors.clear();
            DataResult<List<Cursor>> var10000 = TermiteManager.Cursor.CODEC.listOf().parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getList("cursors", 10)));
            Logger var10001 = LOGGER;
            Objects.requireNonNull(var10001);
            List<TermiteManager.Cursor> list = var10000.resultOrPartial(var10001::error).orElseGet(ArrayList::new);
            int i = Math.min(list.size(), 32);

            for(int j = 0; j < i; ++j) {
                this.addCursor(list.get(j));
            }
        }

    }

    public void writeNbt(NbtCompound nbt) {
        DataResult<?> var10000 = TermiteManager.Cursor.CODEC.listOf().encodeStart(NbtOps.INSTANCE, this.cursors);
        Logger var10001 = LOGGER;
        Objects.requireNonNull(var10001);
        var10000.resultOrPartial(var10001::error).ifPresent((cursorsNbt) -> {
            nbt.put("cursors", (NbtElement) cursorsNbt);
        });
    }

    public void spread(BlockPos pos, int termites) {
        while(termites > 0) {
            int i = Math.min(termites, 1000);
            this.addCursor(new TermiteManager.Cursor(pos, i));
            termites -= i;
        }

    }

    private void addCursor(TermiteManager.Cursor cursor) {
        if (this.cursors.size() < 32) {
            this.cursors.add(cursor);
        }
    }

    public void tick(WorldAccess world, BlockPos pos, AbstractRandom random, boolean shouldConvertToBlock) {
        if (!this.cursors.isEmpty()) {
            List<TermiteManager.Cursor> list = new ArrayList<>();
            Map<BlockPos, TermiteManager.Cursor> map = new HashMap<>();
            Object2IntMap<BlockPos> object2IntMap = new Object2IntOpenHashMap<>();
            Iterator<Cursor> var8 = this.cursors.iterator();

            while(true) {
                BlockPos blockPos;
                while(var8.hasNext()) {
                    TermiteManager.Cursor cursor = var8.next();
                    cursor.spread(world, pos, random, this, shouldConvertToBlock);
                    if (cursor.termites <= 0) {
                        world.syncWorldEvent(3006, cursor.getPos(), 0);
                    } else {
                        blockPos = cursor.getPos();
                        object2IntMap.computeInt(blockPos, (posx, termites) -> (termites == null ? 0 : termites) + cursor.termites);
                        TermiteManager.Cursor cursor2 = map.get(blockPos);
                        if (cursor2 == null) {
                            map.put(blockPos, cursor);
                            list.add(cursor);
                        } else if (cursor.termites + cursor2.termites <= 1000) {
                            cursor2.merge(cursor);
                        } else {
                            list.add(cursor);
                            if (cursor.termites < cursor2.termites) {
                                map.put(blockPos, cursor);
                            }
                        }
                    }
                }

                for (Entry<BlockPos> entry : object2IntMap.object2IntEntrySet()) {
                    blockPos = entry.getKey();
                    int i = entry.getIntValue();
                    Cursor cursor3 = map.get(blockPos);
                    Collection<Direction> collection = cursor3 == null ? null : cursor3.getFaces();
                    if (i > 0 && collection != null) {
                        int j = (int) (Math.log1p(i) / 2.299999952316284D) + 1;
                        int k = (j << 6) + MultifaceGrowthBlock.directionsToFlag(collection);
                        world.syncWorldEvent(3006, blockPos, k);
                    }
                }

                this.cursors = list;
                return;
            }
        }
    }

    public static class Cursor {
        private static final ObjectArrayList OFFSETS = Util.make(new ObjectArrayList(18), (objectArrayList) -> {
            Stream<BlockPos> var10000 = BlockPos.stream(new BlockPos(-1, -1, -1), new BlockPos(1, 1, 1)).filter((pos) -> {
                return (pos.getX() == 0 || pos.getY() == 0 || pos.getZ() == 0) && !pos.equals(BlockPos.ORIGIN);
            }).map(BlockPos::toImmutable);
            Objects.requireNonNull(objectArrayList);
            var10000.forEach(objectArrayList::add);
        });
        private BlockPos pos;
        int termites;
        private int update;
        private int decay;
        @Nullable
        private Set<Direction> faces;
        private static final Codec<Set<Direction>> DIRECTION_SET_CODEC;
        public static final Codec<TermiteManager.Cursor> CODEC;

        private Cursor(BlockPos pos, int termites, int decay, int update, Optional<Set<Direction>> faces) {
            this.pos = pos;
            this.termites = termites;
            this.decay = decay;
            this.update = update;
            this.faces = faces.orElse(null);
        }

        public Cursor(BlockPos pos, int termites) {
            this(pos, termites, 0, 0, Optional.empty());
        }

        public BlockPos getPos() { return this.pos; }
        public int getTermites() { return this.termites; }
        public int getDecay() { return this.decay; }

        @Nullable
        public Set<Direction> getFaces() {
            return this.faces;
        }

        private boolean canSpread(WorldAccess world, BlockPos pos) {
            if (this.termites <= 0) {
                return false;
            } else if (world instanceof ServerWorld serverWorld) {
                return serverWorld.shouldTickBlockPos(pos);
            } else {
                return false;
            }
        }

        public void spread(WorldAccess world, BlockPos pos, AbstractRandom random, TermiteManager spreadManager, boolean shouldConvertToBlock) {
            if (this.canSpread(world, pos)) {
                if (this.update > 0) {
                    --this.update;
                } else {
                    BlockState blockState = world.getBlockState(this.pos);

                    //this.termites = sculkSpreadable.spread(this, world, pos, random, spreadManager, shouldConvertToBlock);
                }
            }
        }

        void merge(TermiteManager.Cursor cursor) {
            this.termites += cursor.termites;
            cursor.termites = 0;
            this.update = Math.min(this.update, cursor.update);
        }

        private static List<Vec3i> shuffleOffsets(AbstractRandom random) {
            return Util.copyShuffled(OFFSETS, random);
        }

        @Nullable
        private static BlockPos getSpreadPos(WorldAccess world, BlockPos pos, AbstractRandom random) {
            Mutable mutable = pos.mutableCopy();
            Mutable mutable2 = pos.mutableCopy();

            for (Vec3i vec3i : shuffleOffsets(random)) {
                mutable2.set(pos, vec3i);
                BlockState blockState = world.getBlockState(mutable2);
                if (blockState.getBlock() instanceof SculkSpreadable && canSpread(world, pos, (BlockPos) mutable2)) {
                    mutable.set(mutable2);
                    if (SculkVeinBlock.veinCoversSculkReplaceable(world, blockState, mutable2)) {
                        break;
                    }
                }
            }

            return mutable.equals(pos) ? null : mutable;
        }

        private static boolean canSpread(WorldAccess world, BlockPos sourcePos, BlockPos targetPos) {
            if (sourcePos.getManhattanDistance(targetPos) == 1) {
                return true;
            } else {
                BlockPos blockPos = targetPos.subtract(sourcePos);
                Direction direction = Direction.from(Axis.X, blockPos.getX() < 0 ? AxisDirection.NEGATIVE : AxisDirection.POSITIVE);
                Direction direction2 = Direction.from(Axis.Y, blockPos.getY() < 0 ? AxisDirection.NEGATIVE : AxisDirection.POSITIVE);
                Direction direction3 = Direction.from(Axis.Z, blockPos.getZ() < 0 ? AxisDirection.NEGATIVE : AxisDirection.POSITIVE);
                if (blockPos.getX() == 0) {
                    return canSpread(world, sourcePos, direction2) || canSpread(world, sourcePos, direction3);
                } else if (blockPos.getY() == 0) {
                    return canSpread(world, sourcePos, direction) || canSpread(world, sourcePos, direction3);
                } else {
                    return canSpread(world, sourcePos, direction) || canSpread(world, sourcePos, direction2);
                }
            }
        }

        private static boolean canSpread(WorldAccess world, BlockPos pos, Direction direction) {
            BlockPos blockPos = pos.offset(direction);
            return !world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction.getOpposite());
        }

        static {
            DIRECTION_SET_CODEC = Direction.CODEC.listOf().xmap((directions) -> {
                return Sets.newEnumSet(directions, Direction.class);
            }, Lists::newArrayList);
            CODEC = RecordCodecBuilder.create((instance) -> {
                return instance.group(BlockPos.CODEC.fieldOf("pos").forGetter(TermiteManager.Cursor::getPos),
                        Codec.intRange(0, 1000).fieldOf("termites").orElse(0).forGetter(TermiteManager.Cursor::getTermites),
                        Codec.intRange(0, 1).fieldOf("decay_delay").orElse(1).forGetter(TermiteManager.Cursor::getDecay),
                        Codec.intRange(0, 2147483647).fieldOf("update_delay").orElse(0).forGetter((cursor) -> {
                    return cursor.update;
                }), DIRECTION_SET_CODEC.optionalFieldOf("facings").forGetter((cursor) -> {
                    return Optional.ofNullable(cursor.getFaces());
                })).apply(instance, TermiteManager.Cursor::new);
            });
        }
    }
}
