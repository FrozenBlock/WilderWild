/*
 * Copyright 2022 QuiltMC
 * Modified to work on Fabric
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.quiltmc.qsl.frozenblock.datafixerupper.mixin.client;

import net.minecraft.client.HotbarManager;
import net.minecraft.nbt.CompoundTag;
import org.quiltmc.qsl.frozenblock.datafixerupper.impl.QuiltDataFixesInternals;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Modified to work on Fabric
 */
@Mixin(value = HotbarManager.class, priority = 1001)
public abstract class HotbarStorageMixin {
    @Inject(
            method = "save",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtIo;write(Lnet/minecraft/nbt/CompoundTag;Ljava/io/File;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void addModDataVersions(CallbackInfo ci, CompoundTag compound) {
        QuiltDataFixesInternals.get().addModDataVersions(compound);
    }
}