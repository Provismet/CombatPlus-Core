package com.provismet.CombatPlusCore.debug.items;

import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.items.AbstractShieldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class DebugShield extends AbstractShieldItem {
    public DebugShield (Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxCooldown (ItemStack itemStack) {
        return 15;
    }

    @Override
    public void postBlock (ItemStack itemStack, LivingEntity user, Entity attacker) {
        CPCMain.LOGGER.info("Item: {} blocked an attack from {}", user, attacker);
    }
}
