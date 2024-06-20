package com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.registries.CPCSingleEntityLootConditionTypes;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.entity.Entity;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;

/**
 * Returns true is the entity's age, in ticks, is divisible by the provided value (rounded down).
 *
 * @param value The value to divide by.
 */
public record TickModulo (EnchantmentLevelBasedValue value) implements SingleEntityCondition {
    public static final MapCodec<TickModulo> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(EnchantmentLevelBasedValue.CODEC.fieldOf("value").forGetter(TickModulo::value)).apply(instance, TickModulo::new));

    @Override
    public LootConditionType getType () {
        return CPCSingleEntityLootConditionTypes.TICK_MODULO;
    }

    @Override
    public boolean test (LootContext lootContext) {
        Entity entity = lootContext.get(LootContextParameters.THIS_ENTITY);
        int modulo = (int)this.value.getValue(lootContext.get(LootContextParameters.ENCHANTMENT_LEVEL));
        return entity.age % modulo == 0;
    }

    public static SingleEntityCondition.Builder builder (EnchantmentLevelBasedValue value) {
        return () -> new TickModulo(value);
    }
}
