package com.provismet.CombatPlusCore.utility;

import org.apache.commons.lang3.mutable.MutableFloat;

import com.provismet.CombatPlusCore.interfaces.CPCEnchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;

/**
 * Enchantment helper to apply hooks from {@link CPCEnchantment}.
 */
public class CPCEnchantmentHelper {
    /**
     * Gets the bonus attack damage that the user should deal against the target.
     * 
     * <p> This method internally calls the vanilla {@link EnchantmentHelper#getAttackDamage}.
     * 
     * @param defaultSlot The equipment slot to be passed to the vanilla EnchantmentHelper. Almost always MAINHAND.
     * @param user The wielder of the item.
     * @param target The entity that was struck.
     * @return The additional damage to deal to the target.
     */
    public static float getAttackDamage (EquipmentSlot defaultSlot, LivingEntity user, LivingEntity target) {
        MutableFloat totalDamage = new MutableFloat();

        // CPCEnchantments should ALWAYS return zero for their vanilla damage to avoiding doubling the effectiveness.
        totalDamage.add(EnchantmentHelper.getAttackDamage(user.getEquippedStack(defaultSlot), target.getGroup()));

        CPCEnchantmentHelper.forEachEnchantment((enchantment, level, slot) -> {
            if (enchantment instanceof CPCEnchantment cpcEnchant) totalDamage.add(cpcEnchant.getAttackDamage(level, slot, user, target));
        }, user);

        return totalDamage.floatValue();
    }

    /**
     * Gets the bonus attack damage that the user should deal against the target.
     * 
     * <p> This method internally calls the vanilla {@link EnchantmentHelper#getAttackDamage} with a MAINHAND equipment slot.
     * 
     * @param user The wielder of the item.
     * @param target The entity that was struck.
     * @return The additional damage to deal to the target.
     */
    public static float getAttackDamage (LivingEntity user, LivingEntity target) {
        return CPCEnchantmentHelper.getAttackDamage(EquipmentSlot.MAINHAND, user, target);
    }

    /**
     * Calls enchantment callbacks for charged hits.
     * 
     * @param user The wielder of the item.
     * @param target The entity that was struck.
     */
    public static void postChargedHit (LivingEntity user, LivingEntity target) {
        CPCEnchantmentHelper.forEachEnchantment((enchantment, level, slot) -> {
            if (enchantment instanceof CPCEnchantment cpcEnchant) cpcEnchant.postChargedHit(level, slot, user, target);
        }, user);
    }

    /**
     * Calls enchantment callbacks for critical hits.
     * 
     * @param user The wielder of the item.
     * @param target The entity that was struck.
     */
    public static void postCriticalHit (LivingEntity user, LivingEntity target) {
        CPCEnchantmentHelper.forEachEnchantment((enchantment, level, slot) -> {
            if (enchantment instanceof CPCEnchantment cpcEnchant) cpcEnchant.postCriticalHit(level, slot, user, target);
        }, user);
    }

    /**
     * Calls enchantment callbacks for kills.
     * 
     * @param user The wielder of the item.
     * @param target The entity that was killed.
     */
    public static void postKill (LivingEntity user, LivingEntity target) {
        CPCEnchantmentHelper.forEachEnchantment((enchantment, level, slot) -> {
            if (enchantment instanceof CPCEnchantment cpcEnchant) cpcEnchant.postKill(level, slot, user, target);
        }, user);
    }

    /**
     * Iterates over all equipment slots on the user, activating a consumer for each enchantment.
     * 
     * @param consumer A consumer / lambda function to be called.
     * @param user The user of the enchanted items.
     */
    public static void forEachEnchantment (Consumer consumer, LivingEntity user) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack itemStack = user.getEquippedStack(slot);
            CPCEnchantmentHelper.forEachEnchantment(consumer, itemStack, slot);
        }
    }

    /**
     * Iterates over all enchantments on a given item stack, calling the consumer for each.
     * 
     * @param consumer A consumer / lambda function.
     * @param itemStack The item stack.
     * @param slot The item slot that item stack is in.
     */
    public static void forEachEnchantment (Consumer consumer, ItemStack itemStack, EquipmentSlot slot) {
        if (itemStack.isEmpty()) return;

        NbtList nbtList = itemStack.getEnchantments();
        for (int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            Registries.ENCHANTMENT.getOrEmpty(EnchantmentHelper.getIdFromNbt(nbtCompound)).ifPresent(enchantment -> consumer.accept(enchantment, EnchantmentHelper.getLevelFromNbt(nbtCompound), slot));
        }
    }

    @FunctionalInterface
    private static interface Consumer {
        public void accept (Enchantment enchantment, int level, EquipmentSlot slot);
    }
}
