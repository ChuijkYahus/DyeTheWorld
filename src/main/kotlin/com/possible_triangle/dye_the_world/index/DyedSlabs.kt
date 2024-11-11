package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.DyedRegistrate
import com.possible_triangle.dye_the_world.asIngredient
import com.possible_triangle.dye_the_world.createId
import com.possible_triangle.dye_the_world.namespace
import com.possible_triangle.dye_the_world.optionalTag
import com.possible_triangle.dye_the_world.withItem
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.data.recipes.RecipeCategory.BUILDING_BLOCKS
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.StairBlock
import net.minecraft.world.level.block.WallBlock

fun DyedRegistrate.createSlabs(
    from: Map<DyeColor, NonNullSupplier<Block>>,
    name: String,
    modifyBlock: BlockBuilder<SlabBlock, DyedRegistrate>.(DyeColor) -> Unit = {},
    modifyItem: ItemBuilder<BlockItem, BlockBuilder<SlabBlock, DyedRegistrate>>.(DyeColor) -> Unit = {},
) = from.mapValues { (dye, base) ->
    `object`("${dye}_${name}_slab")
        .block(::SlabBlock)
        .initialProperties(base)
        .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
        .optionalTag(BlockTags.SLABS)
        .blockstate { c, p ->
            val texture = dye.namespace.createId("block/${dye}_${name}")
            p.slabBlock(c.get(), dye.namespace.createId("block/${dye}_${name}"), texture)
        }
        .withItem {
            tab(CreativeModeTabs.COLORED_BLOCKS)
            tab(CreativeModeTabs.BUILDING_BLOCKS)
            tag(ItemTags.SLABS)
            recipe { c, p -> p.slab(base.asIngredient(), BUILDING_BLOCKS, c, null, true) }
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
        .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
        .optionalTag(BlockTags.STAIRS)
        .blockstate { c, p ->
            val texture = dye.namespace.createId("block/${dye}_${name}")
            p.stairsBlock(c.get(), texture)
        }
        .withItem {
            tab(CreativeModeTabs.COLORED_BLOCKS)
            tab(CreativeModeTabs.BUILDING_BLOCKS)
            tag(ItemTags.STAIRS)
            recipe { c, p -> p.stairs(base.asIngredient(), BUILDING_BLOCKS, c, null, true) }
            modifyItem(dye)
        }
        .apply { modifyBlock(dye) }
        .register()
}

fun DyedRegistrate.createWalls(
    from: Map<DyeColor, NonNullSupplier<Block>>,
    name: String,
    modifyBlock: BlockBuilder<WallBlock, DyedRegistrate>.(DyeColor) -> Unit = {},
    modifyItem: ItemBuilder<BlockItem, BlockBuilder<WallBlock, DyedRegistrate>>.(DyeColor) -> Unit = {},
) = from.mapValues { (dye, base) ->
    `object`("${dye}_${name}_wall")
        .block(::WallBlock)
        .initialProperties(base)
        .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
        .optionalTag(BlockTags.WALLS)
        .blockstate { c, p ->
            val texture = dye.namespace.createId("block/${dye}_${name}")
            p.wallBlock(c.get(), texture)
        }
        .withItem {
            tab(CreativeModeTabs.COLORED_BLOCKS)
            tab(CreativeModeTabs.BUILDING_BLOCKS)
            tag(ItemTags.WALLS)
            recipe { c, p -> p.wall(base.asIngredient(), BUILDING_BLOCKS, c) }
            model { c, p ->
                val texture = dye.namespace.createId("block/${dye}_${name}")
                p.wallInventory(c.name, texture)
            }
            modifyItem(dye)
        }
        .apply { modifyBlock(dye) }
        .register()
}