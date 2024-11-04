package com.possible_triangle.dye_the_world.data.furniture

import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.Constants.Mods.ANOTHER_FURNITURE
import com.starfish_studios.another_furniture.block.CurtainBlock
import com.starfish_studios.another_furniture.block.properties.HorizontalConnectionType
import com.starfish_studios.another_furniture.registry.AFBlocks
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import net.minecraft.core.Direction
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.client.model.generators.ConfiguredModel

fun <T : Item, P> ItemBuilder<T, P>.curtainRecipes(dye: DyeColor) = recipe { context, provider ->
    val wool = blockByDye(dye, "wool")
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, context.get(), 3)
        .group("curtains")
        .pattern("//")
        .pattern("##")
        .pattern("##")
        .define('#', wool)
        .define('/', Items.STICK)
        .unlockedBy("has_wool", RegistrateRecipeProvider.has(wool))
        .save(provider)
}.dyeingRecipe(dye, AFBlocks.WHITE_CURTAIN) {
    group("curtains")
}

fun <T : Block, P> BlockBuilder<T, P>.curtainBlockstate(dye: DyeColor) = blockstate { context, provider ->
    fun texture(suffix: String): ResourceLocation =
        Constants.MOD_ID.createId("block/$ANOTHER_FURNITURE/curtain2/${dye}$suffix")

    provider.getVariantBuilder(context.get()).forAllStatesExcept({ state ->
        val facing = state.getValue(CurtainBlock.FACING)
        val vertical = state.getValue(CurtainBlock.VERTICAL_CONNECTION_TYPE)
        val horizontal = state.getValue(CurtainBlock.HORIZONTAL_CONNECTION_TYPE)
        val open = state.getValue(CurtainBlock.OPEN)

        val suffixV = when (vertical) {
            Direction.UP -> "_top"
            else -> "_bottom"
        }

        val suffixH = when (horizontal) {
            HorizontalConnectionType.LEFT -> "${suffixV}_left"
            HorizontalConnectionType.RIGHT -> "${suffixV}_right"
            else -> "_middle"
        }

        val isMiddle = suffixH == "_middle"
        val suffix = suffixH + if (open || isMiddle) "" else "_closed"

        if (isMiddle && open) {
            return@forAllStatesExcept ConfiguredModel.builder()
                .modelFile(provider.models().getExistingFile(ResourceLocation("block/air")))
                .build()
        }

        val parent = ANOTHER_FURNITURE.createId("block/template/curtain$suffix")
        val model = provider.models().withExistingParent(context.name + suffix, parent)
            .texture("curtain", texture(suffix))

        ConfiguredModel.builder()
            .rotationY(facing.yRot)
            .modelFile(model)
            .build()
    }, BlockStateProperties.WATERLOGGED)
}

fun <T : Item, P> ItemBuilder<T, P>.curtainItemModel(dye: DyeColor) = model { context, provider ->
    provider.withExistingParent(context.name, ANOTHER_FURNITURE.createId("item/template/curtain"))
        .texture("all", Constants.MOD_ID.createId("block/$ANOTHER_FURNITURE/curtain/$dye"))
}