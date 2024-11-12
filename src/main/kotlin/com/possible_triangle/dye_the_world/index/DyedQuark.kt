package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.Constants.Mods.QUARK
import com.possible_triangle.dye_the_world.ForgeEntrypoint.REGISTRATE
import com.possible_triangle.dye_the_world.data.*
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeCategory.BUILDING_BLOCKS
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraftforge.client.model.generators.ModelBuilder
import net.minecraftforge.common.Tags
import org.violetmoon.quark.content.building.block.StoolBlock
import org.violetmoon.zeta.block.ZetaGlassBlock
import org.violetmoon.zeta.block.ZetaInheritedPaneBlock

private val TRANSLUCENT = ResourceLocation("translucent")

private fun ModelBuilder<*>.translucent() = renderType(TRANSLUCENT)

object DyedQuark {

    private val DYES = dyesFor(QUARK)

    private val TERRACOTTA = dyedBlockMap(QUARK, "terracotta")

    val GLASS_SHARDS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_shard")
            .item(::Item)
            .tab(CreativeModeTabs.INGREDIENTS)
            .optionalTag(DyedTags.Items.GLASS_SHARDS)
            .recipe { context, provider ->
                val glass = dye.blockOf("stained_glass")
                provider.square(context.asIngredient(), RecipeCategory.BUILDING_BLOCKS, { glass }, true)
            }
            .model { context, provider ->
                provider.generated(context, Constants.MOD_ID.createId("item/$QUARK/${context.name}"))
            }
            .lang("${dye.translation} Glass Shard")
            .germanLang("${dye.germanTranslation(Genus.F)} Glasscherbe")
            .register()
    }

    val STOOLS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_quark_stool")
            .block { StoolBlock(null, dye) }
            .optionalTag(DyedTags.Blocks.QUARK_STOOLS)
            .quarkStoolBlockstate(dye)
            .lang("${dye.translation} Stool")
            .withItem {
                quarkStoolRecipe(dye)
                tab(CreativeModeTabs.COLORED_BLOCKS)
                tab(CreativeModeTabs.FUNCTIONAL_BLOCKS)
            }
            .register()
    }

    val SHINGLES = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_shingles")
            .block(::Block)
            .initialProperties { dye.blockOf("terracotta") }
            .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate { c, p ->
                p.simpleBlock(
                    c.get(),
                    p.models().cubeAll(c.name, Constants.MOD_ID.createId("block/quark/${c.name}"))
                )
            }
            .lang("${dye.translation} Terracotta Shingles")
            .germanLang("${dye.germanTranslation(Genus.F)} Schindeln")
            .withItem {
                tab(CreativeModeTabs.COLORED_BLOCKS)
                tab(CreativeModeTabs.BUILDING_BLOCKS)
                shinglesRecipes(dye)
            }
            .register()
    }

    val SHINGLES_SLABS = REGISTRATE.createSlabs(
        SHINGLES,
        "shingles",
        modifyBlock = { dye ->
            germanLang("${dye.germanTranslation(Genus.F)} Schindelstufe")
            blockstate { c, p ->
                val texture = Constants.MOD_ID.createId("block/$QUARK/${dye}_shingles")
                val double = Constants.MOD_ID.createId("block/${dye}_shingles")
                p.slabBlock(c.get(), double, texture)
            }
        },
        modifyItem = { dye ->
            recipe { context, provider ->
                provider.slab(SHINGLES[dye]!!.asIngredient(), BUILDING_BLOCKS, context, null, true)
                provider.stonecutting(TERRACOTTA[dye]!!.asIngredient(), BUILDING_BLOCKS, context, 2)
            }
        },
    )

    val SHINGLES_STAIRS = REGISTRATE.createStairs(
        SHINGLES,
        "shingles",
        modifyBlock = { dye ->
            germanLang("${dye.germanTranslation(Genus.F)} Schindeltreppe")
            blockstate { c, p ->
                val texture = Constants.MOD_ID.createId("block/$QUARK/${dye}_shingles")
                p.stairsBlock(c.get(), texture)
            }
        },
        modifyItem = { dye ->
            recipe { context, provider ->
                provider.stairs(SHINGLES[dye]!!.asIngredient(), BUILDING_BLOCKS, context, null, true)
                provider.stonecutting(TERRACOTTA[dye]!!.asIngredient(), BUILDING_BLOCKS, context)
            }
        },
    )

    val FRAMED_GLASS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_framed_glass")
            .block { ZetaGlassBlock(null, null, true, it) }
            .initialProperties { Blocks.GLASS }
            .properties { it.strength(3F, 10F) }
            .optionalTag(DyedTags.Blocks.FRAMED_GLASSES)
            .optionalTag(BlockTags.IMPERMEABLE)
            .optionalTag(BlockTags.NEEDS_STONE_TOOL)
            .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .optionalTag(Tags.Blocks.GLASS)
            .blockstate { c, p ->
                p.simpleBlock(
                    c.get(),
                    p.models().cubeAll(c.name, Constants.MOD_ID.createId("block/$QUARK/${dye}_framed_glass"))
                        .translucent()
                )
            }
            .lang("${dye.translation} Framed Glass")
            .germanLang("${dye.germanTranslation(Genus.I)} gerahmtes Glas")
            .withItem {
                tab(CreativeModeTabs.COLORED_BLOCKS)
                tab(CreativeModeTabs.BUILDING_BLOCKS)
                tag(Tags.Items.GLASS)
                model { c, p -> p.blockItem(c).translucent() }
                framedGlassRecipes(dye)
            }
            .register()
    }

    val FRAMED_GLASS_PANES = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_framed_glass_pane")
            .block {
                val parent = FRAMED_GLASS[dye]!!.get()
                ZetaInheritedPaneBlock(parent, null, BlockBehaviour.Properties.copy(parent))
            }
            .optionalTag(DyedTags.Blocks.FRAMED_GLASS_PANES)
            .optionalTag(BlockTags.NEEDS_STONE_TOOL)
            .optionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
            .optionalTag(Tags.Blocks.GLASS_PANES)
            .blockstate { c, p ->
                p.paneBlockWithRenderType(
                    c.get(),
                    Constants.MOD_ID.createId("block/$QUARK/${dye}_framed_glass"),
                    QUARK.createId("block/framed_glass_pane_top"),
                    TRANSLUCENT,
                )
            }
            .lang("${dye.translation} Framed Glass Pane")
            .germanLang("${dye.germanTranslation(Genus.F)} gerahmte Glasscheibe")
            .withItem {
                model { c, p ->
                    p.generated(c, Constants.MOD_ID.createId("block/$QUARK/${dye}_framed_glass")).translucent()
                }
                tag(Tags.Items.GLASS_PANES)
                tab(CreativeModeTabs.COLORED_BLOCKS)
                tab(CreativeModeTabs.BUILDING_BLOCKS)
                framedGlassPaneRecipes(dye)
            }
            .register()
    }

    fun register() {
        // Loads this class
    }

}