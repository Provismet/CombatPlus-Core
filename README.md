# Combat+ Core

This is the core API and library mod for the Combat+ mod series, allowing inter-mod compatibility without requiring each mod to even know of the others' existence.

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
