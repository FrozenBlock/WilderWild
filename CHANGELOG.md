Please clear changelog after each release.
Thank you!

-----------------
Added the Ostrich
- Is tamed the same way as Horses, but takes a bit longer due to their feistier nature
- Instead of jumping, will attack with its beak depending on how strong the meter was charged
    - If its beak hits a softer block while trying to attack, it may get stuck temporarily
    - Added the `wilderwild:ostrich_beak_buryable` tag to control which blocks these are, currently containing the tags `minecraft:mineable/shovel,` `minecraft:mineable/hoe,` and `minecraft:wool`
    - Added a new `Ostrich Peck Attack` config option to control whether or not players can use the Ostrich's attack
- Can be bred with Bushes
    - Will lay an egg upon breeding
    - This egg does not require Silk Touch to be obtained
- Naturally spawns in the Savanna, Savanna Plateau, and Windswept Savanna biomes
    - Added the `wilderwild:has_ostrich` biome tag to control the biomes it spawns in
    - Added the `Spawn Ostriches` config option to control whether or not Ostriches will spawn naturally
- If hurt and not tamed, will chase after their attacker and peck them to death
    - If they are tamed, this behavior will only apply to other mobs
    - Will call other Ostriches for backup
    - While provoked, cannot be ridden or fed unless it's already tamed
    - If tamed and fed or ridden while provoked, will calm down

- Added the Dying Forest - A fall-based luxury for all fall enjoyers! I think.
    - Contains many dying trees, dead trees, semi-dead trees, fallen trees, and a few alive trees, with slightly darker colors compared to a regular Forest
    - All regular Forest flowers grow here
    - Seeding Dandelions are more common in this biome
    - Large mushrooms will generate here as they do in regular Forests
    - Bushes and Dead Bushes grow frequently here
    - Pumpkins will generate frequently here
    - Coarse Dirt mounds will generate here as well
    - Coarse Dirt and Podzol paths line the ground of this biome

- Added the Snowy Dying Forest - It got frostbite and died!
    - Contains only dead trees, semi-dead trees and fallen trees, also with slightly darker colors compared to a regular Forest
    - The only flower that grows here are Seeding Dandelions
    - Dead Bushes grow in this biome. Maybe "grow" isn't the right word...
    - Coarse Dirt mounds will also generate here

- Added the Dying Mixed Forest - I couldn't think of anything for this one!
    - Contains the same dying, dead, semi-dead, and fallen trees as the Dying Forest, now with some very alive Spruces thrown in!
    - Generates all the regular Mixed Forest flowers
    - Seeding Dandelions are more common in this biome
    - Bushes and Dead bushes have also found a home here
    - Pumpkins will also generate frequently in this biome
    - Coarse Dirt mounds make their third grand return in this biome
    - Coarse Dirt and Podzol Paths line the ground of this biome as well

- Added the Snowy Dying Mixed Forest - I, too, would have mixed feelings if I were dying of frostbite.
    - Consists of only dead, semi-dead, fallen, and Spruce trees.
    - The only flowers present here are Seeding Dandelions
    - Dead Bushes will generate here
    - Coarse Dirt mounds make their fourth and final grand return in this biome

- Added codecs to as many blocks as possible (1.20.5+)
    - This has resulted in some blocks receiving new constructor parameters
- Added the Bush, Tumbleweed, Tumbleweed Stem, Milkweed, Datura, Cattail, Flowering Lily Pad, Algae, Shelf Fungus, Small Sponge, Prickly Pear Cactus, and Nematocyst blocks to the `minecraft:sword_efficient` tag
- Defined the Random Sequence for every one of Wilder Wild's loot tables
- Potted Small Dripleaves now use their loot table (renamed `wilderwild:blocks/potted_small.dripleaf` to `wilderwild:blocks/potted_small_dripleaf`)
- Reimplemented the custom Warden dying sound after it was accidentally removed during a bugfix
- The Warden's regular death sound will now play on top of its underwater dying sound once again
- Wardens are no longer considered alive during the custom death animation
- The Warden will no longer play heartbeat sounds while it is dying
- Prevented the Warden from moving while it is digging or emerging
- Changed the Warden's secret death sound from stereo to mono
- Refactored mobs' `canSpawn` methods to reflect Vanilla, following the template "checkXSpawnRules"
- Pumpkins generate much more frequently in Old Growth Dark Forests
- Changed the `Cherry Grove Placement` config to be disabled by default as it wasn't as immersive as anticipated
- Fixed the `Stony Shore Placement` config lang reading as `CStony Shore Placement`
- Added the Arid Forest, Arid Savanna, and Oasis biomes to the `minecraft:snow_golem_melts` tag
- Added the Snowy Dying Forest, Snowy Dying Mixed Forest, and Snowy Old Growth Pine Taiga biomes to the `minecraft:spawns_snow_foxes` tag
- Added the Snowy Dying Forest, Snowy Dying Mixed Forest, and Snowy Old Growth Pine Taiga biomes to the `minecraft:spawns_white_rabbit` tag
- Added the Palm Hanging Sign to the `wilderwild:hanging_signs` item tag
- Added the Palm Crown to the `wilderwild:palm_logs` item tag
- Baobab Nuts and Palm Fronds are now compostable
- Decreased the amount of String obtained crafting with Cattails from 3 to 1
- Echo Glass will now drop itself upon breaking due to cracking, only if not hit by a Sonic Boom
- Removed the `Goat Horn Symphony` and `Back` discs as they don't feel necessary nor do they fit the game
    - These will be datafixed into Lena Raine's `Otherside` and Samuel Åberg's `5` respectively
- Updated the protocol version to 3
