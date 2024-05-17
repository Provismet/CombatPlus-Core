package com.provismet.CombatPlusCore.enchantments;

import com.provismet.CombatPlusCore.interfaces.CPCEnchantment;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentHelper;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

/**
 * Weapon enchantments that apply a status change.
 * 
 * <p> Such enchantments should be considered equivalent to Fire Aspect. </p>
 *
 * @see com.provismet.CombatPlusCore.utility.CPCItemTags#ASPECT_ENCHANTABLE
 * @see com.provismet.CombatPlusCore.utility.CPCItemTags#ASPECT_PRIMARY_ENCHANTABLE
 */
public abstract class AspectEnchantment extends Enchantment implements CPCEnchantment {
    protected AspectEnchantment (Properties properties) {
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

    /**
     * <p> Offhand Enchantments do not block Aspect Enchantments. This is one-sided intentionally. </p>
     * <p> To make an Aspect Enchantment that works with Offhand Enchantments, simply override
     * this method to return true for Offhand Enchantments. </p>
     */
    @Override
    public boolean canAccept (Enchantment other) {
        return super.canAccept(other) &&
            !CPCEnchantmentHelper.isAspect(other) &&
            !CPCEnchantmentHelper.isOffhand(other);
    }
}
