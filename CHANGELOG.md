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
    - Contains the `minecraft:trapdoors` tag by default.
  - Added the `wilderwild:geyser_cannot_pass_through` tag to control what blocks an Eruption can never pass through, regardless of block support shape.
    - Contains the `c:glass_blocks` tag by default.
  - Added the `wilderwild:geyser_pushed_extra` tag to control entity types that are pushed a bit harder than usual by Eruptions.
    - Contains `minecraft:arrow` and `minecraft:spectral_arrow` by default.
- Requires a Stone Pickaxe or stronger to be obtained.
- Naturally generates in the Magmatic Caves, Basalt Deltas, and Nether Wastes biomes.
  - Generation in the Nether can be toggled off with the `Geysers In Nether` config option.
  - Can generate in more biomes with the use of the new `wilderwild:has_nether_geyser` and `wilderwild:has_nether_lava_geyser` tags.
    - Contains `minecraft:basalt_deltas` and `minecraft:nether_wastes` respectively by default.
    - Note that these tags will only cause the Nether variations of the feature to place in the specified biomes, not the Magmatic Caves variations.
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
  - Added the `Allow Snowlogging` config option to dictate whether Snowlogging is enabled.
    - Requires the game to be restarted upon changing.
  - Added the `Snowlog Blockades` to dictate whether Fences, Fence Gates, and Walls can be Snowlogged.
    - Requires the game to be restarted upon changing.
    - This option is off by default to prevent excessive loading times.
  - Added the `Natural Snowlogging` config option to dictate whether Snowlogging can occur during worldgen and while it's snowing.

### Fallen large Trees
- Added Fallen Large Jungle Trees.
    - Added the `wilderwild:has_fallen_large_jungle` to control what biomes these will generate in.
    - Added the `wilderwild:has_common_fallen_large_jungle` to control what biomes these will commonly generate in.
- Added Fallen Large Spruce Trees.
    - Added the `wilderwild:has_fallen_large_spruce` to control what biomes these will generate in.
    - Added the `wilderwild:has_common_fallen_large_spruce` to control what biomes these will commonly generate in.
    - Added the `wilderwild:has_clean_fallen_large_spruce` to control what biomes these will generate in, without decoration.
    - Added the `wilderwild:has_common_clean_fallen_large_spruce` to control what biomes these will commonly generate in, without decoration
- Added Fallen Dark Oak Trees.
    - Added the `wilderwild:has_fallen_dark_oak` to control what biomes these will generate in.
    - Added the `wilderwild:has_common_fallen_dark_oak` to control what biomes these will commonly generate in.

### Breezes, Wind Charges, and Wind
- Breezes and Wind Charges now interact with the Wind System.
  - They will now affect particles, Tumbleweed, and Fireflies.
- Breezes are immune to Ancient Horn Vibrations.
- Tumbleweed will pass through Breezes.
  - This is controlled by the `wilderwild:tumbleweed_passes_through` tag.
- Added a new `Wind` category in the `Ambience & Misc` tab.
- Wind particles will now occasionally spawn throughout the world.
  - Particles caused by natural Wind can be disabled with the new `Wind Particles` config option in the `Ambience & Misc.` tab.
    - Added the `Wind Particle Frequency` to control how frequently these will spawn.
      - Added the `Wind Particle Spawn Attempts` to control how many times per tick a Wind particle will attempt to spawn.
  - Particles caused by Wind Disturbances like Breezes and Wind Charges can be disabled with the new `Wind Disturbance Particles` config option in the `Ambience & Misc.` tab.
    - Added the `Wind Disturbance Particle Frequency` to control how frequently these will spawn.
    - Added the `Wind Disturbance Particle Spawn Attempts` to control how many times per tick a Wind Disturbance particle will attempt to spawn.

### Sounds and Ambience
- Gave Magma a new set of sounds.
    - Can be disabled with the `Magma Sounds` config.
- Fire now emits extra particles if on top of Magma.
    - Added the new `Extra Magma Particles` config option to control this.
- The Warm River, Warm Beach, and Snowy Old Growth Pine Taiga will no longer have custom water colors without using the `Water Colors` config.
- LudoCrypt's "Serene Sonder" and "Horizon Afoot" now plays in all Forest and Birch Forest biomes.
- Renamed the `Misc` config tab to `Ambience & Misc.`
- Moved the `Water Colors` category from the `Worldgen` tab to the `Ambience & Misc` tab.
- Added `Deep Dark Fog` to the `Biome Ambience` category in the `Ambience & Misc` tab.
- Added `Frozen Caves Ambience` to the `Biome Ambience` category.
- Added `Frozen Caves Fog` to the `Biome Ambience` category.
- Added `Jellyfish Caves Ambience` to the `Biome Ambience` category.
- Added `Jellyfish Caves Fog` to the `Biome Ambience` category.
- Added `Magmatic Caves Ambience` to the `Biome Ambience` category.
- Added `Magmatic Caves Fog` to the `Biome Ambience` category.
- Added `Magmatic Caves Particles` to the `Biome Ambience` category.
- Added `Badlands Foliage Color` to the new `Vegetation Colors` category in the `Ambience & Misc` tab.
- Tweaked how wind interacts with Pollen and Seed particles to be more obvious.
- Pollen and Seed particles are now visible while on the ground.

