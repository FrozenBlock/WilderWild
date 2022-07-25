package net.frozenblock.wilderwild.mixin.server;

import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.datafixer.Schemas;
import net.minecraft.datafixer.fix.BlockNameFix;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.function.BiFunction;

@Mixin(Schemas.class)
@Debug(export = true)
public class SchemasMixin {

    @Shadow @Final private static BiFunction<Integer, Schema, Schema> EMPTY_IDENTIFIER_NORMALIZE;

    @Inject(method = "build", at = @At("TAIL"))
    private static void build(DataFixerBuilder builder, CallbackInfo ci) {
        Schema schema69420 = builder.addSchema(3097, EMPTY_IDENTIFIER_NORMALIZE);
        builder.addFixer(
                BlockNameFix.create(
                        schema69420,
                        "white_dandelion block renamer",
                        id -> Objects.equals(IdentifierNormalizingSchema.normalize(id), WilderWild.MOD_ID + ":" + "white_dandelion") ? WilderWild.MOD_ID + ":" + "blooming_dandelion" : id
                ));
    }
}
