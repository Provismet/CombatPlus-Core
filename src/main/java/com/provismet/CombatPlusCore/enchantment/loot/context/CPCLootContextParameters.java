package com.provismet.CombatPlusCore.enchantment.loot.context;

import com.provismet.CombatPlusCore.CPCMain;
import net.minecraft.entity.Entity;
import net.minecraft.util.context.ContextParameter;


public class CPCLootContextParameters {
    public static final ContextParameter<Entity> TARGET_ENTITY = register("target_entity");

    private static <T> ContextParameter<T> register (String name) {
        return new ContextParameter<>(CPCMain.identifier(name));
    }
}
