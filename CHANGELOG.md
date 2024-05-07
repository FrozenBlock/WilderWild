Please clear changelog after each release.
Thank you!
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED

-----------------
- Added new config options to control the Ancient Horn's cooldowns in different scenarios.
  - `Default Cooldown`
  - `Creative Cooldown`
  - `Sculk Sensor Cooldown`
  - `Sculk Shrieker Cooldown`
  - `Hanging Tendril Cooldown`
- Geysers will now put out Fires, Campfires, and Candles upon Erupting without Lava, and ignite them upon Erupting with Lava. ([#372](https://github.com/FrozenBlock/WilderWild/issues/372))
- Fixed the length of Geyser Eruptions, now being 5 instead of 4.
- Fixed many blocks rendering incorrectly on servers using the new `Server Compat Mode` config option in the `Block` tab.
  - Must be switched on for this fix to be enabled, and requires the game to be restarted upon changing.
- Added better support for Snowlogging on Embeddium thanks to embeddedt!
  - Snowlogging on Embeddium will no longer result in Z-Fighting with Pink Petals.
- Cloud movement no longer uses separate mixins for Embeddium and Sodium, also thanks to embeddedt!
- Updated Scorched Eye and Fermented Scorched Eye textures.
