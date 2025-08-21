Please clear changelog after each release.
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED.
-----------------
# Sounds
- Added unique sounds for Maple Leaves.
- Added unique sounds for Maple Leaf Litters.
- Added unique sounds for the Maple wood set.
- Added unique sounds for Pale Oak Leaves.
  - This will be disabled if the config options for either Pale Oak wood or Leaves sounds are disabled.
- Added unique sounds for the Pale Oak wood set.
  - Added a config option to toggle these sounds.
- Added unique sounds for Auburn and Pale Moss.
  - Added a config option to toggle these new sounds.
- Leaves blocks now use Azalea Leaves' sounds instead of Cherry Leaves' sounds.
- Added unique sounds for coniferous Leaves blocks.
- Updated all Hollowed Log/Stem sounds to be more pleasing to the ears.
- Added unique sounds for Short/Tall Grass, Fern, Bush, and Clover blocks, now sounding much cleaner and less disturbing.
  - The Frozen and Dry variants of these blocks will use tweaked versions of these sounds to fit their appearance better.
  - Added a config option to toggle these new sounds.
- Added unique sounds for placing and breaking Barnacles.
- Added unique sounds for placing and breaking Sea Anemone.
- Added unique sounds for placing and breaking Tube Worms.
- Added unique sounds for Copper Chests being opened and Closed underwater in 25w32a+.
- Updated the place and break sounds of Geysers to be distinct from Gabbro.
- Updated Gabbro Bricks sounds to be more distinct from regular Gabbro blocks.
- Updated Coarse Dirt sounds to be more distinct from regular Dirt.
- Updated Fragile Ice and Frosted Ice sounds to be more distinct from other Ice blocks.
- Updated Ice, Packed Ice, and Blue Ice sounds to be more distinct from Fragile Ice and Frosted Ice.
- Added unique sounds for entering, exiting, swimming, and splashing in Mesoglea.
- Added a unique ambient loop while a player is inside of Mesoglea.
- Tweaked the sounds and volume of Mesoglea Bubbles popping.
- Added unique sounds for Jellyfish hiding in Mesoglea/Nematocyst.
- Sponge Buds now use the Wet Sponge sound type when waterlogged.
- Fixed Mushroom Stems not using Wilder Wild's mushroom block sound type.
- Fixed Jack O Lanterns not using Wilder Wild's melon sound type.
- Pumpkin and Melon stems now use crop sounds opposed to wood sounds.
- Fixed Pink Petals incorrectly being assigned a different block SoundType.
- Wilder Wild's Sandstone sounds block tag now uses Fabric's conventional Sandstone block tag.
- Moved Leaf Litters from the `minecraft:inside_step_sound_blocks` block tag to the `minecraft:combination_step_sound_blocks` block tag in 1.21.5+.
- Scorched Sand blocks and Tumbleweed now play Desert ambience.
- Desert ambience now plays in the Oasis biome in 1.21.5.
  - Added a `block_ambience` field to Wilder Wild's mixin config.
  - These changes are not present in 1.21.6+ as the biome requirement no longer exists.
- Fixed Echo Glass place sounds being stereo instead of mono.
- Fixed underwater Chest closing sounds being stereo instead of mono.
- Decreased the amount of bass present in some of the Jellyfish's sounds.
- The ambient loops for the Deep Dark, Dripstone Caves, and Lush Caves biomes are no longer preloaded.
- Removed duplicate leaves sounds.
- Removed duplicate flower sounds.
- Removed sounds for the Palm Crown, as the block has long-since been removed.
- Removed a few unused sounds.

# Blocks
- Added Leaf Litter blocks for every leaf type in 1.21.5+.
- Leaf particles now spawn while walking on Leaf Litters and Leaves blocks in 1.21.5+.
  - Added config options to toggle these individually, with normal Leaves blocks having this disabled by default.
  - This applies to Breezes, which will spawn a steady stream of Leaves flowing upwards.
- Leaf particles now spawn from explosions near and when manually breaking Leaf Litters and Leaves blocks in 1.21.5+.
  - Added config options to toggle these individually, all being disabled by default.
- Vastly extended the usability of Wilder Wild's Falling Leaf particles for modders in 1.21.5+:
  - Any block can now be given Falling Leaves.
  - Added the `LeafMovementType` enum, used to specify different ways the Falling Leaf should move.
