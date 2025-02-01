package net.frozenblock.wilderwild.mixin.datagen;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.data.models.BlockModelGenerators;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockModelGenerators.class)
public class BlockModelGeneratorsMixin {

	@WrapOperation(
		method = "<init>",
		at = @At(
			value = "INVOKE",
			target = "Lcom/google/common/collect/ImmutableMap$Builder;put(Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableMap$Builder;",
			ordinal = 0
		)
	)
	public ImmutableMap.Builder wilderWild$addRotatedGabbroGeneration(
		ImmutableMap.Builder instance, Object key, Object value, Operation<ImmutableMap.Builder> original
	) {
		original.call(instance, WWBlocks.GABBRO, value);
		return original.call(instance, key, value);
	}
}
