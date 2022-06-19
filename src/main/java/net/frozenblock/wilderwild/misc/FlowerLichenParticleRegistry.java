package net.frozenblock.wilderwild.misc;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.block.Block;
import net.minecraft.particle.ParticleEffect;

import java.util.ArrayList;
import java.util.List;

public class FlowerLichenParticleRegistry {

    public static void init() {
        addBlock(RegisterBlocks.POLLEN_BLOCK, RegisterParticles.POLLEN);
    }

    public static void addBlock(Block block, ParticleEffect particle) {
        blocks.add(block);
        particles.add(particle);
    }

    public static List<Block> blocks = new ArrayList<>();
    public static List<ParticleEffect> particles = new ArrayList<>();
}
