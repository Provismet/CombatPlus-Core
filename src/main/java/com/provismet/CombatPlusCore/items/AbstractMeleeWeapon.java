package com.provismet.CombatPlusCore.items;

import com.provismet.CombatPlusCore.interfaces.MeleeWeapon;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractMeleeWeapon extends ToolItem implements MeleeWeapon {
    private float weaponDamage = 0f;
    private boolean cachedWeaponDamage = false;

    protected AbstractMeleeWeapon (ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public boolean canMine (BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public boolean postHit (ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
        return true;
    }

    // Default getter for weapon damage. Override this if your weapon requires custom damage values.
    @Override
    public float getWeaponDamage (ItemStack itemStack) {
        if (this.cachedWeaponDamage) return this.weaponDamage;

        AttributeModifiersComponent attributes = itemStack.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);
        double bonusDamage = 0f;
        for (AttributeModifiersComponent.Entry entry : attributes.modifiers()) {
            if (entry.attribute() == EntityAttributes.GENERIC_ATTACK_DAMAGE && entry.modifier().operation() == EntityAttributeModifier.Operation.ADD_VALUE) {
                bonusDamage += entry.modifier().value();
            }
        }
        this.weaponDamage = (float)bonusDamage;
        this.cachedWeaponDamage = true;
        return this.weaponDamage;
    }
}
