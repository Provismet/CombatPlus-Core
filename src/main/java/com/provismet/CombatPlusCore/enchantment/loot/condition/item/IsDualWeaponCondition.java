package com.provismet.CombatPlusCore.enchantment.loot.condition.item;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.enchantment.loot.condition.ItemCondition;
import com.provismet.CombatPlusCore.interfaces.DualWeapon;
import com.provismet.CombatPlusCore.registries.ItemConditionTypes;
import com.provismet.CombatPlusCore.utility.tag.CPCItemTags;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;

public record IsDualWeaponCondition () implements ItemCondition {
    public static final MapCodec<IsDualWeaponCondition> CODEC = MapCodec.unit(IsDualWeaponCondition::new);

    @Override
    public LootConditionType getType () {
        return ItemConditionTypes.IS_DUAL_WEAPON;
    }

    @Override
    public boolean test (LootContext lootContext) {
        ItemStack item = lootContext.get(LootContextParameters.TOOL);
        return item.isIn(CPCItemTags.DUAL_WEAPON) || item.getItem() instanceof DualWeapon;
    }

    public static ItemCondition.Builder builder () {
        return IsDualWeaponCondition::new;
    }
}
