package com.provismet.datagen.CombatPlusCore;

import com.provismet.CombatPlusCore.debug.registries.CPCDebugItems;
import com.provismet.CombatPlusCore.utility.tag.CPCItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.tag.ProvidedTagBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagBuilder;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class ItemTagGenerator extends FabricTagProvider.ItemTagProvider {
    public ItemTagGenerator (FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure (RegistryWrapper.WrapperLookup wrapperLookup) {
        this.valueLookupBuilder(CPCItemTags.DUAL_WEAPON)
            .addOptionalTag(ItemTags.SWORDS)
            .addOptional(CPCDebugItems.getOptionalDebugItem().get());

        this.valueLookupBuilder(CPCItemTags.MELEE_WEAPON)
            .addOptionalTag(CPCItemTags.DUAL_WEAPON)
            .addOptionalTag(ItemTags.AXES)
            .addOptionalTag(ItemTags.SPEARS)
            .add(Items.MACE);

        this.valueLookupBuilder(CPCItemTags.ASPECT_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.ASPECT_PRIMARY_ENCHANTABLE);

        this.valueLookupBuilder(CPCItemTags.ASPECT_PRIMARY_ENCHANTABLE)
            .addOptionalTag(ItemTags.FIRE_ASPECT_ENCHANTABLE);

        this.valueLookupBuilder(CPCItemTags.DAMAGE_ENCHANTABLE)
            .addOptionalTag(ItemTags.WEAPON_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.DAMAGE_PRIMARY_ENCHANTABLE)
            .addOptional(CPCDebugItems.getOptionalDebugItem().get());

        this.valueLookupBuilder(CPCItemTags.DAMAGE_PRIMARY_ENCHANTABLE)
            .addOptionalTag(ItemTags.MELEE_WEAPON_ENCHANTABLE)
            .addOptional(CPCDebugItems.getOptionalDebugItem().get());

        this.valueLookupBuilder(CPCItemTags.WEAPON_UTILITY_ENCHANTABLE)
            .addOptionalTag(ItemTags.WEAPON_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.WEAPON_UTILITY_PRIMARY_ENCHANTABLE);

        this.valueLookupBuilder(CPCItemTags.WEAPON_UTILITY_PRIMARY_ENCHANTABLE)
            .addOptionalTag(ItemTags.SHARP_WEAPON_ENCHANTABLE);

        this.valueLookupBuilder(CPCItemTags.OFFHAND_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.OFFHAND_PRIMARY_ENCHANTABLE);

        this.valueLookupBuilder(CPCItemTags.OFFHAND_PRIMARY_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.DUAL_WEAPON);

        this.valueLookupBuilder(CPCItemTags.SHIELD_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.SHIELD_PRIMARY_ENCHANTABLE)
            .addOptionalTag(ConventionalItemTags.SHIELD_TOOLS);

        this.valueLookupBuilder(CPCItemTags.SHIELD_PRIMARY_ENCHANTABLE)
            .add(Items.SHIELD);

        this.valueLookupBuilder(ConventionalItemTags.MELEE_WEAPON_TOOLS)
            .addOptionalTag(CPCItemTags.MELEE_WEAPON);

        this.valueLookupBuilder(ItemTags.WEAPON_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.MELEE_WEAPON);

        this.valueLookupBuilder(ItemTags.SHARP_WEAPON_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.DAMAGE_ENCHANTABLE);

        this.valueLookupBuilder(ConventionalItemTags.ENCHANTABLES)
            .addOptionalTag(CPCItemTags.DAMAGE_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.WEAPON_UTILITY_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.ASPECT_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.OFFHAND_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.SHIELD_ENCHANTABLE);

        this.valueLookupBuilder(ConventionalItemTags.SHIELD_TOOLS)
            .addOptional(CPCDebugItems.getOptionalDebugShield().get());
    }

    @Override
    protected @NotNull ProvidedTagBuilder<Item, Item> valueLookupBuilder(@NotNull TagKey<Item> tag) {
        TagBuilder tagBuilder = this.getTagBuilder(tag);
        ProvidedTagBuilder<RegistryKey<Item>, Item> builder = ProvidedTagBuilder.of(tagBuilder);
        Function<Item, RegistryKey<Item>> mapper = item -> item.getRegistryEntry().registryKey();
        return fixMapped(mapper, builder);
    }

    // This only exists because the vanilla one is bugged and does not do optional entries! This can be safely removed when it's fixed in Minecraft.
    public static ProvidedTagBuilder<Item, Item> fixMapped (Function<Item, RegistryKey<Item>> mapper, ProvidedTagBuilder<RegistryKey<Item>, Item> providedTagBuilder) {
        return new ProvidedTagBuilder<> () {
            @Override
            public ProvidedTagBuilder<Item, Item> add(Item value) {
                providedTagBuilder.add(mapper.apply(value));
                return this;
            }

            @Override
            public ProvidedTagBuilder<Item, Item> addOptional(Item value) {
                providedTagBuilder.addOptional(mapper.apply(value));
                return this;
            }

            @Override
            public ProvidedTagBuilder<Item, Item> addTag(TagKey<Item> tag) {
                providedTagBuilder.addTag(tag);
                return this;
            }

            @Override
            public ProvidedTagBuilder<Item, Item> addOptionalTag(TagKey<Item> tag) {
                providedTagBuilder.addOptionalTag(tag);
                return this;
            }
        };
    }
}
