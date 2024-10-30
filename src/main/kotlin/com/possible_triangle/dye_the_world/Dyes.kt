package com.possible_triangle.dye_the_world

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block

private val DEPOT_DYE = listOf(
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

private val DYES = mapOf(
    Constants.Mods.ANOTHER_FURNITURE to DEPOT_DYE
)

fun dyesFor(modid: String): List<DyeColor> {
    return DYES[modid]?.mapNotNull { DyeColor.byName(it, null) } ?: emptyList()
}

val DyeColor.namespace: String
    get() {
        return if (DEPOT_DYE.contains(serializedName)) "dye_depot"
        else "minecraft"
    }

fun blockByDye(dye: DyeColor, type: String): Block {
    val id = ResourceKey.create(Registries.BLOCK, ResourceLocation(dye.namespace, "${dye}_$type"))
    return BuiltInRegistries.BLOCK.getOrThrow(id)
}