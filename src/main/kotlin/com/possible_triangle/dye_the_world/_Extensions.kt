package com.possible_triangle.dye_the_world

import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.BlockEntityBuilder
import com.tterrag.registrate.builders.Builder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.util.DataIngredient
import com.tterrag.registrate.util.nullness.NonNullSupplier
import net.minecraft.core.Direction
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.Property
import net.minecraftforge.client.model.generators.BlockStateProvider
import net.minecraftforge.client.model.generators.ConfiguredModel
import net.minecraftforge.data.loading.DatagenModLoader
import net.minecraftforge.fml.ModList
import java.util.*

fun isLoad(modid: String) = ModList.get().isLoaded(modid)

fun ifLoaded(modid: String, block: () -> Unit) {
    if (isLoad(modid) || DatagenModLoader.isRunningDataGen()) block()
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

fun <T : BlockEntity, P> BlockEntityBuilder<T, P>.validBlocks(
    values: Collection<NonNullSupplier<out Block>>,
): BlockEntityBuilder<T, P> {
    return validBlocks(*values.toTypedArray())
}

val DyeColor.translation get() = serializedName.replaceFirstChar { it.uppercase(Locale.ROOT) }

val <R, T : R, P, S : Builder<R, T, P, S>> Builder<R, T, P, S>.namespace
    get(): String {
        return when (val parent = parent) {
            is AbstractRegistrate<*> -> parent.modid
            is Builder<*, *, *, *> -> parent.namespace
            else -> error("Unable to locate registrate ancestor")
        }
    }

fun BlockStateProvider.createVariant(
    block: NonNullSupplier<out Block>,
    vararg ignored: Property<*>,
    mapper: (BlockState) -> ConfiguredModel.Builder<*>,
) {
    getVariantBuilder(block.get()).forAllStatesExcept(
        { mapper(it).build() },
        BlockStateProperties.WATERLOGGED,
        *ignored
    )
}

fun NonNullSupplier<out ItemLike>.asIngredient() = DataIngredient.items(this)

fun <K, V> Map<V,K>.inverse() = map { it.value to it.key }.toMap()