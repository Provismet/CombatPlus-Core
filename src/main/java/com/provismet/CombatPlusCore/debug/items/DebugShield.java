package com.provismet.CombatPlusCore.debug.items;

import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.items.AbstractShieldItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;

public class DebugShield extends AbstractShieldItem {
    public DebugShield (Settings settings) {
        super(settings);
    }

    @Override
    public void postBlock (ItemStack itemStack, LivingEntity user, DamageSource source, float damageAmount) {
        super.postBlock(itemStack, user, source, damageAmount);
        CPCMain.LOGGER.info("Item: {} blocked an attack from {}", user, source.getSource());
    }
}
