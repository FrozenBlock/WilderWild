package net.frozenblock.wilderwild.misc;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class FlowerLichenParticleRegistry {

    public static void init() {
        addBlock(RegisterBlocks.POLLEN_BLOCK, RegisterParticles.POLLEN);
    }

    public static void addBlock(Block block, ParticleOptions particle) {
        blocks.add(block);
        particles.add(particle);
    }

    public static final List<Block> blocks = new ArrayList<>();
    public static final List<ParticleOptions> particles = new ArrayList<>();
}
