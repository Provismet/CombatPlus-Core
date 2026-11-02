package com.provismet.combatplus.mixin;

import com.provismet.combatplus.api.item.interfaces.MeleeWeapon;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin extends Item implements MeleeWeapon {
    public AxeItemMixin (Properties settings) {
        super(settings);
    }
}
