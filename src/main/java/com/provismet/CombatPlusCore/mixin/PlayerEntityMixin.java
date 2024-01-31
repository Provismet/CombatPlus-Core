package com.provismet.CombatPlusCore.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;
import com.provismet.CombatPlusCore.interfaces.mixin.IMixinItemStack;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentHelper;
import com.provismet.CombatPlusCore.utility.CombatGameRules;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin (EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method="attack", at=@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", shift=At.Shift.AFTER))
    private void applyHitEffects (Entity entity, CallbackInfo info, @Local(ordinal=0) boolean charged, @Local(ordinal=2) boolean critical) {
        if (entity instanceof LivingEntity target) {
            if (charged) {
                ((IMixinItemStack)(Object)this.getMainHandStack()).CPC_postChargedHit(this, target);
                CPCEnchantmentHelper.postChargedHit(this, target, EquipmentSlot.MAINHAND);
            }
            if (critical) {
                ((IMixinItemStack)(Object)this.getMainHandStack()).CPC_postCriticalHit(this, target);
                CPCEnchantmentHelper.postCriticalHit(this, target, EquipmentSlot.MAINHAND);
            }
        }
    }

    @Redirect(method="attack", at=@At(value="INVOKE", target="Lnet/minecraft/enchantment/EnchantmentHelper;getAttackDamage(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityGroup;)F"))
    public float redirectVanillaEnchantments (ItemStack itemStack, EntityGroup entityGroup, Entity target) {
        if (target instanceof LivingEntity living) {
            return CPCEnchantmentHelper.getAttackDamage(this, living);
        }
        return EnchantmentHelper.getAttackDamage(itemStack, entityGroup);
    }    

    @ModifyVariable(method="attack", at=@At("STORE"), ordinal=3, slice=@Slice(from=@At(value="INVOKE", target="Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;")))
    private boolean stopSweeping (boolean original) {
        if (this.getWorld().getGameRules().getBoolean(CombatGameRules.SWEEPING_REQUIRES_ENCHANTMENT) && EnchantmentHelper.getLevel(Enchantments.SWEEPING, this.getMainHandStack()) == 0) return false;
        return original;
    }
}
