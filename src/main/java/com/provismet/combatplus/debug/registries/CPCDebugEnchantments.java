package com.provismet.combatplus.debug.registries;

import com.provismet.combatplus.CombatPlusCoreMain;
import com.provismet.combatplus.api.enchantment.effect.doubleEntity.CodeExecutionDoubleEntityEffect;
import com.provismet.combatplus.registries.CombatPlusEnchantmentComponentTypes;
import com.provismet.combatplus.api.tag.CombatPlusItemTags;
import com.provismet.lilylib.container.EnchantmentContainer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import java.util.Optional;

public class CPCDebugEnchantments {
    private static final EnchantmentContainer LOGGER = new EnchantmentContainer(
        CombatPlusCoreMain.identifier("logger"),
        (itemLookup, enchantmentLookup, damageLookup, blockLookup, entityLookup) -> Enchantment.enchantment(
            Enchantment.definition(
                itemLookup.getOrThrow(CombatPlusItemTags.DAMAGE_ENCHANTABLE),
                1,
                1,
                Enchantment.constantCost(1),
                Enchantment.constantCost(1),
                1,
                EquipmentSlotGroup.MAINHAND
            )
        ).withEffect(
            CombatPlusEnchantmentComponentTypes.POST_CHARGED_ATTACK,
            new CodeExecutionDoubleEntityEffect(CombatPlusCoreMain.identifier("log-charged"))
        ).withEffect(
            CombatPlusEnchantmentComponentTypes.POST_CRITICAL_ATTACK,
            new CodeExecutionDoubleEntityEffect(CombatPlusCoreMain.identifier("log-critical"))
        ).withEffect(
            CombatPlusEnchantmentComponentTypes.POST_KILL,
            new CodeExecutionDoubleEntityEffect(CombatPlusCoreMain.identifier("log-kill"))
        )
    );

    private static final EnchantmentContainer SHIELD_LOGGER = new EnchantmentContainer(
        CombatPlusCoreMain.identifier("shield_logger"),
        (itemLookup, enchantmentLookup, damageLookup, blockLookup, entityLookup) -> Enchantment.enchantment(
            Enchantment.definition(
                itemLookup.getOrThrow(CombatPlusItemTags.SHIELD_ENCHANTABLE),
                1,
                1,
                Enchantment.constantCost(1),
                Enchantment.constantCost(1),
                1,
                EquipmentSlotGroup.HAND
            )
        ).withEffect(
            CombatPlusEnchantmentComponentTypes.POST_BLOCK,
            new CodeExecutionDoubleEntityEffect(CombatPlusCoreMain.identifier("log-block"))
        )
    );

    public static Optional<EnchantmentContainer> getDebugContainer () {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) return Optional.of(LOGGER);
        return Optional.empty();
    }

    public static Optional<EnchantmentContainer> getShieldDebugContainer () {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) return Optional.of(SHIELD_LOGGER);
        return Optional.empty();
    }
}
