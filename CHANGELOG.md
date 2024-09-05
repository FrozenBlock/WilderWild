Please clear changelog after each release.
Thank you!
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED

-----------------
# Additions
- Added Maple trees.
- Added Marigold.
- Added better transitions/fizzling edges for Coarse Dirt in biomes where these blocks are prominent.
- Stone piles now generate in Stony Shores, Flower Forests, Sunflower Plains, and Maple Groves.
  - This is controlled with the `wilderwild:has_stone_pile` and `wilderwild:has_stone_pile_rare` tags.

# Changes
- Removed Palms from normal beaches as it was immersion-breaking in cooler areas.
  - Warm Beaches will still have Palms.
- The foliage of Wine Palms now uses Vanilla's `FancyFoliagePlacer.`
- The foliage and grass colors of the Oasis biome are no longer a saturated green.
- Sunflower Plains have been revamped again, and are no longer an eyesore.
- Renamed the Small Sponge to Sponge Bud.
- Removed the Cherry Grove panorama.

# Bug Fixes
- Fixed a few tags that were still unfinished and excluded the Palm set.
- Fixed the Carnation's dye recipe being in the `orange_dye` group.
- Cherry particles are now much more responsive to wind, and no longer get removed upon spawning.
- Baobab, Cypress, and Palm's sign types are now placed after the Mangrove Hanging Sign in the creative inventory, instead of between the Mangrove Sign and Mangrove Hanging Sign.
- Ostrich Eggs are now placed before Sniffer Eggs in the creative inventory.
- Scorched Sand is no longer found in the `Functional Items` tab of the creative inventory.
- Prickly Pears are now placed after Sweet Berries in the `Food & Drinks` tab of the creative inventory.
- Crab Claws and Cooked Crab Claws are now placed before Cod in the creative inventory.
- Baobab, Cypress, and Palm foliage is now found between Mangrove Leaves and Cherry Leaves in the creative inventory.
- Fixed potential worldgen feature cycle crashes when certain config options were disabled.

# Technical Changes
- Refactored classes with the `Wilder` prefix to start with the `WW` prefix.
  - This change was made in order for mod compatibility development with Wilder Wild to be more organized.
- Refactored custom BlockState Property-related classes to `block/property,` from `block/impl.`
- A lot more refactoring.
- Completely reorganized `en_us.json,` now being much easier to find and add translation strings.
- Split the contents of WilderWildClient into multiple classes.
