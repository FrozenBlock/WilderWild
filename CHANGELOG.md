Please clear changelog after each release.
Thank you!
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED
hi
-----------------
- Bumped Wilder Wild's protocol version to 12.

# Tundras
- Added the Tundra biome.
- Generates near the colder regions of the world where Plains would be, before the snow takes over.

# Auburn Moss
- A new moss family with a distinct red hue.
- Added Aurburn Moss.
    - Can be Bone-Mealed to spread itself to nearby natural blocks under the `wilderwild:red_moss_replaceable` block tag.
    - Spreads tallgrass grass, and related foliage.
- Added Auburn Moss Carpet.
    - Can be waterlogged.
- Added Auburn Moss Lichen.
    - A glow-lichen like block that can be placed up to 6 times in a blockspace, once on each respective face.
    - Can be waterlogged.
- Primarily generates as patches in Tundras, Maple Forests, and Oceans.

# Pale Gardens Additions (1.21.4+)
- Added the Pale Mushroom and Pale Shelf Fungi blocks.
  - At night, these will emit gray spore-like particles.
- Added the Pale Mushroom Block.
  - Emits fog-like particles at night while above a Mushroom Stem block.
- Huge Pale Mushrooms generate naturally in the Pale Garden Biome.
- Added more variety to Pale Oak trees.
- Properly updated the sound type for Eyeblossoms to match other flowers.
- Termites can now eat Pale Oak blocks.
- Resin Clumps, Creaking Hearts, Eyeblossoms, and Pale Mushrooms now kill Termites on contact.

# 1.21.5+ Changes
- Fireflies now spawn near Firefly Bushes in dim lighting.
- Renamed Wilder Wild's `Bush` to `Shrub.`
- Added the `wilderwild:non_overriden_falling_leaves` block tag, controlling which leaves blocks won't have their particles entirely replaced by Wilder WIld.
- Increased the frequency of Wilder Wild's falling leaves.
- Updated all Maple Leaf Litters to be consistent with Vanilla's new Leaf Litter block.
- Dry Grass now generates in Beaches.

# Swamp Overhaul
- Added the Willow tree.
  - Willows generate very frequently in Swamps, finally bringing the biome completely up to speed with what was shown in concept art.
  - Saplings can be placed and grown underwater.
  - Witch Huts now generate with the Willow block set.
- Removed the `wilderwild:oak_saplings_grow_swamp_variant` biome tag as Willows replaced Wilder Wild's updated Swamp Oaks.
- Added Tall Grass and Ferns to the Swamp biome.
  - Added the `wilderwild:feature/has_swamp_fern` and `wilderwild:feature/has_swamp_tall_grass` biome tags to control the placement of these features.
    - Both of these features contain only the `minecraft:swamp` biome by default.
- Cypress Wetlands now generate Lily Pads and Seagrass.
- Cypress Wetlands now take up less space from Swamps.
- Replaced the `wilderwild:feature/has_fallen_swamp_oak_trees` biome tag with the `wilderwild:feature/has_fallen_swamp_trees` biome tag.

# Ocean Overhaul
- Minecraft's seas have been given various new decorations.
- Added Barnacles.
  - Barnacles can be placed on multiple block faces, similar to Sculk Veins and Glow Lichens.
  - Generates naturally most frequently in Stony Shores, Mangrove Swamp, Warm Ocean, and Lukewarm Ocean biomes.
  - The biome tags `wilderwild:has_barnacles_common,` `wilderwild:has_barnacles,` `wilderwild:has_barnacles_sparse,` and `wilderwild:has_barnacles_rare` control the generation of Barnacles.
    - The block tag `wilderwild:barnacles_feature_placeable` contains this the of blocks Barnacles can be placed on.
  - The biome tags `wilderwild:has_barnacles_structure` dictate which biomes a special feature for placing Barnacles on structure-related blocks will generate in.
    - The block tag `wilderwild:barnacles_feature_placeable_structure` contains this list of blocks.
  - Drops without Silk Touch or Shears.
  - Using bone meal underwater in biomes Barnacles can generate in has a chance to place Barnacles.
