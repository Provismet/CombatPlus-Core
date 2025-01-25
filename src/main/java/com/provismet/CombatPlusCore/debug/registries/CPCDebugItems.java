package com.provismet.CombatPlusCore.debug.registries;

import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.debug.items.DebugShield;
import com.provismet.CombatPlusCore.debug.items.DebuggerItem;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class CPCDebugItems {
    private static final Item DEBUGGER = new DebuggerItem(new Item.Settings().maxCount(1).attributeModifiers(DebuggerItem.createSimpleAttributes()));
    private static final Item DEBUG_SHIELD = new DebugShield(new Item.Settings().maxCount(1).maxDamage(500));

    public static void register () {
        Registry.register(Registries.ITEM, CPCMain.identifier("debugger"), DEBUGGER);
        Registry.register(Registries.ITEM, CPCMain.identifier("debug_shield"), DEBUG_SHIELD);

        ModelPredicateProviderRegistry.register(DEBUG_SHIELD, Identifier.ofVanilla("blocking"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f);
    }

    public static Optional<Item> getOptionalDebugItem () {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) return Optional.of(DEBUGGER);
        else return Optional.empty();
    }

    public static Optional<Item> getOptionalDebugShield () {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) return Optional.of(DEBUG_SHIELD);
        else return Optional.empty();
    }
}
