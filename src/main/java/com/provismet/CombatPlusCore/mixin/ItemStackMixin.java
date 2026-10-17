package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.enchantment.effect.CPCDataComponentEntityEffect;
import com.provismet.CombatPlusCore.registries.CPCEnchantmentComponentTypes;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.ItemLike;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract ItemEnchantments getEnchantments ();

    @Inject(method = "<init>(Lnet/minecraft/world/level/ItemLike;ILnet/minecraft/core/component/PatchedDataComponentMap;)V", at = @At("TAIL"))
    private void loadEnchantmentDataComponents (ItemLike item, int count, PatchedDataComponentMap components, CallbackInfo info) {
        ItemStack thisItem = (ItemStack)(Object)this;

        CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> {
            List<ConditionalEffect<CPCDataComponentEntityEffect<?>>> componentEffects = enchantment.value().getEffects(CPCEnchantmentComponentTypes.DATA_COMPONENT);
            for (ConditionalEffect<CPCDataComponentEntityEffect<?>> effect : componentEffects) {
                effect.effect().apply(thisItem, level);
            }
        }, thisItem);
    }

    @Inject(method = "set(Lnet/minecraft/core/component/DataComponentType;Ljava/lang/Object;)Ljava/lang/Object;", at = @At("HEAD"))
    private <T> void applyItemComponentsFromEnchantments (DataComponentType<T> type, @Nullable T value, CallbackInfoReturnable<T> cir) {
        if (type != DataComponents.ENCHANTMENTS) return;
        if (!(value instanceof ItemEnchantments newComponent)) return;

        ItemStack thisItem = (ItemStack)(Object)this;

        // Remove the old components.
        CPCEnchantmentHelper.forEachEnchantment((enchantment, level) -> {
            List<ConditionalEffect<CPCDataComponentEntityEffect<?>>> componentEffects = enchantment.value().getEffects(CPCEnchantmentComponentTypes.DATA_COMPONENT);
            for (ConditionalEffect<CPCDataComponentEntityEffect<?>> effect : componentEffects) {
                effect.effect().remove(thisItem, level);
            }
        }, thisItem);

        // Add the new components.
        newComponent.entrySet().forEach(entry -> {
            List<ConditionalEffect<CPCDataComponentEntityEffect<?>>> componentEffects = entry.getKey().value().getEffects(CPCEnchantmentComponentTypes.DATA_COMPONENT);
            for (ConditionalEffect<CPCDataComponentEntityEffect<?>> effect : componentEffects) {
                effect.effect().apply(thisItem, entry.getIntValue());
            }
        });
    }
}
