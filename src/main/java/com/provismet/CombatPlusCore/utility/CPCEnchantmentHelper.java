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
    public static float getAttackDamage (LivingEntity user, LivingEntity target) {
        MutableFloat totalDamage = new MutableFloat();

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack itemStack = user.getEquippedStack(slot);

            CPCEnchantmentHelper.forEach((enchantment, level) -> {
                if (enchantment instanceof CPCEnchantment cpcEnchant && cpcEnchant.shouldApplyDamage(level, slot, user, target)) totalDamage.add(cpcEnchant.getAttackDamage(level, user, target));
                else totalDamage.add(enchantment.getAttackDamage(level, target.getGroup()));
            }, itemStack);
        }
        return totalDamage.floatValue();
    }

    public static void forEach (Consumer consumer, ItemStack itemStack) {
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
