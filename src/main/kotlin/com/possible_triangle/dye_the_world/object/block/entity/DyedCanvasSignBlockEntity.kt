package com.possible_triangle.dye_the_world.`object`.block.entity

import com.possible_triangle.dye_the_world.index.DyedDelight
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import vectorwing.farmersdelight.common.block.entity.CanvasSignBlockEntity

class DyedCanvasSignBlockEntity(pos: BlockPos, state: BlockState) : CanvasSignBlockEntity(pos, state) {

    override fun getType(): BlockEntityType<DyedCanvasSignBlockEntity> =
        DyedDelight.CANVAS_SIGN_BLOCK_ENTITY.get()

}