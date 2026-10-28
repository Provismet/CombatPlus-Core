package com.provismet.CombatPlusCore.api.enchantment.effect.doubleEntity;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.api.enchantment.effect.CombatPlusEnchantmentEntityEffect;
import com.provismet.CombatPlusCore.api.item.interfaces.MeleeWeapon;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;

/**
 * Triggers the attacker's on-kill effect against the attacker.
 */
public record WeaponPostKillEffect () implements CombatPlusEnchantmentEntityEffect {
    public static final MapCodec<WeaponPostKillEffect> CODEC = MapCodec.unit(WeaponPostKillEffect::new);

    @Override
    public void apply (ServerLevel world, int level, EnchantedItemInUse context, Entity attacker, Entity target) {
        if (attacker instanceof LivingEntity livingAttacker && target instanceof LivingEntity livingTarget) {
            if (context.itemStack().getItem() instanceof MeleeWeapon meleeWeapon)
                meleeWeapon.postKill(context.itemStack(), livingAttacker, livingTarget);
            else if (attacker.getWeaponItem().getItem() instanceof MeleeWeapon meleeWeapon)
                meleeWeapon.postKill(attacker.getWeaponItem(), livingAttacker, livingTarget);
        }
    }

    @Override
    public MapCodec<? extends CombatPlusEnchantmentEntityEffect> getCodec () {
        return CODEC;
    }
}
