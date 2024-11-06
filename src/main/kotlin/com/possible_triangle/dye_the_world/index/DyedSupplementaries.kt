package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.Constants.Mods.SUPPLEMENTARIES
import com.possible_triangle.dye_the_world.Constants.Mods.SUPPLEMENTARIES_SQUARED
import com.possible_triangle.dye_the_world.DyedRegistrate
import com.possible_triangle.dye_the_world.data.*
import com.possible_triangle.dye_the_world.dyesFor
import com.possible_triangle.dye_the_world.translation
import com.possible_triangle.dye_the_world.withItem
import net.mehvahdjukaar.supplementaries.common.block.blocks.CandleHolderBlock
import net.mehvahdjukaar.supplementaries.common.block.blocks.SackBlock
import net.minecraft.world.level.block.Block

object DyedSupplementaries {

    private val DYES = dyesFor(SUPPLEMENTARIES)

    private val REGISTRATE = DyedRegistrate(SUPPLEMENTARIES)
    private val SQUARED_REGISTRATE = DyedRegistrate(SUPPLEMENTARIES_SQUARED)

    val SACKS = DYES.associateWith { dye ->
        SQUARED_REGISTRATE.`object`("sack_${dye}")
            .block(::SackBlock)
            .lang("${dye.translation} Sack")
            .sackBlockstate(dye)
            .loot { t, b -> t.add(b, t.createShulkerBoxDrop(b)) }
            .withItem {
                sackItemModel()
            }
            .register()
    }

    val FLAGS = DYES.associateWith { dye ->
        REGISTRATE.`object`("flag_${dye}")
            .block(::Block)
            .lang("${dye.translation} Flag")
            .flagBlockstate()
            .loot { t, b -> t.add(b, t.createBannerDrop(b)) }
            .tag(DyedTags.Blocks.FLAGS)
            .withItem {
                flagRecipe(dye)
                flagItemModel()
            }
            .register()
    }

    val CANDLE_HOLDERS = DYES.associateWith { dye ->
        REGISTRATE.`object`("candle_holder_${dye}")
            .block { CandleHolderBlock(dye, it) }
            .lang("${dye.translation} Candle Holder")
            .candleHolderBlockstate(dye)
            .loot { t, b -> t.add(b, t.createCandleDrops(b)) }
            .withItem {
                candleHolderItemModel(dye)
                candleHolderRecipe(dye)
            }
            .register()
    }

    val GOLD_CANDLE_HOLDERS = DYES.associateWith { dye ->
        SQUARED_REGISTRATE.`object`("gold_candle_holder_${dye}")
            .block { CandleHolderBlock(dye, it) }
            .lang("Gold ${dye.translation} Candle Holder")
            .candleHolderBlockstate(dye)
            .loot { t, b -> t.add(b, t.createCandleDrops(b)) }
            .withItem {
                candleHolderItemModel(dye)
                goldCandleHolderRecipe(dye)
            }
            .register()
    }

    fun register() {
        REGISTRATE.register()
        SQUARED_REGISTRATE.register()
    }

}