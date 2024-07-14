package com.provismet.CombatPlusCore;

import com.provismet.CombatPlusCore.debug.registries.CPCDebugItems;
import com.provismet.CombatPlusCore.registries.CPCEnchantmentComponentTypes;
import com.provismet.CombatPlusCore.registries.CPCLootFunctionTypes;
import com.provismet.CombatPlusCore.registries.DoubleEntityEffects;
import com.provismet.CombatPlusCore.registries.DoubleEntityLootConditionTypes;
import com.provismet.CombatPlusCore.registries.ItemConditionTypes;
import com.provismet.CombatPlusCore.registries.LambdaRegistry;
import com.provismet.CombatPlusCore.registries.SingleEntityEffects;
import com.provismet.CombatPlusCore.registries.SingleEntityLootConditionTypes;
import com.provismet.CombatPlusCore.utility.CPCRegistries;
import com.provismet.lilylib.datagen.condition.LilyResourceConditions;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.provismet.CombatPlusCore.api.CombatPlusEntrypoint;
import com.provismet.CombatPlusCore.interfaces.mixin.IMixinItemStack;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentHelper;
import com.provismet.CombatPlusCore.utility.CPCGameRules;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class CPCMain implements ModInitializer {
    public static final String MODID = "combat-plus";
    public static final Logger LOGGER = LoggerFactory.getLogger("Combat+ Core");

    public static Identifier identifier (String path) {
        return Identifier.of(MODID, path);
    }

    @Override
    public void onInitialize () {
        CPCRegistries.init();
        CPCLootFunctionTypes.init();
        CPCEnchantmentComponentTypes.init();
        SingleEntityLootConditionTypes.init();
        DoubleEntityLootConditionTypes.init();
        ItemConditionTypes.init();
        DoubleEntityEffects.register();
        LambdaRegistry.register();
        CPCGameRules.init();
        SingleEntityEffects.register();

        LilyResourceConditions.register();

        FabricLoader.getInstance().getModContainer(MODID).ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(identifier("enchanted_numerals"), container, Text.translatable("resourcepack.combat-plus.enchanted_numerals"), ResourcePackActivationType.DEFAULT_ENABLED);
            ResourceManagerHelper.registerBuiltinResourcePack(identifier("enchanted_numbers"), container, Text.translatable("resourcepack.combat-plus.enchanted_numbers"), ResourcePackActivationType.NORMAL);
        });

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            LOGGER.warn("Combat+ Core development code is running. If you see this, you should be in a development environment.");
            CPCDebugItems.register();
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
                content.add(CPCDebugItems.getOptionalDebugItem().get(), ItemGroup.StackVisibility.SEARCH_TAB_ONLY);
            });
        }

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, target) -> {
            if (entity instanceof LivingEntity user) {
                ((IMixinItemStack)(Object)user.getMainHandStack()).CPC_postKill(user, target);
                CPCEnchantmentHelper.postKill(world, user, target, EquipmentSlot.MAINHAND);
            }
        });

        FabricLoader.getInstance().getEntrypointContainers(MODID, CombatPlusEntrypoint.class).forEach(
            entrypoint -> {
                String otherModId = entrypoint.getProvider().getMetadata().getId();
                try {
                    entrypoint.getEntrypoint().onInitialize();
                }
                catch (Exception e) {
                    LOGGER.error("Mod " + otherModId + " caused an error during inter-mod initialisation: ", e);
                }
            }
        );
    }
}