package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.FARMERS_DELIGHT
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.data.furniture.canvasSignBlockstate
import com.possible_triangle.dye_the_world.data.furniture.canvasSignItemModel
import com.possible_triangle.dye_the_world.data.furniture.canvasSignRecipes
import com.possible_triangle.dye_the_world.data.translation
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.withItem
import vectorwing.farmersdelight.common.block.StandingCanvasSignBlock
import vectorwing.farmersdelight.common.block.WallCanvasSignBlock
import vectorwing.farmersdelight.common.registry.ModBlocks
import vectorwing.farmersdelight.common.tag.ModTags

object DyedDelight {

    val STANDING_CANVAS_SIGNS = dyesFor(FARMERS_DELIGHT).associateWith { dye ->
        REGISTRATE.`object`("${dye}_canvas_sign")
            .block { StandingCanvasSignBlock(dye) }
            .initialProperties { ModBlocks.RED_CANVAS_SIGN.get() }
            .lang("${dye.translation} Canvas Sign")
            .canvasSignBlockstate()
            .withItem {
                tag(ModTags.CANVAS_SIGNS)
                canvasSignRecipes(dye)
                canvasSignItemModel()
            }
            .register()
    }

    val WALL_CANVAS_SIGNS = dyesFor(FARMERS_DELIGHT).associateWith { dye ->
        REGISTRATE.`object`("${dye}_canvas_wall_sign")
            .block { WallCanvasSignBlock(it, dye) }
            .initialProperties { ModBlocks.RED_CANVAS_WALL_SIGN.get() }
            .canvasSignBlockstate()
            .register()
    }

    fun register() {
        // Loads this class
    }

}