package net.frozenblock.wilderwild.block;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.MUSHROOM;

public class BlockSoundGroupOverwrites {

    public static List<Identifier> ids = new ArrayList<>() {{
        add(new Identifier("minecraft", "red_mushroom"));
        add(new Identifier("minecraft", "brown_mushroom"));
        add(new Identifier("minecraft", "red_mushroom_block"));
        add(new Identifier("minecraft", "brown_mushroom_block"));
        add(new Identifier("minecraft", "brown_mushroom_block"));
        add(new Identifier("minecraft", "mushroom_stem"));
    }};

    public static List<BlockSoundGroup> soundGroups = new ArrayList<>() {{
        add(MUSHROOM);
        add(MUSHROOM);
        add(MUSHROOM);
        add(MUSHROOM);
        add(MUSHROOM);
    }};
}
