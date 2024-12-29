Please clear changelog after each release.
Thank you!
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED
hi
-----------------
- Added the Tundra biome.
- Revamped flower distribution and generation.
- Added a new config option to disable Pollen generation.
- Fixed the models of Hollowed Log blocks, now having proper UV and rotation like normal logs thanks to Luffurius! ([#443](https://github.com/FrozenBlock/WilderWild/pull/443))
- Slightly optimized Tumbleweed rendering.

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
