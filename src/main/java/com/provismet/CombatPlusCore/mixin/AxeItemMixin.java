package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.interfaces.MeleeWeapon;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin extends Item implements MeleeWeapon {
    public AxeItemMixin (Settings settings) {
        super(settings);
    }
}
