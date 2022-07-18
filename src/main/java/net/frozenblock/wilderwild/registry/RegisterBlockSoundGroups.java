package net.frozenblock.wilderwild.registry;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

import static net.frozenblock.wilderwild.registry.RegisterSounds.*;

public class RegisterBlockSoundGroups {

    public static final BlockSoundGroup OSSEOUS_SCULK = new BlockSoundGroup(1.0F, 1.0F,
            BLOCK_OSSEOUS_SCULK_BREAK,
            BLOCK_OSSEOUS_SCULK_STEP,
            BLOCK_OSSEOUS_SCULK_PLACE,
            BLOCK_OSSEOUS_SCULK_HIT,
            BLOCK_OSSEOUS_SCULK_FALL
    );

    public static final BlockSoundGroup HANGING_TENDRIL = new BlockSoundGroup(1.0F, 1.25F,
            RegisterSounds.BLOCK_HANGING_TENDRIL_BREAK,
            RegisterSounds.BLOCK_HANGING_TENDRIL_STEP,
            RegisterSounds.BLOCK_HANGING_TENDRIL_PLACE,
            RegisterSounds.BLOCK_HANGING_TENDRIL_HIT,
            RegisterSounds.BLOCK_HANGING_TENDRIL_FALL
    );

    public static final BlockSoundGroup HOLLOWED_LOG = new BlockSoundGroup(1.0F, 1.0F,
            BLOCK_HOLLOWED_LOG_BREAK,
            BLOCK_HOLLOWED_LOG_STEP,
            BLOCK_HOLLOWED_LOG_PLACE,
            BLOCK_HOLLOWED_LOG_HIT,
            BLOCK_HOLLOWED_LOG_FALL
    );

    public static final BlockSoundGroup ECHO_GLASS = new BlockSoundGroup(1.0F, 1.25F,
            RegisterSounds.BLOCK_ECHO_GLASS_BREAK,
            RegisterSounds.BLOCK_ECHO_GLASS_STEP,
            RegisterSounds.BLOCK_ECHO_GLASS_PLACE,
            RegisterSounds.BLOCK_ECHO_GLASS_CRACK,
            RegisterSounds.BLOCK_ECHO_GLASS_FALL
    );

    public static final BlockSoundGroup MUSHROOM = new BlockSoundGroup(1.0F, 1.0F,
            BLOCK_MUSHROOM_BREAK,
            BLOCK_MUSHROOM_STEP,
            BLOCK_MUSHROOM_PLACE,
            BLOCK_MUSHROOM_HIT,
            BLOCK_MUSHROOM_FALL
    );

    public static final BlockSoundGroup MUSHROOM_BLOCK = new BlockSoundGroup(1.0F, 1.0F,
            BLOCK_MUSHROOM_BLOCK_BREAK,
            BLOCK_MUSHROOM_BLOCK_STEP,
            BLOCK_MUSHROOM_BLOCK_PLACE,
            BLOCK_MUSHROOM_BLOCK_HIT,
            BLOCK_MUSHROOM_BLOCK_FALL
    );

    public static final BlockSoundGroup ICE_BLOCKS = new BlockSoundGroup(1.0F, 1.25F,
            BLOCK_ICE_BREAK,
            BLOCK_ICE_STEP,
            BLOCK_ICE_PLACE,
            BLOCK_ICE_HIT,
            BLOCK_ICE_FALL
    );

    public static final BlockSoundGroup LEAVES = new BlockSoundGroup(1.0F, 1.0F,
            BLOCK_LEAVES_BREAK,
            BLOCK_LEAVES_STEP,
            BLOCK_LEAVES_PLACE,
            BLOCK_LEAVES_HIT,
            BLOCK_LEAVES_FALL
    );

    public static final BlockSoundGroup FLOWER = new BlockSoundGroup(1.0F, 1.0F,
            BLOCK_FLOWER_BREAK,
            BLOCK_FLOWER_STEP,
            BLOCK_FLOWER_PLACE,
            BLOCK_FLOWER_HIT,
            BLOCK_FLOWER_FALL
    );

    public static final BlockSoundGroup WEB = new BlockSoundGroup(1.0F, 1.5F,
            BLOCK_COBWEB_BREAK,
            BLOCK_COBWEB_STEP,
            BLOCK_COBWEB_PLACE,
            BLOCK_COBWEB_HIT,
            BLOCK_COBWEB_FALL
    );

    public static final BlockSoundGroup LILYPAD = new BlockSoundGroup(1.0F, 1.0F,
            SoundEvents.BLOCK_LILY_PAD_PLACE,
            SoundEvents.BLOCK_LILY_PAD_PLACE,
            SoundEvents.BLOCK_LILY_PAD_PLACE,
            SoundEvents.BLOCK_LILY_PAD_PLACE,
            SoundEvents.BLOCK_LILY_PAD_PLACE
    );

    public static final BlockSoundGroup SUGARCANE = new BlockSoundGroup(1.0F, 1.0F,
            BLOCK_SUGAR_CANE_BREAK,
            BLOCK_SUGAR_CANE_STEP,
            BLOCK_SUGAR_CANE_PLACE,
            BLOCK_SUGAR_CANE_HIT,
            BLOCK_SUGAR_CANE_FALL
    );

    public static final BlockSoundGroup COARSEDIRT = new BlockSoundGroup(0.8F, 1.0F,
            BLOCK_COARSE_DIRT_BREAK,
            BLOCK_COARSE_DIRT_STEP,
            BLOCK_COARSE_DIRT_PLACE,
            BLOCK_COARSE_DIRT_HIT,
            BLOCK_COARSE_DIRT_FALL
    );

    public static final BlockSoundGroup REINFORCEDDEEPSLATE = new BlockSoundGroup(1.0F, 1.0F,
            RegisterSounds.BLOCK_REINFORCED_DEEPSLATE_BREAK,
            RegisterSounds.BLOCK_REINFORCED_DEEPSLATE_STEP,
            RegisterSounds.BLOCK_REINFORCED_DEEPSLATE_PLACE,
            RegisterSounds.BLOCK_REINFORCED_DEEPSLATE_HIT,
            RegisterSounds.BLOCK_REINFORCED_DEEPSLATE_FALL
    );

    public static final BlockSoundGroup FUNNY = new BlockSoundGroup(1.0F, 1.0F,
            SoundEvents.BLOCK_ANVIL_PLACE,
            SoundEvents.BLOCK_ANVIL_PLACE,
            SoundEvents.BLOCK_ANVIL_PLACE,
            SoundEvents.BLOCK_ANVIL_PLACE,
            SoundEvents.BLOCK_ANVIL_PLACE
    );

    public static void init() {
        //Just to make sure this class gets loaded.
    }
}
