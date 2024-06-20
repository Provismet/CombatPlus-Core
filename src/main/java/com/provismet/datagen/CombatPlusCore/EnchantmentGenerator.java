package com.provismet.datagen.CombatPlusCore;

import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.enchantment.component.CPCEnchantmentComponents;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.ApplyToTargetEntityEnchantmentEffect;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.ApplyToUserEntityEnchantmentEffect;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.CodeExecutionDoubleEntityEnchantmentEffect;
import com.provismet.CombatPlusCore.enchantment.effect.singleEntity.ApplyVelocityEnchantmentEffect;
import com.provismet.CombatPlusCore.enchantment.effect.singleEntity.HealEnchantmentEffect;
import com.provismet.CombatPlusCore.enchantment.loot.condition.doubleEntity.ApplyToTargetCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.doubleEntity.ApplyToUserCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.DimensionCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.ExposedToSun;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.RelativeHealthCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.TickModulo;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContext;
import com.provismet.CombatPlusCore.utility.resource.CPCResourceConditions;
import com.provismet.CombatPlusCore.utility.resource.DevModeResourceCondition;
import com.provismet.CombatPlusCore.utility.tag.CPCItemTags;
import com.provismet.datagen.CombatPlusCore.provider.CPCEnchantmentProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.AttributeEnchantmentEffect;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.enchantment.effect.entity.IgniteEnchantmentEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.EntityFlagsPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class EnchantmentGenerator extends CPCEnchantmentProvider {
    public EnchantmentGenerator (FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure (RegistryWrapper.WrapperLookup registries, Entries entries, EnchantmentBuilder builder) {
        builder.add(
            CPCMain.identifier("logger"),
            Enchantment.builder(
                Enchantment.definition(
                    builder.getItemEntryList(CPCItemTags.DAMAGE_ENCHANTABLE),
                    1,
                    1,
                    Enchantment.constantCost(1),
                    Enchantment.constantCost(1),
                    1,
                    AttributeModifierSlot.MAINHAND
                )
            ).addEffect(
                CPCEnchantmentComponents.POST_CHARGED_ATTACK,
                new CodeExecutionDoubleEntityEnchantmentEffect(CPCMain.identifier("log-charged"))
            ).addEffect(
                CPCEnchantmentComponents.POST_CRITICAL_ATTACK,
                new CodeExecutionDoubleEntityEnchantmentEffect(CPCMain.identifier("log-critical"))
            ).addEffect(
                CPCEnchantmentComponents.POST_KILL,
                new CodeExecutionDoubleEntityEnchantmentEffect(CPCMain.identifier("log-kill"))
            ),
            new DevModeResourceCondition()
        );
    }
}
