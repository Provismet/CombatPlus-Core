package com.provismet.CombatPlusCore.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.provismet.CombatPlusCore.interfaces.DualWeapon;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;

@Mixin(SwordItem.class)
public abstract class SwordItemMixin extends ToolItem implements DualWeapon {
    protected SwordItemMixin (ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Shadow
    public abstract float getAttackDamage();

    @Override
    public float getWeaponDamage () {
        return this.getAttackDamage();
    }
}
