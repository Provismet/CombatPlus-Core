package com.provismet.CombatPlusCore.debug.registries;

import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.debug.items.DebuggerItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class CPCDebugItems {
    private static final Item DEBUGGER = new DebuggerItem(new Item.Settings().maxCount(1).attributeModifiers(DebuggerItem.createSimpleAttributes()));

    public static void register () {
        Registry.register(Registries.ITEM, CPCMain.identifier("debugger"), DEBUGGER);
    }
}
