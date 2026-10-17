package com.provismet.CombatPlusCore.registries;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.loot.functions.EnchantRandomlyFromKeyLootFunction;
import com.provismet.CombatPlusCore.loot.functions.EnchantRandomlyFromTagLootFunction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public abstract class CPCLootFunctionTypes {
    public static final LootItemFunctionType<EnchantRandomlyFromTagLootFunction> ENCHANT_FROM_TAG = register("enchant_from_tag", EnchantRandomlyFromTagLootFunction.CODEC);
    public static final LootItemFunctionType<EnchantRandomlyFromKeyLootFunction> ENCHANT_FROM_KEY = register("enchant_from_keys", EnchantRandomlyFromKeyLootFunction.CODEC);

    public static void init () {}

    private static <T extends LootItemFunction> LootItemFunctionType<T> register (String name, MapCodec<T> codec) {
        return Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, CPCMain.identifier(name), new LootItemFunctionType<>(codec));
    }
}
