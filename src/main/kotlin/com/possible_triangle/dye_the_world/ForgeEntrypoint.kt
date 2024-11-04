package com.possible_triangle.dye_the_world

import com.possible_triangle.dye_the_world.data.generateDyeTags
import com.possible_triangle.dye_the_world.index.DyedComforts
import com.possible_triangle.dye_the_world.index.DyedFurniture
import com.possible_triangle.dye_the_world.index.DyedSupplementaries
import net.minecraftforge.data.loading.DatagenModLoader
import net.minecraftforge.fml.common.Mod

@Mod(Constants.MOD_ID)
object ForgeEntrypoint {

    val REGISTRATE = DyedRegistrate(Constants.MOD_ID)

    init {
        REGISTRATE.register()

        REGISTRATE.generateDyeTags()

        ifLoaded(Constants.Mods.ANOTHER_FURNITURE) {
            DyedFurniture.register()
        }

        if (DatagenModLoader.isRunningDataGen()) {
            DyedSupplementaries.register()
            DyedComforts.register()
        }
    }

}