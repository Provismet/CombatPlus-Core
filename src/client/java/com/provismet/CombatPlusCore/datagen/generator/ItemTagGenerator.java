package com.provismet.CombatPlusCore.datagen.generator;

import com.provismet.CombatPlusCore.debug.registries.CPCDebugItems;
import com.provismet.CombatPlusCore.api.tag.CombatPlusItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class ItemTagGenerator extends FabricTagsProvider.ItemTagsProvider {
    public ItemTagGenerator (FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags (HolderLookup.Provider wrapperLookup) {
        this.valueLookupBuilder(CombatPlusItemTags.DUAL_WEAPON)
            .addOptionalTag(ItemTags.SWORDS)
            .addOptional(CPCDebugItems.getOptionalDebugItem().get());

        this.valueLookupBuilder(CombatPlusItemTags.MELEE_WEAPON)
            .addOptionalTag(CombatPlusItemTags.DUAL_WEAPON)
            .addOptionalTag(ItemTags.AXES)
            .addOptionalTag(ItemTags.SPEARS)
            .add(Items.MACE);

        this.valueLookupBuilder(CombatPlusItemTags.ASPECT_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.ASPECT_PRIMARY_ENCHANTABLE);

        this.valueLookupBuilder(CombatPlusItemTags.ASPECT_PRIMARY_ENCHANTABLE)
            .addOptionalTag(ItemTags.FIRE_ASPECT_ENCHANTABLE);

        this.valueLookupBuilder(CombatPlusItemTags.DAMAGE_ENCHANTABLE)
            .addOptionalTag(ItemTags.WEAPON_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.DAMAGE_PRIMARY_ENCHANTABLE)
            .addOptional(CPCDebugItems.getOptionalDebugItem().get());

        this.valueLookupBuilder(CombatPlusItemTags.DAMAGE_PRIMARY_ENCHANTABLE)
            .addOptionalTag(ItemTags.MELEE_WEAPON_ENCHANTABLE)
            .addOptional(CPCDebugItems.getOptionalDebugItem().get());

        this.valueLookupBuilder(CombatPlusItemTags.WEAPON_UTILITY_ENCHANTABLE)
            .addOptionalTag(ItemTags.WEAPON_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.WEAPON_UTILITY_PRIMARY_ENCHANTABLE);

        this.valueLookupBuilder(CombatPlusItemTags.WEAPON_UTILITY_PRIMARY_ENCHANTABLE)
            .addOptionalTag(ItemTags.SHARP_WEAPON_ENCHANTABLE);

        this.valueLookupBuilder(CombatPlusItemTags.OFFHAND_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.OFFHAND_PRIMARY_ENCHANTABLE);

        this.valueLookupBuilder(CombatPlusItemTags.OFFHAND_PRIMARY_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.DUAL_WEAPON);

        this.valueLookupBuilder(CombatPlusItemTags.SHIELD_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.SHIELD_PRIMARY_ENCHANTABLE)
            .addOptionalTag(ConventionalItemTags.SHIELD_TOOLS);

        this.valueLookupBuilder(CombatPlusItemTags.SHIELD_PRIMARY_ENCHANTABLE)
            .add(Items.SHIELD);

        this.valueLookupBuilder(ConventionalItemTags.MELEE_WEAPON_TOOLS)
            .addOptionalTag(CombatPlusItemTags.MELEE_WEAPON);

        this.valueLookupBuilder(ItemTags.WEAPON_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.MELEE_WEAPON);

        this.valueLookupBuilder(ItemTags.SHARP_WEAPON_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.DAMAGE_ENCHANTABLE);

        this.valueLookupBuilder(ConventionalItemTags.ENCHANTABLES)
            .addOptionalTag(CombatPlusItemTags.DAMAGE_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.WEAPON_UTILITY_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.ASPECT_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.OFFHAND_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.SHIELD_ENCHANTABLE);

        this.valueLookupBuilder(ConventionalItemTags.SHIELD_TOOLS)
            .addOptional(CPCDebugItems.getOptionalDebugShield().get());
    }

    @Override
    protected @NotNull TagAppender<Item, Item> valueLookupBuilder(@NotNull TagKey<Item> tag) {
        TagBuilder tagBuilder = this.getOrCreateRawBuilder(tag);
        TagAppender<ResourceKey<Item>, Item> builder = TagAppender.forBuilder(tagBuilder);
        Function<Item, ResourceKey<Item>> mapper = item -> item.builtInRegistryHolder().key();
        return fixMapped(mapper, builder);
    }

    // This only exists because the vanilla one is bugged and does not do optional entries! This can be safely removed when it's fixed in Minecraft.
    public static TagAppender<Item, Item> fixMapped (Function<Item, ResourceKey<Item>> mapper, TagAppender<ResourceKey<Item>, Item> providedTagBuilder) {
        return new TagAppender<> () {
            @Override
            public TagAppender<Item, Item> add(Item value) {
                providedTagBuilder.add(mapper.apply(value));
                return this;
            }

            @Override
            public TagAppender<Item, Item> addOptional(Item value) {
                providedTagBuilder.addOptional(mapper.apply(value));
                return this;
            }

            @Override
            public TagAppender<Item, Item> addTag(TagKey<Item> tag) {
                providedTagBuilder.addTag(tag);
                return this;
            }

            @Override
            public TagAppender<Item, Item> addOptionalTag(TagKey<Item> tag) {
                providedTagBuilder.addOptionalTag(tag);
                return this;
            }
        };
    }
}
