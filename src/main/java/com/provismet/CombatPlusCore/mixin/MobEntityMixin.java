package com.provismet.CombatPlusCore.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.provismet.CombatPlusCore.interfaces.mixin.IMixinItemStack;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentHelper;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {
    protected MobEntityMixin (EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    
    @Inject(method="tryAttack", at=@At(value="INVOKE", target="Lnet/minecraft/enchantment/EnchantmentHelper;getKnockback(Lnet/minecraft/entity/LivingEntity;)I", shift=At.Shift.AFTER))
    public void onHit (Entity target, CallbackInfoReturnable<Boolean> cir) {
        if (target instanceof LivingEntity living) {
            ((IMixinItemStack)(Object)this.getMainHandStack()).CPC_postChargedHit(this, living);
            CPCEnchantmentHelper.postChargedHit(this, living, EquipmentSlot.MAINHAND);
        }
    }

    @Redirect(method="tryAttack", at=@At(value="INVOKE", target="Lnet/minecraft/enchantment/EnchantmentHelper;getAttackDamage(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityGroup;)F"))
    public float redirectVanillaEnchantments (ItemStack itemStack, EntityGroup entityGroup, Entity target) {
        if (target instanceof LivingEntity living) {
            return CPCEnchantmentHelper.getAttackDamage(this, living);
        }
        return EnchantmentHelper.getAttackDamage(itemStack, entityGroup);
    }  
}