- Moss now generates on the floor of Deep Lukewarm Oceans.
- Seagrass now covers more of Deep Lukewarm Oceans, bringing them closer to real-world seagrass meadows.
- Added Sea Anemone.
  - Glows softly during daytime, giving the bottom of the oceans some light.
  - Will stop glowing after becoming nighttime.
  - Generates most frequently in Deep Lukewarm Oceans, but can sparsely be found in other Ocean Biomes.
- Added Tube Worms.
  - Generates rarely in most deep ocean biomes.
  - Can be placed on top itself, similar to Kelp and Sugar Cane.
- Added Sea Whips.
  - Generates in all non-frozen oceans, most commonly in temperate and lukewarm oceans.
- Added the Plankton block.
  - Is placed on top of water.
  - Spawns Plankton particles underwater.
  - During the night, starts glowing.
- Added hydrothermal vents to oceans.
  - Generates rarely in non-cold deep ocean biomes.
  - Generates with Tube Worms surrounding itself.
  - Geysers facing into Water with Magma behind themselves with now act as a hydrothermal vent.
  - Geysers acting as hydrothermal vents will now spawn falling ash particles in surrounding water.
  - Aquatic vegetation can no longer be placed on Geysers.

# Mosoglea Caves
- Mesoglea features no longer carve "fake caves," instead only generating in areas with Water or Air nearby.
- Jellyfish can now only track their attackers within a 4 block range, and forget them after 60 ticks.
  - This change was made to make Jellyfish more peaceful and realistic.
- Mesoglea is now always "waterlogged," maaking it much easier for players to utilize it properly.
  - Mesoglea will no longer leave a water block behind when broken.
  - Fluid-like rendering for Mesoglea has been optimized.
  - Water no longer renders inside Mesoglea Blocks.
  - Mesoglea now overrides the water fog color with its own.
  - Geysers can now erupt through Mesoglea.
  - Mesoglea now has custom Bubble, Bubble Pop, and Splash particles.
    - Mesoglea Bubbles float and can persist in the air for a short period of time.

# Magmatic Caves
- Added the Gabbro stone type.
  - Generates naturally in Magmatic Caves in place of the Basalt.
  - Can be polished.
  - Can be crafted into bricks.
  - Mossy brick variants and stair, slab, and wall variants of the base stone require Trailier Tales to be installed.
  - Scorched can spawn on Gabbro.
- Are now larger on average.

# Frozen Caves
- Added Icicles.
  - Grows naturally from the bottom of Fragile Ice.
  - Grows in length if below a Fragile Ice block, or an ice block with water above.
  - Occasionally spreads to other ice blocks if able to grow in length.
  - Will occasionally fall if placed under an ice block.
    - Game Events in the `wilderwild:makes_icicle_fall` game event tag will cause nearby Icicles to fall.
- Added Fragile Ice.
  - Will crack twice when stood on before shattering.
  - Shatters when an entity falls onto the block.
  - When shattered, all adjacent Fragile Ice blocks will shatter.
  - Ice can be smelted into Fragile Ice.
  - Mobs in the `wilderwild:fragile_ice_unwalkable_mobs` entity tag will crack, then shatter Fragile Ice when standing on it.
  - Mobs not in the `wilderwild:fragile_ice_doesnt_crack_on_fall` entity tag will cause Fragile Ice to shatter when falling onto it.
- Removed Snow patches in favour of utilizing Fragile Ice and Icicles.
- The `Snow Under Mountains` config option has been set to off by default.

# Snowy Foliage
- Generic foliage blocks now have cold variants.
- Added Frozen Short Grass.
- Added Frozen Tall Grass.
- Added Frozen Fern.
- Added Frozen Large.
- Shoutout to just_jose2006 for the initial textures and inspiration!

