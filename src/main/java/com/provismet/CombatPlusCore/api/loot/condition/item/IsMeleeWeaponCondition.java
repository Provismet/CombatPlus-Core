package com.provismet.CombatPlusCore.api.loot.condition.item;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.api.loot.condition.ItemCondition;
import com.provismet.CombatPlusCore.api.item.interfaces.MeleeWeapon;
import com.provismet.CombatPlusCore.api.tag.CombatPlusItemTags;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

public record IsMeleeWeaponCondition () implements ItemCondition {
    public static final MapCodec<IsMeleeWeaponCondition> CODEC = MapCodec.unit(IsMeleeWeaponCondition::new);

    @Override
    public boolean test (LootContext lootContext) {
        ItemInstance item = lootContext.getOptionalParameter(LootContextParams.TOOL);
        if (item == null) return false;
        return item.is(CombatPlusItemTags.MELEE_WEAPON) || (item instanceof ItemStack stack && stack.getItem() instanceof MeleeWeapon);
    }

    public static ItemCondition.Builder builder () {
        return IsMeleeWeaponCondition::new;
    }

    @Override
    @NotNull
    public MapCodec<? extends LootItemCondition> codec () {
        return CODEC;
    }
}
