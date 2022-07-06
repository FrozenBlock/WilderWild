package net.frozenblock.wilderwild.entity.ai;

import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.passive.AxolotlEntity;

public class WardenMoveControl extends AquaticMoveControl {

    public WardenMoveControl(WardenEntity warden) {
        super(warden, 85, 10, 0.1F, 1.0F, false);
    }
}
