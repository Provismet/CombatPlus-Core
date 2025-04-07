package com.provismet.CombatPlusCore.debug.registries;

import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.debug.items.DebugShield;
import com.provismet.CombatPlusCore.debug.items.DebuggerItem;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundEvents;

import java.util.List;
import java.util.Optional;

public class CPCDebugItems {
    private static final RegistryKey<Item> DEBUGGER_KEY = RegistryKey.of(RegistryKeys.ITEM, CPCMain.identifier("debugger"));
    private static final Item DEBUGGER = new DebuggerItem(new Item.Settings().maxCount(1).enchantable(1).registryKey(DEBUGGER_KEY).attributeModifiers(DebuggerItem.createSimpleAttributes()));

    private static final RegistryKey<Item> DEBUG_SHIELD_KEY = RegistryKey.of(RegistryKeys.ITEM, CPCMain.identifier("debug_shield"));
    private static final Item DEBUG_SHIELD = new DebugShield(new Item.Settings().maxCount(1).maxDamage(500)
        .component(
            DataComponentTypes.BLOCKS_ATTACKS,
            new BlocksAttacksComponent(
                0.25f,
                1f,
                List.of(new BlocksAttacksComponent.DamageReduction(90f, Optional.empty(), 0f, 1f)),
                new BlocksAttacksComponent.ItemDamage(3f, 1f, 1f),
                Optional.of(DamageTypeTags.BYPASSES_SHIELD),
                Optional.of(SoundEvents.ITEM_SHIELD_BLOCK),
                Optional.of(SoundEvents.ITEM_SHIELD_BREAK)
            )
        )
        .component(DataComponentTypes.BREAK_SOUND, SoundEvents.ITEM_SHIELD_BREAK)
        .registryKey(DEBUG_SHIELD_KEY));

    public static void register () {
        Registry.register(Registries.ITEM, DEBUGGER_KEY, DEBUGGER);
        Registry.register(Registries.ITEM, DEBUG_SHIELD_KEY, DEBUG_SHIELD);
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
