package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.UPGRADE_AQUATIC
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.data.bedrollBlockstate
import com.possible_triangle.dye_the_world.data.bedrollItemModel
import com.possible_triangle.dye_the_world.data.bedrollRecipe
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.translation
import com.possible_triangle.dye_the_world.extensions.withItem
import com.teamabnormals.upgrade_aquatic.common.block.BedrollBlock
import com.teamabnormals.upgrade_aquatic.core.other.tags.UABlockTags
import com.teamabnormals.upgrade_aquatic.core.other.tags.UAItemTags
import com.teamabnormals.upgrade_aquatic.core.registry.UABlocks
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation

object DyedAquatic {

    private val TAB =
        ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation(UPGRADE_AQUATIC, UPGRADE_AQUATIC))

    val PET_BEDS = dyesFor(UPGRADE_AQUATIC).associateWith { dye ->
        REGISTRATE.`object`("${dye}_bedroll")
            .block { BedrollBlock(dye, it) }
            .initialProperties { UABlocks.BEDROLL.get() }
            .properties { it.mapColor(dye) }
            .lang("${dye.translation} Bedroll")
            .bedrollBlockstate(dye)
            .optionalTag(UABlockTags.BEDROLLS)
            .withItem {
                tab(TAB)
                optionalTag(UAItemTags.BEDROLLS)
                bedrollRecipe(dye)
                bedrollItemModel(dye)
            }
            .register()
    }


    fun register() {
        // Loads this class
    }

}