# Penguins
- Added the Penguin.
  - Penguins spawn in Snowy Beaches and Frozen Oceans.
  - Will dive underwater to hunt for Squid.
  - Can be bred with an Ink Sac or Glow Ink Sac, laying an egg afterward.
  - Chases Boats controlled by players, granting them a temporary speed boost.
  - Will occasionally "call" out for other Penguins, grouping together for a short time.
  - The Penguin will spawn in any biomes within the `wilderwild:entity/spawn/penguin` biome tag.
  - The Penguin can spawn on top of any blocks within the `wilderwild:penguins_spawnable_on` block tag.
  - Penguins will ignore unique friction from any blocks within the `wilderwild:penguin_ignore_friction` block tag.
- Added the Penguin Egg block.

# Butterflies
- Added Butterflies
  - A new ambient entity that sporadically flutters around, ocassionally stopping to rest.
  - Can be bottled, storing all entity data.
- Butteflies Have 7 Variants:
  - Clouded Yellow: Spawns in Meadows, Flower Forests, Sunflower Plains, Flower Fields, Maple Forests, and Tundras.
  - Duskwing: Spawns in Tundras.
  - Grean Hairstreak: Spawns in Flower Forests and Flower Fields.
  - Marbled: Spawns in Meadows, Flower Forests and Flower Fields.
  - Monarch: Spawns in Flower Forests, Sunflower Plains, Flower Fields, Maple Forests, and Tundras.
  - Morpho Blue: Spawns in Meadows, Flower Forests and Flower Fields.
  - Red Lacewing,: Spawns in Flower Forests, Flower Fields, Maple Forests, and Tundras.
- Added Butterfly Bottles
  - Used to capture and release Butterflies.

# Mooblooms
- Added Mooblooms
  - When bred, spawn a baby with a flower of a mixed color if possible.
  - Cannot be converted into regular Cows, unlike Mooshrooms.
  - Each flower can be sheared off the Moobloom.
    - These can each be regrown by feeding it Wheat.
    - Cannot breed unless all flowers are present.
  - Spawn in the Flower Forest, Flower Fields, and Sunflower Plains biomes by default.
  - Butterflies will follow Mooblooms.

# Fireflies
- Fireflies now move faster.
- Fireflies now spawn and move in swarms.
  - When the leader of a swarm is killed or captured in a bottle, the nearest Firefly will become the new swarm leader.
  - Firefly Swarms can be toggled in the config.
- Removed unnecessary spawning mechanics that were unique only to the Firefly.
- Moved multiple aspects of the Firefly to be managed by its brain.
- Fireflies now only spawn at night.
- Fireflies now only spawn in Swamp-like biomes by default.
- Expanded the list of blocks Fireflies can "hide" in during the day.
- Renamed the `wilderwildfireflies` mob category to `wilderwildfirefly`.
- Removed the `wilderwild:spawn/firefly_spawnable_cave` and `wilderwild:spawn/firefly_spawnable_during_day` tags as these spawning behaviors have been removed.

# Music
- Added three new tracks by Willow/pictochats_.
  - Dahlia
  - Amber
  - Molt
- Added two tracks by C418.
  - Excuse
  - Flake
- Completely reworked Wilder Wild's music distribution, thanks to Rebel459! ([#450](https://github.com/FrozenBlock/WilderWild/pull/450))
  - Added multiple new config options to control where new music can play.
  - Added more tags to control which biomes contain what music.
  - Music that plays in dying forest biomes will now play slightly out of tune.

