Please clear changelog after each release.
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED.
-----------------
- Bumped Wilder Wild's protocol version to 19.
- Termite Mounds now only hold a single Termite Swarm at a time.
  - Termite Mounds placed by players could previously hold three Termite Swarms at a time, which has been observed to be very unbalanced and sound-heavy.
  - This change should make Termite Mounds more acceptable in terms of game balance and volume/particle count.
  - As a result of this change, the `can_spawn_termites` BlockState property has been removed.
    - The `termites_awake` property now takes into account the conditions the `can_spawn_termites` property was previously set under.
- Updated and optimized how Termite Mounds sync Termite Swarm data to clients.
  - Syncing now only occurs if a Termite Swam was spawned, removed, or had its position or eating state changed.
    - Syncing would previously occur each tick while a Termite Swarm was present.
  - Significantly reduced the amount of data required to be sent in order to sync Termite Swarm data to clients.
- Added the `#wilderwild:cannot_support_upwards_termite_movement` Block tag.
  - Contains the `#minecraft:inside_step_sound_blocks`, `#minecraft:replaceable_by_trees`, and `#minecraft:flowers` Block tags by default.
  - This tag is used to define which Blocks a Termite Swarm cannot travel upwards into.
  - This behavior previously used the three tags individually, so there should be no noticeable change for players.
