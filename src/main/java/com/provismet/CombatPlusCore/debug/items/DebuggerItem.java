package com.provismet.CombatPlusCore.debug.items;

import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.interfaces.DualWeapon;
import com.provismet.CombatPlusCore.items.AbstractMeleeWeapon;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;

public class DebuggerItem extends AbstractMeleeWeapon implements DualWeapon {
    public DebuggerItem (Settings settings) {
        super(settings);
    }

    public static AttributeModifiersComponent createSimpleAttributes () {
        return AttributeModifiersComponent.builder()
            .add(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 3f, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
            .add(EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -2.4f, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
            .build();
    }

    @Override
    public void postChargedHit (ItemStack itemStack, LivingEntity user, LivingEntity target) {
        CPCMain.LOGGER.info("Item: {} performed a charged hit against {}", user, target);
    }

    @Override
    public void postCriticalHit (ItemStack itemStack, LivingEntity user, LivingEntity target) {
        CPCMain.LOGGER.info("Item: {} performed a critical hit against {}", user, target);
    }

    @Override
    public void postKill (ItemStack itemStack, LivingEntity user, LivingEntity target) {
        CPCMain.LOGGER.info("Item: {} has killed {}", user, target);
    }
}
