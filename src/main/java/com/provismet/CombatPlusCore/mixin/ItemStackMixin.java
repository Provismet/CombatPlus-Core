package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.enchantment.effect.CPCDataComponentEntityEffect;
import com.provismet.CombatPlusCore.registries.CPCEnchantmentComponentTypes;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentHelper;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.MergedComponentMap;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.effect.EnchantmentEffectEntry;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract ItemEnchantmentsComponent getEnchantments ();

    @Inject(method = "<init>(Lnet/minecraft/item/ItemConvertible;ILnet/minecraft/component/MergedComponentMap;)V", at = @At("TAIL"))
    private void loadEnchantmentDataComponents (ItemConvertible item, int count, MergedComponentMap components, CallbackInfo info) {
        ItemStack thisItem = (ItemStack)(Object)this;

        CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> {
            List<EnchantmentEffectEntry<CPCDataComponentEntityEffect<?>>> componentEffects = enchantment.value().getEffect(CPCEnchantmentComponentTypes.DATA_COMPONENT);
            for (EnchantmentEffectEntry<CPCDataComponentEntityEffect<?>> effect : componentEffects) {
                effect.effect().apply(thisItem, level);
            }
        }, thisItem);
    }

    @Inject(method = "set", at = @At("HEAD"))
    private <T> void applyItemComponentsFromEnchantments (ComponentType<T> type, @Nullable T value, CallbackInfoReturnable<T> cir) {
        if (type != DataComponentTypes.ENCHANTMENTS) return;
        if (!(value instanceof ItemEnchantmentsComponent newComponent)) return;

        ItemStack thisItem = (ItemStack)(Object)this;

        // Remove the old components.
        CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> {
            List<EnchantmentEffectEntry<CPCDataComponentEntityEffect<?>>> componentEffects = enchantment.value().getEffect(CPCEnchantmentComponentTypes.DATA_COMPONENT);
            for (EnchantmentEffectEntry<CPCDataComponentEntityEffect<?>> effect : componentEffects) {
                effect.effect().remove(thisItem, level);
            }
        }, thisItem);

        // Add the new components.
        newComponent.getEnchantmentEntries().forEach(entry -> {
            List<EnchantmentEffectEntry<CPCDataComponentEntityEffect<?>>> componentEffects = entry.getKey().value().getEffect(CPCEnchantmentComponentTypes.DATA_COMPONENT);
            for (EnchantmentEffectEntry<CPCDataComponentEntityEffect<?>> effect : componentEffects) {
                effect.effect().apply(thisItem, entry.getIntValue());
            }
        });
    }
}
