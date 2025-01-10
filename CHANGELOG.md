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

# 1.21.5
- Added the `wilderwild:non_overriden_falling_leaves` block tag, controlling which leaves blocks won't have their particles entirely replaced by Wilder Wild.
- Increased the frequency of Wilder Wild's falling leaves.
