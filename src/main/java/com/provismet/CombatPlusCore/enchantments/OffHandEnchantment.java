package com.provismet.CombatPlusCore.enchantments;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import com.provismet.CombatPlusCore.interfaces.CPCEnchantment;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentHelper;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.LuckEnchantment;

/**
 * Enchantments that are applied to offhanded dual weapons.
 *
 * @see com.provismet.CombatPlusCore.utility.CPCItemTags#DUAL_WEAPON
 * @see com.provismet.CombatPlusCore.utility.CPCItemTags#OFFHAND_ENCHANTABLE
 * @see com.provismet.CombatPlusCore.utility.CPCItemTags#OFFHAND_PRIMARY_ENCHANTABLE
 */
public abstract class OffHandEnchantment extends Enchantment implements CPCEnchantment {
    protected OffHandEnchantment(Properties properties) {
        super(properties);
    }

    /**
     * <p> Deprecated in Combat+. </p>
     * <p> Use {@link #getAttackDamage(int, EquipmentSlot, LivingEntity, LivingEntity)} instead. </p>
     */
    @Override @Deprecated
    public final float getAttackDamage (int level, @Nullable EntityType<?> entityType) {
        return 0f;
    }

    @Override
    public boolean canAccept (Enchantment other) {
        return super.canAccept(other) &&
            !(other == Enchantments.FIRE_ASPECT) &&
            !(other == Enchantments.SWEEPING_EDGE) &&
            !(other instanceof LuckEnchantment) &&
            !CPCEnchantmentHelper.isDamage(other) &&
            !(other instanceof OffHandEnchantment otherOffhand &&
                otherOffhand.getGroup() != null &&
                this.getGroup() != null &&
                !otherOffhand.getGroup().equals(this.getGroup())
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
