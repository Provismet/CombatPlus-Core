package com.provismet.CombatPlusCore.api.loot.condition.item;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.CombatPlusCoreMain;
import com.provismet.CombatPlusCore.api.loot.condition.ItemCondition;
import com.provismet.CombatPlusCore.utility.CPCRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public record ItemLambdaCondition (Identifier function) implements ItemCondition {
    public static final MapCodec<ItemLambdaCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Identifier.CODEC.fieldOf("function").forGetter(ItemLambdaCondition::function)).apply(instance, ItemLambdaCondition::new));

    @Override
    public boolean test (LootContext lootContext) {
        Predicate<ItemStack> predicate = CPCRegistries.ITEM_LAMBDA_CONDITION.getValue(this.function);
        if (predicate == null) {
            CombatPlusCoreMain.LOGGER.warn("Enchantment attempted to execute unregistered lambda function: {}", this.function.toString());
            return false;
        }
        ItemInstance instance = lootContext.getOptionalParameter(LootContextParams.TOOL);
        if (instance instanceof ItemStack stack) return predicate.test(stack);
        return false;
    }

    public static ItemCondition.Builder builder (Identifier function) {
        return () -> new ItemLambdaCondition(function);
    }

    @Override
    @NotNull
    public MapCodec<? extends LootItemCondition> codec () {
        return CODEC;
    }
}
