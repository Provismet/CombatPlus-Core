package com.provismet.CombatPlusCore.api.loot.condition.item;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.api.loot.condition.ItemCondition;
import com.provismet.CombatPlusCore.api.item.interfaces.DualWeapon;
import com.provismet.CombatPlusCore.api.tag.CPCItemTags;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

public record IsDualWeaponCondition () implements ItemCondition {
    public static final MapCodec<IsDualWeaponCondition> CODEC = MapCodec.unit(IsDualWeaponCondition::new);

    @Override
    public boolean test (LootContext lootContext) {
        ItemInstance item = lootContext.getOptionalParameter(LootContextParams.TOOL);
        if (item == null) return false;
        return item.is(CPCItemTags.DUAL_WEAPON) || (item instanceof ItemStack stack && stack.getItem() instanceof DualWeapon);
    }

    public static ItemCondition.Builder builder () {
        return IsDualWeaponCondition::new;
    }

    @Override
    @NotNull
    public MapCodec<? extends LootItemCondition> codec () {
        return CODEC;
    }
}
