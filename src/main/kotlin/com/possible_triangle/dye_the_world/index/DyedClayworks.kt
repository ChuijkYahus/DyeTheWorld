package com.possible_triangle.dye_the_world.index

import com.google.common.base.Suppliers.memoize
import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.Constants.Mods.CLAYWORKS
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.teamabnormals.blueprint.common.item.BEWLRBlockItem
import com.teamabnormals.clayworks.client.DecoratedPotBlockEntityWithoutLevelRenderer
import com.teamabnormals.clayworks.core.data.server.ClayworksLootTableProvider
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import net.minecraft.core.BlockPos
import net.minecraft.data.recipes.RecipeCategory.BUILDING_BLOCKS
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DecoratedPotBlock
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity
import net.minecraft.world.level.storage.loot.LootTable
import java.util.concurrent.Callable

object DyedClayworks {

    private val DYES = dyesFor(CLAYWORKS)

    private val TERRACOTTA = dyedBlockMap(CLAYWORKS, "terracotta")

    val TERRACOTTA_BRICKS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_terracotta_bricks")
            .block(::Block)
            .initialProperties { dye.blockOf("terracotta") }
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate { c, p ->
                p.simpleBlock(
                    c.get(), p.models().cubeAll(c.name, Constants.MOD_ID.createId("block/$CLAYWORKS/${c.name}"))
                )
            }
            .lang("${dye.translation} Terracotta Bricks")
            .withItem {
                tab(CreativeModeTabs.COLORED_BLOCKS)
                tab(CreativeModeTabs.BUILDING_BLOCKS)
                recipe { c, p ->
                    p.stonecutting(TERRACOTTA[dye]!!.asIngredient(), BUILDING_BLOCKS, c)
                }
            }
            .register()
    }

    val TERRACOTTA_BRICK_SLABS = REGISTRATE.createSlabs(
        TERRACOTTA_BRICKS,
        "terracotta_brick",
        modifyBlock = { dye ->
            blockstate { c, p ->
                val texture = Constants.MOD_ID.createId("block/$CLAYWORKS/${dye}_terracotta_bricks")
                val double = Constants.MOD_ID.createId("block/${dye}_terracotta_bricks")
                p.slabBlock(c.get(), double, texture)
            }
        },
        modifyItem = { dye ->
            recipe { context, provider ->
                provider.slab(TERRACOTTA_BRICKS[dye]!!.asIngredient(), BUILDING_BLOCKS, context, null, true)
                provider.stonecutting(TERRACOTTA[dye]!!.asIngredient(), BUILDING_BLOCKS, context, 2)
            }
        },
    )

    val TERRACOTTA_BRICK_STAIRS = REGISTRATE.createStairs(
        TERRACOTTA_BRICKS,
        "terracotta_brick",
        modifyBlock = { dye ->
            blockstate { c, p ->
                val texture = Constants.MOD_ID.createId("block/$CLAYWORKS/${dye}_terracotta_bricks")
                p.stairsBlock(c.get(), texture)
            }
        },
        modifyItem = { dye ->
            recipe { context, provider ->
                provider.stairs(TERRACOTTA_BRICKS[dye]!!.asIngredient(), BUILDING_BLOCKS, context, null, true)
                provider.stonecutting(TERRACOTTA[dye]!!.asIngredient(), BUILDING_BLOCKS, context)
            }
        },
    )

    val TERRACOTTA_BRICK_WALLS = REGISTRATE.createWalls(
        TERRACOTTA_BRICKS,
        "terracotta_brick",
        modifyBlock = { dye ->
            blockstate { c, p ->
                val texture = Constants.MOD_ID.createId("block/$CLAYWORKS/${dye}_terracotta_bricks")
                p.wallBlock(c.get(), texture)
            }
        },
        modifyItem = { dye ->
            recipe { context, provider ->
                provider.wall(TERRACOTTA_BRICKS[dye]!!.asIngredient(), BUILDING_BLOCKS, context)
                provider.stonecutting(TERRACOTTA[dye]!!.asIngredient(), BUILDING_BLOCKS, context)
            }
            model { c, p ->
                val texture = Constants.MOD_ID.createId("block/$CLAYWORKS/${dye}_terracotta_bricks")
                p.wallInventory(c.name, texture)
            }
        },
    )

    val CHISELED_TERRACOTTA_BRICKS = DYES.associateWith { dye ->
        REGISTRATE.`object`("chiseled_${dye}_terracotta_bricks")
            .block(::Block)
            .initialProperties { dye.blockOf("terracotta") }
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate { c, p ->
                p.simpleBlock(
                    c.get(), p.models().cubeAll(c.name, Constants.MOD_ID.createId("block/$CLAYWORKS/${c.name}"))
                )
            }
            .lang("Chiseled ${dye.translation} Terracotta Bricks")
            .withItem {
                tab(CreativeModeTabs.COLORED_BLOCKS)
                tab(CreativeModeTabs.BUILDING_BLOCKS)
                recipe { c, p ->
                    p.stonecutting(TERRACOTTA_BRICKS[dye]!!.asIngredient(), BUILDING_BLOCKS, c)
                    p.stonecutting(TERRACOTTA[dye]!!.asIngredient(), BUILDING_BLOCKS, c)

                    val slab = TERRACOTTA_BRICK_SLABS[dye]!!.get()
                    ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, c.get())
                        .pattern("#")
                        .pattern("#")
                        .define('#', slab)
                        .unlockedBy("has_slab", RegistrateRecipeProvider.has(slab))
                        .save(p)
                }
            }
            .register()
    }

    val TERRACOTTA_SLABS = REGISTRATE.createSlabs(TERRACOTTA, "terracotta")

    val TERRACOTTA_STAIRS = REGISTRATE.createStairs(TERRACOTTA, "terracotta")

    val TERRACOTTA_WALLS = REGISTRATE.createWalls(TERRACOTTA, "terracotta")

    val DECORATED_POTS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_decorated_pot")
            .block(::DecoratedPotBlock)
            .initialProperties { Blocks.DECORATED_POT }
            .properties { it.mapColor(dye) }
            .lang("${dye.translation} Decorated Pot")
            .blockstate { c, p ->
                val model = p.models().getBuilder(c.name)
                    .texture("particle", ResourceLocation(dye.namespace, "block/${dye}_terracotta"))
                p.simpleBlock(c.get(), model)
            }
            .loot { tables, block ->
                val table = LootTable.lootTable()
                    .withPool(ClayworksLootTableProvider.ClayworksBlockLoot.createDynamicTrimDropPool())
                    .withPool(ClayworksLootTableProvider.ClayworksBlockLoot.createDecoratedPotPool(block))
                tables.add(block, table)
            }
            .withItem({ block, properties ->
                BEWLRBlockItem(block, properties) {
                    Callable {
                        BEWLRBlockItem.LazyBEWLR { dispatcher, models ->
                            DecoratedPotBlockEntityWithoutLevelRenderer(
                                dispatcher, models, DecoratedPotBlockEntity(
                                    BlockPos.ZERO, block.defaultBlockState()
                                )
                            )
                        }
                    }
                }
            }) {
                properties { it.stacksTo(1) }
                model { c, p -> p.withExistingParent(c.name, ResourceLocation("item/decorated_pot")) }
                tab(CreativeModeTabs.COLORED_BLOCKS)
                tab(CreativeModeTabs.FUNCTIONAL_BLOCKS)
            }
            .register()
    }

    private val DYE_BY_DECORATED_POT = memoize {
        DECORATED_POTS.mapValues { it.value.get() }.inverse()
    }

    fun dyeOf(block: Block): DyeColor? = DYE_BY_DECORATED_POT.get()[block]

    fun register() {
        // Loads this class
    }

}