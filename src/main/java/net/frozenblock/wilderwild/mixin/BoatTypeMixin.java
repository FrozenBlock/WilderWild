package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.misc.CustomBoatType;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.vehicle.BoatEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(BoatEntity.Type.class)
public class BoatTypeMixin {

    //CREDIT TO nyuppo/fabric-boat-example ON GITHUB

    @SuppressWarnings("InvokerTarget")
    @Invoker("<init>")
    private static BoatEntity.Type newType(String internalName, int internalId, Block baseBlock, String name) {
        throw new AssertionError();
    }

    @SuppressWarnings("ShadowTarget")
    @Final
    @Shadow
    @Mutable
    private static BoatEntity.Type[] field_7724;

    @Inject(method = "<clinit>", at = @At(value = "FIELD",
            opcode = Opcodes.PUTSTATIC,
            target = "Lnet/minecraft/entity/vehicle/BoatEntity$Type;field_7724:[Lnet/minecraft/entity/vehicle/BoatEntity$Type;",
            shift = At.Shift.AFTER))
    private static void addCustomBoatType(CallbackInfo ci) {
        var types = new ArrayList<>(Arrays.asList(field_7724));
        var last = types.get(types.size() - 1);

        var baobab = newType("BAOBAB", last.ordinal() + 1, RegisterBlocks.BAOBAB_PLANKS, "baobab");
        CustomBoatType.BAOBAB = baobab;
        types.add(baobab);

        var cypress = newType("CYPRESS", last.ordinal() + 2, RegisterBlocks.CYPRESS_PLANKS, "cypress");
        CustomBoatType.CYPRESS = cypress;
        types.add(cypress);
        field_7724 = types.toArray(new BoatEntity.Type[0]);
    }
}
