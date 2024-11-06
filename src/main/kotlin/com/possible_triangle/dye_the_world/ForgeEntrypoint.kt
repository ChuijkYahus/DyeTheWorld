package com.possible_triangle.dye_the_world

import com.possible_triangle.dye_the_world.data.generateTags
import com.possible_triangle.dye_the_world.index.*
import net.minecraftforge.data.loading.DatagenModLoader
import net.minecraftforge.fml.common.Mod

@Mod(Constants.MOD_ID)
object ForgeEntrypoint {

    val REGISTRATE = DyedRegistrate(Constants.MOD_ID)

    init {
        REGISTRATE.register()

        REGISTRATE.generateTags()

        ifLoaded(Constants.Mods.ANOTHER_FURNITURE) {
            DyedFurniture.register()
        }

        ifLoaded(Constants.Mods.QUARK) {
            DyedQuark.register()
        }

        ifLoaded(Constants.Mods.CLAYWORKS) {
            DyedClayworks.register()
        }

        ifLoaded(Constants.Mods.FARMERS_DELIGHT) {
            DyedDelight.register()
        }

        if (DatagenModLoader.isRunningDataGen()) {
            // These are blocks & Items which are automatically added for all dye colors, included modded ones.
            // Therefore, they only lack assets & data files, which have to be generated, but do not need to be registered.

            DyedSupplementaries.register()
            DyedComforts.register()
            DyedCreate.register()
        }
    }

}