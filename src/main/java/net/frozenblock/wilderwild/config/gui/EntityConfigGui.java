/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.wilderwild.config.EntityConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.text;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.tooltip;

@Environment(EnvType.CLIENT)
public final class EntityConfigGui {
	private EntityConfigGui() {
		throw new UnsupportedOperationException("EntityConfigGui contains only static declarations.");
	}

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
		var config = EntityConfig.get(true);
		var defaultConfig = EntityConfig.INSTANCE.defaultInstance();
		var allay = config.allay;
		var enderMan = config.enderMan;
		var firefly = config.firefly;
		var jellyfish = config.jellyfish;
		var tumbleweed = config.tumbleweed;
		var warden = config.warden;
		category.setBackground(WilderSharedConstants.id("textures/config/entity.png"));
		var unpassableRail = category.addEntry(entryBuilder.startBooleanToggle(text("unpassable_rail"), config.unpassableRail)
			.setDefaultValue(defaultConfig.unpassableRail)
			.setSaveConsumer(newValue -> config.unpassableRail = newValue)
			.setTooltip(tooltip("unpassable_rail"))
			.requireRestart()
			.build());

		var keyframeAllayDance = entryBuilder.startBooleanToggle(text("keyframe_allay_dance"), allay.keyframeAllayDance)
			.setDefaultValue(defaultConfig.allay.keyframeAllayDance)
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
			.setDefaultValue(defaultConfig.enderMan.angerLoopSound)
			.setSaveConsumer(newValue -> enderMan.angerLoopSound = newValue)
			.setTooltip(tooltip("anger_loop_sound"))
			.build();
		var movingStareSound = entryBuilder.startBooleanToggle(text("moving_stare_sound"), enderMan.movingStareSound)
			.setDefaultValue(defaultConfig.enderMan.movingStareSound)
			.setSaveConsumer(newValue -> enderMan.movingStareSound = newValue)
			.setTooltip(tooltip("moving_stare_sound"))
			.build();

