Please clear changelog after each release.
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED.
-----------------
- Crabs no longer freeze in place and jitter when attempting to climb blocks such as Mud.
- Crabs no longer freeze in place and jitter when attempting to climb blocks underwater.
- Crabs no longer attempt to "cling" to blocks while descending underwater.
- Fixed Penguin Eggs not having a loot table.
- Fixed Hollowed Crimson Stems and Hollowed Warped Stems incorrectly being in the `wilderwild:hollow_logs_that_burn` item tag. ([#513](https://github.com/FrozenBlock/WilderWild/issues/513))
- Fixed a crash on 1.21.5+ when using Bone Meal in a Jungle biome. ([#510](https://github.com/FrozenBlock/WilderWild/issues/510))
- Fixed the new Lightning Rod blocks being missing from the `wilderwild:no_lightning_block_particles` and `wilderwild:no_lightning_smoke_particles` block tags on 1.21.9+. ([#511](https://github.com/FrozenBlock/WilderWild/issues/511))
- Fixed the rendering of Dripping Mesoglea particles on 1.21.9+.

### 25w42a+
- Revamped the implementation used to switch between the regular Water fog and Mesoglea fog colors.
- Renamed `wilderwild:blue_pearlescent_mesoglea` to `wilderwild:pearlescent_blue_mesoglea`.
- Renamed `wilderwild:purple_pearlescent_mesoglea` to `wilderwild:pearlescent_purple_mesoglea`.
- Renamed `wilderwild:blue_pearlescent_nematocyst` to `wilderwild:pearlescent_blue_nematocyst`.
- Renamed `wilderwild:purple_pearlescent_nematocyst` to `wilderwild:pearlescent_purple_nematocyst`.
- Renamed all Mesoglea-related particles and their textures in order to improve both organization and consistency.
- Reworked how Dripping Mesoglea particles spawn, now using a mixin on vanilla code to be more consistent with Dripping Water's implementation.
- Cleaned up Wilder Wild's biome injection implementation.
