package net.frozenblock.wilderwild.mixin.worldgen;

import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = JigsawStructure.class, priority = 999)
public class JigsawStructureMixin {

    @ModifyConstant(method = {"m_ywxdqoox","method_41662"}, constant = @Constant(intValue = 7), require = 1)
    private static int frozenLib_expandSize(int constant) {
        return 20;
    }

}
