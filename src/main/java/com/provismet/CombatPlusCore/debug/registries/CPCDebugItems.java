package com.provismet.CombatPlusCore.debug.registries;

import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.debug.items.DebuggerItem;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.Optional;

public class CPCDebugItems {
    private static final RegistryKey<Item> DEBUGGER_KEY = RegistryKey.of(RegistryKeys.ITEM, CPCMain.identifier("debugger"));
    private static final Item DEBUGGER = new DebuggerItem(new Item.Settings().maxCount(1).enchantable(1).registryKey(DEBUGGER_KEY).attributeModifiers(DebuggerItem.createSimpleAttributes()));

    public static void register () {
        Registry.register(Registries.ITEM, DEBUGGER_KEY, DEBUGGER);
    }

    public static Optional<Item> getOptionalDebugItem () {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) return Optional.of(DEBUGGER);
        else return Optional.empty();
    }
}
