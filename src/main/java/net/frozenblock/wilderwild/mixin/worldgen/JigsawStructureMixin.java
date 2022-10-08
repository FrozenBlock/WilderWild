package net.frozenblock.wilderwild.mixin.worldgen;

import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(value = JigsawStructure.class, priority = 999)
public class JigsawStructureMixin {

    @ModifyConstant(method = "method_41662(Lcom/mojang/serialization/codecs/RecordCodecBuilder$Instance;)Lcom/mojang/datafixers/kinds/App;",
            constant = @Constant(intValue = 7))
    private static int expandSize(int constant) {
        return 20;
    }
}
