package com.provismet.CombatPlusCore.items;

import com.provismet.CombatPlusCore.interfaces.BlockingItem;
import net.minecraft.world.item.ShieldItem;

public abstract class AbstractShieldItem extends ShieldItem implements BlockingItem {
    public AbstractShieldItem (Properties settings) {
        super(settings);
    }
}
