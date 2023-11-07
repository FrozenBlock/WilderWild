Please clear changelog after each release.
Thank you!

-----------------
# Wilder Wild 2.2
## What's new?

---

- ### Added the Crab!
    - Crabs are neutral mobs that spawn in Mangrove Swamps, Warm Oceans, Lukewarm Oceans, Deep Lukewarm Oceans, Oceans, Deep Oceans, Warm Beaches, Beaches, and Cypress Wetlands.
    - If no players are close to a Crab and it's not been disturbed for a while, the Crab will hide in the ground.
    - If a player comes nearby or a loud vibration occurs, the Crab will come back out of hiding.
    - When attacked, the Crab will run towards its attacker and call all other Crabs nearby, even those in hiding, for backup.
    - Baby Crabs cannot attack, and will instead panic.
    - Can be bred with Kelp.
    - Can climb up walls.
    - Can be scooped up in Buckets.
    - Has a chance to drop its Claw upon death (only when killed by a player), which can be cooked.
    - The Crab's Claw can also be brewed with an Awkward potion to create a Potion of Reaching.
- ### Added the Reach Boost effect!
    - Extends the player's reach by 1 block per level.
    - Does not affect attacking, only impacts interactions with Items and Blocks including both placing and breaking.
        - There is, however, a config option to let this impact the attack reach.

## Bug Fixes & Other Changes

---

- Fixed Jellyfish no longer spawning in Jellyfish Caves
- Slightly optimized Jellyfish rendering with the rainbow easter egg
- Added a translation string for Coconut projectiles
- Removed the Fabric Loader version requirement in hopes it will fix issues with Quilt
- Changed the grammar of Wilder Wild's advancements to align with Vanilla's
- Fixed Fireflies with the Nectar easter egg not properly rendering their glowing overlay
- Fixed Firefly Bottles with the Nectar easter egg not using the Nectar texture
- Increased the chances of a flickering Firefly spawning (smooth pulsing vs. flickering)
- Changed how flickering Fireflies calculate their colors ([#316](https://github.com/FrozenBlock/WilderWild/issues/316))
- Fixed Display Lantern silk touch loot table
- Fixed compatibility with Roughly Enough Resources ([#315](https://github.com/FrozenBlock/WilderWild/issues/315))
- Added Palm Crowns into tags it was missing from
- Refactored multiple classes to improve internal organization
