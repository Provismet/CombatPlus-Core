package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.registries.CPCEntityAttributes;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    @Inject(method="getDamage", at=@At("HEAD"), cancellable=true)
    private static void getCombatPlusDamage (ServerWorld world, ItemStack stack, Entity target, DamageSource damageSource, float baseDamage, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(CPCEnchantmentHelper.getDamage(world, stack, target, damageSource, baseDamage));
    }

    @Inject(method = "getProtectionAmount", at = @At("RETURN"), cancellable = true)
    private static void modifyProtection (ServerWorld world, LivingEntity user, DamageSource damageSource, CallbackInfoReturnable<Float> cir) {
        Float out = cir.getReturnValue();
        cir.setReturnValue(out * (float)user.getAttributeValue(CPCEntityAttributes.PROTECTION_EFFECTIVENESS));
    }
}
