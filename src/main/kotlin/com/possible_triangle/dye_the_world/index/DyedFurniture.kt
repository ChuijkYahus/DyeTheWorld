package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.ANOTHER_FURNITURE
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.blockByDye
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.yRot
import com.starfish_studios.another_furniture.block.SofaBlock
import com.starfish_studios.another_furniture.block.properties.SofaType
import com.starfish_studios.another_furniture.registry.AFBlockTags
import com.starfish_studios.another_furniture.registry.AFBlocks
import com.starfish_studios.another_furniture.registry.AFItemTags
import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.RegistrateBlockstateProvider
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import net.minecraft.core.Direction
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Items
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraftforge.client.model.generators.BlockModelBuilder
import net.minecraftforge.client.model.generators.ConfiguredModel
import net.minecraftforge.client.model.generators.ModelBuilder

object DyedFurniture {

    val SOFAS = dyesFor(ANOTHER_FURNITURE).associateWith { dye ->
        REGISTRATE.`object`("${dye}_sofa")
            .block(::SofaBlock)
            .initialProperties { AFBlocks.RED_SOFA.get() }
            .tag(AFBlockTags.SOFAS)
            .blockstate(::sofaBlockstate)
            .item()
            .recipe { c, p -> sofaRecipes(dye, c, p) }
            .tag(AFItemTags.SOFAS)
            .build()
            .register()
    }

    private fun sofaRecipes(
        dye: DyeColor,
        context: DataGenContext<out ItemLike, out ItemLike>,
        provider: RegistrateRecipeProvider,
    ) {
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

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, context.get())
            .group("sofas")
            .requires(dye.tag)
            .requires(AFBlocks.WHITE_SOFA.get())
            .unlockedBy("has_white_sofa", RegistrateRecipeProvider.has(AFBlocks.WHITE_SOFA.get()))
            .save(provider, provider.safeId(context.get()).withSuffix("_dyeing"))
    }

    fun sofaBlockstate(context: DataGenContext<Block, SofaBlock>, provider: RegistrateBlockstateProvider) {
        val color = "red"

        fun texture(suffix: String) = ResourceLocation(ANOTHER_FURNITURE, "block/sofa/${color}_$suffix")

        fun sofaModel(suffix: String? = null): BlockModelBuilder {
            val realSuffix = suffix?.let { "_$it" } ?: ""
            val parent = ResourceLocation(ANOTHER_FURNITURE, "block/template/sofa$realSuffix")
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
        }, SofaBlock.WATERLOGGED)
    }

    fun register() {
        // Loads this class
    }

}