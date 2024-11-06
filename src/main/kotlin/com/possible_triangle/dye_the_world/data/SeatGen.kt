package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants.Mods.CREATE
import com.possible_triangle.dye_the_world.createId
import com.tterrag.registrate.builders.BlockBuilder
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block

// TODO modify after creating texture
fun <T : Block, P> BlockBuilder<T, P>.seatBlockstate(dye: DyeColor) = blockstate { context, provider ->
    val parent = CREATE.createId("block/red_seat")
    val model = provider.models().withExistingParent(context.name, parent)
    provider.simpleBlock(context.get(), model)
}