### Retextures
- Retextured all Baobab Nut textures thanks to Zhen!
- Retextured all Glory of the Snow textures thanks to Zhen!
- Retextured all Milkweed block textures thanks to Zhen!

Bug Fixes, Changes, & Other Additions
---
- Updated the mod protocol version to 5.
- Removed the Coated Sculk item model.

- Snow will now continue to generate under snowy mountain biomes, somewhat mimicking older Bedrock Edition Beta versions.
  - Added the `Snow Under Mountains` config option to control this.
  - More biomes can be added to this surface rule by adding them to the `wilderwild:below_surface_snow` tag.
- Fixed Dungeon placement in Jellyfish Caves.
- The `wilderwild:small_sponge_grows_on` tag now includes the `wilderwild:mesoglea` tag instead of only the two Pearlescent Mesoglea types.
- Restricted the placement of multiple features, so they will no longer generate in unwanted places, like on top of structures.
  - Added the `wilderwild:fallen_tree_placeable` tag to control where Fallen Trees can generate.
- Refactored multiple paths in the `world` package.
- Fixed some Snapped Large Spruce Trees not generating as intended, instead being skinny.
- Added some Spruce Tree features to biomes they were missing from.
- Properly added Wilder Wild's biomes to Fabric's conventional tags after having some tags missing or improperly used for a long time.
- Nearly all custom generation can now be enabled/disabled in-game after changing the config and rejoining a world, instead of needing to restart the game.
- Added Wilder Wild's content to Serene Season's tags.

- Removed particle textures from the Blocks atlas.
- Removed an unused Ancient Horn particle texture.
- All particle options used by Wilder Wild have been fixed for use in commands and once again accept Vec3.
- Bushes can now be sheared with Dispensers.
- Bushes now grow at the same rate as saplings.
- Glory of the Snow now only drops one item upon being sheared.
- Milkweed now grows a bit slower.
- Using Bone Meal on Milkweed will now cause it to grow first, then drop additional flowers like other tall flowers afterwards.
  - Before, Milkweed would use the tall flower functionality on its top half, and grow on the bottom half.
- Prickly Pears can now be sheared with Dispensers.
- Prickly Pears now grow nearly 4 times slower.
- Shelf Fungus can now be sheared with Dispensers.
- Small Sponges can now be sheared with Dispensers.
- Tumbleweed and Tumbleweed Stems can now be sheared with Dispensers.
- Tumbleweed Stems now grow a bit slower.
- Hanging Tendrils will now drop all their stored XP upon breaking as opposed to only one, to be more fair to players.
- Fixed Hanging Tendrils crashing with C2ME installed. ([#358](https://github.com/FrozenBlock/WilderWild/issues/358))

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
- Slightly altered the config background screens, now starting at Dirt and ending with Chiseled Mud Bricks instead of starting with Mud and ending with the Stripped Cypress Log.

- Added Embeddium compatability.
- Refactored the `BubbleDirection,` `FlowerColor,` and `SlabWillStairSculkBehavior` classes to the `block.impl` package.
- More internal cleanup per usual.
- Removed the Merp Slime easter egg.

### Splash Texts
- Removed "Made from maple!"
- Removed "May or may not contain minuscule amounts of Merp"
- Removed "A slime named "Merp""
- Removed "sweat bread heaheaheh"
- Added "Goodbye to troubled days!"
- Added "Avoids manipulation!"
- Added "IT BURNS!!!11!!1!"
- Added "Scorched Legs!"
- Added "Spiders in the lava!"
- Added "Lava pool floaties!"
- Added "Hiding under the Geyser!"
- Added "Jet streams!"
- Added "Redstone-controlled eruptions!"
- Added "Fire in the hole!"
- Added "Ice in the caves!"
- Added "FrozenBiome"
- Added "Snow on the mountains!"
- Added "Water on the hill!"
- Added "Air on the ground!"
- Added "Windier Wind!"
- Added "Breezes of pollen!"
- Added "The Texture Update!"
- Added "Closes at 8pm!"
- Added "Stationary Edition!"
- Added "Use the config!"
- Added "i need a jacket its cold"
- Added "turn your ac up"
- Added "Also try Configurable Everything!"
- Added "Powered with tags!"
