package net.frozenblock.wilderwild.block;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.*;

public class BlockSoundGroupOverwrites {

    public static List<Identifier> ids = new ArrayList<>() {{
        add(new Identifier("red_mushroom"));
        add(new Identifier("brown_mushroom"));
        add(new Identifier("red_mushroom_block"));
        add(new Identifier("brown_mushroom_block"));
        add(new Identifier("brown_mushroom_block"));
        add(new Identifier("mushroom_stem"));
        add(new Identifier("lily_pad"));
        add(new Identifier("stone"));
    }};

    public static List<BlockSoundGroup> soundGroups = new ArrayList<>() {{
        add(MUSHROOM);
        add(MUSHROOM);
        add(MUSHROOM);
        add(MUSHROOM);
        add(MUSHROOM);
        add(MUSHROOM);
        add(LILYPAD);
        add(BlockSoundGroup.DEEPSLATE);
    }};
}
