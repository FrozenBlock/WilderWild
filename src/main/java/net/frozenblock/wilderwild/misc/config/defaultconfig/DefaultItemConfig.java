package net.frozenblock.wilderwild.misc.config.defaultconfig;

import net.frozenblock.wilderwild.entity.AncientHornProjectile;

public class DefaultItemConfig {

	public static class AncientHornConfig {
		public static final boolean ANCIENT_HORN_CAN_SUMMON_WARDEN = true;
		public static final int ANCIENT_HORN_LIFESPAN = AncientHornProjectile.DEFAULT_LIFESPAN;
		public static final int ANCIENT_HORN_MOB_DAMAGE = 22;
		public static final int ANCIENT_HORN_PLAYER_DAMAGE = 15;
		public static final boolean ANCIENT_HORN_SHATTERS_GLASS = false;
	}

	public static final boolean PROJECTILE_BREAK_PARTICLES = true;
}
