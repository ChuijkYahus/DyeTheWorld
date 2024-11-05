package com.possible_triangle.dye_the_world.compat

import com.possible_triangle.dye_the_world.Constants.Mods.CREATE
import com.possible_triangle.dye_the_world.dyesFor
import com.simibubi.create.foundation.utility.Couple
import net.minecraft.world.item.DyeColor

object CreateCompat {

    val ADDITIONAL_DYE_COLORS: Map<DyeColor, Couple<Int>> = dyesFor(CREATE).associateWith {
        Couple.create(it.textColor, it.textColor)
    }

}