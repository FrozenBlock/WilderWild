package net.frozenblock.wilderwild.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Either;
import java.util.Collection;
import java.util.List;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// todo remove this once multiloader data is done
@Mixin(TagLoader.class)
public class TempTagLoaderMixin<T> {

	@Inject(method = "tryBuildTag", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z"))
	private void ignoreInvalidTags(TagEntry.Lookup<T> lookup, List<TagLoader.EntryWithSource> list, CallbackInfoReturnable<Either<Collection<TagLoader.EntryWithSource>, Collection<T>>> cir, @Local(ordinal = 1) List<TagLoader.EntryWithSource> list2) {
		list2.clear();
	}

}
