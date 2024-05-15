package com.provismet.CombatPlusCore.enchantments;

import com.provismet.CombatPlusCore.interfaces.CPCEnchantment;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentHelper;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

/**
 * Damaging enchantments intended as alternatives to the typical {@link DamageEnchantment} type.
 */
public abstract class AdditionalDamageEnchantment extends Enchantment implements CPCEnchantment {
    protected AdditionalDamageEnchantment(Properties properties) {
        super(properties);
    }

    /**
     * <p> Deprecated in Combat+. </p>
     * <p> Use {@link CPCEnchantment#getAttackDamage(int, EquipmentSlot, LivingEntity, LivingEntity)} instead. </p>
     */
    @Override
    public final float getAttackDamage (int level, @Nullable EntityType<?> entityType) {
        return 0f;
    }

    @Override
    protected boolean canAccept (Enchantment other) {
        return super.canAccept(other) &&
            !CPCEnchantmentHelper.isDamage(other) &&
            !CPCEnchantmentHelper.isOffhand(other);
    }
}
