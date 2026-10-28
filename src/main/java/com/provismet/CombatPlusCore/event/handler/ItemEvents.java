package com.provismet.CombatPlusCore.event.handler;

import com.provismet.CombatPlusCore.interfaces.MeleeWeapon;
import com.provismet.CombatPlusCore.items.component.MeleeWeaponComponent;
import com.provismet.CombatPlusCore.registries.CPCDataComponentTypes;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public interface ItemEvents {
    static void register () {
        DefaultItemComponentEvents.MODIFY.register(ItemEvents::addMeleeWeaponComponent);
    }

    private static void addMeleeWeaponComponent (DefaultItemComponentEvents.ModifyContext context) {
        context.modify(item -> item instanceof MeleeWeapon, (builder, item) -> {
            if (!builder.contains(CPCDataComponentTypes.MELEE_WEAPON) && builder.contains(DataComponents.WEAPON)) {
                builder.set(
                    CPCDataComponentTypes.MELEE_WEAPON,
                    MeleeWeaponComponent.createFromAttributes(builder.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY))
                );
            }
        });
    }
}
