package com.possible_triangle.dye_the_world.data

import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.ItemLike
import java.util.function.Consumer

class ShapedRecipeBuilderWithNbt(
    category: RecipeCategory,
    result: ItemLike,
    count: Int = 1,
) : ShapedRecipeBuilder(category, result, count) {

    override fun save(p_126141_: Consumer<FinishedRecipe?>, p_126142_: ResourceLocation) {
        super.save(p_126141_, p_126142_)
    }

}