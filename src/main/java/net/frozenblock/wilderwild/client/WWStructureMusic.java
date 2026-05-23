package net.frozenblock.wilderwild.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.v2.entry.data.ConfigEntryPredicate;
import net.frozenblock.lib.music.api.structure.StructureMusic;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import java.util.List;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class WWStructureMusic {

	public static final ResourceKey<StructureMusic> ANCIENT_CITY_MUSIC = createKey("ancient_city_music");

	public static void bootstrap(BootstrapContext<StructureMusic> context) {
		StructureMusic.register(
			context,
			ANCIENT_CITY_MUSIC,
			List.of(BuiltinStructures.ANCIENT_CITY.identifier()),
			new BackgroundMusic(new Music(SoundEvents.MUSIC_BIOME_DEEP_DARK, 6000, 12000, false)),
			false,
			Optional.of(ConfigEntryPredicate.equalTo(WWAmbienceAndMiscConfig.ANCIENT_CITY_MUSIC, true))
		);
	}

	private static ResourceKey<StructureMusic> createKey(String id) {
		return ResourceKey.create(FrozenLibRegistries.STRUCTURE_MUSIC, WWConstants.id(id));
	}
}
