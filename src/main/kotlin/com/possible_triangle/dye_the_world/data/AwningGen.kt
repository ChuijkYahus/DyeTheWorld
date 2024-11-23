package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.SUPPLEMENTARIES
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.dyeingRecipe
import com.possible_triangle.dye_the_world.extensions.yRot
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.mehvahdjukaar.supplementaries.common.block.blocks.AwningBlock
import net.mehvahdjukaar.supplementaries.reg.ModRegistry
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraftforge.client.model.generators.ConfiguredModel

fun <T : AwningBlock, P> BlockBuilder<T, P>.awningBlockstate(dye: DyeColor) = blockstate { context, provider ->
    fun texture(suffix: String = "") = Constants.MOD_ID.createId("block/$SUPPLEMENTARIES/awnings/awning_${dye}$suffix")

    provider.createVariant(context) { state ->
        val facing = state.getValue(AwningBlock.FACING)
        val bottom = state.getValue(AwningBlock.BOTTOM)
        val slanted = state.getValue(AwningBlock.SLANTED)

        val slantedSuffix = if(slanted) "_slanted" else ""
        val halfSuffix = if(bottom) "bottom" else "top"
        val type = halfSuffix + slantedSuffix

        val parent = SUPPLEMENTARIES.createId("block/awnings/$type")
        val model = provider.models().withExistingParent("${context.name}_$type", parent)
            .texture("1", texture())
            .texture("up", texture("_side"))
            .texture("particle", texture())

        ConfiguredModel.builder()
            .modelFile(model)
            .rotationY(facing.yRot)
    }
}

fun <T : Item, P> ItemBuilder<T, P>.awningItemModel(dye: DyeColor) = model { context, provider ->
    provider.generated(context, Constants.MOD_ID.createId("block/$SUPPLEMENTARIES/awnings/awning_$dye"))
}

fun <T : Item, P> ItemBuilder<T, P>.awningRecipe(dye: DyeColor) = recipe { context, provider ->
    provider.dyeingRecipe(dye, ModRegistry.AWNINGS[null]!!.get(), context::get)
}