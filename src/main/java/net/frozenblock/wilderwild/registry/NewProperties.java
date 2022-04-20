package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.block.entity.SculkEchoerPhase;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;

public class NewProperties extends Properties {

    public static final EnumProperty<SculkEchoerPhase> SCULK_ECHOER_PHASE = EnumProperty.of("sculk_echoer_phase", SculkEchoerPhase.class);
    public static final BooleanProperty ECHOING = BooleanProperty.of("echoing");

    public static final IntProperty PILLAR_HEIGHT_LEFT = IntProperty.of("pillar_height_left", 0, 15);
    public static final BooleanProperty UPSIDE_DOWN = BooleanProperty.of("upside_down");
    public static final IntProperty TOTAL_HEIGHT = IntProperty.of("total_pillar_height", 0, 16);

}
