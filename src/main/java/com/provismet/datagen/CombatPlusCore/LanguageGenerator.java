package com.provismet.datagen.CombatPlusCore;

import com.provismet.CombatPlusCore.utility.CPCEnchantmentTags;
import com.provismet.CombatPlusCore.utility.CPCItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class LanguageGenerator extends FabricLanguageProvider {
    protected LanguageGenerator (FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations (RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add("gamerule.category.combat-plus", "Combat+");
        translationBuilder.add("gamerule.sweepingRequiresEnchantment", "Sweeping Requires Enchantment");
        translationBuilder.add("gamerule.keepLoyaltyTridents", "Keep Loyalty Tridents");

        translationBuilder.add(CPCItemTags.MELEE_WEAPON, "Melee Weapons");
        translationBuilder.add(CPCItemTags.DUAL_WEAPON, "Dual Weapons");
        translationBuilder.add(CPCItemTags.SHIELD_BREAKER, "Breaks Shields");
        translationBuilder.add(CPCItemTags.ASPECT_ENCHANTABLE, "Aspect Enchantable");

        translationBuilder.add(CPCEnchantmentTags.ADDITIONAL_DAMAGE, "Additional Damage Enchantments");
        translationBuilder.add(CPCEnchantmentTags.ASPECT, "Aspect Enchantments");
        translationBuilder.add(CPCEnchantmentTags.WEAPON_UTILITY, "Weapon Utility Enchantments");
        translationBuilder.add(CPCEnchantmentTags.OFFHAND, "Offhand Enchantments");
    }
}
