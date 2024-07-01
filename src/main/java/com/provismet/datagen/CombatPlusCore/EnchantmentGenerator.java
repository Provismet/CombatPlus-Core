package com.provismet.datagen.CombatPlusCore;

import com.provismet.CombatPlusCore.debug.registries.CPCDebugEnchantments;
import com.provismet.CombatPlusCore.utility.resource.DevModeResourceCondition;
import com.provismet.CombatPlusCore.utility.tag.CPCItemTags;
import com.provismet.datagen.CombatPlusCore.provider.CPCEnchantmentProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.enchantment.effect.entity.ApplyMobEffectEnchantmentEffect;
import net.minecraft.enchantment.effect.entity.IgniteEnchantmentEffect;
import net.minecraft.enchantment.effect.value.AddEnchantmentEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.loot.condition.DamageSourcePropertiesLootCondition;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.EntityTypePredicate;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class EnchantmentGenerator extends CPCEnchantmentProvider {
    public EnchantmentGenerator (FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure (RegistryWrapper.WrapperLookup registries, Entries entries, EnchantmentBuilder builder) {
        if (CPCDebugEnchantments.getDebugContainer().isPresent()) {
            builder.add(
                CPCDebugEnchantments.getDebugContainer().get(),
                new DevModeResourceCondition()
            );
        }

        builder.add(
            Enchantments.SHARPNESS,
            Enchantment.builder(
                Enchantment.definition(
                    builder.getItemEntryList(ItemTags.SHARP_WEAPON_ENCHANTABLE),
                    builder.getItemEntryList(CPCItemTags.DAMAGE_PRIMARY_ENCHANTABLE),
                    10,
                    5,
                    Enchantment.leveledCost(1, 11),
                    Enchantment.leveledCost(21, 11),
                    1,
                    AttributeModifierSlot.MAINHAND
                )
            ).exclusiveSet(
                builder.getEnchantmentEntryList(EnchantmentTags.DAMAGE_EXCLUSIVE_SET)
            ).addEffect(
                EnchantmentEffectComponentTypes.DAMAGE,
                new AddEnchantmentEffect(
                    EnchantmentLevelBasedValue.linear(1.0f, 0.5f)
                )
            )
        );

        builder.add(
            Enchantments.SMITE,
            Enchantment.builder(
                Enchantment.definition(
                    builder.getItemEntryList(CPCItemTags.DAMAGE_ENCHANTABLE),
                    builder.getItemEntryList(CPCItemTags.DAMAGE_PRIMARY_ENCHANTABLE),
                    5,
                    5,
                    Enchantment.leveledCost(5, 8),
                    Enchantment.leveledCost(25, 8),
                    2,
                    AttributeModifierSlot.MAINHAND
                )
            ).exclusiveSet(
                builder.getEnchantmentEntryList(EnchantmentTags.DAMAGE_EXCLUSIVE_SET)
            ).addEffect(
                EnchantmentEffectComponentTypes.DAMAGE,
                new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(2.5f)),
                EntityPropertiesLootCondition.builder(
                    LootContext.EntityTarget.THIS,
                    EntityPredicate.Builder.create().type(EntityTypePredicate.create(EntityTypeTags.SENSITIVE_TO_SMITE))
                )
            )
        );

        builder.add(
            Enchantments.BANE_OF_ARTHROPODS,
            Enchantment.builder(
                Enchantment.definition(
                    builder.getItemEntryList(CPCItemTags.DAMAGE_ENCHANTABLE),
                    builder.getItemEntryList(CPCItemTags.DAMAGE_PRIMARY_ENCHANTABLE),
                    5,
                    5,
                    Enchantment.leveledCost(5, 8),
                    Enchantment.leveledCost(25, 8),
                    2,
                    AttributeModifierSlot.MAINHAND)
            ).exclusiveSet(
                builder.getEnchantmentEntryList(EnchantmentTags.DAMAGE_EXCLUSIVE_SET)
            ).addEffect(
                EnchantmentEffectComponentTypes.DAMAGE,
                new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(2.5f)),
                EntityPropertiesLootCondition.builder(
                    LootContext.EntityTarget.THIS,
                    EntityPredicate.Builder.create().type(EntityTypePredicate.create(EntityTypeTags.SENSITIVE_TO_BANE_OF_ARTHROPODS))
                )
            ).addEffect(
                EnchantmentEffectComponentTypes.POST_ATTACK,
                EnchantmentEffectTarget.ATTACKER,
                EnchantmentEffectTarget.VICTIM,
                new ApplyMobEffectEnchantmentEffect(
                    RegistryEntryList.of(StatusEffects.SLOWNESS),
                    EnchantmentLevelBasedValue.constant(1.5f),
                    EnchantmentLevelBasedValue.linear(1.5f, 0.5f),
                    EnchantmentLevelBasedValue.constant(3.0f),
                    EnchantmentLevelBasedValue.constant(3.0f)
                ),
                EntityPropertiesLootCondition.builder(
                    LootContext.EntityTarget.THIS,
                    EntityPredicate.Builder.create().type(EntityTypePredicate.create(EntityTypeTags.SENSITIVE_TO_BANE_OF_ARTHROPODS))
                ).and(
                    DamageSourcePropertiesLootCondition.builder(DamageSourcePredicate.Builder.create().isDirect(true))
                )
            )
        );

        builder.add(
            Enchantments.FIRE_ASPECT,
            Enchantment.builder(
                Enchantment.definition(
                    builder.getItemEntryList(CPCItemTags.ASPECT_ENCHANTABLE),
                    builder.getItemEntryList(CPCItemTags.ASPECT_PRIMARY_ENCHANTABLE),
                    2,
                    2,
                    Enchantment.leveledCost(10, 20),
                    Enchantment.leveledCost(60, 20),
                    4,
                    AttributeModifierSlot.MAINHAND
                )
            ).addEffect(
                EnchantmentEffectComponentTypes.POST_ATTACK,
                EnchantmentEffectTarget.ATTACKER,
                EnchantmentEffectTarget.VICTIM,
                new IgniteEnchantmentEffect(EnchantmentLevelBasedValue.linear(4.0f)),
                DamageSourcePropertiesLootCondition.builder(DamageSourcePredicate.Builder.create().isDirect(true))
            )
        );
    }
}
