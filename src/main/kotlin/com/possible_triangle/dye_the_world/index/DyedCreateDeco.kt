package com.possible_triangle.dye_the_world.index

import com.github.talrey.createdeco.api.CDTags
import com.github.talrey.createdeco.blocks.ShippingContainerBlock
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE_DECO
import com.possible_triangle.dye_the_world.DyedRegistrate
import com.possible_triangle.dye_the_world.data.*
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.optionalTag
import com.possible_triangle.dye_the_world.translation
import com.possible_triangle.dye_the_world.withItem
import com.simibubi.create.AllTags.AllBlockTags
import com.simibubi.create.content.decoration.placard.PlacardBlock
import net.minecraft.tags.BlockTags

object DyedCreateDeco {

    private val REGISTRATE = DyedRegistrate(CREATE_DECO)

    private val DYES = dyesFor(CREATE_DECO)

    val SHIPPING_CONTAINERS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_shipping_container")
            .block { ShippingContainerBlock(it, dye) }
            .lang("${dye.translation} Shipping Container")
            .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .shippingContainerBlockstate()
            .withItem {
                shippingContainerRecipe(dye)
            }
            .register()
    }

    val PLACARDS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_placard")
            .block(::PlacardBlock)
            .lang("${dye.translation} Placard")
            .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .tag(AllBlockTags.SAFE_NBT.tag)
            .placardBlockstate(dye)
            .withItem {
                tag(CDTags.PLACARD)
                placardRecipe(dye)
            }
            .register()
    }

    fun register() {
        REGISTRATE.register()
    }

}