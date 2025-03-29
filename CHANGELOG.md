Please clear changelog after each release.
Thank you!
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED
hi
-----------------
- Added Biolith integration, thanks to Rebel459! ([#463](https://github.com/FrozenBlock/WilderWild/pull/463))
- Fixed many of Wilder Wild's features not generating, or generating in unintended ways.
  - This primarily impacted surface transition features, which would not generate at all.
- Tall Grass and Large Ferns are now consistently converted to their snowy equivalents in worldgen.
- Added the `getNonSnowyEquivalent` and `replaceWithNonSnowyEquivalent` methods to `SnowyBlockUtils.`
- Wilder Wild's loot table modifications should now impede less upon chances of obtaining certain Vanilla loot.
- Added sounds for hydrothermal vents.
- Fixed Chest Boats and Rafts crashing on 1.21.2+.
- Termite Mounds, Display Lanterns, and Sculk Sensors now sync their data to the client more similarly to Vanilla's method of doing so.
- Fixed Firefly and Butterfly Bottles playing the release sound using the incorrect sound category.
- Fixed Sheep being unable to eat Short Grass.
- Fixed config fields in the `structure` sub-category of the `worldgen` config incorrectly trying to sync to the `transitionGeneration` sub-category.
- Removed the `echo_glass` mixin package and merged its contents into existing mixins in the `block_break` mixin package.
- Removed `EntityMixin` from the `tumbleweed` mixin package, and implemented its functionality into Tumbleweed itself.
- Removed Serene Seasons-related tags.
  - Serene Seasons integration, including fixes for Snowlogging, will be found in a separate mod titled `Serene Wild.`
