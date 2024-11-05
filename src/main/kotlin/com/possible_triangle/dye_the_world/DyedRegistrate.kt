package com.possible_triangle.dye_the_world

import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import com.tterrag.registrate.providers.RegistrateTagsProvider
import net.minecraft.core.registries.Registries
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.minecraftforge.eventbus.api.IEventBus
import thedarkcolour.kotlinforforge.forge.MOD_BUS

class DyedRegistrate(modid: String) : AbstractRegistrate<DyedRegistrate>(modid) {

    fun register() {
        registerEventListeners(modEventBus)
    }

    override fun getModEventBus(): IEventBus {
        return MOD_BUS
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> TagKey<T>.provider(): ProviderType<out RegistrateTagsProvider<T>> {
        return when (registry) {
            Registries.BLOCK -> ProviderType.BLOCK_TAGS
            Registries.ITEM -> ProviderType.ITEM_TAGS
            else -> throw IllegalArgumentException("no tag provider known for registry ${registry.location()}")
        } as ProviderType<out RegistrateTagsProvider<T>>
    }

    fun <T : Any> TagKey<T>.addOptional(id: ResourceLocation) {
        setDataGenerator(id.path, registry, provider()) {
            it.addTag(this).addOptional(id)
        }
    }

    fun <T : Any> TagKey<T>.addOptional(tag: TagKey<T>) {
        setDataGenerator(tag.location.path, registry, provider()) {
            it.addTag(this).addOptionalTag(tag)
        }
    }

}

private fun RegistrateRecipeProvider.dyeingRecipe(
    dye: DyeColor,
    from: Ingredient,
    to: DataGenContext<*, out ItemLike>,
    build: ShapelessRecipeBuilder.() -> Unit = { },
) {
    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, to.get())
        .apply(build)
        .requires(dye.tag)
        .requires(from)
        .save(this, safeId(to.get()).withSuffix("_dyeing"))
}

fun RegistrateRecipeProvider.dyeingRecipe(
    dye: DyeColor,
    from: ItemLike,
    to: DataGenContext<*, out ItemLike>,
    build: ShapelessRecipeBuilder.() -> Unit = { },
) {
    val name = safeId(from).path
    dyeingRecipe(dye, Ingredient.of(from), to) {
        unlockedBy("has_${name}", RegistrateRecipeProvider.has(from))
        build()
    }
}

fun RegistrateRecipeProvider.dyeingRecipe(
    dye: DyeColor,
    from: TagKey<Item>,
    to: DataGenContext<*, out ItemLike>,
    build: ShapelessRecipeBuilder.() -> Unit = { },
) {
    dyeingRecipe(dye, Ingredient.of(from), to) {
        unlockedBy("has_${from.location.path}", RegistrateRecipeProvider.has(from))
        build()
    }
}
