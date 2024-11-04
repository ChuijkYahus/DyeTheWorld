package com.possible_triangle.dye_the_world

import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.core.Direction
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.fml.ModList

fun isLoad(modid: String) = ModList.get().isLoaded(modid)

fun ifLoaded(modid: String, block: () -> Unit) {
    if (isLoad(modid)) block()
}

val Direction.yRot: Int
    get() = when (this) {
        Direction.EAST -> 90
        Direction.SOUTH -> 180
        Direction.WEST -> 270
        else -> 0
    }

fun <T : Any> Registry<T>.getOrThrow(id: ResourceLocation): T {
    val key = ResourceKey.create(key(), id)
    return getOrThrow(key)
}

fun String.createId(path: String) = ResourceLocation(this, path)

fun <T : Block, P> BlockBuilder<T, P>.withItem(
    factory: (T, Item.Properties) -> BlockItem = ::BlockItem,
    block: ItemBuilder<BlockItem, BlockBuilder<T, P>>.() -> Unit,
): BlockBuilder<T, P> {
    return item(factory)
        .apply(block)
        .build()
}