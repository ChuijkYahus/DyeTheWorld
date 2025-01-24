package com.possible_triangle.dye_the_world.`object`

import com.google.common.collect.ImmutableSet
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.possible_triangle.dye_the_world.ForgeEntrypoint
import net.minecraft.advancements.critereon.StatePropertiesPredicate
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.storage.loot.LootContext
import net.minecraft.world.level.storage.loot.parameters.LootContextParam
import net.minecraft.world.level.storage.loot.parameters.LootContextParams
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType
import net.minecraft.world.level.storage.loot.Serializer as ConditionSerializer

class BlockLessStatePropertyCondition(private val predicate: StatePropertiesPredicate) : LootItemCondition {

    override fun getType(): LootItemConditionType = ForgeEntrypoint.OPTIONAL_STATE_PROPERTY_CONDITION.get()

    override fun getReferencedContextParams(): MutableSet<LootContextParam<*>> {
        return ImmutableSet.of<LootContextParam<*>?>(LootContextParams.BLOCK_STATE)
    }

    override fun test(context: LootContext): Boolean {
        val state = context.getParamOrNull(LootContextParams.BLOCK_STATE)
        return state != null &&  predicate.matches(state)
    }

    companion object {
        fun of(factory: StatePropertiesPredicate.Builder.() -> Unit): LootItemCondition.Builder {
            val predicate = StatePropertiesPredicate.Builder.properties()
                .apply(factory)
                .build()
            return object : LootItemCondition.Builder {
                override fun build(): LootItemCondition {
                    return BlockLessStatePropertyCondition(predicate)
                }
            }
        }

        fun wrapped(block: Block): LootItemBlockStatePropertyCondition.Builder {
            lateinit var predicate: StatePropertiesPredicate

            return object : LootItemBlockStatePropertyCondition.Builder(block) {
                override fun setProperties(builder: StatePropertiesPredicate.Builder) = apply {
                    predicate = builder.build()
                }

                override fun build(): LootItemCondition {
                    return BlockLessStatePropertyCondition(predicate)
                }
            }
        }
    }

    object Serializer : ConditionSerializer<BlockLessStatePropertyCondition> {
        override fun serialize(
            json: JsonObject,
            value: BlockLessStatePropertyCondition,
            context: JsonSerializationContext
        ) {
            json.add("properties", value.predicate.serializeToJson())
        }

        override fun deserialize(
            json: JsonObject,
            context: JsonDeserializationContext
        ): BlockLessStatePropertyCondition {
            val predicate = StatePropertiesPredicate.fromJson(json.get("properties"))
            return BlockLessStatePropertyCondition(predicate)
        }
    }

}