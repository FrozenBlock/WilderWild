Please clear changelog after each release.
Thank you!

-----------------
TODO:
- Finish dying forest
- Add snowy dying forest
- Add dying trees into normal biomes (more around clearings)
- Add dying mixed forest
- Add dying snowy mixed forest

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


- Added the Bush, Tumbleweed, Tumbleweed Stem, Milkweed, Datura, Cattail, Flowering Lily Pad, Algae, Shelf Fungus, Small Sponge, Prickly Pear Cactus, and Nematocyst blocks to the `minecraft:sword_efficient` tag
- Defined the Random Sequence for every one of Wilder Wild's loot tables
- Potted Small Dripleaves now use their loot table (renamed `wilderwild:blocks/potted_small.dripleaf` to `wilderwild:blocks/potted_small_dripleaf`)
- Reimplemented the custom Warden dying sound after it was accidentally removed during a bugfix
- The Warden's regular death sound will now play on top of its underwater dying sound once again
- Wardens are no longer considered alive during the custom death animation
- The Warden will no longer play heartbeat sounds while it is dying
- Prevented the Warden from moving while it is digging or emerging
- Changed the Warden's secret death sound from stereo to mono
- Refactored mob's `canSpawn` methods to reflect Vanilla, following the template "checkXSpawnRules"
