package com.possible_triangle.dye_the_world.data

import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.util.DataIngredient
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.world.item.Item
import net.minecraft.world.level.ItemLike

fun <T : Item, P> ItemBuilder<T, P>.slabRecipes(from: () -> ItemLike) = recipe { context, provider ->
    val source = DataIngredient.items(from())
    provider.slab(source, RecipeCategory.BUILDING_BLOCKS, context, null, true)
}

fun <T : Item, P> ItemBuilder<T, P>.stairRecipes(from: () -> ItemLike) = recipe { context, provider ->
    val source = DataIngredient.items(from())
    provider.stairs(source, RecipeCategory.BUILDING_BLOCKS, context, null, true)
}