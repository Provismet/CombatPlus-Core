<div align="center">

# Combat+ Core
[![](https://img.shields.io/jitpack/version/com.github.Provismet/combatplus-core?style=flat-square&logo=jitpack&color=F6F6F6)](https://jitpack.io/#Provismet/combatplus-core)

</div>

This is the core API and library mod for the Combat+ mod series, allowing inter-mod compatibility without requiring each mod to know of the others' existence.

This mod makes no changes to the vanilla game, it only provides hooks, interfaces, and implementations for other mods to add their content.

## Implementation Summary
- The vanilla call to `EnchantmentHelper.getAttackDamage` has been redirected to the `CPCEnchantmentHelper.getAttackDamage` for Players and mobs. This is necessary due to how limited the vanilla enchantment system is.
  - This should not cause incompatibilities with most mods because the redirected method acts as a wrapper for the vanilla equivalent. The vanilla version is still called.
- Adds two interfaces for melee weapons. `MeleeWeapon` and `DualWeapon`. The latter extends the former.
- Adds two item tags: `combat-plus:melee_weapon` and `combat-plus:dual_weapon`. If you want to add compatibility without depending on this mod, then use these tags.
  - `combat-plus:melee_weapon` subsumes `combat-plus:dual_weapon`, there is no need to add the same item to both tags.
  - Items in these tags should be treated as honorary `MeleeWeapon` and `DualWeapon` items.
  - Items that implement the interfaces do not need to be in these tags, but should be added regardless for consistency.
- Adds two EnchantmentTargets, both found in the `CPCEnchantmentTargets` class.
  - The `CPCEnchantmentTargets.MELEE_WEAPON` target subsumes the vanilla `EnchantmentTarget.WEAPON` target. Anything that qualifies for the former will qualify for the latter (which is only swords in vanilla).
  - The `MELEE_WEAPON` and `DUAL_WEAPON` enchantment targets also check their respective tags.
- Swords implement `DualWeapon`.
- Axes implement `MeleeWeapon`.

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
  modImplementation 'com.github.Provismet:combatplus-core:0.1.0-mc1.20.1'
}
```
The version number may optionally be replaced by `${project.combatplus_version}` so long as this variable is defined in your gradle.properties file.
