package com.provismet.CombatPlusCore.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.provismet.CombatPlusCore.utility.WeaponTypes;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

@Mixin(DamageEnchantment.class)
public abstract class DamageEnchantmentMixin extends Enchantment {
    protected DamageEnchantmentMixin (Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(weight, target, slotTypes);
    }
    
    // Allows DamageEnchantments to be applied to all melee weapons. (Axes are already compatible, so this has no effect on vanilla behaviour.)
    @Inject(method="isAcceptableItem", at=@At("HEAD"), cancellable=true)
    public void allowMeleeWeapons (ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        if (WeaponTypes.isMeleeWeapon(itemStack)) {
            cir.setReturnValue(true);
        }
    }
}
