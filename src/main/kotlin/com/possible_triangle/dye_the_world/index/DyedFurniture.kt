package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.ANOTHER_FURNITURE
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.data.*
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.translation
import com.possible_triangle.dye_the_world.extensions.withItem
import com.starfish_studios.another_furniture.block.*
import com.starfish_studios.another_furniture.registry.AFBlockTags
import com.starfish_studios.another_furniture.registry.AFBlocks
import com.starfish_studios.another_furniture.registry.AFItemTags
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation

object DyedFurniture {

    private val TAB =
        ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation(ANOTHER_FURNITURE, ANOTHER_FURNITURE))

    val SOFAS = dyesFor(ANOTHER_FURNITURE).associateWith { dye ->
        REGISTRATE.`object`("${dye}_sofa")
            .block(::SofaBlock)
            .initialProperties { AFBlocks.RED_SOFA.get() }
            .optionalTag(AFBlockTags.SOFAS)
            .sofaBlockstate(dye)
            .withItem {
                sofaRecipes(dye)
                optionalTag(AFItemTags.SOFAS)
                tab(TAB)
            }
            .register()
    }

    val STOOLS = dyesFor(ANOTHER_FURNITURE).associateWith { dye ->
        REGISTRATE.`object`("${dye}_stool")
            .block(::StoolBlock)
            .initialProperties { AFBlocks.RED_STOOL.get() }
            .optionalTag(AFBlockTags.STOOLS)
            .stoolBlockstate(dye)
            .withItem {
                stoolRecipes(dye)
                optionalTag(AFItemTags.STOOLS)
                tab(TAB)
            }
            .register()
    }

    val TALL_STOOLS = dyesFor(ANOTHER_FURNITURE).associateWith { dye ->
        REGISTRATE.`object`("${dye}_tall_stool")
            .block(::TallStoolBlock)
            .initialProperties { AFBlocks.RED_TALL_STOOL.get() }
            .optionalTag(AFBlockTags.TALL_STOOLS)
            .tallStoolBlockstate(dye)
            .withItem {
                tallStoolRecipes(dye)
                optionalTag(AFItemTags.TALL_STOOLS)
                tab(TAB)
            }
            .register()
    }

    val CURTAINS = dyesFor(ANOTHER_FURNITURE).associateWith { dye ->
        REGISTRATE.`object`("${dye}_curtain")
            .block(::CurtainBlock)
            .initialProperties { AFBlocks.RED_CURTAIN.get() }
            .optionalTag(AFBlockTags.CURTAINS)
            .curtainBlockstate(dye)
            .curtainLoot()
            .withItem {
                curtainRecipes(dye)
                curtainItemModel(dye)
                optionalTag(AFItemTags.CURTAINS)
                tab(TAB)
            }
            .register()
    }

    val LAMPS = dyesFor(ANOTHER_FURNITURE).associateWith { dye ->
        REGISTRATE.`object`("${dye}_lamp")
            .block { LampBlock(dye, it) }
            .initialProperties { AFBlocks.RED_LAMP.get() }
            .optionalTag(AFBlockTags.LAMPS)
            .lampBlockstate(dye)
            .withItem {
                lampRecipes(dye)
                lampItemModel(dye)
                optionalTag(AFItemTags.LAMPS)
                tab(TAB)
            }
            .register()
    }

    val LAMPS_CONNECTORS = dyesFor(ANOTHER_FURNITURE).associateWith { dye ->
        REGISTRATE.`object`("${dye}_lamp_connector")
            .block { LampConnectorBlock(dye, it) }
            .lang("${dye.translation} Lamp")
            .loot { t, b -> t.dropOther(b, LAMPS[dye]!!.get()) }
            .initialProperties { AFBlocks.RED_LAMP_CONNECTOR.get() }
            .lampConnectorBlockstate(dye)
            .register()
    }

    fun register() {
        // Loads this class
    }

}