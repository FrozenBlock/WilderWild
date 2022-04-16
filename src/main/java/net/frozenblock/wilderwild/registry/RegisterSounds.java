package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegisterSounds {

    public static SoundEvent BLOCK_SCULK_JAW_CLAMP = new SoundEvent(new Identifier(WilderWild.MOD_ID, "block.sculk_jaw.clamp"));
    public static SoundEvent BLOCK_SCULK_ECHOER_RECEIVE_VIBRATION = new SoundEvent(new Identifier(WilderWild.MOD_ID, "block.sculk_echoer.receive_vibration"));
    public static SoundEvent MUSIC_DISC_BENEATH = new SoundEvent(new Identifier(WilderWild.MOD_ID, "music_disc.beneath"));
    public static SoundEvent MUSIC_DISC_GOATHORN_SYMPHONY = new SoundEvent(new Identifier(WilderWild.MOD_ID, "music_disc.goathorn_symphony"));

    public static void RegisterSounds() {
        BLOCK_SCULK_JAW_CLAMP = register(BLOCK_SCULK_JAW_CLAMP.getId());
        BLOCK_SCULK_ECHOER_RECEIVE_VIBRATION = register(BLOCK_SCULK_ECHOER_RECEIVE_VIBRATION.getId());
        MUSIC_DISC_BENEATH = register(MUSIC_DISC_BENEATH.getId());
        MUSIC_DISC_GOATHORN_SYMPHONY = register(MUSIC_DISC_GOATHORN_SYMPHONY.getId());

    }

    private static SoundEvent register(Identifier id) {
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

}
