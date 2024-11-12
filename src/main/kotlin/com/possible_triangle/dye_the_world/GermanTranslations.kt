package com.possible_triangle.dye_the_world

import net.minecraft.world.item.DyeColor

object Genus {
    const val M = "er"
    const val F = "e"
    const val I = "es"
}

fun DyeColor.germanTranslation(suffix: String) : String {
    return when(serializedName) {
        "amber" -> "Bernstein$suffix"
        "aqua" -> "Türkis$suffix"
        "beige" -> "Beig$suffix"
        "coral" -> "Korall$suffix"
        "forest" -> "Blattgrün$suffix"
        "ginger" -> "Bronzen$suffix"
        "indigo" -> "Indigo"
        "maroon" -> "Marone$suffix"
        "mint" -> "Minzgrün$suffix"
        "navy" -> "Marineblau$suffix"
        "olive" -> "Olivengrün$suffix"
        "rose" -> "Rosarot$suffix"
        "slate" -> "Schieferblau$suffix"
        "tan" -> "Lohfarben$suffix"
        "teal" -> "Dunkeltürkis$suffix"
        "verdant" -> "Dunkelgrün$suffix"
        else -> "???"
    }
}