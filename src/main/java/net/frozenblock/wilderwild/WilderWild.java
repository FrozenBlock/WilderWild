package net.frozenblock.wilderwild;

import com.chocohead.mm.api.ClassTinkerers;
import com.mojang.serialization.Codec;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.misc.BlockSoundGroupOverwrites;
import net.frozenblock.wilderwild.registry.*;
import net.frozenblock.wilderwild.world.feature.WildConfiguredFeatures;
import net.frozenblock.wilderwild.world.feature.WildMiscConfigured;
import net.frozenblock.wilderwild.world.feature.WildTreeConfigured;
import net.frozenblock.wilderwild.world.feature.WildTreePlaced;
import net.frozenblock.wilderwild.world.feature.features.CattailFeature;
import net.frozenblock.wilderwild.world.feature.features.NoisePathFeature;
import net.frozenblock.wilderwild.world.feature.features.ShelfFungusFeature;
import net.frozenblock.wilderwild.world.feature.features.config.PathFeatureConfig;
import net.frozenblock.wilderwild.world.feature.features.config.ShelfFungusFeatureConfig;
import net.frozenblock.wilderwild.world.gen.WildMusic;
import net.frozenblock.wilderwild.world.gen.WildWorldGen;
import net.frozenblock.wilderwild.world.gen.trunk.BaobabTrunkPlacer;
import net.frozenblock.wilderwild.world.gen.trunk.FallenTrunkWithLogs;
import net.frozenblock.wilderwild.world.gen.trunk.StraightTrunkWithLogs;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WilderWild implements ModInitializer {
    public static final String MOD_ID = "wilderwild";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static boolean DEV_LOGGING = true;
    public static boolean UNSTABLE_LOGGING = true; //Used for features that may possibly be unstable and crash in public builds - it's smart to use this for at least registries.

    public static final TrunkPlacerType<StraightTrunkWithLogs> STRAIGHT_TRUNK_WITH_LOGS_PLACER_TYPE = registerTrunk("straight_trunk_logs_placer", StraightTrunkWithLogs.CODEC);
    public static final TrunkPlacerType<FallenTrunkWithLogs> FALLEN_TRUNK_WITH_LOGS_PLACER_TYPE = registerTrunk("fallen_trunk_logs_placer", FallenTrunkWithLogs.CODEC);
    public static final TrunkPlacerType<BaobabTrunkPlacer> BAOBAB_TRUNK_PLACER = registerTrunk("baobab_trunk_placer", BaobabTrunkPlacer.CODEC);
    public static final Feature<ShelfFungusFeatureConfig> SHELF_FUNGUS_FEATURE = new ShelfFungusFeature(ShelfFungusFeatureConfig.CODEC);
    public static final CattailFeature CATTAIL_FEATURE = new CattailFeature(ProbabilityConfig.CODEC);
    public static final NoisePathFeature NOISE_PATH_FEATURE = new NoisePathFeature(PathFeatureConfig.CODEC);

    public static final SpawnGroup FIREFLIES = ClassTinkerers.getEnum(SpawnGroup.class, "FIREFLIES");

    @Override
    public void onInitialize() {
        RegisterBlocks.registerBlocks();
        RegisterBlocks.addBaobab();
        RegisterItems.registerItems();
        WildConfiguredFeatures.registerConfiguredFeatures();
        WildTreeConfigured.registerTreeConfigured();
        WildTreePlaced.registerTreePlaced();
        WildMiscConfigured.registerMiscPlaced();
        WildWorldGen.generateWildWorldGen();
        WildMusic.playMusic();
        RegisterFlammability.register();
        RegisterGameEvents.RegisterEvents();

        RegisterSounds.init();
        RegisterBlockSoundGroups.init();
        RegisterBlockEntityType.register();
        RegisterEntities.init();
        RegisterEnchantments.init();
        BlockSoundGroupOverwrites.init();
        //RegisterLootTables.init();

        Registry.register(Registry.FEATURE, new Identifier(WilderWild.MOD_ID, "shelf_fungus_feature"), SHELF_FUNGUS_FEATURE);
        Registry.register(Registry.FEATURE, new Identifier(WilderWild.MOD_ID, "cattail_feature"), CATTAIL_FEATURE);
        Registry.register(Registry.FEATURE, new Identifier(WilderWild.MOD_ID, "noise_path_feature"), NOISE_PATH_FEATURE);

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) { /* DEV-ONLY */
            RegisterDevelopment.init();
        }

        TermiteMoundBlockEntity.Termite.addDegradableBlocks();
    }

    public static final Identifier SEED_PACKET = new Identifier(WilderWild.MOD_ID,"seed_particle_packet");
    public static final Identifier CONTROLLED_SEED_PACKET = new Identifier(WilderWild.MOD_ID,"controlled_seed_particle_packet");
    public static final Identifier FLOATING_SCULK_BUBBLE_PACKET = new Identifier(WilderWild.MOD_ID,"floating_sculk_bubble_easy_packet");
    public static final Identifier TERMITE_PARTICLE_PACKET = new Identifier(WilderWild.MOD_ID,"termite_particle_packet");
    public static final Identifier HORN_PROJECTILE_PACKET_ID = new Identifier(WilderWild.MOD_ID, "ancient_horn_projectile_packet");

    public static final Identifier CAPTURE_FIREFLY_NOTIFY_PACKET = new Identifier(WilderWild.MOD_ID,"capture_firefly_notify_packet");
    public static final Identifier FLYBY_SOUND_PACKET = new Identifier(WilderWild.MOD_ID,"flyby_sound_packet");
    public static final Identifier MOVING_LOOPING_SOUND_PACKET = new Identifier(WilderWild.MOD_ID,"moving_looping_sound_packet");

    public static void log(String string, boolean shouldLog) {
        if (shouldLog) {
            LOGGER.info(string);
        }
    }
    public static void logInsane(String string, boolean shouldLog) {
        if (shouldLog) {
            for (int i=0; i<Math.random()*5; i++) {
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
        return Registry.register(Registry.TRUNK_PLACER_TYPE, new Identifier(MOD_ID, id), new TrunkPlacerType<>(codec));
    }
}
