package net.frozenblock.wilderwild.item.cooldown;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Optional;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class SaveableItemCooldowns {

	public static ArrayList<SaveableCooldownInstance> makeSaveableCooldownInstanceList(@NotNull ServerPlayer player) {
		ArrayList<SaveableCooldownInstance> saveableCooldownInstances = new ArrayList<>();
		int tickCount = player.getCooldowns().tickCount;
		player.getCooldowns().cooldowns.forEach(
				((item, cooldownInstance) -> {
					if (item == RegisterItems.ANCIENT_HORN || WilderSharedConstants.config().itemCooldownsSave()) {
						saveableCooldownInstances.add(SaveableCooldownInstance.makeFromCooldownInstance(item, cooldownInstance, tickCount));
					}
				})
		);
		return saveableCooldownInstances;
	}

	public static void saveCooldowns(@NotNull CompoundTag tag,  @NotNull ServerPlayer player) {
		Logger logger = WilderSharedConstants.LOGGER;
		SaveableCooldownInstance.CODEC.listOf()
				.encodeStart(NbtOps.INSTANCE, makeSaveableCooldownInstanceList(player))
				.resultOrPartial(logger::error)
				.ifPresent((savedItemCooldownsNbt) -> tag.put("WilderWildSavedItemCooldowns", savedItemCooldownsNbt));
	}

	public static ArrayList<SaveableCooldownInstance> readCooldowns(@NotNull CompoundTag tag) {
		ArrayList<SaveableCooldownInstance> saveableCooldownInstances = new ArrayList<>();
		if (tag.contains("WilderWildSavedItemCooldowns", 9)) {
			Logger logger = WilderSharedConstants.LOGGER;
			SaveableCooldownInstance.CODEC.listOf().parse(new Dynamic<>(NbtOps.INSTANCE, tag.getList("WilderWildSavedItemCooldowns", 10)))
					.resultOrPartial(logger::error)
					.ifPresent(saveableCooldownInstances::addAll);
		}
		return saveableCooldownInstances;
	}

	public static void setCooldowns(@NotNull ArrayList<SaveableCooldownInstance> saveableCooldownInstances, @NotNull ServerPlayer player) {
		ItemCooldowns itemCooldowns = player.getCooldowns();
		for (SaveableCooldownInstance saveableCooldownInstance : saveableCooldownInstances) {
			int cooldownLeft = saveableCooldownInstance.getCooldownLeft();
			Optional<Item> item = Registry.ITEM.getOptional(saveableCooldownInstance.getItemResourceLocation());
			item.ifPresent(value -> itemCooldowns.cooldowns.put(value, new ItemCooldowns.CooldownInstance(itemCooldowns.tickCount - (saveableCooldownInstance.getTotalCooldownTime() - cooldownLeft), itemCooldowns.tickCount + cooldownLeft)));
		}
	}

	public static class SaveableCooldownInstance {

		private final ResourceLocation itemResourceLocation;
		private final int cooldownLeft;
		private final int totalCooldownTime;

		public static final Codec<SaveableCooldownInstance> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				ResourceLocation.CODEC.fieldOf("ItemResourceLocation").forGetter(SaveableCooldownInstance::getItemResourceLocation),
				Codec.INT.fieldOf("CooldownLeft").orElse(0).forGetter(SaveableCooldownInstance::getCooldownLeft),
				Codec.INT.fieldOf("TotalCooldownTime").orElse(0).forGetter(SaveableCooldownInstance::getTotalCooldownTime)
		).apply(instance, SaveableCooldownInstance::new));


		public SaveableCooldownInstance(ResourceLocation itemResourceLocation, int cooldownLeft, int totalCooldownTime) {
			this.itemResourceLocation = itemResourceLocation;
			this.cooldownLeft = cooldownLeft;
			this.totalCooldownTime = totalCooldownTime;
		}

		public static SaveableCooldownInstance makeFromCooldownInstance(@NotNull Item item, @NotNull ItemCooldowns.CooldownInstance cooldownInstance, int tickCount) {
			ResourceLocation resourceLocation = Registry.ITEM.getKey(item);
			int cooldownLeft = cooldownInstance.endTime - tickCount;
			int totalCooldownTime = cooldownInstance.endTime - cooldownInstance.startTime;
			return new SaveableCooldownInstance(resourceLocation, cooldownLeft, totalCooldownTime);
		}

		public ResourceLocation getItemResourceLocation() {
			return this.itemResourceLocation;
		}

		public int getCooldownLeft() {
			return this.cooldownLeft;
		}

		public int getTotalCooldownTime() {
			return this.totalCooldownTime;
		}

	}

}
