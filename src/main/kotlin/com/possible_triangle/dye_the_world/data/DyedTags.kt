package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.DEPOT_DYES
import com.possible_triangle.dye_the_world.getOrThrow
import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.providers.ProviderType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.DyeColor

fun AbstractRegistrate<*>.generateItemTags() {
    DEPOT_DYES.forEach { color ->
        val dye = DyeColor.byName(color, null) ?: throw NullPointerException("missing dye $color")
        val id = ResourceLocation(Constants.Mods.DYE_DEPOT, "${color}_dye")

        setDataGenerator(id.path, Registries.ITEM, ProviderType.ITEM_TAGS) {
            val item = BuiltInRegistries.ITEM.getOrThrow(id)
            it.addTag(dye.tag).add(item)
        }
    }
}