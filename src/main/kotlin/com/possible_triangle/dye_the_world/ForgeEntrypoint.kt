package com.possible_triangle.dye_the_world

import com.possible_triangle.dye_the_world.data.generateDyeTags
import com.possible_triangle.dye_the_world.index.*
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

        ifLoaded(Constants.Mods.QUARK) {
            DyedQuark.register()
        }

        ifLoaded(Constants.Mods.FARMERS_DELIGHT) {
            DyedDelight.register()
        }

        if (DatagenModLoader.isRunningDataGen()) {
            DyedSupplementaries.register()
            DyedComforts.register()
            DyedCreate.register()
        }
    }

}