package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.FrozenConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultBlockConfig;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.*;

@Config(name = "block")
public final class BlockConfig implements ConfigData {

	@ConfigEntry.Gui.CollapsibleObject
	public final BlockSoundsConfig blockSounds = new BlockSoundsConfig();
	@ConfigEntry.Gui.CollapsibleObject
	public final StoneChestConfig stoneChest = new StoneChestConfig();

	public static class BlockSoundsConfig {
		public boolean cactusSounds = DefaultBlockConfig.BlockSoundsConfig.cactusSounds;
		public boolean claySounds = DefaultBlockConfig.BlockSoundsConfig.claySounds;
		public boolean coarseDirtSounds = DefaultBlockConfig.BlockSoundsConfig.coarseDirtSounds;
		public boolean cobwebSounds = DefaultBlockConfig.BlockSoundsConfig.cobwebSounds;
		public boolean deadBushSounds = DefaultBlockConfig.BlockSoundsConfig.deadBushSounds;
		public boolean flowerSounds = DefaultBlockConfig.BlockSoundsConfig.flowerSounds;
		public boolean frostedIceSounds = DefaultBlockConfig.BlockSoundsConfig.frostedIceSounds;
		public boolean gravelSounds = DefaultBlockConfig.BlockSoundsConfig.gravelSounds;
		public boolean leafSounds = DefaultBlockConfig.BlockSoundsConfig.leafSounds;
		public boolean lilyPadSounds = DefaultBlockConfig.BlockSoundsConfig.lilyPadSounds;
		public boolean mushroomBlockSounds = DefaultBlockConfig.BlockSoundsConfig.mushroomBlockSounds;
		public boolean podzolSounds = DefaultBlockConfig.BlockSoundsConfig.podzolSounds;
		public boolean reinforcedDeepslateSounds = DefaultBlockConfig.BlockSoundsConfig.reinforcedDeepslateSounds;
		public boolean sugarCaneSounds = DefaultBlockConfig.BlockSoundsConfig.sugarCaneSounds;
		public boolean witherRoseSounds = DefaultBlockConfig.BlockSoundsConfig.witherRoseSounds;
	}

	public static class StoneChestConfig {
		public int stoneChestTimer = DefaultBlockConfig.StoneChestConfig.stoneChestTimer;
	}

    public boolean mcLiveSensorTendrils = DefaultBlockConfig.mcLiveSensorTendrils;
    public boolean shriekerGargling = DefaultBlockConfig.shriekerGargling;
    public boolean soulFireSounds = DefaultBlockConfig.soulFireSounds;

    @Environment(EnvType.CLIENT)
    static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
        var config = WilderWildConfig.get().block;
		var blockSounds = config.blockSounds;
		var stoneChest = config.stoneChest;
        category.setBackground(WilderSharedConstants.id("textures/config/block.png"));
        var mcLiveSensorTendrils = category.addEntry(entryBuilder.startBooleanToggle(text("mc_live_sensor_tendrils"), config.mcLiveSensorTendrils)
                .setDefaultValue(DefaultBlockConfig.mcLiveSensorTendrils)
                .setSaveConsumer(newValue -> config.mcLiveSensorTendrils = newValue)
                .setYesNoTextSupplier(bool -> text("mc_live_sensor_tendrils." + bool))
                .setTooltip(tooltip("mc_live_sensor_tendrils"))
                .build()
        );
        var shriekerGargling = category.addEntry(entryBuilder.startBooleanToggle(text("shrieker_gargling"), config.shriekerGargling)
                .setDefaultValue(DefaultBlockConfig.shriekerGargling)
                .setSaveConsumer(newValue -> config.shriekerGargling = newValue)
                .setTooltip(tooltip("shrieker_gargling"))
                .build()
        );
        var soulFireSounds = category.addEntry(entryBuilder.startBooleanToggle(text("soul_fire_sounds"), config.soulFireSounds)
                .setDefaultValue(DefaultBlockConfig.soulFireSounds)
                .setSaveConsumer(newValue -> config.soulFireSounds = newValue)
                .setTooltip(tooltip("soul_fire_sounds"))
                .build()
        );

        /*var displayLanternCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("display_lantern"),
                false,
                tooltip("display_lantern")

        );*/

		var cactusSounds = entryBuilder.startBooleanToggle(text("cactus_sounds"), blockSounds.cactusSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.cactusSounds)
				.setSaveConsumer(newValue -> blockSounds.cactusSounds = newValue)
				.setTooltip(tooltip("cactus_sounds"))
				.requireRestart()
				.build();

