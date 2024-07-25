package com.provismet.datagen.CombatPlusCore;

import com.provismet.CombatPlusCore.utility.CPCDamageTypes;
import com.provismet.CombatPlusCore.utility.CPCGameRules;
import com.provismet.CombatPlusCore.utility.tag.CPCEnchantmentTags;
import com.provismet.CombatPlusCore.utility.tag.CPCItemTags;
import com.provismet.lilylib.datagen.provider.LilyLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class LanguageGenerator extends LilyLanguageProvider {
    protected LanguageGenerator (FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations (RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add("gamerule.category.combat-plus", "Combat+");
        translationBuilder.add(CPCGameRules.SWEEPING_REQUIRES_ENCHANTMENT.getTranslationKey(), "Sweeping Requires Enchantment");
        translationBuilder.add(CPCGameRules.LOYALTY_STAYS_IN_HAND.getTranslationKey(), "Keep Loyalty Tridents");
        translationBuilder.add(CPCGameRules.PVP_DAMAGE_MODIFIER.getTranslationKey(), "PvP Enchantment Modifier");
        translationBuilder.add(CPCGameRules.PVP_DAMAGE_MODIFIER.getTranslationKey() + ".description", "Modifies the effectiveness of certain damaging enchantments when used against players.");
        translationBuilder.add(CPCGameRules.LETHAL_POISON.getTranslationKey(), "Lethal Poison");

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

        translationBuilder.add("resourcepack.combat-plus.enchanted_numerals", "Enchantment Numerals");
        translationBuilder.add("resourcepack.combat-plus.enchanted_numerals.description", "Numerals go up to level 255");
        translationBuilder.add("resourcepack.combat-plus.enchanted_numbers", "Enchantment Numbers");
        translationBuilder.add("resourcepack.combat-plus.enchanted_numbers.description", "Numerals are now numbers");

        addDeathMessage(translationBuilder, CPCDamageTypes.POISON,
            "%1$s couldn't find an antidote",
            "%1$s succumbed to poison whilst fighting %2$s",
            "%1$s succumbed to poison whilst fighting %2$s using %3$s"
        );
    }
}
