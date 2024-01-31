package com.provismet.CombatPlusCore.utility;

import com.provismet.CombatPlusCore.CPCMain;

import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.BooleanRule;

public class CombatGameRules {
    public static final CustomGameRuleCategory CATEGORY = new CustomGameRuleCategory(CPCMain.identifier("gamerule_category"), Text.translatable("gamerule.category.combat-plus").formatted(Formatting.BOLD, Formatting.YELLOW));
    public static final GameRules.Key<BooleanRule> SWEEPING_REQUIRES_ENCHANTMENT = GameRuleRegistry.register("sweepingRequiresEnchantment", CATEGORY, GameRuleFactory.createBooleanRule(false));

    public static void register () {
        // Loads the class...
    }
}
