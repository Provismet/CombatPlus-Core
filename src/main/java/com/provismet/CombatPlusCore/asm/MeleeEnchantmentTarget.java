package com.provismet.CombatPlusCore.asm;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.provismet.CombatPlusCore.interfaces.MeleeWeapon;
import com.provismet.CombatPlusCore.utility.CombatTags;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;

public class MeleeEnchantmentTarget extends MeleeEnchantmentTargetMixin {
    @Override
    public boolean isAcceptableItem (Item item) {
        return item instanceof MeleeWeapon || (item != null && item.getDefaultStack().isIn(CombatTags.MELEE_WEAPON));
    }
    
}

@Mixin(EnchantmentTarget.class)
abstract class MeleeEnchantmentTargetMixin {
    @Shadow
    abstract boolean isAcceptableItem (Item item);
}
