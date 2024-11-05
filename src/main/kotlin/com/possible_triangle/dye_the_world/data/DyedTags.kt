package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants.Mods
import com.possible_triangle.dye_the_world.DEPOT_DYES
import com.possible_triangle.dye_the_world.DyedRegistrate
import com.possible_triangle.dye_the_world.createId
import com.possible_triangle.dye_the_world.index.DyedTags
import com.simibubi.create.AllTags
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.DyeColor

fun DyedRegistrate.generateDyeTags() {
    DEPOT_DYES.forEach { color ->
        val dye = DyeColor.byName(color, null) ?: throw NullPointerException("missing dye $color")

        dye.tag.addOptional(Mods.DYE_DEPOT.createId("${color}_dye"))
    }

    DyeColor.entries.forEach { dye ->
        DyedTags.Items.HAMMOCKS.addOptional(Mods.COMFORTS.createId("hammock_$dye"))
        DyedTags.Items.SLEEPING_BAGS.addOptional(Mods.COMFORTS.createId("sleeping_bag_$dye"))
    }

    BlockTags.MINEABLE_WITH_AXE.addOptional(AllTags.AllBlockTags.SEATS.tag)

}