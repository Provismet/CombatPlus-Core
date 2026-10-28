package com.provismet.CombatPlusCore.api.enchantment.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.utility.CPCRegistries;
import java.util.function.Function;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

public interface CPCDataComponentEntityEffect<T> {
    Codec<CPCDataComponentEntityEffect<?>> CODEC = CPCRegistries.ENCHANTMENT_DATA_COMPONENT_EFFECT_TYPE.byNameCodec().dispatch(CPCDataComponentEntityEffect::getCodec, Function.identity());

    default void apply (ItemStack stack, int level) {
        if (stack.get(this.getComponentType()) == null) {
            stack.set(this.getComponentType(), this.getComponent(level));
        }
    }

    default void remove (ItemStack stack, int level) {
        // Would this item normally have this component? If so then assume the enchantment never applied a new version.
        if (stack.getItem().getDefaultInstance().get(this.getComponentType()) == null) {
            stack.remove(this.getComponentType());
        }
    }

    T getComponent (int level);
    DataComponentType<T> getComponentType ();
    MapCodec<? extends CPCDataComponentEntityEffect<?>> getCodec ();
}
