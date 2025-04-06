package com.provismet.CombatPlusCore.enchantment.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.utility.CPCRegistries;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;

import java.util.function.Function;

public interface CPCDataComponentEntityEffect<T> {
    Codec<CPCDataComponentEntityEffect<?>> CODEC = CPCRegistries.ENCHANTMENT_DATA_COMPONENT_EFFECT_TYPE.getCodec().dispatch(CPCDataComponentEntityEffect::getCodec, Function.identity());

    default void apply (ItemStack stack, int level) {
        if (stack.get(this.getComponentType()) == null) {
            stack.set(this.getComponentType(), this.getComponent(level));
        }
    }

    default void remove (ItemStack stack, int level) {
        // Would this item normally have this component? If so then assume the enchantment never applied a new version.
        if (stack.getItem().getDefaultStack().get(this.getComponentType()) == null) {
            stack.remove(this.getComponentType());
        }
    }

    T getComponent (int level);
    ComponentType<T> getComponentType ();
    MapCodec<? extends CPCDataComponentEntityEffect<?>> getCodec ();
}
