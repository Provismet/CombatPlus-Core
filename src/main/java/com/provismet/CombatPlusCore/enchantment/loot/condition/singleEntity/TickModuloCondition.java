package com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.registries.SingleEntityLootConditionTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

/**
 * Returns true is the entity's age, in ticks, is divisible by the provided value (rounded down).
 *
 * @param interval The value to divide by.
 */
public record TickModuloCondition (LevelBasedValue interval) implements SingleEntityCondition {
    public static final MapCodec<TickModuloCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(LevelBasedValue.CODEC.fieldOf("value").forGetter(TickModuloCondition::interval)).apply(instance, TickModuloCondition::new));

    @Override
    public LootItemConditionType getType () {
        return SingleEntityLootConditionTypes.TICK_MODULO;
    }

    @Override
    public boolean test (LootContext lootContext) {
        Entity entity = lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY);
        int modulo = (int)this.interval.calculate(lootContext.getOptionalParameter(LootContextParams.ENCHANTMENT_LEVEL));
        return entity.tickCount % modulo == 0;
    }

    public static SingleEntityCondition.Builder builder (LevelBasedValue value) {
        return () -> new TickModuloCondition(value);
    }
}
