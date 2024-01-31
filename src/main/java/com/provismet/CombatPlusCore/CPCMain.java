package com.provismet.CombatPlusCore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.provismet.CombatPlusCore.api.CombatPlusEntrypoint;
import com.provismet.CombatPlusCore.interfaces.mixin.IMixinItemStack;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentHelper;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentTargets;
import com.provismet.CombatPlusCore.utility.CombatGameRules;

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
        CPCEnchantmentTargets.init();
        CombatGameRules.register();

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, target) -> {
            if (entity instanceof LivingEntity user) {
                ((IMixinItemStack)(Object)user.getMainHandStack()).CPC_postKill(user, target);
                CPCEnchantmentHelper.postKill(user, target, EquipmentSlot.MAINHAND);
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