		var claySounds = entryBuilder.startBooleanToggle(text("clay_sounds"), blockSounds.claySounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.claySounds)
				.setSaveConsumer(newValue -> blockSounds.claySounds = newValue)
				.setTooltip(tooltip("clay_sounds"))
				.requireRestart()
				.build();

		var coarseDirtSounds = entryBuilder.startBooleanToggle(text("coarse_dirt_sounds"), blockSounds.coarseDirtSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.coarseDirtSounds)
				.setSaveConsumer(newValue -> blockSounds.coarseDirtSounds = newValue)
				.setTooltip(tooltip("coarse_dirt_sounds"))
				.requireRestart()
				.build();

		var cobwebSounds = entryBuilder.startBooleanToggle(text("cobweb_sounds"), blockSounds.cobwebSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.cobwebSounds)
				.setSaveConsumer(newValue -> blockSounds.cobwebSounds = newValue)
				.setTooltip(tooltip("cobweb_sounds"))
				.requireRestart()
				.build();

		var deadBushSounds = entryBuilder.startBooleanToggle(text("dead_bush_sounds"), blockSounds.deadBushSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.deadBushSounds)
				.setSaveConsumer(newValue -> blockSounds.deadBushSounds = newValue)
				.setTooltip(tooltip("dead_bush_sounds"))
				.requireRestart()
				.build();

		var flowerSounds = entryBuilder.startBooleanToggle(text("flower_sounds"), blockSounds.flowerSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.flowerSounds)
				.setSaveConsumer(newValue -> blockSounds.flowerSounds = newValue)
				.setTooltip(tooltip("flower_sounds"))
				.requireRestart()
				.build();

		var gravelSounds = entryBuilder.startBooleanToggle(text("gravel_sounds"), blockSounds.gravelSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.gravelSounds)
				.setSaveConsumer(newValue -> blockSounds.gravelSounds = newValue)
				.setTooltip(tooltip("gravel_sounds"))
				.requireRestart()
				.build();

		var frostedIceSounds = entryBuilder.startBooleanToggle(text("frosted_ice_sounds"), blockSounds.frostedIceSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.frostedIceSounds)
				.setSaveConsumer(newValue -> blockSounds.frostedIceSounds = newValue)
				.setTooltip(tooltip("frosted_ice_sounds"))
				.requireRestart()
				.build();

		var leafSounds = entryBuilder.startBooleanToggle(text("leaf_sounds"), blockSounds.leafSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.leafSounds)
				.setSaveConsumer(newValue -> blockSounds.leafSounds = newValue)
				.setTooltip(tooltip("leaf_sounds"))
				.requireRestart()
				.build();

		var lilyPadSounds = entryBuilder.startBooleanToggle(text("lily_pad_sounds"), blockSounds.lilyPadSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.lilyPadSounds)
				.setSaveConsumer(newValue -> blockSounds.lilyPadSounds = newValue)
				.setTooltip(tooltip("lily_pad_sounds"))
				.requireRestart()
				.build();

		var mushroomBlockSounds = entryBuilder.startBooleanToggle(text("mushroom_block_sounds"), blockSounds.mushroomBlockSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.mushroomBlockSounds)
				.setSaveConsumer(newValue -> blockSounds.mushroomBlockSounds = newValue)
				.setTooltip(tooltip("mushroom_block_sounds"))
				.requireRestart()
				.build();

		var podzolSounds = entryBuilder.startBooleanToggle(text("podzol_sounds"), blockSounds.podzolSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.podzolSounds)
				.setSaveConsumer(newValue -> blockSounds.podzolSounds = newValue)
				.setTooltip(tooltip("podzol_sounds"))
				.requireRestart()
				.build();

		var reinforcedDeepslateSounds = entryBuilder.startBooleanToggle(text("reinforced_deepslate_sounds"), blockSounds.reinforcedDeepslateSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.reinforcedDeepslateSounds)
				.setSaveConsumer(newValue -> blockSounds.reinforcedDeepslateSounds = newValue)
				.setTooltip(tooltip("reinforced_deepslate_sounds"))
				.requireRestart()
				.build();

		var sugarCaneSounds = entryBuilder.startBooleanToggle(text("sugar_cane_sounds"), blockSounds.sugarCaneSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.sugarCaneSounds)
				.setSaveConsumer(newValue -> blockSounds.sugarCaneSounds = newValue)
				.setTooltip(tooltip("sugar_cane_sounds"))
				.requireRestart()
				.build();

		var witherRoseSounds = entryBuilder.startBooleanToggle(text("wither_rose_sounds"), blockSounds.witherRoseSounds)
				.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.witherRoseSounds)
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
				.setDefaultValue(DefaultBlockConfig.StoneChestConfig.stoneChestTimer)
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
}
