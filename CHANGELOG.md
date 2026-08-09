Please clear changelog after each release.
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED.
-----------------
- Modified the placement of Dappled Forests, to not interfere as much with Maple Forests.
  - Maple Forests and Dappled Forests can often be seen near each other, though Dappled Forests favor higher and bumpier terrain a tad further from snowy regions.
- Wilder Wild's blocks are now properly included in the `minecraft:replaceable` Blocks Tag.
- Added the Stripped Poplar Log and Stripped Hollowed Poplar Log blocks.
- Wilder Wild's Material Rules (formerly known as Surface Rules) are now data-driven.
- Wilder Wild's custom Cactus damage sound now only plays if the Cactus block sounds config option is enabled.
- Removed Wilder Wild's Potent Sulfur Geyser Wind Disturbance, as it has been moved into FrozenLib.
- Wilder Wild's Structure Processor modifications are now data-driven per FrozenLib's changes.
- Revamped the implementation of Chest bubbling.
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
- Fixed an issue that caused Hanging Tendrils to continue rendering as a Block Entity whilst the billboard rendering config option is disabled.
- Fixed an issue that resulted in Icicles dropping as items after landing instead of shattering as intended.

### 26.3+
- The Shrub now uses Vanilla's Red Shrub sound type.
- Added the `wilderwild:termite_edible` Structure Processor, with the following format:
  - `value`: Can be either true or false. Sets the `termite_edible` property to this value for all Blocks with this property.
