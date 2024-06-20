package com.provismet.CombatPlusCore.enchantment.loot.context;

import com.provismet.CombatPlusCore.CPCMain;
import net.minecraft.entity.Entity;
import net.minecraft.loot.context.LootContextParameter;


public class CPCLootContextParameters {
    public static final LootContextParameter<Entity> TARGET_ENTITY = register("target_entity");

    private static <T> LootContextParameter<T> register (String name) {
        return new LootContextParameter<>(CPCMain.identifier(name));
    }
}
