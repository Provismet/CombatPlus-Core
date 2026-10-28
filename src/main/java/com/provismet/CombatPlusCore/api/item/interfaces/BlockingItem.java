package com.provismet.CombatPlusCore.api.item.interfaces;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

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
