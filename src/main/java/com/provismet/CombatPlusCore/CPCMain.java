package com.provismet.CombatPlusCore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class CPCMain implements ModInitializer {
    public static final String MODID = "combat-plus";
    public static final Logger LOGGER = LoggerFactory.getLogger("Combat+ Core");

    public static Identifier identifier (String path) {
        return Identifier.of(MODID, path);
    }

    @Override
    public void onInitialize () {
        
    }
    
}