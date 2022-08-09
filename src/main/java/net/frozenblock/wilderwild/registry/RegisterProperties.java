package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.misc.FlowerColors;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;

public class RegisterProperties extends Properties {

    //Osseous Sculk
    public static final IntProperty PILLAR_HEIGHT_LEFT = IntProperty.of("pillar_height_left", 0, 15);
    public static final BooleanProperty UPSIDE_DOWN = BooleanProperty.of("upside_down");
    public static final IntProperty TOTAL_HEIGHT = IntProperty.of("total_pillar_height", 0, 16);
    //Hanging Tendril
    public static final BooleanProperty TWITCHING = BooleanProperty.of("twitching");
    public static final BooleanProperty WRINGING_OUT = BooleanProperty.of("wringing_out");
    //Echo Glass
    public static final IntProperty DAMAGE = IntProperty.of("damage", 0, 3);
    //Shelf Fungus
    public static final IntProperty FUNGUS_STAGE = IntProperty.of("shelf_fungus_stage", 1, 4);
    //Termite Mound
    public static final BooleanProperty NATURAL = BooleanProperty.of("natural");
    //Glory of The Snow
    public static final EnumProperty<FlowerColors> FLOWER_COLOR = EnumProperty.of("flower_color", FlowerColors.class);
    //Cypress Roots
    public static final IntProperty ROOTS = IntProperty.of("roots", 1, 4);

    //Vanilla Blocks
    public static final IntProperty SOULS_TAKEN = IntProperty.of("souls_taken", 0, 2); //Sculk Shrieker
    public static final BooleanProperty HICCUPPING = BooleanProperty.of("hiccupping"); //Sculk Sensor

}
