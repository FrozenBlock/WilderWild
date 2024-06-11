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
- Pollen and Seed particles will be removed from the world ten times faster after becoming immobile.
- Ever so slightly improved the Baoab trunk placer.
- Optimized Snowlogging further.
- Optimized Mesoglea Bubble Columns slightly.
- Optimized Wind usage.
- Optimized other parts of the mod that rely on frequent config checks.
- Substantially improved the Hollowed Log model thanks to Soulfate24! ([#379](https://github.com/FrozenBlock/WilderWild/issues/379))
    - Previously, we were not able to figure out how to make a Hollowed Log model without the texture getting misaligned due to a vanilla bug.
    - The result was using a model comprised of only two parts, which messed up the breaking texture.
    - This new model fixes every possible issue with it!
- Updated Flowering Lily Pad textures thanks to Zhen!
- Updated the Milkweed Pod texture thanks to Zhen!
