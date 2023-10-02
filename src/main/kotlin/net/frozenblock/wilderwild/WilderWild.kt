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
package net.frozenblock.wilderwild

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.loader.api.ModContainer
import net.frozenblock.lib.entrypoint.api.FrozenModInitializer
import net.frozenblock.lib.mobcategory.api.entrypoint.FrozenMobCategoryEntrypoint
import net.frozenblock.lib.mobcategory.impl.FrozenMobCategory
import net.frozenblock.wilderwild.block.entity.PalmCrownBlockEntity
import net.frozenblock.wilderwild.config.EntityConfig
import net.frozenblock.wilderwild.entity.Jellyfish
import net.frozenblock.wilderwild.entity.ai.TermiteManager
import net.frozenblock.wilderwild.misc.command.SpreadSculkCommand
import net.frozenblock.wilderwild.misc.datafixer.DrySandStateFix
import net.frozenblock.wilderwild.misc.datafixer.NematocystStateFix
import net.frozenblock.wilderwild.misc.datafixer.OsseousSculkStateFix
import net.frozenblock.wilderwild.misc.datafixer.ScorchedSandStateFix2
import net.frozenblock.wilderwild.misc.mod_compat.WilderModIntegrations
import net.frozenblock.wilderwild.registry.*
import net.frozenblock.wilderwild.world.additions.gen.WilderWorldGen
import net.frozenblock.wilderwild.world.generation.conditionsource.BetaBeachConditionSource
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.util.datafix.schemas.NamespacedSchema
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixerBuilder
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixes
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.SimpleFixes
import kotlin.system.measureNanoTime

