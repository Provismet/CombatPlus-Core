package com.provismet.combatplus;

import com.provismet.combatplus.api.CombatPlusEntrypoint;
import com.provismet.combatplus.debug.registries.CombatPlusDebugItems;
import com.provismet.combatplus.event.handler.CombatEvents;
import com.provismet.combatplus.event.handler.ItemEvents;
import com.provismet.combatplus.registries.CombatPlusDataComponentEffects;
import com.provismet.combatplus.registries.CombatPlusDataComponentTypes;
import com.provismet.combatplus.registries.CombatPlusEnchantmentComponentTypes;
import com.provismet.combatplus.registries.CombatPlusAttributes;
import com.provismet.combatplus.registries.CombatPlusLootFunctionTypes;
import com.provismet.combatplus.registries.DoubleEntityEffects;
import com.provismet.combatplus.registries.DoubleEntityLootConditionTypes;
import com.provismet.combatplus.registries.ItemConditionTypes;
import com.provismet.combatplus.registries.LambdaRegistry;
import com.provismet.combatplus.registries.SingleEntityEffects;
import com.provismet.combatplus.registries.SingleEntityLootConditionTypes;
import com.provismet.combatplus.utility.CombatPlusGameRules;
import com.provismet.combatplus.utility.CombatPlusRegistries;
import com.provismet.lilylib.datagen.condition.LilyResourceConditions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CombatPlusCoreMain implements ModInitializer {
    public static final String MODID = "combat-plus";
    public static final Logger LOGGER = LoggerFactory.getLogger("Combat+ Core");

    public static Identifier identifier (String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }

    @Override
    public void onInitialize () {
        CombatPlusAttributes.init();
        CombatPlusRegistries.init();
        CombatPlusLootFunctionTypes.register();
        CombatPlusEnchantmentComponentTypes.init();
        SingleEntityLootConditionTypes.register();
        DoubleEntityLootConditionTypes.register();
        ItemConditionTypes.register();
        DoubleEntityEffects.register();
        LambdaRegistry.register();
        CombatPlusGameRules.init();
        SingleEntityEffects.register();
        CombatPlusDataComponentEffects.register();
        CombatPlusDataComponentTypes.init();

        ItemEvents.register();
        CombatEvents.register();

        LilyResourceConditions.register();

        FabricLoader.getInstance().getModContainer(MODID).ifPresent(container -> {
            ResourceLoader.registerBuiltinPack(identifier("enchanted_numerals"), container, Component.translatable("resourcepack.combat-plus.enchanted_numerals"), PackActivationType.DEFAULT_ENABLED);
            ResourceLoader.registerBuiltinPack(identifier("enchanted_numbers"), container, Component.translatable("resourcepack.combat-plus.enchanted_numbers"), PackActivationType.NORMAL);
        });

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            LOGGER.warn("Combat+ Core development code is running. If you see this, you should be in a development environment.");
            CombatPlusDebugItems.register();
            CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(content -> {
                CombatPlusDebugItems.getOptionalDebugItem().ifPresent(item -> content.accept(item, CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY));
                CombatPlusDebugItems.getOptionalDebugShield().ifPresent(item -> content.accept(item, CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY));
            });
        }

        FabricLoader.getInstance().getEntrypointContainers(MODID, CombatPlusEntrypoint.class).forEach(
            entrypoint -> {
                String otherModId = entrypoint.getProvider().getMetadata().getId();
                try {
                    entrypoint.getEntrypoint().onInitialize();
                }
                catch (Exception e) {
                    LOGGER.error("Mod {} caused an error during inter-mod initialisation: ", otherModId, e);
                }
            }
        );
    }
}