package com.provismet.CombatPlusCore.datagen.generator;

import com.provismet.CombatPlusCore.CombatPlusCoreMain;
import com.provismet.CombatPlusCore.debug.registries.CPCDebugEnchantments;
import com.provismet.CombatPlusCore.api.enchantment.effect.component.CooldownGroupComponentEntityEffect;
import com.provismet.CombatPlusCore.registries.CPCEnchantmentComponentTypes;
import com.provismet.CombatPlusCore.api.tag.CPCItemTags;
import com.provismet.lilylib.datagen.condition.DevModeResourceCondition;
import com.provismet.lilylib.datagen.provider.LilyEnchantmentProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.advancements.criterion.DamageSourcePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityTypePredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.ApplyMobEffect;
import net.minecraft.world.item.enchantment.effects.Ignite;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;

import java.util.concurrent.CompletableFuture;

public class EnchantmentGenerator extends LilyEnchantmentProvider {
    public EnchantmentGenerator (FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void generate (HolderLookup.Provider registries, EnchantmentBuilder builder) {
        CPCDebugEnchantments.getDebugContainer().ifPresent(enchantment -> builder.add(enchantment, new DevModeResourceCondition()));
        CPCDebugEnchantments.getShieldDebugContainer().ifPresent(enchantment -> builder.add(enchantment, new DevModeResourceCondition()));

        builder.add(
            Enchantments.SHARPNESS,
            Enchantment.enchantment(
                Enchantment.definition(
                    builder.getItemEntryList(ItemTags.SHARP_WEAPON_ENCHANTABLE),
                    builder.getItemEntryList(CPCItemTags.DAMAGE_PRIMARY_ENCHANTABLE),
                    10,
                    5,
                    Enchantment.dynamicCost(1, 11),
                    Enchantment.dynamicCost(21, 11),
                    1,
                    EquipmentSlotGroup.MAINHAND
                )
            ).exclusiveWith(
                builder.getEnchantmentEntryList(EnchantmentTags.DAMAGE_EXCLUSIVE)
            ).withEffect(
                EnchantmentEffectComponents.DAMAGE,
                new AddValue(
                    LevelBasedValue.perLevel(1.0f, 0.5f)
                )
            )
        );

        builder.add(
            Enchantments.SMITE,
            Enchantment.enchantment(
                Enchantment.definition(
                    builder.getItemEntryList(CPCItemTags.DAMAGE_ENCHANTABLE),
                    builder.getItemEntryList(CPCItemTags.DAMAGE_PRIMARY_ENCHANTABLE),
                    5,
                    5,
                    Enchantment.dynamicCost(5, 8),
                    Enchantment.dynamicCost(25, 8),
                    2,
                    EquipmentSlotGroup.MAINHAND
                )
            ).exclusiveWith(
                builder.getEnchantmentEntryList(EnchantmentTags.DAMAGE_EXCLUSIVE)
            ).withEffect(
                CPCEnchantmentComponentTypes.GAMERULE_DAMAGE,
                new AddValue(LevelBasedValue.perLevel(2.5f)),
                LootItemEntityPropertyCondition.hasProperties(
                    LootContext.EntityTarget.THIS,
                    EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(registries.lookupOrThrow(Registries.ENTITY_TYPE), EntityTypeTags.SENSITIVE_TO_SMITE))
                )
            )
        );

        builder.add(
            Enchantments.BANE_OF_ARTHROPODS,
            Enchantment.enchantment(
                Enchantment.definition(
                    builder.getItemEntryList(CPCItemTags.DAMAGE_ENCHANTABLE),
                    builder.getItemEntryList(CPCItemTags.DAMAGE_PRIMARY_ENCHANTABLE),
                    5,
                    5,
                    Enchantment.dynamicCost(5, 8),
                    Enchantment.dynamicCost(25, 8),
                    2,
                    EquipmentSlotGroup.MAINHAND)
            ).exclusiveWith(
                builder.getEnchantmentEntryList(EnchantmentTags.DAMAGE_EXCLUSIVE)
            ).withEffect(
                CPCEnchantmentComponentTypes.GAMERULE_DAMAGE,
                new AddValue(LevelBasedValue.perLevel(2.5f)),
                LootItemEntityPropertyCondition.hasProperties(
                    LootContext.EntityTarget.THIS,
                    EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(registries.lookupOrThrow(Registries.ENTITY_TYPE), EntityTypeTags.SENSITIVE_TO_BANE_OF_ARTHROPODS))
                )
            ).withEffect(
                EnchantmentEffectComponents.POST_ATTACK,
                EnchantmentTarget.ATTACKER,
                EnchantmentTarget.VICTIM,
                new ApplyMobEffect(
                    HolderSet.direct(MobEffects.SLOWNESS),
                    LevelBasedValue.constant(1.5f),
                    LevelBasedValue.perLevel(1.5f, 0.5f),
                    LevelBasedValue.constant(3.0f),
                    LevelBasedValue.constant(3.0f)
                ),
                LootItemEntityPropertyCondition.hasProperties(
                    LootContext.EntityTarget.THIS,
                    EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(registries.lookupOrThrow(Registries.ENTITY_TYPE), EntityTypeTags.SENSITIVE_TO_BANE_OF_ARTHROPODS))
                ).and(
                    DamageSourceCondition.hasDamageSource(DamageSourcePredicate.Builder.damageType().isDirect(true))
                )
            )
        );

        builder.add(
            Enchantments.FIRE_ASPECT,
            Enchantment.enchantment(
                Enchantment.definition(
                    builder.getItemEntryList(CPCItemTags.ASPECT_ENCHANTABLE),
                    builder.getItemEntryList(CPCItemTags.ASPECT_PRIMARY_ENCHANTABLE),
                    2,
                    2,
                    Enchantment.dynamicCost(10, 20),
                    Enchantment.dynamicCost(60, 20),
                    4,
                    EquipmentSlotGroup.MAINHAND
                )
            ).withEffect(
                EnchantmentEffectComponents.POST_ATTACK,
                EnchantmentTarget.ATTACKER,
                EnchantmentTarget.VICTIM,
                new Ignite(LevelBasedValue.perLevel(4.0f)),
                DamageSourceCondition.hasDamageSource(DamageSourcePredicate.Builder.damageType().isDirect(true))
            )
        );

        builder.add(
            Enchantments.LOYALTY,
            Enchantment.enchantment(
                Enchantment.definition(
                    builder.getItemEntryList(ItemTags.TRIDENT_ENCHANTABLE),
                    5,
                    3,
                    Enchantment.dynamicCost(12, 7),
                    Enchantment.constantCost(50),
                    2,
                    EquipmentSlotGroup.MAINHAND
                )
            ).withEffect(
                EnchantmentEffectComponents.TRIDENT_RETURN_ACCELERATION, new AddValue(LevelBasedValue.perLevel(1.0F))
            ).withEffect(
                CPCEnchantmentComponentTypes.DATA_COMPONENT, new CooldownGroupComponentEntityEffect(CombatPlusCoreMain.identifier("loyalty_trident"))
            )
        );
    }
}
