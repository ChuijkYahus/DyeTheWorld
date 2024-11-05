package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.FARMERS_DELIGHT
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.data.furniture.canvasSignBlockstate
import com.possible_triangle.dye_the_world.data.furniture.canvasSignItemModel
import com.possible_triangle.dye_the_world.data.furniture.canvasSignRecipes
import com.possible_triangle.dye_the_world.data.furniture.hangingCanvasSignRecipes
import com.possible_triangle.dye_the_world.data.translation
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.`object`.block.DyedCeilingHangingCanvasSignBlock
import com.possible_triangle.dye_the_world.`object`.block.DyedStandingCanvasSignBlock
import com.possible_triangle.dye_the_world.`object`.block.DyedWallCanvasSignBlock
import com.possible_triangle.dye_the_world.`object`.block.DyedWallHangingCanvasSignBlock
import com.possible_triangle.dye_the_world.`object`.block.entity.DyedCanvasSignBlockEntity
import com.possible_triangle.dye_the_world.`object`.block.entity.DyedHangingCanvasSignBlockEntity
import com.possible_triangle.dye_the_world.validBlocks
import com.possible_triangle.dye_the_world.withItem
import com.tterrag.registrate.util.nullness.NonNullFunction
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.SignItem
import net.minecraft.world.level.block.Block
import vectorwing.farmersdelight.client.renderer.CanvasSignRenderer
import vectorwing.farmersdelight.client.renderer.HangingCanvasSignRenderer
import vectorwing.farmersdelight.common.registry.ModBlocks
import vectorwing.farmersdelight.common.tag.ModTags

object DyedDelight {

    val CANVAS_WALL_SIGNS = dyesFor(FARMERS_DELIGHT).associateWith { dye ->
        REGISTRATE.`object`("${dye}_canvas_wall_sign")
            .block { DyedWallCanvasSignBlock(it, dye) }
            .initialProperties { ModBlocks.RED_CANVAS_WALL_SIGN.get() }
            .lang { it.descriptionId + ".wall" }
            .canvasSignBlockstate()
            .register()
    }

    val CANVAS_SIGNS = dyesFor(FARMERS_DELIGHT).associateWith { dye ->
        REGISTRATE.`object`("${dye}_canvas_sign")
            .block { DyedStandingCanvasSignBlock(dye) }
            .initialProperties { ModBlocks.RED_CANVAS_SIGN.get() }
            .lang("${dye.translation} Canvas Sign")
            .canvasSignBlockstate()
            .withItem(dye.signItem(CANVAS_WALL_SIGNS)) {
                tag(ModTags.CANVAS_SIGNS)
                canvasSignRecipes(dye)
                canvasSignItemModel()
            }
            .register()
    }

    val CANVAS_SIGN_BLOCK_ENTITY = REGISTRATE.`object`("canvas_sign")
        .blockEntity { _, pos, state -> DyedCanvasSignBlockEntity(pos, state) }
        .renderer { NonNullFunction(::CanvasSignRenderer) }
        .validBlocks(CANVAS_SIGNS.values)
        .validBlocks(CANVAS_WALL_SIGNS.values)
        .register()

    val HANGING_CANVAS_WALL_SIGNS = dyesFor(FARMERS_DELIGHT).associateWith { dye ->
        REGISTRATE.`object`("${dye}_wall_hanging_canvas_sign")
            .block { DyedWallHangingCanvasSignBlock(it, dye) }
            .initialProperties { ModBlocks.RED_HANGING_CANVAS_WALL_SIGN.get() }
            .lang { it.descriptionId + ".wall" }
            .canvasSignBlockstate()
            .register()
    }

    val HANGING_CANVAS_SIGNS = dyesFor(FARMERS_DELIGHT).associateWith { dye ->
        REGISTRATE.`object`("${dye}_hanging_canvas_sign")
            .block { DyedCeilingHangingCanvasSignBlock(dye) }
            .initialProperties { ModBlocks.RED_HANGING_CANVAS_SIGN.get() }
            .lang("${dye.translation} Hanging Canvas Sign")
            .canvasSignBlockstate()
            .withItem(dye.signItem(HANGING_CANVAS_WALL_SIGNS)) {
                tag(ModTags.HANGING_CANVAS_SIGNS)
                hangingCanvasSignRecipes(dye)
                canvasSignItemModel()
            }
            .register()
    }

    val HANGING_CANVAS_SIGN_BLOCK_ENTITY = REGISTRATE.`object`("hanging_canvas_sign")
        .blockEntity { _, pos, state -> DyedHangingCanvasSignBlockEntity(pos, state) }
        .renderer { NonNullFunction(::HangingCanvasSignRenderer) }
        .validBlocks(HANGING_CANVAS_SIGNS.values)
        .validBlocks(HANGING_CANVAS_WALL_SIGNS.values)
        .register()

    fun register() {
        // Loads this class
    }

}

private fun <T : Block> DyeColor.signItem(wallSigns: Map<DyeColor, NonNullSupplier<out Block>>) =
    { block: T, properties: Item.Properties ->
        SignItem(
            properties,
            block,
            wallSigns[this]!!.get()
        )
    }