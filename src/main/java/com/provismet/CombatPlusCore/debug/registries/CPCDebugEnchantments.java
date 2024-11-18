package com.provismet.CombatPlusCore.debug.registries;

import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.CodeExecutionDoubleEntityEffect;
import com.provismet.CombatPlusCore.registries.CPCEnchantmentComponentTypes;
import com.provismet.CombatPlusCore.utility.tag.CPCItemTags;
import com.provismet.lilylib.container.EnchantmentContainer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;

import java.util.Optional;

public class CPCDebugEnchantments {
    private static final EnchantmentContainer LOGGER = new EnchantmentContainer(
        CPCMain.identifier("logger"),
        (itemLookup, enchantmentLookup, damageLookup, blockLookup, entityLookup) -> Enchantment.builder(
            Enchantment.definition(
                itemLookup.getOrThrow(CPCItemTags.DAMAGE_ENCHANTABLE),
                1,
                1,
                Enchantment.constantCost(1),
                Enchantment.constantCost(1),
                1,
                AttributeModifierSlot.MAINHAND
            )
        ).addEffect(
            CPCEnchantmentComponentTypes.POST_CHARGED_ATTACK,
            new CodeExecutionDoubleEntityEffect(CPCMain.identifier("log-charged"))
        ).addEffect(
            CPCEnchantmentComponentTypes.POST_CRITICAL_ATTACK,
            new CodeExecutionDoubleEntityEffect(CPCMain.identifier("log-critical"))
        ).addEffect(
            CPCEnchantmentComponentTypes.POST_KILL,
            new CodeExecutionDoubleEntityEffect(CPCMain.identifier("log-kill"))
        )
    );

    public static Optional<EnchantmentContainer> getDebugContainer () {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) return Optional.of(LOGGER);
        return Optional.empty();
    }
}
