package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.interfaces.DualWeapon;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SwordItem.class)
public abstract class SwordItemMixin extends ToolItem implements DualWeapon {
    protected SwordItemMixin (ToolMaterial material, Settings settings) {
        super(material, settings);
    }
}
