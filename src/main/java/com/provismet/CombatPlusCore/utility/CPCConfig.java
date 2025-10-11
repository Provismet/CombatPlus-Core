package com.provismet.CombatPlusCore.utility;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class CPCConfig {
    @Deprecated
    public static final String FOLDER = "./config/combat-plus";
    public static final Path FOLDER_PATH = FabricLoader.getInstance().getConfigDir().resolve("combat-plus");
    public static final String KEY_OVERRIDE_DATAPACK_LOOT_TABLES = "override_datapack_loot_tables";

    public static Path getConfigDirectory () {
        if (!Files.exists(FOLDER_PATH)) {
            try {
                Files.createDirectories(FOLDER_PATH);
            } catch (IOException e) {
                throw new RuntimeException("Creating Combat+ Core config directory", e);
            }
        }

        return FOLDER_PATH;
    }
}
