package com.provismet.CombatPlusCore;

import com.provismet.CombatPlusCore.debug.registries.CPCDebugItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class CPCClient implements ClientModInitializer {
    @Override
    public void onInitializeClient () {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            CPCDebugItems.registerModel();
        }
    }
}
