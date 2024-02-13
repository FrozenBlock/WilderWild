Please clear changelog after each release.
Thank you!

Additions
-----------------
- Added snowlogging to some blocks, namely 1-block tall plants, Bushes, Fences, Fence Gates, Walls, Shelf Fungi, Vines, and Sugar Cane.
  - Added the `Allow Snowlogging,` `Snowlog Blockades,` and `Natural Snowlogging` config options.
    - To prevent long load times due to excessive blockstates, `Snowlog Blockades` is off by default.

- Added the Magmatic Caves biome.
  - It set my house on fire. Great!
  - A new, scorching underground biome consisting of sprawling Magma, Basalt, and Lava pools.
  - Lava-related features generate more frequently in this biome.
  - Fossils generate much more frequently in this biome.
  - Contains the new Geyser block
  - Generates in hotter areas of the world.

- Added the Geyser!
  - Will occasionally erupt, pushing entities in the direction it's facing.
   - If covered by Lava, will set entities on fire.
   - The Eruption of a Geyser lasts between one to two seconds.
     - Can also be powered with Redstone to erupt.
     - Eruption will last 30 ticks (one-and-a-half seconds) if triggered by Redstone.
     - The Eruption will remain effective unless blocked.
   - Eruptions that start in the air and move into a liquid, or that start in a liquid and move to the air or another liquid will be less effective past that point.
   - If placed by a player, will not erupt naturally.
   - Added the `wilderwild:geyser_can_pass_through` tag to control what blocks an Eruption can safely pass through, regardless of block support shape.
     - Currently contains the `minecraft:trapdoors` tag.
   - Added the `wilderwild:geyser_cannot_pass_through` tag to control what blocks an Eruption can never pass through, regardless of block support shape.
     - Currently contains the `c:glass_blocks` tag.
  - Requires a Stone Pickaxe or stronger to be obtained.

- Added the Frozen Caves biome.
  - Ice wasn't built in a day! Or was it?
  - Consists of many icy and snowy blocks and a soft water color.
  - Generates primarily under snowy mountain biomes, but may rarely be found elsewhere.
    - Note that this does not impact Deep Dark generation whatsoever, as Frozen Caves will only generate above the Deep Dark.
  - Strays will spawn here naturally.
    - They can, however, be disabled by removing `wilderwild:frozen_caves` from the `wilderwild:strays_can_spawn_underground` tag.

Bug Fixes & Changes
---

## Breezes, Wind Charges, and Wind
- Tweaked how wind interacts with Pollen and Seed particles to be more obvious.
- Breezes and Wind Charges now interact with the Wind System.
  - They will now affect particles, Tumbleweed, and Fireflies.
  - Breezes are immune to Ancient Horn Vibrations.
  - Tumbleweed will pass through Breezes.
    - This is controlled by the `wilderwild:tumbleweed_passes_through` tag.

- Gave Magma a new set of sounds.
  - Can be disabled with the `Magma Sounds` config.
- Fire now emits extra particles if on top of Magma.
  - Added the new `Extra Magma Particles` config option to control this.
- Snow will now continue to generate under snowy mountain biomes, somewhat mimicking older Bedrock Edition Beta versions.
  - Added the `Snow Under Mountains` config option to control this.
  - More biomes can be added to this surface rule by adding them to the `wilderwild:below_surface_snow` tag.
- The `wilderwild:small_sponge_grows_on` tag now includes the `wilderwild:mesoglea` tag instead of only the two Pearlescent Mesoglea types.
- Restricted the placement of multiple features, so they will no longer generate in unwanted places, like on top of structures.
  - Added the `wilderwild:fallen_tree_placeable` tag to control where Fallen Trees can generate.
- Refactored the `BubbleDirection,` `FlowerColor,` and `SlabWillStairSculkBehavior` classes to the `block.impl` package.
- The `New Cactus Placement` config is now set to off by default instead of on.
- Fixed Dungeon placement in Jellyfish Caves.

2.3.1
---
- Fixed Shelf Fungi crashing the game upon being interacted with on 1.20.2 and 1.20.1.
- Removed the sound events for the previously removed discs.
- Ostrich attacks can now damage and break Armor Stands.
- Fixed the death message from an Ostrich's attack not displaying properly.
- Fixed shears not snipping Glory of the Snow blocks
- Updated zh_CN (Simplified Chinese) translation (Thanks to ioococ)
