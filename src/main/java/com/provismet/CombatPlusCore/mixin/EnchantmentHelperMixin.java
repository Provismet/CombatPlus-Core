package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.registries.CPCEntityAttributes;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    @Inject(method="modifyDamage", at=@At("HEAD"), cancellable=true)
    private static void getCombatPlusDamage (ServerLevel world, ItemStack stack, Entity target, DamageSource damageSource, float baseDamage, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(CPCEnchantmentHelper.getDamage(world, stack, target, damageSource, baseDamage));
    }

    @Inject(method = "getDamageProtection", at = @At("RETURN"), cancellable = true)
    private static void modifyProtection (ServerLevel world, LivingEntity user, DamageSource damageSource, CallbackInfoReturnable<Float> cir) {
        Float out = cir.getReturnValue();
        cir.setReturnValue(out * (float)user.getAttributeValue(CPCEntityAttributes.PROTECTION_EFFECTIVENESS));
    }
}
