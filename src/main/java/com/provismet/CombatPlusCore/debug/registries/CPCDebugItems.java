package com.provismet.CombatPlusCore.debug.registries;

import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.debug.items.DebugShield;
import com.provismet.CombatPlusCore.debug.items.DebuggerItem;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.BlocksAttacks;
import java.util.List;
import java.util.Optional;

public class CPCDebugItems {
    private static final ResourceKey<Item> DEBUGGER_KEY = ResourceKey.create(Registries.ITEM, CPCMain.identifier("debugger"));
    private static final Item DEBUGGER = new DebuggerItem(new Item.Properties().stacksTo(1).enchantable(1).setId(DEBUGGER_KEY).attributes(DebuggerItem.createSimpleAttributes()));

    private static final ResourceKey<Item> DEBUG_SHIELD_KEY = ResourceKey.create(Registries.ITEM, CPCMain.identifier("debug_shield"));
    private static final Item DEBUG_SHIELD = new DebugShield(new Item.Properties().stacksTo(1).durability(500)
        .component(
            DataComponents.BLOCKS_ATTACKS,
            new BlocksAttacks(
                0.25f,
                1f,
                List.of(new BlocksAttacks.DamageReduction(90f, Optional.empty(), 0f, 1f)),
                new BlocksAttacks.ItemDamageFunction(3f, 1f, 1f),
                Optional.of(DamageTypeTags.BYPASSES_SHIELD),
                Optional.of(SoundEvents.SHIELD_BLOCK),
                Optional.of(SoundEvents.SHIELD_BREAK)
            )
        )
        .component(DataComponents.BREAK_SOUND, SoundEvents.SHIELD_BREAK)
        .setId(DEBUG_SHIELD_KEY));

    public static void register () {
        Registry.register(BuiltInRegistries.ITEM, DEBUGGER_KEY, DEBUGGER);
        Registry.register(BuiltInRegistries.ITEM, DEBUG_SHIELD_KEY, DEBUG_SHIELD);
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
