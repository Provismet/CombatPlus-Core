package com.provismet.combatplus.api.item;

import com.provismet.combatplus.api.item.interfaces.BlockingItem;
import net.minecraft.world.item.ShieldItem;

/**
 * Convenience class for creating shields.
 *
 * @apiNote This class currently adds nothing. Instances of this class are subject to Minecraft's custom shield rendering.
 */
public abstract class AbstractShieldItem extends ShieldItem implements BlockingItem {
    public AbstractShieldItem (Properties settings) {
        super(settings);
    }
}
