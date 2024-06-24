package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.interfaces.MeleeWeapon;
import net.minecraft.item.Item;
import net.minecraft.item.MaceItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MaceItem.class)
public abstract class MaceItemMixin extends Item implements MeleeWeapon {
    protected MaceItemMixin(Settings settings) {
        super(settings);
    }
}
