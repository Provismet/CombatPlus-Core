package com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.registries.SingleEntityLootConditionTypes;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.entity.Entity;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;

/**
 * Returns true is the entity's age, in ticks, is divisible by the provided value (rounded down).
 *
 * @param interval The value to divide by.
 */
public record TickModuloCondition(EnchantmentLevelBasedValue interval) implements SingleEntityCondition {
    public static final MapCodec<TickModuloCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(EnchantmentLevelBasedValue.CODEC.fieldOf("value").forGetter(TickModuloCondition::interval)).apply(instance, TickModuloCondition::new));

    @Override
    public LootConditionType getType () {
        return SingleEntityLootConditionTypes.TICK_MODULO;
    }

    @Override
    public boolean test (LootContext lootContext) {
        Entity entity = lootContext.get(LootContextParameters.THIS_ENTITY);
        int modulo = (int)this.interval.getValue(lootContext.get(LootContextParameters.ENCHANTMENT_LEVEL));
        return entity.age % modulo == 0;
    }

    public static SingleEntityCondition.Builder builder (EnchantmentLevelBasedValue value) {
        return () -> new TickModuloCondition(value);
    }
}
