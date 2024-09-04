package net.frozenblock.wilderwild.block.api;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.Map;
import java.util.Optional;
import net.frozenblock.wilderwild.block.LeafLitterBlock;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import org.jetbrains.annotations.Nullable;

public class FallingLeafRegistry {
	private static final LeafParticleData DEFAULT_LEAF_PARTICLE_DATA = new LeafParticleData(0.0125F, 0.225F, 3F);
	private static final Map<Block, FallingLeafData> LEAVES_TO_FALLING_LEAF_DATA = new Object2ObjectLinkedOpenHashMap<>();
	private static final Map<ParticleOptions, LeafParticleData> PARTICLE_TO_LEAF_PARTICLE_DATA = new Object2ObjectLinkedOpenHashMap<>();

	public static void addFallingLeaf(
		Block block,
		LeafLitterBlock leafLitterBlock, float litterChance,
		ParticleOptions leafParticle
	) {
		addFallingLeaf(
			block, new FallingLeafData(leafLitterBlock, litterChance, leafParticle),
			leafParticle, null
		);
	}

	public static void addFallingLeaf(
		Block block,
		LeafLitterBlock leafLitterBlock, float litterChance,
		ParticleOptions leafParticle, float particleChance, float quadSize, float particleGravityScale
	) {
		addFallingLeaf(
			block, new FallingLeafData(leafLitterBlock, litterChance, leafParticle),
			leafParticle, new LeafParticleData(particleChance, quadSize, particleGravityScale)
		);
	}

	private static void addFallingLeaf(
		Block block, FallingLeafData fallingLeafData, ParticleOptions leafParticle, @Nullable LeafParticleData leafParticleData
	) {
		if (block instanceof LeavesBlock leavesBlock) {
			LEAVES_TO_FALLING_LEAF_DATA.put(leavesBlock, fallingLeafData);
			if (leafParticleData != null) {
				PARTICLE_TO_LEAF_PARTICLE_DATA.put(leafParticle, leafParticleData);
			}
			LeafLitterBlock.LeafParticleRegistry.registerLeafParticle(fallingLeafData.leafLitterBlock, leafParticle);
		} else {
			throw new IllegalStateException("Block should be an instance of LeavesBlock!");
		}
	}

	public static Optional<FallingLeafData> getFallingLeafData(Block block) {
		return Optional.ofNullable(LEAVES_TO_FALLING_LEAF_DATA.get(block));
	}

	public static LeafParticleData getLeafParticleData(ParticleOptions leafParticle) {
		return PARTICLE_TO_LEAF_PARTICLE_DATA.getOrDefault(leafParticle, DEFAULT_LEAF_PARTICLE_DATA);
	}

	public record FallingLeafData(LeafLitterBlock leafLitterBlock, float litterChance, ParticleOptions particle) {}

	public record LeafParticleData(float particleChance, float quadSize, float particleGravityScale) {}
}
