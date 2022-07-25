package net.frozenblock.wilderwild.mixin.server;

import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.datafixer.Schemas;
import net.minecraft.datafixer.fix.BlockNameFix;
import net.minecraft.datafixer.fix.ItemNameFix;
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
        Schema schema = builder.addSchema(3097, EMPTY_IDENTIFIER_NORMALIZE);
        wildBlockItemRenamer(builder, schema, "white_dandelion", "seeding_dandelion");
        wildBlockItemRenamer(builder, schema, "blooming_dandelion", "seeding_dandelion");
        wildBlockRenamer(builder, schema, "potted_white_dandelion", "potted_seeding_dandelion");
        wildBlockRenamer(builder, schema, "potted_blooming_dandelion", "potted_seeding_dandelion");
        wildBlockItemRenamer(builder, schema, "floating_moss", "algae");

        //TESTING
        //wildBlockItemRenamer(builder, schema, "test_1", "test_2");
    }

    private static void wildBlockItemRenamer(DataFixerBuilder builder, Schema schema, String startString, String endString) {
        wildBlockRenamer(builder, schema, startString, endString);
        wildItemRenamer(builder, schema, startString, endString);
    }

    private static void wildBlockRenamer(DataFixerBuilder builder, Schema schema, String startString, String endString) {
        builder.addFixer(
                BlockNameFix.create(schema,startString + " block renamer",
                        id -> Objects.equals(IdentifierNormalizingSchema.normalize(id), WilderWild.string(startString)) ? WilderWild.string(endString) : id
                ));
    }

    private static void wildItemRenamer(DataFixerBuilder builder, Schema schema, String startString, String endString) {
        builder.addFixer(
                ItemNameFix.create(schema,startString + " block renamer",
                        id -> Objects.equals(IdentifierNormalizingSchema.normalize(id), WilderWild.string(startString)) ? WilderWild.string(endString) : id
                ));
    }

}
