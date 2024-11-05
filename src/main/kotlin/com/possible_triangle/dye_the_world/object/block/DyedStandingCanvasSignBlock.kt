package com.possible_triangle.dye_the_world.`object`.block

import com.possible_triangle.dye_the_world.`object`.block.entity.DyedCanvasSignBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.state.BlockState
import vectorwing.farmersdelight.common.block.StandingCanvasSignBlock

class DyedStandingCanvasSignBlock(dye: DyeColor) : StandingCanvasSignBlock(dye) {

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = DyedCanvasSignBlockEntity(pos, state)

}