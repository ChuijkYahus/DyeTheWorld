package com.possible_triangle.dye_the_world.`object`.block.entity

import com.possible_triangle.dye_the_world.index.DyedDelight
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import vectorwing.farmersdelight.common.block.entity.HangingCanvasSignBlockEntity

class DyedHangingCanvasSignBlockEntity(pos: BlockPos, state: BlockState) : HangingCanvasSignBlockEntity(pos, state) {

    override fun getType(): BlockEntityType<DyedHangingCanvasSignBlockEntity> =
        DyedDelight.HANGING_CANVAS_SIGN_BLOCK_ENTITY.get()

}