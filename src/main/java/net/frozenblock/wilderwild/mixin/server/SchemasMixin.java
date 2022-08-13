package net.frozenblock.wilderwild.mixin.server;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerBuilder;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.SharedConstants;
import net.minecraft.datafixer.DataFixerPhase;
import net.minecraft.datafixer.Schemas;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
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

    /**
     * @author
     * @reason
     */
    @Overwrite
    private static synchronized DataFixer create() {
        DataFixerBuilder dataFixerBuilder = new DataFixerBuilder(SharedConstants.getGameVersion().getWorldVersion());
        build(dataFixerBuilder);

        boolean bl = switch(SharedConstants.dataFixerPhase) {
            case UNINITIALIZED_OPTIMIZED -> true;
            case UNINITIALIZED_UNOPTIMIZED -> false;
            default -> throw new IllegalStateException("Already loaded");
        };
        SharedConstants.dataFixerPhase = bl ? DataFixerPhase.INITIALIZED_OPTIMIZED : DataFixerPhase.INITIALIZED_UNOPTIMIZED;
        WilderWild.LOGGER.info("Building Wilder Wild's {} datafixer", bl ? "optimized" : "unoptimized");
        return bl ? dataFixerBuilder.buildOptimized(Util.getBootstrapExecutor()) : dataFixerBuilder.buildUnoptimized();
    }
}
