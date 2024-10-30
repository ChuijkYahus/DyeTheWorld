package com.possible_triangle.dye_the_world

import net.minecraft.core.Direction
import net.minecraftforge.fml.ModList

fun isLoad(modid: String) = ModList.get().isLoaded(modid)

fun ifLoaded(modid: String, block: () -> Unit) {
    if(isLoad(modid)) block()
}

val Direction.yRot: Int get() = when(this) {
    Direction.EAST -> 90
    Direction.SOUTH -> 180
    Direction.WEST -> 270
    else -> 0
}