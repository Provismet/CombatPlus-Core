package com.provismet.CombatPlusCore.api;

/**
 * Custom entrypoint for Combat+ Core.
 * <p>
 * The {@code onInitialize()} method is called once at the end of Combat+ Core's own initialiser.
 * Use this interface if you want to guarantee code to run after Combat+ Core has finished all initialisation.
 * <p>
 * It is also recommended to use this initialiser for mods that offer optional compatibility and cannot guarantee Combat+ Core will be present.
 */
public interface CombatPlusEntrypoint {
    void onInitialize ();
}
