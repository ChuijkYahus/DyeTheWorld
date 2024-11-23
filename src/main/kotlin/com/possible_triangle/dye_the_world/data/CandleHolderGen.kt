package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.namespace
import com.possible_triangle.dye_the_world.extensions.yRot
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import net.mehvahdjukaar.supplementaries.reg.ModRegistry
import net.mehvahdjukaar.suppsquared.SuppSquared
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.client.model.generators.ConfiguredModel

fun <T : Block, P> BlockBuilder<T, P>.candleHolderBlockstate(dye: DyeColor) =
    blockstate { context, provider ->
        provider.createVariant(context) { state ->
            val lit = state.getValue(BlockStateProperties.LIT)
            val candles = state.getValue(BlockStateProperties.CANDLES)
            val facing = state.getValue(HorizontalDirectionalBlock.FACING)
            val face = state.getValue(BlockStateProperties.ATTACH_FACE)

            val suffix = "${face.serializedName}_${candles}"
            val litSuffix = if (lit) "_lit" else ""

            val parent = namespace.createId("block/candle_holders/$suffix")
            val model =
                provider.models()
                    .withExistingParent("block/${namespace}/candle_holders/${dye}_$suffix$litSuffix", parent)
                    .texture("all", ResourceLocation(dye.namespace, "block/${dye}_candle$litSuffix"))

            ConfiguredModel.builder()
                .modelFile(model)
                .rotationY(facing.yRot)
        }
    }

fun <T : Item, P> ItemBuilder<T, P>.candleHolderItemModel(dye: DyeColor) = model { context, provider ->
    provider.generated(
        context,
        Constants.MOD_ID.createId("item/$namespace/candle_holders/$dye")
    )
}

fun <T : Item, P> ItemBuilder<T, P>.candleHolderRecipe(dye: DyeColor) = recipe { context, provider ->
    val candle = dye.blockOf("candle")
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, context.get())
        .pattern("NCN")
        .pattern(" N ")
        .define('C', candle)
        .define('N', Items.IRON_INGOT)
        .unlockedBy("has_candle", RegistrateRecipeProvider.has(candle))
        .group("candle_holder")
        .save(provider)

    provider.dyeingRecipe(dye, ModRegistry.CANDLE_HOLDERS[null]!!.get(), context)
}

fun <T : Item, P> ItemBuilder<T, P>.goldCandleHolderRecipe(dye: DyeColor) = recipe { context, provider ->
    val candle = dye.blockOf("candle")
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, context.get())
        .pattern("C")
        .pattern("N")
        .define('C', candle)
        .define('N', Items.GOLD_INGOT)
        .unlockedBy("has_candle", RegistrateRecipeProvider.has(candle))
        .group("gold_candle_holder")
        .save(provider)

    provider.dyeingRecipe(dye, SuppSquared.GOLDEN_CANDLE_HOLDERS[null]!!.get(), context)
}