class WilderWild : FrozenModInitializer(MOD_ID), FrozenMobCategoryEntrypoint {
    companion object {
        // PACKETS
        @JvmField
        val SEED_PACKET = id("seed_particle_packet")
        @JvmField
        val CONTROLLED_SEED_PACKET = id("controlled_seed_particle_packet")
        @JvmField
        val FLOATING_SCULK_BUBBLE_PACKET = id("floating_sculk_bubble_easy_packet")
        @JvmField
        val TERMITE_PARTICLE_PACKET = id("termite_particle_packet")
        @JvmField
        val SENSOR_HICCUP_PACKET = id("sensor_hiccup_packet")
        @JvmField
        val JELLY_STING_PACKET = id("jelly_sting_packet")
        private fun applyDataFixes(mod: ModContainer) {
            log("Applying DataFixes for Wilder Wild with Data Version $DATA_VERSION", true)
            val builder = QuiltDataFixerBuilder(WilderSharedConstants.DATA_VERSION)
            builder.addSchema(0, QuiltDataFixes.BASE_SCHEMA)
            val schemaV1 = builder.addSchema(1, ::NamespacedSchema)
            SimpleFixes.addBlockRenameFix(builder, "Rename white_dandelion to blooming_dandelion", id("white_dandelion"), id("blooming_dandelion"), schemaV1)
            SimpleFixes.addBlockRenameFix(builder, "Rename potted_white_dandelion to potted_blooming_dandelion", id("potted_white_dandelion"), id("potted_blooming_dandelion"), schemaV1)
            val schemaV2 = builder.addSchema(2, ::NamespacedSchema)
            SimpleFixes.addBlockRenameFix(builder, "Rename blooming_dandelion to seeding_dandelion", id("blooming_dandelion"), id("seeding_dandelion"), schemaV2)
            SimpleFixes.addBlockRenameFix(builder, "Rename potted_blooming_dandelion to potted_seeding_dandelion", id("potted_blooming_dandelion"), id("potted_seeding_dandelion"), schemaV2)
            val schemaV3 = builder.addSchema(3, ::NamespacedSchema)
            SimpleFixes.addBlockRenameFix(builder, "Rename floating_moss to algae", id("floating_moss"), id("algae"), schemaV3)
            SimpleFixes.addItemRenameFix(builder, "Rename floating_moss to algae", id("floating_moss"), id("algae"), schemaV3)
            val schemaV4 = builder.addSchema(4, ::NamespacedSchema)
            SimpleFixes.addBlockRenameFix(builder, "Rename test_1 to null_block", id("test_1"), id("null_block"), schemaV4)
            val schemaV5 = builder.addSchema(5, ::NamespacedSchema)
            SimpleFixes.addBlockRenameFix(builder, "Rename sculk_echoer to null_block", id("sculk_echoer"), id("null_block"), schemaV5)
            SimpleFixes.addBlockRenameFix(builder, "Rename sculk_jaw to null_block", id("sculk_jaw"), id("null_block"), schemaV5)
            val schemaV6 = builder.addSchema(6, ::NamespacedSchema)
            SimpleFixes.addBlockRenameFix(builder, "Rename baobab_sapling to baobab_nut", id("baobab_sapling"), id("baobab_nut"), schemaV6)
            SimpleFixes.addBlockRenameFix(builder, "Rename baobab_nut_sapling to baobab_nut", id("baobab_nut_sapling"), id("baobab_nut"), schemaV6)
            SimpleFixes.addBlockRenameFix(builder, "Rename potted_baobab_sapling to potted_baobab_nut", id("potted_baobab_sapling"), id("potted_baobab_nut"), schemaV6)
            val schemaV7 = builder.addSchema(7, ::NamespacedSchema)
            SimpleFixes.addBlockRenameFix(builder, "Rename firefly_lantern to display_lantern", id("firefly_lantern"), id("display_lantern"), schemaV7)
            SimpleFixes.addBlockRenameFix(builder, "Rename mesoglea to blue_pearlescent_mesoglea", id("mesoglea"), id("blue_pearlescent_mesoglea"), schemaV7)
            SimpleFixes.addItemRenameFix(builder, "Rename mesoglea to blue_pearlescent_mesoglea", id("mesoglea"), id("blue_pearlescent_mesoglea"), schemaV7)
            val schemaV8 = builder.addSchema(8, ::NamespacedSchema)
            SimpleFixes.addBlockStateRenameFix(builder, "display_lantern_rename_fix", id("display_lantern"), "light", "0", "display_light", schemaV8)
            val schemaV9 = builder.addSchema(9, ::NamespacedSchema)
            builder.addFixer(NematocystStateFix(schemaV9, "blue_nematocyst_fix", id("blue_nematocyst")))
            builder.addFixer(NematocystStateFix(schemaV9, "blue_pearlescent_nematocyst_fix", id("blue_pearlescent_nematocyst")))
            builder.addFixer(NematocystStateFix(schemaV9, "lime_nematocyst_fix", id("lime_nematocyst")))
            builder.addFixer(NematocystStateFix(schemaV9, "pink_nematocyst_fix", id("pink_nematocyst")))
            builder.addFixer(NematocystStateFix(schemaV9, "purple_pearlescent_nematocyst_fix", id("purple_pearlescent_nematocyst")))
            builder.addFixer(NematocystStateFix(schemaV9, "red_nematocyst_fix", id("red_nematocyst")))
            builder.addFixer(NematocystStateFix(schemaV9, "yellow_nematocyst_fix", id("yellow_nematocyst")))
            val schemaV10 = builder.addSchema(10, ::NamespacedSchema)
            SimpleFixes.addBlockRenameFix(builder, "Rename palm_sapling to coconut", id("palm_sapling"), id("coconut"), schemaV10)
            val schemaV11 = builder.addSchema(11, ::NamespacedSchema)
            builder.addFixer(DrySandStateFix(schemaV11, "dry_sand_crackness_to_crackedness", id("dry_sand")))
            SimpleFixes.addBlockRenameFix(builder, "Rename dry_sand to scorched_sand", id("dry_sand"), id("scorched_sand"), schemaV11)
            SimpleFixes.addItemRenameFix(builder, "Rename dry_sand to scorched_sand", id("dry_sand"), id("scorched_sand"), schemaV11)
            builder.addFixer(DrySandStateFix(schemaV11, "scorched_sand_crackness_to_crackedness", id("scorched_sand")))
            val schemaV13 = builder.addSchema(13, ::NamespacedSchema)
            SimpleFixes.addBlockRenameFix(builder, "Rename palm_leaves to palm_fronds", id("palm_leaves"), id("palm_fronds"), schemaV13)
            SimpleFixes.addItemRenameFix(builder, "Rename palm_leaves to palm_fronds", id("palm_leaves"), id("palm_fronds"), schemaV13)
            val schemaV14 = builder.addSchema(14, ::NamespacedSchema)
            builder.addFixer(ScorchedSandStateFix2(schemaV14, "scorched_sand_integer_to_boolean", id("scorched_sand")))
            builder.addFixer(ScorchedSandStateFix2(schemaV14, "scorched_red_sand_integer_to_boolean", id("scorched_red_sand")))
            val schemaV15 = builder.addSchema(15, ::NamespacedSchema)
            builder.addFixer(OsseousSculkStateFix(schemaV15, "osseous_sculk_axis_to_direction", id("osseous_sculk")))
            QuiltDataFixes.buildAndRegisterFixer(mod, builder)
            log("DataFixes for Wilder Wild have been applied", true)
        }
    }

