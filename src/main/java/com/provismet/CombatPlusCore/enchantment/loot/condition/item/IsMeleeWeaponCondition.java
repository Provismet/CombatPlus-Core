package com.provismet.CombatPlusCore.enchantment.loot.condition.item;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.enchantment.loot.condition.ItemCondition;
import com.provismet.CombatPlusCore.interfaces.MeleeWeapon;
import com.provismet.CombatPlusCore.registries.ItemConditionTypes;
import com.provismet.CombatPlusCore.utility.tag.CPCItemTags;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;

public record IsMeleeWeaponCondition() implements ItemCondition {
    public static final MapCodec<IsMeleeWeaponCondition> CODEC = MapCodec.unit(IsMeleeWeaponCondition::new);

    @Override
    public LootConditionType getType () {
        return ItemConditionTypes.IS_MELEE_WEAPON;
    }

    @Override
    public boolean test (LootContext lootContext) {
        ItemStack item = lootContext.get(LootContextParameters.TOOL);
        return item.isIn(CPCItemTags.MELEE_WEAPON) || item.getItem() instanceof MeleeWeapon;
    }

    public static ItemCondition.Builder builder () {
        return IsMeleeWeaponCondition::new;
    }
}
