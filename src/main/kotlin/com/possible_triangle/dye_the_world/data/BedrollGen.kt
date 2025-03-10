package com.possible_triangle.dye_the_world.data

import com.possible_triangle.dye_the_world.Constants
import com.possible_triangle.dye_the_world.Constants.Mods.UPGRADE_AQUATIC
import com.possible_triangle.dye_the_world.dyeingRecipe
import com.possible_triangle.dye_the_world.extensions.createId
import com.possible_triangle.dye_the_world.extensions.createVariant
import com.possible_triangle.dye_the_world.extensions.recipe
import com.possible_triangle.dye_the_world.extensions.yRot
import com.possible_triangle.dye_the_world.index.DyedTags
import com.teamabnormals.upgrade_aquatic.common.block.BedrollBlock
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BedPart
import net.minecraftforge.client.model.generators.ConfiguredModel

fun <T : Block, P> BlockBuilder<T, P>.bedrollBlockstate(dye: DyeColor) = blockstate { context, provider ->
    val texture = Constants.MOD_ID.createId("block/$UPGRADE_AQUATIC/bedroll/${dye}")
    val particle = texture.withSuffix("_particle")

    val foot = provider.models().withExistingParent(context.name, UPGRADE_AQUATIC.createId("block/bedroll/template_bedroll_foot"))
        .texture("bedroll", texture)
        .texture("particle", particle)

    val head = provider.models().withExistingParent(context.name, UPGRADE_AQUATIC.createId("block/bedroll/template_bedroll_head"))
        .texture("bedroll", texture)
        .texture("particle", particle)

    provider.createVariant(context, BedrollBlock.OCCUPIED) { state ->
        val facing = state.getValue(BedrollBlock.FACING)
        val part = state.getValue(BedrollBlock.PART)

        val model = if(part == BedPart.FOOT) foot else head

        ConfiguredModel.builder()
            .modelFile(model)
            .rotationY(facing.yRot)
    }
}

fun <T : Item, P> ItemBuilder<T, P>.bedrollRecipe(dye: DyeColor) = recipe(UPGRADE_AQUATIC) { context, provider ->
    provider.dyeingRecipe(dye, DyedTags.Items.PET_BEDS, context)
}

fun <T : Item, P> ItemBuilder<T, P>.bedrollItemModel(dye: DyeColor) = model { context, provider ->
    provider.generated(context, Constants.MOD_ID.createId("item/$UPGRADE_AQUATIC/bedroll/$dye"))
}