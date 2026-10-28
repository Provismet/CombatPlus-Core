package com.provismet.CombatPlusCore.debug.registries;

import com.provismet.CombatPlusCore.CombatPlusCoreMain;
import com.provismet.CombatPlusCore.api.enchantment.effect.doubleEntity.CodeExecutionDoubleEntityEffect;
import com.provismet.CombatPlusCore.registries.CPCEnchantmentComponentTypes;
import com.provismet.CombatPlusCore.api.tag.CPCItemTags;
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
                itemLookup.getOrThrow(CPCItemTags.DAMAGE_ENCHANTABLE),
                1,
                1,
                Enchantment.constantCost(1),
                Enchantment.constantCost(1),
                1,
                EquipmentSlotGroup.MAINHAND
            )
        ).withEffect(
            CPCEnchantmentComponentTypes.POST_CHARGED_ATTACK,
            new CodeExecutionDoubleEntityEffect(CombatPlusCoreMain.identifier("log-charged"))
        ).withEffect(
            CPCEnchantmentComponentTypes.POST_CRITICAL_ATTACK,
            new CodeExecutionDoubleEntityEffect(CombatPlusCoreMain.identifier("log-critical"))
        ).withEffect(
            CPCEnchantmentComponentTypes.POST_KILL,
            new CodeExecutionDoubleEntityEffect(CombatPlusCoreMain.identifier("log-kill"))
        )
    );

    private static final EnchantmentContainer SHIELD_LOGGER = new EnchantmentContainer(
        CombatPlusCoreMain.identifier("shield_logger"),
        (itemLookup, enchantmentLookup, damageLookup, blockLookup, entityLookup) -> Enchantment.enchantment(
            Enchantment.definition(
                itemLookup.getOrThrow(CPCItemTags.SHIELD_ENCHANTABLE),
                1,
                1,
                Enchantment.constantCost(1),
                Enchantment.constantCost(1),
                1,
                EquipmentSlotGroup.HAND
            )
        ).withEffect(
            CPCEnchantmentComponentTypes.POST_BLOCK,
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
