package net.frozenblock.wilderwild.block.entity;

import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.frozenblock.wilderwild.registry.RegisterBlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;

import java.util.ArrayList;

public class TermiteBlockEntity extends BlockEntity {
    private static final Logger LOGGER = LogUtils.getLogger();
    ArrayList<Termite> termites = new ArrayList<>();
    IntArrayList termiteX = new IntArrayList();
    IntArrayList termiteY = new IntArrayList();
    IntArrayList termiteZ = new IntArrayList();
    IntArrayList termiteDestroyPower = new IntArrayList();

    public TermiteBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntityType.TERMITE, pos, state);
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.termites.clear();
        this.termiteX = IntArrayList.wrap(nbt.getIntArray("termiteX"));
        this.termiteY = IntArrayList.wrap(nbt.getIntArray("termiteY"));
        this.termiteZ = IntArrayList.wrap(nbt.getIntArray("termiteZ"));
        this.termiteDestroyPower = IntArrayList.wrap(nbt.getIntArray("termiteDestroyPower"));
        for (int x : termiteX) {
            int index = termiteX.indexOf(x);
            int y = termiteY.getInt(index);
            int z = termiteZ.getInt(index);
            int power = termiteDestroyPower.getInt(index);
            this.termites.add(new Termite(x,y, z, power));
        }
    }

    public void removeTermite(Termite termite) {
        int index = termites.indexOf(termite);
        termites.remove(termite);
        termiteX.removeInt(index);
        termiteY.removeInt(index);
        termiteZ.removeInt(index);
        termiteDestroyPower.removeInt(index);
    }

    public void addTermite(BlockPos pos) {
        Termite termite = new Termite(pos.getX(), pos.getY(), pos.getZ(), 0);
        this.termites.add(termite);
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (!this.termites.isEmpty()) {
            this.termiteX.clear();
            this.termiteY.clear();
            this.termiteZ.clear();
            this.termiteDestroyPower.clear();
            for (Termite termite : termites) {
                termiteX.add(termite.pos.getX());
                termiteY.add(termite.pos.getY());
                termiteZ.add(termite.pos.getZ());
                termiteDestroyPower.add(termite.blockDestroyPower);
            }
        }
        nbt.putIntArray("termiteX", this.termiteX);
        nbt.putIntArray("termiteY", this.termiteY);
        nbt.putIntArray("termiteZ", this.termiteZ);
        nbt.putIntArray("termiteDestroyPower", this.termiteDestroyPower);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        for (Termite termite : this.termites) {
            world.syncWorldEvent(3006, termite.pos, 0);
        }
        this.addTermite(pos.up());
    }

    public class Termite {
        public BlockPos pos;
        public int blockDestroyPower;

        public Termite(int x, int y, int z, int blockDestroyPower) {
            this.pos = new BlockPos(x,y,z);
            this.blockDestroyPower=blockDestroyPower;
        }

    }
}