package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.Constants.Mods.ALEXS_CAVES
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType

object DyedCaves {

    val RADON_LAMPS = dyesFor(ALEXS_CAVES).associateWith { dye ->
        REGISTRATE.`object`("radon_lamp_${dye}")
            .block(::Block)
            .properties { it.strength(2.0f, 11.0f) }
            .properties { it.requiresCorrectToolForDrops() }
            .properties { it.lightLevel { 15 } }
            .properties { it.sound(SoundType.GLASS) }
            .properties { it.mapColor(dye) }
            .lang("${dye.translation} Radon Lamp")
            .blockstate { c, p ->
                val model = p.models().cubeAll(c.name, Constants.MOD_ID.createId("block/$ALEXS_CAVES/${c.name}"))
                p.simpleBlock(c.get(), model)
            }
            .withItem {
                tab(CreativeModeTabs.COLORED_BLOCKS)
                tab(CreativeModeTabs.FUNCTIONAL_BLOCKS)
            }
            .register()
    }


    fun register() {
        // Loads this class
    }

}