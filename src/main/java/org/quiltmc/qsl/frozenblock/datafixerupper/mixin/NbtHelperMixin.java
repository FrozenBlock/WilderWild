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

package org.quiltmc.qsl.frozenblock.datafixerupper.mixin;

import com.mojang.datafixers.DataFixer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;

import org.quiltmc.qsl.frozenblock.datafixerupper.impl.QuiltDataFixesInternals;

/**
 * Modified to work on Fabric
 */
@Mixin(value = NbtUtils.class, priority = 1001)
public abstract class NbtHelperMixin {
    @Inject(
            method = "update(Lcom/mojang/datafixers/DataFixer;Lnet/minecraft/util/datafix/DataFixTypes;Lnet/minecraft/nbt/CompoundTag;II)Lnet/minecraft/nbt/CompoundTag;",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void updateDataWithFixers(DataFixer fixer, DataFixTypes fixTypes, CompoundTag compound,
                                             int oldVersion, int targetVersion, CallbackInfoReturnable<CompoundTag> cir) {
        cir.setReturnValue(QuiltDataFixesInternals.get().updateWithAllFixers(fixTypes, cir.getReturnValue()));
    }
}