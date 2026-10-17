package com.provismet.CombatPlusCore.enchantment.loot.condition.item;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.enchantment.loot.condition.ItemCondition;
import com.provismet.CombatPlusCore.interfaces.DualWeapon;
import com.provismet.CombatPlusCore.registries.ItemConditionTypes;
import com.provismet.CombatPlusCore.utility.tag.CPCItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record IsDualWeaponCondition () implements ItemCondition {
    public static final MapCodec<IsDualWeaponCondition> CODEC = MapCodec.unit(IsDualWeaponCondition::new);

    @Override
    public LootItemConditionType getType () {
        return ItemConditionTypes.IS_DUAL_WEAPON;
    }

    @Override
    public boolean test (LootContext lootContext) {
        ItemStack item = lootContext.getOptionalParameter(LootContextParams.TOOL);
        return item.is(CPCItemTags.DUAL_WEAPON) || item.getItem() instanceof DualWeapon;
    }

    public static ItemCondition.Builder builder () {
        return IsDualWeaponCondition::new;
    }
}
