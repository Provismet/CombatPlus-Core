package com.provismet.combatplus.mixin;

import com.provismet.combatplus.api.tag.CombatPlusDamageTypeTags;
import com.provismet.combatplus.registries.CombatPlusAttributes;
import com.provismet.combatplus.utility.CombatPlusCallbackUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
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
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin (EntityType<?> type, Level world) {
        super(type, world);
    }

    @Shadow public abstract InteractionHand getUsedItemHand ();

    @Shadow public abstract @Nullable ItemStack getItemBlockingWith ();

    @Inject(method = "applyItemBlocking", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/BlocksAttacks;hurtBlockingItem(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/InteractionHand;F)V", shift = At.Shift.AFTER))
    private void applyBlockerItemEffects (ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Float> cir) {
        ItemStack blockingStack = this.getItemBlockingWith();
        if (blockingStack != null) {
            CombatPlusCallbackUtil.postBlock(level, this.getItemBlockingWith(), this.getUsedItemHand().asEquipmentSlot(), (LivingEntity)(Object)this, source, damage);
        }
    }

    @Inject(method = "createLivingAttributes", at = @At("RETURN"))
    private static void addCPCAttributes (CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.getReturnValue()
            .add(CombatPlusAttributes.PROTECTION_EFFECTIVENESS)
            .add(CombatPlusAttributes.PROJECTILE_DAMAGE)
            .add(CombatPlusAttributes.MAGIC_DAMAGE);
    }

    @ModifyVariable(method = "hurtServer", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private float applyBonusDamageAttributes (float damage, final ServerLevel level, final DamageSource source) {
        if (source.getEntity() instanceof LivingEntity attacker) {
            if (source.is(DamageTypeTags.IS_PROJECTILE)) damage += (float)attacker.getAttributeValue(CombatPlusAttributes.PROJECTILE_DAMAGE);
            if (source.is(CombatPlusDamageTypeTags.IS_MAGIC)) damage += (float)attacker.getAttributeValue(CombatPlusAttributes.MAGIC_DAMAGE);
        }
        return damage;
    }
}
