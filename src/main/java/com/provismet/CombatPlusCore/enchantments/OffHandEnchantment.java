package com.provismet.CombatPlusCore.enchantments;

import org.jetbrains.annotations.Nullable;

import com.provismet.CombatPlusCore.interfaces.CPCEnchantment;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentHelper;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentTargets;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.enchantment.LuckEnchantment;
import net.minecraft.enchantment.SweepingEnchantment;
import net.minecraft.entity.EquipmentSlot;

/**
 * Enchantments that are applied to offhanded dual weapons.
 */
public abstract class OffHandEnchantment extends Enchantment implements CPCEnchantment {
    protected OffHandEnchantment (Rarity weight, EnchantmentTarget target) {
        super(weight, target, new EquipmentSlot[] {EquipmentSlot.OFFHAND});
    }

    protected OffHandEnchantment (Rarity weight) {
        this(weight, CPCEnchantmentTargets.DUAL_WEAPON);
    }
    
    @Override
    public boolean canAccept (Enchantment other) {
        return super.canAccept(other) &&
            !(other instanceof FireAspectEnchantment) &&
            !(other instanceof SweepingEnchantment) &&
            !(other instanceof LuckEnchantment) &&
            !CPCEnchantmentHelper.isDamage(other) &&
            !CPCEnchantmentHelper.isAdditionalDamage(other) &&
            !(other instanceof OffHandEnchantment otherOffhand &&
                otherOffhand.getGroup() != null &&
                this.getGroup() != null &&
                otherOffhand.getGroup() != this.getGroup()
            );
    }

    /**
     * Returns the group name of this enchantment. Offhand enchantments that have a group cannot
     * be mixed with offhand enchantments that belong to a different group.
     * 
     * <p> This system exists because offhand enchantments may be designed to interact with each other (see the Dual Swords mod).
     * The Offhand Enchantment class is intentionally miscellaneous by nature, so this library will offer a rudimentary grouping
     * system instead of direct subclasses.
     * 
     * @return A unique string denoting the group name of this enchantment, or null.
     */
    @Nullable
    protected abstract String getGroup ();
}
