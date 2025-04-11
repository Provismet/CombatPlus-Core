package com.provismet.CombatPlusCore.interfaces;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;

public interface BlockingItem {
    /**
     * Executes immediately when the user blocks an attack.
     *
     * @param itemStack This item.
     * @param user The entity that blocked the attack.
     * @param source The damage source.
     * @param damageAmount The amount of damage this attack would deal.
     */
    default void postBlock (ItemStack itemStack, LivingEntity user, DamageSource source, float damageAmount) {

    }
}
