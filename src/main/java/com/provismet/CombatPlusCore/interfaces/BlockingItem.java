package com.provismet.CombatPlusCore.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface BlockingItem {
    /**
     * Executes immediately when the user blocks an attack.
     *
     * @param itemStack This item.
     * @param user The entity that blocked the attack.
     * @param attacker The attacker.
     */
    default void postBlock (ItemStack itemStack, LivingEntity user, Entity attacker) {

    }
}
