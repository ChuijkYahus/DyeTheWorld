package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants.Mods.QUARK
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.getOrThrow
import com.possible_triangle.dye_the_world.extensions.recipe
import com.possible_triangle.dye_the_world.index.DyedQuark
import com.possible_triangle.dye_the_world.shapedDyeingRecipe
import com.possible_triangle.dye_the_world.withCondition
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import com.tterrag.registrate.util.DataIngredient
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item

fun <I : Item, P> ItemBuilder<I, P>.shinglesRecipes(dye: DyeColor) = recipe(QUARK) { context, provider ->
    provider.withCondition(DyedQuark.flagCondition("shingles")) {
        val terracotta = dye.blockOf("terracotta")
        val shingles = BuiltInRegistries.BLOCK.getOrThrow(QUARK.createId("shingles"))

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, context.get(), 2)
            .pattern("##")
            .define('#', terracotta)
            .unlockedBy("has_terracotta", RegistrateRecipeProvider.has(terracotta))
            .save(provider)

        provider.shapedDyeingRecipe(dye, shingles, context)
        provider.stonecutting(DataIngredient.items(terracotta), RecipeCategory.BUILDING_BLOCKS, context)
    }
}