- Added Gabbro blocks to Sculk-related tags they were missing from.
- Nether Wart, Nether Wart Blocks, and Warped Wart Blocks now kill Termites upon contact.
- Added a few missing Crimson and Warped blocks to the `wilderwild:kills_termite` block tag.
- Fixed a few Pale Oak blocks missing from tags.
- Removed the cracked variants of Scorched Sand blocks, simplifying them in both implementation and design to be more consistent with Vanilla.
- Removed duplicate Stonecutting recipes for Mud Bricks. ([#502](https://github.com/FrozenBlock/WilderWild/issues/502))
- Penguin and Ostrich eggs now take much longer to hatch. ([#501](https://github.com/FrozenBlock/WilderWild/issues/501))

# Textures & Models
- Retextured all Jellyfish to be more distinct from one another, and to fit into Minecraft's art style more.
  - Jellyfish now have additional "oral arms."
  - Fixed an incorrect calculation that caused the Jellyfish's tentacles to not lag behind the "cap" properly, but instead get pushed in the opposite direction.
- Retextured the Jellyfish Bucket.
- Retextured the Jellyfish Spawn Egg in 1.21.5+.
- Updated the textures of most Nematocyst and Mesoglea blocks to reflect the respective new Jellyfish textures.
- The Nematocyst model no longer has shading.
- Fixed a slight offset on one part of the Nematocyst's model.
- Redesigned the Crab to be more in-line with Mojang's proposed design.
  - The old textures and model can still be used by disabling the internal `Mojang Crabs` Resource Pack.
- Retextured the Marigold.
- Retextured the Marigold Moobloom.
- Retextured Lantanas.
- Changed the Clovers' textures to be brighter, making them more visible on Grass Blocks.
- Retextured the Sea Anemone's item texture to feel more like a Sea Anemone.
- Updated the Plankton block's textures and animation.
- Updated the Cattail's textures.
  - Cattails can now sway like other underwater vegetation.
  - Swaying will occur when waterlogged, though the top half will have a weaker sway if it isn't also waterlogged.
- Completely retextured Milkweed, now looking more akin to their real-world tropical counterpart.
- Updated the textures for all Hibiscus flowers.
- Fixed a tiling error with the Stripped Maple Log.
- Retextured the Cypress Log.
- Geysers now use an emissive top face in 1.21.4+.
- Retextured the Penguin Spawn Egg in 1.21.5+.
- Retextured the Ostrich Spawn Egg in 1.21.5+.

# Music
- Removed the internal .zip resource pack containing Wilder Wild's music.
  - This has been reverted to the original implementation, including the config option and resource pack to disable Wilder Wild's music playing in the title screen and while under water.
  - C418's music has once again been removed.
  - A separate .zip resource pack is now downloaded in the background upon boot.
    - The downloaded .zip can be found in the `/frozenlib/downloaded_resourcepacks` folder within Minecraft's run directory.
    - A toast will be displayed instructing the player to reload the game's resources once the pack has been downloaded.
      - Resource pack downloading can be disabled via FrozenLib's config.
      - This change was made to minimize the amount of file space Wilder Wild takes up, cutting it down by roughly two-thirds of its size.
        - This makes Wilder Wild more friendly to download for users with slower internet connections as its music will only be downloaded once, while also removing unnecessary file space on servers.
- Added missing translation strings for Wilder Wild's music in 1.21.6+.

# World Generation
- Renamed the `wilderwild:feature/has_field_flowers` biome tag to `wilderwild:feature/has_flower_field_flowers.`
  - The `wilderwild:feature/has_flower_forest_flowers` biome tag has been added, differentiating Flower Field and Flower Forest flower generation.
- Updated flower generation in Flower Fields and Flower Forests, being more visually pleasing.
  - Flower generation in Flower Forests is now more even and sparse than that of Flower Fields.
- Toned down flower generation in Tundras.
- Leaf Litters of every leaf type now generate.
  - Azalea and Cherry Leaf Litters do not generate naturally, but can still be obtained by smelting their leaves block.
  - This change was made to establish stronger consistency between Vanilla and Wilder Wild.
- Added config options to toggle the generation of every Leaf Litter type.
  - This does not apply to Maple Leaf Litters.
- Leaf Litter patch features no longer get placed over blocks such as Sand or Gravel.
  - This change was made to improve the aesthetic of Beta Beaches.
- Significantly cleaned up feature datagen in 1.21.5+.

# Entities
- Moobloom variants are now selected based upon the flower features in the biome.
  - As a result of this, all biome tags relating to Moobloom variants have been removed.
- Mooblooms will now prefer to walk towards flowers of the same type as themselves.
- Fixed an issue where feeding a Moobloom to regrow its flowers would not make the play swing their arm in 1.21.2+.
- Butterflies will now prefer moving towards flowers slightly more.
- Updated mob variant selection on 1.21.6+ to be consistent with Vanilla.
  - This change does not apply to Mooblooms.
- Fireflies can now hide in frozen vegetation blocks.
- Fireflies can now hide in Bushes in 1.21.5+.
- Reworked Firefly spawning in 1.21.5+, making it less likely for Fireflies to all pool up in one place.
- Added a config option in 1.21.5+ to dictate whether Fireflies can spawn wherever a Firefly Bush is.
  - If disabled, Fireflies can only spawn in Swamp-like biomes.
- Fixed Penguins not counting towards the `Flightless Flight` advancement.

# Items
- The Milkweed pod now has a cooldown of two seconds.
- Added unique sounds for blowing a Milkweed Pod.
- Using a Milkweed Pod now properly awards the statistic for item usage.
- Ender Pearls no longer play two teleportation sounds upon landing.
- Removed yet another internal reference to the Ancient Horn. Begone. Your time has long passed.

# Misc.
- Increased Wilder Wild's protocol version to 15.
