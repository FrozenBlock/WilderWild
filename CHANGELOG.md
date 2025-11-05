Please clear changelog after each release.
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED.
-----------------
- Bumped Wilder Wild's protocol version to 17.
- Added a config option to grant players extended reach while holding a Crab Claw in either hand.
  - This is off by default, but can be changed in-game on-the-fly via the config.
  - The on-the-fly config functionality may not work on 1.21.2+, hence the restart prompt appearing when changing this config value.
- Fireflies can now be dyed in-world by clicking them with a Dye item.
- Cattails now generate closer together, and generate more Cattails per-patch on average.
- Cattail patches can that generate on Mud when not near water have been removed.
- Fixed Jungle Logs turning into Oak Logs when hollowed. ([#516](https://github.com/FrozenBlock/WilderWild/issues/516))
- Fixed a feature cycle order crash with `Oh The Biomes We've Gone`. ([#518](https://github.com/FrozenBlock/WilderWild/issues/518))
- Updated the Ukranian translations, thanks to StarmanMine142! ([#514](https://github.com/FrozenBlock/WilderWild/pull/514) & ([#519](https://github.com/FrozenBlock/WilderWild/pull/519)))
- Updated the Spanish translation, thanks to Kokoroto!

### Tree Changes
- Hanging Coconuts now drop between 1-4 Coconuts when broken, opposed to 3-4.
- Palm Trees can now only naturally generate a maximum of three Coconuts.
- Added the Yellow Maple Sapling, Orange Maple Sapling, and Red Maple Sapling.
  - This change was made due to community request and feedback, as having only one sapling which grants a random color was not preferred.
  - The original Maple Sapling will be datafixed into the Yellow Maple Sapling.
- The generation of Maple Trees has been completely overhauled, now sporting a much more natural shape.
  - This change was made after a long time due to community request and feedback, as the old Maple Tree generation did not fit well any other trees in Minecraft.
  - Added a config option to toggle the old shapes of Maple Tree trunks and foliage.
- Fallen Maple Trees now occasionally generate with branches.
- Added the `wilderwild:maple_trunk_placer` Trunk Placer, used for the new Maple Tree generation.
  - Contains both a `trunk_branch_placement` and `lower_trunk_branch_placement` field.
    - `lower_trunk_branch_placement` will be used if the current trunk position is less than half of the total trunk height.
  - The `alt_trunk_placer` field contains an alternate Trunk Placer, which will be used if the new Maple Tree generation is disabled.
- Renamed the old `wilderwild:maple_foliage_placer` Foliage Placer to `wilderwild:legacy_maple_foliage_placer`.
- Removed the `wilderwild:round_maple_foliage_placer` Foliage Placer.
- Added the `wilderwild:maple_foliage_placer` Foliage Placer, used for the new Maple Tree generation.
  - Generates much like Oak and Birch foliage but with hanging leaves on the bottom, similar to Cherry foliage.
  - The `hanging_leaves_chance` and `hanging_leaves_extension_chance` dictate the chances for hanging leaves blocks to generate, then to be extended upon generation respectively.
  - The `alt_foliage_placer` field contains an alternate Foliage Placer, which will be used if the new Maple Tree generation is disabled.
- The `foliage_radius_shrink` field of Wilder Wild's Trunk Branch Placement has been renamed to `foliage_radius_offset`, and now uses an Integer Provider instead of an Integer.
- The `offset_last_log_chance`, `minimum_branch_length_for_offset`, `foliage_placement_chance`, and `foliage_radius_offset` fields of Wilder Wild's Trunk Branch Placement are now all optional.

### Trades & Loot Tables
- Desert Fisherman Villagers now sell Palm Boats instead of Jungle Boats.
- Fisherman Villagers can now purchase Crab Buckets and Jellyfish Buckets for Emeralds.
- Wandering Traders can now sell:
  - Carnations
  - Hibiscus
  - Seeding Dandelions
  - Marigolds
  - Pasqueflowers
  - Tumbleweed Stems
  - Prickly Pears
  - Icicles
  - Barnacles
  - Sea Anemone
  - Sea Whips
  - Auburn Moss
  - Algae
  - Plankton
  - Geysers
- Config options to toggle every single Wilder Wild Villager trade have been introduced.
- Added a new `entity_villager` mixin config option.
  - This is only used to swap the Desert Fisherman Villager's Jungle Boat for a Palm Boat.
- Loot Tables that are modified by Wilder Wild now only add items if their respective worldgen config options are enabled.
- Prickly Pears can now be found within Chests in Desert Villages.

### Creative Inventory Sorting
- Shelf Fungi are now placed after all fungi, instead of being placed after both Overworld and Nether fungi.
- Pollen is now placed before Glow Lichen.
- Coconut is now placed after the Blue Egg in the 'Combat' category in 1.21.5+.
- The Firefly Bottle and Butterfly Bottle are now placed after the Milk Bucket.
- Barnacles is now placed before Algae and Plankton.
- The Ostrich Egg and Penguin Egg are now placed after Frogspawn on 1.21.1.
  - Do note this was already fixed on 1.21.2+.
- Fixed the Gabbro building set being misplaced on 1.21.1.
- Moved Sculk building blocks to the very bottom, and added the regular Sculk block alongside them.

### Textures
- Retextured the Palm Sapling.
- Retextured all Gabbro blocks.
- Retextured the Geyser.
- Retextured the Baobab Nut item.
- Retextured the Jellyfish Bucket.
- Retextured the Marigold.
- Retextured the Willow Sapling.
- Retextured the Spruce Leaf Litter.
- Retextured the Willow Leaf Litter.
- Retextured the Maple Sapling.
