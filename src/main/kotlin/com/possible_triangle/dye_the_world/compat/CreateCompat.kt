package com.possible_triangle.dye_the_world.compat

import com.possible_triangle.dye_the_world.Constants.Mods.CREATE
import com.possible_triangle.dye_the_world.dyedBlockMap
import com.possible_triangle.dye_the_world.dyesFor
import com.simibubi.create.foundation.utility.DyeHelper

object CreateCompat {

    private val WOOL = dyedBlockMap(CREATE, "wool")

    fun registerDyes() {
        dyesFor(CREATE).forEach { dye ->
            DyeHelper.addDye(dye, dye.textColor, dye.textColor) {
                WOOL[dye]!!.get()
            }
        }
    }

}