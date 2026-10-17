package com.provismet.CombatPlusCore.utility;

import com.provismet.CombatPlusCore.CPCMain;

import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.gamerules.GameRule;

public class CPCGameRules {
    public static final CustomGameRuleCategory CATEGORY = new CustomGameRuleCategory(CPCMain.identifier("gamerule_category"), Component.translatable("gamerule.category.combat-plus").withStyle(ChatFormatting.BOLD, ChatFormatting.YELLOW));
    public static final GameRule<Boolean> SWEEPING_REQUIRES_ENCHANTMENT = forBool("sweeping_requires_enchantment", false);
    public static final GameRule<Boolean> LOYALTY_STAYS_IN_HAND = forBool("keep_loyalty_tridents", false);
    public static final GameRule<Double> PVP_DAMAGE_MODIFIER = GameRuleBuilder.forDouble(0.5).category(CATEGORY).minValue(0.0).buildAndRegister(CPCMain.identifier("pvp_enchantment_modifier"));
    public static final GameRule<Boolean> LETHAL_POISON = forBool("lethal_poison", false);

    private static GameRule<Boolean> forBool (String name, boolean defaultValue) {
        return GameRuleBuilder.forBoolean(defaultValue).category(CATEGORY).buildAndRegister(CPCMain.identifier(name));
    }



    public static void init () {
        // Loads the class...
    }
}
