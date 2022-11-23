package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.FrozenConfig;
import net.frozenblock.wilderwild.WilderWild;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.text;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.tooltip;

@Config(name = "block")
public final class BlockConfig implements ConfigData {

	@ConfigEntry.Gui.CollapsibleObject
	public BlockSoundsConfig blockSounds = new BlockSoundsConfig();
	@ConfigEntry.Gui.CollapsibleObject
	public StoneChestConfig stoneChest = new StoneChestConfig();

	public static class BlockSoundsConfig {
		public boolean cactusSounds = true;
		public boolean claySounds = true;
		public boolean coarseDirtSounds = true;
		public boolean cobwebSounds = true;
		public boolean deadBushSounds = true;
		public boolean flowerSounds = true;
		public boolean frostedIceSounds = true;
		public boolean gravelSounds = true;
		public boolean leafSounds = true;
		public boolean lilyPadSounds = true;
		public boolean mushroomBlockSounds = true;
		public boolean podzolSounds = true;
		public boolean reinforcedDeepslateSounds = true;
		public boolean sugarCaneSounds = true;
		public boolean witherRoseSounds = true;
	}

	public static class StoneChestConfig {
		public int stoneChestTimer = 100;
	}

	public boolean mcLiveSensorTendrils = true;
	public boolean shriekerGargling = true;
	public boolean soulFireSounds = true;

