package com.provismet.CombatPlusCore.enchantment.effect.doubleEntity;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.enchantment.effect.CPCEnchantmentEntityEffect;
import com.provismet.CombatPlusCore.interfaces.MeleeWeapon;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;

/**
 * Triggers the attacker's melee weapon on-charged-hit effect against the target.
 */
public record WeaponPostChargedEffect () implements CPCEnchantmentEntityEffect {
    public static final MapCodec<WeaponPostChargedEffect> CODEC = MapCodec.unit(WeaponPostChargedEffect::new);

    @Override
    public void apply (ServerLevel world, int level, EnchantedItemInUse context, Entity attacker, Entity target) {
        if (attacker instanceof LivingEntity livingAttacker && target instanceof LivingEntity livingTarget) {
            if (context.itemStack().getItem() instanceof MeleeWeapon meleeWeapon)
                meleeWeapon.postChargedHit(context.itemStack(), livingAttacker, livingTarget);
            else if (attacker.getWeaponItem().getItem() instanceof MeleeWeapon meleeWeapon)
                meleeWeapon.postChargedHit(attacker.getWeaponItem(), livingAttacker, livingTarget);
        }
    }

    @Override
    public MapCodec<? extends CPCEnchantmentEntityEffect> getCodec () {
        return CODEC;
    }
}
