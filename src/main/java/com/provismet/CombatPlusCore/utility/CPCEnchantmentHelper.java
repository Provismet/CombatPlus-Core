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

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> {
                if (enchantment instanceof CPCEnchantment cpcEnchant) totalDamage.add(cpcEnchant.getAttackDamage(level, slot, user, target));
            }, user, slot);
        }

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
     * @param slot The equipment slot to trigger callbacks for.
     */
    public static void postChargedHit (LivingEntity user, LivingEntity target, EquipmentSlot slot) {
        CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> {
            if (enchantment instanceof CPCEnchantment cpcEnchant) cpcEnchant.postChargedHit(level, user, target);
        }, user, slot);
    }

    /**
     * Calls enchantment callbacks for charged hits.
     * 
     * @param user The wielder of the item.
     * @param target The entity that was struck.
     * @param itemStack The item stack to trigger callbacks for.
     */
    public static void postChargedHit (LivingEntity user, LivingEntity target, ItemStack itemStack) {
        CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> {
            if (enchantment instanceof CPCEnchantment cpcEnchant) cpcEnchant.postChargedHit(level, user, target);
        }, itemStack);
    }

    /**
     * Calls enchantment callbacks for critical hits.
     * 
     * @param user The wielder of the item.
     * @param target The entity that was struck.
     * @param slot The equipment slot to trigger callbacks for.
     */
    public static void postCriticalHit (LivingEntity user, LivingEntity target, EquipmentSlot slot) {
        CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> {
            if (enchantment instanceof CPCEnchantment cpcEnchant) cpcEnchant.postCriticalHit(level, user, target);
        }, user, slot);
    }

    /**
     * Calls enchantment callbacks for critical hits.
     * 
     * @param user The wielder of the item.
     * @param target The entity that was struck.
     * @param itemStack The item stack to trigger callbacks for.
     */
    public static void postCriticalHit (LivingEntity user, LivingEntity target, ItemStack itemStack) {
        CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> {
            if (enchantment instanceof CPCEnchantment cpcEnchant) cpcEnchant.postCriticalHit(level, user, target);
        }, itemStack);
    }

    /**
     * Calls enchantment callbacks for kills.
     * 
     * @param user The wielder of the item.
     * @param target The entity that was killed.
     * @param slot The equipment slot to trigger callbacks for.
     */
    public static void postKill (LivingEntity user, LivingEntity target, EquipmentSlot slot) {
        CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> {
            if (enchantment instanceof CPCEnchantment cpcEnchant) cpcEnchant.postKill(level, user, target);
        }, user, slot);
    }

    /**
     * Calls enchantment callbacks for kills.
     * 
     * @param user The wielder of the item.
     * @param target The entity that was killed.
     * @param itemStack The item stack to trigger callbacks for.
     */
    public static void postKill (LivingEntity user, LivingEntity target, ItemStack itemStack) {
        CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> {
            if (enchantment instanceof CPCEnchantment cpcEnchant) cpcEnchant.postKill(level, user, target);
        }, itemStack);
    }

    /**
     * Iterates over all equipment slots on the user, activating a consumer for each enchantment.
     * 
     * @param consumer A consumer / lambda function to be called.
     * @param user The user of the enchanted items.
     * @param slot The equipment slot that holds the user's item.
     */
    public static void forEachEnchantment (Consumer consumer, LivingEntity user, EquipmentSlot slot) {
        ItemStack itemStack = user.getEquippedStack(slot);
        CPCEnchantmentHelper.forEachEnchantment(consumer, itemStack);
    }

    /**
     * Iterates over all enchantments on a given item stack, calling the consumer for each.
     * 
     * @param consumer A consumer / lambda function.
     * @param itemStack The item stack.
     */
    public static void forEachEnchantment (Consumer consumer, ItemStack itemStack) {
        if (itemStack.isEmpty()) return;

        NbtList nbtList = itemStack.getEnchantments();
        for (int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            Registries.ENCHANTMENT.getOrEmpty(EnchantmentHelper.getIdFromNbt(nbtCompound)).ifPresent(enchantment -> consumer.accept(enchantment, EnchantmentHelper.getLevelFromNbt(nbtCompound)));
        }
    }

    @FunctionalInterface
    private static interface Consumer {
        public void accept (Enchantment enchantment, int level);
    }
}
