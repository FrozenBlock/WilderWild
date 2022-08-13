package net.frozenblock.wilderwild.mixin.server;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerBuilder;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.SharedConstants;
import net.minecraft.datafixer.Schemas;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Schemas.class)
public class SchemasMixin {

    @Inject(method = "create", at = @At("HEAD"))
    private static synchronized void create(CallbackInfoReturnable<DataFixer> cir) {
        DataFixerBuilder dataFixerBuilder = new DataFixerBuilder(SharedConstants.getGameVersion().getWorldVersion() + 100);
        WilderWild.doDataFixers(dataFixerBuilder);
    }
}
