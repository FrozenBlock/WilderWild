Please clear changelog after each release.
Thank you!
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED

-----------------
- Added Cracked and Mossy Mud Bricks.
  - This includes Stairs, Slabs, and Walls for Mossy Mud Bricks.
  - Alongside these additions, Stonecutters now have Mud recipes for the sake of consistency and ease of use.
- Updated the texture for the Scorching effect's icon.
- The Scorching effect now places Fire upon a mob's death.
- Increased the max time the Scorching effect sets attackers on fire from 4 to 8 seconds.
- Scorched Eyes can now be crafted from a Spider Eye and Blaze Powder.
- Fall damage calculation with Geysers is now consistent with Wind Charges.
- Geysers now output a signal when used with Comparators.
- Possibly fixed some potential crashes related to Geysers.
- Increased the average amount of flowers that generate in Flower Forests.
- Decreased Milkweed generation overall.
- Decreased the amount of flowers in Rainforests.
- Removed Vanilla's default flower feature from plains biomes.
- Reworked flower generation in some biomes to be more "organic," generating in gradients. ([#405](https://github.com/FrozenBlock/WilderWild/issues/405))
  - Plains and Forest Biomes.
  - Birch Forests.
  - Meadows.
  - Rainforests and Temperate Rainforests.
  - Cypress Wetlands.
  - Jungles.
- Slightly optimized Tumbleweed rendering.
- Updated Wilder Wild's BlockEntity registry to match Vanilla, per a new Fabric API update.
- Tweaked how Osseous Sculk is generated once again, no longer using noise sampling.
- Refactored `WilderPreMixinInjectConstants` to `WilderDatagenConstants.`
- Fixed a crash when trying to load the `Lists` class.
- Fixed the mod's config missing a background and smearing frames in certain situations. ([#402](https://github.com/FrozenBlock/WilderWild/issues/402))
