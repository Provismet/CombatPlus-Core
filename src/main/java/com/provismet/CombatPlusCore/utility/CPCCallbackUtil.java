package com.provismet.CombatPlusCore.utility;

import com.provismet.CombatPlusCore.interfaces.BlockingItem;
import com.provismet.CombatPlusCore.interfaces.MeleeWeapon;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * Basic utility class for triggering Combat+ callbacks between two entities.
 */
public class CPCCallbackUtil {
    /**
     * Triggers both the item and enchantment callbacks for a charged attack.
     *
     * @param world The world.
     * @param itemStack The weapon used.
     * @param slot The equipment slot of the weapon.
     * @param user The attacking entity.
     * @param target The attacked entity.
     */
    public static void postChargedHit (ServerLevel world, ItemStack itemStack, EquipmentSlot slot, LivingEntity user, LivingEntity target) {
        if (itemStack.getItem() instanceof MeleeWeapon meleeWeapon) {
            meleeWeapon.postChargedHit(itemStack, user, target);
        }
        CPCEnchantmentHelper.postChargedHit(world, user, target, slot);
    }

    /**
     * Triggers both the item and enchantment callbacks for a critical attack.
     *
     * @param world The world.
     * @param itemStack The weapon used.
     * @param slot The equipment slot of the weapon.
     * @param user The attacking entity.
     * @param target The attacked entity.
     */
    public static void postCriticalHit (ServerLevel world, ItemStack itemStack, EquipmentSlot slot, LivingEntity user, LivingEntity target) {
        if (itemStack.getItem() instanceof MeleeWeapon meleeWeapon) {
            meleeWeapon.postCriticalHit(itemStack, user, target);
        }
        CPCEnchantmentHelper.postCriticalHit(world, user, target, slot);
    }

    /**
     * Triggers both the item and enchantment callbacks for one entity killing another.
     *
     * @param world The world.
     * @param itemStack The weapon used.
     * @param slot The equipment slot of the weapon.
     * @param user The attacking entity.
     * @param target The slain entity.
     */
    public static void postKill (ServerLevel world, ItemStack itemStack, EquipmentSlot slot, LivingEntity user, LivingEntity target) {
        if (itemStack.getItem() instanceof MeleeWeapon meleeWeapon) {
            meleeWeapon.postKill(itemStack, user, target);
        }
        CPCEnchantmentHelper.postKill(world, user, target, slot);
    }

    /**
     * Triggers both the item and enchantment callbacks for blocking attacks.
     *
     * @param world The world.
     * @param itemStack The shield or blocking item used.
     * @param slot The equipment slot of the item.
     * @param user The blocking entity.
     * @param source The blocked damage source.
     * @param amount The full damage that would have been dealt.
     */
    public static void postBlock (ServerLevel world, ItemStack itemStack, EquipmentSlot slot, LivingEntity user, DamageSource source, float amount) {
        if (itemStack.getItem() instanceof BlockingItem block) {
            block.postBlock(itemStack, user, source, amount);
        }
        CPCEnchantmentHelper.postBlock(world, user, source.getDirectEntity(), slot);
    }
}
