package com.provismet.CombatPlusCore.enchantment.effect.doubleEntity;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.enchantment.effect.CPCEnchantmentEntityEffect;
import com.provismet.CombatPlusCore.interfaces.MeleeWeapon;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;

public record WeaponPostCharged () implements CPCEnchantmentEntityEffect {
    public static final MapCodec<WeaponPostCharged> CODEC = MapCodec.unit(WeaponPostCharged::new);

    @Override
    public void apply (ServerWorld world, int level, EnchantmentEffectContext context, Entity attacker, Entity target) {
        if (attacker instanceof LivingEntity livingAttacker && target instanceof LivingEntity livingTarget) {
            if (context.stack().getItem() instanceof MeleeWeapon meleeWeapon)
                meleeWeapon.postChargedHit(context.stack(), livingAttacker, livingTarget);
            else if (attacker.getWeaponStack().getItem() instanceof MeleeWeapon meleeWeapon)
                meleeWeapon.postChargedHit(attacker.getWeaponStack(), livingAttacker, livingTarget);
        }
    }

    @Override
    public MapCodec<? extends CPCEnchantmentEntityEffect> getCodec () {
        return CODEC;
    }
}
