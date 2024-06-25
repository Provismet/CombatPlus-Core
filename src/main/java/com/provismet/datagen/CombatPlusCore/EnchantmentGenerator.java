package com.provismet.datagen.CombatPlusCore;

import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.enchantment.component.CPCEnchantmentComponents;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.CodeExecutionDoubleEntityEnchantmentEffect;
import com.provismet.CombatPlusCore.utility.resource.DevModeResourceCondition;
import com.provismet.CombatPlusCore.utility.tag.CPCItemTags;
import com.provismet.datagen.CombatPlusCore.provider.CPCEnchantmentProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryWrapper;

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
