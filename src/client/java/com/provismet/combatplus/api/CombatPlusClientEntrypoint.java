package com.provismet.combatplus.api;

/**
 * Custom client-only entrypoint for Combat+ Core. Called after all client initialisation is complete.
 *
 * @apiNote Combat+ Core does not initialise anything on the client entrypoint, so this entrypoint is called immediately.
 */
public interface CombatPlusClientEntrypoint {
    void onInitializeClient ();
}
