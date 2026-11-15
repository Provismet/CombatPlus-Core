Major update, mostly internal, to update to 26.x versions.

## Additions
- `combat-plus:magic_damage` entity attribute.
- `combat-plus:projectile_damage` entity attribute.
- `combat-plus:is_melee` damage type tag (absorbed from Extended Enchanting)
- `combat-plus:is_magic` damage type tag (absorbed from Additional Armoury)
- New damage type tags for elemental damage.
  - `combat-plus:is_elemental`
  - `combat-plus:is_water`
  - `combat-plus:is_earth`
  - `combat-plus:is_wind`
  - `combat-plus:is_light`
  - `combat-plus:is_dark`
  - `combat-plus:is_life`
  - `combat-plus:is_death`
  - `combat-plus:is_spatial`
  - `combat-plus:is_temporal`
  - `combat-plus:is_void`
- Client API initialiser for convenient mod compatibility features.

## Changes
- Renamed all classes prefixed with `CPC` to be prefixed with `CombatPlus`.
  - e.g. `CPCEnchantmentTags` -> `CombatPlusEnchantmentTags` 
- Moved large portions of the codebase into a new API package.
- Damage-type tag renamed: `combat-plus:standard` -> `combat-plus:is_standard`