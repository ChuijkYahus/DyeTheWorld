package com.possible_triangle.dye_the_world

import com.tterrag.registrate.Registrate
import net.minecraftforge.eventbus.api.IEventBus
import thedarkcolour.kotlinforforge.forge.MOD_BUS

class KotlinRegistrate(modid: String) : Registrate(modid) {

    init {
        registerEventListeners(modEventBus)
    }

    override fun getModEventBus(): IEventBus {
        return MOD_BUS
    }

}