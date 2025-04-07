package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.registries.CPCEntityAttributes;
import com.provismet.CombatPlusCore.utility.CPCCallbackUtil;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

    @Shadow protected ItemStack activeItemStack;

    @Shadow public abstract Hand getActiveHand ();

    @Inject(method="takeShieldHit", at=@At("TAIL"))
    private void applyBlockerItemEffects (ServerWorld world, LivingEntity attacker, CallbackInfo ci) {
        CPCCallbackUtil.postBlock(world, activeItemStack, LivingEntity.getSlotForHand(this.getActiveHand()), (LivingEntity)(Object)this, attacker);
    }

    @Inject(method = "createLivingAttributes", at = @At("RETURN"))
    private static void addCPCAttributes (CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.getReturnValue()
            .add(CPCEntityAttributes.PROTECTION_EFFECTIVENESS, 1);
    }
}
