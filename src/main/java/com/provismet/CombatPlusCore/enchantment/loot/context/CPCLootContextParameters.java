package com.provismet.CombatPlusCore.enchantment.loot.context;

import com.provismet.CombatPlusCore.CPCMain;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;


public class CPCLootContextParameters {
    public static final ContextKey<Entity> TARGET_ENTITY = register("target_entity");

    private static <T> ContextKey<T> register (String name) {
        return new ContextKey<>(CPCMain.identifier(name));
    }
}
