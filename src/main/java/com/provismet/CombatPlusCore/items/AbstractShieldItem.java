package com.provismet.CombatPlusCore.items;

import com.provismet.CombatPlusCore.interfaces.BlockingItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;

public abstract class AbstractShieldItem extends ShieldItem implements BlockingItem {
    public AbstractShieldItem (Settings settings) {
        super(settings);
    }

    @Override
    public int blockChargeTicks (ItemStack itemStack) {
        return 5;
    }
}
