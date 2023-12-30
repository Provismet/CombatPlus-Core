package com.provismet.CombatPlusCore.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.provismet.CombatPlusCore.interfaces.MeleeWeapon;

import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.TagKey;

@Mixin(AxeItem.class)
public class AxeItemMixin extends MiningToolItem implements MeleeWeapon {
    protected AxeItemMixin(float attackDamage, float attackSpeed, ToolMaterial material, TagKey<Block> effectiveBlocks, Settings settings) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
    }

    @Override
    public float getWeaponDamage () {
        return this.getAttackDamage();
    }
}
