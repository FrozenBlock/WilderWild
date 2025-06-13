Please clear changelog after each release.
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED.
-----------------
- Added a title screen version of `Espial` by `pictochats_`!
- Fixed Doors not using the proper item class.
- Jellyfish have had their hardcoded spawning logic vastly simplified, now using Vanilla's Spawn Charge/Cost system.
  - As a result of this change, the following changes have been made:
    - The `wilderwild:entity/spawn/jellyfish` biome tag now dictates all biomes Jellyfish can spawn in.
    - The `wilderwild:entity/spawn/jellyfish_special_spawn` biome tag has been replaced with the `wilderwild:entity/spawn/jellyfish_common_spawn` biome tag.
      - This tag now dictates which biomes (of the ones they can spawn in) Jellyfish can spawn more frequently and closer together in.
- The `wilderwild:entity/spawn/common_butterfly` biome tag has been renamed to `wilderwild:entity/spawn/butterfly_common`.
