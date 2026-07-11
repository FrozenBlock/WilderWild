package net.frozenblock.wilderwild.mixin.client.equipment;

import java.util.ArrayList;
import java.util.Arrays;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.resources.model.WWEquipmentClientInfoLayerType;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(EquipmentClientInfo.LayerType.class)
public class EquipmentClientInfoLayerTypeMixin {

	@SuppressWarnings("InvokerTarget")
	@Invoker("<init>")
	private static EquipmentClientInfo.LayerType newLayerType(String internalName, int ordinal, String name) {
		throw new AssertionError("Mixin injection failed - Wilder Wild EquipmentClientInfoLayerTypeMixin");
	}

	@SuppressWarnings("ShadowTarget")
	@Shadow
	@Final
	@Mutable
	private static EquipmentClientInfo.LayerType[] $VALUES;

	@Inject(
		method = "<clinit>",
		at = @At(
			value = "FIELD",
			opcode = Opcodes.PUTSTATIC,
			target = "Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;$VALUES:[Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;",
			shift = At.Shift.AFTER
		)
	)
	private static void wilderwild$addLayerTypes(CallbackInfo info) {
		final var layerTypes = new ArrayList<>(Arrays.asList($VALUES));
		int ordinal = layerTypes.getLast().ordinal() + 1;

		final EquipmentClientInfo.LayerType ostrichSaddle = newLayerType("WILDERWILD_OSTRICH_SADDLE", ordinal++, WWConstants.safeString("ostrich_saddle"));
		WWEquipmentClientInfoLayerType.WILDERWILD_OSTRICH_SADDLE = ostrichSaddle;

		final EquipmentClientInfo.LayerType ostrichZombieSaddle = newLayerType("WILDERWILD_OSTRICH_ZOMBIE_SADDLE", ordinal++, WWConstants.safeString("ostrich_zombie_saddle"));
		WWEquipmentClientInfoLayerType.WILDERWILD_OSTRICH_ZOMBIE_SADDLE = ostrichZombieSaddle;

		$VALUES = layerTypes.toArray(new EquipmentClientInfo.LayerType[0]);
	}
}
