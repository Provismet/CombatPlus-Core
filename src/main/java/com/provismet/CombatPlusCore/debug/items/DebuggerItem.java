package com.provismet.CombatPlusCore.debug.items;

import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.interfaces.DualWeapon;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DebuggerItem extends Item implements DualWeapon {
    public DebuggerItem(Settings settings) {
        super(settings);
    }

    public static AttributeModifiersComponent createSimpleAttributes () {
        return AttributeModifiersComponent.builder()
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", 3f, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
            .add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -2.4f, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
            .build();
    }

    @Override
    public boolean canMine (BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
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
