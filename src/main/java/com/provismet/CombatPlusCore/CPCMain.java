package com.provismet.CombatPlusCore;

import com.provismet.CombatPlusCore.api.CombatPlusEntrypoint;
import com.provismet.CombatPlusCore.debug.registries.CPCDebugItems;
import com.provismet.CombatPlusCore.event.handler.ItemEvents;
import com.provismet.CombatPlusCore.registries.CPCDataComponentEffects;
import com.provismet.CombatPlusCore.registries.CPCDataComponentTypes;
import com.provismet.CombatPlusCore.registries.CPCEnchantmentComponentTypes;
import com.provismet.CombatPlusCore.registries.CPCEntityAttributes;
import com.provismet.CombatPlusCore.registries.CPCLootFunctionTypes;
import com.provismet.CombatPlusCore.registries.DoubleEntityEffects;
import com.provismet.CombatPlusCore.registries.DoubleEntityLootConditionTypes;
import com.provismet.CombatPlusCore.registries.ItemConditionTypes;
import com.provismet.CombatPlusCore.registries.LambdaRegistry;
import com.provismet.CombatPlusCore.registries.SingleEntityEffects;
import com.provismet.CombatPlusCore.registries.SingleEntityLootConditionTypes;
import com.provismet.CombatPlusCore.utility.CPCCallbackUtil;
import com.provismet.CombatPlusCore.utility.CPCGameRules;
import com.provismet.CombatPlusCore.utility.CPCRegistries;
import com.provismet.lilylib.datagen.condition.LilyResourceConditions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CPCMain implements ModInitializer {
    public static final String MODID = "combat-plus";
    public static final Logger LOGGER = LoggerFactory.getLogger("Combat+ Core");

    public static Identifier identifier (String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }

    @Override
    public void onInitialize () {
        CPCEntityAttributes.init();
        CPCRegistries.init();
        CPCLootFunctionTypes.register();
        CPCEnchantmentComponentTypes.init();
        SingleEntityLootConditionTypes.register();
        DoubleEntityLootConditionTypes.register();
        ItemConditionTypes.register();
        DoubleEntityEffects.register();
        LambdaRegistry.register();
        CPCGameRules.init();
        SingleEntityEffects.register();
        CPCDataComponentEffects.register();
        CPCDataComponentTypes.init();
        ItemEvents.RegisterComponentPhase();

        LilyResourceConditions.register();

        FabricLoader.getInstance().getModContainer(MODID).ifPresent(container -> {
            ResourceLoader.registerBuiltinPack(identifier("enchanted_numerals"), container, Component.translatable("resourcepack.combat-plus.enchanted_numerals"), PackActivationType.DEFAULT_ENABLED);
            ResourceLoader.registerBuiltinPack(identifier("enchanted_numbers"), container, Component.translatable("resourcepack.combat-plus.enchanted_numbers"), PackActivationType.NORMAL);
        });

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            LOGGER.warn("Combat+ Core development code is running. If you see this, you should be in a development environment.");
            CPCDebugItems.register();
            CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(content -> {
                CPCDebugItems.getOptionalDebugItem().ifPresent(item -> content.accept(item, CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY));
                CPCDebugItems.getOptionalDebugShield().ifPresent(item -> content.accept(item, CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY));
            });
        }

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, target, damageSource) -> {
            if (entity instanceof LivingEntity user) {
                CPCCallbackUtil.postKill(world, user.getMainHandItem(), EquipmentSlot.MAINHAND, user, target);
            }
        });

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