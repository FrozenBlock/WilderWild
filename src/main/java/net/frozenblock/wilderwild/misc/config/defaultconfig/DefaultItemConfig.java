package net.frozenblock.wilderwild.misc.config.defaultconfig;

import net.frozenblock.wilderwild.entity.AncientHornProjectile;

public class DefaultItemConfig {

	public static class AncientHornConfig {
		public static final boolean ancientHornCanSummonWarden = true;
		public static final int ancientHornLifespan = AncientHornProjectile.DEFAULT_LIFESPAN;
		public static final int ancientHornMobDamage = 22;
		public static final int ancientHornPlayerDamage = 15;
		public static final boolean ancientHornShattersGlass = false;
	}

	public static final boolean projectileBreakParticles = true;
}
