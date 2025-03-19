package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.CREATE_INTERIORS
import com.possible_triangle.dye_the_world.DyedRegistrate
import com.possible_triangle.dye_the_world.Genus
import com.possible_triangle.dye_the_world.data.chairBlockstate
import com.possible_triangle.dye_the_world.data.chairItemModel
import com.possible_triangle.dye_the_world.data.chairRecipe
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.germanLang
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.translation
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.germanTranslation
import com.possible_triangle.dye_the_world.`object`.block.DummyChairBlock
import net.minecraft.tags.BlockTags

object DyedCreateInterior {

    private val DYES = dyesFor(CREATE_INTERIORS)

    private val REGISTRATE = DyedRegistrate.create(CREATE_INTERIORS)

    val CHAIRS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_chair")
            .block(::DummyChairBlock)
            .lang("${dye.translation} Chair")
            .germanLang("${dye.germanTranslation(Genus.M)} Stuhl")
            .optionalTag(DyedTags.Blocks.CHAIRS)
            .optionalTag(BlockTags.MINEABLE_WITH_AXE)
            .chairBlockstate(dye)
            .withItem {
                optionalTag(DyedTags.Items.CHAIRS)
                chairItemModel()
                chairRecipe(dye)
            }
            .register()
    }

    val FLOOR_CHAIRS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_floor_chair")
            .block(::DummyChairBlock)
            .lang("${dye.translation} Floor Chair")
            .optionalTag(DyedTags.Blocks.FLOOR_CHAIRS)
            .optionalTag(BlockTags.MINEABLE_WITH_AXE)
            .chairBlockstate(dye)
            .withItem {
                optionalTag(DyedTags.Items.FLOOR_CHAIRS)
                chairItemModel()
                chairRecipe(dye)
            }
            .register()
    }

    fun register() {
        REGISTRATE.register()
    }

}