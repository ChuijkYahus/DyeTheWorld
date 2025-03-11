package com.possible_triangle.dye_the_world.data

import net.minecraftforge.common.crafting.conditions.ICondition
import java.util.stream.Stream

interface DyedRegistrateRecipeProvider {

    fun pushCondition(condition: ICondition)

    fun popCondition()

    fun getCondition(): Stream<ICondition>

}