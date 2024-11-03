package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.createId
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey

object DyedTags {

    val HAMMOCKS = TagKey.create(Registries.ITEM, Constants.Mods.COMFORTS.createId("hammocks"))

    val SLEEPING_BAGS = TagKey.create(Registries.ITEM, Constants.Mods.COMFORTS.createId("sleeping_bags"))

}