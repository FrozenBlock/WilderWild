Please clear changelog after each release.
Thank you!
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED

-----------------
- Refactored everything in the `misc` package to other places, removing the `misc` class.
  - This was done to prevent slander of our team.
    - ...yeah.
- Increased the protocol version to 6.
- Shortened the channel names for Wilder Wild's custom packets.
- Fixed the Stone Chest's lid animation. ([#383](https://github.com/FrozenBlock/WilderWild/issues/383))
- Added the Geyser to the Redstone tab, just before the Sculk Sensor.
- Properly integrated Villager Types with Wilder Wild's biomes.
- Pollen and Seed particles will be removed from the world five times faster after becoming immobile.
- Seed particles now properly interact with water.
- Ever so slightly improved the Baoab trunk placer.
- Optimized some parts of the mod that rely on frequent config checks.
  - Snowlogging.
  - Mesoglea Bubble Columns.
  - Wind usage.
- Fixed a broken translation string pertaining to the `Magmatic Caves Fog` config option.
- Substantially improved the Hollowed Log model thanks to Soulfate24! ([#379](https://github.com/FrozenBlock/WilderWild/issues/379))
    - Previously, we were not able to figure out how to make a Hollowed Log model without the texture getting misaligned due to a vanilla bug.
    - The result was using a model comprised of only two parts, which messed up the breaking texture.
    - This new model fixes every possible issue with it!
- Updated Flowering Lily Pad textures thanks to Zhen!
- Updated the Milkweed Pod texture thanks to Zhen!
- Updated the entire Cypress set's textures thanks to Zhen's brush-ups!
  - Many issues that were once present in this set are now gone, including:
    - Cypress Leaves being an edit of Spruce Leaves prior.
    - The entire Cypress wood set having contrast issues when compared to all other wood sets in the game.
    - The base color of the Cypress wood set being too desaturated and yellow.
    - A lack of contrast on the sides of Cypress Logs.
