package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.DyedRegistrate
import com.possible_triangle.dye_the_world.index.DyedQuark.GLASS_SHARDS
import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.BuilderCallback
import net.minecraft.advancements.critereon.EnchantmentPredicate
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.advancements.critereon.MinMaxBounds
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.storage.loot.IntRange
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay
import net.minecraft.world.level.storage.loot.functions.LimitCount
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.predicates.MatchTool
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator

private val HAS_SILK_TOUCH = MatchTool.toolMatches(
    ItemPredicate.Builder.item()
        .hasEnchantment(EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)))
)

private class SimpleBlockBuilder<P : AbstractRegistrate<*>>(
    owner: P, name: String,
    callback: BuilderCallback,
) :
    BlockBuilder<Block, P>(owner, owner, name, callback, ::Block, BlockBehaviour.Properties::of)

fun generateGlassShardLoot() {
    val registrate = DyedRegistrate(Constants.Mods.DYE_DEPOT)

    GLASS_SHARDS.forEach { (dye, shard) ->
        registrate.`object`("${dye}_stained_glass")
            .entry { name, callback -> SimpleBlockBuilder(registrate, name, callback) }
            .loot { tables, stainedGlass ->
                val entry = AlternativesEntry.alternatives(
                    LootItem.lootTableItem(stainedGlass).`when`(HAS_SILK_TOUCH),
                    LootItem.lootTableItem(shard)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 4F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 1))
                        .apply(LimitCount.limitCount(IntRange.range(1, 4)))
                        .apply(ApplyExplosionDecay.explosionDecay())
                )

                val table = LootTable.lootTable().withPool(LootPool.lootPool().add(entry))

                tables.add(stainedGlass, table)
            }
            .register()
    }

    registrate.register()
}