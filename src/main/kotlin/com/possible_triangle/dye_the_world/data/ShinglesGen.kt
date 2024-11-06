package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants.Mods.QUARK
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.createId
import com.possible_triangle.dye_the_world.getOrThrow
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import com.tterrag.registrate.util.DataIngredient
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item

fun <I : Item, P> ItemBuilder<I, P>.shinglesRecipes(dye: DyeColor) = recipe { context, provider ->
    val terracotta = dye.blockOf("terracotta")
    val shingles = BuiltInRegistries.BLOCK.getOrThrow(QUARK.createId("shingles"))

    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, context.get(), 2)
        .pattern("##")
        .define('#', terracotta)
        .unlockedBy("has_terracotta", RegistrateRecipeProvider.has(terracotta))
        .save(provider)

    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, context.get(), 8)
        .pattern("###")
        .pattern("#D#")
        .pattern("###")
        .define('#', shingles)
        .define('D', dye.tag)
        .unlockedBy("has_terracotta", RegistrateRecipeProvider.has(shingles))
        .unlockedBy("has_dye", RegistrateRecipeProvider.has(dye.tag))
        .save(provider, context.id.withSuffix("_dyeing"))

    provider.stonecutting(DataIngredient.items(terracotta), RecipeCategory.BUILDING_BLOCKS, context)
}