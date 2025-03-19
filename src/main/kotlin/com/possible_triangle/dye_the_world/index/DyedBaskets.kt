package com.possible_triangle.dye_the_world.index

import com.possible_triangle.dye_the_world.*
import com.possible_triangle.dye_the_world.Constants.Mods.DYE_DEPOT
import com.possible_triangle.dye_the_world.extensions.*
import com.possible_triangle.dye_the_world.`object`.block.DyeBasketBlock
import com.tterrag.registrate.providers.ProviderType
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.level.block.SoundType

object DyedBaskets {

    private val DYES = VANILLA_DYES + dyesFor(DYE_DEPOT)

    private val REGISTRATE = DyedRegistrate.create(DYE_DEPOT)

    val POOF_SOUND = REGISTRATE.`object`("block.dye_basket.poof")
        .sound()
        .addMiscData(ProviderType.LANG) { it.add("subtitles.block.dye_depot.dye_basket.poof", "Dye poofs") }
        .register()

    val BASKETS = DYES.associateWith { dye ->
        REGISTRATE.`object`("${dye}_dye_basket")
            .block { DyeBasketBlock(it, dye) }
            .lang("${dye.translation} Dye Basket")
            .germanLang("${dye.germanTranslation(Genus.M)} Farbkorb")
            .optionalTag(BlockTags.MINEABLE_WITH_HOE)
            .optionalTag(DyedTags.Blocks.NON_CLEANABLE)
            .properties { it.strength(0.8F) }
            .properties { it.sound(SoundType.WOOL) }
            .properties { it.ignitedByLava() }
            .properties { it.mapColor(dye) }
            .blockstate { context, provider ->
                fun texture(suffix: String) =
                    Constants.MOD_ID.createId("block/basket/${dye}_$suffix")

                val model = provider.models().orientableWithBottom(
                    context.name,
                    texture("side"),
                    texture("front"),
                    texture("bottom"),
                    texture("top"),
                ).texture("particle", texture("top"))

                provider.horizontalBlock(context.get(), model)
            }
            .withItem {
                tab(CreativeModeTabs.COLORED_BLOCKS)
                optionalTag(DyedTags.Items.NON_CLEANABLE)
                recipe { context, provider ->
                    provider.storage({ dye.itemOf("dye") }, RecipeCategory.DECORATIONS, context)
                }
            }
            .register()
    }

    fun register() {
        REGISTRATE.register()
    }

}