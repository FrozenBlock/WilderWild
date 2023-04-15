package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.advancement.FireflyBottleTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class RegisterCriteria {

	public static final FireflyBottleTrigger FIREFLY_BOTTLE = CriteriaTriggers.register(new FireflyBottleTrigger());

	public static void register() {
		CriteriaTriggers.register(FIREFLY_BOTTLE);
	}

}
