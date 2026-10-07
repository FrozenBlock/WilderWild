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
- Added separate Block Sound Set Overrides for the Icicle and Ice Crystal blocks.
  - Added the `#wilderwild:sound/icicle` Block Tag.
  - Added the `#wilderwild:sound/ice_crystal` Block Tag.
  - Added the `wilderwild:sound_override_icicle` Config Predicate Provider.
    - This Config Predicate Provider controls both the Icicle's Block Sound Set Override, and whether to use Wilder Wild's custom sound for Icicles landing.
- Added the `#wilderwild:icicle_can_grow_under` Block Tag, controlling which blocks Icicles can grow in length while under
  - Contains Packed Ice and Fragile Ice by default.
- Added new config options to control:
  - The frequency of which Icicles will attempt to grow (range between 0-100, 0 being Vanilla's default of ~1.138% and 100 being 16%.)
  - The maximum growth length of Icicles (range between 2-7, 4 by default.)
  - Whether Icicles can grow on floors (disabled by default.)
  - Whether Icicles will drop an Item upon landing (enabled by default.)
  - Whether the amount of damage inflicted by falling Icicles is weaker than other Speleothems (disabled by default.)
  - Whether damaged inflicted by falling Icicles uses a new Damage Source (enabled by default.)
