package com.provismet.combatplus.api.loot.context;

import com.provismet.combatplus.CombatPlusCoreMain;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;


public class CombatPlusLootContextParameters {
    public static final ContextKey<Entity> TARGET_ENTITY = register("target_entity");

    private static <T> ContextKey<T> register (String name) {
        return new ContextKey<>(CombatPlusCoreMain.identifier(name));
    }
}
