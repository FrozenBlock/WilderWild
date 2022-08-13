
/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 TheoreticallyUseful
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DefaultedRegistry.class)
public abstract class WilderIDReplacerMixin<T> {
    //From UpdateFixerUpper by TheoreticallyUseful, usage allowed through MIT Licence.
    //GitHub Link: https://github.com/SilverAndro/UpdateFixerUpper

    @Shadow
    public abstract @NotNull T get(@Nullable ResourceLocation id);

    private static ResourceLocation capturedId;
    private static boolean isInLookup = false;

    @Inject(method = "get(Lnet/minecraft/util/Identifier;)Ljava/lang/Object;", at = @At("HEAD"))
    void setCapturedId(ResourceLocation id, CallbackInfoReturnable<T> info) {
        capturedId = id;
    }

    @Redirect(method = "get(Lnet/minecraft/resources/ResourceLocation;)Ljava/lang/Object;", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceKey;value()Ljava/lang/Object;"))
    T fixMissingFromRegistry(Holder<T> instance) {
        if (capturedId == null || isInLookup) {
            return instance.value();
        }

        isInLookup = true;
        T result = get(WilderWild.DataFixMap.getOrDefault(capturedId.toString(), capturedId));
        isInLookup = false;

        return result;
    }

}
