package com.provismet.CombatPlusCore.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.provismet.CombatPlusCore.interfaces.MeleeWeapon;
import com.provismet.CombatPlusCore.interfaces.mixin.IMixinItemStack;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements IMixinItemStack {
    @Shadow
    public abstract Item getItem();

    @Override
    public void CPC_postChargedHit (LivingEntity user, LivingEntity target) {
        if (this.getItem() instanceof MeleeWeapon weapon) weapon.postChargedHit((ItemStack)(Object)this, user, target);
    }

    @Override
    public void CPC_postCriticalHit (LivingEntity user, LivingEntity target) {
        if (this.getItem() instanceof MeleeWeapon weapon) weapon.postCriticalHit((ItemStack)(Object)this, user, target);
    }

    @Override
    public void CPC_postKill (LivingEntity user, LivingEntity target) {
        if (this.getItem() instanceof MeleeWeapon weapon) weapon.postKill((ItemStack)(Object)this, user, target);
    }
}
