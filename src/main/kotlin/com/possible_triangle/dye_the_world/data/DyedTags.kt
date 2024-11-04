package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants.Mods
import com.possible_triangle.dye_the_world.DEPOT_DYES
import com.possible_triangle.dye_the_world.DyedRegistrate
import com.possible_triangle.dye_the_world.createId
import com.possible_triangle.dye_the_world.index.DyedTags
import com.simibubi.create.AllTags
import net.minecraft.world.item.DyeColor
import java.util.*

val DyeColor.translation get() = serializedName.replaceFirstChar { it.uppercase(Locale.ROOT) }

fun DyedRegistrate.generateDyeTags() {
    DEPOT_DYES.forEach { color ->
        val dye = DyeColor.byName(color, null) ?: throw NullPointerException("missing dye $color")

        dye.tag.addOptional(Mods.DYE_DEPOT.createId("${color}_dye"))

        Mods.CREATE.createId("${color}_toolbox").also {
            AllTags.AllBlockTags.TOOLBOXES.tag.addOptional(it)
            AllTags.AllItemTags.TOOLBOXES.tag.addOptional(it)

            addLang("block", it, "${dye.translation} Toolbox")
        }

        Mods.CREATE.createId("${color}_seat").also {
            AllTags.AllBlockTags.SEATS.tag.addOptional(it)
            AllTags.AllItemTags.SEATS.tag.addOptional(it)

            addLang("block", it, "${dye.translation} Seat")
        }
    }

    DyeColor.entries.forEach { dye ->
        DyedTags.Items.HAMMOCKS.addOptional(Mods.COMFORTS.createId("hammock_$dye"))
        DyedTags.Items.SLEEPING_BAGS.addOptional(Mods.COMFORTS.createId("sleeping_bag_$dye"))
    }
}