package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.CHALK
import com.possible_triangle.dye_the_world.DyedRegistrate
import com.possible_triangle.dye_the_world.data.chalkBlockstate
import com.possible_triangle.dye_the_world.data.chalkBoxModel
import com.possible_triangle.dye_the_world.data.chalkItemModel
import com.possible_triangle.dye_the_world.data.chalkRecipe
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.translation
import io.github.mortuusars.chalk.Chalk
import io.github.mortuusars.chalk.block.ChalkMarkBlock
import io.github.mortuusars.chalk.items.ChalkItem
import io.github.mortuusars.chalk.render.ChalkColors
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item

object DyedChalk {

    private val REGISTRATE = DyedRegistrate(CHALK)

    private val DYES = dyesFor(CHALK)

    val CHALK_MARKS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_chalk_mark")
            .block { ChalkMarkBlock(dye, it) }
            .tag(Chalk.Tags.Blocks.CHALK_MARKS)
            .lang("${dye.translation} Chalk Mark")
            .chalkBlockstate()
            .register()
    }

    val CHALKS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_chalk")
            .item { ChalkItem(dye, it) }
            .tag(Chalk.Tags.Items.CHALKS)
            .lang("${dye.translation} Chalk")
            .tab(CreativeModeTabs.TOOLS_AND_UTILITIES)
            .chalkItemModel(dye)
            .chalkRecipe(dye)
            .register()
    }

    val CHALK_BOX = REGISTRATE.`object`("chalk_box")
        .item(::Item)
        .chalkBoxModel()
        .register()

    fun registerColors() {
        DYES.forEach {
            ChalkColors.COLORS[it] = it.fireworkColor
        }
    }

    fun registerDatagen() {
        REGISTRATE.register()
    }

}