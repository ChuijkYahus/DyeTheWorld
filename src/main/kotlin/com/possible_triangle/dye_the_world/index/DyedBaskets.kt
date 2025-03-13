package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.DYE_DEPOT
import com.possible_triangle.dye_the_world.DEPOT_DYES
import com.possible_triangle.dye_the_world.DyedRegistrate
import com.possible_triangle.dye_the_world.VANILLA_DYES
import com.possible_triangle.dye_the_world.extensions.asIngredient
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.withItem
import net.minecraft.world.level.block.Block
import net.minecraftforge.client.model.generators.ConfiguredModel

object DyedBaskets {

    private val DYES = VANILLA_DYES + DEPOT_DYES

    private val REGISTRATE = DyedRegistrate(DYE_DEPOT)

    val BASKETS = DYES.map { dye ->
        REGISTRATE.`object`("${dye}_dye_basket")
            .block(::Block)
            .blockstate {context, provider ->
                fun texture(suffix: String = "") =
                    Constants.MOD_ID.createId("block/basket/$dye$suffix")

                provider.horizontalBlock(
                    context.get(),
                    texture(),
                    texture("_front"),
                    texture("_top"),
                )
            }
            .withItem {
                recipe {context, provider ->
                    provider.storage(context.asIngredient())
                }
            }
            .register()
    }

    fun register() {
        REGISTRATE.register()
    }

}