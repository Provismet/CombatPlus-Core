package com.provismet.CombatPlusCore.registries;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.loot.functions.EnchantRandomlyFromKeyLootFunction;
import com.provismet.CombatPlusCore.loot.functions.EnchantRandomlyFromTagLootFunction;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public abstract class CPCLootFunctionTypes {
    public static final LootFunctionType<EnchantRandomlyFromTagLootFunction> ENCHANT_FROM_TAG = register("enchant_from_tag", EnchantRandomlyFromTagLootFunction.CODEC);
    public static final LootFunctionType<EnchantRandomlyFromKeyLootFunction> ENCHANT_FROM_KEY = register("enchant_from_keys", EnchantRandomlyFromKeyLootFunction.CODEC);

    public static void init () {}

    private static <T extends LootFunction> LootFunctionType<T> register (String name, MapCodec<T> codec) {
        return Registry.register(Registries.LOOT_FUNCTION_TYPE, CPCMain.identifier(name), new LootFunctionType<>(codec));
    }
}
