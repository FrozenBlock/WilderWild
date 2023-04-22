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

package net.frozenblock.wilderwild;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.entrypoint.api.FrozenModInitializer;
import net.frozenblock.lib.mobcategory.api.entrypoint.FrozenMobCategoryEntrypoint;
import net.frozenblock.lib.mobcategory.impl.FrozenMobCategory;
import net.frozenblock.wilderwild.block.entity.PalmCrownBlockEntity;
import net.frozenblock.wilderwild.entity.TermiteManager;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.command.SpreadSculkCommand;
import net.frozenblock.wilderwild.misc.datafixer.DrySandStateFix;
import net.frozenblock.wilderwild.misc.datafixer.NematocystStateFix;
import net.frozenblock.wilderwild.misc.mod_compat.WilderModIntegrations;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterBlockSoundTypes;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterCriteria;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterGameEvents;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterLootTables;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterResources;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.registry.RegisterStructures;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.registry.WilderRegistry;
import net.frozenblock.wilderwild.world.additions.feature.WilderConfiguredFeatures;
import net.frozenblock.wilderwild.world.additions.feature.WilderMiscConfigured;
import net.frozenblock.wilderwild.world.additions.feature.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.additions.feature.WilderPlacedFeatures;
import net.frozenblock.wilderwild.world.additions.feature.WilderTreeConfigured;
import net.frozenblock.wilderwild.world.additions.feature.WilderTreePlaced;
import net.frozenblock.wilderwild.world.additions.gen.WilderWorldGen;
import net.frozenblock.wilderwild.world.generation.conditionsource.BetaBeachConditionSource;
import net.frozenblock.wilderwild.world.generation.features.AlgaeFeature;
import net.frozenblock.wilderwild.world.generation.features.CattailFeature;
import net.frozenblock.wilderwild.world.generation.features.LargeMesogleaFeature;
import net.frozenblock.wilderwild.world.generation.features.NematocystFeature;
import net.frozenblock.wilderwild.world.generation.features.ShelfFungusFeature;
import net.frozenblock.wilderwild.world.generation.features.SmallSpongeFeature;
import net.frozenblock.wilderwild.world.generation.features.SnowAndFreezeDiskFeature;
import net.frozenblock.wilderwild.world.generation.features.SnowBlanketFeature;
import net.frozenblock.wilderwild.world.generation.features.config.LargeMesogleaConfig;
import net.frozenblock.wilderwild.world.generation.features.config.ShelfFungusFeatureConfig;
import net.frozenblock.wilderwild.world.generation.features.config.SmallSpongeFeatureConfig;
import net.frozenblock.wilderwild.world.generation.features.config.SnowAndIceDiskFeatureConfig;
import net.frozenblock.wilderwild.world.generation.foliage.PalmFoliagePlacer;
import net.frozenblock.wilderwild.world.generation.foliage.ShortPalmFoliagePlacer;
import net.frozenblock.wilderwild.world.generation.trunk.BaobabTrunkPlacer;
import net.frozenblock.wilderwild.world.generation.trunk.FallenTrunkWithLogs;
import net.frozenblock.wilderwild.world.generation.trunk.FancyDarkOakTrunkPlacer;
import net.frozenblock.wilderwild.world.generation.trunk.JuniperTrunkPlacer;
import net.frozenblock.wilderwild.world.generation.trunk.PalmTrunkPlacer;
import net.frozenblock.wilderwild.world.generation.trunk.SnappedTrunkPlacer;
import net.frozenblock.wilderwild.world.generation.trunk.StraightTrunkWithLogs;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.biome.Biomes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixerBuilder;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixes;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.SimpleFixes;

public final class WilderWild extends FrozenModInitializer implements FrozenMobCategoryEntrypoint {

	public static WilderWild INSTANCE;

	public WilderWild() {
		super(WilderSharedConstants.MOD_ID);
		INSTANCE = this;
	}

