package net.frozenblock.wilderwild.entity.render;

import net.minecraft.entity.AnimationState;

public interface WardenAnimationInterface {

    AnimationState dyingAnimationState = new AnimationState();

    AnimationState getDyingAnimationState();
}
