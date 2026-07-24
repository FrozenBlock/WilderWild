Please clear changelog after each release.
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED.
-----------------
- Updated the Jellyfish's loot table.
  - One loot table is now used for the Jellyfish instead of multiple, with the previous loot tables now being referenced from within the new loot table.
- Falling Maple Leaves can no longer increase the amount of leaves in an already existing leaf litter block.
- Falling Maple Leaves now only have a 25% chance to place a leaf litter block.
- Fixed an issue that prevented Orange Maple Leaves from being smelted into Orange Maple Leaf Litter.
- The Dying Yellow Maple tree feature's height is now the same as its Orange and Red counterparts.
- Wilder Wild's Wolf Variants spawn modifications are now data-driven.
  - These can be found within the `frozenlib/variant_spawn_injection` data folder.
  - These modifications simply allow Wolf Variants to spawn in Wilder Wild's biomes without interfering with Data Packs or other mods.
  - Added the following Biome Tags:
    - `#wilderwild:entity/variant/wolf_ashen`
    - `#wilderwild:entity/variant/wolf_woods`
    - `#wilderwild:entity/variant/wolf_pale`
    - `#wilderwild:entity/variant/wolf_black`
    - `#wilderwild:entity/variant/wolf_chestnut`
  - Added a config option to toggle these modifications.
