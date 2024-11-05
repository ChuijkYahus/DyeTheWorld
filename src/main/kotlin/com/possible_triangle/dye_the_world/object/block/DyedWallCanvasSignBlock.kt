package com.possible_triangle.dye_the_world.`object`.block

import com.possible_triangle.dye_the_world.`object`.block.entity.DyedCanvasSignBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.state.BlockState
import vectorwing.farmersdelight.common.block.WallCanvasSignBlock

class DyedWallCanvasSignBlock(properties: Properties, dye: DyeColor) :
    WallCanvasSignBlock(properties, dye) {

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = DyedCanvasSignBlockEntity(pos, state)

}