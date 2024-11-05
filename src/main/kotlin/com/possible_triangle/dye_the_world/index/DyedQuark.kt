package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.QUARK
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.blockOf
import com.possible_triangle.dye_the_world.createId
import com.possible_triangle.dye_the_world.data.translation
import com.possible_triangle.dye_the_world.dyesFor
import com.tterrag.registrate.util.DataIngredient
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.world.item.Item

object DyedQuark {

    val GLASS_SHARDS = dyesFor(QUARK).associateWith { dye ->
        REGISTRATE.`object`("${dye}_shard")
            .item(::Item)
            .tag(DyedTags.Items.GLASS_SHARDS)
            .recipe { context, provider ->
                val glass = dye.blockOf("stained_glass")
                provider.square(DataIngredient.items(context), RecipeCategory.BUILDING_BLOCKS, { glass }, true)
            }
            .model { context, provider ->
                provider.generated(context, Constants.MOD_ID.createId("item/$QUARK/${context.name}"))
            }
            .lang("${dye.translation} Glass Shard")
            .register()
    }

    fun register() {
        // Loads this class
    }

}