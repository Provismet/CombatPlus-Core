package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.interfaces.DualWeapon;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SwordItem.class)
public abstract class SwordItemMixin extends Item implements DualWeapon {
    protected SwordItemMixin (Settings settings) {
        super(settings);
    }
}
