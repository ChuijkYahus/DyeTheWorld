package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.Constants.Mods.QUARK
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.starfish_studios.another_furniture.registry.AFBlocks
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraftforge.client.model.generators.ConfiguredModel
import org.violetmoon.quark.content.building.block.StoolBlock

fun <T : Item, P> ItemBuilder<T, P>.quarkStoolRecipe(dye: DyeColor) = recipe { context, provider ->
    val wool = dye.blockOf("wool")
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, context.get())
        .group("stools")
        .pattern("#W#")
        .pattern("WWW")
        .define('#', ItemTags.WOODEN_SLABS)
        .define('W', wool)
        .unlockedBy("has_wool", RegistrateRecipeProvider.has(wool))
        .save(provider)

    provider.dyeingRecipe(dye, AFBlocks.WHITE_STOOL.get(), context) {
        group("stools")
    }
}

fun <T : StoolBlock, P> BlockBuilder<T, P>.quarkStoolBlockstate(dye: DyeColor) = blockstate { context, provider ->
    provider.createVariant(context) { state ->
        val big = state.getValue(StoolBlock.BIG)

        val prefix = if (big) "big_" else ""
        val parent = QUARK.createId("block/${prefix}stool")
        val model = provider.models().withExistingParent("block/$prefix${context.name}", parent)
            .texture("main", Constants.MOD_ID.createId("block/$QUARK/${dye}_stool"))

        ConfiguredModel.builder()
            .modelFile(model)
    }
}