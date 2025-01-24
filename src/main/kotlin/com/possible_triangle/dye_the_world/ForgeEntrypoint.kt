package com.possible_triangle.dye_the_world

import com.possible_triangle.dye_the_world.data.generateGlassShardLoot
import com.possible_triangle.dye_the_world.data.generateTags
import com.possible_triangle.dye_the_world.extensions.ifLoaded
import com.possible_triangle.dye_the_world.index.*
import com.possible_triangle.dye_the_world.`object`.BlockLessStatePropertyCondition
import com.possible_triangle.dye_the_world.`object`.OptionalLootEntry
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType
import net.minecraftforge.data.loading.DatagenModLoader
import net.minecraftforge.fml.common.Mod

@Mod(Constants.MOD_ID)
object ForgeEntrypoint {

    val REGISTRATE = DyedRegistrate(Constants.MOD_ID)

    val OPTIONAL_LOOT_ENTRY = REGISTRATE.`object`("item")
        .generic(Registries.LOOT_POOL_ENTRY_TYPE) { LootPoolEntryType(OptionalLootEntry.Serializer) }
        .register()

    val OPTIONAL_STATE_PROPERTY_CONDITION = REGISTRATE.`object`("block_state_property")
        .generic(Registries.LOOT_CONDITION_TYPE) { LootItemConditionType(BlockLessStatePropertyCondition.Serializer) }
        .register()

    init {
        REGISTRATE.register()

        ifLoaded(Constants.Mods.ANOTHER_FURNITURE) {
            DyedFurniture.register()
        }

        ifLoaded(Constants.Mods.QUARK) {
            DyedQuark.register()
        }

        ifLoaded(Constants.Mods.CLAYWORKS) {
            DyedClayworks.register()
        }

        ifLoaded(Constants.Mods.FARMERS_DELIGHT) {
            DyedDelight.register()
        }

        ifLoaded(Constants.Mods.ALEXS_CAVES) {
            DyedCaves.register()
        }

        ifLoaded(Constants.Mods.DOMESTICATION_INNOVATION) {
            DyedDomestication.register()
        }

        if (DatagenModLoader.isRunningDataGen()) {
            REGISTRATE.generateTags()
            generateGlassShardLoot()

            // These are blocks & Items which are automatically added for all dye colors, included modded ones.
            // Therefore, they only lack assets & data files, which have to be generated, but do not need to be registered.
            DyedSupplementaries.register()
            DyedComforts.register()
            DyedCreate.register()
            DyedCreateDeco.register()
            DyedRailways.register()
            DyedChalk.registerDatagen()
        }
    }

}