package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(StructurePlaceSettings.class)
public class StructurePlaceSettingsMixin {

    @Inject(method = "getProcessors", at = @At("TAIL"), cancellable = true)
    public void getProcessors(CallbackInfoReturnable<List<StructureProcessor>> info) {
        List<StructureProcessor> processors = new ArrayList<>(info.getReturnValue());
        boolean has = false;
        for (StructureProcessor processor : processors) {
            for (StructureProcessor city : ProcessorLists.ANCIENT_CITY_GENERIC_DEGRADATION.value().list()) {
                if (city == processor) {
                    has = true;
                    break;
                }
            }
            for (StructureProcessor city : ProcessorLists.ANCIENT_CITY_START_DEGRADATION.value().list()) {
                if (city == processor) {
                    has = true;
                    break;
                }
            }
            for (StructureProcessor city : ProcessorLists.ANCIENT_CITY_WALLS_DEGRADATION.value().list()) {
                if (city == processor) {
                    has = true;
                    break;
                }
            }
        }
        if (has) {
            processors.addAll(WilderWild.CHEST_TO_STONE.value().list());
            info.setReturnValue(processors);
            info.cancel();
        }
    }

}
