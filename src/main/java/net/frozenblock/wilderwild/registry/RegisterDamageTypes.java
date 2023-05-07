package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

public class RegisterDamageTypes {

	public static final ResourceKey<DamageType> ANCIENT_HORN = bind("ancient_horn");
	public static final ResourceKey<DamageType> PRICKLY_PEAR = bind("prickly_pear");
	public static final ResourceKey<DamageType> TUMBLEWEED = bind("tumbleweed");

	public static void init() {
	}

	public static void bootstrap(BootstapContext<DamageType> context) {
		context.register(ANCIENT_HORN, new DamageType("ancient_horn", 0.1F));
		context.register(PRICKLY_PEAR, new DamageType("prickly_pear", 0.1F));
		context.register(TUMBLEWEED, new DamageType("tumbleweed", DamageScaling.ALWAYS, 2F));
	}

	private static ResourceKey<DamageType> bind(String path) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, WilderSharedConstants.id(path));
	}
}
