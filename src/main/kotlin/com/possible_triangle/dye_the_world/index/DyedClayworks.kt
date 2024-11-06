package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.data.slabRecipes
import com.possible_triangle.dye_the_world.data.stairRecipes
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.StairBlock

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
        "terracotta_bricks",
        { Constants.MOD_ID.createId("block/clayworks/${it}_terracotta_bricks") }
    )

    val TERRACOTTA_BRICKS_STAIRS = REGISTRATE.createStairs(
        TERRACOTTA_BRICKS,
        "terracotta_bricks",
        { Constants.MOD_ID.createId("block/clayworks/${it}_terracotta_bricks") }
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

    val TERRACOTTA_SLABS = DYES.associateWith { dye ->
        val base = { dye.blockOf("terracotta") }

        REGISTRATE.`object`("${dye}_terracotta_slab")
            .block(::SlabBlock)
            .initialProperties(base)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .tag(BlockTags.SLABS)
            .blockstate { c, p ->
                val texture = Constants.Mods.DYE_DEPOT.createId("block/${dye}_terracotta")
                p.slabBlock(c.get(), texture, texture)
            }
            .lang("${dye.translation} Terracotta Slab")
            .withItem {
                tab(CreativeModeTabs.COLORED_BLOCKS)
                tab(CreativeModeTabs.BUILDING_BLOCKS)
                tag(ItemTags.SLABS)
                slabRecipes(base)
            }
            .register()
    }

    val TERRACOTTA_STAIRS = DYES.associateWith { dye ->
        val base = { dye.blockOf("terracotta") }

        REGISTRATE.`object`("${dye}_terracotta_stairs")
            .block { StairBlock({ base().defaultBlockState() }, it) }
            .initialProperties(base)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .tag(BlockTags.STAIRS)
            .blockstate { c, p ->
                val texture = Constants.Mods.DYE_DEPOT.createId("block/${dye}_terracotta")
                p.stairsBlock(c.get(), texture)
            }
            .lang("${dye.translation} Terracotta Stairs")
            .withItem {
                tag(ItemTags.STAIRS)
                tab(CreativeModeTabs.COLORED_BLOCKS)
                tab(CreativeModeTabs.BUILDING_BLOCKS)
                stairRecipes(base)
            }
            .register()
    }

    fun register() {
        // Loads this class
    }

}