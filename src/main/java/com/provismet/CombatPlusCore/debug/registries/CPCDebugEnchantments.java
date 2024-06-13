package com.provismet.CombatPlusCore.debug.registries;

import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.debug.enchantments.LoggerEnchantment;
import com.provismet.CombatPlusCore.utility.CPCItemTags;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class CPCDebugEnchantments {
    private static final Enchantment LOGGER = new LoggerEnchantment(Enchantment.properties(CPCItemTags.DAMAGE_ENCHANTABLE, 1, 1, Enchantment.constantCost(1), Enchantment.constantCost(1), 1, EquipmentSlot.MAINHAND));

    public static void register () {
        Registry.register(Registries.ENCHANTMENT, CPCMain.identifier("logger"), LOGGER);
    }
}