		var enderManCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("enderman"),
			false,
			tooltip("enderman"),
			angerLoopSound, movingStareSound
		);

		var fireflySpawnCap = entryBuilder.startIntSlider(text("firefly_spawn_cap"), firefly.fireflySpawnCap, 0, 100)
			.setDefaultValue(defaultConfig.firefly.fireflySpawnCap)
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
			.setDefaultValue(defaultConfig.jellyfish.jellyfishSpawnCap)
			.setSaveConsumer(newValue -> jellyfish.jellyfishSpawnCap = newValue)
			.setTooltip(tooltip("jellyfish_spawn_cap"))
			.requireRestart()
			.build();

		var jellyfishTentacles = entryBuilder.startIntSlider(text("jellyfish_tentacles"), jellyfish.jellyfishTentacles, 0, 100)
			.setDefaultValue(defaultConfig.jellyfish.jellyfishTentacles)
			.setSaveConsumer(newValue -> jellyfish.jellyfishTentacles = newValue)
			.setTooltip(tooltip("jellyfish_tentacles"))
			.requireRestart()
			.build();

		var jellyfishCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("jellyfish"),
			false,
			tooltip("jellyfish"),
			jellyfishSpawnCap,
			jellyfishTentacles
		);

		var tumbleweedSpawnCap = entryBuilder.startIntSlider(text("tumbleweed_spawn_cap"), tumbleweed.tumbleweedSpawnCap, 0, 100)
			.setDefaultValue(defaultConfig.tumbleweed.tumbleweedSpawnCap)
			.setSaveConsumer(newValue -> tumbleweed.tumbleweedSpawnCap = newValue)
			.setTooltip(tooltip("tumbleweed_spawn_cap"))
			.requireRestart()
			.build();

		var leashedTumbleweed = entryBuilder.startBooleanToggle(text("leashed_tumbleweed"), tumbleweed.leashedTumbleweed)
			.setDefaultValue(defaultConfig.tumbleweed.leashedTumbleweed)
			.setSaveConsumer(newValue -> tumbleweed.leashedTumbleweed = newValue)
			.setTooltip(tooltip("leashed_tumbleweed"))
			.build();

		var tumbleweedDestroysCrops = entryBuilder.startBooleanToggle(text("tumbleweed_destroys_crops"), tumbleweed.tumbleweedDestroysCrops)
			.setDefaultValue(defaultConfig.tumbleweed.tumbleweedDestroysCrops)
			.setSaveConsumer(newValue -> tumbleweed.tumbleweedDestroysCrops = newValue)
			.setTooltip(tooltip("tumbleweed_destroys_crops"))
			.build();

		var tumbleweedRotatesToLookDirection = entryBuilder.startBooleanToggle(text("tumbleweed_rotates_to_look_direction"), tumbleweed.tumbleweedRotatesToLookDirection)
			.setDefaultValue(defaultConfig.tumbleweed.tumbleweedRotatesToLookDirection)
			.setSaveConsumer(newValue -> tumbleweed.tumbleweedRotatesToLookDirection = newValue)
			.setTooltip(tooltip("tumbleweed_rotates_to_look_direction"))
			.build();

		var tumbleweedCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("tumbleweed"),
			false,
			tooltip("tumbleweed"),
			tumbleweedSpawnCap,
			leashedTumbleweed,
			tumbleweedDestroysCrops,
			tumbleweedRotatesToLookDirection
		);


		var instantAttack = entryBuilder.startBooleanToggle(text("warden_attacks_immediately"), warden.wardenAttacksImmediately)
			.setDefaultValue(defaultConfig.warden.wardenAttacksImmediately)
			.setSaveConsumer(newValue -> warden.wardenAttacksImmediately = newValue)
			.setTooltip(tooltip("warden_attacks_immediately"))
			.build();
		var dying = entryBuilder.startBooleanToggle(text("warden_dying_animation"), warden.wardenDyingAnimation)
			.setDefaultValue(defaultConfig.warden.wardenDyingAnimation)
			.setSaveConsumer(newValue -> warden.wardenDyingAnimation = newValue)
			.setTooltip(tooltip("warden_dying_animation"))
			.build();
		var command = entryBuilder.startBooleanToggle(text("warden_emerges_from_command"), warden.wardenEmergesFromCommand)
			.setDefaultValue(defaultConfig.warden.wardenEmergesFromCommand)
			.setSaveConsumer(newValue -> warden.wardenEmergesFromCommand = newValue)
			.setTooltip(tooltip("warden_emerges_from_command"))
			.build();
		var egg = entryBuilder.startBooleanToggle(text("warden_emerges_from_egg"), warden.wardenEmergesFromEgg)
			.setDefaultValue(defaultConfig.warden.wardenEmergesFromEgg)
			.setSaveConsumer(newValue -> warden.wardenEmergesFromEgg = newValue)
			.setTooltip(tooltip("warden_emerges_from_egg"))
			.build();
		var swimming = entryBuilder.startBooleanToggle(text("warden_swim_animation"), warden.wardenSwimAnimation)
			.setDefaultValue(defaultConfig.warden.wardenSwimAnimation)
			.setSaveConsumer(newValue -> warden.wardenSwimAnimation = newValue)
			.setTooltip(tooltip("warden_swim_animation"))
			.build();
		var tendrils = entryBuilder.startBooleanToggle(text("warden_custom_tendrils"), warden.wardenCustomTendrils)
			.setDefaultValue(defaultConfig.warden.wardenCustomTendrils)
			.setSaveConsumer(newValue -> warden.wardenCustomTendrils = newValue)
			.setYesNoTextSupplier(bool -> text("warden_custom_tendrils." + bool))
			.setTooltip(tooltip("warden_custom_tendrils"))
			.build();
		var sniff = entryBuilder.startBooleanToggle(text("warden_bedrock_sniff"), warden.wardenBedrockSniff)
			.setDefaultValue(defaultConfig.warden.wardenBedrockSniff)
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
}
