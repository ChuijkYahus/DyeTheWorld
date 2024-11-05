package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.CREATE
import com.possible_triangle.dye_the_world.DyedRegistrate
import com.possible_triangle.dye_the_world.data.furniture.nixieTubeBlockstate
import com.possible_triangle.dye_the_world.data.translation
import com.possible_triangle.dye_the_world.dyesFor
import com.simibubi.create.AllBlocks
import com.simibubi.create.content.redstone.nixieTube.NixieTubeBlock
import net.minecraft.tags.BlockTags

object DyedCreate {

    private val REGISTRATE = DyedRegistrate(CREATE)

    val NIXIE_TUBES = dyesFor(CREATE).associateWith { dye ->
        REGISTRATE.`object`("${dye}_nixie_tube")
            .block { NixieTubeBlock(it, dye) }
            .initialProperties { AllBlocks.ORANGE_NIXIE_TUBE.get() }
            .lang("${dye.translation} Nixie Tube")
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .nixieTubeBlockstate()
            .loot { t, b -> t.dropOther(b, AllBlocks.ORANGE_NIXIE_TUBE) }
            .register()
    }

    fun register() {
        REGISTRATE.register()
    }

}