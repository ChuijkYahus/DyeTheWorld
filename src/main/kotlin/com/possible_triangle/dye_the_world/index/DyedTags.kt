package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods
import com.possible_triangle.dye_the_world.createId
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey

object DyedTags {

    object Items {
        val HAMMOCKS = TagKey.create(Registries.ITEM, Mods.COMFORTS.createId("hammocks"))
        val SLEEPING_BAGS = TagKey.create(Registries.ITEM, Mods.COMFORTS.createId("sleeping_bags"))
        val GLASS_SHARDS = TagKey.create(Registries.ITEM, Mods.QUARK.createId("shards"))
        val PET_BEDS = TagKey.create(Registries.ITEM, Mods.DOMESTICATION_INNOVATION.createId("pet_beds"))
        val RADON_LAMPS = TagKey.create(Registries.ITEM, Mods.ALEXS_CAVES.createId("radon_lamps"))
    }

    object Blocks {
        val FLAGS = TagKey.create(Registries.BLOCK, Mods.SUPPLEMENTARIES.createId("flags"))
        val QUARK_STOOLS = TagKey.create(Registries.BLOCK, Mods.QUARK.createId("stools"))
        val FRAMED_GLASSES = TagKey.create(Registries.BLOCK, Mods.QUARK.createId("framed_glasses"))
        val FRAMED_GLASS_PANES = TagKey.create(Registries.BLOCK, Mods.QUARK.createId("framed_glass_panes"))
        val CEILING_BANNERS = TagKey.create(Registries.BLOCK, Mods.AMENDMENTS.createId("ceiling_banners"))
        val BUNTINGS = TagKey.create(Registries.BLOCK, Mods.SUPPLEMENTARIES.createId("buntings"))
    }

}