package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.interfaces.MeleeWeapon;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AxeItem.class)
public class AxeItemMixin extends MiningToolItem implements MeleeWeapon {
    protected AxeItemMixin (ToolMaterial material, TagKey<Block> effectiveBlocks, Settings settings) {
        super(material, effectiveBlocks, settings);
    }

    @Override
    public float getWeaponDamage (ItemStack itemStack) {
        AttributeModifiersComponent attributes = itemStack.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);
        double bonusDamage = 0f;
        for (AttributeModifiersComponent.Entry entry : attributes.modifiers()) {
            if (entry.attribute() == EntityAttributes.GENERIC_ATTACK_DAMAGE && entry.modifier().operation() == EntityAttributeModifier.Operation.ADD_VALUE) {
                bonusDamage += entry.modifier().value();
            }
        }
        return (float)bonusDamage;
    }
}
