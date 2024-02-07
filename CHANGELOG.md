Please clear changelog after each release.
Thank you!

-----------------
Additions
---

  - Added snowlogging to some blocks, namely 1-block tall plants, Bushes, Fences, Fence Gates, Walls, Shelf Fungi, and Vines.
    - Added the `Allow Snowlogging,` `Snowlog Blockades,` and `Natural Snowlogging` config options.
      - To prevent long load times due to excessive blockstates, `Snowlog Blockades` is off by default.

Bug Fixes & Changes
---

  - The `wilderwild:small_sponge_grows_on` tag now includes the `wilderwild:mesoglea` tag instead of only the two Pearlescent Mesoglea types.
  - Restricted the placement of multiple features, so they will no longer generate in unwanted places, like on top of structures.
    - Added the `wilderwild:fallen_tree_placeable` tag to control where Fallen Trees can generate.
  - Refactored the `BubbleDirection,` `FlowerColor,` and `SlabWillStairSculkBehavior` classes to the `block.impl` package.
  - The `New Cactus Placement` config is now set to off by default instead of on.
  - Moved the `Block Sounds` config category to the bottom of the `Block` config.
