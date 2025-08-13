/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.mixin.client.firefly;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.renderer.entity.state.FireflyRenderState;
import net.frozenblock.wilderwild.client.renderer.impl.FireflyNodeCollectorInterface;
import net.frozenblock.wilderwild.client.renderer.impl.FireflyNodeStorageInterface;
import net.frozenblock.wilderwild.client.renderer.impl.FireflySubmit;
import net.minecraft.client.renderer.SubmitNodeStorage;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(SubmitNodeStorage.class)
public class SubmitNodeStorageMixin implements FireflyNodeCollectorInterface, FireflyNodeStorageInterface {

	@Unique
	private final List<FireflySubmit> wilderWild$fireflySubmits = new ArrayList<>();

	@Unique
	@Override
	public void wilderWild$submitFirefly(@NotNull PoseStack poseStack, FireflyRenderState renderState, boolean renderColor, Quaternionf cameraOrientation) {
		Vector3f position = poseStack.last().pose().transformPosition(new Vector3f());
		this.wilderWild$fireflySubmits.add(new FireflySubmit(poseStack.last().copy(), renderState, renderColor, cameraOrientation, renderColor ? 1 : 0, position));
	}

	@Override
	public List<FireflySubmit> wilderWild$getFireflySubmits() {
		return this.wilderWild$fireflySubmits;
	}

	@Inject(method = "clear", at = @At("HEAD"))
	public void wilderWild$clearFireflySubmits(CallbackInfo info) {
		this.wilderWild$fireflySubmits.clear();
	}
}
