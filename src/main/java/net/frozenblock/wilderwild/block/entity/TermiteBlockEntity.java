package net.frozenblock.wilderwild.block.entity;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.logging.LogUtils;
import net.frozenblock.wilderwild.entity.TermiteManager;
import net.frozenblock.wilderwild.registry.RegisterBlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;

public class TermiteBlockEntity extends BlockEntity {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final TermiteManager termiteManager;

    public TermiteBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntityType.TERMITE, pos, state);
        this.termiteManager = TermiteManager.create();
    }

    // this.termiteManager.spread(new BlockPos(event.getEmitterPos().withBias(Direction.UP, 0.5D)), i);


    public void tick(World world, BlockPos pos, BlockState state) {
        this.termiteManager.spread(pos, 1);
        this.termiteManager.tick(world, pos, world.getRandom(), true);
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.termiteManager.readNbt(nbt);
    }

    protected void writeNbt(NbtCompound nbt) {
        this.termiteManager.writeNbt(nbt);
        super.writeNbt(nbt);
    }

    @VisibleForTesting
    public TermiteManager getTermiteManager() {
        return this.termiteManager;
    }

}