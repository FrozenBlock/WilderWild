Please clear changelog after each release.
Thank you!
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED

-----------------
# Additions
- Added Maple trees.
- Added Maple Grove biome
  - Chestnut Wolves spawn here
- Added Marigold.
- Added better transitions/fizzling edges for Coarse Dirt in biomes where these blocks are prominent.
- Stone piles now generate in Stony Shores, Flower Forests, Sunflower Plains, and Maple Groves.
  - This is controlled with the `wilderwild:has_stone_pile_common,` `wilderwild:has_stone_pile,` and `wilderwild:has_stone_pile_rare` tags.
- Added leaf litters.
- Added falling leaves.
- Added Crimson and Warped Shelf Fungi, which generate naturally.
- Added fallen and snapped Crimson and Warped Fungi, which generate naturally.
- Added more block sound type compatibility for BetterNether and BetterEnd.
- Added block sound type compatibility for Nature's Spirit.
- Added block sound type compatibility for Biomes O' Plenty.
- Added block sound type compatibility for Terrestria.
- Added block sound type compatibility for Regions Unexplored.
- Added block sound type compatibility for Traverse.
- Added block sound type compatibility for Excessive Building.

# Changes
- The Ancient Horn has finally been removed, as it was incredibly out-of-place and overpowered.
- Removed Palms from normal beaches as it was immersion-breaking in cooler areas.
  - Warm Beaches will still have Palms.
- Rewrote the foliage generation of Palm trees.
  - Normal Palm trees now look more "Minecrafty" and no longer use realistic frond generation.
    - In other words, they're visually pleasing and no longer messy!
  - Palms that generated with circular foliage have also received upgraded foliage generation, mirroring the real-world Windmill Palm.
  - Thanks to this new generation, the mixin that modifies the `distance` property's maximum value has been removed.
  - A minimum of one Coconut is now guaranteed to generate on each Palm tree.
- The foliage and grass colors of the Oasis biome are no longer a saturated green.
- Sunflower Plains have been revamped again, and are no longer an eyesore.
- Renamed the Small Sponge to Sponge Bud.
- Removed the Cherry Grove panorama.
- The Jellyfish's animations and rendering have been updated to be more consistent with Vanilla and smoother overall.
- Most naturally generating Birch trees are now medium-sized instead of being very tall, including in Birch Forests.
  - Old Growth Birch Forests will still keep their tall trees.
- Birch Saplings can now grow Medium and Super Birches.
- Both types of Shelf Fungus have now been renamed to end with `Fungi` instead of `Fungus.`
- Removed the custom item models for Beehives and Bee Nests filled with honey for 1.21.2 versions, as this has been added to Vanilla.

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
- Removed many duplicate spawn entries in Wilder Wild's biomes.
- Tumbleweed no longer drop feathers.
- Palm Fronds are now compostable.
- Prickly Pears and Peeled Prickly Pears can now be composted.
- The loot tables of Shelf Fungi have been fixed.

# Technical Changes
- Refactored classes with the `Wilder` prefix to start with the `WW` prefix.
  - This change was made in order for mod compatibility development with Wilder Wild to be more organized.
- Refactored custom BlockState Property-related classes to `block/property,` from `block/impl.`
- Refactored `WilderEnumValues` to `WWBoatTypes.`
- A lot more refactoring.
- Completely reorganized `en_us.json,` now being much easier to find and add translation strings.
- Split the contents of WilderWildClient into multiple classes.
- Swapped out a mixin on `BeaconBlockEntity` in favor of FrozenLib's new `BeaconEffectRegistry.`
