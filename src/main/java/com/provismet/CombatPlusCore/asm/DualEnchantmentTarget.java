package com.provismet.CombatPlusCore.asm;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.provismet.CombatPlusCore.interfaces.DualWeapon;
import com.provismet.CombatPlusCore.utility.CombatTags;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;

public class DualEnchantmentTarget extends DualEnchantmentTargetMixin {
    @Override
    public boolean isAcceptableItem (Item item) {
        return item instanceof DualWeapon || (item != null && item.getDefaultStack().isIn(CombatTags.DUAL_WEAPON));
    }
    
}

@Mixin(EnchantmentTarget.class)
abstract class DualEnchantmentTargetMixin {
    @Shadow
    abstract boolean isAcceptableItem (Item item);
}
