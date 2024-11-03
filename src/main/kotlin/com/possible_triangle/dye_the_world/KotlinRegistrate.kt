package com.possible_triangle.dye_the_world

import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import com.tterrag.registrate.providers.RegistrateTagsProvider
import com.tterrag.registrate.util.DataIngredient
import net.minecraft.core.registries.Registries
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraftforge.eventbus.api.IEventBus
import thedarkcolour.kotlinforforge.forge.MOD_BUS

class KotlinRegistrate(modid: String) : AbstractRegistrate<KotlinRegistrate>(modid) {

    fun register() {
        registerEventListeners(modEventBus)
    }

    override fun getModEventBus(): IEventBus {
        return MOD_BUS
    }

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

fun DyeColor.dyeingRecipe(
    context: DataGenContext<out ItemLike, out ItemLike>,
    provider: RegistrateRecipeProvider,
    from: Ingredient,
    build: ShapelessRecipeBuilder.() -> Unit = {  },
) {
    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, context.get())
        .apply(build)
        .requires(tag)
        .requires(from)
        .save(provider, provider.safeId(context.get()).withSuffix("_dyeing"))
}

fun DyeColor.dyeingRecipe(
    context: DataGenContext<out ItemLike, out ItemLike>,
    provider: RegistrateRecipeProvider,
    from: ItemLike,
    build: ShapelessRecipeBuilder.() -> Unit = {  },
) {
    dyeingRecipe(context, provider, Ingredient.of(from)) {
        unlockedBy("has_${provider.safeId(from)}", RegistrateRecipeProvider.has(from))
        build()
    }
}

fun DyeColor.dyeingRecipe(
    context: DataGenContext<out ItemLike, out ItemLike>,
    provider: RegistrateRecipeProvider,
    from: TagKey<Item>,
    build: ShapelessRecipeBuilder.() -> Unit = {  },
) {
    dyeingRecipe(context, provider, Ingredient.of(from)) {
        unlockedBy("has_${provider.safeId(DataIngredient.tag(from))}", RegistrateRecipeProvider.has(from))
        build()
    }
}