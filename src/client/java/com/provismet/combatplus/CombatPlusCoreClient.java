package com.provismet.combatplus;

import com.provismet.combatplus.api.CombatPlusClientEntrypoint;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class CombatPlusCoreClient implements ClientModInitializer {
    @Override
    public void onInitializeClient () {
        FabricLoader.getInstance().getEntrypointContainers(CombatPlusCoreMain.MODID + "-client", CombatPlusClientEntrypoint.class).forEach(
            entrypoint -> {
                String otherModId = entrypoint.getProvider().getMetadata().getId();
                try {
                    entrypoint.getEntrypoint().onInitializeClient();
                }
                catch (Exception e) {
                    CombatPlusCoreMain.LOGGER.error("Mod {} caused an error during inter-mod initialisation: ", otherModId, e);
                }
            }
        );
    }
}