# Config System
- Added a new config option to disable Pollen generation.
- Added a config option to toggle rotatable Reinforced Deepslate.
- Added a config option to toggle Hanging Tendril generation.
- Added a config option to toggle Osseous Sculk generation.
- Added a config option to toggle Sculk Slab, Sculk Stairs, and Sculk Wall generation.
- Grouped all Sculk-related config options into a new dropdown in the Block config menu.
- Added a config option to toggle Dandelion bone mealing into Seeding Dandelions.
- Added a config option to toggle Seeding Dandelion shearing into Dandelions.
- Added a config option to toggle Lily Pad bone mealing into Flowering Lily Pads.
- Added a config option to toggle Flowering Lily Pad shearing into Lily Pads.
- Added a new config option to toggle between the updated and original Sculk Shrieker selection outline.
- Added a config option to toggle pools of water generating in River biomes.
  - This is now disabled by default, unlike before.
- Revamped config options related to tree generation.
  - Grouped all existing tree-related options into one category.
  - Added options to individually disable Birch, Oak, Dark Oak, and Pale Oak branch generation.
  - Added an option to prevent Fallen Trees from generating with Hollowed Logs.
  - Added an option to disable Baobab generation.
    - This requires the `Tree Generation` option to be enabled.
  - Added an option to disable Palm generation.
  - Added an option to disable Willow generation.
  - Added an option to disable Shelf Fungi generation.
- Moved the `Ancient City Stone Chests` config option from the `Worldgen` tab to the `Block` tab.
- The surface transition generation config option has now been split into multiple options per-block type.
- The surface decoration generation config option has now been split into multiple options per-feature type.

# Changes
- Removed Mud-specific Cattail features from biomes that do not generate Mud.
  - The `wilderwild:feature/has_cattail_mud` biome tag has been added as a result of this change.
- Removed Shelf Fungi-only features, now relying solely on trunk decorators to place Shelf Fungi.
  - The `wilderwild:feature/has_brown_shelf_fungi` and `wilderwild:feature/has_red_shelf_fungi` biome tags have been removed as a result of this change.
- All trees that don't place foliage now use `wilderwild:no_op_foliage_placer.`
- Removed random circular, shallow pools from caves.
  - The `wilderwild:feature/no_pools` biome tag has been removed as a result of this change.
- Oak trees generated in Savanna or Badlads biomes no longer have Shelf Fungi.
- Bushes now generate on Grass and Coarse Dirt in Wooded Badlands.
  - Renamed the `wilderwild:feature/has_wooded_badlands_terracotta_bush` biome tag to `wilderwild:feature/has_wooded_badlands_bush` as a result of this change.
- Decreased the amount of trees per-chunk in Wooded Badlands by 1.
- Jungle trees can now generate with Shelf Fungi.
- Mangrove trees can now generate with Shelf Fungi.
- Revamped flower distribution and generation.
- All Shelf Fungi can now be mined faster with an Axe.
- Crab variants can now be added via datapacks, though by default there is only one Crab variant.
- Removed two unnecessary blockstate properties from Osseous Sculk relating to their generation height.
- Reworked Osseous Sculk generation once again.
  - Osseous Sculk generation is now taller on average, much like it once was in earlier versions of Wilder Wild.
- The sound of an Ostrich laying an egg no longer uses Frog Spawn sounds.
- Added Pollen to the `minecraft:inside_step_sound_blocks` and `minecraft:combination_step_sound_blocks` block tags.
- Removed the Bush from the `minecraft:inside_step_sound_blocks` block tag.
- Slightly decreased the pitch of Magma block sounds.
- Removed the `wilderwild:chest_bubbler` entity as it was unnecessary.
  - This has been replaced with the `wilderwild:chest_bubbles` particle, which will now handle the same behavior but on the client.
- Removed the `wilderwild:hanging_tendril_extract_xp` game event in favor of `minecraft:block_change.`
- Slightly decreased the amount of large Oak trees in Forests.
- Revamped the generation of flowers in the Flower Forest and Flower Fields biomes, being much more visually pleasing.
- Small Bushes now generate with all exposed sides covered by leaves.
- Coconuts no longer immediately fall once logs connected to Palm Fronds are broken.
- Slightly reworking the erupting of naturally generated Geysers.
  - Naturally generated Geysers now only erupt when a player is within 48 blocks of them.
  - The closer a player is, the higher the chance for it to erupt.
