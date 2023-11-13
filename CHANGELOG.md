Please clear changelog after each release.
Thank you!

-----------------
Additions
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
        - There is, however, a config option to let this impact attack reach.

Bug Fixes & Other Changes
---

- Milkweed now plays a sound when rustled
- Milkweed can now be sheared with a Dispenser
- Glory of the Snow can now be sheared with a Dispenser
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
- Hopefully resolved world generation issues with C2ME ([#311](https://github.com/FrozenBlock/WilderWild/issues/311))
- Refactored multiple classes to improve internal organization

Wilder Wild 2.2 Newsletter - *Luna*
---

Hey, all! This is Luna/AViewFromTheTop here. :)

I decided to start this "newsletter" of sorts as an easier way to communicate with you all, and to share things that I (or Treetrain, if he decides to write as well) would like our community to look out for and whatnot.

First order of business, I'm hoping I've just been able to fix ([#311](https://github.com/FrozenBlock/WilderWild/issues/311)), The issue where C2ME would sometimes cause crashes while generating a Deep Dark biome. I'd like for you all to test if this issue has actually been fixed, and if not, to create a new [issue for it on our GitHub page](https://github.com/FrozenBlock/WilderWild/issues).

Aside from that, I'd appreciate if you all could give us feedback on Crabs! They've been quite troublesome to make and I'm sure I wasn't able to incorporate every idea possible into them, so I'd like to know what you think could be improved upon or what may be missing. On that note, help from other modders to get their rotation and navigation working flawlessly would be very much appreciated!

The last thing I'd like to mention is that we're open to new sound designers and texture artists! I would prefer to have a few of each so we don't have to pawn all our requests on to one person, and to allow for more flexibility and looser deadlines in our team. It would also be beneficial to be able to review multiple takes on one idea, if need be!

-Luna.
