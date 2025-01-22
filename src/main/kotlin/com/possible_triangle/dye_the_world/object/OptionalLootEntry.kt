package com.possible_triangle.dye_the_world.`object`

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.ForgeEntrypoint
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.storage.loot.LootContext
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer
import net.minecraft.world.level.storage.loot.functions.LootItemFunction
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Consumer
import kotlin.jvm.optionals.getOrNull

class OptionalLootEntry(
    val item: Item?,
    weight: Int,
    quality: Int,
    conditions: Array<LootItemCondition>,
    functions: Array<LootItemFunction>
) : LootPoolSingletonContainer(weight, quality, conditions, functions) {

    override fun getType(): LootPoolEntryType = ForgeEntrypoint.OPTIONAL_LOOT_ENTRY.get()

    override fun createItemStack(consumer: Consumer<ItemStack>, context: LootContext) {
        item?.let { consumer.accept(ItemStack(it)) }
    }

    companion object {
        fun create(item: ItemLike): Builder<*> {
            return simpleBuilder { weight, quality, conditions, functions ->
                OptionalLootEntry(
                    item.asItem(),
                    weight,
                    quality,
                    conditions,
                    functions
                )
            }
        }
    }

    object Serializer : LootPoolSingletonContainer.Serializer<OptionalLootEntry>() {
        override fun serializeCustom(json: JsonObject, entry: OptionalLootEntry, context: JsonSerializationContext) {
            super.serializeCustom(json, entry, context)
            val id = ForgeRegistries.ITEMS.getKey(
                entry.item ?: throw NullPointerException("Trying to serialize optional item")
            )
            json.addProperty("name", id.toString())
        }

        override fun deserialize(
            json: JsonObject,
            context: JsonDeserializationContext,
            weight: Int,
            quality: Int,
            conditions: Array<LootItemCondition>,
            functions: Array<LootItemFunction>
        ): OptionalLootEntry {
            val id = GsonHelper.getAsString(json, "name")
            val holder = ForgeRegistries.ITEMS.getHolder(ResourceLocation(id))
            if (holder.isEmpty) {
                Constants.LOGGER.debug("Loot item with ID '${id}' not present, loot table will drop nothing")
            }
            return OptionalLootEntry(holder.getOrNull()?.value(), weight, quality, conditions, functions)
        }
    }

}