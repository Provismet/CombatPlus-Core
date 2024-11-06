package com.provismet.CombatPlusCore.enchantment.loot.condition.item;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.CPCMain;
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
        Predicate<ItemStack> predicate = CPCRegistries.ITEM_LAMBDA_CONDITION.get(this.function);
        if (predicate == null) {
            CPCMain.LOGGER.warn("Enchantment attempted to execute unregistered lambda function: {}", this.function.toString());
            return false;
        }
        return predicate.test(lootContext.get(LootContextParameters.TOOL));
    }

    public static ItemCondition.Builder builder (Identifier function) {
        return () -> new ItemLambdaCondition(function);
    }
}
