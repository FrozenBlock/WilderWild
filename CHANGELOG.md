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

Bug Fixes & Changes
---

- Tweaked leaf decay distance to 10 in order to accommodate for Palm Fronds, so they now work like regular leaves
    - Other leaf types will still cap out at 7, but can be changed with commands or a Debug Stick to go up to 10
- Removed the custom distance detection from Palm Crowns
- Added Palm Crowns to tags it was missing from
- Reworked how Coconuts are placed on Palms, so they should no longer break upon generation
- Added PalmTreeFeature to handle Palms' unique Coconut growth and Frond distance detection
- Added a translation string for Coconut projectiles
- Fixed compatibility between Wilder Wild's Stripped Logs and Create

- Milkweed now plays a sound when rustled
- Milkweed can now be sheared with a Dispenser
- Glory of the Snow can now be sheared with a Dispenser
- Prickly Pear's Damage Source has been added to the bypasses_armor tag
- Tumbleweed entities will now drop themselves if killed by any entity with Silk Touch, instead of only doing so when killed by a player

- Fixed Fireflies with the Nectar easter egg not properly rendering their glowing overlay
- Fixed Firefly Bottles with the Nectar easter egg not using the Nectar texture
- Increased the chances of a flickering Firefly spawning (smooth pulsing vs. flickering)
- Changed how flickering Fireflies calculate their colors ([#316](https://github.com/FrozenBlock/WilderWild/issues/316))

- Display Lanterns will now interact with Redstone Comparators, outputting a full signal if they have an item inside, or a signal based upon how many Fireflies are placed inside
- Fixed an issue pertaining to Silk Touch with the Display Lantern's loot table
- Display Lanterns will now properly spawn Fireflies that were held inside of them when broken

- Fixed Jellyfish no longer spawning in Jellyfish Caves
- Slightly optimized Jellyfish rendering with the rainbow easter egg
- Reworked how clipping works with Waterlogged Mesoglea, as it will now apply to all entities as opposed to just players as is much more stable
    - This comes alongside BlockGetterMixin having much cleaner code, and the removal of IN_MESOGLEA in WilderSharedConstants

- Osseous Sculk will now cover their branches in Sculk Veins when its base is converted to Sculk
- Increased the chances of larger Osseous Sculk generating during worldgen
- Sculk Shriekers will now only create Sculk Bubbles underwater if the Shrieker Gargling config is enabled
- Stone Chests will now interact with Redstone Comparators, outputting a signal based on how high their lid is lifted ([#319](https://github.com/FrozenBlock/WilderWild/issues/319))
- Echo Glass now occludes Vibrations
- Added a config option to control whether or not the Warden should be able to swim
  - This config option should now result in all Warden changes becoming toggleable
- The Warden's death particles will now only spawn at the Warden's position as opposed to spawning within its entire hitbox

- Landing/Potion sound code will no longer attempt to run on the client
- Landing Ender Pearls will now play sounds in their owner's sound category (Zombies would be Hostile, and Players would be Player)

- The config will now sync between server and client
    - Operators of servers will modify the server's config upon modifying it on their end
    - Non-operators will see that config options that don't solely pertain to the client will be blocked out and set to the server's value
- Added a "New Frosted Ice Cracking" config option to control Frosted Ice's updated cracking (playing sounds and spawning particles)
- Added a config option for whether or not Dripleaves will use updated Redstone powering functionality
- Swapped the placement of the Misc and Worldgen config tabs

- Refactored many aspects Wilder Wild's configured features' field names to be more consistent with Vanilla
- Removed LeavesAroundTopTreeDecorator
- Removed additional Plains flowers from Meadows
- Increased the amount of flowers that typically generate in Meadows

- Removed the Fabric Loader version requirement in hopes it will fix issues with Quilt
- Changed the grammar of Wilder Wild's advancements to align with Vanilla's
- Fixed compatibility with Roughly Enough Resources ([#315](https://github.com/FrozenBlock/WilderWild/issues/315))
- Hopefully resolved world generation issues with C2ME ([#311](https://github.com/FrozenBlock/WilderWild/issues/311))
- Instrument items will now properly play on servers instead of immediately cutting out ([#284](https://github.com/FrozenBlock/WilderWild/issues/284))
- Refactored, cleaned up, and reworked multiple classes and mixins to improve internal organization, stability, and code integrity

Wilder Wild 2.2 Newsletter - *Luna*
---

Hey, all! This is Luna/AViewFromTheTop here. :)

I decided to start this "newsletter" of sorts as an easier way to communicate with you all, and to share things that I (or Treetrain, if he decides to write as well) would like our community to look out for and whatnot.

First order of business, I'm hoping I've just been able to fix ([#311](https://github.com/FrozenBlock/WilderWild/issues/311)), The issue where C2ME would sometimes cause crashes while generating a Deep Dark biome. I'd like for you all to test if this issue has actually been fixed, and if not, to create a new [issue for it on our GitHub page](https://github.com/FrozenBlock/WilderWild/issues).

Aside from that, I'd appreciate if you all could give us feedback on Crabs! They've been quite troublesome to make and I'm sure I wasn't able to incorporate every idea possible into them, so I'd like to know what you think could be improved upon or what may be missing. On that note, help from other modders to get their rotation and navigation working flawlessly would be very much appreciated!

Crabs work a bit like Wardens, by the way. They can detect specific game events while hiding underground, which will cause them to emerge. They will *always* emerge if a Player is within 4 blocks of them, however. Projectiles landing, explosions, blocks breaking, etc. will cause a Crab to emerge.
So why didn't I make "lower" vibrations trigger this? As a matter of fact, it's based both on fact and for gameplay reasons. Crabs hardly have good enough hearing and mostly depend on pressure changes to tell where things are, if I remember correctly. While researching this I came across someone who found a Crab would immediately react to someone jumping, but wouldn't budge with music blasting directly towards it.
Now, I understand how the assortment of Vibrations I chose for the Crab to react to may be omitting some relatively logical options, though, I personally believe there is substantial enough reason to do so on the gameplay side of things. I want Crabs to have a balance between feeling sneaky, ruthless, yet innocent and still maintaining a naive nature. I don't want them emerging if they can tell they wouldn't be in possible immediate danger!
I almost forgot to mention, too, this behavior was inspired by the Sheargrubs in Pikmin. I want Crabs to occassionally surprise the player when they suddenly emerge, you know? Scary.

The last thing I'd like to mention is that we're open to new sound designers and texture artists! I would prefer to have a few of each so we don't have to pawn all our requests on to one person, and to allow for more flexibility and looser deadlines in our team. It would also be beneficial to be able to review multiple takes on one idea, if need be!

-Luna.
