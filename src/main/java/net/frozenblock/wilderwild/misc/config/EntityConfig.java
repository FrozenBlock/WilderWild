package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.util.Utils;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.FrozenConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.*;

@Config(name = "entity")
public final class EntityConfig implements ConfigData {

	@ConfigEntry.Gui.CollapsibleObject
	public final AllayConfig allay = new AllayConfig();

	@ConfigEntry.Gui.CollapsibleObject
	public final EnderManConfig enderMan = new EnderManConfig();

	@ConfigEntry.Gui.CollapsibleObject
	public final WardenConfig warden = new WardenConfig();

	@ConfigEntry.Gui.CollapsibleObject
	public final FireflyConfig firefly = new FireflyConfig();

	@ConfigEntry.Gui.CollapsibleObject
	public final JellyfishConfig jellyfish = new JellyfishConfig();

	public static class AllayConfig {
		public boolean keyframeAllayDance = true;
	}

	public static class EnderManConfig {
		public boolean angerLoopSound = true;
		public boolean movingStareSound = true;
	}

	public static class WardenConfig {
		public boolean wardenAttacksInstantly = true;
		public boolean wardenCustomTendrils = true;
		public boolean wardenBedrockSniff = true;
		public boolean wardenDyingAnimation = true;
		public boolean wardenEmergesFromCommand = false;
		public boolean wardenEmergesFromEgg = false;
		public boolean wardenSwimAnimation = true;
	}

	public static class FireflyConfig {
		public int fireflySpawnCap = 56;
	}

	public static class JellyfishConfig {
		public int jellyfishSpawnCap = 30;
	}

	public boolean unpassableRail = false;

	@Environment(EnvType.CLIENT)
	static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
		var config = WilderWildConfig.get().entity;
		var allay = config.allay;
		var enderMan = config.enderMan;
		var firefly = config.firefly;
		var jellyfish = config.jellyfish;
		var warden = config.warden;
		category.setBackground(WilderSharedConstants.id("textures/config/entity.png"));
		var unpassableRail = category.addEntry(entryBuilder.startBooleanToggle(text("unpassable_rail"), config.unpassableRail)
				.setDefaultValue(false)
				.setSaveConsumer(newValue -> config.unpassableRail = newValue)
				.setTooltip(tooltip("unpassable_rail"))
				.requireRestart()
				.build());

		var keyframeAllayDance = entryBuilder.startBooleanToggle(text("keyframe_allay_dance"), allay.keyframeAllayDance)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> allay.keyframeAllayDance = newValue)
				.setTooltip(tooltip("keyframe_allay_dance"))
				.requireRestart()
				.build();

		var allayCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("allay"),
				false,
				tooltip("allay"),
				keyframeAllayDance
		);

		var angerLoopSound = entryBuilder.startBooleanToggle(text("anger_loop_sound"), enderMan.angerLoopSound)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> enderMan.angerLoopSound = newValue)
				.setTooltip(tooltip("anger_loop_sound"))
				.build();
		var movingStareSound = entryBuilder.startBooleanToggle(text("moving_stare_sound"), enderMan.movingStareSound)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> enderMan.movingStareSound = newValue)
				.setTooltip(tooltip("moving_stare_sound"))
				.build();

		var enderManCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("enderman"),
				false,
				tooltip("enderman"),
				angerLoopSound, movingStareSound
		);

		var fireflySpawnCap = entryBuilder.startIntSlider(text("firefly_spawn_cap"), firefly.fireflySpawnCap, 0, 100)
				.setDefaultValue(56)
				.setSaveConsumer(newValue -> firefly.fireflySpawnCap = newValue)
				.setTooltip(tooltip("firefly_spawn_cap"))
				.requireRestart()
				.build();

        var fireflyCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("firefly"),
                false,
                tooltip("firefly"),
				fireflySpawnCap
        );

		var jellyfishSpawnCap = entryBuilder.startIntSlider(text("jellyfish_spawn_cap"), jellyfish.jellyfishSpawnCap, 0, 100)
				.setDefaultValue(30)
				.setSaveConsumer(newValue -> jellyfish.jellyfishSpawnCap = newValue)
				.setTooltip(tooltip("jellyfish_spawn_cap"))
				.requireRestart()
				.build();

        var jellyfishCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("jellyfish"),
                false,
                tooltip("jellyfish"),
				jellyfishSpawnCap
        );

		var instantAttack = entryBuilder.startBooleanToggle(text("warden_attacks_instantly"), warden.wardenAttacksInstantly)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> warden.wardenAttacksInstantly = newValue)
				.setTooltip(tooltip("warden_attacks_instantly"))
				.build();
		var dying = entryBuilder.startBooleanToggle(text("warden_dying_animation"), warden.wardenDyingAnimation)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> warden.wardenDyingAnimation = newValue)
				.setTooltip(tooltip("warden_dying_animation"))
				.build();
		var command = entryBuilder.startBooleanToggle(text("warden_emerges_from_command"), warden.wardenEmergesFromCommand)
				.setDefaultValue(false)
				.setSaveConsumer(newValue -> warden.wardenEmergesFromCommand = newValue)
				.setTooltip(tooltip("warden_emerges_from_command"))
				.build();
		var emerging = entryBuilder.startBooleanToggle(text("warden_emerges_from_egg"), warden.wardenEmergesFromEgg)
				.setDefaultValue(false)
				.setSaveConsumer(newValue -> warden.wardenEmergesFromEgg = newValue)
				.setTooltip(tooltip("warden_emerges_from_egg"))
				.build();
		var swimming = entryBuilder.startBooleanToggle(text("warden_swim_animation"), warden.wardenSwimAnimation)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> warden.wardenSwimAnimation = newValue)
				.setTooltip(tooltip("warden_swim_animation"))
				.build();
		var tendrils = entryBuilder.startBooleanToggle(text("warden_custom_tendrils"), warden.wardenCustomTendrils)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> warden.wardenCustomTendrils = newValue)
				.setYesNoTextSupplier(bool -> text("warden_custom_tendrils." + bool))
				.setTooltip(tooltip("warden_custom_tendrils"))
				.build();
		var sniff = entryBuilder.startBooleanToggle(text("warden_bedrock_sniff"), warden.wardenBedrockSniff)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> warden.wardenBedrockSniff = newValue)
				.setYesNoTextSupplier(bool -> text("warden_bedrock_sniff." + bool))
				.setTooltip(tooltip("warden_bedrock_sniff"))
				.requireRestart()
				.build();

		var wardenCategory = FrozenConfig.createSubCategory(entryBuilder, category, text("warden"),
				false,
				tooltip("warden"),
				instantAttack, dying, command, emerging, swimming, tendrils
		);
	}


	//public static final EnumConfigOption<ModMenuConfig.ModsButtonStyle> MODS_BUTTON_STYLE = new EnumConfigOption<>("mods_button_style", ModMenuConfig.ModsButtonStyle.CLASSIC);
	//public static final StringSetConfigOption HIDDEN_MODS = new StringSetConfigOption("hidden_mods", new HashSet<>());
    /*public static EntityConfig get() {
        AutoConfig.register(EntityConfig.class, GsonConfigSerializer::new);
        return AutoConfig.getConfigHolder(EntityConfig.class).getConfig();
    }*/
}
