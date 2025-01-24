package com.possible_triangle.dye_the_world.data

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.possible_triangle.dye_the_world.Constants
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraftforge.common.crafting.CraftingHelper
import net.minecraftforge.common.crafting.conditions.ICondition
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition

class ConditionalFinishedRecipe(
    private val inner: FinishedRecipe,
    private val conditions: Collection<ICondition>,
) : FinishedRecipe {

    companion object {
        private val IGNORED_NAMESPACES = listOf("minecraft", "forge", Constants.Mods.DYE_DEPOT, Constants.MOD_ID)
    }

    override fun serializeRecipeData(json: JsonObject) {
        inner.serializeRecipeData(json)

        val conditions = conditions.ifEmpty {
            if (!IGNORED_NAMESPACES.contains(id.namespace)) listOf(ModLoadedCondition(id.namespace))
            emptyList()
        }

        if (conditions.isNotEmpty()) {
            json.add("conditions", JsonArray().apply {
                conditions.forEach {
                    add(CraftingHelper.serialize(it))
                }
            })
        } else {
            throw IllegalArgumentException("Recipe '$id' is missing a condition")
        }
    }

    override fun getId() = inner.id

    override fun getType() = inner.type

    override fun serializeAdvancement() = inner.serializeAdvancement()

    override fun getAdvancementId() = inner.advancementId

}