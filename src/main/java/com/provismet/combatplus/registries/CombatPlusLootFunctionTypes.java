package com.provismet.combatplus.registries;

import com.mojang.serialization.MapCodec;
import com.provismet.combatplus.CombatPlusCoreMain;
import com.provismet.combatplus.api.loot.functions.EnchantRandomlyFromKeyLootFunction;
import com.provismet.combatplus.api.loot.functions.EnchantRandomlyFromTagLootFunction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

public interface CombatPlusLootFunctionTypes {
    static void register () {
        register("enchant_from_tag", EnchantRandomlyFromTagLootFunction.CODEC);
        register("enchant_from_keys", EnchantRandomlyFromKeyLootFunction.CODEC);
    }

    private static <T extends LootItemFunction> void register (String name, MapCodec<T> codec) {
        Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, CombatPlusCoreMain.identifier(name), codec);
    }
}
