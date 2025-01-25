package com.provismet.CombatPlusCore.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.provismet.CombatPlusCore.interfaces.BlockingItem;
import com.provismet.CombatPlusCore.utility.CPCCallbackUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.provismet.CombatPlusCore.utility.tag.CPCItemTags;

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

    @Shadow public abstract ItemStack getMainHandStack();

    @Shadow protected ItemStack activeItemStack;

    @Shadow public abstract Hand getActiveHand ();

    @Shadow protected int itemUseTimeLeft;

    @Shadow public abstract boolean isBlocking ();

    @Inject(method="disablesShield", at=@At("HEAD"), cancellable=true)
    private void applyDisableTag (CallbackInfoReturnable<Boolean> cir) {
        if (this.getMainHandStack().isIn(CPCItemTags.SHIELD_BREAKER)) cir.setReturnValue(true);
    }

    @Inject(method="damageShield", at=@At("HEAD"), cancellable = true)
    private void damageBlockingItem (float amount, CallbackInfo info) {
        if (this.isBlocking() && this.activeItemStack.getItem() instanceof BlockingItem blockingItem) {
            blockingItem.damageDurability(this.activeItemStack, amount, (LivingEntity)(Object)this);
            info.cancel();
        }
    }

    @Inject(method="takeShieldHit", at=@At("TAIL"))
    private void applyBlockerItemEffects (LivingEntity attacker, CallbackInfo info) {
        if (this.getWorld() instanceof ServerWorld world) {
            CPCCallbackUtil.postBlock(world, activeItemStack, LivingEntity.getSlotForHand(this.getActiveHand()), (LivingEntity)(Object)this, attacker);
        }
    }

    @Inject(method="getBlockingItem", at= @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", shift=At.Shift.AFTER), cancellable=true)
    private void allowBlockingItems (CallbackInfoReturnable<ItemStack> cir) {
        Item activeItem = this.activeItemStack.getItem();
        LivingEntity thisEntity = (LivingEntity)(Object)this;
        if (activeItem instanceof BlockingItem blockingItem &&
            blockingItem.canBlock(this.activeItemStack) &&
            this.activeItemStack.getMaxUseTime(thisEntity) - this.itemUseTimeLeft >= blockingItem.blockChargeTicks(this.activeItemStack)) {
            cir.setReturnValue(this.activeItemStack);
        }
    }
}
