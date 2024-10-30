package com.possible_triangle.dye_the_world

import com.possible_triangle.dye_the_world.index.DyedFurniture
import net.minecraftforge.fml.common.Mod

@Mod(Constants.MOD_ID)
object ForgeEntrypoint {

    val REGISTRATE = KotlinRegistrate(Constants.MOD_ID)

    init {
        ifLoaded(Constants.Mods.ANOTHER_FURNITURE) {
            DyedFurniture.register()
        }
    }

}