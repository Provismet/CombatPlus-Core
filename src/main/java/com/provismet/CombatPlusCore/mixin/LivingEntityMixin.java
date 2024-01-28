package com.provismet.CombatPlusCore.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.provismet.CombatPlusCore.utility.CombatTags;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin (EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract ItemStack getMainHandStack();
    
    @Inject(method="disablesShield", at=@At("HEAD"), cancellable=true)
    public void applyDisableTag (CallbackInfoReturnable<Boolean> cir) {
        if (this.getMainHandStack().isIn(CombatTags.SHIELD_BREAKER)) cir.setReturnValue(true);
    }
}
