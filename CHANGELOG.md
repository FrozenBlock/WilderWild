Please clear changelog after each release.
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED.
-----------------
# Blocks
- Added unique sounds for Maple Leaves.
- Added unique sounds for the Maple wood set. (WIP)
- Added unique sounds for Auburn and Pale Moss.
  - Added a config option to toggle these new sounds.
- Removed duplicate leaves sounds.
- Fixed Pink Petals incorrectly being assigned a different block SoundType.
- Removed duplicate flower sounds.
- Removed sounds for the Palm Crown, as the block has long-since been removed.
- Updated all Hollowed Log/Stem sounds to be more pleasing to the ears.
- Added unique sounds for Short/Tall Grass, Fern, Bush, and Clover blocks, now sounding much cleaner and less disturbing.
  - Added a config option to toggle these new sounds.
- Fixed underwater Chest closing sounds being stereo instead of mono.
- Fixed Jack O Lanterns not using Wilder Wild's melon sound type.
- Pumpkin and Melon stems now use crop sounds opposed to wood sounds.
- Wilder Wild's Sandstone sounds block tag now uses Fabric's conventional Sandstone block tag.
- Scorched Sand blocks and Tumbleweed now play Desert ambience.
- Desert ambience now plays in the Oasis biome in 1.21.5.
  - Added a `block_ambience` field to Wilder Wild's mixin config.
  - These changes are not present in 1.21.6+ as the biome requirement no longer exists.
- Updated Gabbro Bricks sounds to be more distinct from regular Gabbro blocks.
- Added Gabbro blocks to Sculk-related tags they were missing from.
- Nether Wart, Nether Wart Blocks, and Warped Wart Blocks now kill Termites upon contact.
- Added a few missing Crimson and Warped blocks to the `wilderwild:kills_termite` block tag.
- Fixed a few Pale Oak blocks missing from tags.
- Removed duplicate Stonecutting recipes for Mud Bricks. ([#502](https://github.com/FrozenBlock/WilderWild/issues/502))
- Penguin and Ostrich eggs now take much longer to hatch. ([#501](https://github.com/FrozenBlock/WilderWild/issues/501))

# Textures
- Retextured the Marigold.
- Retextured the Marigold Moobloom.
- Retextured Lantanas.
- Changed the Clovers' textures to be brighter, making them more visible on Grass Blocks.
- Retextured the Penguin Spawn Egg in 1.21.5+.

# Music
- Removed the internal .zip resource pack containing Wilder Wild's music.
  - This has been reverted to the original implementation, including the config option and resource pack to disable Wilder Wild's music playing in the title screen and while under water.
  - C418's music has once again been removed.
- Added missing translation strings for Wilder Wild's music in 1.21.6+.

# World Generation
- Renamed the `wilderwild:feature/has_field_flowers` biome tag to `wilderwild:feature/has_flower_field_flowers.`
  - The `wilderwild:feature/has_flower_forest_flowers` biome tag has been added, differentiating Flower Field and Flower Forest flower generation.
- Updated flower generation in Flower Fields and Flower Forests, being more visually pleasing.
  - Flower generation in Flower Forests is now more even and sparse than that of Flower Fields.
- Toned down flower generation in Tundras.

# Entities
- Moobloom variants are now selected based upon the flower features in the biome.
  - As a result of this, all biome tags relating to Moobloom variants have been removed.
- Mooblooms will now prefer to walk towards flowers of the same type as themselves.
- Butterflies will now prefer moving towards flowers slightly more.
- Updated mob variant selection on 1.21.6+ to be consistent with Vanilla.
  - This change does not apply to Mooblooms.
- Fireflies can now hide in frozen vegetation blocks.
- Fireflies can now hide in Bushes in 1.21.5+.
- Reworked Firefly spawning in 1.21.5+, making it less likely for Fireflies to all pool up in one place.
- Added a config option in 1.21.5+ to dictate whether Fireflies can spawn wherever a Firefly Bush is.
  - If disabled, Fireflies can only spawn in Swamp-like biomes.
- Fixed Penguins not counting towards the `Flightless Flight` advancement.

# Misc.
- Vastly extended the usability of Wilder Wild's Falling Leaf particles for modders in 1.21.5+:
  - Any block can now be given Falling Leaves.
  - Added the `LeafMovementType` enum, used to specify different ways the Falling Leaf should move.
- Removed yet another internal reference to the Ancient Horn. Begone. Your time has long passed.
