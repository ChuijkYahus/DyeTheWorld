package com.possible_triangle.dye_the_world.data

import java.util.stream.Stream

interface DyedRegistrateRecipeProvider {

    fun pushNamespace(namespace: String)

    fun popNamespace()

    fun getNamespaces(): Stream<String>

}