    //Alan Wilder Wild
    override fun onInitialize(modId: String, container: ModContainer) {
        val time = measureNanoTime {
            applyDataFixes(container)
            WilderRegistry.initRegistry()
            RegisterBlocks.registerBlocks()
            RegisterItems.registerItems()
            RegisterItems.registerBlockItems()
            RegisterGameEvents.registerEvents()
            RegisterBlocks.registerDispenses()
            RegisterSounds.init()
            RegisterBlockSoundTypes.init()
            RegisterBlockEntities.register()
            RegisterEntities.init()
            RegisterLootTables.init()
            RegisterParticles.registerParticles()
            RegisterResources.register(container)
            RegisterProperties.init()
            RegisterCriteria.init()
            RegisterFeatures.init()
            RegisterWorldgen.init()
            WilderWorldGen.generateWildWorldGen()
            Registry.register(
                BuiltInRegistries.MATERIAL_CONDITION,
                id("beta_beach_condition_source"),
                BetaBeachConditionSource.CODEC.codec()
            )
            TermiteManager.Termite.addDegradableBlocks()
            TermiteManager.Termite.addNaturalDegradableBlocks()
            RegisterBlocks.registerBlockProperties()
            ServerLifecycleEvents.SERVER_STOPPED.register { _ ->
                PalmCrownBlockEntity.PalmCrownPositions.clearAll()
                Jellyfish.clearLevelToNonPearlescentCount()
            }
            ServerTickEvents.START_SERVER_TICK.register { _ ->
                PalmCrownBlockEntity.PalmCrownPositions.clearAndSwitch()
                Jellyfish.clearLevelToNonPearlescentCount()
            }
            CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
                SpreadSculkCommand.register(dispatcher)
            }

            WilderModIntegrations.init()
        }
        log("WilderWild took $time nanoseconds")
    }

    override fun newCategories(context: ArrayList<FrozenMobCategory>) {
        context.addAll(
            listOf(
                FrozenMobCategoryEntrypoint.createCategory(
                    id("fireflies"),
                    EntityConfig.get().firefly.fireflySpawnCap,
                    true,
                    false,
                    80
                ),

                FrozenMobCategoryEntrypoint.createCategory(
                    id("jellyfish"),
                    EntityConfig.get().jellyfish.jellyfishSpawnCap,
                    true,
                    false,
                    64
                ),

                FrozenMobCategoryEntrypoint.createCategory(
                    id("tumbleweed"),
                    EntityConfig.get().tumbleweed.tumbleweedSpawnCap,
                    true,
                    false,
                    64
                )
            )
        )
    }
}
