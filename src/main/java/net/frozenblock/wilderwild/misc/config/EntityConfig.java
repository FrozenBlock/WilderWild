/*
 * Copyright 2022-2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.text;
import static net.frozenblock.wilderwild.misc.config.WilderWildConfig.tooltip;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultEntityConfig;

@Config(name = "entity")
public final class EntityConfig implements ConfigData {

	@ConfigEntry.Gui.CollapsibleObject
	public final AllayConfig allay = new AllayConfig();

	@ConfigEntry.Gui.CollapsibleObject
	public final EnderManConfig enderMan = new EnderManConfig();

	@ConfigEntry.Gui.CollapsibleObject
	public final FireflyConfig firefly = new FireflyConfig();

	@ConfigEntry.Gui.CollapsibleObject
	public final JellyfishConfig jellyfish = new JellyfishConfig();

	@ConfigEntry.Gui.CollapsibleObject
	public final TumbleweedConfig tumbleweed = new TumbleweedConfig();

	@ConfigEntry.Gui.CollapsibleObject
	public final WardenConfig warden = new WardenConfig();
	public boolean unpassableRail = DefaultEntityConfig.UNPASSABLE_RAIL;

	@Environment(EnvType.CLIENT)
	static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
		var config = WilderWildConfig.get().entity;
		var allay = config.allay;
		var enderMan = config.enderMan;
		var firefly = config.firefly;
		var jellyfish = config.jellyfish;
		var tumbleweed = config.tumbleweed;
		var warden = config.warden;
		category.setBackground(WilderSharedConstants.id("textures/config/entity.png"));
		var unpassableRail = category.addEntry(entryBuilder.startBooleanToggle(text("unpassable_rail"), config.unpassableRail)
			.setDefaultValue(DefaultEntityConfig.UNPASSABLE_RAIL)
			.setSaveConsumer(newValue -> config.unpassableRail = newValue)
			.setTooltip(tooltip("unpassable_rail"))
			.requireRestart()
			.build());

		var keyframeAllayDance = entryBuilder.startBooleanToggle(text("keyframe_allay_dance"), allay.keyframeAllayDance)
			.setDefaultValue(DefaultEntityConfig.AllayConfig.KEYFRAME_ALLAY_DANCE)
			.setSaveConsumer(newValue -> allay.keyframeAllayDance = newValue)
			.setTooltip(tooltip("keyframe_allay_dance"))
			.requireRestart()
			.build();

		var allayCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("allay"),
			false,
			tooltip("allay"),
			keyframeAllayDance
		);

		var angerLoopSound = entryBuilder.startBooleanToggle(text("anger_loop_sound"), enderMan.angerLoopSound)
			.setDefaultValue(DefaultEntityConfig.EnderManConfig.ANGER_LOOP_SOUND)
			.setSaveConsumer(newValue -> enderMan.angerLoopSound = newValue)
			.setTooltip(tooltip("anger_loop_sound"))
			.build();
		var movingStareSound = entryBuilder.startBooleanToggle(text("moving_stare_sound"), enderMan.movingStareSound)
			.setDefaultValue(DefaultEntityConfig.EnderManConfig.MOVING_STARE_SOUND)
			.setSaveConsumer(newValue -> enderMan.movingStareSound = newValue)
			.setTooltip(tooltip("moving_stare_sound"))
			.build();

		var enderManCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("enderman"),
			false,
			tooltip("enderman"),
			angerLoopSound, movingStareSound
		);

		var fireflySpawnCap = entryBuilder.startIntSlider(text("firefly_spawn_cap"), firefly.fireflySpawnCap, 0, 100)
			.setDefaultValue(DefaultEntityConfig.FireflyConfig.FIREFLY_SPAWN_CAP)
			.setSaveConsumer(newValue -> firefly.fireflySpawnCap = newValue)
			.setTooltip(tooltip("firefly_spawn_cap"))
			.requireRestart()
			.build();

		var fireflyCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("firefly"),
			false,
			tooltip("firefly"),
			fireflySpawnCap
		);

		var jellyfishSpawnCap = entryBuilder.startIntSlider(text("jellyfish_spawn_cap"), jellyfish.jellyfishSpawnCap, 0, 100)
			.setDefaultValue(DefaultEntityConfig.JellyfishConfig.JELLYFISH_SPAWN_CAP)
			.setSaveConsumer(newValue -> jellyfish.jellyfishSpawnCap = newValue)
			.setTooltip(tooltip("jellyfish_spawn_cap"))
			.requireRestart()
			.build();

		var jellyfishCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("jellyfish"),
			false,
			tooltip("jellyfish"),
			jellyfishSpawnCap
		);

		var tumbleweedSpawnCap = entryBuilder.startIntSlider(text("tumbleweed_spawn_cap"), tumbleweed.tumbleweedSpawnCap, 0, 100)
			.setDefaultValue(DefaultEntityConfig.TumbleweedConfig.TUMBLEWEED_SPAWN_CAP)
			.setSaveConsumer(newValue -> tumbleweed.tumbleweedSpawnCap = newValue)
			.setTooltip(tooltip("tumbleweed_spawn_cap"))
			.requireRestart()
			.build();

		var leashedTumbleweed = entryBuilder.startBooleanToggle(text("leashed_tumbleweed"), tumbleweed.leashedTumbleweed)
			.setDefaultValue(DefaultEntityConfig.TumbleweedConfig.LEASHED_TUMBLEWEED)
			.setSaveConsumer(newValue -> tumbleweed.leashedTumbleweed = newValue)
			.setTooltip(tooltip("leashed_tumbleweed"))
			.build();

		var tumbleweedCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("tumbleweed"),
			false,
			tooltip("tumbleweed"),
			tumbleweedSpawnCap,
			leashedTumbleweed
		);


		var instantAttack = entryBuilder.startBooleanToggle(text("warden_attacks_immediately"), warden.wardenAttacksImmediately)
			.setDefaultValue(DefaultEntityConfig.WardenConfig.WARDEN_ATTACKS_IMMEDIATELY)
			.setSaveConsumer(newValue -> warden.wardenAttacksImmediately = newValue)
			.setTooltip(tooltip("warden_attacks_immediately"))
			.build();
		var dying = entryBuilder.startBooleanToggle(text("warden_dying_animation"), warden.wardenDyingAnimation)
			.setDefaultValue(DefaultEntityConfig.WardenConfig.WARDEN_DYING_ANIMATION)
			.setSaveConsumer(newValue -> warden.wardenDyingAnimation = newValue)
			.setTooltip(tooltip("warden_dying_animation"))
			.build();
		var command = entryBuilder.startBooleanToggle(text("warden_emerges_from_command"), warden.wardenEmergesFromCommand)
			.setDefaultValue(DefaultEntityConfig.WardenConfig.WARDEN_EMERGES_FROM_COMMAND)
			.setSaveConsumer(newValue -> warden.wardenEmergesFromCommand = newValue)
			.setTooltip(tooltip("warden_emerges_from_command"))
			.build();
		var egg = entryBuilder.startBooleanToggle(text("warden_emerges_from_egg"), warden.wardenEmergesFromEgg)
			.setDefaultValue(DefaultEntityConfig.WardenConfig.WARDEN_EMERGES_FROM_EGG)
			.setSaveConsumer(newValue -> warden.wardenEmergesFromEgg = newValue)
			.setTooltip(tooltip("warden_emerges_from_egg"))
			.build();
		var swimming = entryBuilder.startBooleanToggle(text("warden_swim_animation"), warden.wardenSwimAnimation)
			.setDefaultValue(DefaultEntityConfig.WardenConfig.WARDEN_SWIM_ANIMATION)
			.setSaveConsumer(newValue -> warden.wardenSwimAnimation = newValue)
			.setTooltip(tooltip("warden_swim_animation"))
			.build();
		var tendrils = entryBuilder.startBooleanToggle(text("warden_custom_tendrils"), warden.wardenCustomTendrils)
			.setDefaultValue(DefaultEntityConfig.WardenConfig.WARDEN_CUSTOM_TENDRILS)
			.setSaveConsumer(newValue -> warden.wardenCustomTendrils = newValue)
			.setYesNoTextSupplier(bool -> text("warden_custom_tendrils." + bool))
			.setTooltip(tooltip("warden_custom_tendrils"))
			.build();
		var sniff = entryBuilder.startBooleanToggle(text("warden_bedrock_sniff"), warden.wardenBedrockSniff)
			.setDefaultValue(DefaultEntityConfig.WardenConfig.WARDEN_BEDROCK_SNIFF)
			.setSaveConsumer(newValue -> warden.wardenBedrockSniff = newValue)
			.setYesNoTextSupplier(bool -> text("warden_bedrock_sniff." + bool))
			.setTooltip(tooltip("warden_bedrock_sniff"))
			.requireRestart()
			.build();

		var wardenCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("warden"),
			false,
			tooltip("warden"),
			instantAttack, dying, command, egg, swimming, tendrils, sniff
		);
	}

	public static class AllayConfig {
		public boolean keyframeAllayDance = DefaultEntityConfig.AllayConfig.KEYFRAME_ALLAY_DANCE;
	}

	public static class EnderManConfig {
		public boolean angerLoopSound = DefaultEntityConfig.EnderManConfig.ANGER_LOOP_SOUND;
		public boolean movingStareSound = DefaultEntityConfig.EnderManConfig.MOVING_STARE_SOUND;
	}

	public static class FireflyConfig {
		public int fireflySpawnCap = DefaultEntityConfig.FireflyConfig.FIREFLY_SPAWN_CAP;
	}

	public static class JellyfishConfig {
		public int jellyfishSpawnCap = DefaultEntityConfig.JellyfishConfig.JELLYFISH_SPAWN_CAP;
	}

	public static class TumbleweedConfig {
		public int tumbleweedSpawnCap = DefaultEntityConfig.TumbleweedConfig.TUMBLEWEED_SPAWN_CAP;
		public boolean leashedTumbleweed = DefaultEntityConfig.TumbleweedConfig.LEASHED_TUMBLEWEED;
	}

	public static class WardenConfig {
		public boolean wardenAttacksImmediately = DefaultEntityConfig.WardenConfig.WARDEN_ATTACKS_IMMEDIATELY;
		public boolean wardenCustomTendrils = DefaultEntityConfig.WardenConfig.WARDEN_CUSTOM_TENDRILS;
		public boolean wardenBedrockSniff = DefaultEntityConfig.WardenConfig.WARDEN_BEDROCK_SNIFF;
		public boolean wardenDyingAnimation = DefaultEntityConfig.WardenConfig.WARDEN_DYING_ANIMATION;
		public boolean wardenEmergesFromCommand = DefaultEntityConfig.WardenConfig.WARDEN_EMERGES_FROM_COMMAND;
		public boolean wardenEmergesFromEgg = DefaultEntityConfig.WardenConfig.WARDEN_EMERGES_FROM_EGG;
		public boolean wardenSwimAnimation = DefaultEntityConfig.WardenConfig.WARDEN_SWIM_ANIMATION;
	}
}
