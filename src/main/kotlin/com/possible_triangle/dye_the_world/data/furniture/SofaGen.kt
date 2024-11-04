package com.possible_triangle.dye_the_world.data.furniture

import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.Constants.Mods.ANOTHER_FURNITURE
import com.starfish_studios.another_furniture.block.SofaBlock
import com.starfish_studios.another_furniture.block.properties.SofaType
import com.starfish_studios.another_furniture.registry.AFBlocks
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import net.minecraft.core.Direction
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.client.model.generators.BlockModelBuilder
import net.minecraftforge.client.model.generators.ConfiguredModel
import net.minecraftforge.client.model.generators.ModelBuilder

fun <T : Item, P> ItemBuilder<T, P>.sofaRecipes(dye: DyeColor) = recipe { context, provider ->
    val wool = blockByDye(dye, "wool")
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, context.get(), 3)
        .group("sofas")
        .pattern("#W ")
        .pattern("#WW")
        .pattern("/ /")
        .define('#', ItemTags.PLANKS)
        .define('W', wool)
        .define('/', Items.STICK)
        .unlockedBy("has_wool", RegistrateRecipeProvider.has(wool))
        .save(provider)
}.dyeingRecipe(dye, AFBlocks.WHITE_SOFA) {
    group("sofas")
}

fun <T : Block, P> BlockBuilder<T, P>.sofaBlockstate(dye: DyeColor) = blockstate { context, provider ->
    fun texture(suffix: String) = Constants.MOD_ID.createId("block/$ANOTHER_FURNITURE/sofa/${dye}_$suffix")

    fun sofaModel(suffix: String? = null): BlockModelBuilder {
        val realSuffix = suffix?.let { "_$it" } ?: ""
        val parent = ANOTHER_FURNITURE.createId("block/template/sofa$realSuffix")
        return provider.models().withExistingParent(context.name + realSuffix, parent)
            .texture("back", texture("back"))
    }

    fun singleSofaModel(facing: Direction): ConfiguredModel.Builder<*> {
        val model = sofaModel()
            .texture("seat_top", texture("seat_single"))
            .texture("seat_front", texture("seat_front"))
            .texture("seat_back_1", texture("seat_back_1"))

        return ConfiguredModel.builder()
            .modelFile(model)
            .rotationY(facing.yRot)
    }

    fun middleSofaModel(facing: Direction): ConfiguredModel.Builder<*> {
        val model = sofaModel("middle")
            .texture("seat_top", texture("seat_middle"))
            .texture("seat_front", texture("seat_front"))
            .texture("seat_side", texture("seat_side"))
            .texture("seat_back_1", texture("seat_back_1"))

        return ConfiguredModel.builder()
            .modelFile(model)
            .rotationY(facing.yRot)
    }

    fun innerSofaModel(facing: Direction): ConfiguredModel.Builder<*> {
        val model = sofaModel("inner")
            .texture("seat_top", texture("seat_inner"))
            .texture("seat_front", texture("seat_front"))
            .texture("seat_back_2", texture("seat_back_2"))

        return ConfiguredModel.builder()
            .modelFile(model)
            .rotationY(facing.yRot)
    }

    fun outerSofaModel(facing: Direction): ConfiguredModel.Builder<*> {
        val model = sofaModel("outer")
            .texture("seat_top", texture("seat_corner"))
            .texture("seat_front", texture("seat_front"))
            .texture("seat_side", texture("seat_side"))
            .texture("seat_back_1", texture("seat_back_1"))
            .texture("seat_back_2", texture("seat_back_2"))

        return ConfiguredModel.builder()
            .modelFile(model)
            .rotationY(facing.yRot)
    }

    fun <T : ModelBuilder<T>> ModelBuilder<T>.cornerTextures() = apply {
        texture("seat_top", texture("seat_edge"))
        texture("seat_front", texture("seat_front"))
        texture("seat_side", texture("seat_side"))
        texture("seat_back_1", texture("seat_back_1"))
    }

    fun cornerSofaModel(facing: Direction, type: String): ConfiguredModel.Builder<*> {
        val model = sofaModel(type).cornerTextures()

        return ConfiguredModel.builder()
            .modelFile(model)
            .rotationY(facing.yRot)
    }

    provider.getVariantBuilder(context.get()).forAllStatesExcept({ state ->
        val facing = state.getValue(SofaBlock.FACING)
        val type = state.getValue(SofaBlock.TYPE)

        when (type) {
            SofaType.SINGLE -> singleSofaModel(facing)
            SofaType.MIDDLE -> middleSofaModel(facing)
            SofaType.LEFT -> cornerSofaModel(facing, "left")
            SofaType.RIGHT -> cornerSofaModel(facing, "right")
            SofaType.INNER_LEFT -> innerSofaModel(facing)
            SofaType.INNER_RIGHT -> innerSofaModel(facing).rotationY(facing.yRot + 90)
            SofaType.OUTER_LEFT -> outerSofaModel(facing)
            SofaType.OUTER_RIGHT -> outerSofaModel(facing).rotationY(facing.yRot + 90)
            else -> throw IllegalArgumentException("Unknown sofa type $type")
        }.build()
    }, BlockStateProperties.WATERLOGGED)
}