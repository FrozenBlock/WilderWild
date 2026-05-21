Please clear changelog after each release.
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED.
-----------------
- Renamed the `wilderwild:feature/blue_nematocyst_feature_placeable` Block Tag to `wilderwild:feature/pearlescent_blue_nematocyst_placeable`.
- Renamed the `wilderwild:feature/purple_nematocyst_feature_placeable` Block Tag to `wilderwild:feature/pearlescent_purple_nematocyst_placeable`.
- Cleaned up lots of Configured Features & Data Generation.
- Renamed all of Wilder Wild's `FeatureConfiguration` classes to end with `Configuration` instead of `Config`.
- Removed all Sulfur Caves-related Configured & Placed Features, Biome Tags, and Block Tags.
  - These will now be in a mod named `Chaos Hypercubed`, which is currently in development.
- Removed the `wilderwild:sea_anemone` and `wilderwild:sea_whip` Worldgen Features.
  - Their functionality is replicated through `PlacementModifier`s, similar to the changes made in 26.1.
- We are testing a new method of worldgen configuration!
  - All aquatic worldgen features are now added to their respective biomes, regardless of their respective config entry's value.
  - Now, all aquatic worldgen features will use a new placement modifier that listens to the current value of the config entry.
    - Long-story-short, aquatic worldgen features can be toggled in-game without needing to rejoin the world!
  - Please monitor worldgen performance and tell us if you notice any issues. This is an incredibly important feature to us.
    - If no performance issues are found, we will slowly expand this new system to other worldgen features.
- Slightly decreased the average size of Mesoglea Caves.
