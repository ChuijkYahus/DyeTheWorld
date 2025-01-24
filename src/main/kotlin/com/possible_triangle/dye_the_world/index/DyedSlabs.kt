package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.DyedRegistrate
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.namespace
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.data.recipes.RecipeCategory.BUILDING_BLOCKS
import net.minecraft.resources.ResourceLocation
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
    name: ResourceLocation,
    modifyBlock: BlockBuilder<SlabBlock, DyedRegistrate>.(DyeColor) -> Unit = {},
    modifyItem: ItemBuilder<BlockItem, BlockBuilder<SlabBlock, DyedRegistrate>>.(DyeColor) -> Unit = {},
) = from.mapValues { (dye, base) ->
    `object`("${dye}_${name.path}_slab")
        .block(::SlabBlock)
        .initialProperties(base)
        .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
        .optionalTag(BlockTags.SLABS)
        .blockstate { c, p ->
            val texture = dye.namespace.createId("block/${dye}_${name.path}")
            p.slabBlock(c.get(), dye.namespace.createId("block/${dye}_${name.path}"), texture)
        }
        .loot { c, p ->
            c.add(p, c.createSlabItemTable(p))
        }
        .withItem {
            tab(CreativeModeTabs.COLORED_BLOCKS)
            tab(CreativeModeTabs.BUILDING_BLOCKS)
            optionalTag(ItemTags.SLABS)
            recipe(name.namespace) { c, p -> p.slab(base.asIngredient(), BUILDING_BLOCKS, c, null, true) }
            modifyItem(dye)
        }
        .apply { modifyBlock(dye) }
        .register()
}

fun DyedRegistrate.createStairs(
    from: Map<DyeColor, NonNullSupplier<Block>>,
    name: ResourceLocation,
    modifyBlock: BlockBuilder<StairBlock, DyedRegistrate>.(DyeColor) -> Unit = {},
    modifyItem: ItemBuilder<BlockItem, BlockBuilder<StairBlock, DyedRegistrate>>.(DyeColor) -> Unit = {},
) = from.mapValues { (dye, base) ->
    `object`("${dye}_${name.path}_stairs")
        .block { StairBlock({ base.get().defaultBlockState() }, it) }
        .initialProperties(base)
        .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
        .optionalTag(BlockTags.STAIRS)
        .blockstate { c, p ->
            val texture = dye.namespace.createId("block/${dye}_${name.path}")
            p.stairsBlock(c.get(), texture)
        }
        .withItem {
            tab(CreativeModeTabs.COLORED_BLOCKS)
            tab(CreativeModeTabs.BUILDING_BLOCKS)
            optionalTag(ItemTags.STAIRS)
            recipe(name.namespace) { c, p -> p.stairs(base.asIngredient(), BUILDING_BLOCKS, c, null, true) }
            modifyItem(dye)
        }
        .apply { modifyBlock(dye) }
        .register()
}

fun DyedRegistrate.createWalls(
    from: Map<DyeColor, NonNullSupplier<Block>>,
    name: ResourceLocation,
    modifyBlock: BlockBuilder<WallBlock, DyedRegistrate>.(DyeColor) -> Unit = {},
    modifyItem: ItemBuilder<BlockItem, BlockBuilder<WallBlock, DyedRegistrate>>.(DyeColor) -> Unit = {},
) = from.mapValues { (dye, base) ->
    `object`("${dye}_${name.path}_wall")
        .block(::WallBlock)
        .initialProperties(base)
        .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
        .optionalTag(BlockTags.WALLS)
        .blockstate { c, p ->
            val texture = dye.namespace.createId("block/${dye}_${name.path}")
            p.wallBlock(c.get(), texture)
        }
        .withItem {
            tab(CreativeModeTabs.COLORED_BLOCKS)
            tab(CreativeModeTabs.BUILDING_BLOCKS)
            optionalTag(ItemTags.WALLS)
            recipe(name.namespace) { c, p -> p.wall(base.asIngredient(), BUILDING_BLOCKS, c) }
            model { c, p ->
                val texture = dye.namespace.createId("block/${dye}_${name.path}")
                p.wallInventory(c.name, texture)
            }
            modifyItem(dye)
        }
        .apply { modifyBlock(dye) }
        .register()
}