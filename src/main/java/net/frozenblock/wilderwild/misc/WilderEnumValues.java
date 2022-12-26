package net.frozenblock.wilderwild.misc;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.Boat;

public final class WilderEnumValues {
    //CREDIT TO nyuppo/fabric-boat-example ON GITHUB
    static {
        Boat.Type.values();
    }

    public static Boat.Type BAOBAB;
    public static Boat.Type CYPRESS;
    public static Boat.Type PALM;

	static {
		MobCategory.values();
	}

	public static MobCategory FIREFLIES;
	public static MobCategory JELLYFISH;
}
