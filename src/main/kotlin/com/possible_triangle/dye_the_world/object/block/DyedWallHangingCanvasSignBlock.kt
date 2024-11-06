package com.possible_triangle.dye_the_world.`object`.block

import com.possible_triangle.dye_the_world.index.isDarkBackground
import com.possible_triangle.dye_the_world.`object`.block.entity.DyedHangingCanvasSignBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.state.BlockState
import vectorwing.farmersdelight.common.block.WallHangingCanvasSignBlock

class DyedWallHangingCanvasSignBlock(properties: Properties, dye: DyeColor) :
    WallHangingCanvasSignBlock(properties, dye) {

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = DyedHangingCanvasSignBlockEntity(pos, state)

    override fun isDarkBackground() = backgroundColor!!.isDarkBackground

}