	public static final TrunkPlacerType<StraightTrunkWithLogs> STRAIGHT_TRUNK_WITH_LOGS_PLACER_TYPE = registerTrunk("straight_trunk_logs_placer", StraightTrunkWithLogs.CODEC);
    public static final TrunkPlacerType<FallenTrunkWithLogs> FALLEN_TRUNK_WITH_LOGS_PLACER_TYPE = registerTrunk("fallen_trunk_logs_placer", FallenTrunkWithLogs.CODEC);
    public static final TrunkPlacerType<BaobabTrunkPlacer> BAOBAB_TRUNK_PLACER = registerTrunk("baobab_trunk_placer", BaobabTrunkPlacer.CODEC);
	public static final TrunkPlacerType<PalmTrunkPlacer> PALM_TRUNK_PLACER = registerTrunk("palm_trunk_placer", PalmTrunkPlacer.CODEC);
	public static final TrunkPlacerType<JuniperTrunkPlacer> JUNIPER_TRUNK_PLACER = registerTrunk("juniper_trunk_placer", JuniperTrunkPlacer.CODEC);
	public static final TrunkPlacerType<FancyDarkOakTrunkPlacer> FANCY_DARK_OAK_TRUNK_PLACER = registerTrunk("fancy_dark_oak_trunk_placer", FancyDarkOakTrunkPlacer.CODEC);
	public static final TrunkPlacerType<SnappedTrunkPlacer> SNAPPED_TRUNK_PLACER= registerTrunk("snapped_trunk_placer", SnappedTrunkPlacer.CODEC);

	public static final Feature<ShelfFungusFeatureConfig> SHELF_FUNGUS_FEATURE = register("shelf_fungus_feature", new ShelfFungusFeature(ShelfFungusFeatureConfig.CODEC));
	public static final Feature<SmallSpongeFeatureConfig> SMALL_SPONGE_FEATURE = register("small_sponge_feature", new SmallSpongeFeature(SmallSpongeFeatureConfig.CODEC));
    public static final CattailFeature CATTAIL_FEATURE = register("cattail_feature", new CattailFeature(ProbabilityFeatureConfiguration.CODEC));
    public static final AlgaeFeature ALGAE_FEATURE = register("algae_feature", new AlgaeFeature(ProbabilityFeatureConfiguration.CODEC));
    public static final NematocystFeature NEMATOCYST_FEATURE = register("nematocyst_feature", new NematocystFeature(MultifaceGrowthConfiguration.CODEC));
	public static final LargeMesogleaFeature LARGE_MESOGLEA_FEATURE = register("large_mesoglea_feature", new LargeMesogleaFeature(LargeMesogleaConfig.CODEC));
	public static final SnowBlanketFeature SNOW_BLANKET_FEATURE = register("snow_blanket_feature", new SnowBlanketFeature(NoneFeatureConfiguration.CODEC));
	public static final SnowAndFreezeDiskFeature SNOW_AND_FREEZE_DISK_FEATURE = register("snow_and_freeze_disk_feature", new SnowAndFreezeDiskFeature(SnowAndIceDiskFeatureConfig.CODEC));
    public static final FoliagePlacerType<PalmFoliagePlacer> PALM_FOLIAGE_PLACER = registerFoliage("palm_foliage_placer", PalmFoliagePlacer.CODEC);
	public static final FoliagePlacerType<ShortPalmFoliagePlacer> SHORT_PALM_FOLIAGE_PLACER = registerFoliage("short_palm_foliage_placer", ShortPalmFoliagePlacer.CODEC);

    @Override //Alan Wilder Wild
    public void onInitialize(String modId, ModContainer container) {
        WilderSharedConstants.startMeasuring(this);
        applyDataFixes(container);

        WilderRegistry.initRegistry();
        RegisterBlocks.registerBlocks();
        RegisterItems.registerItems();
		WilderTreeConfigured.init();
		WilderTreePlaced.init();
		WilderMiscConfigured.init();
		WilderMiscPlaced.init();
        WilderConfiguredFeatures.init();
        WilderPlacedFeatures.init();
        WilderWorldGen.generateWildWorldGen();
        RegisterGameEvents.registerEvents();
        RegisterWorldgen.registerWorldgen();
        RegisterStructures.init();
		RegisterItems.registerBlockItems();
		RegisterBlocks.registerDispenses();

        RegisterSounds.init();
        RegisterBlockSoundTypes.init();
        RegisterBlockEntities.register();
        RegisterEntities.init();
        RegisterLootTables.init();
        RegisterParticles.registerParticles();
		RegisterResources.register(container);
		RegisterProperties.init();
		RegisterCriteria.init();

		Registry.register(Registry.CONDITION, WilderSharedConstants.id("beta_beach_condition_source"), BetaBeachConditionSource.CODEC.codec());

		TermiteManager.Termite.addDegradableBlocks();
		TermiteManager.Termite.addNaturalDegradableBlocks();

		RegisterBlocks.registerBlockProperties();

		ServerLifecycleEvents.SERVER_STOPPED.register((server) -> PalmCrownBlockEntity.PalmCrownPositions.clearAll());
		ServerTickEvents.START_SERVER_TICK.register((listener) -> PalmCrownBlockEntity.PalmCrownPositions.clearAndSwitch());

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> SpreadSculkCommand.register(dispatcher));

		WilderModIntegrations.init();

