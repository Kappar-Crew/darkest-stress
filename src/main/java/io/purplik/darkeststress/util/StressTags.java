package io.purplik.darkeststress.util;

import io.purplik.darkeststress.DarkestStress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class StressTags {
    public static class Items {
        public static final TagKey<Item> STRESS_GAIN_FOOD = ItemTags.create(new ResourceLocation(DarkestStress.MOD_ID, "stress_gain_food"));
        public static final TagKey<Item> STRESS_RELIEVE_FOOD = ItemTags.create(new ResourceLocation(DarkestStress.MOD_ID, "stress_relieve_food"));
        public static final TagKey<Item> STRESS_SLIGHT_RELIEVE_FOOD = ItemTags.create(new ResourceLocation(DarkestStress.MOD_ID, "stress_slight_relieve_food"));
    }
}
