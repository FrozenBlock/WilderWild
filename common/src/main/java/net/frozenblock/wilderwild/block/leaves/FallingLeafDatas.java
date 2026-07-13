package net.frozenblock.wilderwild.block.leaves;

import java.util.Optional;
import net.frozenblock.lib.config.v2.entry.ConfigEntry;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.impl.MapleCollection;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.ApiStatus;

public final class FallingLeafDatas {

	public static void register(
		BootstrapContext<FallingLeafData> context,
		ResourceKey<FallingLeafData> key,
		Block leavesBlock,
		Block leafLitterBlock,
		Optional<FallingLeafData.ParticleData> leafParticle,
		Optional<FallingLeafData.ParticleData> leafLitterParticle,
		Optional<FallingLeafData.FallingLeafLitterData> fallingLeafLitter
	) {
		context.register(
			key,
			new FallingLeafData(
				HolderSet.direct(leavesBlock.builtInRegistryHolder()),
				HolderSet.direct(leafLitterBlock.builtInRegistryHolder()),
				leafParticle,
				leafLitterParticle,
				fallingLeafLitter
			)
		);
	}

	public static void register(
		BootstrapContext<FallingLeafData> context,
		ResourceKey<FallingLeafData> key,
		Block leavesBlock,
		Block leafLitterBlock,
		FallingLeafData.ParticleData leafParticle,
		FallingLeafData.ParticleData leafLitterParticle,
		FallingLeafData.FallingLeafLitterData fallingLeafLitter
	) {
		register(
			context,
			key,
			leavesBlock,
			leafLitterBlock,
			Optional.of(leafParticle),
			Optional.of(leafLitterParticle),
			Optional.of(fallingLeafLitter)
		);
	}

	public static void register(
		BootstrapContext<FallingLeafData> context,
		ResourceKey<FallingLeafData> key,
		Block leavesBlock,
		Block leafLitterBlock,
		FallingLeafData.ParticleData leafParticle,
		FallingLeafData.ParticleData leafLitterParticle
	) {
		register(
			context,
			key,
			leavesBlock,
			leafLitterBlock,
			Optional.of(leafParticle),
			Optional.of(leafLitterParticle),
			Optional.empty()
		);
	}

	public static void register(
		BootstrapContext<FallingLeafData> context,
		ResourceKey<FallingLeafData> key,
		Block leavesBlock,
		Block leafLitterBlock,
		Holder<ParticleType<?>> leafParticle,
		Holder<ParticleType<?>> leafLitterParticle,
		float spawnChance,
		ConfigEntry<Integer> spawnChanceModifier,
		int textureSize,
		float gravityScale,
		float windScale,
		FallingLeafData.LeafMovementType movementType
	) {
		register(
			context,
			key,
			leavesBlock,
			leafLitterBlock,
			Optional.of(
				FallingLeafData.ParticleData.forLeaves(
					leafParticle,
					leavesBlock,
					spawnChance,
					spawnChanceModifier,
					textureSize,
					gravityScale,
					windScale,
					movementType
				)
			),
			Optional.of(
				FallingLeafData.ParticleData.forLeafLitter(
					leafLitterParticle,
					leafLitterBlock,
					textureSize,
					gravityScale,
					windScale,
					movementType.getGroundSupportingEquivalent()
				)
			),
			Optional.empty()
		);
	}

	public static ResourceKey<FallingLeafData> createKey(Identifier id) {
		return ResourceKey.create(WilderWildRegistries.FALLING_LEAF, id);
	}

	private static ResourceKey<FallingLeafData> createKey(String string) {
		return createKey(WWConstants.id(string));
	}