        WilderSharedConstants.stopMeasuring(this);
    }

	@Override
	public void newCategories(ArrayList<FrozenMobCategory> context) {
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("fireflies"), WilderSharedConstants.config().fireflySpawnCap(), true, false, 80));
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("jellyfish"), WilderSharedConstants.config().jellyfishSpawnCap(), true, false, 64));
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("tumbleweed"), WilderSharedConstants.config().tumbleweedSpawnCap(), true, false, 64));
	}

    private static void applyDataFixes(final @NotNull ModContainer mod) {
        WilderSharedConstants.log("Applying DataFixes for Wilder Wild with Data Version " + WilderSharedConstants.DATA_VERSION, true);
        var builder = new QuiltDataFixerBuilder(WilderSharedConstants.DATA_VERSION);
        builder.addSchema(0, QuiltDataFixes.BASE_SCHEMA);
        Schema schemaV1 = builder.addSchema(1, NamespacedSchema::new);
        SimpleFixes.addBlockRenameFix(builder, "Rename white_dandelion to blooming_dandelion", WilderSharedConstants.id("white_dandelion"), WilderSharedConstants.id("blooming_dandelion"), schemaV1);
        SimpleFixes.addBlockRenameFix(builder, "Rename potted_white_dandelion to potted_blooming_dandelion", WilderSharedConstants.id("potted_white_dandelion"), WilderSharedConstants.id("potted_blooming_dandelion"), schemaV1);
        Schema schemaV2 = builder.addSchema(2, NamespacedSchema::new);
        SimpleFixes.addBlockRenameFix(builder, "Rename blooming_dandelion to seeding_dandelion", WilderSharedConstants.id("blooming_dandelion"), WilderSharedConstants.id("seeding_dandelion"), schemaV2);
        SimpleFixes.addBlockRenameFix(builder, "Rename potted_blooming_dandelion to potted_seeding_dandelion", WilderSharedConstants.id("potted_blooming_dandelion"), WilderSharedConstants.id("potted_seeding_dandelion"), schemaV2);
        Schema schemaV3 = builder.addSchema(3, NamespacedSchema::new);
        SimpleFixes.addBlockRenameFix(builder, "Rename floating_moss to algae", WilderSharedConstants.id("floating_moss"), WilderSharedConstants.id("algae"), schemaV3);
        SimpleFixes.addItemRenameFix(builder, "Rename floating_moss to algae", WilderSharedConstants.id("floating_moss"), WilderSharedConstants.id("algae"), schemaV3);
        Schema schemaV4 = builder.addSchema(4, NamespacedSchema::new);
        SimpleFixes.addBlockRenameFix(builder, "Rename test_1 to null_block", WilderSharedConstants.id("test_1"), WilderSharedConstants.id("null_block"), schemaV4);
        Schema schemaV5 = builder.addSchema(5, NamespacedSchema::new);
        SimpleFixes.addBlockRenameFix(builder, "Rename sculk_echoer to null_block", WilderSharedConstants.id("sculk_echoer"), WilderSharedConstants.id("null_block"), schemaV5);
        SimpleFixes.addBlockRenameFix(builder, "Rename sculk_jaw to null_block", WilderSharedConstants.id("sculk_jaw"), WilderSharedConstants.id("null_block"), schemaV5);
        Schema schemaV6 = builder.addSchema(6, NamespacedSchema::new);
        SimpleFixes.addBlockRenameFix(builder, "Rename baobab_sapling to baobab_nut", WilderSharedConstants.id("baobab_sapling"), WilderSharedConstants.id("baobab_nut"), schemaV6);
        SimpleFixes.addBlockRenameFix(builder, "Rename baobab_nut_sapling to baobab_nut", WilderSharedConstants.id("baobab_nut_sapling"), WilderSharedConstants.id("baobab_nut"), schemaV6);
        SimpleFixes.addBlockRenameFix(builder, "Rename potted_baobab_sapling to potted_baobab_nut", WilderSharedConstants.id("potted_baobab_sapling"), WilderSharedConstants.id("potted_baobab_nut"), schemaV6);
        Schema schemaV7 = builder.addSchema(7, NamespacedSchema::new);
        SimpleFixes.addBlockRenameFix(builder, "Rename firefly_lantern to display_lantern", WilderSharedConstants.id("firefly_lantern"), WilderSharedConstants.id("display_lantern"), schemaV7);
        SimpleFixes.addBlockRenameFix(builder, "Rename mesoglea to blue_pearlescent_mesoglea", WilderSharedConstants.id("mesoglea"), WilderSharedConstants.id("blue_pearlescent_mesoglea"), schemaV7);
        SimpleFixes.addItemRenameFix(builder, "Rename mesoglea to blue_pearlescent_mesoglea", WilderSharedConstants.id("mesoglea"), WilderSharedConstants.id("blue_pearlescent_mesoglea"), schemaV7);
        Schema schemaV8 = builder.addSchema(8, NamespacedSchema::new);
        SimpleFixes.addBlockStateRenameFix(builder, "display_lantern_rename_fix", WilderSharedConstants.id("display_lantern"), "light", "0", "display_light", schemaV8);
		Schema schemaV9 = builder.addSchema(9, NamespacedSchema::new);
		builder.addFixer(new NematocystStateFix(schemaV9, "blue_nematocyst_fix", WilderSharedConstants.id("blue_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "blue_pearlescent_nematocyst_fix", WilderSharedConstants.id("blue_pearlescent_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "lime_nematocyst_fix", WilderSharedConstants.id("lime_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "pink_nematocyst_fix", WilderSharedConstants.id("pink_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "purple_pearlescent_nematocyst_fix", WilderSharedConstants.id("purple_pearlescent_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "red_nematocyst_fix", WilderSharedConstants.id("red_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "yellow_nematocyst_fix", WilderSharedConstants.id("yellow_nematocyst")));
		Schema schemaV10 = builder.addSchema(10, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename palm_sapling to coconut", WilderSharedConstants.id("palm_sapling"), WilderSharedConstants.id("coconut"), schemaV10);
		Schema schemaV11 = builder.addSchema(11, NamespacedSchema::new);
		builder.addFixer(new DrySandStateFix(schemaV11, "dry_sand_crackness_to_crackedness", WilderSharedConstants.id("dry_sand")));
		SimpleFixes.addBlockRenameFix(builder, "Rename dry_sand to scorched_sand", WilderSharedConstants.id("dry_sand"), WilderSharedConstants.id("scorched_sand"), schemaV11);
		SimpleFixes.addItemRenameFix(builder, "Rename dry_sand to scorched_sand", WilderSharedConstants.id("dry_sand"), WilderSharedConstants.id("scorched_sand"), schemaV11);
		builder.addFixer(new DrySandStateFix(schemaV11, "scorched_sand_crackness_to_crackedness", WilderSharedConstants.id("scorched_sand")));
		Schema schemaV13 = builder.addSchema(13, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename palm_leaves to palm_fronds", WilderSharedConstants.id("palm_leaves"), WilderSharedConstants.id("palm_fronds"), schemaV13);
		SimpleFixes.addItemRenameFix(builder, "Rename palm_leaves to palm_fronds", WilderSharedConstants.id("palm_leaves"), WilderSharedConstants.id("palm_fronds"), schemaV13);
        QuiltDataFixes.buildAndRegisterFixer(mod, builder);
		WilderSharedConstants.log("DataFixes for Wilder Wild have been applied", true);
    }

    private static <P extends TrunkPlacer> TrunkPlacerType<P> registerTrunk(String id, Codec<P> codec) {
        return Registry.register(Registry.TRUNK_PLACER_TYPES, WilderSharedConstants.id(id), new TrunkPlacerType<>(codec));
    }

	private static <P extends FoliagePlacer> FoliagePlacerType<P> registerFoliage(String id, Codec<P> codec) {
		return Registry.register(Registry.FOLIAGE_PLACER_TYPES, WilderSharedConstants.id(id), new FoliagePlacerType<>(codec));
	}

	private static <FC extends FeatureConfiguration, T extends Feature<FC>> T register(String id, T feature) {
		return Registry.register(Registry.FEATURE, WilderSharedConstants.id(id), feature);
	}

    // GAME RULES
    public static final GameRules.Key<GameRules.BooleanValue> STONE_CHEST_CLOSES =
            GameRuleRegistry.register("stoneChestCloses", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));

    // PACKETS
    public static final ResourceLocation SEED_PACKET = WilderSharedConstants.id("seed_particle_packet");
    public static final ResourceLocation CONTROLLED_SEED_PACKET = WilderSharedConstants.id("controlled_seed_particle_packet");
    public static final ResourceLocation FLOATING_SCULK_BUBBLE_PACKET = WilderSharedConstants.id("floating_sculk_bubble_easy_packet");
    public static final ResourceLocation TERMITE_PARTICLE_PACKET = WilderSharedConstants.id("termite_particle_packet");
    public static final ResourceLocation HORN_PROJECTILE_PACKET_ID = WilderSharedConstants.id("ancient_horn_projectile_packet");
    public static final ResourceLocation SENSOR_HICCUP_PACKET = WilderSharedConstants.id("sensor_hiccup_packet");
    public static final ResourceLocation JELLY_STING_PACKET = WilderSharedConstants.id("jelly_sting_packet");
}