	@Environment(EnvType.CLIENT)
	static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
		var config = WilderWildConfig.get().block;
		var blockSounds = config.blockSounds;
		var stoneChest = config.stoneChest;
		category.setBackground(WilderWild.id("textures/config/block.png"));
		var mcLiveSensorTendrils = category.addEntry(entryBuilder.startBooleanToggle(text("mc_live_sensor_tendrils"), config.mcLiveSensorTendrils)
				.setDefaultValue(false)
				.setSaveConsumer(newValue -> config.mcLiveSensorTendrils = newValue)
				.setYesNoTextSupplier(bool -> text("mc_live_sensor_tendrils." + bool))
				.setTooltip(tooltip("mc_live_sensor_tendrils"))
				.build()
		);
		var shriekerGargling = category.addEntry(entryBuilder.startBooleanToggle(text("shrieker_gargling"), config.shriekerGargling)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.shriekerGargling = newValue)
				.setTooltip(tooltip("shrieker_gargling"))
				.build()
		);
		var soulFireSounds = category.addEntry(entryBuilder.startBooleanToggle(text("soul_fire_sounds"), config.soulFireSounds)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.soulFireSounds = newValue)
				.setTooltip(tooltip("soul_fire_sounds"))
				.build()
		);

        /*var displayLanternCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("display_lantern"),
                false,
                tooltip("display_lantern")

        );*/

		var cactusSounds = entryBuilder.startBooleanToggle(text("cactus_sounds"), blockSounds.cactusSounds)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> blockSounds.cactusSounds = newValue)
				.setTooltip(tooltip("cactus_sounds"))
				.requireRestart()
				.build();

		var claySounds = entryBuilder.startBooleanToggle(text("clay_sounds"), blockSounds.claySounds)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> blockSounds.claySounds = newValue)
				.setTooltip(tooltip("clay_sounds"))
				.requireRestart()
				.build();

		var coarseDirtSounds = entryBuilder.startBooleanToggle(text("coarse_dirt_sounds"), blockSounds.coarseDirtSounds)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> blockSounds.coarseDirtSounds = newValue)
				.setTooltip(tooltip("coarse_dirt_sounds"))
				.requireRestart()
				.build();

		var cobwebSounds = entryBuilder.startBooleanToggle(text("cobweb_sounds"), blockSounds.cobwebSounds)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> blockSounds.cobwebSounds = newValue)
				.setTooltip(tooltip("cobweb_sounds"))
				.requireRestart()
				.build();

		var deadBushSounds = entryBuilder.startBooleanToggle(text("dead_bush_sounds"), blockSounds.deadBushSounds)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> blockSounds.deadBushSounds = newValue)
				.setTooltip(tooltip("dead_bush_sounds"))
				.requireRestart()
				.build();

		var flowerSounds = entryBuilder.startBooleanToggle(text("flower_sounds"), blockSounds.flowerSounds)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> blockSounds.flowerSounds = newValue)
				.setTooltip(tooltip("flower_sounds"))
				.requireRestart()
				.build();

		var gravelSounds = entryBuilder.startBooleanToggle(text("gravel_sounds"), blockSounds.gravelSounds)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> blockSounds.gravelSounds = newValue)
				.setTooltip(tooltip("gravel_sounds"))
				.requireRestart()
				.build();

		var frostedIceSounds = entryBuilder.startBooleanToggle(text("frosted_ice_sounds"), blockSounds.frostedIceSounds)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> blockSounds.frostedIceSounds = newValue)
				.setTooltip(tooltip("frosted_ice_sounds"))
				.requireRestart()
				.build();

		var leafSounds = entryBuilder.startBooleanToggle(text("leaf_sounds"), blockSounds.leafSounds)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> blockSounds.leafSounds = newValue)
				.setTooltip(tooltip("leaf_sounds"))
				.requireRestart()
				.build();

		var lilyPadSounds = entryBuilder.startBooleanToggle(text("lily_pad_sounds"), blockSounds.lilyPadSounds)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> blockSounds.lilyPadSounds = newValue)
				.setTooltip(tooltip("lily_pad_sounds"))
				.requireRestart()
				.build();

		var mushroomBlockSounds = entryBuilder.startBooleanToggle(text("mushroom_block_sounds"), blockSounds.mushroomBlockSounds)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> blockSounds.mushroomBlockSounds = newValue)
				.setTooltip(tooltip("mushroom_block_sounds"))
				.requireRestart()
				.build();

		var podzolSounds = entryBuilder.startBooleanToggle(text("podzol_sounds"), blockSounds.podzolSounds)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> blockSounds.podzolSounds = newValue)
				.setTooltip(tooltip("podzol_sounds"))
				.requireRestart()
				.build();

		var reinforcedDeepslateSounds = entryBuilder.startBooleanToggle(text("reinforced_deepslate_sounds"), blockSounds.reinforcedDeepslateSounds)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> blockSounds.reinforcedDeepslateSounds = newValue)
				.setTooltip(tooltip("reinforced_deepslate_sounds"))
				.requireRestart()
				.build();

		var sugarCaneSounds = entryBuilder.startBooleanToggle(text("sugar_cane_sounds"), blockSounds.sugarCaneSounds)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> blockSounds.sugarCaneSounds = newValue)
				.setTooltip(tooltip("sugar_cane_sounds"))
				.requireRestart()
				.build();

		var witherRoseSounds = entryBuilder.startBooleanToggle(text("wither_rose_sounds"), blockSounds.witherRoseSounds)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> blockSounds.witherRoseSounds = newValue)
				.setTooltip(tooltip("wither_rose_sounds"))
				.requireRestart()
				.build();

		var blockSoundsCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("block_sounds"),
				false,
				tooltip("block_sounds"),
				cactusSounds, claySounds, coarseDirtSounds, cobwebSounds, deadBushSounds,
				flowerSounds, gravelSounds, frostedIceSounds, leafSounds, lilyPadSounds,
				mushroomBlockSounds, podzolSounds, reinforcedDeepslateSounds, sugarCaneSounds, witherRoseSounds
		);

		var stoneChestTimer = entryBuilder.startIntSlider(text("stone_chest_timer"), stoneChest.stoneChestTimer, 50, 200)
				.setDefaultValue(100)
				.setSaveConsumer(newValue -> stoneChest.stoneChestTimer = newValue)
				.setTooltip(tooltip("stone_chest_timer"))
				.build();

		var stoneChestCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("stone_chest"),
				false,
				tooltip("stone_chest"),
				stoneChestTimer
		);

        /*var termiteCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("termite"),
                false,
                tooltip("termite")

        );*/
	}

	//public static final StringSetConfigOption HIDDEN_MODS = new StringSetConfigOption("hidden_mods", new HashSet<>());
    /*public static BlockConfig get() {
        AutoConfig.register(BlockConfig.class, GsonConfigSerializer::new);
        return AutoConfig.getConfigHolder(BlockConfig.class).getConfig();
    }*/
}
