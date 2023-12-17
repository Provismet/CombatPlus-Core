package com.provismet.CombatPlusCore.enchantments;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;

public class ExtraEnchantmentTarget {
    private List<Class<? extends Item>> targets;

    public static ExtraEnchantmentTarget DualWeapons;

    public ExtraEnchantmentTarget () {
        this.targets = new ArrayList<>();
    }

    public boolean add (Class<? extends Item> itemClass) {
        return this.targets.add(itemClass);
    }

    public boolean accepts (Item item) {
        for (Class<? extends Item> itemClass : this.targets) {
            if (itemClass.isInstance(item)) return true;
        }
        return false;
    }

    public static void init () {
        DualWeapons = new ExtraEnchantmentTarget();
        DualWeapons.add(SwordItem.class);
    }
}
