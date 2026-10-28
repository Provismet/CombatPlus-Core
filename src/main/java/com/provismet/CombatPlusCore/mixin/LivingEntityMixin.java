package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.registries.CombatPlusAttributes;
import com.provismet.CombatPlusCore.utility.CombatPlusCallbackUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin (EntityType<?> type, Level world) {
        super(type, world);
    }

    @Shadow public abstract InteractionHand getUsedItemHand ();

    @Shadow public abstract @Nullable ItemStack getItemBlockingWith ();

    @Inject(method = "applyItemBlocking", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/BlocksAttacks;hurtBlockingItem(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/InteractionHand;F)V", shift = At.Shift.AFTER))
    private void applyBlockerItemEffects (ServerLevel world, DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        ItemStack blockingStack = this.getItemBlockingWith();
        if (blockingStack != null) {
            CombatPlusCallbackUtil.postBlock(world, this.getItemBlockingWith(), this.getUsedItemHand().asEquipmentSlot(), (LivingEntity)(Object)this, source, amount);
        }
    }

    @Inject(method = "createLivingAttributes", at = @At("RETURN"))
    private static void addCPCAttributes (CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.getReturnValue()
            .add(CombatPlusAttributes.PROTECTION_EFFECTIVENESS, 1);
    }
}
