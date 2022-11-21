package net.frozenblock.wilderwild.registry;

import static net.frozenblock.wilderwild.registry.RegisterSounds.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;

public final class RegisterBlockSoundGroups {
	private RegisterBlockSoundGroups() {
		throw new UnsupportedOperationException("RegisterBlockEntities contains only static declarations.");
	}

	public static final SoundType BAOBAB_NUT = new SoundType(1.0F, 1.0F,
			BLOCK_BAOBAB_NUT_BREAK,
			BLOCK_BAOBAB_NUT_STEP,
			BLOCK_BAOBAB_NUT_PLACE,
			BLOCK_BAOBAB_NUT_HIT,
			BLOCK_BAOBAB_NUT_FALL
	);

	public static final SoundType CLAY_BLOCK = new SoundType(1.0F, 1.0F,
			BLOCK_CLAY_BREAK,
			BLOCK_CLAY_STEP,
			BLOCK_CLAY_PLACE,
			BLOCK_CLAY_HIT,
			BLOCK_CLAY_FALL
	);

	public static final SoundType GRAVELSOUNDS = new SoundType(1.0F, 1.0F,
			BLOCK_GRAVEL_BREAK,
			BLOCK_GRAVEL_STEP,
			BLOCK_GRAVEL_PLACE,
			BLOCK_GRAVEL_HIT,
			BLOCK_GRAVEL_FALL
	);

	public static final SoundType OSSEOUS_SCULK = new SoundType(1.0F, 1.0F,
			BLOCK_OSSEOUS_SCULK_BREAK,
			BLOCK_OSSEOUS_SCULK_STEP,
			BLOCK_OSSEOUS_SCULK_PLACE,
			BLOCK_OSSEOUS_SCULK_HIT,
			BLOCK_OSSEOUS_SCULK_FALL
	);

	public static final SoundType NEMATOCYST = new SoundType(1.0F, 1.0F,
			BLOCK_NEMATOCYST_BREAK,
			BLOCK_NEMATOCYST_STEP,
			BLOCK_NEMATOCYST_PLACE,
			BLOCK_NEMATOCYST_HIT,
			BLOCK_NEMATOCYST_FALL
	);

	public static final SoundType NULL_BLOCK = new SoundType(1.0F, 1.0F,
			BLOCK_NULL_BLOCK_BREAK,
			BLOCK_NULL_BLOCK_STEP,
			BLOCK_NULL_BLOCK_PLACE,
			BLOCK_NULL_BLOCK_HIT,
			BLOCK_NULL_BLOCK_FALL
	);

	public static final SoundType HANGING_TENDRIL = new SoundType(1.0F, 1.25F,
			RegisterSounds.BLOCK_HANGING_TENDRIL_BREAK,
			RegisterSounds.BLOCK_HANGING_TENDRIL_STEP,
			RegisterSounds.BLOCK_HANGING_TENDRIL_PLACE,
			RegisterSounds.BLOCK_HANGING_TENDRIL_HIT,
			RegisterSounds.BLOCK_HANGING_TENDRIL_FALL
	);

	public static final SoundType HOLLOWED_LOG = new SoundType(1.0F, 1.0F,
			BLOCK_HOLLOWED_LOG_BREAK,
			BLOCK_HOLLOWED_LOG_STEP,
			BLOCK_HOLLOWED_LOG_PLACE,
			BLOCK_HOLLOWED_LOG_HIT,
			BLOCK_HOLLOWED_LOG_FALL
	);

	public static final SoundType ECHO_GLASS = new SoundType(1.0F, 1.25F,
			RegisterSounds.BLOCK_ECHO_GLASS_BREAK,
			RegisterSounds.BLOCK_ECHO_GLASS_STEP,
			RegisterSounds.BLOCK_ECHO_GLASS_PLACE,
			RegisterSounds.BLOCK_ECHO_GLASS_CRACK,
			RegisterSounds.BLOCK_ECHO_GLASS_FALL
	);

	public static final SoundType MESOGLEA = new SoundType(1.0F, 1.0F,
			BLOCK_MESOGLEA_BREAK,
			BLOCK_MESOGLEA_STEP,
			BLOCK_MESOGLEA_PLACE,
			BLOCK_MESOGLEA_HIT,
			BLOCK_MESOGLEA_FALL
	);

