package com.possible_triangle.dye_the_world

import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import com.tterrag.registrate.providers.RegistrateTagsProvider
import net.minecraft.core.registries.BuiltInRegistries
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
import java.util.function.Supplier

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

}

private fun <T : Item, P> ItemBuilder<T, P>.dyeingRecipe(
    dye: DyeColor,
    from: () -> Ingredient,
    build: ShapelessRecipeBuilder.() -> Unit = { },
) = recipe { context, provider ->
    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, context.get())
        .apply(build)
        .requires(dye.tag)
        .requires(from())
        .save(provider, provider.safeId(context.get()).withSuffix("_dyeing"))
}

fun <T : Item, P> ItemBuilder<T, P>.dyeingRecipe(
    dye: DyeColor,
    from: Supplier<out ItemLike>,
    build: ShapelessRecipeBuilder.() -> Unit = { },
): ItemBuilder<T, P> {
    return dyeingRecipe(dye, { Ingredient.of(from.get()) }, {
        val name = BuiltInRegistries.ITEM.getKey(from.get().asItem()).path
        unlockedBy("has_${name}", RegistrateRecipeProvider.has(from.get()))
        build()
    })
}

fun <T : Item, P> ItemBuilder<T, P>.dyeingRecipe(
    dye: DyeColor,
    from: TagKey<Item>,
    build: ShapelessRecipeBuilder.() -> Unit = { },
): ItemBuilder<T, P> {
    return dyeingRecipe(dye, { Ingredient.of(from) }) {
        unlockedBy("has_${from.location.path}", RegistrateRecipeProvider.has(from))
        build()
    }
}
