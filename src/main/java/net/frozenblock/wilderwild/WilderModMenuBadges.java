package net.frozenblock.wilderwild;

import java.util.ArrayList;
import net.frozenblock.modmenu.api.FrozenModMenuEntrypoint;
import net.frozenblock.modmenu.impl.FrozenModMenuBadge;

public class WilderModMenuBadges extends FrozenModMenuEntrypoint {

	@Override
	public ArrayList<FrozenModMenuBadge> newBadges() {
		ArrayList<FrozenModMenuBadge> list = new ArrayList<>();
		list.add(FrozenModMenuEntrypoint.createBadge("Indev", 0xFF71BC00, 0xFF1C5400, "indev"));
		return list;
	}

}
