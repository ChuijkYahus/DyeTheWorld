package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.CREATE
import com.possible_triangle.dye_the_world.DyedRegistrate
import com.possible_triangle.dye_the_world.Genus
import com.possible_triangle.dye_the_world.compat.CreateCompat
import com.possible_triangle.dye_the_world.data.*
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.extensions.germanLang
import com.possible_triangle.dye_the_world.extensions.optionalTag
import com.possible_triangle.dye_the_world.extensions.translation
import com.possible_triangle.dye_the_world.extensions.withItem
import com.possible_triangle.dye_the_world.germanTranslation
import com.simibubi.create.AllBlocks
import com.simibubi.create.AllTags
import com.simibubi.create.content.contraptions.actors.seat.SeatBlock
import com.simibubi.create.content.contraptions.bearing.SailBlock
import com.simibubi.create.content.equipment.toolbox.ToolboxBlock
import com.simibubi.create.content.kinetics.crank.ValveHandleBlock
import com.simibubi.create.content.logistics.packagePort.postbox.PostboxBlock
import com.simibubi.create.content.logistics.tableCloth.TableClothBlock
import com.simibubi.create.content.redstone.nixieTube.NixieTubeBlock
import net.minecraft.tags.BlockTags

object DyedCreate {

    private val REGISTRATE = DyedRegistrate(CREATE)

    private val DYES = dyesFor(CREATE)

    val NIXIE_TUBES = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_nixie_tube")
            .block { NixieTubeBlock(it, dye) }
            .initialProperties { AllBlocks.ORANGE_NIXIE_TUBE.get() }
            .lang("${dye.translation} Nixie Tube")
            .germanLang("${dye.germanTranslation(Genus.F)} Nixie-Röhre")
            .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .nixieTubeBlockstate()
            .loot { t, b -> t.dropOther(b, AllBlocks.ORANGE_NIXIE_TUBE) }
            .register()
    }

    val TOOLBOXES = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_toolbox")
            .block { ToolboxBlock(it, dye) }
            .lang("${dye.translation} Toolbox")
            .germanLang("${dye.germanTranslation(Genus.M)} Werkzeugkasten")
            .optionalTag(AllTags.AllBlockTags.TOOLBOXES.tag)
            .toolboxLoot()
            .toolboxBlockstate(dye)
            .withItem {
                optionalTag(AllTags.AllItemTags.TOOLBOXES.tag)
                toolboxItemModel(dye)
            }
            .register()
    }

    val SEATS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_seat")
            .block { SeatBlock(it, dye) }
            .lang("${dye.translation} Seat")
            .germanLang("${dye.germanTranslation(Genus.M)} Sitz")
            .optionalTag(AllTags.AllBlockTags.SEATS.tag)
            .seatBlockstate(dye)
            .withItem {
                optionalTag(AllTags.AllItemTags.SEATS.tag)
                seatRecipe(dye)
            }
            .register()
    }

    val SAILS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_sail")
            .block { SailBlock.withCanvas(it, dye) }
            .lang("${dye.translation} Sail")
            .germanLang("${dye.germanTranslation(Genus.I)} Segel")
            .optionalTag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
            .sailBlockstate()
            .register()
    }

    val VALVE_HANDLES = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_valve_handle")
            .block { ValveHandleBlock.dyed(it, dye) }
            .lang("${dye.translation} Valve Handle")
            .germanLang("${dye.germanTranslation(Genus.M)} Ventilgriff")
            .optionalTag(AllTags.AllBlockTags.VALVE_HANDLES.tag)
            .valveBlockstate(dye)
            .withItem {
                optionalTag(AllTags.AllItemTags.VALVE_HANDLES.tag)
                valveRecipe(dye)
            }
            .register()
    }

    val TABLE_CLOTHS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_table_cloth")
            .block { TableClothBlock(it, dye) }
            .lang("${dye.translation} Table Cloth")
            .germanLang("${dye.germanTranslation(Genus.F)} Tischdecke")
            .optionalTag(AllTags.AllBlockTags.TABLE_CLOTHS.tag)
            .optionalTag(BlockTags.COMBINATION_STEP_SOUND_BLOCKS)
            .tableClothBlockstate(dye)
            .withItem {
                optionalTag(AllTags.AllItemTags.TABLE_CLOTHS.tag)
                optionalTag(AllTags.AllItemTags.DYED_TABLE_CLOTHS.tag)
                tableClothItemModel(dye)
                tableClothRecipe(dye)
            }
            .register()
    }

    val POST_BOXES = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_postbox")
            .block { PostboxBlock(it, dye) }
            .lang("${dye.translation} Postbox")
            .germanLang("${dye.germanTranslation(Genus.M)} Briefkasten")
            .optionalTag(AllTags.AllBlockTags.POSTBOXES.tag)
            .optionalTag(BlockTags.MINEABLE_WITH_AXE)
            .postboxBlockstate(dye)
            .withItem {
                optionalTag(AllTags.AllItemTags.POSTBOXES.tag)
                postboxItemModel(dye)
                postboxRecipe(dye)
            }
            .register()
    }

    fun register() {
        REGISTRATE.register()
    }

}