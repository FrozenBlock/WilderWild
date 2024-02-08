Please clear changelog after each release.
Thank you!

Additions
-----------------
  - Added snowlogging to some blocks, namely 1-block tall plants, Bushes, Fences, Fence Gates, Walls, Shelf Fungi, Vines, and Sugar Cane.
    - Added the `Allow Snowlogging,` `Snowlog Blockades,` and `Natural Snowlogging` config options.
      - To prevent long load times due to excessive blockstates, `Snowlog Blockades` is off by default.

Bug Fixes & Changes
---

## Breezes, Wind Charges, and Wind
   - Tweaked how wind interacts with Pollen and Seed particles to be more obvious
   - Breezes and Wind Charges now interact with the Wind System
    - They will now affect particles, Tumbleweed, and Fireflies

  - The `wilderwild:small_sponge_grows_on` tag now includes the `wilderwild:mesoglea` tag instead of only the two Pearlescent Mesoglea types.
  - Restricted the placement of multiple features, so they will no longer generate in unwanted places, like on top of structures.
    - Added the `wilderwild:fallen_tree_placeable` tag to control where Fallen Trees can generate.
  - Refactored the `BubbleDirection,` `FlowerColor,` and `SlabWillStairSculkBehavior` classes to the `block.impl` package.
  - The `New Cactus Placement` config is now set to off by default instead of on.
  - Changed the Oasis biome's generation to not take up as much space in Deserts.

2.3.1
---
  - Fixed Shelf Fungi crashing the game upon being interacted with on 1.20.2 and 1.20.1.
  - Removed the sound events for the previously removed discs.
  - Ostrich attacks can now damage and break Armor Stands.
  - Fixed the death message from an Ostrich's attack not displaying properly.
