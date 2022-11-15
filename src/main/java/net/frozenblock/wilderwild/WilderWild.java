package net.frozenblock.wilderwild;

import com.chocohead.mm.api.ClassTinkerers;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Codec;
import java.util.HashMap;
import java.util.Map;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.misc.BlockSoundGroupOverwrites;
import net.frozenblock.wilderwild.misc.FireflyColor;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
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
import net.frozenblock.wilderwild.registry.WilderRegistry;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.feature.WilderConfiguredFeatures;
import net.frozenblock.wilderwild.world.feature.WilderMiscConfigured;
import net.frozenblock.wilderwild.world.feature.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.frozenblock.wilderwild.world.feature.WilderTreeConfigured;
import net.frozenblock.wilderwild.world.feature.WilderTreePlaced;
import net.frozenblock.wilderwild.world.feature.features.AlgaeFeature;
import net.frozenblock.wilderwild.world.feature.features.CattailFeature;
import net.frozenblock.wilderwild.world.feature.features.ColumnWithDiskFeature;
import net.frozenblock.wilderwild.world.feature.features.DownwardsPillarFeature;
import net.frozenblock.wilderwild.world.feature.features.NematocystFeature;
import net.frozenblock.wilderwild.world.feature.features.NoisePathFeature;
import net.frozenblock.wilderwild.world.feature.features.NoisePathUnderWaterFeature;
import net.frozenblock.wilderwild.world.feature.features.NoisePlantFeature;
import net.frozenblock.wilderwild.world.feature.features.ShelfFungusFeature;
import net.frozenblock.wilderwild.world.feature.features.UpwardsPillarFeature;
import net.frozenblock.wilderwild.world.feature.features.config.ColumnWithDiskFeatureConfig;
import net.frozenblock.wilderwild.world.feature.features.config.NematocystFeatureConfig;
import net.frozenblock.wilderwild.world.feature.features.config.PathFeatureConfig;
import net.frozenblock.wilderwild.world.feature.features.config.ShelfFungusFeatureConfig;
import net.frozenblock.wilderwild.world.feature.features.config.WilderPillarConfig;
import net.frozenblock.wilderwild.world.gen.WilderWorldGen;
import net.frozenblock.wilderwild.world.gen.trunk.BaobabTrunkPlacer;
import net.frozenblock.wilderwild.world.gen.trunk.FallenTrunkWithLogs;
import net.frozenblock.wilderwild.world.gen.trunk.StraightTrunkWithLogs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
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
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
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

    public static boolean areConfigsInit = false;

    public static final TrunkPlacerType<StraightTrunkWithLogs> STRAIGHT_TRUNK_WITH_LOGS_PLACER_TYPE = registerTrunk("straight_trunk_logs_placer", StraightTrunkWithLogs.CODEC);
    public static final TrunkPlacerType<FallenTrunkWithLogs> FALLEN_TRUNK_WITH_LOGS_PLACER_TYPE = registerTrunk("fallen_trunk_logs_placer", FallenTrunkWithLogs.CODEC);
    public static final TrunkPlacerType<BaobabTrunkPlacer> BAOBAB_TRUNK_PLACER = registerTrunk("baobab_trunk_placer", BaobabTrunkPlacer.CODEC);
    public static final Feature<ShelfFungusFeatureConfig> SHELF_FUNGUS_FEATURE = new ShelfFungusFeature(ShelfFungusFeatureConfig.CODEC);
    public static final CattailFeature CATTAIL_FEATURE = new CattailFeature(ProbabilityFeatureConfiguration.CODEC);
    public static final AlgaeFeature ALGAE_FEATURE = new AlgaeFeature(ProbabilityFeatureConfiguration.CODEC);
    public static final NoisePathFeature NOISE_PATH_FEATURE = new NoisePathFeature(PathFeatureConfig.CODEC);
    public static final NoisePlantFeature NOISE_PLANT_FEATURE = new NoisePlantFeature(PathFeatureConfig.CODEC);
    public static final NoisePathUnderWaterFeature NOISE_PATH_UNDER_WATER_FEATURE = new NoisePathUnderWaterFeature(PathFeatureConfig.CODEC);
    public static final ColumnWithDiskFeature COLUMN_WITH_DISK_FEATURE = new ColumnWithDiskFeature(ColumnWithDiskFeatureConfig.CODEC);
    public static final UpwardsPillarFeature UPWARDS_PILLAR_FEATURE = new UpwardsPillarFeature(WilderPillarConfig.CODEC);
    public static final DownwardsPillarFeature DOWNWARDS_PILLAR_FEATURE = new DownwardsPillarFeature(WilderPillarConfig.CODEC);
    public static final NematocystFeature NEMATOCYST_FEATURE = new NematocystFeature(NematocystFeatureConfig.CODEC);

    //Fabric ASM
    public static final MobCategory FIREFLIES = ClassTinkerers.getEnum(MobCategory.class, "WILDERWILDFIREFLIES");
    public static final MobCategory JELLYFISH = ClassTinkerers.getEnum(MobCategory.class, "WILDERWILDJELLYFISH");

    public static RandomSource random() {
        return RandomSource.create();
    }

    @Override
    public void onInitialize() {
        startMeasuring(this);
        applyDataFixes(FabricLoader.getInstance().getModContainer(WilderSharedConstants.MOD_ID).orElseThrow());

        WilderRegistry.initRegistry();
        RegisterBlocks.registerBlocks();
        RegisterItems.registerItems();
        RegisterGameEvents.registerEvents();
		WilderWorldGen.generateWildWorldGen();

        RegisterSounds.init();
        RegisterBlockSoundGroups.init();
        RegisterBlockEntities.register();
        RegisterEntities.init();
        BlockSoundGroupOverwrites.init();
        RegisterLootTables.init();
        RegisterParticles.registerParticles();
		RegisterResources.register();

        Registry.register(BuiltInRegistries.FEATURE, WilderSharedConstants.id("shelf_fungus_feature"), SHELF_FUNGUS_FEATURE);
        Registry.register(BuiltInRegistries.FEATURE, WilderSharedConstants.id("cattail_feature"), CATTAIL_FEATURE);
        Registry.register(BuiltInRegistries.FEATURE, WilderSharedConstants.id("algae_feature"), ALGAE_FEATURE);
        Registry.register(BuiltInRegistries.FEATURE, WilderSharedConstants.id("noise_path_feature"), NOISE_PATH_FEATURE);
        Registry.register(BuiltInRegistries.FEATURE, WilderSharedConstants.id("noise_plant_feature"), NOISE_PLANT_FEATURE);
        Registry.register(BuiltInRegistries.FEATURE, WilderSharedConstants.id("noise_path_under_water_feature"), NOISE_PATH_UNDER_WATER_FEATURE);
        Registry.register(BuiltInRegistries.FEATURE, WilderSharedConstants.id("column_with_disk_feature"), COLUMN_WITH_DISK_FEATURE);
        Registry.register(BuiltInRegistries.FEATURE, WilderSharedConstants.id("upwards_pillar"), UPWARDS_PILLAR_FEATURE);
        Registry.register(BuiltInRegistries.FEATURE, WilderSharedConstants.id("downwards_pillar"), DOWNWARDS_PILLAR_FEATURE);
        Registry.register(BuiltInRegistries.FEATURE, WilderSharedConstants.id("nematocyst_feature"), NEMATOCYST_FEATURE);


        TermiteMoundBlockEntity.Termite.addDegradableBlocks();
        TermiteMoundBlockEntity.Termite.addNaturalDegradableBlocks();

        if (FrozenBools.HAS_TERRALITH) {
            terralith();
        }

        stopMeasuring(this);
    }

    public static final int DATA_VERSION = 8;

    private static void applyDataFixes(ModContainer mod) {
        log("Applying DataFixes for Wilder Wild", true);
        var builder = new QuiltDataFixerBuilder(DATA_VERSION);
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

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(ResourceKey.create(Registries.BIOME, new ResourceLocation("terralith", "cave/underground_jungle"))),
                WilderWild.FIREFLIES, RegisterEntities.FIREFLY, 12, 2, 4);

		WilderRegistry.MULTILAYER_SAND_BEACH_BIOMES.add(ResourceKey.create(Registries.BIOME, new ResourceLocation("terralith", "arid_highlands")));
    }

    public static boolean isCopperPipe(BlockState state) {
        if (FrozenBools.HAS_SIMPLE_COPPER_PIPES) {
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(state.getBlock());
            return id.getNamespace().equals("lunade") && id.getPath().contains("pipe");
        }
        return false;
    }

    // LOGGING
    public static void log(String string, boolean shouldLog) {
        if (shouldLog) {
            WilderSharedConstants.LOGGER.info(string);
        }
    }

    public static void logInsane(String string, boolean shouldLog) {
        if (shouldLog) {
            for (int i = 0; i < Math.random() * 5; i++) {
                WilderSharedConstants.LOGGER.warn(string);
                WilderSharedConstants.LOGGER.error(string);
                WilderSharedConstants.LOGGER.warn(string);
                WilderSharedConstants.LOGGER.error(string);
                WilderSharedConstants.LOGGER.warn(string);
                WilderSharedConstants.LOGGER.error(string);
                WilderSharedConstants.LOGGER.warn(string);
                WilderSharedConstants.LOGGER.error(string);
            }
        }
    }

    public static void log(Entity entity, String string, boolean shouldLog) {
        if (shouldLog) {
            WilderSharedConstants.LOGGER.info(entity.toString() + " : " + string + " : " + entity.position());
        }
    }

    public static void log(Block block, String string, boolean shouldLog) {
        if (shouldLog) {
            WilderSharedConstants.LOGGER.info(block.toString() + " : " + string + " : ");
        }
    }

    public static void log(Block block, BlockPos pos, String string, boolean shouldLog) {
        if (shouldLog) {
            WilderSharedConstants.LOGGER.info(block.toString() + " : " + string + " : " + pos);
        }
    }

    public static void logWild(String string, boolean shouldLog) {
        if (shouldLog) {
            WilderSharedConstants.LOGGER.info(string + " " + WilderSharedConstants.MOD_ID);
        }
    }

    private static <P extends TrunkPlacer> TrunkPlacerType<P> registerTrunk(String id, Codec<P> codec) {
        return Registry.register(BuiltInRegistries.TRUNK_PLACER_TYPE, WilderSharedConstants.id(id), new TrunkPlacerType<>(codec));
    }

    // MEASURING
    public static final Map<Object, Long> INSTANT_MAP = new HashMap<>();

    public static void startMeasuring(Object object) {
        long started = System.nanoTime();
        String name = object.getClass().getName();
        WilderSharedConstants.LOGGER.error("Started measuring {}", name.substring(name.lastIndexOf(".") + 1));
        INSTANT_MAP.put(object, started);
    }

    public static void stopMeasuring(Object object) {
        if (INSTANT_MAP.containsKey(object)) {
            String name = object.getClass().getName();
            WilderSharedConstants.LOGGER.error("{} took {} nanoseconds", name.substring(name.lastIndexOf(".") + 1), System.nanoTime() - INSTANT_MAP.get(object));
            INSTANT_MAP.remove(object);
        }
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

    public static final ResourceLocation CAPTURE_FIREFLY_NOTIFY_PACKET = WilderSharedConstants.id("capture_firefly_notify_packet");
    public static final ResourceLocation ANCIENT_HORN_KILL_NOTIFY_PACKET = WilderSharedConstants.id("ancient_horn_kill_notify_packet");

	public static String string(String path) {
        return WilderSharedConstants.id(path).toString();
    }

}
