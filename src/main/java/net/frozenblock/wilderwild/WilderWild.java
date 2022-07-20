package net.frozenblock.wilderwild;

import com.chocohead.mm.api.ClassTinkerers;
import com.mojang.serialization.Codec;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.misc.BlockSoundGroupOverwrites;
import net.frozenblock.wilderwild.misc.WildConfig;
import net.frozenblock.wilderwild.registry.*;
import net.frozenblock.wilderwild.world.feature.WilderConfiguredFeatures;
import net.frozenblock.wilderwild.world.feature.WilderMiscConfigured;
import net.frozenblock.wilderwild.world.feature.WilderTreeConfigured;
import net.frozenblock.wilderwild.world.feature.WilderTreePlaced;
import net.frozenblock.wilderwild.world.feature.features.*;
import net.frozenblock.wilderwild.world.feature.features.config.ColumnWithDiskFeatureConfig;
import net.frozenblock.wilderwild.world.feature.features.config.PathFeatureConfig;
import net.frozenblock.wilderwild.world.feature.features.config.ShelfFungusFeatureConfig;
import net.frozenblock.wilderwild.world.gen.WilderWorldGen;
import net.frozenblock.wilderwild.world.gen.trunk.BaobabTrunkPlacer;
import net.frozenblock.wilderwild.world.gen.trunk.FallenTrunkWithLogs;
import net.frozenblock.wilderwild.world.gen.trunk.StraightTrunkWithLogs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WilderWild implements ModInitializer {
    public static final String MOD_ID = "wilderwild";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final boolean DEV_LOGGING = false;
    public static final boolean UNSTABLE_LOGGING = true; //Used for features that may possibly be unstable and crash in public builds - it's smart to use this for at least registries.
    public static final String snapshotName = "22wWa";

    public static final TrunkPlacerType<StraightTrunkWithLogs> STRAIGHT_TRUNK_WITH_LOGS_PLACER_TYPE = registerTrunk("straight_trunk_logs_placer", StraightTrunkWithLogs.CODEC);
    public static final TrunkPlacerType<FallenTrunkWithLogs> FALLEN_TRUNK_WITH_LOGS_PLACER_TYPE = registerTrunk("fallen_trunk_logs_placer", FallenTrunkWithLogs.CODEC);
    public static final TrunkPlacerType<BaobabTrunkPlacer> BAOBAB_TRUNK_PLACER = registerTrunk("baobab_trunk_placer", BaobabTrunkPlacer.CODEC);
    public static final Feature<ShelfFungusFeatureConfig> SHELF_FUNGUS_FEATURE = new ShelfFungusFeature(ShelfFungusFeatureConfig.CODEC);
    public static final CattailFeature CATTAIL_FEATURE = new CattailFeature(ProbabilityFeatureConfiguration.CODEC);
    public static final WaterMossFeature WATER_MOSS_FEATURE = new WaterMossFeature(ProbabilityFeatureConfiguration.CODEC);
    public static final NoisePathFeature NOISE_PATH_FEATURE = new NoisePathFeature(PathFeatureConfig.CODEC);
    public static final NoisePlantFeature NOISE_PLANT_FEATURE = new NoisePlantFeature(PathFeatureConfig.CODEC);
    public static final NoisePathUnderWaterFeature NOISE_PATH_UNDER_WATER_FEATURE = new NoisePathUnderWaterFeature(PathFeatureConfig.CODEC);
    public static final ColumnWithDiskFeature COLUMN_WITH_DISK_FEATURE = new ColumnWithDiskFeature(ColumnWithDiskFeatureConfig.CODEC);

    public static final TagKey<Instrument> WILD_HORNS = TagKey.create(Registry.INSTRUMENT_REGISTRY, id("wild_horns"));

    //ClassTinkerers
    public static final MobCategory FIREFLIES = ClassTinkerers.getEnum(MobCategory.class, "FIREFLIES");

    @Override
    public void onInitialize() {
        RegisterBlocks.registerBlocks();
        RegisterBlocks.addBaobab();
        RegisterItems.registerItems();
        WilderConfiguredFeatures.registerConfiguredFeatures();
        WilderTreeConfigured.registerTreeConfigured();
        WilderTreePlaced.registerTreePlaced();
        WilderMiscConfigured.registerMiscPlaced();
        WilderWorldGen.generateWildWorldGen();
        RegisterGameEvents.registerEvents();
        RegisterWorldgen.registerWorldGen();

        RegisterSounds.init();
        RegisterBlockSoundGroups.init();
        RegisterBlockEntities.register();
        RegisterEntities.init();
        BlockSoundGroupOverwrites.init();
        RegisterLootTables.init();
        RegisterParticles.registerParticles();

        RegisterLoopingSoundRestrictions.init();

        Registry.register(Registry.FEATURE, id("shelf_fungus_feature"), SHELF_FUNGUS_FEATURE);
        Registry.register(Registry.FEATURE, id("cattail_feature"), CATTAIL_FEATURE);
        Registry.register(Registry.FEATURE, id("water_moss_feature"), WATER_MOSS_FEATURE);
        Registry.register(Registry.FEATURE, id("noise_path_feature"), NOISE_PATH_FEATURE);
        Registry.register(Registry.FEATURE, id("noise_plant_feature"), NOISE_PLANT_FEATURE);
        Registry.register(Registry.FEATURE, id("noise_path_under_water_feature"), NOISE_PATH_UNDER_WATER_FEATURE);
        Registry.register(Registry.FEATURE, id("column_with_disk_feature"), COLUMN_WITH_DISK_FEATURE);

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) { /* DEV-ONLY */
            RegisterDevelopment.init();
        }

        TermiteMoundBlockEntity.Termite.addDegradableBlocks();
        TermiteMoundBlockEntity.Termite.addNaturalDegradableBlocks();
        WildConfig.makeConfig();
    }

    public static RandomSource random() {
        return RandomSource.create();
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static final ResourceLocation SEED_PACKET = id("seed_particle_packet");
    public static final ResourceLocation CONTROLLED_SEED_PACKET = id("controlled_seed_particle_packet");
    public static final ResourceLocation FLOATING_SCULK_BUBBLE_PACKET = id("floating_sculk_bubble_easy_packet");
    public static final ResourceLocation TERMITE_PARTICLE_PACKET = id("termite_particle_packet");
    public static final ResourceLocation HORN_PROJECTILE_PACKET_ID = id("ancient_horn_projectile_packet");
    public static final ResourceLocation SENSOR_HICCUP_PACKET = id("sensor_hiccup_packet");

    public static final ResourceLocation CAPTURE_FIREFLY_NOTIFY_PACKET = id("capture_firefly_notify_packet");
    public static final ResourceLocation ANCIENT_HORN_KILL_NOTIFY_PACKET = id("ancient_horn_kill_notify_packet");
    public static final ResourceLocation FLYBY_SOUND_PACKET = id("flyby_sound_packet");
    public static final ResourceLocation MOVING_LOOPING_SOUND_PACKET = id("moving_looping_sound_packet");

    public static void log(String string, boolean shouldLog) {
        if (shouldLog) {
            LOGGER.info(string);
        }
    }

    public static void logInsane(String string, boolean shouldLog) {
        if (shouldLog) {
            for (int i = 0; i < Math.random() * 5; i++) {
                LOGGER.warn(string);
                LOGGER.error(string);
                LOGGER.warn(string);
                LOGGER.error(string);
                LOGGER.warn(string);
                LOGGER.error(string);
                LOGGER.warn(string);
                LOGGER.error(string);
            }
        }
    }

    public static void log(Entity entity, String string, boolean shouldLog) {
        if (shouldLog) {
            LOGGER.info(entity.toString() + " : " + string + " : " + entity.position());
        }
    }

    public static void log(Block block, String string, boolean shouldLog) {
        if (shouldLog) {
            LOGGER.info(block.toString() + " : " + string + " : ");
        }
    }

    public static void log(Block block, BlockPos pos, String string, boolean shouldLog) {
        if (shouldLog) {
            LOGGER.info(block.toString() + " : " + string + " : " + pos);
        }
    }

    public static void logWild(String string, boolean shouldLog) {
        if (shouldLog) {
            LOGGER.info(string + " " + MOD_ID);
        }
    }


    private static <P extends TrunkPlacer> TrunkPlacerType<P> registerTrunk(String id, Codec<P> codec) {
        return Registry.register(Registry.TRUNK_PLACER_TYPES, id(id), new TrunkPlacerType<>(codec));
    }
}
