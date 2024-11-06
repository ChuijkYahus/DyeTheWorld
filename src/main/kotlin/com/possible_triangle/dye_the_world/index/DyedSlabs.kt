package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.DyedRegistrate
import com.possible_triangle.dye_the_world.createId
import com.possible_triangle.dye_the_world.data.slabRecipes
import com.possible_triangle.dye_the_world.data.stairRecipes
import com.possible_triangle.dye_the_world.namespace
import com.possible_triangle.dye_the_world.withItem
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.StairBlock

fun DyedRegistrate.createSlabs(
    from: Map<DyeColor, NonNullSupplier<Block>>,
    name: String,
    modifyBlock: BlockBuilder<SlabBlock, DyedRegistrate>.(DyeColor) -> Unit = {},
    modifyItem: ItemBuilder<BlockItem, BlockBuilder<SlabBlock, DyedRegistrate>>.(DyeColor) -> Unit = {},
) = from.mapValues { (dye, base) ->
    `object`("${dye}_${name}_slab")
        .block(::SlabBlock)
        .initialProperties(base)
        .tag(BlockTags.MINEABLE_WITH_PICKAXE)
        .tag(BlockTags.SLABS)
        .blockstate { c, p ->
            val texture = dye.namespace.createId("block/${dye}_${name}")
            p.slabBlock(c.get(), dye.namespace.createId("block/${dye}_${name}"), texture)
        }
        .withItem {
            tab(CreativeModeTabs.COLORED_BLOCKS)
            tab(CreativeModeTabs.BUILDING_BLOCKS)
            tag(ItemTags.SLABS)
            slabRecipes { base.get() }
            modifyItem(dye)
        }
        .apply { modifyBlock(dye) }
        .register()
}

fun DyedRegistrate.createStairs(
    from: Map<DyeColor, NonNullSupplier<Block>>,
    name: String,
    modifyBlock: BlockBuilder<StairBlock, DyedRegistrate>.(DyeColor) -> Unit = {},
    modifyItem: ItemBuilder<BlockItem, BlockBuilder<StairBlock, DyedRegistrate>>.(DyeColor) -> Unit = {},
) = from.mapValues { (dye, base) ->
    `object`("${dye}_${name}_stairs")
        .block { StairBlock({ base.get().defaultBlockState() }, it) }
        .initialProperties(base)
        .tag(BlockTags.MINEABLE_WITH_PICKAXE)
        .tag(BlockTags.STAIRS)
        .blockstate { c, p ->
            val texture = dye.namespace.createId("block/${dye}_${name}")
            p.stairsBlock(c.get(), texture)
        }
        .withItem {
            tab(CreativeModeTabs.COLORED_BLOCKS)
            tab(CreativeModeTabs.BUILDING_BLOCKS)
            tag(ItemTags.STAIRS)
            stairRecipes { base.get() }
            modifyItem(dye)
        }
        .apply { modifyBlock(dye) }
        .register()
}