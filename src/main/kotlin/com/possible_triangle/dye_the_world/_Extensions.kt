package com.possible_triangle.dye_the_world

import net.minecraft.core.Direction
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.fml.ModList

fun isLoad(modid: String) = ModList.get().isLoaded(modid)

fun ifLoaded(modid: String, block: () -> Unit) {
    if (isLoad(modid)) block()
}

val Direction.yRot: Int
    get() = when (this) {
        Direction.EAST -> 90
        Direction.SOUTH -> 180
        Direction.WEST -> 270
        else -> 0
    }

fun <T : Any> Registry<T>.getOrThrow(id: ResourceLocation): T {
    val key = ResourceKey.create(key(), id)
    return getOrThrow(key)
}