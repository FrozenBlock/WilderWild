package net.frozenblock.wilderwild.mixin.server;

import com.google.common.collect.Maps;
import net.frozenblock.wilderwild.block.ClusterBlock;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(AmethystClusterBlock.class)
public class AmethystClusterBlockMixin extends AmethystBlock implements ClusterBlock {


    @Shadow
    @Final
    protected VoxelShape northAabb;

    @Shadow
    @Final
    protected VoxelShape eastAabb;

    @Shadow
    @Final
    protected VoxelShape southAabb;

    @Shadow
    @Final
    protected VoxelShape westAabb;

    @Shadow
    @Final
    protected VoxelShape upAabb;

    @Shadow
    @Final
    protected VoxelShape downAabb;

    public AmethystClusterBlockMixin(Properties properties) {
        super(properties);
    }

    private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION;

    private final Map<Direction, VoxelShape> SHAPE_BY_DIRECTION = Util.make(Maps.newEnumMap(Direction.class), shapes -> {
        shapes.put(Direction.NORTH, northAabb);
        shapes.put(Direction.EAST, eastAabb);
        shapes.put(Direction.SOUTH, southAabb);
        shapes.put(Direction.WEST, westAabb);
        shapes.put(Direction.UP, upAabb);
        shapes.put(Direction.DOWN, downAabb);
    });

    private static final Direction[] DIRECTIONS = Direction.values();

    @Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
    private void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
        for (Direction direction : DIRECTIONS) {
            builder.add(MultifaceBlock.getFaceProperty(direction));
        }
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void cluster(int height, int xzOffset, Properties settings, CallbackInfo ci) {
        this.registerDefaultState(getDefaultClusterState(this.stateDefinition));
    }

    private static BlockState getDefaultClusterState(StateDefinition<Block, BlockState> stateManager) {
        BlockState blockState = stateManager.any();

        for (BooleanProperty booleanProperty : PROPERTY_BY_DIRECTION.values()) {
            if (blockState.hasProperty(booleanProperty)) {
                blockState = blockState.setValue(booleanProperty, false);
            }
        }

        return blockState;
    }
}
