package com.provismet.CombatPlusCore.debug.items;

import com.provismet.CombatPlusCore.CombatPlusCoreMain;
import com.provismet.CombatPlusCore.items.AbstractShieldItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class DebugShield extends AbstractShieldItem {
    public DebugShield (Properties settings) {
        super(settings);
    }

    @Override
    public void postBlock (ItemStack itemStack, LivingEntity user, DamageSource source, float damageAmount) {
        super.postBlock(itemStack, user, source, damageAmount);
        CombatPlusCoreMain.LOGGER.info("Item: {} blocked an attack from {}", user, source.getDirectEntity());
    }
}
