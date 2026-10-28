package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.api.enchantment.effect.CombatPlusDataComponentEntityEffect;
import com.provismet.CombatPlusCore.registries.CombatPlusEnchantmentComponentTypes;
import com.provismet.CombatPlusCore.utility.CombatPlusEnchantmentHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.ItemEnchantments;
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
    @Shadow public abstract ItemEnchantments getEnchantments ();

    @Inject(method = "<init>(Lnet/minecraft/core/Holder;ILnet/minecraft/core/component/PatchedDataComponentMap;)V", at = @At("TAIL"))
    private void loadEnchantmentDataComponents (Holder<Item> item, int count, PatchedDataComponentMap components, CallbackInfo info) {
        ItemStack thisItem = (ItemStack)(Object)this;

        CombatPlusEnchantmentHelper.forEachEnchantment((enchantment, level) -> {
            List<ConditionalEffect<CombatPlusDataComponentEntityEffect<?>>> componentEffects = enchantment.value().getEffects(CombatPlusEnchantmentComponentTypes.DATA_COMPONENT);
            for (ConditionalEffect<CombatPlusDataComponentEntityEffect<?>> effect : componentEffects) {
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
        CombatPlusEnchantmentHelper.forEachEnchantment((enchantment, level) -> {
            List<ConditionalEffect<CombatPlusDataComponentEntityEffect<?>>> componentEffects = enchantment.value().getEffects(CombatPlusEnchantmentComponentTypes.DATA_COMPONENT);
            for (ConditionalEffect<CombatPlusDataComponentEntityEffect<?>> effect : componentEffects) {
                effect.effect().remove(thisItem, level);
            }
        }, thisItem);

        // Add the new components.
        newComponent.entrySet().forEach(entry -> {
            List<ConditionalEffect<CombatPlusDataComponentEntityEffect<?>>> componentEffects = entry.getKey().value().getEffects(CombatPlusEnchantmentComponentTypes.DATA_COMPONENT);
            for (ConditionalEffect<CombatPlusDataComponentEntityEffect<?>> effect : componentEffects) {
                effect.effect().apply(thisItem, entry.getIntValue());
            }
        });
    }
}
