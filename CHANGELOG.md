Please clear changelog after each release.
Thank you!
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED
hi
-----------------
- Bumped Wilder Wild's protocol version to 12.
- Added the Tundra biome.
  - This biome generates near the colder regions of the world where Plains would be, before the snow takes over.
- Revamped flower distribution and generation.
- Added a new config option to disable Pollen generation.
- Fixed the models of Hollowed Log blocks, now having proper UV and rotation like normal logs thanks to Luffurius! ([#443](https://github.com/FrozenBlock/WilderWild/pull/443))
- Slightly optimized Tumbleweed rendering.
- Added a config option to toggle rotatable Reinforced Deepslate.
- Fixed Maple Leaves not dropping Leaf Litters on 1.21.2+.
- Added more variety to Pale Oak trees on 1.21.4.
- Crab variants can now be added via datapacks, though by default there is only one Crab variant.
- Removed two unnecessary blockstate properties from Osseous Sculk relating to their generation height.
- Reworked Osseous Sculk generation once again.
  - Osseous Sculk generation is now taller on average, much like it once was in earlier versions of Wilder Wild.
  - Many optimizations were made to Osseous Sculk generation.
- Added a config option to toggle Hanging Tendril generation.
- Added a config option to toggle Osseous Sculk generation.
- Added a config option to toggle Sculk Slab, Sculk Stairs, and Sculk Wall generation.
- Grouped all Sculk-related config options into a new dropdown in the Block config menu.
- Optimized Hanging Tendril rendering.
- Added a config option to toggle Dandelion bone mealing into Seeding Dandelions.
- Added a config option to toggle Seeding Dandelion shearing into Dandelions.
- Added a config option to toggle Lily Pad bone mealing into Flowering Lily Pads.
- Added a config option to toggle Flowering Lily Pad shearing into Lily Pads.
- The sound of an Ostrich laying an egg no longer uses Frog Spawn sounds.
- Fixed Palm, Maple, and Cypress blocks using the incorrect map colors.
- Added Pollen to the `minecraft:inside_step_sound_blocks` and `minecraft:combination_step_sound_blocks` block tags.
- Removed the Bush from the `minecraft:inside_step_sound_blocks` block tag.
- Slightly decreased the pitch of Magma block sounds.
- Fixed Slimes and Magma Cubes not spawning.
- Fixed a few broken recipe unlocks.
- Fixed the Stone Chest having wood-like properties.
- Removed the `wilderwild:chest_bubbler` entity as it was unnecessary.
  - This has been replaced with the `wilderwild:chest_bubbles` particle, which will now handle the same behavior but on the client.
- Moved the `Ancient City Stone Chests` config option from the `Worldgen` tab to the `Block` tab.
- Removed the `wilderwild:hanging_tendril_extract_xp` game event in favor of `minecraft:block_change.`
- Fixed the Potted Marigold block not dropping items upon breaking.
- Slightly decreased the amount of large Oak trees in Forests.
- Revamped the generation of flowers in the Flower Forest and Flower Fields biomes, being much more visually pleasing.
- Small Bushes now generate with all exposed sides covered by leaves.
- Coconuts no longer immediately fall once logs connected to Palm Fronds are broken.
- Slightly reworking the erupting of naturally generated Geysers.
  - Naturally generated Geysers now only erupt when a player is within 48 blocks of them.
  - The closer a player is, the higher the chance for it to erupt.
- Fixed the placement of the Crab Claw in the Creative Inventory.
- Fixed the placement of the Baobab Nut and Split Coconut in the Creative Inventory.
- Fixed the placement of the Ostrich Egg in the Creative Inventory.
- Hanging Tendrils now play animations in sync with each other while being rendered as billboards.
- Fixed an issue that prevented Hanging Tendrils from properly loading and saving Vibration data.
- Fixed an issue that prevented Crabs from properly loading and saving Vibration data.
- Particles created by gargling Sculk Shriekers are now handled by the client, using the new `wilderwild:shrieker_bubbles` particle.
- Added a new config option to toggle between the updated and original Sculk Shrieker selection outline.
- Particles created by entities touching Seeding Dandelions are now dependent on how the entity was moving.
- Glories of the Snow have been removed.
  - They are now replaced with four Hibiscus Blocks:
    - Red Hibiscus
    - Yellow Hibiscus.
    - White Hibiscus.
    - Pink Hibiscus.
    - Purple Hibiscus.
  - Glory of the Snows will be datafixed into random Hibiscuses.
  - Glory of the Snow Petals will be datafixed into Pollen.
- Added a config option to toggle pools of water generating in River biomes.
  - This is now disabled by default, unlike before.
- Scorched now set entities on fire upon attacking.
- Removed the `wilderwild:sculk_spreader` entity, and updated the `spreadsculk` command to place all Sculk at once.
- Updated the Chiseled Mud Bricks texture.
- Wilder Wild's custom flower, leaves, sapling, cactus, coarse dirt, ice, frosted ice, mushroom, mushroom block, sandstone, lily pad, and melon block sounds now all rely on tags.
  - These tags can be found in the `/sound` folder for block tags.
- Fixed Wilder Wild's custom Flower Pot models having ambient occlusion enabled.
- Fixed Wilder Wild's custom Flower Pot models not having the correct UV mapping.
- Fixed some of Wilder Wild's custom Flower Pot models not using correct particle textures.
- Pink Petals can now be placed into Flower Pots.
- Backported Wildflowers.
  - Wildflowers generate in biomes in the `wilderwild:feature/has_wildflowers` and `wilderwild:feature/has_sparse_wildflowers` tags.
- Added Clovers.
  - Clovers generate in biomes in the `wilderwild:feature/has_clovers` tag.

# Music
- Added three new tracks by Willow/pictochats_.
  - Dahlia.
    - Plays in Magmatic Caves biomes.
  - Caves Temp
    - Plays in Frozen Caves, Magmatic Caves, Mesoglea Caves, and Lush Caves biomes.
  - Maple Temp
    - Plays in Maple Forest and Tundra biomes.
- Ludocrypt's `Dove` now also plays in Lush Caves.

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
- Renamed the `wilderwild:feature/has_fallen_swamp_oak_trees` biome tag with the `wilderwild:feature/has_fallen_swamp_trees` biome tag.

# Penguins
- Added the Penguin.
  - Penguins spawn in Snowy Beaches and Frozen Oceans.
  - Will dive underwater to hunt for Squid.
  - Can be bred with an Ink Sac or Glow Ink Sac, laying an egg after afterward.
  - Chases Boats controlled by players, granting them a temporary speed boost.
  - Will occasionally "call" out for other Penguins, grouping together for a short time.
  - The Penguin will spawn in any biomes within the `wilderwild:entity/spawn/penguin` biome tag.
  - The Penguin can spawn on top of any blocks within the `wilderwild:penguins_spawnable_on` block tag.
  - Penguins will ignore unique friction from any blocks within the `wilderwild:penguin_ignore_friction` block tag.
- Added the Penguin Egg block.

# Butterfly Branch
- Added Butterflies
- Added Butterfly Bottles
- Removed an unused sound event for caves
- Removed Abandoned Cabins as they conflicted with Vanilla's modern structure design.
- Converted all of Wilder Wild's mob variants to now be data-driven!
  - As such, `FireflyBiomeColorRegistry` has been removed and is replaced with a `biomes` field in each .json file.
- Optimized Firefly rendering.
- Fireflies now only have one flicker pattern.
- Merged all 17 Firefly Bottle items into 1 item, now relying on components to save the Firefly's color.
- Jellyfish textures are now prefixed with `jellyfish_`.
- Optimized Jellyfish rendering.
- Biome tags relating to entities are now placed in the `entity/spawn` path, being only `spawn` prior.
- Biome tags relating to entity variants are now placed in the `entity/variant` path, being in the `spawn` path prior.
- Cleaned up code related to Termite handling.
- Termite eating behaviors are now entirely data-generated, offering a simple way to achieve mod compatibility.

# Moobloom Branch
- Added Mooblooms
  - When bred, spawn a baby with a flower of a mixed color if possible.
  - Cannot be converted into regular Cows, unlike Mooshrooms.
  - Each flower can be sheared off the Moobloom.
    - These can each be regrown by feeding it Wheat.
    - Cannot breed unless all flowers are present.
  - Spawn in the Flower Forest, Flower Fields, and Sunflower Plains biomes by default.
  - Butterflies will follow Mooblooms.

# Firefly Branch
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

# Feature Optimization Branch
- Removed Mud-specific Cattail features from biomes that do not generate Mud.
  - The `wilderwild:feature/has_cattail_mud` biome tag has been added as a result of this change.
- Removed Shelf Fungi-only features, now relying solely on trunk decorators to place Shelf Fungi.
  - The `wilderwild:feature/has_brown_shelf_fungi` and `wilderwild:feature/has_red_shelf_fungi` biome tags have been removed as a result of this change.
- Optimized and cleaned up the implementation of many worldgen features.
- All trees that don't place foliage now use `wilderwild:no_op_foliage_placer.`
- Removed random circular, shallow pools from caves.
  - The `wilderwild:feature/no_pools` biome tag has been removed as a result of this change.
- Oak trees generated in Savanna or Badlads biomes no longer have Shelf Fungi.
- Bushes now generate on Grass and Coarse Dirt in Wooded Badlands.
  - Renamed the `wilderwild:feature/has_wooded_badlands_terracotta_bush` biome tag to `wilderwild:feature/has_wooded_badlands_bush` as a result of this change.
- Decreased the amount of trees per-chunk in Wooded Badlands by 1.
- Jungle trees can now generate with Shelf Fungi.
- Mangrove trees can now generate with Shelf Fungi.

# Pale Mushroom Branch
- All Shelf Fungi can now be mined faster with an Axe.
- Added the Pale Mushroom and Pale Shelf Fungi blocks.
    - At night, these will emit gray spore-like particles.
- Added the Pale Mushroom Block.
    - Emits fog-like particles at night while above a Mushroom Stem block.
- Huge Pale Mushrooms generate naturally in the Pale Garden Biome.
- Properly updated the sound type for Eyeblossoms to match other flowers.
- Termites can now eat Pale Oak blocks.
- Resin Clumps, Creaking Hearts, Eyeblossoms, and Pale Mushrooms now kill Termites on contact.

# Gabbro Branch
- Added the Gabbro stone type.
    - Generates naturally in Magmatic Caves.
    - Can be polished.
    - Can be crafted into bricks.
    - Mossy brick variants and stair, slab, and wall variants of the base stone require Trailier Tales to be installed.
    - Scorched can spawn on Gabbro.

# 1.21.5
- Added the `wilderwild:non_overriden_falling_leaves` block tag, controlling which leaves blocks won't have their particles entirely replaced by Wilder Wild.
- Increased the frequency of Wilder Wild's falling leaves.
- Renamed Wilder Wild's `Bush` to `Shrub.`
- Updated all Maple Leaf Litters to be consistent with Vanilla's new Leaf Litter block.
