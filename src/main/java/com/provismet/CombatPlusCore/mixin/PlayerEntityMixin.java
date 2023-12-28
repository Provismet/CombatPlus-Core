package com.provismet.CombatPlusCore.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.provismet.CombatPlusCore.interfaces.mixin.IMixinItemStack;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentHelper;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin (EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    
    @Inject(method="attack", at=@At(value="INVOKE", target="Lnet/minecraft/entity/player/PlayerEntity;addCritParticles(Lnet/minecraft/entity/Entity;)V", shift=At.Shift.AFTER))
    public void onCriticalHit (Entity entity, CallbackInfo info) {
        if (entity instanceof LivingEntity target) {
            ((IMixinItemStack)(Object)this.getMainHandStack()).CPC_postCriticalHit(this, target);
            CPCEnchantmentHelper.postCriticalHit(this, target);
        }
    }

    @Inject(method="attack", at=@At(value="INVOKE", target="Lnet/minecraft/entity/player/PlayerEntity;getAttackCooldownProgress(F)F"))
    public void postChargedHit (Entity entity, CallbackInfo info) {
        if (entity instanceof LivingEntity target && ((PlayerEntity)(Object)this).getAttackCooldownProgress(0.5f) > 0.9f) {
            ((IMixinItemStack)(Object)this.getMainHandStack()).CPC_postChargedHit(this, target);
            CPCEnchantmentHelper.postChargedHit(this, target);
        }
    }

    @Redirect(method="attack", at=@At(value="INVOKE", target="Lnet/minecraft/enchantment/EnchantmentHelper;getAttackDamage(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityGroup;)F"))
    public float redirectVanillaEnchantments (ItemStack itemStack, EntityGroup entityGroup, Entity target) {
        if (target instanceof LivingEntity living) {
            return CPCEnchantmentHelper.getAttackDamage(this, living);
        }
        return EnchantmentHelper.getAttackDamage(itemStack, entityGroup);
    }
}
