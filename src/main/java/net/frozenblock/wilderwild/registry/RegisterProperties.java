package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.block.entity.HangingTendrilPhase;
import net.frozenblock.wilderwild.block.entity.SculkEchoerPhase;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;

public class RegisterProperties extends Properties {

    public static final EnumProperty<SculkEchoerPhase> SCULK_ECHOER_PHASE = EnumProperty.of("sculk_echoer_phase", SculkEchoerPhase.class);
    public static final BooleanProperty ECHOING = BooleanProperty.of("echoing");

    public static final IntProperty PILLAR_HEIGHT_LEFT = IntProperty.of("pillar_height_left", 0, 15);
    public static final BooleanProperty UPSIDE_DOWN = BooleanProperty.of("upside_down");
    public static final IntProperty TOTAL_HEIGHT = IntProperty.of("total_pillar_height", 0, 16);

    public static final EnumProperty<HangingTendrilPhase> HANGING_TENDRIL_PHASE = EnumProperty.of("hanging_tendril_phase", HangingTendrilPhase.class);
    public static final BooleanProperty TWITCHING = BooleanProperty.of("twitching");
    public static final BooleanProperty WRINGING_OUT = BooleanProperty.of("wringing_out");

    public static final IntProperty SOULS_TAKEN = IntProperty.of("souls", 0, 3);

}
