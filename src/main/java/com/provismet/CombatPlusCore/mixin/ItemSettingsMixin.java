package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.items.component.MeleeWeaponComponent;
import com.provismet.CombatPlusCore.registries.CPCDataComponentTypes;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.Properties.class)
public abstract class ItemSettingsMixin {
    @Shadow public abstract <T> Item.Properties component (DataComponentType<T> type, T value);

    @Inject(method = "sword", at = @At("HEAD"))
    private void applyDualWeapon (ToolMaterial material, float attackDamage, float attackSpeed, CallbackInfoReturnable<Item.Properties> cir) {
        this.component(CPCDataComponentTypes.MELEE_WEAPON, MeleeWeaponComponent.createDual(attackDamage + material.attackDamageBonus()));
    }

    @Inject(method = "axe", at = @At("HEAD"))
    private void applyMeleeWeapon (ToolMaterial material, float attackDamage, float attackSpeed, CallbackInfoReturnable<Item.Properties> cir) {
        this.component(CPCDataComponentTypes.MELEE_WEAPON, MeleeWeaponComponent.createMelee(attackDamage + material.attackDamageBonus()));
    }
}
