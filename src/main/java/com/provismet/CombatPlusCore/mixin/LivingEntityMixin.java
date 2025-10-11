package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.registries.CPCEntityAttributes;
import com.provismet.CombatPlusCore.utility.CPCCallbackUtil;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin (EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract Hand getActiveHand ();

    @Shadow public abstract @Nullable ItemStack getBlockingItem ();

    @Inject(method = "getDamageBlockedAmount", at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/BlocksAttacksComponent;onShieldHit(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/Hand;F)V", shift = At.Shift.AFTER))
    private void applyBlockerItemEffects (ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        ItemStack blockingStack = this.getBlockingItem();
        if (blockingStack != null) {
            CPCCallbackUtil.postBlock(world, this.getBlockingItem(), this.getActiveHand().getEquipmentSlot(), (LivingEntity)(Object)this, source, amount);
        }
    }

    @Inject(method = "createLivingAttributes", at = @At("RETURN"))
    private static void addCPCAttributes (CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.getReturnValue()
            .add(CPCEntityAttributes.PROTECTION_EFFECTIVENESS, 1);
    }
}