- Hanging Tendrils now play animations in sync with each other while being rendered as billboards.
- Particles created by gargling Sculk Shriekers are now handled by the client, using the new `wilderwild:shrieker_bubbles` particle.
- Particles created by entities touching Seeding Dandelions are now dependent on how the entity was moving.
- Glories of the Snow have been removed.
  - They are now replaced with five Hibiscus Blocks:
    - Red Hibiscus
    - Yellow Hibiscus.
    - White Hibiscus.
    - Pink Hibiscus.
    - Purple Hibiscus.
  - Glory of the Snows will be datafixed into random Hibiscuses.
  - Glory of the Snow Petals will be datafixed into Pollen.
- Scorched now set entities on fire upon attacking.
- Removed the `wilderwild:sculk_spreader` entity, and updated the `spreadsculk` command to place all Sculk at once.
- Updated the Chiseled Mud Bricks texture.
- Wilder Wild's custom flower, leaves, sapling, cactus, coarse dirt, ice, frosted ice, mushroom, mushroom block, sandstone, lily pad, and melon block sounds now all rely on tags.
  - These tags can be found in the `/sound` folder for block tags.
- Pink Petals can now be placed into Flower Pots.
- Backported Wildflowers.
  - Generates in Birch Forest, Meadow, and Sunflower Plains biomes..
  - Can be crafted into Yellow Dye.
- Added Clovers.
  - Generates most commonly in Meadow biomes.
- Removed shallow pools from the Oasis biome as they weren't visually pleasing.
- Ostriches now drop up to 4 feathers on death.
- Maple Forests can no longer generate Beta Beaches.
- Rattling sounds are now used as a footstep sound instead of idle sound for Crabs.
- Added the Phlox flower.
  - Generates in Birch Forest, Old Growth Birch Forest, and Meadow biomes.
  - Generates occasionally in Dark Forest, Swamp, Dying Forest, Maple Forest, and Autumnal Plains biomes.
  - Can be crafted into Purple Dye.
- Added the Lantanas flower.
  - Generates in Old Growth Birch Forest, Savanna, Dark Forest, and Sunflower Plains biomes.
  - Can be crafted into Orange Dye.
- Lowered the average amount of Bushes that generate.
- Increased the average amount of Tumbleweed that generate.
- Sand and Red Sand can no longer be scorched by Lava falling from Pointed Dripstone.
- Removed the `ancient` property from Stone Chests.
  - As a result of this change, Stone Chests found in Ancient Cities will be quicker to open.
- Carnations now yield magenta dye instead of purple dye.
- Changed the Warm River's surface rules to likely reduce lag caused by Sand blocks falling.
- Added the `wilderwild:snow_generation_can_search_through` block tag, listing non-air blocks that will be iterated through while searching for the lowest possible Snow layer generation position in worldgen.
- Significantly updated how many of Wilder Wild's trees place branches.
  - Related tree features now have a `trunk_branch_placement` array in them, containing the fields:
    - `branch_cutoff_from_top`: How far below the top of the tree branches can start generating at.
    - `branch_length`: An integer provider defining the minimum and maximum branch lengths.
    - `branch_placement_chance`: The chance per-block for a branch to generate.
    - `foliage_placement_chance`: The chance for foliage to be placed at the end of a branch.
    - `foliage_radius_shrink`: How many blocks the radius of the placed foliage should be shrunk by.
    - `max_branch_count`: The maximum number of branches allowed to generate per-tree.
    - `offset_last_log_chance`: The chance for the last log on a branch to be offset upwards.
    - `minimum_branch_length_for_offset`: The minimum length of a branch required to be able to offset a log upwards.
  - Renamed `straight_with_logs_trunk_placer` to `straight_with_branches_trunk_placer.`
  - Renamed `fallen_with_logs_trunk_placer` to `fallen_with_branches_trunk_placer.`