	public static final SoundType MUSHROOM = new SoundType(1.0F, 1.0F,
			BLOCK_MUSHROOM_BREAK,
			BLOCK_MUSHROOM_STEP,
			BLOCK_MUSHROOM_PLACE,
			BLOCK_MUSHROOM_HIT,
			BLOCK_MUSHROOM_FALL
	);

	public static final SoundType MUSHROOM_BLOCK = new SoundType(1.0F, 1.0F,
			BLOCK_MUSHROOM_BLOCK_BREAK,
			BLOCK_MUSHROOM_BLOCK_STEP,
			BLOCK_MUSHROOM_BLOCK_PLACE,
			BLOCK_MUSHROOM_BLOCK_HIT,
			BLOCK_MUSHROOM_BLOCK_FALL
	);

	public static final SoundType ICE_BLOCKS = new SoundType(1.0F, 1.25F,
			BLOCK_ICE_BREAK,
			BLOCK_ICE_STEP,
			BLOCK_ICE_PLACE,
			BLOCK_ICE_HIT,
			BLOCK_ICE_FALL
	);

	public static final SoundType LEAVES = new SoundType(1.0F, 1.0F,
			BLOCK_LEAVES_BREAK,
			BLOCK_LEAVES_STEP,
			BLOCK_LEAVES_PLACE,
			BLOCK_LEAVES_HIT,
			BLOCK_LEAVES_FALL
	);

	public static final SoundType FLOWER = new SoundType(1.0F, 1.0F,
			BLOCK_FLOWER_BREAK,
			BLOCK_FLOWER_STEP,
			BLOCK_FLOWER_PLACE,
			BLOCK_FLOWER_HIT,
			BLOCK_FLOWER_FALL
	);

	public static final SoundType WEB = new SoundType(1.0F, 1.5F,
			BLOCK_COBWEB_BREAK,
			BLOCK_COBWEB_STEP,
			BLOCK_COBWEB_PLACE,
			BLOCK_COBWEB_HIT,
			BLOCK_COBWEB_FALL
	);

	public static final SoundType LILYPAD = new SoundType(1.0F, 1.0F,
			SoundEvents.LILY_PAD_PLACE,
			SoundEvents.LILY_PAD_PLACE,
			SoundEvents.LILY_PAD_PLACE,
			SoundEvents.LILY_PAD_PLACE,
			SoundEvents.LILY_PAD_PLACE
	);

	public static final SoundType SUGARCANE = new SoundType(1.0F, 1.0F,
			BLOCK_SUGAR_CANE_BREAK,
			BLOCK_SUGAR_CANE_STEP,
			BLOCK_SUGAR_CANE_PLACE,
			BLOCK_SUGAR_CANE_HIT,
			BLOCK_SUGAR_CANE_FALL
	);

	public static final SoundType COARSEDIRT = new SoundType(0.8F, 1.0F,
			BLOCK_COARSE_DIRT_BREAK,
			BLOCK_COARSE_DIRT_STEP,
			BLOCK_COARSE_DIRT_PLACE,
			BLOCK_COARSE_DIRT_HIT,
			BLOCK_COARSE_DIRT_FALL
	);

	public static final SoundType REINFORCEDDEEPSLATE = new SoundType(1.0F, 1.0F,
			RegisterSounds.BLOCK_REINFORCED_DEEPSLATE_BREAK,
			RegisterSounds.BLOCK_REINFORCED_DEEPSLATE_STEP,
			RegisterSounds.BLOCK_REINFORCED_DEEPSLATE_PLACE,
			RegisterSounds.BLOCK_REINFORCED_DEEPSLATE_HIT,
			RegisterSounds.BLOCK_REINFORCED_DEEPSLATE_FALL
	);

	public static final SoundType FUNNY = new SoundType(1.0F, 1.0F,
			SoundEvents.ANVIL_PLACE,
			SoundEvents.ANVIL_PLACE,
			SoundEvents.ANVIL_PLACE,
			SoundEvents.ANVIL_PLACE,
			SoundEvents.ANVIL_PLACE
	);

	public static void init() {
		//Just to make sure this class gets loaded.
	}
}
