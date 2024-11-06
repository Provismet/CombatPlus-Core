package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.interfaces.MeleeWeapon;
import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin extends MiningToolItem implements MeleeWeapon {
    public AxeItemMixin (ToolMaterial material, TagKey<Block> effectiveBlocks, float attackDamage, float attackSpeed, Settings settings) {
        super(material, effectiveBlocks, attackDamage, attackSpeed, settings);
    }
}
