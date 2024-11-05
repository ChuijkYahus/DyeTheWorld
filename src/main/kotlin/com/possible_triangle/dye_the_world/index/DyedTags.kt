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
    }

    object Blocks {
        val FLAGS = TagKey.create(Registries.BLOCK, Mods.SUPPLEMENTARIES.createId("flags"))
    }

}