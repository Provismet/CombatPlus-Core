package com.provismet.CombatPlusCore.enchantment.loot.condition.item;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.loot.condition.ItemCondition;
import com.provismet.CombatPlusCore.registries.ItemConditionTypes;
import com.provismet.CombatPlusCore.utility.CPCRegistries;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.function.Predicate;

public record ItemLambdaCondition (Identifier function) implements ItemCondition {
    public static final MapCodec<ItemLambdaCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Identifier.CODEC.fieldOf("function").forGetter(ItemLambdaCondition::function)).apply(instance, ItemLambdaCondition::new));

    @Override
    public LootConditionType getType () {
        return ItemConditionTypes.LAMBDA;
    }

    @Override
    public boolean test (LootContext lootContext) {
        Optional<Predicate<ItemStack>> predicate = CPCRegistries.ITEM_CONDITION.getOrEmpty(this.function);
        if (predicate.isPresent()) return predicate.get().test(lootContext.get(LootContextParameters.TOOL));
        return false;
    }
}
