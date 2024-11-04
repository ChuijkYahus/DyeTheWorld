package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.Constants.Mods.SUPPLEMENTARIES
import com.possible_triangle.dye_the_world.Constants.Mods.SUPPLEMENTARIES_SQUARED
import com.possible_triangle.dye_the_world.data.translation
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import net.mehvahdjukaar.supplementaries.common.block.blocks.SackBlock
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.client.model.generators.ConfiguredModel

object DyedSupplementaries {

    private val REGISTRATE = DyedRegistrate(SUPPLEMENTARIES)
    private val SQUARED_REGISTRATE = DyedRegistrate(SUPPLEMENTARIES_SQUARED)

    val SACKS = dyesFor(SUPPLEMENTARIES).associateWith { dye ->
        SQUARED_REGISTRATE.`object`("sack_${dye}")
            .block(::SackBlock)
            .lang("${dye.translation} Sack")
            .sackBlockstate(dye)
            .loot { t, b -> t.add(b, t.createShulkerBoxDrop(b)) }
            .item()
            .sackItemModel()
            .build()
            .register()
    }

    val FLAGS = dyesFor(SUPPLEMENTARIES).associateWith { dye ->
        REGISTRATE.`object`("flag_${dye}")
            .block(::Block)
            .lang("${dye.translation} Flag")
            .flagBlockstate()
            .loot { t, b -> t.add(b, t.createBannerDrop(b)) }
            .tag(DyedTags.Blocks.FLAGS)
            .item()
            .flagRecipe(dye)
            .flagItemModel()
            .build()
            .register()
    }


    fun register() {
        REGISTRATE.register()
        SQUARED_REGISTRATE.register()
    }

}

private fun <T : Block, P> BlockBuilder<T, P>.flagBlockstate() = blockstate { context, provider ->
    provider.simpleBlock(
        context.get(),
        provider.models().getExistingFile(SUPPLEMENTARIES.createId("block/flag"))
    )
}

private fun <T : Item, P> ItemBuilder<T, P>.flagItemModel() = model { context, provider ->
    provider.withExistingParent(
        "item/${context.name}",
        SUPPLEMENTARIES.createId("item/flag_black")
    )
}

private fun <T : Block, P> BlockBuilder<T, P>.sackBlockstate(dye: DyeColor) =
    blockstate { context, provider ->
        fun texture(suffix: String) =
            Constants.MOD_ID.createId("block/${SUPPLEMENTARIES_SQUARED}/sack_${dye}_$suffix")

        provider.getVariantBuilder(context.get()).forAllStatesExcept({ state ->
            val open = state.getValue(SackBlock.OPEN)

            val suffix = if (open) "open" else "closed"
            val model = provider.models()
                .withExistingParent("block/${context.name}_$suffix", SUPPLEMENTARIES.createId("block/sack_$suffix"))
                .texture("1", texture("front"))
                .texture("2", texture("top"))
                .texture("3", texture("bottom"))
                .texture("4", texture("closed"))
                .texture("particle", texture("front"))

            ConfiguredModel.builder()
                .modelFile(model)
                .build()
        }, BlockStateProperties.WATERLOGGED)
    }

private fun <T : Item, P> ItemBuilder<T, P>.sackItemModel() = model { context, provider ->
    provider.withExistingParent(
        "item/${context.name}",
        SUPPLEMENTARIES_SQUARED.createId("block/${context.name}_closed")
    )
}

private fun <T : Item, P> ItemBuilder<T, P>.flagRecipe(dye: DyeColor) = recipe { context, provider ->
    val wool = blockByDye(dye, "wool")
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