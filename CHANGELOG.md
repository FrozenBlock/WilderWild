Please clear changelog after each release.
Thank you!

Additions
-----------------
### Magmatic Caves
- Added the Magmatic Caves biome.
- It set my house on fire. Great!
- A new, scorching underground biome consisting of sprawling Magma, Basalt, and Lava pools.
- Lava-related features generate more frequently in this biome.
- Fossils generate much more frequently in this biome.
- Contains the new Geyser block
- Generates in hotter areas of the world.
- The Scorched will frequently spawn here.

### Geysers
- Added the Geyser!
- Will occasionally erupt, pushing entities in the direction it's facing.
- The Eruption of a Geyser lasts between one and two seconds.
  - Can also be powered with Redstone to erupt.
  - Eruptions will last 30 ticks (one and a half seconds) if triggered by Redstone.
  - If placed by a player, will not erupt naturally and will require Redstone activation.
  - The Eruption will remain effective unless blocked.
  - Eruptions that start in the air and move into a liquid, or that start in a liquid and move to the air or another liquid will be less effective past that point.
  - Eruptions started from Lava will set colliding entities ablaze.
  - Will cause a strong wind disturbance upon eruption.
  - Added the `wilderwild:geyser_can_pass_through` tag to control what blocks an Eruption can safely pass through, regardless of block support shape.
    - Contains the `minecraft:trapdoors` tag.
  - Added the `wilderwild:geyser_cannot_pass_through` tag to control what blocks an Eruption can never pass through, regardless of block support shape.
    - Contains the `c:glass_blocks` tag.
- Requires a Stone Pickaxe or stronger to be obtained.
- Naturally generates in the Magmatic Caves and Basalt Deltas biomes.
- Can be crafted with 4 Magma blocks, 4 Basalt blocks, and 1 Lava Bucket.

### Scorched
- Added the Scorched!
- A new Spider variant that is fire-resistant and can walk on Lava.
- Is more resistant to fall damage than regular Spiders.
- Will avoid Water, and cannot swim in it.
- Added a new `Spawn Scorched` config option to control whether Scorched can spawn naturally.

### Frozen Caves
- Added the Frozen Caves biome!
- Ice wasn't built in a day! Or was it?
- Consists of many icy and snowy blocks and a soft water color.
- Generates primarily under snowy mountainous biomes.
  - Note that this does not impact Deep Dark generation whatsoever, as Frozen Caves will only generate above the Deep Dark.
- Strays will spawn here naturally.
  - They can, however, be disabled by removing `wilderwild:frozen_caves` from the `wilderwild:strays_can_spawn_underground` tag.

### Snowlogging
- One-block tall plants, Bushes, Fences, Fence Gates, Walls, Shelf Fungi, Vines, and Sugar Cane can now be Snowlogged.
  - Added the `Allow Snowlogging` config option to control whether Snowlogging is enabled.
    - Requires the game to be restarted upon changing.
  - Added the `Snowlog Blockades` to control whether Fences, Fence Gates, and Walls can be Snowlogged.
    - Requires the game to be restarted upon changing.
    - This option is off by default to prevent excessive loading times.
  - Added the `Natural Snowlogging` control option to dictate whether Snowlogging can occur during worldgen and while it's snowing.

### Breezes, Wind Charges, and Wind
- Breezes and Wind Charges now interact with the Wind System.
  - They will now affect particles, Tumbleweed, and Fireflies.
- Breezes are immune to Ancient Horn Vibrations.
- Tumbleweed will pass through Breezes.
  - This is controlled by the `wilderwild:tumbleweed_passes_through` tag.

Bug Fixes & Changes
---
- Updated the mod protocol version to 4.
- Removed the Coated Sculk item model.

- Gave Magma a new set of sounds.
  - Can be disabled with the `Magma Sounds` config.
- Fire now emits extra particles if on top of Magma.
  - Added the new `Extra Magma Particles` config option to control this.
- Added the new `Geysers In Basalt Deltas` config option to dictate whether Geysers can generate in Basalt Deltas.

- Snow will now continue to generate under snowy mountain biomes, somewhat mimicking older Bedrock Edition Beta versions.
  - Added the `Snow Under Mountains` config option to control this.
  - More biomes can be added to this surface rule by adding them to the `wilderwild:below_surface_snow` tag.
- Fixed Dungeon placement in Jellyfish Caves.
- The `wilderwild:small_sponge_grows_on` tag now includes the `wilderwild:mesoglea` tag instead of only the two Pearlescent Mesoglea types.
- Restricted the placement of multiple features, so they will no longer generate in unwanted places, like on top of structures.
  - Added the `wilderwild:fallen_tree_placeable` tag to control where Fallen Trees can generate.

- Tweaked how wind interacts with Pollen and Seed particles to be more obvious.
- Pollen and Seed particles will now be visible while on the ground.

- The `New Cactus Placement` config is now set to off by default instead of on.
- Added a new `Spawn Fireflies` config option to control whether Fireflies can spawn naturally.
  - The minimum value for `Firefly Spawn Cap` has been changed to 1 as `Spawn Fireflies` makes 0 redundant.
- Added a new `Spawn Jellyfish` config option to control whether Jellyfish can spawn naturally.
  - The minimum value for `Jellyfish Spawn Cap` has been changed to 1 as `Spawn Jellyfish` makes 0 redundant.
- Added a new `Spawn Tumbleweed` config option to control whether Tumbleweed can spawn naturally.
  - The minimum value for `Tumbleweed Spawn Cap` has been changed to 1 as `Spawn Tumbleweed` makes 0 redundant.
- Added a new `Spawn Crabs` config option to control whether Crabs can spawn naturally.
  - The minimum value for `Crab Spawn Cap` has been changed to 1 as `Spawn Crabs` makes 0 redundant.
- Added `block_palm_fronds` to the Mixins config.

- Refactored the `BubbleDirection,` `FlowerColor,` and `SlabWillStairSculkBehavior` classes to the `block.impl` package.

- Glory of the Snow now only drops one item upon being sheared.
- Retextured Baobab Nuts thanks to Zhen!