- Player-placed Termite Mounds now only support a max of 3 Termite instances, much like naturally-generated Termite Mounds.
- Updated Particle Rain compatibility.
- Updated Sodium compatibility.
- Retextured the Datura, thanks to Zhen!
- Removed an unused sound event for caves
- Removed Abandoned Cabins as they conflicted with Vanilla's modern structure design.
- Converted all of Wilder Wild's mob variants to now be data-driven!
    - As such, `FireflyBiomeColorRegistry` has been removed and is replaced with a `biomes` field in each .json file.
- Fireflies now only have one flicker pattern.
- Merged all 17 Firefly Bottle items into 1 item, now relying on components to save the Firefly's color.
- Jellyfish textures are now prefixed with `jellyfish_`.
- Biome tags relating to entities are now placed in the `entity/spawn` path, being only `spawn` prior.
- Biome tags relating to entity variants are now placed in the `entity/variant` path, being in the `spawn` path prior.
- Cleaned up code related to Termite handling.
- Termite eating behaviors are now entirely data-generated, offering a simple way to achieve mod compatibility.

# Optimizations
- Optimized and cleaned up the implementation of many worldgen features.
- Slightly optimized Tumbleweed rendering.
- Optimized Hanging Tendril rendering.
- Optimized Firefly rendering.
- Optimized Jellyfish rendering.

# Bugfixes
- Fixed the models of Hollowed Log blocks, now having proper UV and rotation like normal logs thanks to Luth! ([#443](https://github.com/FrozenBlock/WilderWild/pull/443))
- Fixed Maple Leaves not dropping Leaf Litters on 1.21.2+.
- Fixed Palm, Maple, and Cypress blocks using the incorrect map colors.
- Fixed Slimes and Magma Cubes not spawning.
- Fixed a few broken recipe unlocks.
- Fixed the Stone Chest having wood-like properties.
- Fixed the Potted Marigold block not dropping items upon breaking.
- Fixed the placement of the Crab Claw in the Creative Inventory.
- Fixed the placement of the Baobab Nut and Split Coconut in the Creative Inventory.
- Fixed the placement of the Ostrich Egg in the Creative Inventory.
- Fixed an issue that prevented Hanging Tendrils from properly loading and saving Vibration data.
- Fixed an issue that prevented Crabs from properly loading and saving Vibration data.
- Fixed Wilder Wild's custom Flower Pot models having ambient occlusion enabled.
- Fixed Wilder Wild's custom Flower Pot models not having the correct UV mapping.
- Fixed some of Wilder Wild's custom Flower Pot models not using correct particle textures.
- Fixed Geysers not being able to erupt when placed facing directly into a block in the `wilderwild:block/geyser_can_pass_through` tag, thanks to Kluski42/Ashlyn!
- Fixed Ostriches not being required for the `Two by Two` advancement.
- Fixed the client tracking ranges and update intervals of Wilder Wild's entities to be consistent with Vanilla's.
- Fixed Wind particle rendering, now properly rotating to face the camera with sideways and always visible to the player.
- Fixed a few issues with Baobab generation.
  - Long pillars of dirt are no longer placed.
    - Instead, up to a max of four blocks of dirt are placed below the tree.
    - Logs are now placed instead of dirt below the "root" sections of the tree.
  - Increased the average amount of Baobab Nuts each Baobab tree generates.
  - Fixed an unstable implementation to check if blocks are replaceable, now replying on the `minecraft:replaceable_by_trees` block tag.
  - Fixed an issue that led to mutable block positions being added to a list instead of immutable block positions.
- Fixed Wilder Wild's blocks having incorrect flammability values.
- Fixed a bug with Wilder Wild's music playing too frequently and in biomes it isn't meant to, thanks to Rebel459! ([#450](https://github.com/FrozenBlock/WilderWild/pull/450))
