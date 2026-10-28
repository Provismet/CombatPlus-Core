package com.provismet.CombatPlusCore.debug.items;

import com.provismet.CombatPlusCore.CombatPlusCoreMain;
import com.provismet.CombatPlusCore.api.item.interfaces.DualWeapon;
import com.provismet.CombatPlusCore.api.item.AbstractMeleeWeapon;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class DebuggerItem extends AbstractMeleeWeapon implements DualWeapon {
    public DebuggerItem (Properties settings) {
        super(settings);
    }

    public static ItemAttributeModifiers createSimpleAttributes () {
        return ItemAttributeModifiers.builder()
            .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 3f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -2.4f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .build();
    }

    @Override
    public void postChargedHit (ItemStack itemStack, LivingEntity user, LivingEntity target) {
        CombatPlusCoreMain.LOGGER.info("Item: {} performed a charged hit against {}", user, target);
    }

    @Override
    public void postCriticalHit (ItemStack itemStack, LivingEntity user, LivingEntity target) {
        CombatPlusCoreMain.LOGGER.info("Item: {} performed a critical hit against {}", user, target);
    }

    @Override
    public void postKill (ItemStack itemStack, LivingEntity user, LivingEntity target) {
        CombatPlusCoreMain.LOGGER.info("Item: {} has killed {}", user, target);
    }
}
