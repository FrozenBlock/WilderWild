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
- Added a new song that plays in Lush and Frozen caves, by Willow/pictochats_!
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
- Updated Glory of the Snow petals to use softer sounds.
- Fixed Palm, Maple, and Cypress blocks using the incorrect map colors.
- Added Pollen and all Glory of the Snow Petals to the `minecraft:inside_step_sound_blocks` and `minecraft:combination_step_sound_blocks` block tags.
- Removed the Bush from the `minecraft:inside_step_sound_blocks` block tag.
- Slightly decreased the pitch of Magma block sounds.
- Fixed Slimes and Magma Cubes not spawning.
- Added LudoCrypt's `Dove` to the Magmatic Caves and Lush Caves.
- Fixed a few broken recipe unlocks.
- Fixed the Stone Chest having wood-like properties.
- Removed the `wilderwild:chest_bubbler` entity as it was unnecessary.
  - This has been replaced with the `wilderwild:chest_bubbles` particle, which will now handle the same behavior but on the client.
- Moved the `Ancient City Stone Chests` config option from the `Worldgen` tab to the `Block` tab.

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
  - Spawn in the Flower Forest and Flower Fields biomes by default.
  - Butterflies will follow Mooblooms.

# Firefly Branch
- Fireflies now move faster.
- Fireflies now spawn and move in swarms.
  - When the leader of a swarm is killed or captured in a bottle, the nearest Firefly will become the new swarm leader.
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
