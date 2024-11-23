package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE
import com.possible_triangle.dye_the_world.extensions.createId
import com.tterrag.registrate.builders.BlockBuilder
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block

fun <T : Block, P> BlockBuilder<T, P>.seatBlockstate(dye: DyeColor) = blockstate { context, provider ->
    val parent = CREATE.createId("block/seat")
    val model = provider.models().withExistingParent(context.name, parent)
        .texture("1", Constants.MOD_ID.createId("block/$CREATE/seat/top_$dye"))
        .texture("2", Constants.MOD_ID.createId("block/$CREATE/seat/side_$dye"))


    provider.simpleBlock(context.get(), model)
}