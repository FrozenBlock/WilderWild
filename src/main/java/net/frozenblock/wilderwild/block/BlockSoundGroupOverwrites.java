package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class BlockSoundGroupOverwrites {
    public static final BlockSoundGroup MUSHROOMS = new BlockSoundGroup(1.0F, 1.0F, RegisterSounds.BLOCK_MUSHROOM_BREAK, RegisterSounds.BLOCK_MUSHROOM_STEP, RegisterSounds.BLOCK_MUSHROOM_PLACE, RegisterSounds.BLOCK_MUSHROOM_HIT, RegisterSounds.BLOCK_MUSHROOM_FALL);

    public static List<Identifier> ids = new ArrayList<>() {{
        add(new Identifier("minecraft", "red_mushroom"));
        add(new Identifier("minecraft", "brown_mushroom"));
        add(new Identifier("minecraft", "red_mushroom_block"));
        add(new Identifier("minecraft", "brown_mushroom_block"));
    }};

    public static List<BlockSoundGroup> soundGroups = new ArrayList<>() {{
        add(MUSHROOMS);
        add(MUSHROOMS);
        add(MUSHROOMS);
        add(MUSHROOMS);
    }};
}
