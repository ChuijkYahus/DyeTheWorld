package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.SUPPLEMENTARIES
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.mehvahdjukaar.supplementaries.common.block.ModBlockProperties
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.client.model.generators.ConfiguredModel

fun <T : Block, P> BlockBuilder<T, P>.presentBlockstate(dye: DyeColor, trapped: Boolean) =
    blockstate { context, provider ->
        fun texture(type: String): ResourceLocation =
            Constants.MOD_ID.createId("block/$SUPPLEMENTARIES/present/${type}_$dye")

        provider.createVariant(
            context,
            BlockStateProperties.HORIZONTAL_FACING,
            BlockStateProperties.TRIGGERED
        ) { state ->
            val closed = state.getValue(ModBlockProperties.PACKED)

            val suffix = if (closed) "closed" else "opened"
            val prefix = if (trapped) "trapped_" else ""

            val model = provider.models()
                .withExistingParent(
                    "block/${prefix}present_${suffix}_$dye",
                    SUPPLEMENTARIES.createId("block/${prefix}present_${suffix}_template")
                )
                .texture("3", texture("trapped"))
                .texture("bottom", texture("bottom"))
                .texture("top", texture("top"))
                .texture("particle", texture("side"))
                .texture("side", texture("side"))
                .texture("inside", texture("inside"))

            ConfiguredModel.builder().modelFile(model)
        }
    }

fun <T : Item, P> ItemBuilder<T, P>.presentItemModel(dye: DyeColor, trapped: Boolean) = model { context, provider ->
    val prefix = if (trapped) "trapped_" else ""
    val opened = SUPPLEMENTARIES.createId("block/${prefix}present_opened_$dye")
    val closed = SUPPLEMENTARIES.createId("block/${prefix}present_closed_$dye")
    provider.withExistingParent(context.name, opened)
        .override()
        .model(provider.getExistingFile(closed))
        .predicate(SUPPLEMENTARIES.createId("packed"), 1F)
        .end()
}