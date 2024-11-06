package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.createId
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block

fun <T : Block, P> BlockBuilder<T, P>.flagBlockstate() = blockstate { context, provider ->
    provider.simpleBlock(
        context.get(),
        provider.models().getExistingFile(Constants.Mods.SUPPLEMENTARIES.createId("block/flag"))
    )
}

fun <T : Item, P> ItemBuilder<T, P>.flagItemModel() = model { context, provider ->
    provider.withExistingParent(
        "item/${context.name}",
        Constants.Mods.SUPPLEMENTARIES.createId("item/flag_black")
    )
}

fun <T : Item, P> ItemBuilder<T, P>.flagRecipe(dye: DyeColor) = recipe { context, provider ->
    val wool = dye.blockOf("wool")
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, context.get())
        .pattern("###")
        .pattern("###")
        .pattern("/  ")
        .define('#', wool)
        .define('/', Items.STICK)
        .unlockedBy("has_wool", RegistrateRecipeProvider.has(wool))
        .group("flag")
        .save(provider)
}