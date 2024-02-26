<div align="center">

# Combat+ Core
[![](https://img.shields.io/jitpack/version/com.github.Provismet/combatplus-core?style=flat-square&logo=jitpack&color=F6F6F6)](https://jitpack.io/#Provismet/combatplus-core)
[![](https://img.shields.io/modrinth/dt/NbKFMiE7?style=flat-square&logo=modrinth&color=F6F6F6)](https://modrinth.com/mod/combatplus-core)
[![](https://img.shields.io/curseforge/dt/973671?style=flat-square&logo=curseforge&color=F6F6F6)](https://www.curseforge.com/minecraft/mc-mods/combat-plus-core)

<img src="https://github.com/Provismet/CombatPlus-Core/assets/17149901/d7ee8c04-1769-4873-b5a4-8fa23ed26a30" width=250px>

</div>

This is the core API and library mod for the Combat+ mod series, allowing inter-mod compatibility without requiring each mod to know of the others' existence.

This mod makes no changes to the vanilla game, it only provides hooks, interfaces, and implementations for other mods to add their content.

## API
- Adds two interfaces for weapons:
  - `MeleeWeapon`
  - `DualWeapon`
- Adds three interfaces for items:
  - `combat-plus:melee_weapon`
  - `combat-plus:dual_weapon`
  - `combat-plus:breaks_shields`
- Adds an entrypoint initialiser for easier mod compatibility.
- Adds two enchantment targets.
  - Do not use these in dev environments due to FabricASM weirdness.
- Adds utility methods for checking if an item is a Melee or Dual.
- Adds new types of enchantment for better inter-mod compatibility checking:
  - Additional Damage
  - Aspect
  - Weapon Utility
  - Offhand

## Implementation Summary
- The vanilla call to `EnchantmentHelper.getAttackDamage` has been redirected to the `CPCEnchantmentHelper.getAttackDamage` for Players and mobs. This is necessary due to how limited the vanilla enchantment system is.
  - This should not cause incompatibilities with most mods because the redirected method acts as a wrapper for the vanilla equivalent. The vanilla version is still called.
- For both the interfaces and the item tags, Dual Weapons are a subset of Melee Weapons.
- Using the interfaces is best for mod support as they allow callbacks and keep data consistent. Using the tags allows for an item to be an "honorary" melee/dual weapon, these will still be applicable for enchantments.
- Swords implement `DualWeapon`.
- Axes implement `MeleeWeapon`.
- `DamageEnchantment` accepts melee weapons in anvils. (This mirrors the vanilla functionality of Sharpness working on Axes on anvils.)
- Items in the `combat-plus:breaks_shields` tag will _obviously_ break shields.

## Dependency
This mod is available as a dependency on [Jitpack](https://jitpack.io/#Provismet/combatplus-core).

To add this to your mod, add the following to your build.gradle:
```gradle
repositories {
  maven { url "https://jitpack.io" }
}
```

```gradle
dependencies {
  modImplementation "com.github.Provismet:combatplus-core:${project.combatplus_version}"
}
```

Add `combatplus_version` to your gradle.properties file for easier version management. Check the jitpack for the most recent build.
