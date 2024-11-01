package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.ANOTHER_FURNITURE
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.blockByDye
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.yRot
import com.starfish_studios.another_furniture.block.SofaBlock
import com.starfish_studios.another_furniture.block.StoolBlock
import com.starfish_studios.another_furniture.block.properties.SofaType
import com.starfish_studios.another_furniture.registry.AFBlockTags
import com.starfish_studios.another_furniture.registry.AFBlocks
import com.starfish_studios.another_furniture.registry.AFItemTags
import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.RegistrateBlockstateProvider
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import net.minecraft.core.Direction
import net.minecraft.core.registries.Registries
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Items
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.client.model.generators.BlockModelBuilder
import net.minecraftforge.client.model.generators.ConfiguredModel
import net.minecraftforge.client.model.generators.ModelBuilder

object DyedFurniture {

    private val TAB =
        ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation(ANOTHER_FURNITURE, ANOTHER_FURNITURE))

    val SOFAS = dyesFor(ANOTHER_FURNITURE).associateWith { dye ->
        REGISTRATE.`object`("${dye}_sofa")
            .block(::SofaBlock)
            .initialProperties { AFBlocks.RED_SOFA.get() }
            .tag(AFBlockTags.SOFAS)
            .blockstate { c, p -> dye.sofaBlockstate(c, p) }
            .item()
            .recipe { c, p -> dye.sofaRecipes(c, p) }
            .tag(AFItemTags.SOFAS)
            .tab(TAB)
            .build()
            .register()
    }

    val STOOLS = dyesFor(ANOTHER_FURNITURE).associateWith { dye ->
        REGISTRATE.`object`("${dye}_stool")
            .block(::StoolBlock)
            .initialProperties { AFBlocks.RED_STOOL.get() }
            .tag(AFBlockTags.STOOLS)
            .blockstate { c, p -> dye.stoolBlockstate(c, p) }
            .item()
            .recipe { c, p -> dye.stoolRecipes(c, p) }
            .tag(AFItemTags.STOOLS)
            .tab(TAB)
            .build()
            .register()
    }

    private fun DyeColor.dyeingRecipe(
        context: DataGenContext<out ItemLike, out ItemLike>,
        provider: RegistrateRecipeProvider,
        from: ItemLike,
        build: ShapelessRecipeBuilder.() -> ShapelessRecipeBuilder
    ) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, context.get())
            .build()
            .requires(tag)
            .requires(from)
            .unlockedBy("has_${provider.safeId(from)}", RegistrateRecipeProvider.has(from))
            .save(provider, provider.safeId(context.get()).withSuffix("_dyeing"))
    }

    private fun DyeColor.sofaRecipes(
        context: DataGenContext<out ItemLike, out ItemLike>,
        provider: RegistrateRecipeProvider,
    ) {
        val wool = blockByDye(this, "wool")
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

        dyeingRecipe(context, provider, AFBlocks.WHITE_SOFA.get()) {
            group("sofas")
        }
    }

    private fun DyeColor.stoolRecipes(
        context: DataGenContext<out ItemLike, out ItemLike>,
        provider: RegistrateRecipeProvider,
    ) {
        val wool = blockByDye(this, "wool")
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, context.get(), 3)
            .group("stools")
            .pattern("#W#")
            .pattern("/ /")
            .define('#', ItemTags.PLANKS)
            .define('W', wool)
            .define('/', Items.STICK)
            .unlockedBy("has_wool", RegistrateRecipeProvider.has(wool))
            .save(provider)

        dyeingRecipe(context, provider, AFBlocks.WHITE_STOOL.get()) {
            group("stools")
        }
    }

    fun DyeColor.stoolBlockstate(
        context: DataGenContext<Block, StoolBlock>,
        provider: RegistrateBlockstateProvider,
    ) {
        fun texture(suffix: String) = ResourceLocation("patchup", "block/stool/${this}_$suffix")

        provider.getVariantBuilder(context.get()).forAllStatesExcept({ state ->
            val low = state.getValue(StoolBlock.LOW)

            val suffix = if (low) "_low" else ""
            val parent = ResourceLocation(ANOTHER_FURNITURE, "block/template/stool$suffix")
            val model = provider.models().withExistingParent(context.name + suffix, parent)
                .texture("side", texture("side"))
                .texture("top", texture("top"))

            ConfiguredModel.builder()
                .modelFile(model)
                .build()
        }, BlockStateProperties.WATERLOGGED)
    }

    fun DyeColor.sofaBlockstate(
        context: DataGenContext<Block, SofaBlock>,
        provider: RegistrateBlockstateProvider,
    ) {
        fun texture(suffix: String) = ResourceLocation("patchup", "block/sofa/${this}_$suffix")

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
        }, BlockStateProperties.WATERLOGGED)
    }

    fun register() {
        // Loads this class
    }

}