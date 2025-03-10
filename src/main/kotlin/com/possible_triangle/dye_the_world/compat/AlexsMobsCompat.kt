package com.possible_triangle.dye_the_world.compat

import com.possible_triangle.dye_the_world.blockOf
import net.minecraft.world.item.DyeColor

object AlexsMobsCompat {
    fun getCarpet(dye: DyeColor) = dye.blockOf("carpet").asItem()
}
