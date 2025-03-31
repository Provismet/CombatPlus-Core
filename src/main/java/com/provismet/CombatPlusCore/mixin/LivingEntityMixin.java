package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.interfaces.BlockingItem;
import com.provismet.CombatPlusCore.utility.CPCCallbackUtil;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

    @Shadow protected ItemStack activeItemStack;

    @Shadow public abstract Hand getActiveHand ();

    @Shadow protected int itemUseTimeLeft;

    @Inject(method="takeShieldHit", at=@At("TAIL"))
    private void applyBlockerItemEffects (ServerWorld world, LivingEntity attacker, CallbackInfo ci) {
        CPCCallbackUtil.postBlock(world, activeItemStack, LivingEntity.getSlotForHand(this.getActiveHand()), (LivingEntity)(Object)this, attacker);
    }

    @Inject(method="getBlockingItem", at=@At(value="INVOKE", target="Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", shift=At.Shift.AFTER), cancellable=true)
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