	@ApiStatus.Internal
	public static void bootstrap(BootstrapContext<FallingLeafData> context) {
		register(
			context,
			createKey("oak"),
			Blocks.OAK_LEAVES,
			Blocks.LEAF_LITTER,
			WWParticleTypes.OAK_LEAVES.asHolder(),
			WWParticleTypes.OAK_LITTER_LEAVES.asHolder(),
			0.0095F,
			WWAmbienceAndMiscConfig.OAK_LEAF_FREQUENCY,
			5,
			1.4F,
			10F,
			FallingLeafData.LeafMovementType.SWIRL
		);

		register(
			context,
			createKey("spruce"),
			Blocks.SPRUCE_LEAVES,
			WWBlocks.SPRUCE_LEAF_LITTER.get(),
			WWParticleTypes.SPRUCE_LEAVES.asHolder(),
			WWParticleTypes.SPRUCE_LITTER_LEAVES.asHolder(),
			0.0075F,
			WWAmbienceAndMiscConfig.SPRUCE_LEAF_FREQUENCY,
			5,
			2F,
			5F,
			FallingLeafData.LeafMovementType.SWIRL
		);

		register(
			context,
			createKey("birch"),
			Blocks.BIRCH_LEAVES,
			WWBlocks.BIRCH_LEAF_LITTER.get(),
			WWParticleTypes.BIRCH_LEAVES.asHolder(),
			WWParticleTypes.BIRCH_LITTER_LEAVES.asHolder(),
			0.0095F,
			WWAmbienceAndMiscConfig.BIRCH_LEAF_FREQUENCY,
			4,
			1F,
			10F,
			FallingLeafData.LeafMovementType.SWIRL
		);

		register(
			context,
			createKey("jungle"),
			Blocks.JUNGLE_LEAVES,
			WWBlocks.JUNGLE_LEAF_LITTER.get(),
			WWParticleTypes.JUNGLE_LEAVES.asHolder(),
			WWParticleTypes.JUNGLE_LITTER_LEAVES.asHolder(),
			0.0095F,
			WWAmbienceAndMiscConfig.JUNGLE_LEAF_FREQUENCY,
			4,
			1.4F,
			10F,
			FallingLeafData.LeafMovementType.SWIRL
		);

		register(
			context,
			createKey("acacia"),
			Blocks.ACACIA_LEAVES,
			WWBlocks.ACACIA_LEAF_LITTER.get(),
			WWParticleTypes.ACACIA_LEAVES.asHolder(),
			WWParticleTypes.ACACIA_LITTER_LEAVES.asHolder(),
			0.0125F,
			WWAmbienceAndMiscConfig.ACACIA_LEAF_FREQUENCY,
			3,
			1.4F,
			10F,
			FallingLeafData.LeafMovementType.SWIRL
		);

		register(
			context,
			createKey("dark_oak"),
			Blocks.DARK_OAK_LEAVES,
			WWBlocks.DARK_OAK_LEAF_LITTER.get(),
			WWParticleTypes.DARK_OAK_LEAVES.asHolder(),
			WWParticleTypes.DARK_OAK_LITTER_LEAVES.asHolder(),
			0.0095F,
			WWAmbienceAndMiscConfig.DARK_OAK_LEAF_FREQUENCY,
			5,
			1.4F,
			10F,
			FallingLeafData.LeafMovementType.SWIRL
		);

		register(
			context,
			createKey("pale_oak"),
			Blocks.PALE_OAK_LEAVES,
			WWBlocks.PALE_OAK_LEAF_LITTER.get(),
			FallingLeafData.ParticleData.forLeaves(
				WWParticleTypes.PALE_OAK_LEAVES.asHolder(),
				Blocks.PALE_OAK_LEAVES,
				0.0045F,
				WWAmbienceAndMiscConfig.PALE_OAK_LEAF_FREQUENCY,
				5,
				0.28F,
				20F,
				FallingLeafData.LeafMovementType.SWIRL
			),
			FallingLeafData.ParticleData.forLeafLitter(
				WWParticleTypes.PALE_OAK_LITTER_LEAVES.asHolder(),
				WWBlocks.PALE_OAK_LEAF_LITTER.get(),
				5,
				1.4F,
				20F,
				FallingLeafData.LeafMovementType.SWIRL.getGroundSupportingEquivalent()
			)
		);

		register(
			context,
			createKey("mangrove"),
			Blocks.MANGROVE_LEAVES,
			WWBlocks.MANGROVE_LEAF_LITTER.get(),
			WWParticleTypes.MANGROVE_LEAVES.asHolder(),
			WWParticleTypes.MANGROVE_LITTER_LEAVES.asHolder(),
			0.0095F,
			WWAmbienceAndMiscConfig.MANGROVE_LEAF_FREQUENCY,
			6,
			2.5F,
			10F,
			FallingLeafData.LeafMovementType.SWIRL
		);

		register(
			context,
			createKey("cherry"),
			Blocks.CHERRY_LEAVES,
			WWBlocks.CHERRY_LEAF_LITTER.get(),
			WWParticleTypes.CHERRY_LEAVES.asHolder(),
			WWParticleTypes.CHERRY_LITTER_LEAVES.asHolder(),
			0.0125F,
			WWAmbienceAndMiscConfig.CHERRY_LEAF_FREQUENCY,
			4,
			1F,
			2F,
			FallingLeafData.LeafMovementType.FLOW_AWAY
		);

		register(
			context,
			createKey("azalea"),
			Blocks.AZALEA_LEAVES,
			WWBlocks.AZALEA_LEAF_LITTER.get(),
			WWParticleTypes.AZALEA_LEAVES.asHolder(),
			WWParticleTypes.AZALEA_LITTER_LEAVES.asHolder(),
			0.0095F,
			WWAmbienceAndMiscConfig.AZALEA_LEAF_FREQUENCY,
			4,
			2F,
			10F,
			FallingLeafData.LeafMovementType.SWIRL
		);

		context.register(
			createKey("flowering_azalea"),
			new FallingLeafData(
				HolderSet.direct(Blocks.FLOWERING_AZALEA_LEAVES.builtInRegistryHolder()),
				HolderSet.empty(),
				Optional.of(
					FallingLeafData.ParticleData.forLeaves(
						WWParticleTypes.AZALEA_LEAVES.asHolder(),
						Blocks.FLOWERING_AZALEA_LEAVES,
						0.0095F,
						WWAmbienceAndMiscConfig.AZALEA_LEAF_FREQUENCY,
						4,
						2F,
						10F,
						FallingLeafData.LeafMovementType.SWIRL
					)
				),
				Optional.empty(),
				Optional.empty()
			)
		);

		register(
			context,
			createKey("baobab"),
			WWBlocks.BAOBAB_LEAVES.get(),
			WWBlocks.BAOBAB_LEAF_LITTER.get(),
			WWParticleTypes.BAOBAB_LEAVES.asHolder(),
			WWParticleTypes.BAOBAB_LITTER_LEAVES.asHolder(),
			0.0095F,
			WWAmbienceAndMiscConfig.BAOBAB_LEAF_FREQUENCY,
			4,
			2F,
			15F,
			FallingLeafData.LeafMovementType.SWIRL
		);

		register(
			context,
			createKey("willow"),
			WWBlocks.WILLOW_LEAVES.get(),
			WWBlocks.WILLOW_LEAF_LITTER.get(),
			FallingLeafData.ParticleData.forLeaves(
				WWParticleTypes.WILLOW_LEAVES.asHolder(),
				WWBlocks.WILLOW_LEAVES.get(),
				0.0045F,
				WWAmbienceAndMiscConfig.WILLOW_LEAF_FREQUENCY,
				5,
				1.4F,
				10F,
				FallingLeafData.LeafMovementType.SWIRL
			),
			FallingLeafData.ParticleData.forLeafLitter(
				WWParticleTypes.WILLOW_LITTER_LEAVES.asHolder(),
				WWBlocks.WILLOW_LEAF_LITTER.get(),
				4,
				1.4F,
				10F,
				FallingLeafData.LeafMovementType.SWIRL.getGroundSupportingEquivalent()
			)
		);

		register(
			context,
			createKey("cypress"),
			WWBlocks.CYPRESS_LEAVES.get(),
			WWBlocks.CYPRESS_LEAF_LITTER.get(),
			WWParticleTypes.CYPRESS_LEAVES.asHolder(),
			WWParticleTypes.CYPRESS_LITTER_LEAVES.asHolder(),
			0.0095F,
			WWAmbienceAndMiscConfig.CYPRESS_LEAF_FREQUENCY,
			4,
			2F,
			5F,
			FallingLeafData.LeafMovementType.SWIRL
		);

		register(
			context,
			createKey("palm"),
			WWBlocks.PALM_FRONDS.get(),
			WWBlocks.PALM_FROND_LITTER.get(),
			WWParticleTypes.PALM_FRONDS.asHolder(),
			WWParticleTypes.PALM_LITTER_FRONDS.asHolder(),
			0.00055F,
			WWAmbienceAndMiscConfig.PALM_FROND_FREQUENCY,
			6,
			4.5F,
			5F,
			FallingLeafData.LeafMovementType.SWIRL
		);

		MapleCollection.DYE_COLORS.forEach(color -> {
			final Block leaves = WWBlocks.MAPLE_LEAVES.pick(color).get();
			final Block leafLitter = WWBlocks.MAPLE_LEAF_LITTER.pick(color).get();
			final Holder<ParticleType<?>> particle = WWParticleTypes.MAPLE_LEAVES.pick(color).asHolder();

			register(
				context,
				createKey(MapleCollection.NAMES.pick(color) + "_maple"),
				leaves,
				leafLitter,
				FallingLeafData.ParticleData.forLeaves(
					particle,
					leaves,
					0.0225F,
					WWAmbienceAndMiscConfig.MAPLE_LEAF_FREQUENCY,
					5,
					3F,
					10F,
					FallingLeafData.LeafMovementType.SWIRL
				),
				FallingLeafData.ParticleData.forLeafLitter(
					particle,
					leafLitter,
					5,
					4.5F,
					8F,
					FallingLeafData.LeafMovementType.SWIRL.getGroundSupportingEquivalent()
				),
				new FallingLeafData.FallingLeafLitterData(
					leafLitter.builtInRegistryHolder(),
					0.04F,
					0.25F,
					Optional.empty(),
					Optional.of(WWSounds.BLOCK_FALLING_LEAF_LITTER_MAPLE_LAND.asHolder())
				)
			);
		});
	}

}
