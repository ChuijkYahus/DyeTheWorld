package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.SUPPLEMENTARIES
import com.possible_triangle.dye_the_world.Constants.Mods.SUPPLEMENTARIES_SQUARED
import com.possible_triangle.dye_the_world.KotlinRegistrate
import com.possible_triangle.dye_the_world.blockByDye
import com.possible_triangle.dye_the_world.createId
import com.possible_triangle.dye_the_world.data.translation
import com.possible_triangle.dye_the_world.dyesFor
import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.RegistrateBlockstateProvider
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import net.mehvahdjukaar.supplementaries.common.block.blocks.SackBlock
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Items
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.client.model.generators.ConfiguredModel

object DyedSupplementaries {

    private val REGISTRATE = KotlinRegistrate(SUPPLEMENTARIES)
    private val SQUARED_REGISTRATE = KotlinRegistrate(SUPPLEMENTARIES_SQUARED)

    val SACKS = dyesFor(SUPPLEMENTARIES).associateWith { dye ->
        SQUARED_REGISTRATE.`object`("sack_${dye}")
            .block(::SackBlock)
            .lang("${dye.translation} Sack")
            .blockstate { c, p -> dye.sackBlockstate(c, p) }
            .loot { t, b -> t.add(b, t.createShulkerBoxDrop(b)) }
            .item()
            .model { c, p ->
                p.withExistingParent(
                    "item/${c.name}",
                    SQUARED_REGISTRATE.modid.createId("block/${c.name}_closed")
                )
            }
            .build()
            .register()
    }

    val FLAGS = dyesFor(SUPPLEMENTARIES).associateWith { dye ->
        REGISTRATE.`object`("flag_${dye}")
            .block(::Block)
            .lang("${dye.translation} Flag")
            .blockstate { c, p ->
                p.simpleBlock(
                    c.get(),
                    p.models().getExistingFile(SUPPLEMENTARIES.createId("block/flag"))
                )
            }
            .loot { t, b -> t.add(b, t.createBannerDrop(b)) }
            .item()
            .recipe { c, p -> dye.flagRecipe(c, p) }
            .model { c, p ->
                p.withExistingParent(
                    "item/${c.name}",
                    SUPPLEMENTARIES.createId("item/flag_black")
                )
            }
            .build()
            .register()
    }

    private fun DyeColor.sackBlockstate(
        context: DataGenContext<Block, out Block>,
        provider: RegistrateBlockstateProvider,
    ) {
        fun texture(suffix: String) = Constants.MOD_ID.createId("block/${SUPPLEMENTARIES_SQUARED}/sack_${this}_$suffix")

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

    private fun DyeColor.flagRecipe(
        context: DataGenContext<out ItemLike, out ItemLike>,
        provider: RegistrateRecipeProvider,
    ) {
        val wool = blockByDye(this, "wool")
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

    fun register() {
        REGISTRATE.register()
        SQUARED_REGISTRATE.register()
    }


}
