package com.possible_triangle.dye_the_world

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block

val DEPOT_DYES = listOf(
    "amber",
    "aqua",
    "beige",
    "coral",
    "forest",
    "ginger",
    "indigo",
    "maroon",
    "mint",
    "navy",
    "olive",
    "rose",
    "slate",
    "tan",
    "teal",
    "verdant"
)

//private val DYES = mapOf(
//    Constants.Mods.ANOTHER_FURNITURE to DEPOT_DYES,
//)

@Suppress("UNUSED_PARAMETER")
fun dyesFor(modid: String): List<DyeColor> {
    return DEPOT_DYES.mapNotNull { DyeColor.byName(it, null) }
    //return DYES[modid]?.mapNotNull { DyeColor.byName(it, null) } ?: emptyList()
}

val DyeColor.namespace: String
    get() {
        return if (DEPOT_DYES.contains(serializedName)) "dye_depot"
        else "minecraft"
    }

fun DyeColor.blockOf(type: String): Block {
    val id = namespace.createId("${this}_$type")
    return BuiltInRegistries.BLOCK.getOrThrow(id)
}