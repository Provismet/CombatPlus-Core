package com.provismet.CombatPlusCore.api.item;

import com.provismet.CombatPlusCore.api.item.interfaces.BlockingItem;
import net.minecraft.world.item.ShieldItem;

public abstract class AbstractShieldItem extends ShieldItem implements BlockingItem {
    public AbstractShieldItem (Properties settings) {
        super(settings);
    }
}
