package net.frozenblock.wilderwild.registry;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

import static net.frozenblock.wilderwild.registry.RegisterSounds.*;

public class RegisterBlockSoundGroups {

    public static BlockSoundGroup OSSEOUS_SCULK = new BlockSoundGroup(1.0f, 1.0f,
            BLOCK_OSSEOUS_SCULK_BREAK,
            BLOCK_OSSEOUS_SCULK_STEP,
            BLOCK_OSSEOUS_SCULK_PLACE,
            BLOCK_OSSEOUS_SCULK_HIT,
            BLOCK_OSSEOUS_SCULK_FALL);

    public static BlockSoundGroup HANGING_TENDRIL = new BlockSoundGroup(1.0f, 1.25f,
            RegisterSounds.BLOCK_HANGING_TENDRIL_BREAK,
            RegisterSounds.BLOCK_HANGING_TENDRIL_STEP,
            RegisterSounds.BLOCK_HANGING_TENDRIL_PLACE,
            RegisterSounds.BLOCK_HANGING_TENDRIL_HIT,
            RegisterSounds.BLOCK_HANGING_TENDRIL_FALL
    );

    public static BlockSoundGroup ECHO_GLASS = new BlockSoundGroup(1.0f, 1.25f,
            RegisterSounds.BLOCK_ECHO_GLASS_BREAK,
            RegisterSounds.BLOCK_ECHO_GLASS_STEP,
            RegisterSounds.BLOCK_ECHO_GLASS_PLACE,
            RegisterSounds.BLOCK_ECHO_GLASS_CRACK,
            RegisterSounds.BLOCK_ECHO_GLASS_FALL
    );

    public static BlockSoundGroup MUSHROOM = new BlockSoundGroup(1.0f, 1.0f,
            BLOCK_MUSHROOM_BREAK,
            BLOCK_MUSHROOM_STEP,
            BLOCK_MUSHROOM_PLACE,
            BLOCK_MUSHROOM_HIT,
            BLOCK_MUSHROOM_FALL
    );

    public static BlockSoundGroup MUSHROOM_BLOCK = new BlockSoundGroup(1.0f, 1.0f,
            BLOCK_MUSHROOM_BLOCK_BREAK,
            BLOCK_MUSHROOM_BLOCK_STEP,
            BLOCK_MUSHROOM_BLOCK_PLACE,
            BLOCK_MUSHROOM_BLOCK_HIT,
            BLOCK_MUSHROOM_BLOCK_FALL
    );

    public static BlockSoundGroup LILYPAD = new BlockSoundGroup(1.0f, 1.0f,
            SoundEvents.BLOCK_LILY_PAD_PLACE,
            SoundEvents.BLOCK_LILY_PAD_PLACE,
            SoundEvents.BLOCK_LILY_PAD_PLACE,
            SoundEvents.BLOCK_LILY_PAD_PLACE,
            SoundEvents.BLOCK_LILY_PAD_PLACE
    );

    public static BlockSoundGroup REINFORCEDDEEPSLATE = new BlockSoundGroup(1.0f, 1.0f,
            RegisterSounds.BLOCK_REINFORCED_DEEPSLATE_BREAK,
            RegisterSounds.BLOCK_REINFORCED_DEEPSLATE_STEP,
            RegisterSounds.BLOCK_REINFORCED_DEEPSLATE_PLACE,
            RegisterSounds.BLOCK_REINFORCED_DEEPSLATE_HIT,
            RegisterSounds.BLOCK_REINFORCED_DEEPSLATE_FALL
    );

    public static BlockSoundGroup FUNNY = new BlockSoundGroup(1.0f, 1.0f,
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
