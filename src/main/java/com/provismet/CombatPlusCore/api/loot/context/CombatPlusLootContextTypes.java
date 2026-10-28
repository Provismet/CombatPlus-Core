package com.provismet.CombatPlusCore.api.loot.context;

import com.provismet.CombatPlusCore.CombatPlusCoreMain;
import java.util.function.Consumer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

/**
 * Contains loot context types used by Combat+. Mostly used as conditions in enchantments.
 */
public class CombatPlusLootContextTypes {
    public static final ContextKeySet DOUBLE_ENTITY = register("double_entity", builder ->
        builder.required(LootContextParams.THIS_ENTITY)
            .required(LootContextParams.ATTACKING_ENTITY) // In the context of Combat+, THIS_ENTITY and ATTACKING_ENTITY are the same for callbacks.
            .required(CombatPlusLootContextParameters.TARGET_ENTITY)
            .required(LootContextParams.ENCHANTMENT_LEVEL)
            .required(LootContextParams.ORIGIN)
            .required(LootContextParams.TOOL)
    );

    public static final ContextKeySet SINGLE_ENTITY = register("single_entity", builder ->
        builder.required(LootContextParams.THIS_ENTITY)
            .required(LootContextParams.ENCHANTMENT_LEVEL)
            .required(LootContextParams.ORIGIN)
            .required(LootContextParams.TOOL)
    );

    private static ContextKeySet register (String name, Consumer<ContextKeySet.Builder> type) {
        ContextKeySet.Builder builder = new ContextKeySet.Builder();
        type.accept(builder);
        ContextKeySet lootContextType = builder.build();
        Identifier identifier = CombatPlusCoreMain.identifier(name);
        ContextKeySet lootContextType2 = LootContextParamSets.REGISTRY.put(identifier, lootContextType);
        if (lootContextType2 != null) {
            throw new IllegalStateException("Loot table parameter set " + identifier + " is already registered");
        }
        return lootContextType;
    }
}
