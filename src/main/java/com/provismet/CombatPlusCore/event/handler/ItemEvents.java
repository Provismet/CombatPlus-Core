package com.provismet.CombatPlusCore.event.handler;

import com.provismet.CombatPlusCore.interfaces.MeleeWeapon;
import com.provismet.CombatPlusCore.items.component.MeleeWeaponComponent;
import com.provismet.CombatPlusCore.registries.CPCDataComponentTypes;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;

public class ItemEvents {
    public static void RegisterComponentPhase () {
        DefaultItemComponentEvents.MODIFY.register(ItemEvents::addMeleeWeaponComponent);
    }

    private static void addMeleeWeaponComponent (DefaultItemComponentEvents.ModifyContext context) {
        context.modify(item -> item instanceof MeleeWeapon, (builder, item) -> {
            if (!builder.contains(CPCDataComponentTypes.MELEE_WEAPON) && builder.contains(DataComponentTypes.WEAPON)) {
                builder.add(
                    CPCDataComponentTypes.MELEE_WEAPON,
                    MeleeWeaponComponent.createFromAttributes(builder.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT))
                );
            }
        });
    }
}
