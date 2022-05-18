package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegisterSounds {
    
    public static final SoundEvent BLOCK_MUSHROOM_PLACE = register( "block.mushroom.place");
    public static final SoundEvent BLOCK_MUSHROOM_HIT = register("block.mushroom.hit");
    public static final SoundEvent BLOCK_MUSHROOM_BREAK = register( "block.mushroom.break");
    public static final SoundEvent BLOCK_MUSHROOM_STEP = register("block.mushroom.step");
    public static final SoundEvent BLOCK_MUSHROOM_FALL = register("block.mushroom.fall");

    public static final SoundEvent BLOCK_OSSEOUS_SCULK_PLACE = register( "block.osseous_sculk.place");
    public static final SoundEvent BLOCK_OSSEOUS_SCULK_HIT = register("block.osseous_sculk.hit");
    public static final SoundEvent BLOCK_OSSEOUS_SCULK_BREAK = register( "block.osseous_sculk.break");
    public static final SoundEvent BLOCK_OSSEOUS_SCULK_STEP = register("block.osseous_sculk.step");
    public static final SoundEvent BLOCK_OSSEOUS_SCULK_FALL = register("block.osseous_sculk.fall");

    public static final SoundEvent BLOCK_HANGING_TENDRIL_PLACE = register( "block.hanging_tendril.place");
    public static final SoundEvent BLOCK_HANGING_TENDRIL_HIT = register("block.hanging_tendril.hit");
    public static final SoundEvent BLOCK_HANGING_TENDRIL_BREAK = register("block.hanging_tendril.break");
    public static final SoundEvent BLOCK_HANGING_TENDRIL_STEP = register("block.hanging_tendril.step");
    public static final SoundEvent BLOCK_HANGING_TENDRIL_FALL = register("block.hanging_tendril.fall");
    public static final SoundEvent BLOCK_HANGING_TENDRIL_WRING = register( "block.hanging_tendril.wring");

    public static final SoundEvent BLOCK_ECHO_GLASS_PLACE = register( "block.echo_glass.place");
    public static final SoundEvent BLOCK_ECHO_GLASS_HIT = register( "block.echo_glass.hit");
    public static final SoundEvent BLOCK_ECHO_GLASS_BREAK = register( "block.echo_glass.break");
    public static final SoundEvent BLOCK_ECHO_GLASS_STEP = register( "block.echo_glass.step");
    public static final SoundEvent BLOCK_ECHO_GLASS_FALL = register( "block.echo_glass.fall");
    public static final SoundEvent BLOCK_ECHO_GLASS_CRACK = register( "block.echo_glass.crack");
    public static final SoundEvent BLOCK_ECHO_GLASS_REPAIR = register("block.echo_glass.repair");

    public static final SoundEvent BLOCK_SCULK_JAW_CLAMP = register("block.sculk_jaw.clamp");
    public static final SoundEvent BLOCK_SCULK_JAW_RETRACT = register("block.sculk_jaw.retract");

    public static final SoundEvent BLOCK_SCULK_SENSOR_HICCUP = register( "block.sculk_sensor.hiccup");

    public static final SoundEvent BLOCK_SCULK_ECHOER_ECHO = register( "block.sculk_echoer.echo");

    public static final SoundEvent ENTITY_FIREFLY_HURT = register("entity.firefly.hurt");

    public static final SoundEvent FLOATING_SCULK_BUBBLE_POP = register("particle.floating_sculk_bubble.pop");
    public static final SoundEvent FLOATING_SCULK_BUBBLE_BIG_POP = register("particle.floating_sculk_bubble.big_pop");

    public static final SoundEvent ANCIENT_HORN_CALL= register("item.ancient_horn.call");
    public static final SoundEvent ANCIENT_HORN_VIBRATION_DISSAPATE = register("entity.ancient_horn_projectile.dissipate");

    public static final SoundEvent MUSIC_OVERWORLD_BIRCH_FOREST = register("music.overworld.birch_forest");

    public static final SoundEvent MUSIC_DISC_BENEATH = register("music_disc.beneath");
    public static final SoundEvent MUSIC_DISC_GOATHORN_SYMPHONY = register("music_disc.goathorn_symphony");
    public static final SoundEvent MUSIC_DISC_THE_OTHER_SIDE = register("music_disc.the_other_side");

    public static SoundEvent register(String string) {
        return Registry.register(Registry.SOUND_EVENT, new Identifier(WilderWild.MOD_ID, string), new SoundEvent(new Identifier(WilderWild.MOD_ID, string)));
    }

    public static void init() {
        //Loads the class for registry
    }

}
