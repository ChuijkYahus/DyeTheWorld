package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.level.block.Block

object DyedClayworks {

    private val DYES = dyesFor(Constants.Mods.CLAYWORKS)

    val TERRACOTTA_BRICKS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_terracotta_bricks")
            .block(::Block)
            .initialProperties { dye.blockOf("terracotta") }
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate { c, p ->
                p.simpleBlock(
                    c.get(),
                    p.models().cubeAll(c.name, Constants.MOD_ID.createId("block/clayworks/${c.name}"))
                )
            }
            .lang("${dye.translation} Terracotta Bricks")
            .withItem {
                tab(CreativeModeTabs.COLORED_BLOCKS)
                tab(CreativeModeTabs.BUILDING_BLOCKS)
            }
            .register()
    }

    val TERRACOTTA_BRICKS_SLABS = REGISTRATE.createSlabs(
        TERRACOTTA_BRICKS,
        "terracotta_brick",
        modifyBlock = { dye ->
            blockstate { c, p ->
                val texture = Constants.MOD_ID.createId("block/clayworks/${dye}_terracotta_bricks")
                val double = Constants.MOD_ID.createId("block/${dye}_terracotta_bricks")
                p.slabBlock(c.get(), double, texture)
            }
        }
    )

    val TERRACOTTA_BRICKS_STAIRS = REGISTRATE.createStairs(
        TERRACOTTA_BRICKS,
        "terracotta_brick",
        modifyBlock = { dye ->
            blockstate { c, p ->
                val texture = Constants.MOD_ID.createId("block/clayworks/${dye}_terracotta_bricks")
                p.stairsBlock(c.get(), texture)
            }
        }
    )

    val CHISELED_TERRACOTTA_BRICKS = DYES.associateWith { dye ->
        REGISTRATE.`object`("chiseled_${dye}_terracotta_bricks")
            .block(::Block)
            .initialProperties { dye.blockOf("terracotta") }
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate { c, p ->
                p.simpleBlock(
                    c.get(),
                    p.models().cubeAll(c.name, Constants.MOD_ID.createId("block/clayworks/${c.name}"))
                )
            }
            .lang("Chiseled ${dye.translation} Terracotta Bricks")
            .withItem {
                tab(CreativeModeTabs.COLORED_BLOCKS)
                tab(CreativeModeTabs.BUILDING_BLOCKS)
            }
            .register()
    }

    private val TERRACOTTA = dyedBlockMap("terracotta")

    val TERRACOTTA_SLABS = REGISTRATE.createSlabs(TERRACOTTA, "terracotta")

    val TERRACOTTA_STAIRS = REGISTRATE.createStairs(TERRACOTTA, "terracotta")

    fun register() {
        // Loads this class
    }

}