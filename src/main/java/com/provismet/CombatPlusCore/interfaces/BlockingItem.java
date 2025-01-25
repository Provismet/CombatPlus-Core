package com.provismet.CombatPlusCore.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

public interface BlockingItem {
    /**
     * @param itemStack This item.
     * @return Whether or not this item can block attacks.
     */
    default boolean canBlock (ItemStack itemStack) {
        return true;
    }

    /**
     * Damages this item when used to block an attack.
     *
     * @implNote By default, this method replicates the vanilla shield damage method.
     *
     * @param itemStack This item.
     * @param amount The amount of damage blocked.
     * @param user The entity holding this item.
     */
    default void damageDurability (ItemStack itemStack, float amount, LivingEntity user) {
        if (amount >= 3f) {
            int durabilityLoss = 1 + MathHelper.floor(amount);
            Hand hand = user.getActiveHand();
            user.getActiveItem().damage(durabilityLoss, user, PlayerEntity.getSlotForHand(hand));
            if (user.getActiveItem().isEmpty()) {
                if (hand == Hand.MAIN_HAND) user.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                else user.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);

                user.clearActiveItem();
                user.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8f, 0.8f + user.getWorld().random.nextFloat() * 0.4f);
            }
        }
    }

    /**
     * @param itemStack This item.
     * @return How many ticks of cooldown the item should go on after being disabled.
     */
    int getMaxCooldown (ItemStack itemStack);

    /**
     * When blocking with a shield-like item, the player is only protected after a certain number of ticks.
     *
     * @param itemStack This item.
     * @return The number of ticks the item must be used for until blocking is active.
     */
    int blockChargeTicks (ItemStack itemStack);

    /**
     * Executes immediately when the user blocks an attack.
     *
     * @param itemStack This item.
     * @param user The entity that blocked the attack.
     * @param attacker The attacker.
     */
    void postBlock (ItemStack itemStack, LivingEntity user, Entity attacker);
}
