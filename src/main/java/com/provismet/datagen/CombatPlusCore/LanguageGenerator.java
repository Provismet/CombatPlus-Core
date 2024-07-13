package com.provismet.datagen.CombatPlusCore;

import com.provismet.CombatPlusCore.utility.tag.CPCEnchantmentTags;
import com.provismet.CombatPlusCore.utility.tag.CPCItemTags;
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
        translationBuilder.add("gamerule.pvpEnchantmentModifier", "PvP Enchantment Modifier");
        translationBuilder.add("gamerule.pvpEnchantmentModifier.description", "Modifies the effectiveness of certain damaging enchantments when used against players.");

        translationBuilder.add(CPCItemTags.MELEE_WEAPON, "Melee Weapons");
        translationBuilder.add(CPCItemTags.DUAL_WEAPON, "Dual Weapons");
        translationBuilder.add(CPCItemTags.SHIELD_BREAKER, "Breaks Shields");

        translationBuilder.add(CPCItemTags.ASPECT_ENCHANTABLE, "Aspect Enchantable");
        translationBuilder.add(CPCItemTags.ASPECT_PRIMARY_ENCHANTABLE, "Primary Aspect Enchantable");
        translationBuilder.add(CPCItemTags.DAMAGE_ENCHANTABLE, "Damage Enchantable");
        translationBuilder.add(CPCItemTags.DAMAGE_PRIMARY_ENCHANTABLE, "Primary Damage Enchantable");
        translationBuilder.add(CPCItemTags.WEAPON_UTILITY_ENCHANTABLE, "Weapon Utility Enchantable");
        translationBuilder.add(CPCItemTags.WEAPON_UTILITY_PRIMARY_ENCHANTABLE, "Primary Weapon Utility Enchantable");
        translationBuilder.add(CPCItemTags.OFFHAND_ENCHANTABLE, "Offhand Enchantable");
        translationBuilder.add(CPCItemTags.OFFHAND_PRIMARY_ENCHANTABLE, "Primary Offhand Enchantable");

        translationBuilder.add(CPCEnchantmentTags.ADDITIONAL_DAMAGE, "Additional Damage Enchantments");
        translationBuilder.add(CPCEnchantmentTags.ASPECT, "Aspect Enchantments");
        translationBuilder.add(CPCEnchantmentTags.WEAPON_UTILITY, "Weapon Utility Enchantments");
        translationBuilder.add(CPCEnchantmentTags.OFFHAND, "Offhand Enchantments");

        translationBuilder.add("item.combat-plus.debugger", "Example Weapon");
        translationBuilder.add("enchantment.combat-plus.logger", "Example Enchantment");
    }
}
