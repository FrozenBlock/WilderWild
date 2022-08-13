package net.frozenblock.wilderwild.mixin.server;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerBuilder;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderConstants;
import net.minecraft.SharedConstants;
import net.minecraft.datafixer.DataFixerPhase;
import net.minecraft.datafixer.Schemas;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Schemas.class)
public abstract class SchemasMixin {

    @Shadow
    private static void build(DataFixerBuilder builder) {
    }

    @Inject(method = "getFixer", at = @At("RETURN"))
    private static void getFixer(CallbackInfoReturnable<DataFixer> cir) {

    }

    @Inject(method = "create", at = @At("HEAD"), cancellable = true)
    private static synchronized void create(CallbackInfoReturnable<DataFixer> cir) {
        DataFixerBuilder builder = new DataFixerBuilder(SharedConstants.getGameVersion().getSaveVersion().getId() + 100);
        build(builder);
        WilderWild.doDataFixers(builder);

        boolean bl = switch(WilderConstants.dataFixerPhase) {
            case UNINITIALIZED_OPTIMIZED -> true;
            case UNINITIALIZED_UNOPTIMIZED -> false;
            default -> throw new IllegalStateException("Already loaded");
        };
        WilderConstants.dataFixerPhase = bl ? DataFixerPhase.INITIALIZED_OPTIMIZED : DataFixerPhase.INITIALIZED_UNOPTIMIZED;
        WilderWild.LOGGER.info("Building Wilder Wild's {} datafixer", bl ? "optimized" : "unoptimized");
        cir.setReturnValue(bl ? builder.buildOptimized(Util.getBootstrapExecutor()) : builder.buildUnoptimized());
        cir.cancel();
    }
}
