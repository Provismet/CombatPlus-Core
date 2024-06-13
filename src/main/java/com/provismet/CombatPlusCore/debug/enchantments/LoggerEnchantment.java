package com.provismet.CombatPlusCore.debug.enchantments;

import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.interfaces.CPCEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

public class LoggerEnchantment extends Enchantment implements CPCEnchantment {
    public LoggerEnchantment(Properties properties) {
        super(properties);
    }

    @Override
    public float getAttackDamage (int level, EquipmentSlot slot, LivingEntity user, LivingEntity target) {
        if (target.getType() == EntityType.ZOMBIE) {
            CPCMain.LOGGER.info("Enchantment: {} has struck a Zombie", user);
            return 5f;
        }
        return 0f;
    }

    @Override
    public void postChargedHit (int level, LivingEntity user, LivingEntity target) {
        CPCMain.LOGGER.info("Enchantment: {} has charged hit {}", user, target);
    }

    @Override
    public void postCriticalHit (int level, LivingEntity user, LivingEntity target) {
        CPCMain.LOGGER.info("Enchantment: {} has critically hit {}", user, target);
    }

    @Override
    public void postKill (int level, LivingEntity user, LivingEntity target) {
        CPCMain.LOGGER.info("Enchantment: {} has killed {}", user, target);
    }
}
