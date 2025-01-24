package com.possible_triangle.dye_the_world.data

interface DyedRegistrateRecipeProvider {

    fun setNamespace(namespace: String)

    fun resetNamespace()

    fun getNamespace(): String?

}