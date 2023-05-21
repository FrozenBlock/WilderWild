package net.frozenblock.wilderwild.misc.interfaces;

import java.util.List;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public interface BetaBeachEntrypoint {

	void addGravelBeachBiomes(List<ResourceKey<Biome>> context);

	void addSandBeachBiomes(List<ResourceKey<Biome>> context);

	void addMultiLayerBeachBiomes(List<ResourceKey<Biome>> context);

}
