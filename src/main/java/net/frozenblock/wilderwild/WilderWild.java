package net.frozenblock.wilderwild;

import com.chocohead.mm.api.ClassTinkerers;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Codec;
import java.util.HashMap;
import java.util.Map;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.wilderwild.block.entity.PalmCrownBlockEntity;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.misc.FireflyColor;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.datafixer.NematocystStateFix;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterGameEvents;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterLootTables;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.frozenblock.wilderwild.registry.RegisterResources;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.registry.RegisterStructures;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.registry.WilderRegistry;
import net.frozenblock.wilderwild.world.additions.feature.WilderConfiguredFeatures;
import net.frozenblock.wilderwild.world.additions.feature.WilderMiscConfigured;
import net.frozenblock.wilderwild.world.additions.feature.WilderPlacedFeatures;
import net.frozenblock.wilderwild.world.additions.feature.WilderTreeConfigured;
import net.frozenblock.wilderwild.world.additions.feature.WilderTreePlaced;
import net.frozenblock.wilderwild.world.additions.gen.WilderWorldGen;
import net.frozenblock.wilderwild.world.generation.features.AlgaeFeature;
import net.frozenblock.wilderwild.world.generation.features.CattailFeature;
import net.frozenblock.wilderwild.world.generation.features.ColumnWithDiskFeature;
import net.frozenblock.wilderwild.world.generation.features.DownwardsPillarFeature;
import net.frozenblock.wilderwild.world.generation.features.NematocystFeature;
import net.frozenblock.wilderwild.world.generation.features.NoisePathFeature;
import net.frozenblock.wilderwild.world.generation.features.NoisePathSwapUnderWaterFeature;
import net.frozenblock.wilderwild.world.generation.features.NoisePathUnderWaterFeature;
import net.frozenblock.wilderwild.world.generation.features.NoisePlantFeature;
import net.frozenblock.wilderwild.world.generation.features.ShelfFungusFeature;
import net.frozenblock.wilderwild.world.generation.features.UpwardsPillarFeature;
import net.frozenblock.wilderwild.world.generation.features.config.ColumnWithDiskFeatureConfig;
import net.frozenblock.wilderwild.world.generation.features.config.PathFeatureConfig;
import net.frozenblock.wilderwild.world.generation.features.config.PathSwapUnderWaterFeatureConfig;
import net.frozenblock.wilderwild.world.generation.features.config.ShelfFungusFeatureConfig;
import net.frozenblock.wilderwild.world.generation.features.config.WilderPillarConfig;
import net.frozenblock.wilderwild.world.generation.foliage.PalmFoliagePlacer;
import net.frozenblock.wilderwild.world.generation.foliage.ShortPalmFoliagePlacer;
import net.frozenblock.wilderwild.world.generation.trunk.BaobabTrunkPlacer;
import net.frozenblock.wilderwild.world.generation.trunk.FallenTrunkWithLogs;
import net.frozenblock.wilderwild.world.generation.trunk.PalmTrunkPlacer;
import net.frozenblock.wilderwild.world.generation.trunk.StraightTrunkWithLogs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixerBuilder;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixes;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.SimpleFixes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class WilderWild implements ModInitializer {
	/**
	 * @deprecated Use {@link WilderSharedConstants#MOD_ID} instead.
	 */
	@Deprecated(forRemoval = true)
    public static final String MOD_ID = "wilderwild";
	/**
	 * @deprecated Use {@link WilderSharedConstants#LOGGER} instead.
	 */
	@Deprecated(forRemoval = true)
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	/**
	 * @deprecated Use {@link WilderSharedConstants#DEV_LOGGING} instead.
	 */
	@Deprecated(forRemoval = true)
    public static boolean DEV_LOGGING = false;
    /**
     * Used for features that may be unstable and crash in public builds.
     * <p>
     * It's smart to use this for at least registries.
	 * @deprecated Use {@link WilderSharedConstants#UNSTABLE_LOGGING} instead.
     */
	@Deprecated(forRemoval = true)
    public static boolean UNSTABLE_LOGGING = FabricLoader.getInstance().isDevelopmentEnvironment();

	public static final TrunkPlacerType<StraightTrunkWithLogs> STRAIGHT_TRUNK_WITH_LOGS_PLACER_TYPE = registerTrunk("straight_trunk_logs_placer", StraightTrunkWithLogs.CODEC);
    public static final TrunkPlacerType<FallenTrunkWithLogs> FALLEN_TRUNK_WITH_LOGS_PLACER_TYPE = registerTrunk("fallen_trunk_logs_placer", FallenTrunkWithLogs.CODEC);
    public static final TrunkPlacerType<BaobabTrunkPlacer> BAOBAB_TRUNK_PLACER = registerTrunk("baobab_trunk_placer", BaobabTrunkPlacer.CODEC);
	public static final TrunkPlacerType<PalmTrunkPlacer> PALM_TRUNK_PLACER = registerTrunk("palm_trunk_placer", PalmTrunkPlacer.CODEC);
    public static final Feature<ShelfFungusFeatureConfig> SHELF_FUNGUS_FEATURE = new ShelfFungusFeature(ShelfFungusFeatureConfig.CODEC);
    public static final CattailFeature CATTAIL_FEATURE = new CattailFeature(ProbabilityFeatureConfiguration.CODEC);
    public static final AlgaeFeature ALGAE_FEATURE = new AlgaeFeature(ProbabilityFeatureConfiguration.CODEC);
    public static final NoisePathFeature NOISE_PATH_FEATURE = new NoisePathFeature(PathFeatureConfig.CODEC);
    public static final NoisePlantFeature NOISE_PLANT_FEATURE = new NoisePlantFeature(PathFeatureConfig.CODEC);
	public static final NoisePathSwapUnderWaterFeature NOISE_PATH_SWAP_UNDER_WATER_FEATURE = new NoisePathSwapUnderWaterFeature(PathSwapUnderWaterFeatureConfig.CODEC);
    public static final NoisePathUnderWaterFeature NOISE_PATH_UNDER_WATER_FEATURE = new NoisePathUnderWaterFeature(PathFeatureConfig.CODEC);
    public static final ColumnWithDiskFeature COLUMN_WITH_DISK_FEATURE = new ColumnWithDiskFeature(ColumnWithDiskFeatureConfig.CODEC);
    public static final UpwardsPillarFeature UPWARDS_PILLAR_FEATURE = new UpwardsPillarFeature(WilderPillarConfig.CODEC);
    public static final DownwardsPillarFeature DOWNWARDS_PILLAR_FEATURE = new DownwardsPillarFeature(WilderPillarConfig.CODEC);
    public static final NematocystFeature NEMATOCYST_FEATURE = new NematocystFeature(MultifaceGrowthConfiguration.CODEC);
    public static final FoliagePlacerType<PalmFoliagePlacer> PALM_FOLIAGE_PLACER =  registerFoliage("palm_foliage_placer", PalmFoliagePlacer.CODEC);
	public static final FoliagePlacerType<ShortPalmFoliagePlacer> SHORT_PALM_FOLIAGE_PLACER =  registerFoliage("short_palm_foliage_placer", ShortPalmFoliagePlacer.CODEC);

    //Fabric ASM
    public static final MobCategory FIREFLIES = ClassTinkerers.getEnum(MobCategory.class, "WILDERWILDFIREFLIES");
    public static final MobCategory JELLYFISH = ClassTinkerers.getEnum(MobCategory.class, "WILDERWILDJELLYFISH");
	public static final MobCategory TUMBLEWEED = ClassTinkerers.getEnum(MobCategory.class, "WILDERWILDTUMBLEWEED");

	/**
	 * @deprecated Use {@link WilderSharedConstants#random()} instead.
	 */
	@Deprecated(forRemoval = true)
    public static RandomSource random() {
        return RandomSource.create();
    }

    @Override
    public void onInitialize() {
        WilderSharedConstants.startMeasuring(this);
        applyDataFixes(WilderSharedConstants.MOD_CONTAINER);

        WilderRegistry.initRegistry();
        RegisterBlocks.registerBlocks();
        RegisterItems.registerBlockItems();
        RegisterItems.registerItems();
        WilderConfiguredFeatures.registerConfiguredFeatures();
        WilderPlacedFeatures.init();
        WilderTreeConfigured.registerTreeConfigured();
        WilderTreePlaced.registerTreePlaced();
        WilderMiscConfigured.registerMiscPlaced();
        WilderWorldGen.generateWildWorldGen();
        RegisterGameEvents.registerEvents();
        RegisterWorldgen.registerWorldgen();
        RegisterStructures.init();

        RegisterSounds.init();
        RegisterBlockSoundGroups.init();
        RegisterBlockEntities.register();
        RegisterEntities.init();
        RegisterLootTables.init();
        RegisterParticles.registerParticles();
		RegisterResources.register();

        Registry.register(Registry.FEATURE, id("shelf_fungus_feature"), SHELF_FUNGUS_FEATURE);
        Registry.register(Registry.FEATURE, id("cattail_feature"), CATTAIL_FEATURE);
        Registry.register(Registry.FEATURE, id("algae_feature"), ALGAE_FEATURE);
        Registry.register(Registry.FEATURE, id("noise_path_feature"), NOISE_PATH_FEATURE);
        Registry.register(Registry.FEATURE, id("noise_plant_feature"), NOISE_PLANT_FEATURE);
		Registry.register(Registry.FEATURE, id("noise_path_swap_under_water_feature"), NOISE_PATH_SWAP_UNDER_WATER_FEATURE);
        Registry.register(Registry.FEATURE, id("noise_path_under_water_feature"), NOISE_PATH_UNDER_WATER_FEATURE);
        Registry.register(Registry.FEATURE, id("column_with_disk_feature"), COLUMN_WITH_DISK_FEATURE);
        Registry.register(Registry.FEATURE, id("upwards_pillar"), UPWARDS_PILLAR_FEATURE);
        Registry.register(Registry.FEATURE, id("downwards_pillar"), DOWNWARDS_PILLAR_FEATURE);
        Registry.register(Registry.FEATURE, id("nematocyst_feature"), NEMATOCYST_FEATURE);


        TermiteMoundBlockEntity.Termite.addDegradableBlocks();
        TermiteMoundBlockEntity.Termite.addNaturalDegradableBlocks();

        if (FrozenBools.HAS_TERRALITH) {
            terralith();
        }

		ServerLifecycleEvents.SERVER_STOPPED.register((server) -> PalmCrownBlockEntity.PalmCrownPositions.clearAll());

		ServerTickEvents.START_SERVER_TICK.register((listener) -> PalmCrownBlockEntity.PalmCrownPositions.clearAndSwitch());

        stopMeasuring(this);
        WilderSharedConstants.stopMeasuring(this);
    }

	/**
	 * @deprecated Use {@link WilderSharedConstants#DATA_VERSION} instead.
	 */
	@Deprecated(forRemoval = true)
    public static final int DATA_VERSION = 9;

    private static void applyDataFixes(final @NotNull ModContainer mod) {
        log("Applying DataFixes for Wilder Wild with Data Version " + WilderSharedConstants.DATA_VERSION, true);
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

        QuiltDataFixes.buildAndRegisterFixer(mod, builder);
        log("DataFixes for Wilder Wild have been applied", true);
        //return builder;
    }

    //MOD COMPATIBILITY
    public static void terralith() {
        Firefly.FireflyBiomeColorRegistry.addBiomeColor(new ResourceLocation("terralith", "cave/frostfire_caves"), FireflyColor.BLUE);
        Firefly.FireflyBiomeColorRegistry.addBiomeColor(new ResourceLocation("terralith", "cave/frostfire_caves"), FireflyColor.LIGHT_BLUE);

        Firefly.FireflyBiomeColorRegistry.addBiomeColor(new ResourceLocation("terralith", "cave/thermal_caves"), FireflyColor.RED);
        Firefly.FireflyBiomeColorRegistry.addBiomeColor(new ResourceLocation("terralith", "cave/thermal_caves"), FireflyColor.ORANGE);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation("terralith", "cave/underground_jungle"))),
                WilderWild.FIREFLIES, RegisterEntities.FIREFLY, 12, 2, 4);

		WilderRegistry.MULTILAYER_SAND_BEACH_BIOMES.add(ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation("terralith", "arid_highlands")));
    }

    public static boolean isCopperPipe(BlockState state) {
        if (FrozenBools.HAS_SIMPLE_COPPER_PIPES) {
            ResourceLocation id = Registry.BLOCK.getKey(state.getBlock());
            return id.getNamespace().equals("lunade") && id.getPath().contains("pipe");
        }
        return false;
    }

    // LOGGING

	/**
	 * @deprecated Use {@link WilderSharedConstants#log(String, boolean)} instead.
	 */
	@Deprecated(forRemoval = true)
    public static void log(String string, boolean shouldLog) {
        WilderSharedConstants.log(string, shouldLog);
    }

	/**
	 * @deprecated Use {@link WilderSharedConstants#logInsane(String, boolean)} instead.
	 */
	@Deprecated(forRemoval = true)
    public static void logInsane(String string, boolean shouldLog) {
        WilderSharedConstants.logInsane(string, shouldLog);
    }

	/**
	 * @deprecated Use {@link WilderSharedConstants#log(Entity, String, boolean)} instead.
	 */
	@Deprecated(forRemoval = true)
    public static void log(Entity entity, String string, boolean shouldLog) {
        WilderSharedConstants.log(entity, string, shouldLog);
    }

	/**
	 * @deprecated Use {@link WilderSharedConstants#log(Block, String, boolean)} instead.
	 */
	@Deprecated(forRemoval = true)
    public static void log(Block block, String string, boolean shouldLog) {
        WilderSharedConstants.log(block, string, shouldLog);
    }

	/**
	 * @deprecated Use {@link WilderSharedConstants#log(Block, BlockPos, String, boolean)} instead.
	 */
	@Deprecated(forRemoval = true)
    public static void log(Block block, BlockPos pos, String string, boolean shouldLog) {
        WilderSharedConstants.log(block, pos, string, shouldLog);
    }

	/**
	 * @deprecated Use {@link WilderSharedConstants#logWild(String, boolean)} instead.
	 */
	@Deprecated(forRemoval = true)
    public static void logWild(String string, boolean shouldLog) {
        WilderSharedConstants.logWild(string, shouldLog);
    }

    private static <P extends TrunkPlacer> TrunkPlacerType<P> registerTrunk(String id, Codec<P> codec) {
        return Registry.register(Registry.TRUNK_PLACER_TYPES, id(id), new TrunkPlacerType<>(codec));
    }

    private static <P extends FoliagePlacer> FoliagePlacerType<P> registerFoliage(String id, Codec<P> codec) {
        return Registry.register(Registry.FOLIAGE_PLACER_TYPES, id(id), new FoliagePlacerType<>(codec));
    }

    // MEASURING
	/**
	 * @deprecated Use {@link WilderSharedConstants#INSTANT_MAP} instead.
	 */
	@Deprecated(forRemoval = true)
    public static final Map<Object, Long> INSTANT_MAP = WilderSharedConstants.INSTANT_MAP;

	/**
	 * @deprecated Use {@link WilderSharedConstants#startMeasuring(Object)} instead.
	 */
	@Deprecated(forRemoval = true)
    public static void startMeasuring(Object object) {
        WilderSharedConstants.startMeasuring(object);
    }

	/**
	 * @deprecated Use {@link WilderSharedConstants#stopMeasuring(Object)} instead.
	 */
	@Deprecated(forRemoval = true)
    public static void stopMeasuring(Object object) {
        WilderSharedConstants.stopMeasuring(object);
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

	public static final ResourceLocation ANCIENT_HORN_KILL_NOTIFY_PACKET = WilderSharedConstants.id("ancient_horn_kill_notify_packet");
	public static final ResourceLocation CAPTURE_FIREFLY_NOTIFY_PACKET = WilderSharedConstants.id("capture_firefly_notify_packet");

	/**
	 * @deprecated Use {@link WilderSharedConstants#id(String)} instead.
	 */
	@Deprecated(forRemoval = true)
    public static ResourceLocation id(String path) {
        return WilderSharedConstants.id(path);
    }

	/**
	 * @deprecated Use {@link WilderSharedConstants#vanillaId(String)} instead.
	 */
	@Deprecated(forRemoval = true)
	public static ResourceLocation vanillaId(String path) {
		return WilderSharedConstants.vanillaId(path);
	}

	/**
	 * @deprecated Use {@link WilderSharedConstants#string(String)} instead.
	 */
	@Deprecated(forRemoval = true)
	public static String string(String path) {
        return WilderSharedConstants.string(path);
    }

}
