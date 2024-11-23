package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.CREATE
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.yRot
import com.simibubi.create.content.equipment.toolbox.ToolboxBlock
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import net.minecraftforge.client.model.generators.ConfiguredModel

fun <T : Block, P> BlockBuilder<T, P>.toolboxLoot() = loot { tables, block ->
    val pool = tables.applyExplosionDecay(block, LootPool.lootPool())
        .add(LootItem.lootTableItem(block))
        .setRolls(ConstantValue.exactly(1.0F))
        .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
        .apply(
            CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                .copy("UniqueId", "UniqueId")
        )
        .apply(
            CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                .copy("Inventory", "Inventory")
        )

    tables.add(block, LootTable.lootTable().withPool(pool))
}

fun <T : Block, P> BlockBuilder<T, P>.toolboxBlockstate(dye: DyeColor) = blockstate { context, provider ->
    provider.models()
        .withExistingParent("block/toolbox/lid/$dye", CREATE.createId("block/toolbox/lid/brown"))
        .texture("0", Constants.MOD_ID.createId("block/$CREATE/toolbox/$dye"))

    val model = provider.models()
        .withExistingParent(context.name, CREATE.createId("block/toolbox/block"))
        .texture("0", Constants.MOD_ID.createId("block/$CREATE/toolbox/$dye"))

    provider.createVariant(context) { state ->
        val facing = state.getValue(ToolboxBlock.FACING)

        ConfiguredModel.builder()
            .modelFile(model)
            .rotationY(facing.yRot)
    }
}

fun <T : Item, P> ItemBuilder<T, P>.toolboxItemModel(dye: DyeColor) = model { context, provider ->
    val parent = CREATE.createId("block/toolbox/item")
    provider.withExistingParent(context.name, parent)
        .texture("0", Constants.MOD_ID.createId("block/$CREATE/toolbox/$dye"))
}