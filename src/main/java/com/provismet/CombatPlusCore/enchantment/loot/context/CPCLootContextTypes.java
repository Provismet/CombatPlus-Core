package com.provismet.CombatPlusCore.enchantment.loot.context;

import com.provismet.CombatPlusCore.CPCMain;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class CPCLootContextTypes {
    public static final LootContextType DOUBLE_ENTITY = register("double_entity", builder ->
        builder.require(LootContextParameters.THIS_ENTITY)
            .require(LootContextParameters.ATTACKING_ENTITY) // In the context of Combat+, the above two are the same for callbacks.
            .require(CPCLootContextParameters.TARGET_ENTITY)
            .require(LootContextParameters.ENCHANTMENT_LEVEL)
            .require(LootContextParameters.ORIGIN)
            .require(LootContextParameters.TOOL)
    );

    public static final LootContextType SINGLE_ENTITY = register("single_entity", builder ->
        builder.require(LootContextParameters.THIS_ENTITY)
            .require(LootContextParameters.ENCHANTMENT_LEVEL)
            .require(LootContextParameters.ORIGIN)
            .require(LootContextParameters.TOOL)
    );

    private static LootContextType register (String name, Consumer<LootContextType.Builder> type) {
        LootContextType.Builder builder = new LootContextType.Builder();
        type.accept(builder);
        LootContextType lootContextType = builder.build();
        Identifier identifier = CPCMain.identifier(name);
        LootContextType lootContextType2 = LootContextTypes.MAP.put(identifier, lootContextType);
        if (lootContextType2 != null) {
            throw new IllegalStateException("Loot table parameter set " + String.valueOf(identifier) + " is already registered");
        }
        return lootContextType;
    }
}
