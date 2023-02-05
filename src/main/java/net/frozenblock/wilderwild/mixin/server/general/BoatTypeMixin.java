package net.frozenblock.wilderwild.mixin.server.general;

import java.util.ArrayList;
import java.util.Arrays;
import net.frozenblock.wilderwild.misc.WilderEnumValues;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.block.Block;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Boat.Type.class)
public class BoatTypeMixin {

    //CREDIT TO nyuppo/fabric-boat-example ON GITHUB

    @SuppressWarnings("InvokerTarget")
    @Invoker("<init>")
    private static Boat.Type wilderWild$newType(String internalName, int internalId, Block baseBlock, String name) {
		throw new AssertionError("Mixin injection failed - Wilder Wild BoatTypeMixin.");
    }

    @SuppressWarnings("ShadowTarget")
    @Final
    @Shadow
    @Mutable
    private static Boat.Type[] $VALUES;

    @Inject(method = "<clinit>", at = @At(value = "FIELD",
            opcode = Opcodes.PUTSTATIC,
            target = "Lnet/minecraft/world/entity/vehicle/Boat$Type;$VALUES:[Lnet/minecraft/world/entity/vehicle/Boat$Type;",
            shift = At.Shift.AFTER))
    private static void wilderWild$addCustomBoatType(CallbackInfo info) {
        var types = new ArrayList<>(Arrays.asList($VALUES));
        var last = types.get(types.size() - 1);

        var baobab = wilderWild$newType("WILDERWILDBAOBAB", last.ordinal() + 1, RegisterBlocks.BAOBAB_PLANKS, "wilderwildbaobab");
        WilderEnumValues.BAOBAB = baobab;
        types.add(baobab);

        var cypress = wilderWild$newType("WILDERWILDCYPRESS", last.ordinal() + 2, RegisterBlocks.CYPRESS_PLANKS, "wilderwildcypress");
        WilderEnumValues.CYPRESS = cypress;
        types.add(cypress);

		var palm = wilderWild$newType("WILDERWILDPALM", last.ordinal() + 3, RegisterBlocks.PALM_PLANKS, "wilderwildpalm");
		WilderEnumValues.PALM = palm;
		types.add(palm);

        $VALUES = types.toArray(new Boat.Type[0]);
    }

}
