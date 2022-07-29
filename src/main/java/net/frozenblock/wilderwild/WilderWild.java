package net.frozenblock.wilderwild;

import com.chocohead.mm.api.ClassTinkerers;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Codec;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.misc.BlockSoundGroupOverwrites;
import net.frozenblock.wilderwild.misc.simple_pipe_compatability.RegisterSaveableMoveablePipeNbt;
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
import net.minecraft.SharedConstants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.datafixer.Schemas;
import net.minecraft.datafixer.fix.BlockNameFix;
import net.minecraft.datafixer.fix.ItemNameFix;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Instrument;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WilderWild implements ModInitializer {
    public static final String MOD_ID = "wilderwild";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final boolean DEV_LOGGING = false;
    public static boolean UNSTABLE_LOGGING = false; //Used for features that may possibly be unstable and crash in public builds - it's smart to use this for at least registries.

    public static final TrunkPlacerType<StraightTrunkWithLogs> STRAIGHT_TRUNK_WITH_LOGS_PLACER_TYPE = registerTrunk("straight_trunk_logs_placer", StraightTrunkWithLogs.CODEC);
    public static final TrunkPlacerType<FallenTrunkWithLogs> FALLEN_TRUNK_WITH_LOGS_PLACER_TYPE = registerTrunk("fallen_trunk_logs_placer", FallenTrunkWithLogs.CODEC);
    public static final TrunkPlacerType<BaobabTrunkPlacer> BAOBAB_TRUNK_PLACER = registerTrunk("baobab_trunk_placer", BaobabTrunkPlacer.CODEC);
    public static final Feature<ShelfFungusFeatureConfig> SHELF_FUNGUS_FEATURE = new ShelfFungusFeature(ShelfFungusFeatureConfig.CODEC);
    public static final CattailFeature CATTAIL_FEATURE = new CattailFeature(ProbabilityConfig.CODEC);
    public static final AlgaeFeature ALGAE_FEATURE = new AlgaeFeature(ProbabilityConfig.CODEC);
    public static final NoisePathFeature NOISE_PATH_FEATURE = new NoisePathFeature(PathFeatureConfig.CODEC);
    public static final NoisePlantFeature NOISE_PLANT_FEATURE = new NoisePlantFeature(PathFeatureConfig.CODEC);
    public static final NoisePathUnderWaterFeature NOISE_PATH_UNDER_WATER_FEATURE = new NoisePathUnderWaterFeature(PathFeatureConfig.CODEC);
    public static final ColumnWithDiskFeature COLUMN_WITH_DISK_FEATURE = new ColumnWithDiskFeature(ColumnWithDiskFeatureConfig.CODEC);

    public static final TagKey<Instrument> WILD_HORNS = TagKey.of(Registry.INSTRUMENT_KEY, id("wild_horns"));

    //ClassTinkerers
    public static final SpawnGroup FIREFLIES = ClassTinkerers.getEnum(SpawnGroup.class, "FIREFLIES");

    private static final int DATA_VERSION = 3105;

    @Override
    public void onInitialize() {
        startMeasuring(this);
        applyDataFixes();

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
        Registry.register(Registry.FEATURE, id("algae_feature"), ALGAE_FEATURE);
        Registry.register(Registry.FEATURE, id("noise_path_feature"), NOISE_PATH_FEATURE);
        Registry.register(Registry.FEATURE, id("noise_plant_feature"), NOISE_PLANT_FEATURE);
        Registry.register(Registry.FEATURE, id("noise_path_under_water_feature"), NOISE_PATH_UNDER_WATER_FEATURE);
        Registry.register(Registry.FEATURE, id("column_with_disk_feature"), COLUMN_WITH_DISK_FEATURE);

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) { /* DEV-ONLY */
            UNSTABLE_LOGGING = true;
            RegisterDevelopment.init();
        }

        TermiteMoundBlockEntity.Termite.addDegradableBlocks();
        TermiteMoundBlockEntity.Termite.addNaturalDegradableBlocks();

        try {
            terralith();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (hasSimpleCopperPipes()) {
            RegisterSaveableMoveablePipeNbt.init();
        }
        stopMeasuring(this);
    }

    public static void terralith() throws IOException {
        Path destPath = Paths.get(FabricLoader.getInstance().getGameDir().toString(), "mods", "z_wilderwild_terralith_compat.jar");
        Optional<ModContainer> wilderwildOptional = FabricLoader.getInstance().getModContainer("wilderwild");
        Optional<ModContainer> terralithOptional = FabricLoader.getInstance().getModContainer("terralith");
        if (wilderwildOptional.isPresent() && terralithOptional.isPresent()) {

            Firefly.FireflyBiomeColorRegistry.addBiomeColor(new Identifier("terralith", "cave/frostfire_caves"), "blue");
            Firefly.FireflyBiomeColorRegistry.addBiomeColor(new Identifier("terralith", "cave/frostfire_caves"), "light_blue");

            Firefly.FireflyBiomeColorRegistry.addBiomeColor(new Identifier("terralith", "cave/thermal_caves"), "red");
            Firefly.FireflyBiomeColorRegistry.addBiomeColor(new Identifier("terralith", "cave/thermal_caves"), "orange");

            ModContainer wilderwild = wilderwildOptional.get();
            Optional<Path> terraWorld = wilderwild.findPath("data/z_wilderwild_terralith_compat.jar");
            if (terraWorld.isPresent()) {
                Files.copy(terraWorld.get(), destPath, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    public static boolean hasTerralith() {
        return FabricLoader.getInstance().getModContainer("terralith").isPresent();
    }

    public static boolean hasSimpleCopperPipes() {
        return FabricLoader.getInstance().getModContainer("copper_pipe").isPresent();
    }

    public static boolean isCopperPipe(BlockState state) {
        if (hasSimpleCopperPipes()) {
            Identifier id = Registry.BLOCK.getId(state.getBlock());
            return id.getNamespace().equals("lunade") && id.getPath().contains("pipe");
        }
        return false;
    }

    public static Random random() {
        return Random.create();
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    public static String string(String path) {
        return id(path).toString();
    }

    public static final Identifier SEED_PACKET = id("seed_particle_packet");
    public static final Identifier CONTROLLED_SEED_PACKET = id("controlled_seed_particle_packet");
    public static final Identifier FLOATING_SCULK_BUBBLE_PACKET = id("floating_sculk_bubble_easy_packet");
    public static final Identifier TERMITE_PARTICLE_PACKET = id("termite_particle_packet");
    public static final Identifier HORN_PROJECTILE_PACKET_ID = id("ancient_horn_projectile_packet");
    public static final Identifier SENSOR_HICCUP_PACKET = id("sensor_hiccup_packet");

    public static final Identifier CAPTURE_FIREFLY_NOTIFY_PACKET = id("capture_firefly_notify_packet");
    public static final Identifier ANCIENT_HORN_KILL_NOTIFY_PACKET = id("ancient_horn_kill_notify_packet");
    public static final Identifier FLYBY_SOUND_PACKET = id("flyby_sound_packet");
    public static final Identifier MOVING_LOOPING_SOUND_PACKET = id("moving_looping_sound_packet");

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
            LOGGER.info(entity.toString() + " : " + string + " : " + entity.getPos());
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
        return Registry.register(Registry.TRUNK_PLACER_TYPE, id(id), new TrunkPlacerType<>(codec));
    }

    public static Map<Object, Long> instantMap = new HashMap<>();

    public static void startMeasuring(Object object) {
        long started = System.nanoTime();
        String name = object.getClass().getName();
        LOGGER.error("Started measuring {}", name.substring(name.lastIndexOf(".") + 1));
        instantMap.put(object, started);
    }

    public static void stopMeasuring(Object object) {
        if (instantMap.containsKey(object)) {
            String name = object.getClass().getName();
            LOGGER.error("{} took {} nanoseconds", name.substring(name.lastIndexOf(".") + 1), System.nanoTime() - instantMap.get(object));
            instantMap.remove(object);
        }
    }

    private static void applyDataFixes() {
        DataFixerBuilder builder = new DataFixerBuilder(SharedConstants.getGameVersion().getWorldVersion());
        Schema schema = Schemas.getFixer().getSchema(3097);
        wilderBlockItemRenamer(builder, schema, "white_dandelion", "seeding_dandelion");
        wilderBlockItemRenamer(builder, schema, "blooming_dandelion", "seeding_dandelion");
        wilderBlockRenamer(builder, schema, "potted_white_dandelion", "potted_seeding_dandelion");
        wilderBlockRenamer(builder, schema, "potted_blooming_dandelion", "potted_seeding_dandelion");
        wilderBlockItemRenamer(builder, schema, "floating_moss", "algae");
    }

    private static void wilderBlockItemRenamer(DataFixerBuilder builder, Schema schema, String startString, String endString) {
        wilderBlockRenamer(builder, schema, startString, endString);
        wilderItemRenamer(builder, schema, startString, endString);
    }

    private static void wilderBlockRenamer(DataFixerBuilder builder, Schema schema, String startString, String endString) {
        builder.addFixer(BlockNameFix.create(schema, startString + " block renamer", Schemas.replacing(WilderWild.string(startString), WilderWild.string(endString))));
    }

    private static void wilderItemRenamer(DataFixerBuilder builder, Schema schema, String startString, String endString) {
        builder.addFixer(ItemNameFix.create(schema, startString + " item renamer", Schemas.replacing(WilderWild.string(startString), WilderWild.string(endString))));
    }
}
