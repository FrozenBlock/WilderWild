package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.advancement.FireflyBottleTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public final class RegisterCriteria {
	public static final FireflyBottleTrigger FIREFLY_BOTTLE = CriteriaTriggers.register(new FireflyBottleTrigger());

	private RegisterCriteria() {
		throw new UnsupportedOperationException("RegisterCriteria contains only static declarations.");
	}

	public static void init() {
	}

}
