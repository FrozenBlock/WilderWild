Please clear changelog after each release.
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED.
-----------------
- Fixed an oversight that caused Wilder Wild's Material Rules to incorrectly apply in caves.
- Fixed a bug that caused Leaf & Leaf Litter particles to spawn upon their respective blocks breaking when only their walking particles config option is enabled.
- The `Snowlog Blockades` config option has been set back to false by default, after accidentally being changed to true.
- Added the `wilderwild:falling_icicle` Damage Type to the `#minecraft:damages_helmet` Damage Type Tag.
- Added the `wilderwild:ostrich` Damage Type to the `#minecraft:panic_causes` Damage Type Tag.

### 26.4+
- Added the `#wilderwild:music_pitch_shift_dying_forest` Biome Tag, controlling which biomes will subtly, dynamically shift the pitch of background music.
- Removed the following Block Tags related to Wilder Wild's Icicles:
  - `#wilderwild:icicle_grows_when_under`
  - `#wilderwild:icicle_falls_from`
- Added separate Block Sound Set Overrides for the Icicle, Ice Crystal, and Brittle Ice blocks.
  - Added the `#wilderwild:sound/icicle` Block Tag.
  - Added the `#wilderwild:sound/ice_crystal` Block Tag.
  - Added the `#wilderwild:sound/brittle_ice` Block Tag.
- Added new config options to control:
  - The frequency of which Icicles will attempt to grow (range between 0-100, 0 being Vanilla's default of ~1.138% and 100 being 16%.)
  - The maximum growth length of Icicles (range between 2-7, 4 by default.)
  - Whether Icicles can grow on floors (disabled by default.)
  - Whether Icicles will drop an Item upon landing (enabled by default.)
  - Whether the amount of damage inflicted by falling Icicles is weaker than other Speleothems (disabled by default.)
  - Whether damaged inflicted by falling Icicles uses a new Damage Source (enabled by default.)
  - The time in ticks between consecutive cracks of Brittle Ice (15 by deafult.)
  - Whether the shattering of Brittle Ice can spread to neighboring Brittle Ice (enabled by default.)
  - Whether falling onto Brittle Ice causes it to shatter instead of cracking (enabled by default.)
  - Whether projectiles can cause Brittle Ice to shatter (enabled by default.)
- Particles are now much more visibly affected by Wind Disturbances (Breezes, Wind Charges, Geysers, Geothermal Vents, etc.)

### Worldgen
- Completely reorganized and cleaned up Wilder Wild's worldgen.
  - Many features and tags have been removed, renamed, or revamped, and will not be listed in the changelog for sanity's sake.
- Removed the `wilderwild:water_cover` Feature Type, as the following together are able to replicate its functionality:
  - `minecraft:simple_random_selector` Feature Type.
  - `minecraft:count` Placement Modifier Type.
  - `minecraft:offset` Placement Modifier Type.
  - `minecraft:environment_scan` Placement Modifier Type.
- Removed the `wilderwild:large_mesoglea` Feature Type, as the `minecraft:large_speleothem` Feature Type provides the same functionality.
  - A mixin has been implemented in `SpeleothemUtils` to no longer consider Lava a valid block to place features in if the feature's base Block is Mesoglea.
- Random pillars of Mesoglea no longer generate, and have been replaced with more natural-looking patches on ceilings and floors containing small clusters of pillars.
- The `wilderwild:geothermal_vent` Feature Type has been removed, and superseded by the `wilderwild:vent_stack` Feature Type with the following format:
  - `vent_block`: A Block State Provider, determining the top-most Block of the stack.
  - `base_block`: A Block State Provider, determining what Block to encase the stack in.
  - `inner_block`: A Block State Provider, determining what Block to place down the center of the stack.
  - `surface`: Which surface to place the feature on (`ceiling` or `floor`.)
  - `height`: An Integer Provider, determining how tall a stack can be.
  - `replaceable`: A Block's ID, a list of Block IDs, or a Block Tag determining which Blocks this feature can replace.
- Random pillars, splotchy disks, and piles of Gabbro and Magma no longer generate in Magmatic Caves, and have been replaced with:
  - Patches on ceilings and floors containing small clusters of pillars.
  - Geothermal Vent Stacks on ceilings and floors.
  - Lakes of Lava.
- Gabbro and Magma path generation in the Magmatic Caves biome is now generated via a Material Rule instead of a Feature.
- Moss generation in the Deep Lukewarm Ocean biome is now generated via a Material Rule instead of a Feature.
