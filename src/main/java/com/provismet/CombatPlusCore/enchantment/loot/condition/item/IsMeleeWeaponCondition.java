package com.provismet.CombatPlusCore.enchantment.loot.condition.item;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.enchantment.loot.condition.ItemCondition;
import com.provismet.CombatPlusCore.interfaces.MeleeWeapon;
import com.provismet.CombatPlusCore.registries.ItemConditionTypes;
import com.provismet.CombatPlusCore.utility.tag.CPCItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record IsMeleeWeaponCondition () implements ItemCondition {
    public static final MapCodec<IsMeleeWeaponCondition> CODEC = MapCodec.unit(IsMeleeWeaponCondition::new);

    @Override
    public LootItemConditionType getType () {
        return ItemConditionTypes.IS_MELEE_WEAPON;
    }

    @Override
    public boolean test (LootContext lootContext) {
        ItemStack item = lootContext.getOptionalParameter(LootContextParams.TOOL);
        return item.is(CPCItemTags.MELEE_WEAPON) || item.getItem() instanceof MeleeWeapon;
    }

    public static ItemCondition.Builder builder () {
        return IsMeleeWeaponCondition::new;
    }
}
