package com.provismet.CombatPlusCore.utility.item;

import com.provismet.CombatPlusCore.utility.tag.CPCItemTags;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

import java.util.Set;

public class CPCItemGroups {
    public static void register () {
        Set<TagKey<Item>> itemTags = Set.of(CPCItemTags.DAMAGE_ENCHANTABLE, CPCItemTags.WEAPON_UTILITY_ENCHANTABLE, CPCItemTags.ASPECT_ENCHANTABLE, CPCItemTags.OFFHAND_ENCHANTABLE);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> {
            content.getContext().lookup().getOptionalWrapper(RegistryKeys.ENCHANTMENT).ifPresent(registryWrapper -> {
                //ItemGroups.addMaxLevelEnchantedBooks(content, registryWrapper, itemTags, ItemGroup.StackVisibility.PARENT_TAB_ONLY, content.getContext().enabledFeatures());
                //ItemGroups.addAllLevelEnchantedBooks(content, registryWrapper, itemTags, ItemGroup.StackVisibility.SEARCH_TAB_ONLY, content.getContext().enabledFeatures());
            });
        });
    }
}
