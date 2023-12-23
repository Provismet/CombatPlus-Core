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

public class CPCEnchantmentHelper {
    public static float getAttackDamage (EquipmentSlot defaultSlot, LivingEntity user, LivingEntity target) {
        MutableFloat totalDamage = new MutableFloat();

        totalDamage.add(EnchantmentHelper.getAttackDamage(user.getEquippedStack(defaultSlot), target.getGroup()));

        CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> {
            if (enchantment instanceof CPCEnchantment cpcEnchant) totalDamage.add(cpcEnchant.getAttackDamage(level, user, target));
        }, user);

        return totalDamage.floatValue();
    }

    public static void postChargedHit (LivingEntity user, LivingEntity target) {

    }

    public static void postCriticalHit (LivingEntity user, LivingEntity target) {

    }

    public static void postKill (LivingEntity user) {

    }

    public static void forEachEnchantment (Consumer consumer, LivingEntity user) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack itemStack = user.getEquippedStack(slot);
            CPCEnchantmentHelper.forEachEnchantment(consumer, itemStack);
        }
    }

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
